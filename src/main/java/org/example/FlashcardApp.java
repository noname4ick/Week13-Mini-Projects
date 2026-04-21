package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FlashcardApp extends Application {
    public boolean isFlipped = false;
    public int currentCard = 0;
    public void start(Stage stage){


        VBox root = new VBox(16);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Label text = new Label();
        text.setFont(Font.font("Consolas", FontWeight.BOLD,20));
        text.setWrapText(true);
        Label index = new Label();
        Button flip = new Button("Flip");
        Button next = new Button("Next");
        Button previous = new Button("Previous");

        ArrayList<String[]> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("cards.txt"));
            String line;
            while ((line = br.readLine()) != null){
            String[] p = line.split("\\|");
            list.add(p);
            }

        } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        root.getChildren().addAll(
                text,
                index,
                flip,
                next,
                previous
        );
        if(list.size() != 0){
        text.setText(list.get(currentCard)[0]);
        text.setStyle("-fx-background-color: #57a0d8");}
        else{
            text.setText("" +
                    "No cards found. Add cards to cards.txt" +
                    "and restart.");
            stage.close();
        }
        flip.setOnMouseClicked(event -> {
                if(isFlipped){
                    text.setText(list.get(currentCard)[0]);
                    text.setStyle("-fx-background-color: #57a0d8");
                }else{
                    text.setText(list.get(currentCard)[1]);
                    text.setStyle("-fx-background-color: #56b34b");
                }
                isFlipped = !isFlipped;

        });
        index.setText("Card" + (currentCard+1) + "/" + list.size());

        next.setOnMouseClicked(event -> {
            if(currentCard == list.size()-1) {
                currentCard = 0;
            }else{
                currentCard++;
                }
            text.setText(list.get(currentCard)[0]);
            isFlipped = false;
            index.setText("Card" + (currentCard+1) + "/" + list.size());
        });

        previous.setOnMouseClicked(event -> {
            if(currentCard == 0) {
                currentCard = list.size()-1;
            }
            else{
             currentCard--;
            }
            text.setText(list.get(currentCard)[0]);
            isFlipped = false;
            index.setText("Card" + (currentCard+1) + "/" + list.size());
        });

        Scene scene = new Scene(root,480,300);
        stage.setTitle("Flashcard Study App");
        stage.setScene(scene);
        stage.show();
    }
}
