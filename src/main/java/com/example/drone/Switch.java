package com.example.drone;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Switch extends HBox {
    public boolean isOn;
    public DroneController drone;
    public Button powerSwitch;

    public Switch(DroneController drone){
        this.isOn = false;
        this.drone = drone;
        this.powerSwitch = new Button("");
        this.setupStyle();
        this.powerSwitch.setOnMouseClicked(event -> toggleSwitch());
    }
    public void setupStyle(){
        powerSwitch.setPrefHeight(35);
        powerSwitch.setPrefWidth(35);
        powerSwitch.setStyle("-fx-background-color: white;" +
                "-fx-background-radius: 35px; " +
                "-fx-border-radius: 35px;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2 2 2 2;" +
                "-fx-text-fill: white; " +
                "-fx-cursor: hand");

        this.setMaxHeight(40);
        this.setMinWidth(70);
        this.setMaxWidth(70);
        this.getChildren().addAll(powerSwitch);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: #CBCED1;" +
                "-fx-background-radius: 40px; " +
                "-fx-border-radius: 40px;" +
                "-fx-border-color: #CBCED1;" +
                "-fx-border-width: 2 2 2 2;" +
                "-fx-text-fill: white; ");

    }
    public void toggleSwitch(){
        boolean takeOff = drone.getDroneState().getTakeOff();
        if(!takeOff){
            if(!isOn) {
                isOn = true;
                drone.statusUpdate("Switch","[ Event ] Power ON");
                drone.startListening();
                this.setAlignment(Pos.CENTER_RIGHT);
                this.setStyle("-fx-background-color: #29A6D0;" +
                        "-fx-background-radius: 40px; " +
                        "-fx-border-radius: 40px;" +
                        "-fx-border-color: #29A6D0;" +
                        "-fx-border-width: 2 2 2 2;" +
                        "-fx-text-fill: white; ");

            } else{
                isOn = false;
                drone.statusUpdate("Switch","[ Event ] Power OFF");
                this.setAlignment(Pos.CENTER_LEFT);
                this.setStyle("-fx-background-color: #CBCED1;" +
                        "-fx-background-radius: 40px; " +
                        "-fx-border-radius: 40px;" +
                        "-fx-border-color: #CBCED1;" +
                        "-fx-border-width: 2 2 2 2;" +
                        "-fx-text-fill: white; ");
            }

        }
    }
}
