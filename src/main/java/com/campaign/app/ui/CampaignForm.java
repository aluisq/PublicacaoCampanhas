package com.campaign.app.ui;

import com.campaign.app.model.Campaign;
import com.campaign.app.service.CampaignService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.io.File;
import java.time.LocalDate;

public class CampaignForm extends VBox {
    private TextField nameField;
    private TextField subjectField;
    private TextArea descriptionArea;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private ComboBox<String> frequencyBox;
    private Label imageLabel;
    private String selectedImagePath;
    private Campaign existingCampaign; // Null if creating new

    public CampaignForm(Campaign campaign) {
        this.existingCampaign = campaign;
        initialize();
    }

    public CampaignForm() {
        this(null);
    }

    private void initialize() {
        this.setPadding(new Insets(20));
        this.setSpacing(15);
        this.getStyleClass().add("content-area");

        Label titleLabel = new Label(existingCampaign == null ? "Nova Campanha" : "Editar Campanha");
        titleLabel.getStyleClass().add("page-title");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        int row = 0;

        // ID (if editing)
        if (existingCampaign != null) {
            grid.add(new Label("ID da Campanha:"), 0, row);
            TextField idField = new TextField(existingCampaign.getId());
            idField.setEditable(false);
            idField.getStyleClass().add("id-field");

            Button btnCopy = new Button("Copiar");
            btnCopy.setOnAction(e -> {
                javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
                content.putString(existingCampaign.getId());
                javafx.scene.input.Clipboard.getSystemClipboard().setContent(content);
            });

            HBox idBox = new HBox(10, idField, btnCopy);
            grid.add(idBox, 1, row);
            row++;
        }

        // Name
        grid.add(new Label("Nome da Campanha:"), 0, row);
        nameField = new TextField();
        grid.add(nameField, 1, row);
        row++;

        // Subject
        grid.add(new Label("Assunto:"), 0, row);
        subjectField = new TextField();
        grid.add(subjectField, 1, row);
        row++;

        // Description
        grid.add(new Label("Descrição:"), 0, row);
        descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(3);
        grid.add(descriptionArea, 1, row);
        row++;

        // Dates
        grid.add(new Label("Data Início:"), 0, row);
        startDatePicker = new DatePicker();
        grid.add(startDatePicker, 1, row);
        row++;

        grid.add(new Label("Data Final:"), 0, row);
        endDatePicker = new DatePicker();
        grid.add(endDatePicker, 1, row);
        row++;

        // Frequency
        grid.add(new Label("Frequência:"), 0, row);
        frequencyBox = new ComboBox<>();
        frequencyBox.getItems().addAll("Imediata", "Diária", "Mensal");
        frequencyBox.setValue("Imediata");
        grid.add(frequencyBox, 1, row);
        row++;

        // Image
        grid.add(new Label("Imagem:"), 0, row);
        Button btnImage = new Button("Selecionar Imagem");
        imageLabel = new Label("Nenhuma imagem selecionada");
        btnImage.setOnAction(e -> chooseImage());
        VBox imageBox = new VBox(5, btnImage, imageLabel);
        grid.add(imageBox, 1, row);
        row++;

        // Save Button
        Button btnSave = new Button("Salvar");
        btnSave.getStyleClass().add("primary-button");
        btnSave.setOnAction(e -> saveCampaign());

        this.getChildren().addAll(titleLabel, grid, btnSave);

        if (existingCampaign != null) {
            populateFields();
        }
    }

    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            imageLabel.setText(selectedFile.getName());
        }
    }

    private void populateFields() {
        nameField.setText(existingCampaign.getName());
        subjectField.setText(existingCampaign.getSubject());
        descriptionArea.setText(existingCampaign.getDescription());
        startDatePicker.setValue(existingCampaign.getStartDate());
        endDatePicker.setValue(existingCampaign.getEndDate());
        frequencyBox.setValue(existingCampaign.getFrequency());
        selectedImagePath = existingCampaign.getImagePath();
        if (selectedImagePath != null) {
            imageLabel.setText(new File(selectedImagePath).getName());
        }
    }

    private void saveCampaign() {
        String name = nameField.getText();
        String subject = subjectField.getText();
        String description = descriptionArea.getText();
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();
        String freq = frequencyBox.getValue();

        if (name.isEmpty() || subject.isEmpty()) {
            showAlert("Erro", "Nome e Assunto são obrigatórios.");
            return;
        }

        if (existingCampaign == null) {
            Campaign newCampaign = new Campaign(name, subject, description, start, end, freq, selectedImagePath);
            CampaignService.getInstance().saveCampaign(newCampaign);
            showAlert("Sucesso", "Campanha criada com sucesso!");
            clearFields();
        } else {
            existingCampaign.setName(name);
            existingCampaign.setSubject(subject);
            existingCampaign.setDescription(description);
            existingCampaign.setStartDate(start);
            existingCampaign.setEndDate(end);
            existingCampaign.setFrequency(freq);
            existingCampaign.setImagePath(selectedImagePath);
            CampaignService.getInstance().saveCampaign(existingCampaign);
            showAlert("Sucesso", "Campanha atualizada com sucesso!");
        }
    }

    private void clearFields() {
        nameField.clear();
        subjectField.clear();
        descriptionArea.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        frequencyBox.setValue("Imediata");
        imageLabel.setText("Nenhuma imagem selecionada");
        selectedImagePath = null;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
