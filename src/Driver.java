import java.util.HashMap;
import java.util.LinkedList;

public class Driver
{
	//Store Terrain Data in an ElevationGrid & Plant Data in Vectors
	private static ElevationGrid elevationGrid; //Will basically just need this data to produce a background image, then it can be yeeted
	private static HashMap<Integer, Species> canopySpecies;
	private static HashMap<Integer, Species> undergrowthSpecies;
	private static LinkedList<Plant> undergrowthPlants;
	private static LinkedList<Plant> canopyPlants;
	private static FireModel fireModel;
	private static PlantModel plantModel;

	public static void main(String[] args) 
	{
		FileReader fr = new FileReader("./data/");	//If this is in a directory above then you're storing files incorrectly
		//System.out.println(fr.toString());
		elevationGrid = fr.getElevation();
		canopyPlants = fr.getCanopyPlants();
		undergrowthPlants = fr.getUndergrowthPlants();
		fr.readSpc();	//Populates species field
		canopySpecies = fr.getCanopySpecies();
		undergrowthSpecies = fr.getUndergrowthSpecies();

		//System.out.println(undergrowthSpecies);
		plantModel = new PlantModel(elevationGrid, undergrowthPlants, canopyPlants, undergrowthSpecies,canopySpecies);

		LinkedList<Coordinate> speciesLocation = plantModel.getUndergrowthSpeciesCoordinates(3);
		System.out.println(speciesLocation);

		fireModel = new FireModel(plantModel);
		LinkedList<Coordinate> temp = new LinkedList<Coordinate>();
		temp.add(new Coordinate(20,10));
		/*temp.add(new Coordinate(200, 90));
		temp.add(new Coordinate(12,53));
		temp.add(new Coordinate(40,6));
		temp.add(new Coordinate(231,4));*/
		fireModel.computeSpread(3, temp);
		//System.out.println(fireModel);
	}
}
