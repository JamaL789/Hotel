package pl.hotel.application.views.rezerwacja;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
	private Button reserve = new Button("Rezerwuj");
	private Button logIn = new Button("Zaloguj się");
	private Button logOut = new Button("Wyloguj się");
	private Checkbox balcony = new Checkbox("Z balkonem?");
	private H3 info = new H3("Zaloguj się, aby dokonać rezerwacji:");
	private ComboBox<RoomType> roomTypeCombobox = new ComboBox<>("Pokój:");

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

		logIn.addClickListener(e -> {
			if (username.getValue() != "" && password.getValue() != "") {
				User user = userService.getUserByNick(username.getValue());
				if (!(user == null)) {
					if (user.getPassword().equals(password.getValue())) {

						name.setValue(user.getName());
						name.setEnabled(false);
						remove(logIn, password, username);
						add(name, cancel, dateFrom, dateTo, roomTypeCombobox, balcony, reserve, cancel, logOut);
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
			remove(dateFrom, dateTo, name, logOut, cancel, reserve, roomTypeCombobox, balcony);
			add(username, password, logIn);
			dateFrom.clear();
			dateTo.clear();
			roomTypeCombobox.setValue(null);
			balcony.setValue(false);
			username.setValue("");
			password.setValue("");
			name.clear();
		});
		cancel.addClickListener(e -> {
			dateFrom.setValue(null);
			dateTo.setValue(null);
			balcony.setValue(false);
			roomTypeCombobox.setValue(null);
		});
		roomTypeCombobox.addValueChangeListener(e->{
			Room r = roomService.getRoomByType(e.getValue(), balcony.getValue());
			if(r.getAmountFree()>0) {
				Notification.show("Pokój tego typu jest dostępny!");
			}else {
				Notification.show("Brak dostępnych pokojów tego typu");
			}
			if(e.getValue().equals(RoomType.Apartament)) {
				balcony.setValue(true);
				balcony.setEnabled(false);
			}else {
				balcony.setEnabled(true);
			}
		});
		reserve.addClickListener(e -> {
			if (roomTypeCombobox.getValue() != null) {
				Room room = roomService.getRoomByType(roomTypeCombobox.getValue(), balcony.getValue());
				if ((dateFrom.getValue().atStartOfDay().isEqual(LocalDate.now().atStartOfDay())
						|| dateFrom.getValue().atStartOfDay().isAfter(LocalDate.now().atStartOfDay()))
						&& dateTo.getValue().atStartOfDay().isAfter(LocalDate.now().atStartOfDay())
						&& dateTo.getValue().atStartOfDay().isAfter(dateFrom.getValue().atStartOfDay())
						&& roomTypeCombobox.getValue() != null) {
					Reservation res = new Reservation();
					User u = userService.getUserByNick(username.getValue());
					res.setEnded(false);
					Duration diff = Duration.between(dateFrom.getValue().atStartOfDay(),
							dateTo.getValue().atStartOfDay());
					Long diffDays = diff.toDays();
					res.setFee(room.getPrice());
					res.setFrom(dateFrom.getValue());
					res.setTo(dateTo.getValue());
					res.setTotalFee(room.getPrice()*diffDays);
					if(dateFrom.getValue().atStartOfDay().isEqual(LocalDate.now().atStartOfDay())) {
						res.setStarted(true);
					}else {
						res.setStarted(false);
					}
//					res.setReservationNumber(1);
					List<Reservation> reservs = reservationService.getReservations();
					Reservation ress = reservs.stream()
							.max(Comparator.comparing(Reservation::getReservationNumber)).orElse(null);//collect(Collectors.toList());
					res.setReservationNumber(ress.getReservationNumber()+1);
					
					res.setRoomUser(u);
					reservationService.addReservation(res);
					res.setPositionNumber(0);
					Notification.show("Dokonano rezerwacji na termin " + dateFrom.getValue().toString());
					roomService.updateRoomCount(room.getAmountFree()-1, room.getAmountReserved()+1, room.getId());
					dateFrom.clear();
					dateTo.clear();
					roomTypeCombobox.clear();
					balcony.setValue(false);
					
				}else {
					Notification.show("Wybierz poprawny termin rozpoczęcia i zakończenia rezerwacji!");
				}
			}else {
				Notification.show("Wybierz pokój!");
			}

		});
		// setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("text-align", "center");
	}

}
