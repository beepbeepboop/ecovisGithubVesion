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
    private FireSnapshot[] fireSnapshots;   //Contains snapshot of each state as a function of time (array indices)

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

    // Populate the ArrayList of state snapshots
    // time parameter determines start frame of the simulation
    // ignitionPoints stores list of coordinates where fire has newly spread
    // duration = how many more iterations to run
    public void computeSpread(int duration, LinkedList<Coordinate> ignitionPoints)
    {
        fireSnapshots = new FireSnapshot[duration];
        ArrayList<Coordinate> burnedPoints = new ArrayList<>();
        ArrayList<Coordinate> burningPoints = new ArrayList<>();
        for (Coordinate startPoint : ignitionPoints)
        {
            burningPoints.add(startPoint);
        }
        fireSnapshots[0] = new FireSnapshot(burnedPoints, burningPoints);
        computeSpread(fireSnapshots[0], 1, duration-1);
    }

    public void computeSpread(FireSnapshot context, int time, int duration)
    {
        ArrayList<Coordinate> burnedPoints = new ArrayList<>();
        burnedPoints.addAll(context.getBurned());
        ArrayList<Coordinate> burningPoints = new ArrayList<>();
        burningPoints.addAll(context.getBurning());
        int numPoints = context.getBurning().size();
        for (int i = 0; i < numPoints; i++)
        {
            Coordinate startPoint = context.getBurning().get(i);
            for(Coordinate spreadPoint : getNeighbourhood(startPoint))
            {
                    if(spreadProbability(context, startPoint, spreadPoint))
                    {
                        burningPoints.add(spreadPoint);
                    }
            }

            burningPoints.remove(burningPoints.get(0));
            burnedPoints.add(startPoint);
        }

        fireSnapshots[time] = new FireSnapshot(burnedPoints, burningPoints);
        if(duration > 1)
        {
            computeSpread(fireSnapshots[time], time+1, duration-1);
        }
    }

    // Returns list of valid neighbours around a central coordinate
    private LinkedList<Coordinate> getNeighbourhood(Coordinate firePoint)
    {
        int x = firePoint.getX();
        int y = firePoint.getY();
        LinkedList<Coordinate> neighbourhood = new LinkedList<Coordinate>();
        if(x - 1 >= 0 && y - 1 >= 0) { neighbourhood.add(new Coordinate((x-1),(y-1))); }
        if(x - 1 >= 0) { neighbourhood.add(new Coordinate((x-1),y)); }
        if(x - 1 >= 0 && y + 1 <= dimY) { neighbourhood.add(new Coordinate((x-1),(y+1))); }
        if(y - 1 >= 0) { neighbourhood.add(new Coordinate(x,(y-1))); }
        if(y + 1 <= dimY) { neighbourhood.add(new Coordinate(x,(y+1))); }
        if(x + 1 <= dimX && y - 1 >= 0) { neighbourhood.add(new Coordinate((x+1), (y-1))); }
        if(x + 1 <= dimX) { neighbourhood.add(new Coordinate((x+1),y)); }
        if(x + 1 <= dimX && y + 1 <= dimY) { neighbourhood.add(new Coordinate((x+1), (y+1))); }

        return neighbourhood;
    }

    // Can add other parameters like wind vector and plant density
    public boolean spreadProbability(FireSnapshot context, Coordinate start, Coordinate destination)
    {
        float probability;
        if(densityGrid[destination.getX()][destination.getY()] >= 1)
        {
            probability = 1;
        }
        else
        {
            probability = densityGrid[destination.getX()][destination.getY()]; // Probability of spreading to a tile is proportionate to its density
        }


        double random = Math.random()*100;
        if(random <= probability)
        {
            // Search through burned list - cant spread to an already burned tile
            for(Coordinate burnCheck : context.getBurned())
            {
                if (burnCheck.matches(destination));
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
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

        temp += "\nFire Spread\n";
        for(int i = 0; i < fireSnapshots.length; i++)
        {
            temp += "Time: " + i + "\n";
            temp += fireSnapshots[i] + "\n\n";
        }
        return temp;
    }
}