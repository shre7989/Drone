package com.example.drone.airframe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class AirframeStatus extends VBox {
    public Home home;
    public GPS gps;
    public Altimeter altimeter;

    public AirframeStatus(){
        this.home = new Home(this);
        this.gps = new GPS();
        this.altimeter = new Altimeter();

        setupGUI();
    }

    public void setupGUI(){
        double space = 19;

        Label mainLabel = new Label("Airframe Status");
        mainLabel.setFont(Font.font("Helvetica", FontWeight.MEDIUM,18));
        mainLabel.setPadding(new Insets(10));
        mainLabel.setEffect(new DropShadow(5, Color.BLACK));
        mainLabel.setStyle("-fx-background-color: white; " +
                "-fx-border-color: black;" +
                "-fx-border-width: 0 0 0 0;" +
                "-fx-text-fill: black");
        mainLabel.setPrefWidth(300);

        GridPane layout = new GridPane();

        layout.addRow(0,mainLabel);
        layout.addRow(1,home.getNameLocation(), home.getHouseLocation());
        layout.addRow(2,home.getNameAltitude(), home.getHouseAltitude());
        layout.addRow(3,gps.getNameLabel(), gps.getLocationLabel());
        layout.addRow(4, altimeter.getNameLabel(), altimeter.getAltitudeLabel());

        layout.setHgap(space);
        layout.setVgap(space);
        layout.setPadding(new Insets(space));
        layout.setAlignment(Pos.CENTER);


        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 1");
        this.getChildren().addAll(mainLabel,layout);

    }

    public ArrayList<Integer> getCurrCoord(){
        return this.gps.getCurrLocation();
    }

    public int getCurrAltitude(){
        return this.altimeter.getCurrAltitude();
    }

    /**
     * saveHomeState - reads the takeoff location and sets the homeLocation and homeAltitude for the drone
     * @param currVector - the 3d-vector of the takeoff point
     */
    public void saveHomeState(String[] currVector){

        //save homeLocation and homeAltitude
        home.saveHomeLocation(0,0);
        home.saveHomeAltitude(0);
    }

    public void updatePosition(int x, int y, int z){
        gps.updateCurrLocation(x,y);
        altimeter.updateCurrAltitude(z);
    }


}
