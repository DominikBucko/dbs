package com.icloud.dominik.UI;

import backend.entity.Department;
import backend.service.DepartmentService;
import backend.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Collection;

public class UserForm extends FormLayout {
    TextField firstName = new TextField("First Name");
    TextField lastName  = new TextField("Last Name");
    TextField city = new TextField("City");
    TextField address = new TextField("Address");
    TextField postcode = new TextField("Postcode");
    ComboBox<Department> department;
    TextField username = new TextField("Username");
    TextField password = new TextField("Password");
    Checkbox isAdmin = new Checkbox("Make Administrator");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    DepartmentService departmentService = new DepartmentService();
    UserService userService = new UserService();

    public UserForm() {
        addClassName("new-user-form");
        departmentComboSetup();

        add(
                firstName,
                lastName,
                city,
                address,
                postcode,
                department,
                username,
                password,
                isAdmin,
                buttonRow()
        );
    }

    private void departmentComboSetup() {
        Collection<Department> departments = departmentService.getAll();
        department = new ComboBox<>("Department", departments);
        department.setItemLabelGenerator(Department::getDepartment_name);
        department.setPlaceholder("No department selected");
        department.setWidth("100%");
    }

    private HorizontalLayout buttonRow() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    cancel.addClickShortcut(Key.ESCAPE);
    return new HorizontalLayout(save, delete, cancel);
    }

}
