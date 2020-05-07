package com.icloud.dominik.UI.admin;

import backend.entity.Ticket;
import backend.entity.User;
import backend.service.TicketService;
import com.icloud.dominik.UI.admin.HomeLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

import javax.xml.crypto.Data;

@Route(value = "tickets_approval", layout = HomeLayout.class)
@Secured("ROLE_Admin")
@PageTitle("Tickets | SAM")
public class TicketApprovals extends VerticalLayout {
    Grid<Ticket> ticketGrid = new Grid<>(Ticket.class);
    CallbackDataProvider<Ticket, Void> dataProvider;
    TextField filter = new TextField();
    TicketService ticketService = new TicketService();
    Ticket currentTicket;


    public TicketApprovals() {
        setupGrid();
        setupFilters();
    }

    private void setupFilters() {

    }

    private void setupGrid() {
        dataProvider = DataProvider.fromCallbacks(
                query -> ticketService.getUnapprovedTickets(query.getLimit(), query.getOffset()).stream(),
                query -> ticketService.getUnapprovedTickets(query.getLimit(), query.getOffset()).size()
        );
        ticketGrid.setDataProvider(dataProvider);
        ticketGrid.addColumn(ticket -> {
            User user = ticket.getUser();
            return user.getFirst_name() + " " + user.getSurname();
        }).setHeader("Applicant");
        ticketGrid.setColumns("invoice_id", "time_created");
        ticketGrid.asSingleSelect().addValueChangeListener(evt -> openApprovalForTicket(evt.getValue()));
    }

    private void openApprovalForTicket(Ticket value) {
        currentTicket = value;

    }

}

