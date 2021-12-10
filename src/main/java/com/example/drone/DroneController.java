package com.example.drone;

import com.example.drone.airframe.AirframeStatus;
import com.example.drone.radio.SignalHandler;
import com.example.drone.sensor.CollisionDetector;
import com.example.drone.state.DroneState;
import com.example.drone.state.Power;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

public class DroneController extends VBox {
    private final Drone.MotorController motorController;
    private final CollisionDetector collisionDetector;
    private final AirframeStatus airframeStatus;
    private final DroneState droneState;
    private final SignalHandler radio;
    private final Camera camera;
    private final Power power;
    private final Switch powerSwitch;
    private DroneTimer timer;
    private TextArea update;

    public DroneController(int spacing){
        super(spacing);
        this.motorController = new Drone.MotorController(this);
        this.collisionDetector = new CollisionDetector(this);
        this.airframeStatus = new AirframeStatus();
        this.camera = new Camera(this);
        this.power = new Power(this);
        this.droneState = new DroneState(this);
        this.radio = new SignalHandler(this);
        this.powerSwitch = new Switch(this);
        setup(this);
    }

    private void setup(VBox layout){
        // top label
        Label label = new Label("Drone Navigation System");
        label.setFont(Font.font("Impact", FontWeight.LIGHT, 30));
        label.setEffect(new InnerShadow(1, Color.WHITE));

        //top box for the switch button
        HBox topBox = new HBox(370);
        topBox.setAlignment(Pos.CENTER);
        topBox.setMinWidth(700);
        topBox.getChildren().addAll(label,powerSwitch);

        //left box
        update = new TextArea();
        update.setMaxWidth(500);
        update.setPrefHeight(300);
        update.setPadding(new Insets(10));
        update.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 12));
        update.setStyle("-fx-control-inner-background: black; " +
                "-fx-background-color: black;" +
                "-fx-text-fill: #08e023; " +
                "-fx-background-radius: 0px");


        HBox sensorBox = new HBox();
        sensorBox.setAlignment(Pos.CENTER);
        sensorBox.getChildren().addAll(airframeStatus,collisionDetector);

        VBox leftBox = new VBox(10);
        leftBox.setFillWidth(true);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.getChildren().addAll(update,sensorBox);

        //right box
        VBox rightBox = new VBox(20);
        rightBox.setFillWidth(true);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.getChildren().addAll(droneState);
        rightBox.setStyle("-fx-border-color: black; -fx-border-width: 0 1 1 1;");

        HBox mainBox = new HBox();
        mainBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(leftBox,rightBox);

        layout.setStyle("-fx-background-color: white");
        layout.setEffect(new DropShadow(10, Color.BLACK));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(topBox,mainBox);
    }

    public DroneState getDroneState(){
        return this.droneState;
    }

    public Drone.MotorController getMotorController(){
        return this.motorController;
    }

    public synchronized void statusUpdate(String from, String status){
        update.appendText("---------------------------------------------------------------------------------------------\n");
        update.appendText(">> " + from + "\n" + ">> " + status +"\n");
    }

    protected void startListening(){
        Thread radioThread = new Thread(radio);
        radioThread.start();
        statusUpdate("Drone","[ Update ]: searching for controller connection");
    }

    public void setTakeOff(){
        this.getDroneState().setTakeOff(true);
    }

    public void saveHomeState(String[] velocityVector){
        airframeStatus.saveHomeState(velocityVector);
    }

    public void updateCurrVector(String[] velocityVector){
        droneState.updateState("Vector",velocityVector);
    }

    public void startTimer() {
        this.timer = new DroneTimer(this);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer");
                timer.report();
            }
        },2000,2000);
    }

    /**
     * updateDronePosition - called every two seconds, to update the location using current vector
     */
    public void updateDronePosition() {
        ArrayList<Integer> currVector = droneState.getCurrVector();
        int x = currVector.get(0);
        int y = currVector.get(1);
        int z = currVector.get(2);

        //check collision directions: x,y, and -z axis
        //x-axis ->  left and right collision sensors
        if(collisionDetector.checkCollision("left")){
            if(x < 0) x = 0; //if left collision restrict motion
        }
        if(collisionDetector.checkCollision("right")){
            if(x > 0) x = 0;
        }

        //y-axis ->  front and back collision sensors
        if(collisionDetector.checkCollision("front")){
            if(y > 0) y = 0;
        }
        if(collisionDetector.checkCollision("back")){
            if(y < 0) y = 0;
        }

        // -z axis
        if(collisionDetector.checkCollision("down")){
            if(z < 0) z = 0;
        }

        //check altitude cap: positive z-axis
        if(airframeStatus.getCurrAltitude() + 2 * z >= 99) {
            z = 0;
        }
        else if(airframeStatus.getCurrAltitude() + 2 * z <= 0){
            z = 0;
        }

        //multiply  value by 2 to update value after 2 seconds
        airframeStatus.updatePosition(2 * x,2 * y, 2 * z);
    }

    public void autoMovement() {
        ArrayList<Integer> currVector = droneState.getCurrVector();
        ArrayList<Integer> location = airframeStatus.getCurrCoord();
        int xCoord = location.get(0);
        int yCoord = location.get(1);
        int zCoord = airframeStatus.getCurrAltitude();

        int x = currVector.get(0);
        int y = currVector.get(1);
        int z = currVector.get(2);

        //check if drone is in homeLocation
        if(inHomeLocation()) {
            String[] velocityVector = {"0","0","0"};

            statusUpdate("Drone", "[ Update ]: Drone is in home location");
            droneState.setAutoPilot(false);
            droneState.setTakeOff(false);
            updateCurrVector(velocityVector);
            timer.cancel();
            return;
        }
        //check collision directions: x,y, and -z axis
        //x-axis ->  left and right collision sensors
        if(collisionDetector.checkCollision("left")){
            if(x < 0) x = 0; //if left collision restrict motion
        }
        if(collisionDetector.checkCollision("right")){
            if(x > 0) x = 0;
        }

        //y-axis ->  front and back collision sensors
        if(collisionDetector.checkCollision("front")){
            if(y > 0) y = 0;
        }
        if(collisionDetector.checkCollision("back")){
            if(y < 0) y = 0;
        }

        // -z axis
        if(collisionDetector.checkCollision("bottom")){
            if(z < 0) z = 0;
        }

        //configure the vector if already in part of homeLocation
        if(xCoord == 0) x = 0;
        if(yCoord == 0) y = 0;
        if(zCoord == 0) z = 0;

        //z-axis movement
        airframeStatus.updatePosition(2 * x,2 * y, 2 * z);
    }

    public String[] getAutoMovementVector(){
        String[] velocityVector = new String[3];
        ArrayList<Integer> location = airframeStatus.getCurrCoord();
        int xCoord = location.get(0);
        int yCoord = location.get(1);

        //xCoord
        if(xCoord == 0) velocityVector[0] = "0";
        else if(xCoord < 0) velocityVector[0] = "1";
        else velocityVector[0] = "-1";

        //yCoord
        if(yCoord == 0) velocityVector[1] = "0";
        else if(yCoord < 0) velocityVector[1] = "1";
        else velocityVector[1] = "-1";

        //zCoord
        velocityVector[2] = "-1";  //altitude can never be less than 0
        return velocityVector;

    }

    public void updateCollisionDirection(String direction, String operation) {
        HashMap<String,Character> collisions = droneState.getCollisionDirections();
        Label collisionLabel = droneState.getLabel("CollisionDirection");

        if(operation.equals("detected")){
            //first letter of direction
            char dir = direction.toUpperCase().charAt(0);
            collisions.replace(direction,dir);
            collisionLabel.setText(collisions.get("front") + " "
                    + collisions.get("right") + " "
                    + collisions.get("back") + " "
                    + collisions.get("left") + " "
                    + collisions.get("down"));

        }
        else{
            collisions.replace(direction,'*');
            collisionLabel.setText(collisions.get("front") + " "
                    + collisions.get("right") + " "
                    + collisions.get("back") + " "
                    + collisions.get("left") + " "
                    + collisions.get("down"));
        }

    }

    private boolean inHomeLocation(){
        ArrayList<Integer> location = airframeStatus.getCurrCoord();
        int x = location.get(0);
        int y = location.get(1);
        int z = airframeStatus.getCurrAltitude();

        return x == 0 && y == 0 && z == 0;
    }

    public Camera getCamera(){
        return this.camera;
    }

    public Power getPower() {
        return power;
    }

    public SignalHandler getRadio() {
        return radio;
    }
}
