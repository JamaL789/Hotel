package pl.hotel.application.views.hotel;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import pl.hotel.application.views.MainLayout;

@PageTitle("Hotel")
@Route(value = "hotel", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class HotelView extends VerticalLayout {

    public HotelView() {
        setSpacing(false);
      //  getStyle().set("background-image", "url(frontend\\themes\\hotel.jpg)");
//        getStyle().set("background-image", "url(images/hotel.jpg)");
       // getStyle().set("background-image", "url(C:\\Users\\janma\\OneDrive\\Pulpit\\wallpapersden.com_burj-al-arab-hotel-dubai-uae_1920x1080.png)");
  //     getStyle().set("background-image", "url(https://images.hdqwalls.com/wallpapers/dubai-burj-al-arab-hotel-n7.jpg)");
//        Image img = new Image("images/empty-plant.png", "placeholder plant");
        H2 info = new H2("Witamy na stronie Hotelu!");
        Image img = new Image("images/hotel.jpg", "");
        img.setWidth("1024px");
        img.setHeight("768px");
        add(info);
        add(img);
    //    setSizeFull();
   //     setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
