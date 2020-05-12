package com.icloud.dominik.UI.components;

import backend.entity.Asset;
import backend.entity.AssetFault;
import backend.entity.Fault;
import backend.service.AssetFaultService;
import backend.service.AssetService;
import backend.service.FaultService;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.sql.SQLException;


public class FaultForm extends CustomForm {
    IntegerField assetID = new IntegerField("Asset ID");
    TextField department = new TextField("Department");
    TextField assetName = new TextField("Asset Name");
    TextField assetType = new TextField("Asset Type");
    ComboBox<Fault> faults = new ComboBox<>("Fault");
    AssetService assetService = new AssetService();
    Checkbox fixable = new Checkbox("Is fixable");
    AssetFaultService assetFaultService = new AssetFaultService();
    Asset asset;

    public FaultForm() {
        setupNotification("Asset fault registered.");
        filterConfig();
        comboFaultConfig();
        save.addClickListener(click -> registerFault());
        buttonLayout.remove(delete, update, clear);
        save.setVisible(true);
        cancel.setVisible(true);
        add(assetID, department, assetName, assetType, faults, fixable, buttonRow());
    }

    private void registerFault() {
        if (asset != null) {
            try {
                assetFaultService.passToService(assetID.getValue(), faults.getValue().getFault_id(), fixable.getValue());
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            cancel.click();
        }
    }

    private void filterConfig() {
        assetID.setPlaceholder("Asset ID");
        assetID.setValueChangeMode(ValueChangeMode.ON_BLUR);
        assetID.addValueChangeListener(e -> applyFilter());
    }

    private void comboFaultConfig() {
        FaultService faultService = new FaultService();
        faults.setItemLabelGenerator(Fault::getType_of_fault);
        faults.setItems(faultService.getAll());
    }

    private void applyFilter() {
        asset = assetService.getByID(assetID.getValue());
        department.setValue(asset.getDepartment().getDepartment_name());
        assetName.setValue(asset.getName());
        assetType.setValue(asset.getType());
    }

}
