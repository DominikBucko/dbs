package com.icloud.dominik.UI.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

@Route("login")
@PageTitle("Login | SAM")

public class LoginPage extends VerticalLayout implements BeforeEnterObserver {

	private LoginForm login = new LoginForm();
	private Dialog totp_dialog = new Dialog();
	private int tfa = 2;


	@Autowired
	public LoginPage(){
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		login.setAction("login");
		setupTotpDialog();
		add(new H1("SAM - Smart Asset Manager"), login, totp_dialog);

	}

	private void setupTotpDialog() {
		VerticalLayout totpDialogLayout = new VerticalLayout();
		TextField totp = new TextField("Enter one-time password");
    	Button send = new Button("Verify");
    	send.addClickListener(click -> verifyTotp(totp.getValue()));
    	totpDialogLayout.setAlignItems(Alignment.CENTER);
    	totpDialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);
    	totpDialogLayout.setSizeFull();
    	totpDialogLayout.add(totp, send);
	}

	private void verifyTotp(String totp) {
		totp_dialog.close();
		if (totp.equals("123456")) {
			tfa = 1;
		}
		else tfa = 0;
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {

		if(!event.getLocation()
			.getQueryParameters()
			.getParameters()
			.getOrDefault("error", Collections.emptyList())
			.isEmpty()) {
			login.setError(true);
		}



	}
}