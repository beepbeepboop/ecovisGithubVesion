import java.util.HashMap;
import java.util.LinkedList;

public class PlantModel
{
    private ElevationGrid elevationGrid;
    private LinkedList<Plant> undergrowthPlantList;
    private LinkedList<Plant> canopyPlantList;
    private static HashMap<Integer, Species> canopySpecies;
    private static HashMap<Integer, Species> undergrowthSpecies;
    private LinkedList<Plant>[][] grid;
    private int dimX, dimY;
    private float gridSpacing;

    public PlantModel(ElevationGrid elevGrid, LinkedList<Plant> undergrowthPlants, LinkedList<Plant> canopyPlants, HashMap<Integer, Species> undergrowthSpecies, HashMap<Integer, Species> canopySpecies)
    {
        elevationGrid = elevGrid;
        undergrowthPlantList = undergrowthPlants;
        canopyPlantList = canopyPlants;
        this.canopySpecies = canopySpecies;
        this.undergrowthSpecies = undergrowthSpecies;
        dimX = elevationGrid.getDimX();
        dimY = elevationGrid.getDimY();
        grid = new LinkedList[dimX][dimY];
        gridSpacing = elevationGrid.getGridSpacing();
        initialiseGrid();
        assignPlantsToGrid();
    }

    // Runs through grid and creates new linkedLists of Plant objects
    private void initialiseGrid()
    {
        for(int i = 0; i < dimX; i++)
        {
            for(int j = 0; j < dimY; j++)
            {
                grid[i][j] = new LinkedList<Plant>();
            }
        }
    }

    // Populates the gridPosition variable in the plant and adds a reference to the plant in the local grid
    private void assignPlantsToGrid()
    {
        for(Plant plant : undergrowthPlantList)
        {
            int xIndex = (int)(plant.getX()/gridSpacing);
                if(xIndex >= 256){xIndex=255;}
            int yIndex = (int)(plant.getY()/gridSpacing);
                if(yIndex >= 256){yIndex=255;}

            grid[xIndex][yIndex].add(plant);
            plant.setXIndex(xIndex);
            plant.setYIndex(yIndex);
            Species species = undergrowthSpecies.get(plant.getID());
            species.addCoordinate(new Coordinate(xIndex, yIndex));
        }
        for(Plant plant : canopyPlantList)
        {
            int xIndex = (int)(plant.getX()/gridSpacing);
            if(xIndex >= 256){xIndex=255;}
            int yIndex = (int)(plant.getY()/gridSpacing);
            if(yIndex >= 256){yIndex=255;}

            grid[xIndex][yIndex].add(plant);
            plant.setXIndex(xIndex);
            plant.setYIndex(yIndex);
            Species species = canopySpecies.get(plant.getID());
            species.addCoordinate(new Coordinate(xIndex, yIndex));
        }
    }

    public LinkedList<Coordinate> getUndergrowthSpeciesCoordinates(int speciesId)
    {
        if (undergrowthSpecies.containsKey(speciesId))
        {
            Species s = undergrowthSpecies.get(speciesId);
            return s.getCoordinates();
        }
        else
            return null;
    }

    public LinkedList<Coordinate> getCanopySpeciesCoordinates(int speciesId)
    {
        if (canopySpecies.containsKey(speciesId))
        {
            Species s = canopySpecies.get(speciesId);
            return s.getCoordinates();
        }
        else
            return null;
    }

    public LinkedList<Plant>[][] getGrid() { return grid; }
    public int getDimX() { return dimX; }
    public int getDimY() { return dimY; }
    public float getGridSpacing() { return gridSpacing; }
}
