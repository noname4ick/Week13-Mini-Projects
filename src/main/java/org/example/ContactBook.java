package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ContactBook extends Application {

    private Stage stage;
    private Scene listScene;
    private Scene formScene;

    private ListView<String> listView = new ListView<>();
    private Label listErrorLabel = new Label();
    private final String FILE_NAME = "contacts.txt";

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        createListScene();
        createFormScene();

        loadContacts();

        primaryStage.setTitle("Mini Contact Book");
        primaryStage.setScene(listScene);
        primaryStage.show();
    }

    private void createListScene() {
        Label title = new Label("My Contacts");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button addBtn = new Button("Add New");
        Button deleteBtn = new Button("Delete Selected");
        Button refreshBtn = new Button("Refresh");

        addBtn.setOnAction(e -> stage.setScene(formScene));
        refreshBtn.setOnAction(e -> loadContacts());
        deleteBtn.setOnAction(e -> deleteSelected());

        HBox controls = new HBox(10, addBtn, deleteBtn, refreshBtn);
        controls.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, title, listView, controls, listErrorLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        listScene = new Scene(layout, 480, 380);
    }

    private void createFormScene() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(30));
        grid.setAlignment(Pos.CENTER);

        TextField nameField = new TextField();
        TextField phoneField = new TextField();
        TextField emailField = new TextField();
        Label formErrorLabel = new Label();
        formErrorLabel.setTextFill(javafx.scene.paint.Color.RED);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Phone:"), 0, 1);
        grid.add(phoneField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);

        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");

        saveBtn.setOnAction(e -> {
            if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || emailField.getText().isEmpty()) {
                formErrorLabel.setText("All fields are required!");
            } else {
                saveContact(nameField.getText(), phoneField.getText(), emailField.getText());
                nameField.clear(); phoneField.clear(); emailField.clear();
                formErrorLabel.setText("");
                loadContacts();
                stage.setScene(listScene);
            }
        });

        cancelBtn.setOnAction(e -> stage.setScene(listScene));

        HBox formButtons = new HBox(10, saveBtn, cancelBtn);
        VBox layout = new VBox(20, grid, formButtons, formErrorLabel);
        layout.setAlignment(Pos.CENTER);

        formScene = new Scene(layout, 480, 380);
    }


    private void loadContacts() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) file.createNewFile();

            List<String> displayList = Files.readAllLines(Paths.get(FILE_NAME))
                    .stream()
                    .map(line -> {
                        String[] parts = line.split("\\|");
                        return (parts.length == 3) ? parts[0] + " --- " + parts[1] + "@" + parts[2] : line;
                    })
                    .collect(Collectors.toList());

            listView.setItems(FXCollections.observableArrayList(displayList));
            listErrorLabel.setText("");
        } catch (IOException e) {
            listErrorLabel.setText("Error loading contacts: " + e.getMessage());
        }
    }

    private void saveContact(String name, String phone, String email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(name + "|" + phone + "|" + email);
            writer.newLine();
        } catch (IOException e) {
            listErrorLabel.setText("Error saving contact.");
        }
    }

    private void deleteSelected() {
        int index = listView.getSelectionModel().getSelectedIndex();
        if (index < 0) return;

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            lines.remove(index);
            Files.write(Paths.get(FILE_NAME), lines);
            loadContacts();
        } catch (IOException e) {
            listErrorLabel.setText("Error deleting contact.");
        }
    }
}