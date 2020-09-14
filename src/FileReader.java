import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; import java.util.Locale;
import java.util.Vector;

public class FileReader
{
	File undergrowthF, canopyF, elevationF, speciesF;
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
				if(fileName.contains("undergrowth"))
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

	//Read and return file data
	public Vector<Plant> getUnderGrowth(){System.out.println("Reading Undergrowth File");return readPdb(undergrowthF);}
	public Vector<Plant> getCanopy(){System.out.println("Reading Canopy File");return readPdb(canopyF);}
	public Vector<Species> getSpecies(){System.out.println("Reading Species File");return readSpc(speciesF);}
	public ElevationGrid getElvation(){System.out.println("Reading Elevation File");return readElv(elevationF);}


	//Returns a trimmed Vector of Species objects from a file
	//Species: int speciesID, String commonName, String latinName
	public Vector<Species> readSpc(File file)
	{
		Vector<Species> spc = new Vector<Species>();
		try
		{
			Scanner f = new Scanner(file);
			while(f.hasNextLine())
			{
				Scanner line = new Scanner(f.nextLine()).useLocale(Locale.US);
				line.useDelimiter(" “");
				int speciesId = line.nextInt();
				String commonName = line.next().replace("”", "");
				String latinName = line.next().replace("”", "");
				spc.add(new Species(speciesId, commonName, latinName));
			}
			f.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e);
		}

		spc.trimToSize();
		return spc;
	}

	//Read in a plant file and retun a trimmed Vector of plants
	//Plant: int speciesID, float x, y, z, height, canopyRadius
	public Vector<Plant> readPdb(File file)
	{
		Vector<Plant> retern = new Vector<Plant>();

		try
		{    
			Scanner f = new Scanner(file);
			Scanner line = new Scanner(f.nextLine()).useLocale(Locale.US);
			int numSpecies = line.nextInt();
			int speciesId, numPlants;
			//float minHeight, maxHeight, heightRatio;
			for(int i = 0; i < numSpecies; i++)
			{
				line = new Scanner(f.nextLine()).useLocale(Locale.US);
				speciesId = line.nextInt();
				line = new Scanner(f.nextLine()).useLocale(Locale.US);
				numPlants = line.nextInt();

				for(int j = 0; j < numPlants; j++)
				{
					line = new Scanner(f.nextLine()).useLocale(Locale.US);
					float xPos = line.nextFloat();
					float yPos = line.nextFloat();
					float zPos = line.nextFloat();
					float height = line.nextFloat();
					float radius = line.nextFloat();
					retern.add(new Plant(speciesId, xPos, yPos, zPos, height, radius));
				}
			}
			line.close();
			f.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e);
		}
		retern.trimToSize();
		return retern;
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
			float elevation;
			for(int x = 0; x < dimX; x++)
			{
				line = new Scanner(f.nextLine()).useLocale(Locale.US);
				for(int y = 0; y < dimY; y++)
				{
					elevation = line.nextFloat();
					elevGrid.setElevation(x,y,elevation);
				}
			}
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