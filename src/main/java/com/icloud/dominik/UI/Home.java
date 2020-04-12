package com.icloud.dominik.UI;

import backend.entity.Asset;
import backend.entity.Department;
import backend.service.AssetService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "", layout = HomeLayout.class)
@PageTitle("Assets | SAM")
public class Home extends VerticalLayout {
    Grid<Asset> grid = new Grid<>(Asset.class);
    AssetService assetService = new AssetService();
    public Home() {
        addClassName("list-view");
        setSizeFull();
        setupGrid();
        updateGrid();
//        VerticalLayout layout = new VerticalLayout();
//        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
//        Button btn = new Button("EXEC");
//        AssetsManager assetsManager = new AssetsManager();
//        btn.addClickListener(click -> assetsManager.get_assets());
        add(grid);
	}

    private void updateGrid() {
        grid.setItems(assetService.getAll());
    }

    private void setupGrid() {
        grid.addClassName("asset-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("asset_department");
        grid.setColumns("name", "type", "qr_code", "asset_category", "status");
        grid.addColumn(asset -> {
            Department department = asset.getDepartment();
            return department == null ? "none" : department.getDepartment_name();
        }).setHeader("Department");

    }
}
