package com.icloud.dominik.UI.user;

import backend.dataHandling.UserAssetHandler;
import backend.entity.Asset;
import backend.entity.Department;
import backend.entity.Ticket;
import backend.service.AssetService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;


@Route(value = "mytickets", layout = UserLayout.class)
@Secured("ROLE_User")
@PageTitle("My Assets | SAM")

public class UserTickets extends VerticalLayout {
    Grid<Ticket> ticketGrid = new Grid<>(Ticket.class);
    Button availableAssets = new Button("Request an asset");
    UserAssetHandler userAssetHandler = new UserAssetHandler(
            SecurityContextHolder.getContext().getAuthentication().getName()
    );

    private UserTickets() {
        availableAssets.setSizeFull();
        availableAssets.addClickListener(click -> availableAssets.getUI().ifPresent(ui -> ui.navigate(AssetsToRequest.class)));
        setupGrid();
        add(availableAssets, ticketGrid);
    }

    private void setupGrid() {
        ticketGrid.setItems(userAssetHandler.getUserTickets());
        ticketGrid.setSizeFull();
        ticketGrid.addColumn(ticket -> {
            Asset asset = ticket.getAsset();
            return asset == null ? "none" : asset.getName() + asset.getType();
        }).setHeader("Asset");
        ticketGrid.setColumns("invoice_id", "time_created", "time_returned", "comment");
    }
}
