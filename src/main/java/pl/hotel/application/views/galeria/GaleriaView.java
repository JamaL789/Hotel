package pl.hotel.application.views.galeria;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import pl.hotel.application.views.MainLayout;

@PageTitle("Galeria")
@Route(value = "galeria", layout = MainLayout.class)
@AnonymousAllowed
public class GaleriaView extends VerticalLayout {

    public GaleriaView() {
        setSpacing(false);


        setSizeFull();
  //      setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
