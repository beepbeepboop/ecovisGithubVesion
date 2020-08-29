import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Locale;

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
        File[] fileArray = findFiles(path);
        for(File fileName : fileArray)
        {
            System.out.println("Reading file: " + fileName);
            if(fileName.getName().contains(".elv"))
                readElv(fileName);
            else if(fileName.getName().contains(".pdb"))
                readPdb(fileName);
            else if(fileName.getName().contains(".spc"))
                readSpc(fileName);
            else
            {
                System.out.println("Error: Unrecognisable file type");
                System.exit(1);
            }
        }
    }

    private static void readSpc(File fileName)
    {
        try
        {
            Scanner f = new Scanner(fileName);

            f.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
    }

    private static void readPdb(File fileName)
    {
        try
        {
            Scanner f = new Scanner(fileName);

            f.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
    }

    private static ElevationGrid readElv(File fileName)
    {
        try
        {
            Scanner f = new Scanner(fileName);
            //Set the locale to US, Scanner was having trouble with using '.' instead of ',' for decimals
            Scanner line = new Scanner(f.nextLine()).useLocale(Locale.US);
            int dimX = line.nextInt();
            int dimY = line.nextInt();
            float gridSpacing = line.nextFloat();
            float latitude = line.nextFloat();
            elevationGrid = new ElevationGrid(dimX,dimY,gridSpacing,latitude);
            float elevation;
            for(int x = 0; x < dimX; x++)
            {
                System.out.println();
                line = new Scanner(f.nextLine()).useLocale(Locale.US);
                for(int y = 0; y < dimY; y++)
                {
                    elevation = line.nextFloat();
                    elevationGrid.setElevation(x,y,elevation);
                }
            }
            //System.out.println(elevationGrid);//Editing this out for now because it takes poes long to print
            f.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
        return elevationGrid;
    }

    //Returns list of files in a directory
    private static File[] findFiles(String path)
    {
        File f = new File(path);
        return f.listFiles();
        //return new File[]{"./data/S2000-2000-512.elv", "./data/S2000-2000-512_canopy.pdb", "./data/S2000-2000-512_undergrowth.pdb", "./data/S2000-2000-512.spc"}; //Temporary List
    }
}
