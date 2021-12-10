package com.example.drone.remotecontroller;

import com.example.drone.remotecontroller.RemoteController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.net.Socket;

public class RemoteControllerGUI extends VBox {
    private final RemoteController remote;
    private Socket radio;
    private TextField setVectorTextField;
    private TextField changeDirectionField;

    public RemoteControllerGUI(RemoteController remoteController){
        super(20);
        this.remote  = remoteController;
        setupGUI();
    }

    public void setSocket(Socket radio) {
        this.radio = radio;
    }

    public void setupGUI(){
        Label label = new Label("Remote Controller");
        label.setFont(Font.font("Impact", FontWeight.LIGHT, 25));
        label.setEffect(new InnerShadow(1, Color.WHITE));

        this.setVectorTextField = new TextField("");
        this.setVectorTextField.minHeight(40);
        this.setVectorTextField.maxWidth(120);

        Button setVectorButton = new Button("Set Vector");
        setVectorButton.setPrefHeight(40);
        setVectorButton.setPrefWidth(120);
        setVectorButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD,15));
        setVectorButton.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 30px; " +
                "-fx-text-fill: white; " +
                "-fx-cursor: hand");
        setVectorButton.setEffect(new DropShadow(5, Color.BLACK));
        setVectorButton.setOnMouseClicked(event -> {
            remote.setCurrVector(setVectorTextField.getText(),this.radio);
            setVectorTextField.clear();
        });
        
        HBox topBOx = new HBox(10);
        topBOx.setAlignment(Pos.CENTER);
        topBOx.getChildren().addAll(setVectorTextField, setVectorButton);

        this.changeDirectionField = new TextField("");
        this.changeDirectionField.minHeight(40);
        this.changeDirectionField.maxWidth(120);

        Button changeDirection = new Button("Turn");
        changeDirection.setPrefHeight(40);
        changeDirection.setPrefWidth(120);
        changeDirection.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD,15));
        changeDirection.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 30px; " +
                "-fx-text-fill: white; " +
                "-fx-cursor: hand");
        changeDirection.setEffect(new DropShadow(5, Color.BLACK));
        changeDirection.setOnMouseClicked(event -> {
            remote.setCurrVector(changeDirectionField.getText(),this.radio);
            setVectorTextField.clear();
        });

        HBox topBOx2 = new HBox(10);
        topBOx2.setAlignment(Pos.CENTER);
        topBOx2.getChildren().addAll(changeDirectionField, changeDirection);

        Button returnHomeButton = new Button("Go Home");
        returnHomeButton.setPrefHeight(40);
        returnHomeButton.setPrefWidth(120);
        returnHomeButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD,15));
        returnHomeButton.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 30px; " +
                "-fx-text-fill: white; " +
                "-fx-cursor: hand");
        returnHomeButton.setEffect(new DropShadow(5, Color.BLACK));
        returnHomeButton.setOnMouseClicked(event -> remote.returnHome(this.radio));

        Button captureImageButton = new Button("Capture");
        captureImageButton.setPrefHeight(40);
        captureImageButton.setPrefWidth(120);
        captureImageButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD,15));
        captureImageButton.setStyle("-fx-background-color: black;" +
                "-fx-background-radius: 30px; " +
                "-fx-text-fill: white; " +
                "-fx-cursor: hand");
        captureImageButton.setEffect(new DropShadow(5, Color.BLACK));
        captureImageButton.setOnMouseClicked(event -> remote.captureImage(radio));

        this.getChildren().addAll(label,topBOx,returnHomeButton, captureImageButton);
        this.setAlignment(Pos.CENTER);

    }

}
