package pl.hotel.application.views.galeria;

import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import pl.hotel.application.views.MainLayout;
import pl.hotel.application.views.rezerwacja.RezerwacjaView;

@PageTitle("Galeria")
@Route(value = "galeria", layout = MainLayout.class)
@AnonymousAllowed
public class GaleriaView extends VerticalLayout {

	private Button previous = new Button("Poprzedni");
	private Button next = new Button("Następny");
	private Image img1 = new Image("images/Hotel-small.png", "Mały pokój");
	private Image img2 = new Image("images/hotel-medium.jpg", "Średni pokój");
	private Image img3 = new Image("images/hotel-apartment.png", "Apartament");
	private Image img4 = new Image("images/hotel-pool.png", "Basen");
	private List<Image> images = new ArrayList();
	
		
	private HorizontalLayout buttons = new HorizontalLayout();
    public GaleriaView() {
        setSpacing(false);
        buttons.add(previous, next);
        previous.setEnabled(false);
        img1.setHeight("800px");
        img2.setHeight("800px");
        img3.setHeight("800px");
        img4.setHeight("800px");
        images.add(img1);
        images.add(img2);
        images.add(img3);
        images.add(img4);
        add(img1, img2, img3, img4);
        img1.setVisible(true);
        img2.setVisible(false);
        img3.setVisible(false);
        img4.setVisible(false);
        
        previous.addClickListener(e->{
        	if(img2.isVisible()) {
        		img2.setVisible(false);
        		img1.setVisible(true);
        		previous.setEnabled(false);
        	}
        	else if(img3.isVisible()) {
        		img3.setVisible(false);
        		img2.setVisible(true);
        	}
        	else if(img4.isVisible()) {
        		img4.setVisible(false);
        		img3.setVisible(true);
        		next.setEnabled(true);
        	}
        });
       
        next.addClickListener(e->{
        	if(img1.isVisible()) {
        		img2.setVisible(true);
        		img1.setVisible(false);
        		previous.setEnabled(true);
        	}
        	else if(img2.isVisible()) {
        		img2.setVisible(false);
        		img3.setVisible(true);
        	}
        	else if(img3.isVisible()) {
        		img3.setVisible(false);
        		img4.setVisible(true);
        		next.setEnabled(false);
        	}
        });
        add(buttons);
        setSizeFull();
  //      setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
