package com.icloud.dominik.UI;

import backend.transactions.AssetsManager;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "test")
public class Test extends VerticalLayout {
    Button btn = new Button("test");
    public Test() {
        btn.addClickListener(click -> {
            AssetsManager assetsManager = new AssetsManager();
            assetsManager.get_assets();
        });
        UserForm form = new UserForm();
        form.setSizeFull();
        add(btn, form);
    }
}
