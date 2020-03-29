package com.icloud.dominik.UI;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@Route("login")
@PageTitle("Login | SAM")

public class LoginPage extends VerticalLayout implements BeforeEnterObserver {

	private LoginForm login = new LoginForm();

	public LoginPage(){
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		login.setAction("login");

		add(new H1("SAM - Smart Asset Manager"), login);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// inform the user about an authentication error
		if(!event.getLocation()
			.getQueryParameters()
			.getParameters()
			.getOrDefault("error", Collections.emptyList())
			.isEmpty()) {
			login.setError(true);
		}
	}
}