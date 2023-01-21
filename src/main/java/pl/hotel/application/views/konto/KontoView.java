package pl.hotel.application.views.konto;

import javax.annotation.security.PermitAll;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
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

import pl.hotel.application.data.Role;
import pl.hotel.application.data.entity.Reservation;
import pl.hotel.application.data.entity.User;
import pl.hotel.application.data.service.ReservationService;
import pl.hotel.application.data.service.UserService;
import pl.hotel.application.views.MainLayout;

@PageTitle("Konto")
@Route(value = "konto", layout = MainLayout.class)
@AnonymousAllowed
public class KontoView extends VerticalLayout {

	private final UserService userService;
	private final ReservationService reservationService;

	private boolean isRegistration = false;
	private H2 loginInfo = new H2("Zaloguj się:");
	private H5 registerQuestion = new H5("Nie posiadasz konta?");
	private Button logIn = new Button("Zaloguj się");
	private Button register = new Button("Rejestracja");
	private Button comeback = new Button("Powrót");
	private Button logOut = new Button("Wyloguj się");
	private Button reservationHistory = new Button("Rezerwacje");
	private Button modifyInfo = new Button("Edytuj informacje");
	private Grid<Reservation> reservations = new Grid<>(Reservation.class, false);
	private TextField username = new TextField("Nazwa użytkownika:");
	private TextField name = new TextField("Imię i nazwisko:");
	private EmailField email = new EmailField("E-mail:");
	private PasswordField password = new PasswordField("Hasło:");
	private PasswordField secondPassword = new PasswordField("Powtórz hasło:");
	private boolean duringEdition = false;
	private User userEdit = new User();
	
	public KontoView(UserService userService, ReservationService reservationService) {
		this.userService = userService;
		this.reservationService = reservationService;
		setSpacing(false);
		add(loginInfo, username, password, logIn, registerQuestion, register);
		// przycisk od ustawiania widocznosci rezerwacji
		reservationHistory.addClickListener(e -> {
			if (reservations.isVisible()) {
				reservations.setVisible(false);
			} else {
				reservations.setVisible(true);
			}
		});
		// przycisk od modyfikacji informacji o userze
		modifyInfo.addClickListener(e -> {

			if (!duringEdition) {
				userEdit = userService.getUserByNick(username.getValue());
				email.setEnabled(true);
				name.setEnabled(true);
				password.setEnabled(true);
				username.setEnabled(true);
				modifyInfo.setText("Zatwierdz zmiany");
				duringEdition = true;
			} else {
				if (email.getValue() != "" && name.getValue() != "" && password.getValue() != ""
						&& username.getValue() != "") {
					userService.updateUserInfo(username.getValue(), name.getValue(), password.getValue(),
							email.getValue(), userEdit.getId());
					duringEdition = false;
					email.setEnabled(false);
					name.setEnabled(false);
					password.setEnabled(false);
					username.setEnabled(false);
					modifyInfo.setText("Edytuj informacje");
					Notification.show("Zaktualizowano informacje!");
				}else {
					Notification.show("Wypełnij wszystkie pola!");
				}
			}
		});
		reservations.addColumns("reservationNumber", "from", "to", "description", "totalFee");
		// logowanie
		logIn.addClickListener(e -> {
			if (username.getValue() != "" && password.getValue() != "") {
				User user = userService.getUserByNick(username.getValue());
				if (!(user == null)) {
					if (user.getPassword().equals(password.getValue())) {
						loginInfo.setText("Twoje dane: ");
						reservations.setItems(reservationService.getReservationsByUser(user));
						add(email, name, reservationHistory, reservations, modifyInfo, logOut);
						reservations.setVisible(false);
						remove(registerQuestion, register, logIn);
						email.setValue(user.getEmail());
						email.setEnabled(false);
						username.setValue(user.getUsername());
						name.setEnabled(false);
						password.setEnabled(false);
						username.setEnabled(false);
						name.setValue(user.getName());
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
		// wylogowywanie
		logOut.addClickListener(e -> {
			remove(email, name, reservationHistory, reservations, modifyInfo, logOut);
			add(logIn, registerQuestion, register);
			email.setEnabled(true);
			username.setEnabled(true);
			username.clear();
			password.setEnabled(true);
			password.clear();
			name.setEnabled(true);
			loginInfo.setText("Zaloguj się");
			name.clear();
			email.clear();
		});
		// rejestracja
		register.addClickListener(e -> {
			if (isRegistration) {
				User u = userService.getUserByNick(username.getValue());
				if (u == null && username.getValue() != "" && name.getValue() != "" && email.getValue() != ""
						&& !(password.getValue().isEmpty()) && password.getValue().equals(secondPassword.getValue())) {
					User user = new User();
					user.setName(name.getValue());
					user.setUsername(username.getValue());
					user.setEmail(email.getValue());
					user.setPassword(password.getValue());
					userService.addUser(user);
					isRegistration = false;
					Notification.show("Konto zostało pomyślnie utworzone!");
					loginInfo.setText("Zaloguj się:");
					remove(secondPassword, email, name, comeback, register);
					registerQuestion.setVisible(true);
					register.setText("Rejestracja");
					add(logIn, registerQuestion, register);
				} else {
					Notification.show("Wypełnij poprawnie wszystkie pola!");
				}
			} else {
				isRegistration = true;
				loginInfo.setText("Wypełnij poniższe pola:");
				remove(logIn, registerQuestion, password, register);
				add(name, email, password, secondPassword, register, comeback);
				register.setText("Zarejestruj się");
			}
		});
		// powrót z ekranu rejestracji
		comeback.addClickListener(e -> {
			isRegistration = false;
			loginInfo.setText("Zaloguj się:");
			remove(secondPassword, email, name, comeback, register);
			registerQuestion.setVisible(true);
			register.setText("Rejestracja");
			add(logIn, registerQuestion, register);
		});
		setSizeFull();
		// setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("text-align", "center");
	}

}
