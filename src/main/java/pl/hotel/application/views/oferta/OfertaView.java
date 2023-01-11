package pl.hotel.application.views.oferta;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import pl.hotel.application.views.MainLayout;
import pl.hotel.application.views.MainLayout.MenuItemInfo;
import pl.hotel.application.views.rezerwacja.RezerwacjaView;

@PageTitle("Oferta")
@Route(value = "oferta", layout = MainLayout.class)
@AnonymousAllowed
public class OfertaView extends VerticalLayout {

	private H1 info = new H1("Nasza oferta");	
	private Html html = new Html("<div>Wszystkie ceny obejmują koszt za jedną dobę:<br>"
			+ "Pokój jednoosobowy (mały) - 80 zł<br>"
			+ "Pokój jednoosobowy (mały) z balkonem - 100 zł<br>"
			+ "Pokój dwuosobowy (średni) - 160 zł<br>"
			+ "Pokój dwuosobowy (średni) z balkonem - 200 zł<br>"
			+ "Pokój czteroosobowy (duży) - 350 zł<br>"
			+ "Pokój czteroosobowy (duży) z balkonem - 400 zł<br>"
			+ "Apartament (z balkonem) - 500 zł</div>");
	private H2 roomInfo = new H2(html);
	private RouterLink reservation = new RouterLink();
    public OfertaView() {
        setSpacing(false);
        add(info, roomInfo);
        info.getStyle().set("text-align", "center");
        roomInfo.getStyle().set("text-align", "left");
        reservation.setRoute(RezerwacjaView.class);
        reservation.add("Zarezerwuj już dziś!");
        reservation.addClassNames("flex", "gap-xs", "h-m", "items-center", "px-s", "text-body");
    	add(reservation);
        setSizeFull();
 //       setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
   //     getStyle().set("text-align", "center");
    }

}
