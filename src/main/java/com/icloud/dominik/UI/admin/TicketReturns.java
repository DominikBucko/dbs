package com.icloud.dominik.UI.admin;

import backend.entity.Asset;
import backend.entity.Department;
import backend.entity.Ticket;
import backend.entity.User;
import backend.service.TicketService;
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

import java.sql.Date;


@Route(value = "tickets_returns", layout = HomeLayout.class)
@Secured("ROLE_Admin")
@PageTitle("Returns | SAM")
public class TicketReturns extends VerticalLayout {
     Grid<Ticket> ticketGrid = new Grid<>(Ticket.class);
    CallbackDataProvider<Ticket, Void> dataProvider;
    TextField filter = new TextField();
    TicketService ticketService = new TicketService();
    Ticket currentTicket;
    Dialog approveDialog;


    public TicketReturns() {
        setupGrid();
        setupFilters();
        setupDialog();
        add(filter, ticketGrid);
    }

    private void setupDialog() {
        approveDialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();
        H3 prompt = new H3("Do you want to approve return of this request?");
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
        buttonLayout.setAlignItems(Alignment.CENTER);
        dialogLayout.add(prompt, buttonLayout);
        approveDialog.add(dialogLayout);
    }

    private void approveTicket() {
        currentTicket.setTime_returned(new Date(new java.util.Date().getTime()));
        ticketService.updateTicket(currentTicket);
        applyFilter();
        approveDialog.close();
    }

    private void applyFilter() {
        ticketGrid.getDataCommunicator().getKeyMapper().removeAll();
        ticketGrid.setDataProvider(dataProvider);
    }

    private void setupFilters() {
        filter.setPlaceholder("Filter by name..");
//        filter.setMaxWidth("100%");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> applyFilter());
    }

    private void setupGrid() {
        dataProvider = DataProvider.fromCallbacks(
                query -> ticketService.getUnapprovedTickets("return", filter.getValue(), query.getLimit(), query.getOffset()).stream(),
                query -> ticketService.getUnapprovedTickets("return", filter.getValue(), query.getLimit(), query.getOffset()).size()
        );
        ticketGrid.setDataProvider(dataProvider);
        ticketGrid.setColumns("invoice_id", "time_created");
        ticketGrid.addColumn(ticket -> {
            User user = ticket.getUser();
            return user.getFirst_name() + " " + user.getSurname();
        }).setHeader("Applicant");
        ticketGrid.addColumn(ticket -> {
            Department department = ticket.getUser().getDepartment();
            return department.getDepartment_name();
        }).setHeader("Department");
        ticketGrid.addColumn(ticket -> {
            Asset asset = ticket.getAsset();
            return asset.getName() + " " + asset.getType();
        }).setHeader("Asset");
        ticketGrid.asSingleSelect().addValueChangeListener(evt -> openApprovalForTicket(evt.getValue()));
        ticketGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void openApprovalForTicket(Ticket value) {
        currentTicket = value;
        approveDialog.open();
    }
}
