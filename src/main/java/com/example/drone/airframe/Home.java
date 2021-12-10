package com.example.drone.airframe;

import com.example.drone.airframe.AirframeStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class Home {
    private AirframeStatus airframeStatus;
    private ArrayList<Integer> homeLocation;
    private boolean homeSet;
    private int homeAltitude;
    private Label nameLocation;
    private Label nameAltitude;
    private Label houseLocation;
    private Label houseAltitude;

    public Home(AirframeStatus airframeStatus){
        this.airframeStatus = airframeStatus;
        this.homeLocation = new ArrayList<>();
        this.homeAltitude = 0;
        this.homeSet = false;

        setupGUI();
    }

    public void setupGUI(){
        int labelHeight = 40;
        int labelWidth = 120;

        this.nameAltitude = new Label("HomeAltitude:");
        this.nameAltitude.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        this.nameAltitude.setStyle("-fx-text-fill: black;");


        this.nameLocation = new Label("HomeLocation:");
        this.nameLocation.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        this.nameLocation.setStyle("-fx-text-fill: black;");


        houseAltitude = new Label("");
        houseAltitude.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        houseAltitude.setPadding(new Insets(2));
        houseAltitude.setAlignment(Pos.CENTER);
        houseAltitude.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 5px;" +
                "-fx-text-fill: #08e023;");

        houseLocation = new Label("");
        houseLocation.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        houseLocation.setPadding(new Insets(2));
        houseLocation.setAlignment(Pos.CENTER);
        houseLocation.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 5px;" +
                "-fx-text-fill: #08e023;");


        this.nameAltitude.setPrefHeight(labelHeight);
        this.nameAltitude.setPrefWidth(labelWidth);
        this.nameLocation.setPrefHeight(labelHeight);
        this.nameLocation.setPrefWidth(labelWidth);

        this.houseAltitude.setPrefHeight(labelHeight);
        this.houseAltitude.setPrefWidth(120);
        this.houseLocation.setPrefHeight(labelHeight);
        this.houseLocation.setPrefWidth(120);
    }

    public void setHome(){
        if(!homeSet) {
            homeSet = true;

            int x = airframeStatus.getCurrCoord().get(0);
            int y = airframeStatus.getCurrCoord().get(1);
            int alt = airframeStatus.getCurrAltitude();

            this.homeLocation.add(0, x);
            this.homeLocation.add(1, y);
            this.homeAltitude = alt;
            houseLocation.setText("(" + x + "," + y + ")");
            houseAltitude.setText("" + homeAltitude);
        }
    }


    public int getHomeAltitude() {
        return homeAltitude;
    }

    public ArrayList<Integer> getHomeLocation(){
        return this.homeLocation;
    }

    public Label getHouseAltitude() {
        return houseAltitude;
    }

    public Label getHouseLocation() {
        return houseLocation;
    }

    public Label getNameAltitude() {
        return nameAltitude;
    }

    public Label getNameLocation() {
        return nameLocation;
    }

    public void saveHomeAltitude(int altitude){
        homeAltitude = altitude;
        houseAltitude.setText("" + altitude);
    }

    public void saveHomeLocation(int x, int y){
        //Save homeLocation
        homeLocation.clear();
        homeLocation.add(x);
        homeLocation.add(y);
        houseLocation.setText("(" + x + "," + y + ")");
    }
}
