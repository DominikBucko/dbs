package com.icloud.dominik.UI;

import backend.entity.Asset;
import backend.entity.Department;
import backend.entity.Location;
import backend.service.AssetService;
import com.icloud.dominik.UI.components.AssetForm;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


@Route(value = "", layout = HomeLayout.class)
@PageTitle("Assets | SAM")
public class Assets extends VerticalLayout {
    Grid<Asset> grid = new Grid<>(Asset.class);
    AssetService assetService = new AssetService();
    Button addNew = new Button("New Asset");
    AssetForm assetForm = new AssetForm();
    Div dialogContent = new Div();
    Dialog dialog = new Dialog();
    Text itemCount = new Text("");
    public Assets() {
        addClassName("list-view");
        setSizeFull();
        setupGrid();
        updateGrid();
        setupDialog();
        addNew.addClickListener(click -> createNewAsset());
//        VerticalLayout layout = new VerticalLayout();
//        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
//        Button btn = new Button("EXEC");
//        AssetsManager assetsManager = new AssetsManager();
//        btn.addClickListener(click -> assetsManager.get_assets());
        add(addNew, itemCount, grid);
	}

    private void createNewAsset() {
        assetForm.setCreateMode();
        dialog.open();
    }

    private void updateAsset(Asset asset) {
        assetForm.setAsset(asset);
        assetForm.setUpdateMode();
        dialog.open();
    }

    private void setupDialog() {
        dialogContent.add(assetForm);
        assetForm.getCancel().addClickListener(click -> refreshAfterDialogCloses());
        dialog.add(dialogContent);

    }

    private void updateGrid() {
//        List<Asset> locations = assetService.getAll();
        grid.getDataProvider().refreshAll();
        itemCount.setText("Number of items listed: " + assetService.countAll());
    }

    private void setupGrid() {

        CallbackDataProvider<Asset, Void> provider = DataProvider.fromCallbacks(
                query -> assetService.getAll(query.getOffset(), query.getLimit()).stream(),
                query -> assetService.countAll()
        );
        grid.setDataProvider(provider);
        grid.addClassName("asset-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("asset_department");
        grid.setColumns("name", "type", "qr_code", "asset_category", "status");
        grid.addColumn(asset -> {
            Department department = asset.getDepartment();
            return department == null ? "none" : department.getDepartment_name();
        }).setHeader("Department");
        grid.asSingleSelect().addValueChangeListener(evt -> updateAsset(evt.getValue()));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private void refreshAfterDialogCloses() {
        updateGrid();
        dialog.close();
    }

}
