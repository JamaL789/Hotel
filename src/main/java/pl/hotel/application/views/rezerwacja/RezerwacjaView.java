package pl.hotel.application.views.rezerwacja;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
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
    private DatePicker dateFrom = new DatePicker("Od:");
    private DatePicker dateTo = new DatePicker("Do:");
    private Button cancel = new Button("Anuluj");
    private Button reserve = new Button("Rezerwuj");
    private ComboBox<RoomType> roomTypeCombobox = new ComboBox<>("Pokój:");
    public RezerwacjaView(ReservationService reservationService) {
        this.reservationService = reservationService;
        setSpacing(false);
        setSizeFull();
        List<RoomType> typeList = new ArrayList<>();
        typeList.add(RoomType.SMALL);
        typeList.add(RoomType.MEDIUM);
        typeList.add(RoomType.LARGE);
        typeList.add(RoomType.APARTMENT);
        List<String> stringList = new ArrayList<>();
        stringList.add("Maly");
        stringList.add("Sredni");
        stringList.add("Duzy");
        stringList.add("Apartament");
        roomTypeCombobox.setItems(typeList);
   //     roomTypeCombobox.
        add(new H3("Zarezerwuj już teraz:"));
        add(dateFrom);
        add(dateTo);
        add(roomTypeCombobox);
        add(reserve);
        add(cancel);
 //       setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
