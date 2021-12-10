package com.example.drone;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Drone extends Application {
    @Override
    public void start(Stage stage){
        DroneController drone = new DroneController(20);
        Scene scene = new Scene(drone, 800, 750);
        stage.setTitle("Drone");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }

    public static class MotorController {
        private DroneController drone;
        public MotorController(DroneController droneController){
            this.drone = droneController;
        }

        public void moveDrone(String[] velocityVector){
            //check takeoff condition
            boolean takeoff = drone.getDroneState().getTakeOff();
            boolean autoPilot = drone.getDroneState().isAutoPilot();
            if(!autoPilot) {
                if (!takeoff) {
                    drone.setTakeOff(); //set takeoff
                    drone.saveHomeState(velocityVector); // save the location of the takeoff point
                    drone.updateCurrVector(velocityVector);// set the curr velocity vector
                    drone.startTimer();
                    //start timer
                } else {
                    drone.updateCurrVector(velocityVector);
                }
            }
            else{
                drone.updateCurrVector(velocityVector);
            }
        }



    }
}