package com.icloud.dominik.UI.user;

import backend.dataHandling.UserAssetHandler;
import backend.entity.Asset;
import backend.entity.Department;
import backend.entity.Ticket;
import backend.service.AssetService;
import backend.service.LogService;
import backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
//import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.util.List;


@Route(value = "mytickets", layout = UserLayout.class)
@Secured("ROLE_User")
@PageTitle("My Assets | SAM")

public class UserTickets extends VerticalLayout {
    Grid<Ticket> ticketGrid = new Grid<>(Ticket.class);
    Button availableAssets = new Button("Request an asset");
    UserAssetHandler userAssetHandler = new UserAssetHandler(
            SecurityContextHolder.getContext().getAuthentication().getName()
    );
    Ticket currentTicket;
    Dialog returnAssetDialog = new Dialog();

    private UserTickets() {
//        availableAssets.setSizeFull();
        availableAssets.addClickListener(click -> availableAssets.getUI().ifPresent(ui -> ui.navigate(AssetsToRequest.class)));
        setupGrid();
        setupDialog();
        add(availableAssets, ticketGrid);
    }

    private void setupDialog() {
        VerticalLayout dialogLayout = new VerticalLayout();
        H3 prompt = new H3("Do you want to return this asset?");
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button yes = new Button("Yes");
        yes.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        yes.addClickListener(click -> returnAsset());
        Button no = new Button("No");
        no.addThemeVariants(ButtonVariant.LUMO_ERROR);
        no.addClickListener(click -> returnAssetDialog.close());
        buttonLayout.add(no, yes);
        dialogLayout.add(prompt, buttonLayout);
        returnAssetDialog.add(dialogLayout);
    }

    private void returnAsset() {
        currentTicket.setTime_assigned(new Date(new java.util.Date().getTime()));
        userAssetHandler.askForReturn(currentTicket);
        returnAssetDialog.close();
        fillGrid();
        LogService.log(new UserService().getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUser_id(), "Requested return of " + currentTicket.getInvoice_id());


    }

    private void fillGrid() {
        List<Ticket> items = userAssetHandler.getUserTickets();
        ticketGrid.setItems(items);
    }
    private void setupGrid() {
        fillGrid();
//        ticketGrid.setSizeFull();
        ticketGrid.removeAllColumns();
        ticketGrid.addColumn(ticket -> {
            Asset asset = ticket.getAsset();
            return asset == null ? "none" : asset.getName() + " " + asset.getType();
        }).setHeader("Asset");
        ticketGrid.addColumn(Ticket::getInvoice_id).setHeader("Ticket ID");
        ticketGrid.addColumn(Ticket::getTime_created).setHeader("Requested");
        ticketGrid.addColumn(Ticket::getTime_accepted).setHeader("Approved");
        ticketGrid.addColumn(Ticket::getTime_returned).setHeader("Returned");
        ticketGrid.asSingleSelect().addValueChangeListener(
                e -> askForReturn(e.getValue())
        );
    }

    private void askForReturn(Ticket value) {
        currentTicket = value;
        if (value.getTime_accepted() != null && value.getTime_returned() == null) {
            returnAssetDialog.open();
        }
    }
}
