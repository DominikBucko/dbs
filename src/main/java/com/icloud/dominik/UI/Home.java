package com.icloud.dominik.UI;

import backend.transactions.AssetsManager;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class Home extends VerticalLayout {
    public Home() {
        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        Button btn = new Button("EXEC");
        AssetsManager assetsManager = new AssetsManager();
        btn.addClickListener(click -> assetsManager.get_assets());
        add(layout, btn);
	}
}
