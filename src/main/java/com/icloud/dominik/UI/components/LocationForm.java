package com.icloud.dominik.UI.components;

import backend.entity.Location;
import backend.entity.User;
import backend.service.LocationService;
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
        delete.addClickListener(click -> clearFields());

        add(state, address, postcode, buttonRow());
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
