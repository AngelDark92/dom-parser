package com.application.parser.interf;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.application.parser.handlers.FileHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 /**
  * 
  * @author Angelo
  *	Controller for javafx FXML interface
  *
  */

public class Controller implements Initializable{

	@FXML
	Button quitButton;
	@FXML
	Button selectFile;
	@FXML
	Button selectFolder;
	@FXML
	Button convert;
	@FXML
	Button batchConvert;
	@FXML
	MenuItem menuQuit;
	@FXML
	MenuItem selectFileMenu;
	@FXML
	MenuItem selectFolderMenu;
	@FXML
	MenuItem reset;
	@FXML
	MenuItem convertMenu;
	@FXML
	MenuItem batchConvertMenu;
	@FXML
	MenuItem about;
	@FXML
	TextField singleFilePath;
	@FXML
	TextField folderPath;
	@FXML
	TextArea txtArea;
	
	public static TextArea publicTxtArea;

	final FileChooser fileChooser = new FileChooser();
	final DirectoryChooser directoryChooser = new DirectoryChooser();


	Stage stage;

	// close application on quit from menu or button
	@FXML
	public void handleCloseButtonAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quit?");
		alert.setHeaderText("Are you sure you want to quit?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			stage = (Stage) quitButton.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	//Shows file selection dialogue
	public void showFileSelect(ActionEvent event) {
		File file = fileChooser.showOpenDialog(stage);
		if(file!=null)
			singleFilePath.setText(file.getAbsolutePath());
	}

	@FXML
	//Shows folder selection dialogue
	public void showFolderSelect(ActionEvent event) {
		File folder = directoryChooser.showDialog(stage);
		if(folder!=null)
			folderPath.setText(folder.getAbsolutePath());
	}

	@FXML
	public void convert(ActionEvent event) {
		File fileToConv = new File(singleFilePath.getText());
		
		if(fileToConv.exists() && fileToConv.canRead() && fileToConv.getAbsolutePath().endsWith(".xml")) {	
			//assign file to files[] so it can be processed with only one method for both folder and files
			List<File> files = new ArrayList<>();
			files.add(fileToConv);
    		FileHandler.processFiles(files);

		}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Wrong File");
    		alert.setHeaderText("Wrong filename, extension or path.");
    		alert.setContentText("Please select a valid .xml file.");
    		alert.show();
    	}
	}

	@FXML
	public void batchConvert(ActionEvent event) {
		//creates a File that points to a folder
		File folderConvert = new File(folderPath.getText());
		
		//create a list to send to the processer
		List<File> files = new ArrayList<>();
		
		//check if folder contains .xml files
		File[] javaFiles = folderConvert.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".xml");
			}
		});

		
		if (folderConvert.isDirectory() && folderPath.getText() != null && javaFiles.length>0) {
			//add files[] to list
			for(File file : javaFiles){
				files.add(file);
			}
			FileHandler.processFiles(files);
			} 
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Folder Error");
			alert.setHeaderText("Wrong folder name or no path.");
    		alert.setContentText("Please select a valid folder containing .xml files.");
    		alert.show();
		}
	}
	
	@FXML
	public void reset(ActionEvent event) {
		singleFilePath.setText(null);
		folderPath.setText(null);
	}
	

	
	@FXML
	public void about(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("Program Created by Angelo Pascuzzi");
		alert.setContentText("Selects xml files or folder of .xml files "
								+ "pointing to their respective .xslt or having the same name, and converts them.");
		alert.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//initializing the txt area to make it public and static
		Controller.publicTxtArea = txtArea;
		
	}

}
