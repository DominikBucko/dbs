package com.icloud.dominik.UI;

import backend.entity.Department;
import backend.entity.User;
import backend.service.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.nio.file.FileAlreadyExistsException;

@Route(value = "users", layout = HomeLayout.class)
@PageTitle("Users | SAM")
public class UserList extends VerticalLayout {
    Grid<User> userGrid = new Grid<>(User.class);
    UserService userService = new UserService();
    Dialog newUserDialog = new Dialog();
    Div dialogContent = new Div();
    Button newUser = new Button("Create user");
    Notification userAdded = new Notification();

    public UserList() {
        addClassName("user-list");
        setSizeFull();
        setupGrid();
        updateGrid();

        dialogContent.addClassName("dialog-content");
        setupUserDialog();
        newUser.addClickListener(click -> newUserDialog.open());
        add(newUser, newUserDialog, userGrid, userAdded);
    }

    private void setupUserDialog() {
        UserForm userForm = new UserForm();
        dialogContent.add(userForm);
        userForm.cancel.addClickListener(click -> newUserDialog.close());
        newUserDialog.add(dialogContent);

    }

    private void updateGrid() {
        userGrid.setItems(userService.getAll());
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

    }
}
