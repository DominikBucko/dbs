package com.icloud.dominik.UI.components;

import backend.entity.Location;
import backend.entity.User;
import backend.service.LocationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.ArrayList;
import java.util.List;


public class LocationForm extends CustomForm {
    TextField state = new TextField("State");
    TextField address = new TextField("Address");
    IntegerField postcode = new IntegerField("Postcode");
    Binder<Location> binder = new Binder<>(Location.class);
    LocationService locationService = new LocationService();

    public LocationForm() {
        binder.bindInstanceFields(this);
        addClassName("location-form");
        setupNotification("Location saved successfully.");
        save.addClickListener(click -> createNewLocation());
        clear.addClickListener(click -> clearFields());
        delete.addClickListener(clic -> deleteLocation());
        update.addClickListener(click -> updateNewLocation());

        add(state, address, postcode, buttonLayout);
    }

    private void deleteLocation() {
        if (!locationService.delete(binder.getBean())) {
            Notification notification = new Notification(
                    "Deletion not possible! Please remove all references to this item before deleting it");
            notification.setDuration(10000);
//            Button btn = new Button("Close");
//            btn.addClickListener(click -> notification.close());
//            notification.add(btn);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
        cancel.click();
    }

    private void clearFields() {
        state.clear();
        address.clear();
        postcode.clear();
    }

    public void setLocation(Location location) {
        binder.setBean(location);
    }

    private void updateNewLocation() {
        locationService.update(binder.getBean());
        cancel.click();
    }

    private void createNewLocation() {
        List<Location> locations = new ArrayList<>();
        Location location = binder.getBean();
        if (locationService.createNew(locations)) {
            cancel.click();
            setupNotification("Location saved successfully.");
            notification.open();
        } else {
            setupNotification("ERROR");
            notification.open();
        }
    }




}
