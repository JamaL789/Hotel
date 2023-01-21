package pl.hotel.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import pl.hotel.application.data.entity.Reservation;
import pl.hotel.application.data.entity.User;
import pl.hotel.application.data.service.ReservationService;
import pl.hotel.application.data.service.SecurityService;
import pl.hotel.application.security.AuthenticatedUser;
import pl.hotel.application.views.galeria.GaleriaView;
import pl.hotel.application.views.hotel.HotelView;
import pl.hotel.application.views.kontakt.KontaktView;
import pl.hotel.application.views.konto.KontoView;
import pl.hotel.application.views.login.LoginView;
import pl.hotel.application.views.oferta.OfertaView;
import pl.hotel.application.views.rezerwacja.RezerwacjaView;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, String iconClass, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            link.addClassNames("flex", "gap-xs", "h-m", "items-center", "px-s", "text-body");
            link.setRoute(view);
            Span text = new Span(menuTitle);
            text.addClassNames("font-medium", "text-m", "whitespace-nowrap");

            link.add(text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

        @NpmPackage(value = "line-awesome", version = "1.3.0")
        public static class LineAwesomeIcon extends Span {
            public LineAwesomeIcon(String lineawesomeClassnames) {
                // Use Lumo classnames for suitable font styling
                addClassNames("text-l", "text-secondary");
                if (!lineawesomeClassnames.isEmpty()) {
                    addClassNames(lineawesomeClassnames);
                }
            }
        }

    }
    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;
    private final ReservationService reservationService;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker,
    		ReservationService reservationService) {
		this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        this.reservationService = reservationService;
        // aktualizacja bazy danych
        List<Reservation> reservations = reservationService.getReservations();
        reservations.forEach(r->{
        	if((r.getFrom().atStartOfDay().isEqual(LocalDate.now().atStartOfDay()) && r.isStarted()==false)||
        			(r.getFrom().isBefore(LocalDate.now())&& r.isStarted()==false)){
        		r.setStarted(true);
        		reservationService.addReservation(r);
        	}
        	if((r.getTo().atStartOfDay().isEqual(LocalDate.now().atStartOfDay()) && r.isEnded()==false)||
        			(r.getTo().isBefore(LocalDate.now())&& r.isEnded()==false)) {
        		r.setEnded(true);
        		reservationService.addReservation(r);
        	}
        });
        
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent(){//Button logout) {
        Header header = new Header();
        header.addClassNames("box-border", "flex", "flex-col", "w-full");								

        Div layout = new Div();
        layout.addClassNames("flex", "items-center", "px-l");

        MenuBar userMenu = new MenuBar();
        userMenu.setThemeName("tertiary-inline contrast");
        MenuItem userName = userMenu.addItem("");
        Div div = new Div();
        layout.add(userMenu);
        Nav nav = new Nav();
        nav.addClassNames("flex", "overflow-auto", "px-m", "py-xs");

        UnorderedList list = new UnorderedList();
        list.addClassNames("flex", "gap-s", "list-none", "m-0", "p-0");
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            if (accessChecker.hasAccess(menuItem.getView())) {
                list.add(menuItem);
            }
        }
        header.add(layout, nav);
        header.getElement().getStyle().set("align-items", "center");
        header.getElement().getStyle().set("background-color", "black");
        return header;
    }

    private MenuItemInfo[] createMenuItems() {
    	MenuItemInfo hotel = new MenuItemInfo("Hotel", "", HotelView.class);
    	MenuItemInfo oferta = new MenuItemInfo("Oferta", "", OfertaView.class);
    	MenuItemInfo galeria = new MenuItemInfo("Galeria", "", GaleriaView.class);
    	MenuItemInfo kontakt = new MenuItemInfo("Kontakt", "", KontaktView.class);
    	MenuItemInfo konto = new MenuItemInfo("Konto", "", KontoView.class);
    	MenuItemInfo rezerwacja = new MenuItemInfo("Rezerwacja", "", RezerwacjaView.class);
    	MenuItemInfo login = new MenuItemInfo("Zaloguj się", "", LoginView.class);
    	MenuItemInfo[] menu = {hotel, oferta, galeria, kontakt, konto, rezerwacja, login};
    	return menu;
    }
}
