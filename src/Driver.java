public class Driver
{

    public static void main(String[] args) 
    {
        // write your code here
        // Class details:
        // Plant: int speciesID, float x, y, z, height, canopyRadius
        // ElevationGrid: int dimX, dimY, float gridSpacing, latitude, float[][] grid
        // Species: int speciesID, float minHeight, maxHeight, heightRatio, int numPlants
        // Species: int speciesID, string commonName,latinName

        //Get array of files
        String path = args[0];
        String[] fileArray = findFiles(path);
    }

    private static String[] findFiles(String path)
    {
        //Temporary List
        //TODO: Find files in a directory
        return new String[]{"./data/S2000-2000-512.elv"};
    }
}
