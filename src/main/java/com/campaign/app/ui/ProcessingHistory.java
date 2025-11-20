package com.campaign.app.ui;

import com.campaign.app.model.HistoryRecord;
import com.campaign.app.service.CampaignService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

public class ProcessingHistory extends VBox {
    private TextField idField;
    private TableView<HistoryRecord> historyTable;

    public ProcessingHistory() {
        initialize();
    }

    private void initialize() {
        this.setPadding(new Insets(20));
        this.setSpacing(15);
        this.getStyleClass().add("content-area");

        Label titleLabel = new Label("Histórico de Processamento");
        titleLabel.getStyleClass().add("page-title");

        HBox searchBox = new HBox(10);
        idField = new TextField();
        idField.setPromptText("Informe o ID da Campanha (Opcional)");
        idField.setPrefWidth(300);

        Button btnSearch = new Button("Buscar / Atualizar");
        btnSearch.setOnAction(e -> searchHistory());

        searchBox.getChildren().addAll(idField, btnSearch);

        historyTable = new TableView<>();
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<HistoryRecord, String> idCol = new TableColumn<>("ID da Campanha");
        idCol.setCellValueFactory(new PropertyValueFactory<>("campaignId"));

        TableColumn<HistoryRecord, String> dateCol = new TableColumn<>("Data/Hora");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("formattedTimestamp"));

        TableColumn<HistoryRecord, String> typeCol = new TableColumn<>("Tipo de Envio");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        historyTable.getColumns().addAll(idCol, dateCol, typeCol);

        this.getChildren().addAll(titleLabel, searchBox, historyTable);

        // Initial load
        searchHistory();
    }

    private void searchHistory() {
        String id = idField.getText();
        List<HistoryRecord> records;

        if (id == null || id.trim().isEmpty()) {
            records = CampaignService.getInstance().getProcessingHistory(null);
        } else {
            records = CampaignService.getInstance().getProcessingHistory(id);
        }

        historyTable.getItems().setAll(records);
    }
}
