package com.icloud.dominik.UI.user;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class UserLayout extends AppLayout {
    public UserLayout() {
        createHeader();
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
