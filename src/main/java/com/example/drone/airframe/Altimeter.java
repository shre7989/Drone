package com.example.drone.airframe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Altimeter {
    private int currAltitude;
    private Label nameLabel;
    private Label altitudeLabel;


    public Altimeter(){
        this.currAltitude = 0;

        setupGUI();
    }

    public void setupGUI(){
        int labelHeight = 40;
        int labelWidth = 120;

        this.nameLabel = new Label("CurrAltitude:");
        this.nameLabel.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        this.nameLabel.setStyle("-fx-text-fill: black;");

        altitudeLabel = new Label(""+ currAltitude);
        altitudeLabel.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        altitudeLabel.setPadding(new Insets(2));
        altitudeLabel.setAlignment(Pos.CENTER);
        altitudeLabel.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 5px;" +
                "-fx-text-fill: #08e023;");


        this.nameLabel.setPrefHeight(labelHeight);
        this.nameLabel.setPrefWidth(labelWidth);
        this.altitudeLabel.setPrefHeight(labelHeight);
        this.altitudeLabel.setPrefWidth(120);
    }

    public int getCurrAltitude(){
        return this.currAltitude;
    }

    public void updateCurrAltitude(int alt){
        currAltitude = alt + currAltitude;
        altitudeLabel.setText("" + currAltitude);
    }

    public Label getAltitudeLabel() {
        return altitudeLabel;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

}
