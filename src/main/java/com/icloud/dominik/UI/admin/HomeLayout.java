package com.icloud.dominik.UI.admin;

import backend.service.LogService;
import backend.service.UserService;
import com.icloud.dominik.UI.Test;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.context.SecurityContextHolder;

public class HomeLayout extends AppLayout {
    public HomeLayout() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink assetsTab = new RouterLink("Assets", Assets.class);
        RouterLink usersTab = new RouterLink("Users", Users.class);
        RouterLink locationsTab = new RouterLink("Locations", Locations.class);
        RouterLink test = new RouterLink("Test", Test.class);
        RouterLink dashboard = new RouterLink("Dashboard", Dashboard.class);
        RouterLink tickets_approval = new RouterLink("Approve Tickets", TicketApprovals.class);
        RouterLink tickets_returns = new RouterLink("Approve Returns", TicketReturns.class);
        RouterLink faults = new RouterLink("Faults", Faults.class);
        assetsTab.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(
                assetsTab,
                usersTab,
                locationsTab,
                dashboard,
                tickets_approval,
                tickets_returns,
                faults
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
            LogService.log(new UserService().getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUser_id(), "Logout");
            logout.getUI().ifPresent(ui -> ui.navigate("login"));
            logout.getUI().ifPresent(ui -> ui.getSession().close());
        });
        logout.setWidth("10%");
        addToNavbar(header, logout);
    }


}
