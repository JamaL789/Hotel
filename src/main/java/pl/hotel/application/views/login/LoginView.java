package pl.hotel.application.views.login;

import javax.annotation.security.PermitAll;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import pl.hotel.application.data.entity.User;
import pl.hotel.application.data.service.UserService;
import pl.hotel.application.security.AuthenticatedUser;
import pl.hotel.application.views.MainLayout;

//@AnonymousAllowed
@PermitAll
@PageTitle("Login")
@Route(value = "login", layout = MainLayout.class)
public class LoginView extends VerticalLayout {//implements BeforeEnterObserver {

	//private final AuthenticatedUser authenticatedUser;
//	private final LoginForm login = new LoginForm();
	private final UserService userService;
	private static final String LOGIN_SUCCESS_URL = "/";
	private H2 loginInfo = new H2("Zaloguj się:");
	private H5 registerQuestion = new H5("Nie posiadasz konta?");
	private Button logIn = new Button("Zaloguj się");
	private Button register = new Button("Rejestracja");
	private Button comeback = new Button("Powrót");
	private TextField name = new TextField("Imię i nazwisko:");
	private EmailField email = new EmailField("E-mail:");
	private TextField username = new TextField("Nazwa użytkownika:");	
	private PasswordField password = new PasswordField("Hasło:");
	private PasswordField secondPassword = new PasswordField("Powtórz hasło:");
	private boolean isRegistration = false;
	public LoginView(UserService userService) {
		this.userService = userService;
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
//		setJustifyContentMode(JustifyContentMode.CENTER);

//		login.setAction("login");
		add(loginInfo, username, password, logIn, registerQuestion, register);		
//		add(new H1(""), login);
		logIn.addClickListener(e -> {
			if (username.getValue() != "" && password.getValue() != "") {
				User user = userService.getUserByNick(username.getValue());
				if (!(user==null)) {
					if (user.getPassword().equals(password.getValue())) {
/*						loginInfo.setText("Twoje dane: ");
						reservations.setItems(reservationService.getReservationsByUser(user));
						add(email, name, reservationHistory, reservations, logOut);
						reservations.setVisible(false);
						remove(registerQuestion, register, logIn);						
						email.setValue(user.getEmail());
						email.setEnabled(false);
						username.setValue(user.getName());
						name.setEnabled(false);
						password.setEnabled(false);
						username.setEnabled(false);
						name.setValue(user.getName());*/
				        UI.getCurrent().getPage().setLocation(LOGIN_SUCCESS_URL);
				        
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
/*		logOut.addClickListener(e -> {
			remove(email,name, reservationHistory, reservations,logOut);
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
		});*/
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
		comeback.addClickListener(e -> {
			isRegistration = false;
			loginInfo.setText("Zaloguj się:");
			remove(secondPassword, email, name, comeback, register);			
			registerQuestion.setVisible(true);
			register.setText("Rejestracja");			
			add(logIn, registerQuestion, register);			
		});
	}

/*	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

		if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
			login.setError(true);
		}
	}*/

//	setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    
}
