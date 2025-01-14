package com.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private List<String> names;
    private TextField nameField1;
    private TextField nameField2;
    private Label messageLabel;
    private Graph graph;

    @Override
    public void start(Stage primaryStage) {
        // Initialize the graph and load data
        graph = new Graph();
        try {
            graph.loadFromFile();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load graph data. Please ensure the resource files are available.");
            return;
        }

        names = new ArrayList<>(graph.getAdjacencyList().keySet());

        // Create UI components
        Label titleLabel = new Label("Actor Shortest Path Finder");
        titleLabel.getStyleClass().add("title");

        Label explanationLabel = new Label(
                "Find the shortest connection between two actors based on the movies they've acted in together."
        );
        explanationLabel.getStyleClass().add("explanation");
        explanationLabel.setWrapText(true);

        nameField1 = new TextField();
        nameField1.setPromptText("Enter the first actor's name...");
        nameField1.getStyleClass().add("text-field");

        nameField2 = new TextField();
        nameField2.setPromptText("Enter the second actor's name...");
        nameField2.getStyleClass().add("text-field");

        Button findPathButton = new Button("Find Shortest Path");
        findPathButton.getStyleClass().add("button");

        messageLabel = new Label();
        messageLabel.getStyleClass().add("message-label");
        messageLabel.setWrapText(true);

        findPathButton.setOnAction(event -> findShortestPath(nameField1.getText(), nameField2.getText()));

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(titleLabel, explanationLabel, nameField1, nameField2, findPathButton, messageLabel);

        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Actor Shortest Path Finder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void findShortestPath(String name1, String name2) {
        if (name1 == null || name1.isBlank() || name2 == null || name2.isBlank()) {
            messageLabel.setText("Please enter both actor names.");
            return;
        }

        boolean name1Exists = names.contains(name1);
        boolean name2Exists = names.contains(name2);

        if (!name1Exists && !name2Exists) {
            messageLabel.setText("Neither actor name exists. Please try again.");
            return;
        }
        if (!name1Exists) {
            messageLabel.setText("The first actor's name does not exist. Please try again.");
            return;
        }
        if (!name2Exists) {
            messageLabel.setText("The second actor's name does not exist. Please try again.");
            return;
        }

        String path = graph.findShortestPath(name1, name2);
        messageLabel.setText(path);
    }

    private void showError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
