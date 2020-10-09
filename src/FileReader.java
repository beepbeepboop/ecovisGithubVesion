import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader
{
	private File undergrowthF, canopyF, elevationF, speciesF;

	// Used for storing species data
	private Species[] species;


	//Constructor, assigns files based on path
	public FileReader(String path)
	{
		//Get FileNames for reading data
		File f = new File(path);
		File[] fileList = f.listFiles();
		for(File file : fileList)
		{
			String fileName = file.getName();
			if(fileName.contains(".elv"))
				elevationF = file;
			else if(fileName.contains(".pdb"))
			{
				if(fileName.contains("undergrowth"))	// Assumed appropriate naming
					undergrowthF = file;
				else
					canopyF = file;
			}
			else if(fileName.contains(".spc"))
				speciesF = file;
			else
			{
				System.out.println("Error: "+fileName+" is an unrecognised file type.");
				System.exit(1);
			}
		}
	}



	// Read and return file data
	public LinkedList<Plant> getUndergrowthPlants(){System.out.println("Reading undergrowth plants file");return readPdb(undergrowthF);}
	public LinkedList<Plant> getCanopyPlants(){System.out.println("Reading canopy plants file");return readPdb(canopyF);}
	public Species[] getSpecies(){System.out.println("Reading Spc File");readSpc(); return species;}
	public ElevationGrid getElevation(){System.out.println("Reading Elevation File");return readElv(elevationF);}


	// Reads species names and populates species data
	public void readSpc()
	{
		LinkedList<Species> spc = new LinkedList<Species>();
		try
		{
			Scanner f = new Scanner(speciesF);
			int counter = 0;
			while(f.hasNextLine())
			{
				Scanner line = new Scanner(f.nextLine()).useLocale(Locale.US);
				line.useDelimiter(" “");
				int speciesId = line.nextInt();
				String commonName = line.next().replace("”", "");
				String latinName = line.next().replace("”", "");
				spc.add(new Species(commonName, latinName));
			}
			species = spc.toArray(new Species[0]);
			f.close();
		}
		catch (FileNotFoundException e) {System.out.println(e);}
	}

	//Read in a plant file and return a Linked List of plants
	//Plant: int speciesID, float x, y, z, height, canopyRadius
	public LinkedList<Plant> readPdb(File file)
	{
		LinkedList<Plant> ret = new LinkedList<Plant>();
		boolean canopy = file.getName().contains("canopy");
		try
		{    
			Scanner f = new Scanner(file);
			Scanner line = new Scanner(f.nextLine()).useLocale(Locale.US);
			int numSpecies = line.nextInt();
			int speciesId, numPlants;
			float minHeight, maxHeight, heightRatio;
			for(int i = 0; i < numSpecies; i++)
			{
				line = new Scanner(f.nextLine()).useLocale(Locale.US);
				speciesId = line.nextInt();
				minHeight = line.nextFloat();
				maxHeight = line.nextFloat();
				heightRatio = line.nextFloat();
				line = new Scanner(f.nextLine()).useLocale(Locale.US);
				numPlants = line.nextInt();


				//PopulateSpeciesData
				species[i].populateSpc(speciesId, minHeight, maxHeight, heightRatio);
				if(canopy){species[i].setCanopyPos(ret.size());species[i].setNumCanopyPlants(numPlants);}
				else{species[i].setUnderPos(ret.size());species[i].setNumUnderGrowthPlantsPlants(numPlants);}

				for(int j = 0; j < numPlants; j++)
				{
					line = new Scanner(f.nextLine()).useLocale(Locale.US);
					float xPos = line.nextFloat();
					float yPos = line.nextFloat();
					float zPos = line.nextFloat();
					float height = line.nextFloat();
					float radius = line.nextFloat();
					ret.add(new Plant(speciesId, xPos, yPos, zPos, height, radius));
				}
			}
			line.close();
			f.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e);
		}
		return ret;
	}


	//Return a file populated ElevationGrid object
	// ElevationGrid: int dimX, dimY, float gridSpacing, latitude, float[][] grid
	public ElevationGrid readElv(File file)
	{
		try
		{
			ElevationGrid elevGrid;
			Scanner f = new Scanner(file);
			Scanner line = new Scanner(f.nextLine()).useLocale(Locale.US);
			int dimX = line.nextInt();
			int dimY = line.nextInt();
			float gridSpacing = line.nextFloat();
			float latitude = line.nextFloat();

			elevGrid = new ElevationGrid(dimX,dimY,gridSpacing,latitude);
			float elevation,minHeight=1000000000,maxHeight=0;    //fix to float max later look for max float value
			for(int x = 0; x < dimX; x++)
			{
				line = new Scanner(f.nextLine()).useLocale(Locale.US);
				for(int y = 0; y < dimY; y++)
				{
					elevation = line.nextFloat();
					elevGrid.setElevation(x,y,elevation);

					//comparing min and max heights to current elevation
					if (elevation<minHeight){minHeight=elevation;}
					if (elevation>maxHeight){maxHeight=elevation;}
				}
			}
			elevGrid.setMinHeight(minHeight);
			elevGrid.setMaxHeight(maxHeight);

			line.close();
			f.close();
			return elevGrid;
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e);
		}
		return null;
	}


	public String toString()
	{
		return "Elevation:\t\t"+elevationF.getName()+"\nSpecies:\t\t"+speciesF.getName()+"\nCanopy:\t\t\t"+canopyF.getName()+"\nUnderGrowth:\t"+undergrowthF.getName();
	}
}