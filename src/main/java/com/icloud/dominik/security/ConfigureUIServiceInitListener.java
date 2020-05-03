package com.icloud.dominik.security;

import com.icloud.dominik.UI.Dashboard;
import com.icloud.dominik.UI.LoginPage;
import com.icloud.dominik.UI.Test;
import com.icloud.dominik.UI.Users;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component //
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener { //

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
        final UI ui = uiEvent.getUI();
        ui.addBeforeEnterListener(this::beforeEnter); //
        });
    }

    private void beforeEnter(BeforeEnterEvent event) {
        if(!SecurityUtils.isAccessGranted(event.getNavigationTarget())) { //
            if(SecurityUtils.isUserLoggedIn()) { //
                event.rerouteToError(NotFoundException.class); //
            } else {
                event.rerouteTo(LoginPage.class); //
            }
        }
        else {
            rerouteOnRoles(event);
        }

    }
    private void rerouteOnRoles(BeforeEnterEvent event) {
        if (event.getNavigationTarget() == Dashboard.class) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());

            if (!roles.contains("ROLE_Admin")) {
                event.rerouteTo(Test.class);}
        }
    }
}
