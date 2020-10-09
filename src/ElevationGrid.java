public class ElevationGrid
{
    //Class Variables
    private int dimX, dimY;
    private float gridSpacing, latitude, minHeight, maxHeight;
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
    public float getMinHeight(){return minHeight;}
    public float getMaxHeight(){return maxHeight;}
    public int getDimX(){return dimX;}
    public int getDimY(){return dimY;}
    public void setMinHeight(float minHeight) {this.minHeight = minHeight;}
    public void setMaxHeight(float maxHeight) {this.maxHeight = maxHeight;}

    public void setElevation(int x, int y, float elevation){grid[x][y] = elevation;}

    
    public String toString() {
        String temp = "ElevationGrid " +
                "dimX=" + dimX +
                ", dimY=" + dimY +
                ", gridSpacing=" + gridSpacing +
                ", latitude=" + latitude +
                ", grid:\n";

        for(int x = 0; x < dimX; x++)
        {
            for(int y = 0; y < dimY; y++)
            {
                temp += "("+x+","+y+")="+grid[x][y]+" ";
            }
            temp += "\n";
        }
        return temp;
    }


}