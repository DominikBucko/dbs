package com.icloud.dominik.UI;

import backend.authentication.Authenticator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.TextAlign;
import com.vaadin.flow.component.charts.model.Title;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
@Route("")
public class LoginPage extends VerticalLayout {
    public LoginPage() {
        Text title = new Text("Smart Asset Management - SAM");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        Button button = new Button("Login");
        VerticalLayout layout = new VerticalLayout(title, username, password, button);
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(layout);

        button.addClickListener(click -> logIn(username.getValue(), password.getValue(), button, layout));
    }

    private void logIn(String username, String password, Button button, VerticalLayout layout) {
        Authenticator auth = new Authenticator(username, password);
        if (auth.authenticate()){
            button.getUI().ifPresent(ui -> ui.navigate("home"));
        }
        else {
            Notification notification = new Notification("Wrong credentials", 3000);
            notification.open();
        }
    }

}
