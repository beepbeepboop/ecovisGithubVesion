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

		System.out.println(undergrowthSpecies);
	}
}
