package com.icloud.dominik.UI.admin;

import backend.entity.Ticket;
import backend.entity.User;
import backend.service.TicketService;
import com.icloud.dominik.UI.admin.HomeLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
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
    Dialog approveDialog;


    public TicketApprovals() {
        setupGrid();
        setupFilters();
        add(filter, ticketGrid);
    }

    private void setupDialog() {
        VerticalLayout dialogLayout = new VerticalLayout();
        H3 prompt = new H3("Do you want to approve this request?");
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button yes = new Button("Yes");
        yes.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        yes.addClickListener(click -> approveTicket());
        Button no = new Button("No");
        no.addThemeVariants(ButtonVariant.LUMO_ERROR);
        no.addClickListener(click -> approveDialog.close());
        no.setSizeFull();
        yes.setSizeFull();
        buttonLayout.add(no, yes);
        dialogLayout.add(prompt, buttonLayout);
        approveDialog.add(dialogLayout);
    }

    private void approveTicket() {
        ticketService.saveTicket(currentTicket);
        approveDialog.close();
    }

    private void applyFilter() {
        ticketGrid.getDataCommunicator().getKeyMapper().removeAll();
        ticketGrid.setDataProvider(dataProvider);
    }

    private void setupFilters() {
        filter.setPlaceholder("Filter by name..");
        filter.setMaxWidth("50%");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> applyFilter());
    }

    private void setupGrid() {
        dataProvider = DataProvider.fromCallbacks(
                query -> ticketService.getUnapprovedTickets(filter.getValue(), query.getLimit(), query.getOffset()).stream(),
                query -> ticketService.getUnapprovedTickets(filter.getValue(), query.getLimit(), query.getOffset()).size()
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
        approveDialog.open();
    }

}

