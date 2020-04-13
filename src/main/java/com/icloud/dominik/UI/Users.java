package com.icloud.dominik.UI;

import backend.entity.Department;
import backend.entity.User;
import backend.service.UserService;
import com.icloud.dominik.UI.components.UserForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "users", layout = HomeLayout.class)
@PageTitle("Users | SAM")
public class Users extends VerticalLayout {
    Grid<User> userGrid = new Grid<>(User.class);
    UserService userService = new UserService();
    Dialog newUserDialog = new Dialog();
    Div dialogContent = new Div();
    Button newUser = new Button("Create user");
    Notification userAdded = new Notification();
    UserForm userForm;
    TextField filter = new TextField();

    public Users() {
        addClassName("user-list");
        setSizeFull();
        setupGrid();
        updateGrid();

        dialogContent.addClassName("dialog-content");
        setupUserDialog();
        newUser.addClickListener(click -> newUserDialog.open());

        add(newUser, newUserDialog, userGrid, userAdded);
    }

    private void filterConfig() {
        filter.setPlaceholder("Filter by surname..");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateGrid());
    }

    private void setupUserDialog() {
        userForm = new UserForm();
        dialogContent.add(userForm);
        userForm.cancel.addClickListener(click -> newUserDialog.close());
        newUserDialog.add(dialogContent);
    }


    private void updateGrid() {
        userGrid.setItems(userService.getAll());
    }

    private void updateGrid(String search_phrase) {
        userGrid.setItems(userService.filterBy("surname", search_phrase));
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
        userGrid.addClassName("asset-userGrid");
        userGrid.setSizeFull();
        userGrid.removeColumnByKey("user_department");
        userGrid.setColumns("first_name", "surname", "address", "login");
        userGrid.addColumn(user -> {
            Department department = user.getDepartment();
            return department == null ? "none" : department.getDepartment_name();
        }).setHeader("Department");
        userGrid.asSingleSelect().addValueChangeListener(evt -> editUser(evt.getValue()));

    }
}
