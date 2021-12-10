package com.example.drone.sensor;

import com.example.drone.sensor.CollisionDetector;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CollisionSensor extends Button {
    private CollisionDetector detector;
    private boolean collisionDetected;
    private String direction;

    public CollisionSensor(CollisionDetector detector,String sensorDirection){
        super("Clear");
        this.detector = detector;
        this.direction = sensorDirection;
        this.collisionDetected = false;
        this.setupStyle();
        this.setOnMouseClicked(event -> handleCollisionEvent());
    }

    private void setupStyle(){
        this.setPrefHeight(40);
        this.setPrefWidth(120);
        this.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD,15));
        this.setStyle("-fx-background-color: #08E023;" +
                "-fx-background-radius: 30px; " +
                "-fx-text-fill: white; " +
                "-fx-cursor: hand");
        this.setEffect(new DropShadow(5, Color.BLACK));
    }

    private void handleCollisionEvent(){

        this.collisionDetected = !collisionDetected; // Change collision status

        //Handle the collision
        if(collisionDetected){
            if(direction.equals("down")) detector.notifyVerticalTerrain();
            else detector.notifyHorizontalTerrain(direction);
            this.setText("Detected");
            this.setStyle("-fx-background-color: red; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 30px; "+
                    "-fx-cursor: hand");
        }
        else{
            detector.clearCollision(direction);
            this.setText("Clear");
            this.setStyle("-fx-background-color: #08E023; " +
                    "-fx-text-fill: white;" +
                    "-fx-background-radius: 30px; "+
                    "-fx-cursor: hand");
        }
    }

    public String getDirection(){
        return this.direction;
    }

    public boolean isCollisionDetected(){
        return this.collisionDetected;
    }
}
