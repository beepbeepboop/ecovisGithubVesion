public class ElevationGrid
{
    //Class Variables
    private int dimX, dimY;
    private float gridSpacing, latitude;
    private float[][] grid;

    //Constructor
    public ElevationGrid(int x, int y, float gSpacing, float lat)
    {
        dimX = x;
        dimY = y;
        gridSpacing = gSpacing;
        latitude = lat;
        grid = new float[dimX][dimY];
    }

    //Getters and Setters
    public float getElevation(int x, int y){return grid[x][y];}
    public float getGridSpacing(){return gridSpacing;}
    public float getLatitude(){return latitude;}

    public void setElevation(int x, int y, float elevation){grid[x][y] = elevation;}
}