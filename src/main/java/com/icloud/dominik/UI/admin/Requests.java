package com.icloud.dominik.UI.admin;

import backend.dataHandling.TicketHandler;
import backend.dataHandling.UserAssetHandler;
import backend.entity.Asset;
import backend.entity.Ticket;
import com.icloud.dominik.UI.user.AssetsToRequest;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "requests", layout = HomeLayout.class)
@Secured("ROLE_Admin")
@PageTitle("Requests | SAM")

public class Requests extends VerticalLayout {
    Grid<Ticket> ticketGrid = new Grid<>(Ticket.class);
    Button availableAssets = new Button("Request an asset");
    TicketHandler ticketHandler = new TicketHandler();

    private Requests() {
        availableAssets.setSizeFull();
        availableAssets.addClickListener(click -> availableAssets.getUI().ifPresent(ui -> ui.navigate(AssetsToRequest.class)));
        setupGrid();
        add(availableAssets, ticketGrid);
    }

    private void setupGrid() {
        ticketGrid.setItems(ticketHandler.getUnapprovedTickets());
//        ticketGrid.setSizeFull();
        ticketGrid.addColumn(ticket -> {
            Asset asset = ticket.getAsset();
            return asset == null ? "none" : asset.getName() + asset.getType();
        }).setHeader("Asset");
        ticketGrid.setColumns("invoice_id", "time_created", "comment");
    }
}