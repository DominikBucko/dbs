package com.icloud.dominik.security;

import com.icloud.dominik.UI.LoginPage;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::authenticateNavigation);
		});
	}

	private void authenticateNavigation(BeforeEnterEvent event) {
		if (!LoginPage.class.equals(event.getNavigationTarget())
		    && !SecurityUtils.isUserLoggedIn()) {
			event.rerouteTo(LoginPage.class);
		}
	}

	private void beforeEnter(BeforeEnterEvent event) {
    if(!SecurityUtils.isAccessGranted(event.getNavigationTarget())) {
        if(SecurityUtils.isUserLoggedIn()) {
            event.rerouteToError(NotFoundException.class);
        } else {
            event.rerouteTo(LoginPage.class);
        }
    }
}

}