import javafx.application.Application;

import java.util.HashMap;
import java.util.LinkedList;

public class Driver
{
	//Store Terrain Data in an ElevationGrid & Plant Data in Vectors
	private static ElevationGrid elevationGrid; //Will basically just need this data to produce a background image, then it can be yeeted
	private static Species[] species;
	private static Plant[] undergrowthPlants;
	private static Plant[] canopyPlants;
	private static FireModel fireModel;
	private static PlantModel plantModel;

	public static void main(String[] args) 
	{
		FileReader fr = new FileReader("C:\\Users\\jordan\\IdeaProjects\\EcoVis\\data");	//If this is in a directory above then you're storing files incorrectly
		//System.out.println(fr.toString());
		species = fr.getSpecies();
		elevationGrid = fr.getElevation();
		canopyPlants = fr.getCanopyPlants();
		undergrowthPlants = fr.getUndergrowthPlants();
		//fr.readSpc();	//Populates species field


		plantModel = new PlantModel(elevationGrid, undergrowthPlants, canopyPlants, species);
		fireModel = new FireModel(plantModel);

		// ***TESTS***
		//System.out.println(undergrowthSpecies);
		//LinkedList<Coordinate> speciesLocation = plantModel.getUndergrowthSpeciesCoordinates(3);
		//System.out.println(speciesLocation);
		//speciesLocation = plantModel.getUndergrowthSpeciesCoordinates(plantModel.findSpeciesIdByName("Western Swordfern"));
		//System.out.println(speciesLocation);

		LinkedList<Coordinate> fireStart = new LinkedList<Coordinate>();
		fireStart.add(new Coordinate(20,10));
		/*temp.add(new Coordinate(200, 90));
		temp.add(new Coordinate(12,53));
		temp.add(new Coordinate(40,6));*/
		//fireStart.add(new Coordinate(231,4));

		System.out.println("Computing Firemodel");
		fireModel.computeSpread(10, fireStart, 3);
		System.out.println("Compute done, printing Firemodel");
		System.out.println(fireModel);
		System.out.println("firemodel is printed");

		Application.launch(EcovisMainUI.class,args);  //this is the code that calls the UI
	}
}
