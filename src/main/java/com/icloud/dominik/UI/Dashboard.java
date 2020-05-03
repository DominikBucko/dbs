package com.icloud.dominik.UI;

import backend.entity.Asset;
import backend.entity.Department;
import backend.service.AssetService;
import backend.service.DepartmentService;
import backend.service.LocationService;
import backend.service.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.List;


@Route(value = "", layout = HomeLayout.class)
@Secured({"ROLE_Admin", "ROLE_User"})
@PageTitle("Dashboard | SAM")
public class Dashboard extends VerticalLayout {

    Grid<Asset> assetGrid = new Grid<>(Asset.class);
    ComboBox<Department> departments = new ComboBox<>();
    ComboBox<String> status = new ComboBox<>();
    ComboBox<String> category = new ComboBox<>();
    Button show = new Button("Show");
    
    DepartmentService departmentService = new DepartmentService();
    LocationService locationService = new LocationService();
    AssetService assetService = new AssetService();
    UserService userService = new UserService();
    
    CallbackDataProvider<Asset, Void> assetProvider;

    Div div1 = new Div(new Text("Number of registered users: " +userService.countAll()));
    Div div2 = new Div(new Text("Number of existing assets: " + assetService.countAll()));
    Div div3 = new Div(new Text("Number of departments: " + departmentService.countAll()));
    Div div4 = new Div(new Text("Number of locations: " + locationService.countAll()));
    VerticalLayout stats = new VerticalLayout(div1, div2, div3, div4);
    HorizontalLayout filter = new HorizontalLayout(departments, status, category, show);

    @Autowired
    public Dashboard() {
        setUpFilter();
        setupGrid();
        add(stats, filter, assetGrid);
    }

    private void updateGrid() {
        assetGrid.getDataProvider().refreshAll();
    }

    private void setupGrid() {
        assetService.hib_test();
        assetProvider = DataProvider.fromCallbacks(
            query -> assetService.getStats(query.getOffset(), query.getLimit(), departments.getValue().getDepartment_name(), status
            .getValue(), category.getValue()).stream(),
            query -> assetService.getStats(query.getOffset(), query.getLimit(), departments.getValue().getDepartment_name(), status
            .getValue(), category.getValue()).size()
        );
        assetGrid.setDataProvider(assetProvider);
        assetGrid.setColumns("name", "type", "asset_category", "status", "count");
        assetGrid.addColumn(asset -> {
            Department department = asset.getDepartment();
            return department == null ? "none" : department.getDepartment_name();
        }).setHeader("Department");
        assetGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void setUpFilter() {
        List<Department> departmentsList = departmentService.getAll();
        departments.setItems(departmentsList);
        departments.setItemLabelGenerator(Department::getDepartment_name);
        departments.setValue(departmentsList.get(0));
        category.setItems("Consumable", "Asset", "%");
        category.setValue("%");
        status.setItems("FREE", "IN USE", "%");
        status.setValue("%");
        show.addClickListener(click -> updateGrid());
    }
}
