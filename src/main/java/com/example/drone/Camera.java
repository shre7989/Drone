package com.example.drone;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Camera{
    private DroneController drone;
    private boolean hasStorage;
    private Button storage;

    public Camera(DroneController droneController){
        this.drone = droneController;
        this.hasStorage = true;
        setupGUI();
    }

    public void setupGUI(){
        this.storage = new Button("Ok");
        this.storage.setPrefHeight(40);
        this.storage.setPrefWidth(120);
        this.storage.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,15));
        this.storage.setStyle("-fx-background-color: #08E023;" +
                "-fx-background-radius: 30px; " +
                "-fx-text-fill: black; " +
                "-fx-cursor: hand");
        this.storage.setEffect(new DropShadow(5, Color.BLACK));
        this.storage.setOnMouseClicked(event -> handleStorageEvent());
    }

    public void handleStorageEvent(){
        hasStorage = !hasStorage;

        //Handle the collision
        if(hasStorage){
            storage.setText("Ok");
            storage.setStyle("-fx-background-color: #08E023; " +
                    "-fx-text-fill: black; " +
                    "-fx-background-radius: 30px; "+
                    "-fx-cursor: hand");
        }
        else{
            storage.setText("Low");
            storage.setStyle("-fx-background-color: red; " +
                    "-fx-text-fill: white;" +
                    "-fx-background-radius: 30px; "+
                    "-fx-cursor: hand");
        }
    }
    public boolean isHasStorage() {
        return hasStorage;
    }

    public void captureImage(){
        if(hasStorage) drone.statusUpdate("Camera", "[ Event ]: storeImage(Image)");
        else drone.statusUpdate("Camera", "[ Event ]: insufficientStorage");
    }

    public Button getStorage(){
        return this.storage;
    }

}
