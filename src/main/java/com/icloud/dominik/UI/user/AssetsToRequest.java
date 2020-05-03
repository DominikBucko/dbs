package com.icloud.dominik.UI.user;

import backend.dataHandling.UserAssetHandler;
import backend.entity.Asset;
import backend.service.AssetService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "user_assets", layout = UserLayout.class)
@Secured("ROLE_User")
@PageTitle("Assets | SAM")
public class AssetsToRequest extends VerticalLayout {
    Grid<Asset> assetGrid = new Grid<>(Asset.class);
    Button userAssets = new Button("My Assets");
    AssetService assetService = new AssetService();
    UserAssetHandler userAssetHandler = new UserAssetHandler();

}
