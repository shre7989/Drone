package com.example.drone.state;

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
import java.util.Arrays;
import java.util.HashMap;

public class DroneState extends VBox {
    private ArrayList<Label> labels;
    private Boolean autoPilot;
    private Boolean takeOff;
    private Boolean storage;
    private ArrayList<Integer> currVector;
    private HashMap<String,Character> collisionDirections;
    private String[] cardinalDirections;
    private int directionIndex;
    private DroneController drone;

    public DroneState(DroneController drone){
        this.drone = drone;
        this.autoPilot = false;
        this.takeOff = false;
        this.storage = true;
        this.directionIndex = 0;
        this.cardinalDirections = new String[]{"NORTH", "EAST", "SOUTH", "WEST"};
        this.currVector = new ArrayList<>(Arrays.asList(0,0,0));
        this.collisionDirections = new HashMap<>(){{
           put("front",'*');
           put("right",'*');
           put("back",'*');
           put("left",'*');
           put("down",'*');
        }};
        setupGUI();
    }

    private void setupLabels(){
        int height = 40;

        Label autoPilot = new Label("Manual");
        autoPilot.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        autoPilot.setPrefHeight(height);
        autoPilot.setPrefWidth(120);
        autoPilot.setAlignment(Pos.CENTER);
        autoPilot.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 5px; " +
                "-fx-text-fill: #08E023;");
        labels.add(autoPilot);

        Label currVector = new Label("(0,0,0)");
        currVector.setAlignment(Pos.CENTER);
        currVector.setPrefHeight(height);
        currVector.setPrefWidth(120);
        currVector.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        currVector.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 5px; " +
                "-fx-text-fill: #08e023;");
        labels.add(currVector);

        Label collisionDirections = new Label("* * * * *");
        collisionDirections.setAlignment(Pos.CENTER);
        collisionDirections.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        collisionDirections.setPrefHeight(height);
        collisionDirections.setPrefWidth(120);
        collisionDirections.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 5px; " +
                "-fx-text-fill: #08e023;");
        labels.add(collisionDirections);

        Label direction = new Label(cardinalDirections[directionIndex]);
        direction.setAlignment(Pos.CENTER);
        direction.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        direction.setPrefHeight(height);
        direction.setPrefWidth(120);
        direction.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 5px; " +
                "-fx-text-fill: #08e023;");
        labels.add(direction);

    }
    private void setupGUI(){
        int space = 10;

        labels = new ArrayList<>();

        Label mainLabel = new Label("State");
        mainLabel.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 20));
        mainLabel.setPadding(new Insets(space));
        mainLabel.setPrefWidth(250);
        mainLabel.setEffect(new DropShadow(5,Color.BLACK));
        mainLabel.setStyle("-fx-background-color: white; " +
                "-fx-border-color: black;" +
                "-fx-border-width: 0 0 0 0;" +
                "-fx-text-fill: black");
        labels.add(mainLabel);

        setupLabels();

        Label autoPilotLabel = new Label("AutoPilot:");
        Label batteryLabel = new Label("Battery:");
        Label currVectorLabel = new Label("CurrVector:");
        Label collisionDirectionLabel = new Label("Collisions:");
        Label directionLabel = new Label("Direction:");
        Label storageLabel = new Label("Storage:");

        ArrayList<Label> nameLabels = new ArrayList<>();
        nameLabels.add(autoPilotLabel);
        nameLabels.add(batteryLabel);
        nameLabels.add(currVectorLabel);
        nameLabels.add(directionLabel);
        nameLabels.add(collisionDirectionLabel);
        nameLabels.add(storageLabel);

        setupLabelStyle(nameLabels);

        GridPane layout = new GridPane();

        //rows
        layout.addRow(0,labels.get(0));
        layout.addRow(1,autoPilotLabel,labels.get(1));
        layout.addRow(2,currVectorLabel,labels.get(2));
        layout.addRow(3,collisionDirectionLabel,labels.get(3));
        layout.addRow(4,batteryLabel,drone.getPower().getBattery());
        layout.addRow(5,storageLabel,drone.getCamera().getStorage());

        layout.setHgap(space);
        layout.setVgap(60);
        layout.setAlignment(Pos.CENTER);

        this.setStyle("-fx-padding: 0 0 80 0;");
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(mainLabel,layout);

    }

    public void updateState(String state, Object value){
        if(state.equals("Vector")){
            String [] updatedValue = (String[]) value;
            int x = Integer.parseInt(updatedValue[0]);
            int y = Integer.parseInt(updatedValue[1]);
            int z = Integer.parseInt(updatedValue[2]);

            //update the current vector
            currVector.set(0,x);
            currVector.set(1,y);
            currVector.set(2,z);

            //reflect the change in the current vector label
            labels.get(2).setText("(" + x + "," + y + "," + z + ")");
        }
    }

    public void setTakeOff(boolean value){
        this.takeOff = value;
    }

    private void setupLabelStyle(ArrayList<Label> labels){
        for(Label label: labels){
            label.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,15));
            label.setStyle("-fx-text-fill: black;");
            label.setPrefHeight(30);
        }
    }

    public Label getLabel(String labelName){
        switch (labelName) {
            case "AutoPilot":
                return labels.get(1);
            case "CurrVector":
                return labels.get(2);
            case "CollisionDirection":
                return labels.get(3);
        }
        return null;
    }
    public Boolean getStorage() {
        return storage;
    }

    public Boolean getTakeOff() {
        return takeOff;
    }

    public ArrayList<Integer> getCurrVector(){
        return this.currVector;
    }

    public HashMap<String, Character> getCollisionDirections(){
        return this.collisionDirections;
    }

    public void setAutoPilot(boolean value){
        if(value) labels.get(1).setText("Auto");
        else labels.get(1).setText("Manual");
        this.autoPilot = value;
    }

    public boolean isAutoPilot() {
        return this.autoPilot;
    }

    public void changeDirection(String turn){
        if(turn.equals("left")){

            directionIndex = directionIndex - 1;
            if(directionIndex < 0) directionIndex = 3;
            labels.get(4).setText(cardinalDirections[directionIndex]);
        }
        else if(turn.equals("right")){
            directionIndex = directionIndex + 1;
            if(directionIndex > 3) directionIndex = 0;
            labels.get(4).setText(cardinalDirections[directionIndex]);
        }
    }
}
