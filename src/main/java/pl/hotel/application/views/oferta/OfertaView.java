package pl.hotel.application.views.oferta;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import pl.hotel.application.views.MainLayout;

@PageTitle("Oferta")
@Route(value = "oferta", layout = MainLayout.class)
@AnonymousAllowed
public class OfertaView extends VerticalLayout {

    public OfertaView() {
        setSpacing(false);

      

        setSizeFull();
 //       setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
