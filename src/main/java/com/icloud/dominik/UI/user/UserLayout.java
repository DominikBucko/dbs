package com.icloud.dominik.UI.user;

import com.icloud.dominik.UI.admin.TicketApprovals;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class UserLayout extends AppLayout {
    public UserLayout() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink myTickets = new RouterLink("My Tickets", UserTickets.class);
        RouterLink availableAssets = new RouterLink("Available Assets", AssetsToRequest.class);
        addToDrawer(new VerticalLayout(
            myTickets,
            availableAssets
        ));

    }

     private void createHeader() {
        H2 title = new H2("SAM - Smart Asset Manager");
        title.addClassName("title");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        Button logout = new Button("Logout");
        logout.addClickListener(click -> {
            logout.getUI().ifPresent(ui -> ui.navigate("login"));
            logout.getUI().ifPresent(ui -> ui.getSession().close());
        });
        logout.setWidth("10%");
        addToNavbar(header, logout);
    }

}
