package com.icloud.dominik.UI.components;

import backend.entity.Asset;
import backend.entity.Department;
import backend.service.AssetService;
import backend.service.DepartmentService;
import backend.service.LogService;
import backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class AssetForm extends CustomForm {
    TextField name = new TextField("Name");
    TextField type = new TextField("Type");
    TextField qr_code = new TextField( "QR code");
    TextField asset_category = new TextField("AssetCategory");
    ComboBox<Department> department = new ComboBox<>("Department");
    TextField status = new TextField("Status");

    DepartmentService departmentService = new DepartmentService();
    AssetService assetService = new AssetService();
    Binder<Asset> binder = new BeanValidationBinder<>(Asset.class);

    public AssetForm() {
        binder.bindInstanceFields(this);
        addClassName("asset-form");
        setupNotification("Asset saved successfully");
        save.addClickListener(click -> createNewAsset());
        clear.addClickListener(click -> clearFields());
        delete.addClickListener(click -> deleteAsset());
        update.addClickListener(click -> updateAsset());
        setUpCombobox();
        add(name, type, qr_code, asset_category, department, status, buttonLayout);
    }

    private void deleteAsset() {
        if (!assetService.delete(binder.getBean())) {
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

    public void setAsset(Asset asset) {
        binder.setBean(asset);
    }

    private void updateAsset() {
        LogService.log(new UserService().getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUser_id(), "Updated asset " + binder.getBean().getAsset_id());
        assetService.update(binder.getBean());
        cancel.click();
    }

    private void setUpCombobox() {
        department.setItems(departmentService.getAll(0, 10000));
        department.setItemLabelGenerator(Department::getDepartment_name);
        department.setPlaceholder("No department selected");
        department.setWidth("100%");
    }

    private void clearFields() {
        name.clear();
        type.clear();
        qr_code.clear();
        asset_category.clear();
        department.clear();
        status.clear();
    }

    private void createNewAsset() {
        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset(
                name.getValue(),
                type.getValue(),
                qr_code.getValue(),
                asset_category.getValue(),
                status.getValue(),
                department.getValue()
        );

        assets.add(asset);
        LogService.log(new UserService().getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUser_id(), "Created asset " + asset.getAsset_id());
        assetService.createNew(assets);
        cancel.click();
    }
}
