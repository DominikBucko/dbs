package com.icloud.dominik.UI.components;

import backend.entity.Asset;
import backend.entity.Department;
import backend.service.AssetService;
import backend.service.DepartmentService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

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
        delete.addClickListener(click -> clearFields());
        update.addClickListener(click -> updateAsset());
        setUpCombobox();
        add(name, type, qr_code, asset_category, department, status, buttonLayout);
    }

    public void setAsset(Asset asset) {
        binder.setBean(asset);
    }

    private void updateAsset() {
        assetService.update(binder.getBean());
        cancel.click();
    }

    private void setUpCombobox() {
        department.setItems(departmentService.getAll());
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
        assetService.createNew(assets);
        cancel.click();
    }
}
