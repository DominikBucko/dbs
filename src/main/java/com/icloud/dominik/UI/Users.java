package com.icloud.dominik.UI;

import backend.entity.Asset;
import backend.entity.Department;
import backend.entity.User;
import backend.service.UserService;
import com.icloud.dominik.UI.components.UserForm;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;


@Route(value = "users", layout = HomeLayout.class)
@PageTitle("Users | SAM")
public class Users extends VerticalLayout {
    Grid<User> userGrid = new Grid<>(User.class);
    UserService userService = new UserService();
    Dialog newUserDialog = new Dialog();
    Div dialogContent = new Div();
    Button newUser = new Button("Create user");
    Notification userAdded = new Notification();
    ComboBox<String> propertySelect = new ComboBox<>();
    UserForm userForm;
    TextField filter = new TextField();
    HorizontalLayout topRow = new HorizontalLayout();
    VerticalLayout middleRow = new VerticalLayout();
    Text itemCount = new Text("Number of items listed: ");
    CallbackDataProvider<User, Void> provider;

    public Users() {
        addClassName("user-list");
        setSizeFull();
        setupGrid();
        updateGrid();
        topRow.add(newUser, filter, propertySelect);
        setupUserDialog();
        newUser.addClickListener(click -> addNewUser());
        newUser.setWidthFull();
        filterConfig();

        add(topRow, middleRow, itemCount, newUserDialog, userGrid, userAdded);
    }

    private void filterConfig() {
        filter.addFocusListener(focus -> setPropertyOnFocus());
        filter.setPlaceholder("Filter by Surname..");
        filter.setSizeFull();
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> applyFilter());

        propertySelect.setPlaceholder("Filter by:");
        propertySelect.setItemLabelGenerator(this::beautifyPropName);
        propertySelect.setItems("first_name", "surname", "address", "login");
        propertySelect.addValueChangeListener(evt -> updateFilterPrompt());
    }

    private void setPropertyOnFocus() {
        if (propertySelect.getValue() == (propertySelect.getEmptyValue())) {
            propertySelect.setValue("surname");
        }
    }

    private void updateFilterPrompt() {
        filter.setPlaceholder("Filter by " + beautifyPropName(propertySelect.getValue()) +"...");
    }

    private void applyFilter() {
        if (propertySelect.getValue().equals(propertySelect.getEmptyValue())) {
            propertySelect.setValue("surname");
        }
        String property = propertySelect.getValue();
        String toMatch = filter.getValue();
        updateGrid(property, toMatch);
    }

    private String beautifyPropName(String name) {
        StringBuilder myName = new StringBuilder(name);
        try {
            int dash = name.indexOf('_');
            myName.setCharAt(dash, ' ');
        } catch (Exception e) {
        }
        char first = Character.toUpperCase(name.charAt(0));
        myName.setCharAt(0, first);
        return myName.toString();
    }

    private void setupUserDialog() {
        dialogContent.addClassName("dialog-content");
        userForm = new UserForm();
        dialogContent.add(userForm);
        userForm.getCancel().addClickListener(click -> refreshAfterDialogClose());
        newUserDialog.add(dialogContent);
    }

    private void refreshAfterDialogClose() {
        updateGrid();
        newUserDialog.close();
    }


    private void updateGrid() {
        userGrid.getDataProvider().refreshAll();
        itemCount.setText("Number of items listed: " + userService.countAll());
    }

    private void updateGrid(String property, String search_phrase) {
        if (filter.isEmpty()) {
            userGrid.setDataProvider(provider);
            userGrid.scrollToStart();
            return;
        }
        List <User> users = userService.filterBy(property, search_phrase);
        userGrid.setItems(users);
        userGrid.setItems(users);
        itemCount.setText("Number of items listed: " + users.size());
    }

    private void addNewUser() {
        userForm.mapSaveBtnToCreate();
        newUserDialog.open();
    }
    private void editUser(User user) {
        userForm.setUser(user);
        userForm.mapSaveBtnToUpdate();
        newUserDialog.open();
    }

    private void setupGrid() {
        provider = DataProvider.fromCallbacks(
                query -> userService.getAll(query.getOffset(), query.getLimit()).stream(),
                query -> userService.countAll()
        );
        userGrid.setDataProvider(provider);
        userGrid.addClassName("asset-userGrid");
        userGrid.setSizeFull();
        userGrid.recalculateColumnWidths();
        userGrid.removeColumnByKey("user_department");
        userGrid.setColumns("first_name", "surname", "address", "login", "ticketCount");
        userGrid.addColumn(user -> {
            Department department = user.getDepartment();
            return department == null ? "none" : department.getDepartment_name();
        }).setHeader("Department");
        userGrid.asSingleSelect().addValueChangeListener(evt -> editUser(evt.getValue()));
        userGrid.getColumns().forEach(col -> col.setAutoWidth(true));

    }
}
