package pl.hotel.application.views.kontakt;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import pl.hotel.application.views.MainLayout;

@PageTitle("Kontakt")
@Route(value = "kontakt", layout = MainLayout.class)
@AnonymousAllowed
public class KontaktView extends VerticalLayout {

	private H1 info = new H1("Kontakt:");	
	private Html html = new Html("<div>Jan Malinowski<br>"
			+ "E-mail: janmal000@pbs.edu.pl<br>"
			+ "Mikołaj Barański<br>"
			+ "E-mail: mikbar002@pbs.edu.pl<br>");
	private H2 contactInfo = new H2(html);
    public KontaktView() {
        setSpacing(false);
        add(contactInfo);
        contactInfo.getStyle().set("text-align", "left");
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
