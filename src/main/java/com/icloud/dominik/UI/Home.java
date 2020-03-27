package com.icloud.dominik.UI;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("home")
public class Home extends VerticalLayout {
    public Home() {
        Text welcome = new Text("WELCOME");
        VerticalLayout layout = new VerticalLayout(welcome);
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(layout);
    }
}
