package com.icloud.dominik.UI.components;

import backend.entity.Department;
import backend.entity.User;
import backend.service.DepartmentService;
import backend.service.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserForm extends FormLayout {
    TextField first_name = new TextField("First Name");
    TextField surname = new TextField("Last Name");
    TextField city = new TextField("City");
    TextField address = new TextField("Address");
    IntegerField postcode = new IntegerField("Postcode");
    ComboBox<Department> department = new ComboBox<>("Department");
    TextField login = new TextField("Username");
    TextField password = new TextField("Password");
    Checkbox is_admin = new Checkbox("Make Administrator");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button update = new Button("Update");
    Button cancel = new Button("Cancel");
    Notification userAdded = new Notification();
    HorizontalLayout buttonLayout;

    DepartmentService departmentService = new DepartmentService();
    UserService userService = new UserService();

    Binder<User> binder = new Binder<>(User.class);

    public UserForm() {
        binder.bindInstanceFields(this);
        addClassName("new-user-form");
        departmentComboSetup();
        delete.addClickListener(click -> clearForm());
        save.addClickListener(click -> saveUser());
        setupNotification();
        buttonLayout = buttonRow();
        add(
                first_name,
                surname,
                city,
                address,
                postcode,
                department,
                login,
                password,
                is_admin,
                buttonLayout
        );
    }

    public void mapSaveBtnToCreate() {
        buttonLayout.removeAll();
        buttonLayout.add(save, delete, cancel);
    }

    public void mapSaveBtnToUpdate() {
        buttonLayout.removeAll();
        buttonLayout.add(update, delete, cancel);
    }

    private void updateUser() {
        userService.updateUser(binder.getBean());
        cancel.click();
    }

    private void setupNotification() {
        Button cancelNotification = new Button("OK");
        cancelNotification.addClickListener(click -> userAdded.close());
        Text userAddedSuccess = new Text("New user added successfully.");
        HorizontalLayout notificationHL = new HorizontalLayout(userAddedSuccess, cancelNotification);
        userAdded.add(notificationHL);
    }

    public void setUser(User user) {
        binder.setBean(user);
    }

    private void saveUser() {
        List<User> users = new ArrayList<>();
        User user = new User(first_name.getValue(),
                surname.getValue(),
                city.getValue(),
                address.getValue(),
                postcode.getValue(),
                department.getValue().getDepartment_id(),
                login.getValue(),
                password.getValue(),
                is_admin.getValue()
        );
        users.add(user);
        if (userService.createNew(users)) {
            cancel.click();
            userAdded.open();
        } else {
            System.out.println("ERROR");
        }
    }

    private void clearForm() {
        first_name.clear();
        surname.clear();
        city.clear();
        address.clear();
        postcode.clear();
        department.clear();
        login.clear();
        password.clear();
        is_admin.clear();

        cancel.click();
    }

    private void departmentComboSetup() {
        Collection<Department> departments = departmentService.getAll();
        department.setItems(departments);
        department.setItemLabelGenerator(Department::getDepartment_name);
        department.setPlaceholder("No department selected");
        department.setWidth("100%");
    }

    private HorizontalLayout buttonRow() {
    update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    update.addClickShortcut(Key.ENTER);
    save.addClickShortcut(Key.ENTER);
    cancel.addClickShortcut(Key.ESCAPE);
    return new HorizontalLayout(save, delete, cancel);
    }

    public Button getCancel() {
        return cancel;
    }
}
