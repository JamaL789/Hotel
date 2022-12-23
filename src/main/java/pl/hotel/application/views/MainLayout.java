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
import java.util.Optional;
import pl.hotel.application.data.entity.User;
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

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, String iconClass, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames("flex", "gap-xs", "h-m", "items-center", "px-s", "text-body");
            link.setRoute(view);
            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames("font-medium", "text-m", "whitespace-nowrap");

            link.add(text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

        /**
         * Simple wrapper to create icons using LineAwesome iconset. See
         * https://icons8.com/line-awesome
         */
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
//    private final SecurityService securityService;
//    private boolean userLogged = false;
    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
    //		SecurityService securityService) {
  //      this.securityService = securityService;
		this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
  //      Button logout = new Button("Log out", e -> securityService.logout()); 
        
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent(){//Button logout) {
        Header header = new Header();
        header.addClassNames("box-border", "flex", "flex-col", "w-full");

        Div layout = new Div();
        layout.addClassNames("flex", "items-center", "px-l");
//        H1 appName = new H1("Hotel");
//        appName.addClassNames("my-m", "me-auto", "text-l");
//        layout.add(appName);

/*        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName());
            StreamResource resource = new StreamResource("profile-pic",
                    () -> new ByteArrayInputStream(user.getProfilePicture()));
            avatar.setImageResource(resource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }
*/
        MenuBar userMenu = new MenuBar();
        userMenu.setThemeName("tertiary-inline contrast");
        MenuItem userName = userMenu.addItem("");
        Div div = new Div();
        layout.add(userMenu);
//        Anchor loginLink = new Anchor("login", "Sign in");
//        layout.add(loginLink);
        Nav nav = new Nav();
        nav.addClassNames("flex", "overflow-auto", "px-m", "py-xs");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("flex", "gap-s", "list-none", "m-0", "p-0");
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            if (accessChecker.hasAccess(menuItem.getView())) {
                list.add(menuItem);
            }

        }
 //       list.add(logout);
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
    	//hotel.getElement().getStyle().set("align-items", "center");
    	return menu;
/*        return new MenuItemInfo[]{ //
                new MenuItemInfo("Hotel", "", HotelView.class), //

                new MenuItemInfo("Oferta", "", OfertaView.class), //

                new MenuItemInfo("Galeria", "", GaleriaView.class), //

                new MenuItemInfo("Kontakt", "", KontaktView.class), //

                new MenuItemInfo("Konto", "", KontoView.class), //

                new MenuItemInfo("Rezerwacja", "", RezerwacjaView.class), //

        };*/
    }

}
