package com.campaign.app.ui;

import com.campaign.app.model.Campaign;
import com.campaign.app.service.CampaignService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.function.Consumer;

public class CampaignList extends VBox {
    private TableView<Campaign> table;
    private Consumer<Campaign> onEdit;

    public CampaignList(Consumer<Campaign> onEdit) {
        this.onEdit = onEdit;
        initialize();
    }

    private void initialize() {
        this.setPadding(new Insets(20));
        this.setSpacing(15);
        this.getStyleClass().add("content-area");

        Label titleLabel = new Label("Listagem de Campanhas");
        titleLabel.getStyleClass().add("page-title");

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Campaign, String> nameCol = new TableColumn<>("Nome");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Campaign, String> subjectCol = new TableColumn<>("Assunto");
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));

        TableColumn<Campaign, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Campaign, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Campaign, Void> actionCol = new TableColumn<>("Ações");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button("Editar");

            {
                btnEdit.setOnAction(event -> {
                    Campaign campaign = getTableView().getItems().get(getIndex());
                    onEdit.accept(campaign);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEdit);
                }
            }
        });

        table.getColumns().addAll(nameCol, subjectCol, statusCol, idCol, actionCol);

        Button btnRefresh = new Button("Atualizar Lista");
        btnRefresh.setOnAction(e -> refreshData());

        this.getChildren().addAll(titleLabel, btnRefresh, table);
        refreshData();
    }

    public void refreshData() {
        table.getItems().setAll(CampaignService.getInstance().getAllCampaigns());
    }
}
