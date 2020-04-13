package com.icloud.dominik.UI;

import backend.entity.Department;
import backend.entity.Location;
import backend.service.LocationService;
import com.icloud.dominik.UI.components.LocationForm;
import com.vaadin.flow.component.charts.model.Dial;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.*;

@Route(value = "locations", layout = HomeLayout.class)
@PageTitle("Locations | SAM")

public class Locations extends VerticalLayout {
    Grid<Location> locationsGrid = new Grid<>(Location.class);
    LocationService locationService = new LocationService();
    Button newLocation = new Button("New location");
    Dialog dialog = new Dialog();
    LocationForm locationForm = new LocationForm();

    public Locations() {

    }
    private void setupGrid() {
        locationsGrid.addClassName("locations-Grid");
        locationsGrid.setSizeFull();
        locationsGrid.setColumns("state", "address", "postcode");
        locationsGrid.asSingleSelect().addValueChangeListener(evt -> editLocation(evt.getValue()));

    }

    private void editLocation(Location value) {
        locationForm.setLocation(value);
    }

}
