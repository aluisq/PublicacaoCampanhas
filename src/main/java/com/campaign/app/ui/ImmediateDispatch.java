package com.campaign.app.ui;

import com.campaign.app.service.CampaignService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ImmediateDispatch extends VBox {
    private TextField idField;
    private Label statusLabel;

    public ImmediateDispatch() {
        initialize();
    }

    private void initialize() {
        this.setPadding(new Insets(20));
        this.setSpacing(15);
        this.getStyleClass().add("content-area");

        Label titleLabel = new Label("Disparo Imediato");
        titleLabel.getStyleClass().add("page-title");

        Label instructionLabel = new Label("Informe o ID da campanha para realizar o disparo imediato:");

        idField = new TextField();
        idField.setPromptText("ID da Campanha");
        idField.setMaxWidth(400);

        Button btnDispatch = new Button("Realizar Disparo");
        btnDispatch.getStyleClass().add("primary-button");
        btnDispatch.setOnAction(e -> performDispatch());

        statusLabel = new Label();

        this.getChildren().addAll(titleLabel, instructionLabel, idField, btnDispatch, statusLabel);
    }

    private void performDispatch() {
        String id = idField.getText();
        if (id == null || id.trim().isEmpty()) {
            statusLabel.setText("Por favor, informe um ID válido.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (CampaignService.getInstance().getCampaignById(id).isEmpty()) {
            statusLabel.setText("Campanha não encontrada.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        CampaignService.getInstance().addHistory(id, "Disparo Imediato");
        statusLabel.setText("Disparo realizado com sucesso! Verifique o histórico.");
        statusLabel.setStyle("-fx-text-fill: green;");
        idField.clear();
    }
}
