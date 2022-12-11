package pl.hotel.application.views.rezerwacja;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import javax.annotation.security.PermitAll;

import pl.hotel.application.data.service.ReservationService;
import pl.hotel.application.views.MainLayout;

@PageTitle("Rezerwacja")
@Route(value = "rezerwacja", layout = MainLayout.class)
//@PermitAll
@AnonymousAllowed
public class RezerwacjaView extends VerticalLayout {
	
	private final ReservationService reservationService;
	private TextField userName = new TextField("Username");
    private TextField name = new TextField("Name");
    private EmailField email = new EmailField("Email address");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    public RezerwacjaView(ReservationService reservationService) {
        this.reservationService = reservationService;
        setSpacing(false);
        setSizeFull();
 //       setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
