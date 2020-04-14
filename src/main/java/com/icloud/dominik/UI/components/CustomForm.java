package com.icloud.dominik.UI.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomForm extends FormLayout {
    Button save = new Button("Save");
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete");
    Button update = new Button("Update");
    HorizontalLayout buttonLayout = buttonRow();
    Notification notification = new Notification();



    protected void setupNotification(String text) {
        Button cancelNotification = new Button("OK");
        cancelNotification.addClickListener(click -> notification.close());
        Text userAddedSuccess = new Text(text);
        HorizontalLayout notificationHL = new HorizontalLayout(userAddedSuccess, cancelNotification);
        notification.add(notificationHL);
    }

    protected HorizontalLayout buttonRow() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }

    public void setCancelBtn(Button cancel) {
        this.cancel = cancel;
    }

    public Button getCancel() {
        return cancel;
    }

    public void setUpdateMode() {
        buttonLayout.removeAll();
        buttonLayout.add(update, delete, cancel);
    }

    public void setCreateMode() {
        buttonLayout.removeAll();
        buttonLayout.add(save, delete, cancel);
    }

}
