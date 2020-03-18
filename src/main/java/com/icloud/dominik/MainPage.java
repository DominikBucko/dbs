package com.icloud.dominik;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the main-page template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("main-page")
@JsModule("./main-page.js")
public class MainPage extends PolymerTemplate<MainPage.MainPageModel> {

    /**
     * Creates a new MainPage.
     */
    public MainPage() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between MainPage and main-page
     */
    public interface MainPageModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
