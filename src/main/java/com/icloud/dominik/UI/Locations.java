package com.icloud.dominik.UI;

import backend.entity.Department;
import backend.entity.Location;
import backend.service.LocationService;
import com.icloud.dominik.UI.components.LocationForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Dial;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "locations", layout = HomeLayout.class)
@PageTitle("Locations | SAM")

public class Locations extends Div {
    Grid<Location> locationsGrid = new Grid<>(Location.class);
    LocationService locationService = new LocationService();
    Button newLocation = new Button("New location");
    Dialog dialog = new Dialog();
    LocationForm locationForm = new LocationForm();
    Div dialogContent = new Div();


    public Locations() {
        addClassName("locations-list");
        setupGrid();
        updateGrid();
        setupDialog();
        newLocation.addClickListener(click -> createLocation());
        add(newLocation, locationsGrid);
    }

    private void setupDialog() {
        dialogContent.add(locationForm);
        locationForm.getCancel().addClickListener(click -> dialog.close());
        dialog.add(dialogContent);
    }

    public void updateGrid() {
        locationsGrid.setItems(locationService.getAll());
    }

    private void setupGrid() {
        locationsGrid.addClassName("locations-Grid");
//        locationsGrid.setSizeFull(); //BUG
//        locationsGrid.setWidth("90%");
//        locationsGrid.setHeight("50%");
        locationsGrid.recalculateColumnWidths();
        locationsGrid.setColumns("state", "address", "postcode");
        locationsGrid.asSingleSelect().addValueChangeListener(evt -> editLocation(evt.getValue()));
    }

    private void createLocation() {
        locationForm.setCreateMode();
        dialog.open();
    }

    private void editLocation(Location value) {
        locationForm.setLocation(value);
        locationForm.setUpdateMode();
        dialog.open();
    }

}
