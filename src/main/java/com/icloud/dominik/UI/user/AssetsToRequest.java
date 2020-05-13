package com.icloud.dominik.UI.user;

import backend.dataHandling.UserAssetHandler;
import backend.entity.Asset;
import backend.entity.Department;
import backend.service.AssetService;
import backend.service.LogService;
import backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "user_assets", layout = UserLayout.class)
@Secured("ROLE_User")
@PageTitle("Assets | SAM")
public class AssetsToRequest extends VerticalLayout {
    Grid<Asset> assetGrid = new Grid<>(Asset.class);
    Button userAssets = new Button("My Tickets");
    AssetService assetService = new AssetService();
    UserAssetHandler userAssetHandler = new UserAssetHandler(
            SecurityContextHolder.getContext().getAuthentication().getName()
    );
    Dialog requestAssetDialog = new Dialog();
    CallbackDataProvider<Asset, Void> provider;
    Asset current_selection = null;

    private AssetsToRequest() {
        setupGrid();
        setupDialog();
        userAssets.addClickListener(click -> userAssets.getUI().ifPresent(ui -> ui.navigate(UserTickets.class)));
        add(userAssets, assetGrid);
    }

    private void setupDialog() {
        VerticalLayout dialogLayout = new VerticalLayout();
        H3 prompt = new H3("Do you want to request this asset?");
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button yes = new Button("Yes");
        yes.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        yes.addClickListener(click -> requestAsset());
        Button no = new Button("No");
        no.addThemeVariants(ButtonVariant.LUMO_ERROR);
        no.addClickListener(click -> requestAssetDialog.close());
        buttonLayout.add(no, yes);
        dialogLayout.add(prompt, buttonLayout);
        requestAssetDialog.add(dialogLayout);
    }

    private void requestAsset() {
        LogService.log(new UserService().getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUser_id(), "Requested item " + current_selection.getAsset_id());
        userAssetHandler.createTicket(current_selection);
        requestAssetDialog.close();
    }

    private void setupGrid() {
        provider = DataProvider.fromCallbacks(
                query ->  userAssetHandler.getAvailableAssets(query.getLimit(), query.getOffset()).stream(),
                query -> userAssetHandler.getAvailableAssets(query.getLimit(), query.getOffset()).size()
        );
        assetGrid.setDataProvider(provider);
//        assetGrid.setItems(userAssetHandler.getAvailableAssets());
        assetGrid.addClassName("asset-grid");
//        assetGrid.setSizeFull();
        assetGrid.removeColumnByKey("asset_department");
        assetGrid.setColumns("name", "type", "qr_code", "asset_category", "status");
        assetGrid.addColumn(asset -> {
            Department department = asset.getDepartment();
            return department == null ? "none" : department.getDepartment_name();
        }).setHeader("Department");
        assetGrid.asSingleSelect().addValueChangeListener(evt -> openRequestAssetPopup(evt.getValue()));
        assetGrid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private void openRequestAssetPopup(Asset value) {
        current_selection = value;
        requestAssetDialog.open();
    }

}
