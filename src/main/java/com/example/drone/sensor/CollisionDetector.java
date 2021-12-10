package com.example.drone.sensor;

import com.example.drone.DroneController;
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

public class CollisionDetector extends VBox{
    private DroneController drone;
    private CollisionSensor front;
    private CollisionSensor right;
    private CollisionSensor back;
    private CollisionSensor left;
    private CollisionSensor down;

    public CollisionDetector(DroneController droneController){
        this.drone = droneController;
        setupSensors();
        setupGUI();
    }

    private void setupSensors(){
        this.front = new CollisionSensor(this, "front");
        this.right = new CollisionSensor(this, "right");
        this.back = new CollisionSensor(this, "back");
        this.left = new CollisionSensor(this, "left");
        this.down = new CollisionSensor(this,"down");
    }

    private void setupGUI(){
        int space = 10;

        GridPane layout = new GridPane();

        Label mainLabel = new Label("Collision Detector");
        mainLabel.setFont(Font.font("Helvetica", FontWeight.MEDIUM,18));
        mainLabel.setPadding(new Insets(space));
        mainLabel.setEffect(new DropShadow(5, Color.BLACK));
        mainLabel.setPrefWidth(200);
        mainLabel.setStyle("-fx-background-color: white; " +
                "-fx-border-radius: 5px; " +
                "-fx-border-width: 0 0 0 0;" +
                "-fx-text-fill: black");
        VBox mainLabelBox = new VBox();
        mainLabelBox.getChildren().add(mainLabel);

        //labels for sensors
        Label frontLabel = new Label("Front:");
        Label rightLabel = new Label("Right:");
        Label backLabel = new Label("Back:");
        Label leftLabel = new Label("Left:");
        Label downLabel = new Label("Down:");

        ArrayList<Label> labels = new ArrayList<>();
        labels.add(frontLabel);
        labels.add(rightLabel);
        labels.add(backLabel);
        labels.add(leftLabel);
        labels.add(downLabel);

        setupLabelStyle(labels);

        // Laying out rows in the grid
        layout.addRow(0,mainLabel);
        layout.addRow(1,frontLabel,front);
        layout.addRow(2,rightLabel,right);
        layout.addRow(3,backLabel,back);
        layout.addRow(4,leftLabel,left);
        layout.addRow(5,downLabel,down);

        //style for gridpane
        layout.setHgap(space);
        layout.setVgap(space);
        layout.setPadding(new Insets(space));
        layout.setAlignment(Pos.CENTER);

        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 1;" + "-fx-padding: 0 0 1 0");
        this.getChildren().addAll(mainLabel,layout);
    }

    private void setupLabelStyle(ArrayList<Label> labels){
        for(Label label: labels){
            label.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,15));
            label.setPrefHeight(30);
            label.setStyle("-fx-text-fill: black;");
        }
    }

    public void notifyHorizontalTerrain(String direction){
        drone.statusUpdate("Collision Detector", "[ Event ] periferalCollision -> " + direction);
        drone.updateCollisionDirection(direction,"detected");
    }

    public void notifyVerticalTerrain(){
        drone.statusUpdate("Collision Detector", "[ Event ] verticalCollision -> down");
        drone.updateCollisionDirection("down","detected");
    }

    public void clearCollision(String direction){
        drone.statusUpdate("Collision Detector", "[ Event ] collisionCleared -> " + direction);
        drone.updateCollisionDirection(direction,"cleared");
    }

    public boolean checkCollision(String t) {
        switch (t) {
            case "front":
                return front.isCollisionDetected();
            case "right":
                return right.isCollisionDetected();
            case "back":
                return back.isCollisionDetected();
            case "left":
                return left.isCollisionDetected();
            case "down":
                return down.isCollisionDetected();
        }
        return false;
    }
}
