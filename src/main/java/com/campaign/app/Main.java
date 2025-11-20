package com.campaign.app;

import com.campaign.app.model.Campaign;
import com.campaign.app.ui.CampaignForm;
import com.campaign.app.ui.CampaignList;
import com.campaign.app.ui.ImmediateDispatch;
import com.campaign.app.ui.ProcessingHistory;
import com.campaign.app.ui.Sidebar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();

        // Initialize Views
        Sidebar sidebar = new Sidebar(
                this::showCreateCampaign,
                this::showListCampaigns,
                this::showDispatch,
                this::showHistory);

        root.setLeft(sidebar);

        // Show initial view
        showCreateCampaign();

        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Gestão de Campanhas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showCreateCampaign() {
        root.setCenter(new CampaignForm());
    }

    private void showListCampaigns() {
        root.setCenter(new CampaignList(this::showEditCampaign));
    }

    private void showEditCampaign(Campaign campaign) {
        root.setCenter(new CampaignForm(campaign));
    }

    private void showDispatch() {
        root.setCenter(new ImmediateDispatch());
    }

    private void showHistory() {
        root.setCenter(new ProcessingHistory());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
