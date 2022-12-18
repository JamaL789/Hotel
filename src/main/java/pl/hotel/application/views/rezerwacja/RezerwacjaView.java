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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;

import pl.hotel.application.data.RoomType;
import pl.hotel.application.data.entity.User;
import pl.hotel.application.data.service.ReservationService;
import pl.hotel.application.data.service.UserService;
import pl.hotel.application.views.MainLayout;

@PageTitle("Rezerwacja")
@Route(value = "rezerwacja", layout = MainLayout.class)
//@PermitAll
@AnonymousAllowed
public class RezerwacjaView extends VerticalLayout {

	private final ReservationService reservationService;
	private final UserService userService;

	private TextField username = new TextField("Nazwa użytkownika:");
	private TextField name = new TextField("Imię i nazwisko:");
	private TextField password = new TextField("Hasło:");
	private EmailField email = new EmailField("E-mail:");
	private DatePicker dateFrom = new DatePicker("Od:");
	private DatePicker dateTo = new DatePicker("Do:");
	private Button cancel = new Button("Anuluj");
	private Button reserve = new Button("Rezerwuj");
	private Button logIn = new Button("Zaloguj się");
	private Button logOut = new Button("Wyloguj się");
	private Checkbox balcony = new Checkbox("Z balkonem?");
	private H3 info = new H3("Zaloguj się, aby dokonać rezerwacji:");
	private ComboBox<RoomType> roomTypeCombobox = new ComboBox<>("Pokój:");

	public RezerwacjaView(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
        setSpacing(false);
        setSizeFull();
        List<RoomType> typeList = new ArrayList<>();
        typeList.add(RoomType.Maly);
        typeList.add(RoomType.Sredni);
        typeList.add(RoomType.Duzy);
        typeList.add(RoomType.Apartament);
        roomTypeCombobox.setItems(typeList);
        add(info);
        add(username);
        add(password);
        add(logIn);
   //     roomTypeCombobox.
        logIn.addClickListener(e -> {
			if (username.getValue() != "" && password.getValue() != "") {
				User user = userService.getUserByNick(username.getValue());
				if (!(user==null)) {
					if (user.getPassword().equals(password.getValue())) {
						remove(logIn);
						remove(password);
						remove(username);
						add(name);
						name.setValue(user.getName());						
						add(dateFrom);
				        add(dateTo);
				        add(roomTypeCombobox);
				        add(balcony);
				        add(reserve);
				        add(cancel);
						info.setText("Wypelnij ponizsze pola, aby dokonac rezerwacji: ");
												
						remove(logIn);
						email.setValue(user.getEmail());
						email.setEnabled(false);
						username.setValue(user.getName());
						name.setEnabled(false);
						password.setEnabled(false);
						username.setEnabled(false);
						name.setValue(user.getName());
						add(logOut);
					} else {
						Notification.show("Niepoprawne hasło!");
					}
				}else {
						Notification.show("Nie znaleziono użytkownika!");
					}
				}
			else {
				Notification.show("Wypełnij oba pola!");
			}
        });
        logOut.addClickListener(e -> {
			info.setText("Zaloguj się:");
			remove(dateFrom);
			remove(dateTo);
			remove(name);
			remove(logOut);
			remove(cancel);
			remove(reserve);
			remove(roomTypeCombobox);
			remove(balcony);
			add(username);			
			add(password);		
			add(logIn);
		});
		cancel.addClickListener(e ->{
			dateFrom.setValue(null);
			dateTo.setValue(null);
			balcony.setValue(false);
			roomTypeCombobox.setValue(null);
		});
 //       setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
