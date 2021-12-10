package com.example.drone;

import javafx.application.Platform;

import java.util.Timer;

public class DroneTimer extends Timer {
    private DroneController drone;

    public DroneTimer(DroneController drone){
        super();
        this.drone  = drone;
    }

    public synchronized void report(){
        Platform.runLater(() -> {
            boolean autoPilot = drone.getDroneState().isAutoPilot();

            if(autoPilot) {
                drone.autoMovement();
            }
            else drone.updateDronePosition();
        });
    }
}
