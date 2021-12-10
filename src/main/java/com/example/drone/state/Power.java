package com.example.drone.state;

import com.example.drone.DroneController;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Power {
    private final DroneController drone;
    private boolean hasBattery;
    private Button battery;

    public Power(DroneController droneController){
        this.drone = droneController;
        this.hasBattery = true;
        setupGUI();
    }

    public void setupGUI(){
        this.battery = new Button("Ok");
        this.battery.setPrefHeight(40);
        this.battery.setPrefWidth(120);
        this.battery.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,15));
        this.battery.setStyle("-fx-background-color: #08E023;" +
                "-fx-background-radius: 30px; " +
                "-fx-text-fill: black; " +
                "-fx-cursor: hand");
        this.battery.setEffect(new DropShadow(5, Color.BLACK));
        this.battery.setOnMouseClicked(event -> handleBatteryEvent());
    }

    public void handleBatteryEvent(){
        hasBattery = !hasBattery;

        //Handle the collision
        if(hasBattery){
            drone.statusUpdate("Battery", "[ Event ]: okBattery");
            battery.setText("Ok");
            battery.setStyle("-fx-background-color: #08E023; " +
                    "-fx-text-fill: black; " +
                    "-fx-background-radius: 30px; "+
                    "-fx-cursor: hand");
        }
        else{
            drone.statusUpdate("Battery", "[ Event ]: lowBattery");
            drone.getRadio().sendSignal();
            battery.setText("Low");
            battery.setStyle("-fx-background-color: red; " +
                    "-fx-text-fill: white;" +
                    "-fx-background-radius: 30px; "+
                    "-fx-cursor: hand");
        }
    }
    public boolean isHasBattery() {
        return hasBattery;
    }

    public Button getBattery(){
        return battery;
    }

}
