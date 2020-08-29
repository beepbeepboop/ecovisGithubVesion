import sun.awt.image.ImageWatched;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Driver
{
    private static ElevationGrid elevationGrid; //Can't initialise just yet
    private static HashMap<Integer, Species> undergrowthSpecies = new HashMap<Integer,Species>(); //Links species by ID
    private static HashMap<Integer, Species> canopySpecies = new HashMap<Integer,Species>(); //Links species by ID
    private static LinkedList<Plant> undergworthPlants = new LinkedList<Plant>();   //Contains a list of all Plants - order and indexing is irrelevant at current stage of code
    private static LinkedList<Plant> canopyPlants = new LinkedList<Plant>();   //Contains a list of all Plants - order and indexing is irrelevant at current stage of code

    public static void main(String[] args) 
    {
        // Class details:
        // Plant: int speciesID, float x, y, z, height, canopyRadius
        // ElevationGrid: int dimX, dimY, float gridSpacing, latitude, float[][] grid
        // Species: int speciesID, float minHeight, maxHeight, heightRatio, int numPlants
        // Species: int speciesID, string commonName,latinName

        //Get array of files
        String path = "./data/";
        String[] fileArray = findFiles(path);
        for(String fileName : fileArray)
        {
            System.out.println("Reading file: " + fileName);
            if(fileName.contains(".elv"))
                readElv(fileName);
            else if(fileName.contains(".pdb"))
                readPdb(fileName);
            else if(fileName.contains(".spc"))
                readSpc(fileName);
            else
            {
                System.out.println("Error: Unrecognisable file type");
                System.exit(1);
            }
        }
    }

    private static void readSpc(String fileName)
    {
        try
        {
            Scanner f = new Scanner(new File((fileName)));
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
    }

    private static void readPdb(String fileName)
    {
        try
        {
            Scanner f = new Scanner(new File((fileName)));
            Scanner line = new Scanner(f.nextLine());
            int numSpecies = line.nextInt();
            int speciesId, numPlants;
            float minHeight, maxHeight, heightRatio;
            for(int i = 0; i < numSpecies; i++)
            {
                line = new Scanner(f.nextLine());
                speciesId = line.nextInt();
                minHeight = line.nextFloat();
                maxHeight = line.nextFloat();
                heightRatio = line.nextFloat();
                line = new Scanner(f.nextLine());
                numPlants = line.nextInt();

                // Insert species data into hashMap - check for undergrowth or canopy
                if(fileName.contains("canopy"))
                    undergrowthSpecies.put(speciesId,new Species(speciesId,minHeight,maxHeight,heightRatio,numPlants));
                else //Assumption that files will be named appropriately - all .pdb files will either be named canopy or undergrowth
                    canopySpecies.put(speciesId,new Species(speciesId,minHeight,maxHeight,heightRatio,numPlants));

                for(int j = 0; j < numPlants; j++)
                {
                    line = new Scanner(f.nextLine());
                    float xPos = line.nextFloat();
                    float yPos = line.nextFloat();
                    float zPos = line.nextFloat();
                    float height = line.nextFloat();
                    float radius = line.nextFloat();

                    //Insert plant data into list
                    //Possible ordering involves Min/Max heap of plants ordered by their radial distance from origin
                    if(fileName.contains("undergrowth"))
                        undergworthPlants.add(new Plant(speciesId,xPos,yPos,zPos,height,radius));
                    else
                        canopyPlants.add(new Plant(speciesId,xPos,yPos,zPos,height,radius));
                }
            }

            // System.out.println(canopySpecies);
            // System.out.println(canopyPlants);
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
    }

    private static void readElv(String fileName)
    {
        try
        {
            Scanner f = new Scanner(new File((fileName)));
            Scanner line = new Scanner(f.nextLine());
            int dimX = line.nextInt();
            int dimY = line.nextInt();
            float gridSpacing = line.nextFloat();
            float latitude = line.nextFloat();
            elevationGrid = new ElevationGrid(dimX,dimY,gridSpacing,latitude);
            float elevation;
            for(int x = 0; x < dimX; x++)
            {
                line = new Scanner(f.nextLine());
                for(int y = 0; y < dimY; y++)
                {
                    elevation = line.nextFloat();
                    elevationGrid.setElevation(x,y,elevation);
                }
            }
            //System.out.println(elevationGrid);
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
    }

    private static String[] findFiles(String path)
    {
        //TODO: Find files in a directory: will retrieve list of files in specified path and order them as *.elv -> *.pdb -> *.spc
        return new String[]{"./data/S6000-6000-256.elv", "./data/S6000-6000-256_canopy.pdb", "./data/S6000-6000-256_undergrowth.pdb", "./data/S6000-6000-256.spc"}; //Temporary List
    }
}
