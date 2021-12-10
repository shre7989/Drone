package com.example.drone.airframe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class GPS{
    ArrayList<Integer> currLocation;
    Label locationLabel;
    Label nameLabel;

    public GPS(){
        this.currLocation = new ArrayList<>();

        //init coordinates
        this.currLocation.add(0);
        this.currLocation.add(0);

        setupGUI();
    }

    public void setupGUI(){
        this.nameLabel = new Label("CurrLocation:");
        this.nameLabel.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        this.nameLabel.setStyle("-fx-text-fill: black;");

        locationLabel = new Label("(0,0)");
        locationLabel.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        locationLabel.setPadding(new Insets(2));
        locationLabel.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 5px;" +
                "-fx-text-fill: #08e023");
        locationLabel.setAlignment(Pos.CENTER);
        locationLabel.setPrefHeight(40);
        locationLabel.setPrefWidth(120);

    }

    public synchronized void updateCurrLocation(int x, int y){
        int xUpdated = x + currLocation.get(0);
        currLocation.set(0,xUpdated);

        int yUpdated = y + currLocation.get(1);
        currLocation.set(1,yUpdated);

        locationLabel.setText("(" + xUpdated + "," + yUpdated + ")");
    }

    protected ArrayList<Integer> getCurrLocation(){
        return this.currLocation;
    }

    protected Label getLocationLabel(){
        return this.locationLabel;
    }

    public Label getNameLabel(){
        return this.nameLabel;
    }
}
