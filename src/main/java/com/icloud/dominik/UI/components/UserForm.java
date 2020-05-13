package com.icloud.dominik.UI.components;

import backend.entity.Department;
import backend.entity.User;
import backend.service.DepartmentService;
import backend.service.LogService;
import backend.service.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.function.SerializableFunction;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.security.auth.callback.Callback;
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
    Button clear = new Button("Clear");
    Button update = new Button("Update");
    Button cancel = new Button("Cancel");
    Notification userAdded = new Notification();
    HorizontalLayout buttonLayout;
    DataProvider<Department, Void> provider;

    DepartmentService departmentService = new DepartmentService();
    UserService userService = new UserService();

    Binder<User> binder = new Binder<>(User.class);

    public UserForm() {
        binder.bindInstanceFields(this);
        addClassName("new-user-form");
        departmentComboSetup();
        delete.addClickListener(click -> deleteUser());
        save.addClickListener(click -> saveUser());
        update.addClickListener(click -> updateUser());
        clear.addClickListener(click -> clearForm());
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

    private void deleteUser() {
        if(!userService.delete(binder.getBean())) {
            Notification notification = new Notification(
                    "Deletion not possible! Please remove all references to this item before deleting it");
            notification.setDuration(10000);
//            Button btn = new Button("Close");
//            btn.addClickListener(click -> notification.close());
//            notification.add(btn);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
        cancel.click();
    }

    public void mapSaveBtnToCreate() {
        buttonLayout.removeAll();
        buttonLayout.add(save, clear, cancel);
    }

    public void mapSaveBtnToUpdate() {
        buttonLayout.removeAll();
        buttonLayout.add(update, delete, cancel);
    }

    private void updateUser() {
        LogService.log(new UserService().getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUser_id(), "Updated user " + binder.getBean().getUser_id());
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
        LogService.log(new UserService().getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUser_id(), "Cretated user " + user.getUser_id());
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
        Collection<Department> departments = departmentService.getAll(0, 10000);
        department.setItems(departments);
//        SerializableFunction<String, Integer> filterConverter;
//        provider = DataProvider.fromCallbacks(
//                query -> departmentService.getAll(query.getLimit(), query.getOffset()).stream(),
//                query -> departmentService.countAll()
//                );
//        department.setDataProvider((DataProvider<Department, String>) provider);
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
