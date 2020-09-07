//import sun.awt.image.ImageWatched;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

import javax.lang.model.util.ElementScanner6;

import java.util.Locale;

public class Driver
{
    //Store Terrain Data in an ElevationGrid & Plant Data in Vectors
    private static ElevationGrid elevationGrid; //Will basically just need this data to produce a background image, then it can be yeeted
    private static Vector<Species> species;
    private static Vector<Plant> undergrowthPlants;
    private static Vector<Plant> canopyPlants;

    public static void main(String[] args) 
    {
        // Class details:
        // Plant: int speciesID, float x, y, z, height, canopyRadius
        // ElevationGrid: int dimX, dimY, float gridSpacing, latitude, float[][] grid
        // Species: int speciesID, String commonName, String latinName

        //Get array of files
        String path = "../data/";
        File[] fileArray = findFiles(path);
        for(File file : fileArray)
        {
            String fileName = file.getName();
            System.out.println("Reading file: " + fileName);

            if(fileName.contains(".elv"))
                elevationGrid = readElv(file);
            else if(fileName.contains(".pdb"))
            {
                if(fileName.contains("undergrowth"))
                    undergrowthPlants = readPdb(file);
                else
                    canopyPlants = readPdb(file);
            }
            else if(fileName.contains(".spc"))
                species = readSpc(file);
            else
            {
                System.out.println("Error: "+fileName+" is an unrecognised file type.");
                System.exit(1);
            }
        }

        //System.out.println(canopySpecies);
        //System.out.println(undergrowthSpecies);
        //System.out.println(canopyPlants);
        //System.out.println(undergrowthPlants);
        //System.out.println(elevationGrid);
    }

    private static Vector<Species> readSpc(File file)
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

    private static Vector<Plant> readPdb(File file)
    {
        Vector<Plant> ret = new Vector<Plant>();

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
                /*minHeight = line.nextFloat();
                maxHeight = line.nextFloat();
                heightRatio = line.nextFloat();*/
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
		ret.trimToSize();
		return ret;
    }

    private static ElevationGrid readElv(File file)
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

    //Returns list of files in a directory
    private static File[] findFiles(String path)
    {
        File f = new File(path);
        return f.listFiles();
    }
}
