package com.application.parser.interf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * 
 * This is the primary interface for the application
 * it handles the application window and sends files to be handled in FileHandler
 *
 */

public class MainApp extends Application{
    
    //launch javafx
	public static void main(String[] args) {

		launch(args);
		
	}
   
	//create the scene
    @Override
	public void start(Stage stage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		//creating scene
    	Scene scene = new Scene(root);
    	
    	//setting the icon and name
		Image icon = new Image("icon.png");
		stage.getIcons().add(icon);
		stage.setTitle("XML - XSL Converter");
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
		//should be on only while app is doing a job
		stage.setOnCloseRequest(event ->{
			event.consume();
			handleCloseButtonAction(stage);
		});
    }
    
    public void handleCloseButtonAction(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Quit?");
        alert.setHeaderText("Are you sure you want to quit?");
        if(alert.showAndWait().get() == ButtonType.OK) {
        	stage.close();
        }

    }
}
