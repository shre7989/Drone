package com.example.drone.radio;

import com.example.drone.DroneController;
import javafx.application.Platform;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SignalHandler implements Runnable {
    private final DroneController drone;

    public SignalHandler(DroneController controller){
        this.drone = controller;
    }

    @Override
    public void run() {
        try{
            int portNo = 4242;
            ServerSocket droneSocket = new ServerSocket(portNo);

            Socket remoteControllerConnection = droneSocket.accept();
            while (true) {

                InputStreamReader input = new InputStreamReader(remoteControllerConnection.getInputStream());
                BufferedReader inputReader = new BufferedReader(input);

                String command = inputReader.readLine();
                
                receiveSignal(command);

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void receiveSignal(String command){
        if (command.equals("Remote Controller")) {
            drone.statusUpdate(command, "[ Update ] Remote Controller Connected!!");
        } else if (command.equals("Capture Image")) {
            drone.statusUpdate("Remote Controller","[ Event ] takePhoto");
            drone.getCamera().captureImage();
        } else if (command.equals("Return Home")) {
            drone.statusUpdate("Remote Controller", "[ Event ] goHome");
            Platform.runLater(new Runnable() {
                final String[] velocityVector = drone.getAutoMovementVector();
                @Override
                public void run() {
                    drone.getDroneState().setAutoPilot(true); //set autopilot to true
                    drone.getMotorController().moveDrone(velocityVector);
                }
            });
        } else if(!command.contains(",")) {
            drone.statusUpdate("Remote Controller", "[ Update ]: make a " + command + "turn");
            Platform.runLater(() -> drone.getDroneState().changeDirection(command));
        }
        else {
            if(drone.getPower().isHasBattery()) {
                drone.statusUpdate("Remote Controller", "[Event] adjustCurrentVector(" + command + ")");
                String[] velocityVector = command.split(",");
                Platform.runLater(() -> drone.getMotorController().moveDrone(velocityVector));
            }
        }
    }

    public void sendSignal(){
        drone.statusUpdate("Remote Controller", "[ Event ] goHome");
        Platform.runLater(new Runnable() {
            final String[] velocityVector = drone.getAutoMovementVector();
            @Override
            public void run() {
                drone.getDroneState().setAutoPilot(true); //set autopilot to true
                drone.getMotorController().moveDrone(velocityVector);
            }
        });
    }
}
