package pl.hotel.application.views.konto;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import pl.hotel.application.data.Role;
import pl.hotel.application.data.entity.User;
import pl.hotel.application.data.service.UserService;
import pl.hotel.application.views.MainLayout;

@PageTitle("Konto")
@Route(value = "konto", layout = MainLayout.class)
@AnonymousAllowed
public class KontoView extends VerticalLayout {

	private boolean isRegistration = false;
	private H2 loginInfo = new H2("Zaloguj się:");
	private H5 registerQuestion = new H5("Nie posiadasz konta?");
	private Button logIn = new Button("Zaloguj się");
	private Button register = new Button("Rejestracja");
	private Button comeback = new Button("Powrót");
	private Button logOut = new Button("Wyloguj się");
	private TextField username = new TextField("Nazwa użytkownika:");
	private TextField name = new TextField("Imię i nazwisko:");
	private TextField email = new TextField("E-mail:");
	private PasswordField password = new PasswordField("Hasło:");
	private PasswordField secondPassword = new PasswordField("Powtórz hasło:");
	private final UserService userService;

	public KontoView(UserService userService) {
		this.userService = userService;

		setSpacing(false);
		if (!isRegistration) {
			add(loginInfo);
			add(username);
			add(password);
			add(logIn);
			add(registerQuestion);
			add(register);
		}
		logIn.addClickListener(e -> {
			if (username.getValue() != "" && password.getValue() != "") {
				User user = userService.getUserByNick(username.getValue());
				if (!(user==null)) {
					if (user.getPassword().equals(password.getValue())) {
				//		remove(loginInfo);
						loginInfo.setText("Twoje dane: ");
						add(email);
						add(name);
						remove(registerQuestion);
						remove(register);
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
			email.setEnabled(true);
			remove(email);
			username.setEnabled(true);
			username.clear();
			password.setEnabled(true);
			password.clear();
			name.setEnabled(true);
			remove(username);
		//	add(loginInfo);
			loginInfo.setText("Zaloguj się");
			name.clear();
			email.clear();
			add(username);
			remove(password);
			add(password);
			remove(name);
			add(logIn);
			remove(logOut);
			add(registerQuestion);
			add(register);
		});
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
					remove(secondPassword);
					remove(email);
					remove(name);
					registerQuestion.setVisible(true);
					register.setText("Rejestracja");
					remove(comeback);
					remove(register);
					add(logIn);
					add(registerQuestion);
					add(register);
				} else {
					Notification.show("Wypełnij poprawnie wszystkie pola!");
				}
			} else {
				isRegistration = true;
				loginInfo.setText("Wypełnij poniższe pola:");
				remove(logIn);
				remove(registerQuestion);
				remove(password);
				remove(register);
				add(name);
				add(email);
				add(password);
				add(secondPassword);
				register.setText("Zarejestruj się");
				add(register);
				add(comeback);
			}
		});
		comeback.addClickListener(e -> {
			isRegistration = false;
			loginInfo.setText("Zaloguj się:");
			remove(secondPassword);
			remove(email);
			remove(name);
			registerQuestion.setVisible(true);
			register.setText("Rejestracja");
			remove(comeback);
			remove(register);
			add(logIn);
			add(registerQuestion);
			add(register);
		});
		setSizeFull();
		// setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("text-align", "center");
	}

}
