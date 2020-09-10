import java.util.Vector;

public class Driver
{
	//Store Terrain Data in an ElevationGrid & Plant Data in Vectors
	private static ElevationGrid elevationGrid; //Will basically just need this data to produce a background image, then it can be yeeted
	private static Vector<Species> species;
	private static Vector<Plant> undergrowthPlants;
	private static Vector<Plant> canopyPlants;

	public static void main(String[] args) 
	{
		FileReader fr = new FileReader("../data/");
		System.out.println(fr.toString());
		elevationGrid = fr.getElvation();
		species = fr.getSpecies();
		canopyPlants = fr.getCanopy();
		undergrowthPlants = fr.getUnderGrowth();
	}
}
