package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class ExpenseTracker extends Application {
    public void start(Stage stage){
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);
        form.setPadding(new Insets(20));

        Label categoryLabel = new Label("Category");
        TextField category = new TextField();
        Label amountLabel = new Label("Amount");
        TextField amount = new TextField();
        Label noteLabel = new Label("Description");
        TextField note = new TextField();

        form.add(categoryLabel,0,0);
        form.add(category,1,0);
        form.add(amountLabel,0,1);
        form.add(amount,1,1);
        form.add(noteLabel,0,2);
        form.add(note,1,2);


        Button addExpenses = new Button("Add Expenses");
        Button showSummary = new Button("Show Summary");
        Button clearFields = new Button("Clear Fields");
        Label status = new Label("Status");
        TextArea fieldSummary = new TextArea();
        fieldSummary.setEditable(false);
        Label total = new Label();
        addExpenses.setOnMouseClicked(event -> {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("expenses.txt",true));
                String line = "";
                if(!category.getText().trim().isEmpty() && !amount.getText().trim().isEmpty()){
                    line = line.concat(category.getText().trim());
                    line = line.concat("|");
                    double db;
                    try {
                        db = Double.parseDouble(amount.getText().trim());
                    }catch (NumberFormatException e){
                        status.setText("Please enter only number to Amount Field");
                        throw new NumberFormatException();
                    }
                    line = line.concat(Double.toString(db));
                    line = line.concat("|");
                }else{
                    status.setText("Amount or Category must be not empty");
                }
                line = line.concat(note.getText().trim());
                bw.write(line);
                bw.newLine();
                bw.close();
                status.setText("Saved");
                status.setTextFill(Color.GREEN);
            } catch (IOException e) {
                status.setText("Failed");
                status.setTextFill(Color.RED);
                throw new RuntimeException(e);
            }
        });

        showSummary.setOnMouseClicked(event -> {
            try {
                BufferedReader br = new BufferedReader(new FileReader("expenses.txt"));
                String line;
                double totalD = 0;
                String formatted = "";
                while((line = br.readLine()) != null){
                    String[] p = line.split("\\|");
                    String line2 = p[0]+"--- $"+p[1]+"("+p[2]+")";
                    formatted = formatted.concat(line2+"\n");
                    totalD+=Double.parseDouble(p[1]);
                }
                fieldSummary.setText(formatted);
                br.close();
                total.setText("Total: $"+ totalD);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        clearFields.setOnMouseClicked(event -> {
            category.clear();
            amount.clear();
            note.clear();
        });


        VBox root = new VBox(12);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
                form,
                addExpenses,
                status,
                showSummary,
                fieldSummary,
                total,
                clearFields);

        Scene scene = new Scene(root,460,420);
        stage.setTitle("Expense Tracker");
        stage.setScene(scene);
        stage.show();

    }
}