package com.icloud.dominik.UI;

import backend.entity.Asset;
import backend.entity.Department;
import backend.service.AssetService;
import backend.service.DepartmentService;
import backend.service.LocationService;
import backend.service.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "dashboard", layout = HomeLayout.class)
@PageTitle("Dashboard | SAM")
public class Dashboard extends VerticalLayout {
    Grid<Asset> assetGrid = new Grid<>(Asset.class);
    ComboBox<Department> departments = new ComboBox<>();
    ComboBox<String> status = new ComboBox<>();
    ComboBox<String> category = new ComboBox<>();
    
    DepartmentService departmentService = new DepartmentService();
    LocationService locationService = new LocationService();
    AssetService assetService = new AssetService();
    UserService userService = new UserService();
    
    CallbackDataProvider<Asset, Void> assetProvider = DataProvider.fromCallbacks(
            query -> assetService.getStats(query.getOffset(), query.getLimit(), departments.getLabel(), status
            .getValue(), category.getValue()).stream(),
            query -> assetService.countAll()
    );

    Text user_count = new Text("Number of registered users: " + userService.countAll());
    Text asset_count = new Text("Number of existing assets: " + assetService.countAll());
    Text department_count = new Text("Number of departments: " + departmentService.countAll());
    Text location_count = new Text("Number of locations: " + locationService.countAll());
    VerticalLayout stats = new VerticalLayout(user_count, asset_count, department_count, location_count);
    HorizontalLayout filter = new HorizontalLayout(departments, status, category);
    public Dashboard() {
        setUpFilter();
        setupGrid();
    }

    private void setupGrid() {
        assetGrid.setDataProvider(assetProvider);
        assetGrid.setColumns("name", "type", "asset_category");
    }

    private void setUpFilter() {
        departments.setItems(departmentService.getAll());
        departments.setItemLabelGenerator(Department::getDepartment_name);
        category.setItems("Consumable", "Asset");
        status.setItems("FREE", "IN USE");
    }
}
