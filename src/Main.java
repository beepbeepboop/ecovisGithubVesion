//package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.HashMap;
import java.util.LinkedList;

public class Main extends Application {

	private static ElevationGrid elevationGrid; //Will basically just need this data to produce a background image, then it can be yeeted
	private static HashMap<Integer, Species> canopySpecies;
	private static HashMap<Integer, Species> undergrowthSpecies;
	private static LinkedList<Plant> undergrowthPlants;
	private static LinkedList<Plant> canopyPlants;
	//private static FireModel fireModel;
	private static PlantModel plantModel;

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		primaryStage.setTitle("EcoVis");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

	}


	public static void main(String[] args)
	{
		launch(args);
		/*
		FileReader fr = new FileReader("./data/");	//If this is in a directory above then you're storing files incorrectly
		//System.out.println(fr.toString());
		elevationGrid = fr.getElevation();
		canopyPlants = fr.getCanopyPlants();
		undergrowthPlants = fr.getUndergrowthPlants();
		fr.readSpc();	//Populates species field
		canopySpecies = fr.getCanopySpecies();
		undergrowthSpecies = fr.getUndergrowthSpecies();

		//System.out.println(undergrowthSpecies);
		System.out.println("Making PlantModel");
		plantModel = new PlantModel(elevationGrid, undergrowthPlants, canopyPlants);

		 */
	}
}
