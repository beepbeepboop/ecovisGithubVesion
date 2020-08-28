public class Driver
{

    public static void main(String[] args) 
    {
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
        //TODO: Find files in a directory: will retrieve list of files in specified path and order them as *.elv -> *.pdb -> *.spc
        return new String[]{"./data/S2000-2000-512.elv", "./data/S2000-2000-512_canopy.pdb", "./data/S2000-2000-512_undergrowth.pdb", "./data/S2000-2000-512.spc"}; //Temporary List
    }
}
