import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader
{
	private File undergrowthF, canopyF, elevationF, speciesF;

	// Used for storing species data as it is populated through two files
	private Species[] species;


	//Constructor, Assigns files to be used with operations
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
	public Plant[] getUndergrowthPlants(){System.out.println("Reading undergrowth plants file");return readPdb(undergrowthF);}
	public Plant[] getCanopyPlants(){System.out.println("Reading canopy plants file");return readPdb(canopyF);}
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
				spc.add(new Species(speciesId, commonName, latinName));
			}
			species = spc.toArray(new Species[0]);
			f.close();
		}
		catch (FileNotFoundException e) {System.out.println(e);}
	}

	//Read in a plant file and return a Linked List of plants
	//Plant: int speciesID, float x, y, z, height, canopyRadius
	public Plant[] readPdb(File file)
	{
		int spcPos = 0;
		LinkedList<Plant> tempList = new LinkedList<Plant>();
		boolean canopy = file.getName().contains("canopy");
		try
		{    
			Scanner f = new Scanner(file);
			Scanner line = new Scanner(f.nextLine()).useLocale(Locale.US);
			int numSpecies = line.nextInt();
			int speciesId, numPlants;
			for(int i = 0; i < numSpecies; i++)
			{
				line = new Scanner(f.nextLine()).useLocale(Locale.US);
				speciesId = line.nextInt();
				line = new Scanner(f.nextLine()).useLocale(Locale.US);
				numPlants = line.nextInt();

				//PopulateSpeciesData
				if(canopy){species[i].setCanopyPos(spcPos);species[i].setNumCanopyPlants(numPlants);}
				else{species[i].setUnderPos(spcPos);species[i].setNumUnderGrowthPlantsPlants(numPlants);}
				spcPos += numPlants;

				float xPos, yPos, zPos;
				float height, radius;
				for(int j = 0; j < numPlants; j++)
				{
					line = new Scanner(f.nextLine()).useLocale(Locale.US);
					xPos = line.nextFloat();
					yPos = line.nextFloat();
					zPos = line.nextFloat();
					height = line.nextFloat();
					radius = line.nextFloat();
					tempList.add(new Plant(speciesId, xPos, yPos, zPos, height, radius));
				}
			}
			line.close();
			f.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e);
		}
		Plant[] retPlants = new Plant[tempList.size()];
		retPlants = tempList.toArray(retPlants);
		return retPlants;
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
			float elevation,minHeight=Float.MAX_VALUE,maxHeight=0;
			for(int x = 0; x < dimX; x++)
			{
				line = new Scanner(f.nextLine()).useLocale(Locale.US);
				for(int y = 0; y < dimY; y++)
				{
					elevation = line.nextFloat();
					elevGrid.setElevation(x,y,elevation);

					//comparing min and max heights to current elevation
					if (elevation<minHeight){minHeight=elevation;}
					else if (elevation>maxHeight){maxHeight=elevation;}
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