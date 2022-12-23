package pl.hotel.application.views.rezerwacja;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import ch.qos.logback.core.Layout;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;

import pl.hotel.application.data.RoomType;
import pl.hotel.application.data.entity.Reservation;
import pl.hotel.application.data.entity.Room;
import pl.hotel.application.data.entity.User;
import pl.hotel.application.data.service.ReservationService;
import pl.hotel.application.data.service.RoomService;
import pl.hotel.application.data.service.UserService;
import pl.hotel.application.views.MainLayout;

@PageTitle("Rezerwacja")
@Route(value = "rezerwacja", layout = MainLayout.class)
//@PermitAll
@AnonymousAllowed
public class RezerwacjaView extends VerticalLayout {

	private final ReservationService reservationService;
	private final UserService userService;
	private final RoomService roomService;
	private TextField username = new TextField("Nazwa użytkownika:");
	private TextField name = new TextField("Imię i nazwisko:");
	private PasswordField password = new PasswordField("Hasło:");
	private DatePicker dateFrom = new DatePicker("Od:");
	private DatePicker dateTo = new DatePicker("Do:");
	private Button cancel = new Button("Anuluj");
	private Button cancelReservations = new Button("Anuluj rezerwacje");
	private Button reserve = new Button("Rezerwuj");
	private Button logIn = new Button("Zaloguj się");
	private Button logOut = new Button("Wyloguj się");
	private Button addToCart = new Button("Dodaj");
	private Button summary = new Button("Podsumowanie");
	
	private Checkbox withBalcony = new Checkbox("Z balkonem?");
	private H3 info = new H3("Zaloguj się, aby dokonać rezerwacji:");
	private ComboBox<RoomType> roomTypeCombobox = new ComboBox<>("Pokój:");
	private List<Reservation> reservationsInCart = new ArrayList<>();
	private Grid<Reservation> reservationInCartGrid = new Grid<>(Reservation.class, true);
	
	private H3 totalFeeInfo = new H3();
	private int resIdIterator = 1;
	private double totalFee = 0;
	public RezerwacjaView(ReservationService reservationService, UserService userService, RoomService roomService) {
		this.reservationService = reservationService;
		this.userService = userService;
		this.roomService = roomService;
		setSpacing(false);
		setSizeFull();
		List<RoomType> typeList = new ArrayList<>();
		typeList.add(RoomType.Maly);
		typeList.add(RoomType.Sredni);
		typeList.add(RoomType.Duzy);
		typeList.add(RoomType.Apartament);
		roomTypeCombobox.setItems(typeList);
		add(info, username, password, logIn);
		VerticalLayout summaryLayout = new VerticalLayout();
		summaryLayout.add(reservationInCartGrid, totalFeeInfo, reserve, cancelReservations);
		summaryLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		summary.addClickListener(e->{
			reservationInCartGrid.setItems(reservationsInCart);
			
			info.setText("Podsumowanie:");
			totalFeeInfo.setText("Do zapłaty: " + totalFee);
			remove(dateFrom, dateTo, name, logOut, cancel, addToCart, summary, roomTypeCombobox, withBalcony);
			add(summaryLayout);
		});
		cancel.addClickListener(e->{
			dateFrom.setValue(null);
			dateTo.setValue(null);
			withBalcony.setValue(false);
			roomTypeCombobox.setValue(null);
			reservationsInCart.forEach(r->{
				reservationService.removeReservation(r);
			});
			reservationsInCart.clear();
			totalFee = 0;
			Notification.show("Anulowano wszystkie rezerwacje!");
		});
		addToCart.addClickListener(e -> {
			if (roomTypeCombobox.getValue() != null) {
				
		/*		if(roomTypeCombobox.getValue().equals(RoomType.Apartament)){
					room = roomService.getRoomByType(RoomType.Apartament);
				}else {
					room = roomService.getRoomByTypeAndBalcony(roomTypeCombobox.getValue(), withBalcony.getValue());
				}	*/
				if ((dateFrom.getValue().atStartOfDay().isEqual(LocalDate.now().atStartOfDay())
						|| dateFrom.getValue().atStartOfDay().isAfter(LocalDate.now().atStartOfDay()))
						&& dateTo.getValue().atStartOfDay().isAfter(LocalDate.now().atStartOfDay())
						&& dateTo.getValue().atStartOfDay().isAfter(dateFrom.getValue().atStartOfDay())){
//					Room room = new Room();
	//				Boolean x = withBalcony.getValue();
					List<Reservation> reservations = reservationService.getReservations();
				//	List<Room> rooms = reservationService.getReservations();
					Room maybeRoom = new Room();
					if(reservations.size()==0) {
						maybeRoom = roomService.getRoomByTypeAndBalcony(roomTypeCombobox.getValue(), withBalcony.getValue());
					}else {
						List<Room> rooms = reservationService.getReservations()
								.stream()
								.map(r->r.getRoom())
								.filter(r->r.getRoomType().equals(roomTypeCombobox.getValue())
										&& r.isBalcony()==withBalcony.getValue())
								.collect(Collectors.toList());
						maybeRoom = findARoom(rooms, reservationService, dateFrom, dateTo);
					}
							
					
					if(maybeRoom != null) {
						Reservation res = new Reservation();
						User u = userService.getUserByNick(username.getValue());
						res.setEnded(false);
						Duration diff = Duration.between(dateFrom.getValue().atStartOfDay(),
								dateTo.getValue().atStartOfDay());
						Long diffDays = diff.toDays();
						res.setFee(maybeRoom.getPrice());
						res.setFrom(dateFrom.getValue());
						res.setTo(dateTo.getValue());
						res.setTotalFee(maybeRoom.getPrice() * diffDays);
						if (dateFrom.getValue().atStartOfDay().isEqual(LocalDate.now().atStartOfDay())) {
							res.setStarted(true);
						} else {
							res.setStarted(false);
						}
						totalFee += maybeRoom.getPrice() * diffDays;
//						res.setReservationNumber(1);
						List<Reservation> reservs = reservationService.getReservations();
						Reservation ress = reservs.stream().max(Comparator.comparing(Reservation::getReservationNumber))
								.orElse(null);// collect(Collectors.toList());
						res.setReservationNumber(ress.getReservationNumber() + resIdIterator);
						res.setRoom(maybeRoom);
						res.setRoomUser(u);
						res.setPositionNumber(0);
						reservationsInCart.add(res);
						reservationService.addReservation(res);
						Notification.show("Dodano rezerwację do koszyka. Termin: " + dateFrom.getValue().toString());
						maybeRoom = null;
						resIdIterator++;
					}else {
						Notification.show("Brak wolnych pokojów tego typu!");
					}
					
				} else {
					Notification.show("Wybierz poprawny termin rozpoczęcia i zakończenia rezerwacji!");
				}
			} else {
				Notification.show("Wybierz pokój!");
			}
		});
		logIn.addClickListener(e -> {
			if (username.getValue() != "" && password.getValue() != "") {
				User user = userService.getUserByNick(username.getValue());
				if (!(user == null)) {
					if (user.getPassword().equals(password.getValue())) {

						name.setValue(user.getName());
						name.setEnabled(false);
						remove(logIn, password, username);
						add(name, cancel, dateFrom, dateTo, roomTypeCombobox, withBalcony, addToCart, summary, cancel, logOut);
						info.setText("Wypelnij ponizsze pola, aby dokonac rezerwacji: ");
					} else {
						Notification.show("Niepoprawne hasło!");
					}
				} else {
					Notification.show("Nie znaleziono użytkownika!");
				}
			} else {
				Notification.show("Wypełnij oba pola!");
			}
		});
		logOut.addClickListener(e -> {
			info.setText("Zaloguj się, aby dokonać rezerwacji:");
			remove(dateFrom, dateTo, name, logOut, cancel, addToCart, summary, roomTypeCombobox, withBalcony);
			add(username, password, logIn);
			dateFrom.clear();
			dateTo.clear();
			roomTypeCombobox.setValue(null);
			withBalcony.setValue(false);
			username.setValue("");
			password.setValue("");
			name.clear();
			totalFee = 0;
		});
		cancelReservations.addClickListener(e -> {
			dateFrom.setValue(null);
			dateTo.setValue(null);
			withBalcony.setValue(false);
			roomTypeCombobox.setValue(null);
			reservationsInCart.forEach(r->{
				reservationService.removeReservation(r);
			});
			reservationsInCart.clear();
			totalFee = 0;
			remove(summaryLayout);
			Notification.show("Anulowano wszystkie rezerwacje!");
		});
		roomTypeCombobox.addValueChangeListener(e -> {
			Room r = new Room();
			if(e.getValue().equals(RoomType.Apartament)){
				r = roomService.getRoomByType(RoomType.Apartament);
			}else {
				r = roomService.getRoomByTypeAndBalcony(e.getValue(), withBalcony.getValue());
			}		
			if(e.getOldValue().equals(RoomType.Apartament) && !(e.getValue().equals(RoomType.Apartament))) {
				withBalcony.setValue(false);
			}
		/*	
			if (r.getAmount() > 0) {
				Notification.show("Pokój tego typu jest dostępny!");
			} else {
				Notification.show("Brak dostępnych pokojów tego typu");
			}*/
			if (e.getValue().equals(RoomType.Apartament)) {
				withBalcony.setValue(true);
				withBalcony.setEnabled(false);
			} else {
				withBalcony.setEnabled(true);
			}
		});
		reserve.addClickListener(e -> {

//			reservationsInCart.stream().forEach(r -> {
//				reservationService.addReservation(r);
//				Room room = r.getRoom();
//				roomService.updateRoomCount(room.getAmountFree() - 1, room.getAmountReserved() + 1, room.getId());
//			});
			Notification.show("Dokonano " + reservationsInCart.size() + " rezerwacji!");
			remove(summaryLayout);
			add(name, cancel, dateFrom, dateTo, roomTypeCombobox, withBalcony, addToCart, summary, cancel, logOut);
			dateFrom.clear();
			dateTo.clear();
			roomTypeCombobox.setValue(null);
			withBalcony.setValue(false);
			
			totalFee = 0;
		});
		// setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("text-align", "center");
	}
	private Room findARoom(List<Room> rooms, ReservationService resService,
			DatePicker dateFrom, DatePicker dateTo){
//		Room[] readyRoom = {new Room()};
//		Boolean[] arr = {false};
		
		for(Room room : rooms) {
			List<Reservation> reservations = resService.getReservationsByRoom(room);
			for(Reservation res : reservations) {
				if(!(res.isEnded()) && 
						((res.getFrom().isBefore(dateFrom.getValue()) && res.getTo().isAfter(dateTo.getValue())) ||
						(res.getFrom().isAfter(dateFrom.getValue())&& res.getFrom().isBefore(dateTo.getValue())))) {
				}else {
	//				readyRoom[0] = room;
	//				arr[0] = true;
					return room; 
				}
			}
		}/*
		rooms.stream().forEach(room->{
			List<Reservation> reservations = resService.getReservationsByRoom(room);
			reservations.forEach(res->{
				if(!(res.isEnded()) && 
						((res.getFrom().isBefore(dateFrom.getValue()) && res.getTo().isAfter(dateTo.getValue())) ||
						(res.getFrom().isAfter(dateFrom.getValue())&& res.getFrom().isBefore(dateTo.getValue())))) {
				}else {
					readyRoom[0] = room;
					arr[0] = true;
					return;
				}
			});
		});*/
		return null;
		
	}
}
