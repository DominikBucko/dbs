package com.icloud.dominik.UI;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class HomeLayout extends AppLayout {
    public HomeLayout() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink assetsTab = new RouterLink("Assets", Assets.class);
        RouterLink usersTab = new RouterLink("Users", Users.class);
        RouterLink test = new RouterLink("Test", Test.class);
        assetsTab.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(
                assetsTab,
                usersTab,
                test
        ));


    }

    private void createHeader() {
        H2 title = new H2("SAM - Smart Asset Manager");
        title.addClassName("title");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);
    }


}
