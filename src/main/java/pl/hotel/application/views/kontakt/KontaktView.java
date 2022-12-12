package pl.hotel.application.views.kontakt;

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

    public KontaktView() {
        setSpacing(false);

  

        setSizeFull();
 //       setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
