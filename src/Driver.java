import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver
{
    private static ElevationGrid elevationGrid;

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
            System.out.println(elevationGrid);
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
    }

    private static String[] findFiles(String path)
    {
        //TODO: Find files in a directory: will retrieve list of files in specified path and order them as *.elv -> *.pdb -> *.spc
        return new String[]{"./data/S2000-2000-512.elv", "./data/S2000-2000-512_canopy.pdb", "./data/S2000-2000-512_undergrowth.pdb", "./data/S2000-2000-512.spc"}; //Temporary List
    }
}
