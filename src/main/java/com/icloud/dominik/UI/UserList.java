package com.icloud.dominik.UI;

import backend.entity.Department;
import backend.entity.User;
import backend.service.UserService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "users", layout = HomeLayout.class)
@PageTitle("Users | SAM")
public class UserList extends VerticalLayout {
    Grid<User> userGrid = new Grid<>(User.class);
    UserService userService = new UserService();
    public UserList() {
        addClassName("user-list");
        setSizeFull();
        setupGrid();
        updateGrid();
        add(userGrid);
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
