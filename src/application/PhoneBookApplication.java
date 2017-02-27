package application;

import java.io.*;
import java.util.Locale;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import phonebook.MapPhoneBook;
import phonebook.PhoneBook;

public class PhoneBookApplication extends Application{
	private PhoneBook phoneBook;
	private NameListView nameListView;
	private Stage mainStage;

	/**
	 * The entry point for the Java program.
	 * @param args
	 */
	public static void main(String[] args) {	
		// launch() do the following:
		// - creates an instance of the Main class
		// - calls Main.init()
		// - create and start the javaFX application thread
		// - waits for the javaFX application to finish (close all windows)
		// the javaFX application thread do:
		// - calls Main.start(Stage s)
		// - runs the event handling loop
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		phoneBook = new MapPhoneBook();

		//läser in phonebook från fil
		if(Dialogs.confirmDialog("Read", "123", "Want to read from file?")) {
			File file = chooseFile();
			if (file != null)
				readFromFile(file);
		}
		// set default locale english
		Locale.setDefault(Locale.ENGLISH);
		
		nameListView = new NameListView(phoneBook);
		BorderPane root = new BorderPane();
		root.setTop(new PhoneBookMenu(phoneBook, nameListView));
		root.setCenter(nameListView);		
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("PhoneBook");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	@Override
	public void stop(){
		if(Dialogs.confirmDialog("Save", "123", "Want to save before quitting?")) {
			File file = chooseFile();
			if (file != null)
				saveToFile(file);
		}
	}


	private void saveToFile(File file){
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(phoneBook);
			out.close();
		} catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void readFromFile(File file){
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			phoneBook = (MapPhoneBook) in.readObject();
			in.close();
		} catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	private File chooseFile(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

		return fileChooser.showOpenDialog(mainStage);
	}

}
