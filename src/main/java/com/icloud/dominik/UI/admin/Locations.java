package com.icloud.dominik.UI.admin;

import backend.entity.Location;
import backend.service.LocationService;
import com.icloud.dominik.UI.components.LocationForm;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.security.access.annotation.Secured;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Date;
import java.util.Iterator;
import org.hibernate.cfg.Configuration;

@Route(value = "locations", layout = HomeLayout.class)
@Secured("ROLE_Admin")
@PageTitle("Locations | SAM")


public class Locations extends VerticalLayout {
    //    HIBERNATE TEST
//    private static SessionFactory factory;
//
//    public void createFactory(){
//        try {
//            factory = new Configuration().configure().buildSessionFactory();
//        } catch (Throwable ex) {
//        System.err.println("Failed to create sessionFactory object." + ex);
//        throw new ExceptionInInitializerError(ex);
//    }
//}
//END

    Grid<Location> locationsGrid = new Grid<>(Location.class);
    LocationService locationService = new LocationService();
    Button newLocation = new Button("New location");
    Dialog dialog = new Dialog();
    LocationForm locationForm = new LocationForm();
    Div dialogContent = new Div();
    Text itemCount = new Text("Number of items listed: ");


    public Locations() {
        addClassName("locations-list");
        setupGrid();
        updateGrid();
        setupDialog();
        newLocation.addClickListener(click -> createLocation());
        Anchor download = new Anchor(new StreamResource("locations.csv", () -> createResource()), "");
        download.getElement().setAttribute("download", true);
        download.add(new Button("Download .csv", new Icon(VaadinIcon.DOWNLOAD_ALT)));
        add(new HorizontalLayout(newLocation, download), itemCount, locationsGrid);
//        createFactory();
//        listLocations();
    }

    private InputStream createResource() {
        String filename = locationService.exportToCsv();
        try {
            File fileToDl = new File(filename);
            return new FileInputStream(fileToDl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setupDialog() {
        dialogContent.add(locationForm);
        locationForm.getCancel().addClickListener(click -> closeAndUpdate());
        dialog.add(dialogContent);
    }

    private void closeAndUpdate() {
        updateGrid();
        dialog.close();
    }

    public void updateGrid() {
//        List<Location> locations = locationService.getAll();
//        locationsGrid.setItems(locations);
        locationsGrid.getDataProvider().refreshAll();
        itemCount.setText("Number of items listed: " + locationService.countAll());
    }

    private void setupGrid() {
        CallbackDataProvider<Location, Void> provider = DataProvider.fromCallbacks(
//                query -> locationService.getAllHib().stream(),
                query -> locationService.getAll(query.getOffset(), query.getLimit()).stream(),
                query -> locationService.countAll()
        );
        locationsGrid.setDataProvider(provider);
        locationsGrid.addClassName("locations-Grid");
//        locationsGrid.setSizeFull(); //BUG
//        locationsGrid.setWidth("90%");
//        locationsGrid.setHeight("50%");
        locationsGrid.recalculateColumnWidths();
        locationsGrid.setColumns("state", "address", "postcode");
        locationsGrid.asSingleSelect().addValueChangeListener(evt -> editLocation(evt.getValue()));
        locationsGrid.getColumns().forEach(col -> col.setAutoWidth(true));
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

//    public void listLocations(){
//        Session session = factory.openSession();
//        Transaction tx = null;
//
//        try {
//            tx = session.beginTransaction();
//
//            List <Location> locations = session.createQuery("FROM backend.entity.Location").list();
//
////            List locations = session.createQuery("FROM backend.entity.Location").list();
//
//////            for (Iterator iterator = locations.iterator(); iterator.hasNext();){
////            Location location = (Location) iterator.next();
////            System.out.print("state: " + location.getState());
////            System.out.print("address: " + location.getAddress());
////            System.out.println("postcode " + location.getPostcode());
//////            }
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx!=null) tx.rollback();
//            e.printStackTrace();
//        }
//
//    }

}
