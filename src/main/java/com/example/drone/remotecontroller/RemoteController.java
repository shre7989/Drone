package com.example.drone.remotecontroller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class RemoteController extends Application {
    @Override
    public void start(Stage stage) {
        RemoteControllerGUI remoteController = new RemoteControllerGUI(this);

        Scene scene = new Scene(remoteController, 300, 300);
        stage.setTitle("Remote Controller");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        try {
            Inet4Address address = (Inet4Address) InetAddress.getByName("10.88.152.121");
            Socket radio = new Socket(address, 4242);

            remoteController.setSocket(radio);

            PrintWriter writer = new PrintWriter(radio.getOutputStream(), true);
            writer.println("Remote Controller");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void setCurrVector(String vector,Socket radio){
        try {
            PrintWriter writer = new PrintWriter(radio.getOutputStream(),true);
            writer.println(vector);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void changeDirection(String turn, Socket radio){
        try {
            PrintWriter writer = new PrintWriter(radio.getOutputStream(),true);
            writer.println(turn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void captureImage(Socket radio){
        try {
            PrintWriter writer = new PrintWriter(radio.getOutputStream(),true);
            writer.println("Capture Image");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnHome(Socket radio){
        try {
            PrintWriter writer = new PrintWriter(radio.getOutputStream(),true);
            writer.println("Return Home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


