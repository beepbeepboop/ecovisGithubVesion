import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FireModel
{
    private PlantModel plantModel;
    private LinkedList<Plant>[][] grid;
    private float[][] densityGrid;
    private int dimX, dimY;
    private float gridSpacing;

    public FireModel(PlantModel plants)
    {
        plantModel = plants;
        dimX = plantModel.getDimX();
        dimY = plantModel.getDimY();
        gridSpacing = plantModel.getGridSpacing();
        grid  = plantModel.getGrid();
        densityGrid = new float[dimX][dimY];
        computeDensity();
    }

    // Gives each cell a density value
    // Assumptions:
    // Each plant in the grid is located at the center, and the entire mass of the plant is located in the grid
    // Does not separate canopy and undergrowth plants, assumed everything is on the exact same height
    public void computeDensity()
    {
        //iterate through every coordinate on the grid. Populate density
        for(int x = 0; x < dimX; x++) { for (int y = 0; y < dimY; y++)
        {
            float density = 0;
            if(grid[x][y].isEmpty())
            {
                density = 0;
            }
            else
            {
                for(Plant plant : grid[x][y])
                {
                    density += plant.getCanopyRadius(); //Add total radii of plants. Can extend to height to find a volumetric density
                }
                density = density/(gridSpacing*gridSpacing);    //I'm too lazy to use Math.pow and scared I'll use it incorrectly
            }

            densityGrid[x][y] = density;
        }}
    }

    @Override
    public String toString()
    {
        String temp = "DensityGrid " +
                "dimX=" + dimX +
                ", dimY=" + dimY +
                ", gridSpacing=" + gridSpacing +
                ", grid:\n";

        for(int x = 0; x < dimX; x++)
        {
            for(int y = 0; y < dimY; y++)
            {
                temp += "("+x+","+y+")="+densityGrid[x][y]+" ";
            }
            temp += "\n";
        }
        return temp;
    }
}