package com.icloud.dominik.UI.admin;

import backend.csvexport.CSVExport;
import backend.entity.AssetFault;
import backend.entity.Fault;
import backend.service.AssetFaultService;
import backend.service.AssetService;
import backend.service.LogService;
import backend.service.UserService;
import com.icloud.dominik.UI.components.FaultForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
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
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

@Route(value = "faults", layout = HomeLayout.class)
@Secured("ROLE_Admin")
@PageTitle("Faults | SAM")

public class Faults extends VerticalLayout {
    Grid<AssetFault> grid = new Grid<>(AssetFault.class);
    AssetService assetService = new AssetService();
    AssetFaultService assetFaultService = new AssetFaultService();
    CallbackDataProvider<AssetFault, Void> provider;
    Button registerNew = new Button("Register new fault");
    FaultForm faultForm = new FaultForm();
    Dialog newFault = new Dialog();
    Dialog markAsRepaired = new Dialog();
    AssetFault fault;

    public Faults() {
        setupDialog();
        setupGrid();
        setupRepairedDialog();
        registerNew.addClickListener(click -> newFault.open());
        Anchor download = new Anchor(new StreamResource("faults.csv", () -> CSVExport.createResource("asset_fault")), "");
        download.getElement().setAttribute("download", true);
        download.add(new Button("Download .csv", new Icon(VaadinIcon.DOWNLOAD_ALT)));
        add(new HorizontalLayout(registerNew, download), grid, newFault, markAsRepaired);
    }


    private void setupGrid() {
        provider = DataProvider.fromCallbacks(
                query -> assetFaultService.getAll(query.getOffset(), query.getLimit()).stream(),
                query -> assetFaultService.getAll(query.getOffset(), query.getLimit()).size()
        );
        grid.setDataProvider(provider);
        grid.removeAllColumns();
        grid.addColumn(AssetFault::getAsset_failt_id).setHeader("Asset Fault ID");
        grid.addColumn(fault -> fault.getAsset().getAsset_id()).setHeader("Asset ID");
        grid.addColumn(fault -> fault.getAsset().getName()).setHeader("Name");
        grid.addColumn(fault -> fault.getAsset().getType()).setHeader("Type");
        grid.addColumn(fault -> fault.getAsset().getDepartment().getDepartment_name()).setHeader("Department");
        grid.addColumn(AssetFault::getFixable).setHeader("Fixable");
        
        grid.asSingleSelect().addValueChangeListener(evt -> editFault(evt.getValue()));

    }

    private void editFault(AssetFault value) {
        fault = value;
        markAsRepaired.open();
    }

    private void setupRepairedDialog() {
        VerticalLayout layout = new VerticalLayout();
        H3 prompt = new H3("Do you want to mark this asset as repaired?");
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button yes = new Button("Yes");
        Button no = new Button("No");
        yes.setSizeFull();
        yes.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        yes.addClickListener(click -> resolveRepaired());
        no.setSizeFull();
        no.addThemeVariants(ButtonVariant.LUMO_ERROR);
        no.addClickListener(click -> markAsRepaired.close());
        buttonLayout.add(yes, no);
        layout.add(prompt, buttonLayout);
        markAsRepaired.add(layout);
    }

    private void resolveRepaired() {
        try {
            assetFaultService.dropFromService(fault.getAsset().getAsset_id());
        }catch(SQLException e){
            e.printStackTrace();
        }
        LogService.log(new UserService().getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUser_id(), "Dropped asset " + fault.getAsset().getAsset_id() + " from service");

        markAsRepaired.close();
        refreshGrid();
        markAsRepaired.close();
    }

    private void refreshGrid() {
        grid.removeAllColumns();
        grid.setDataProvider(provider);
    }

    private void setupDialog() {
        newFault.add(faultForm);
        faultForm.getCancel().addClickListener(click -> closeAndRefresh());
    }

    private void closeAndRefresh() {
        newFault.close();
        grid.setDataProvider(provider);
//        refreshGrid();
    }
}
