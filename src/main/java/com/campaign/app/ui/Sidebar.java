package com.campaign.app.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Sidebar extends VBox {
    private Runnable onCreateCampaign;
    private Runnable onListCampaigns;
    private Runnable onDispatch;
    private Runnable onHistory;

    public Sidebar(Runnable onCreateCampaign, Runnable onListCampaigns, Runnable onDispatch, Runnable onHistory) {
        this.onCreateCampaign = onCreateCampaign;
        this.onListCampaigns = onListCampaigns;
        this.onDispatch = onDispatch;
        this.onHistory = onHistory;

        initialize();
    }

    private void initialize() {
        this.setPadding(new Insets(20));
        this.setSpacing(15);
        this.getStyleClass().add("sidebar");
        this.setPrefWidth(250);

        Button btnCreate = createButton("Criar Campanha", onCreateCampaign);
        Button btnList = createButton("Listar Campanhas", onListCampaigns);
        Button btnDispatch = createButton("Disparo Imediato", onDispatch);
        Button btnHistory = createButton("Histórico de Processamento", onHistory);

        this.getChildren().addAll(btnCreate, btnList, btnDispatch, btnHistory);
    }

    private Button createButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.getStyleClass().add("sidebar-button");
        btn.setOnAction(e -> action.run());
        return btn;
    }
}
