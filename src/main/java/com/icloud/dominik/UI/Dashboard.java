package com.icloud.dominik.UI;

import backend.entity.Asset;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "dashboard", layout = HomeLayout.class)
@PageTitle("Dashboard | SAM")
public class Dashboard extends VerticalLayout {
    Grid<Asset> assetGrid = new Grid<>(Asset.class);

}
