import java.util.*;

public class FireModel
{
    private PlantModel plantModel;
    private LinkedList<Plant>[][] grid;
    private float[][] densityGrid;
    private float totalAverageDensity;
    private float positveAverageDensity;
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
        float densityTotal = 0;
        float positiveDensityTotal = 0;
        int positiveDensityCount = 0;
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
                positiveDensityTotal += density;
                positiveDensityCount++;
            }
            densityTotal += density;
            densityGrid[x][y] = density;
        }}
        if(positiveDensityCount!=0)
        positveAverageDensity = positiveDensityTotal/positiveDensityCount;
        totalAverageDensity = densityTotal/(dimX*dimY);
    }

    // Populate the ArrayList of state snapshots
    // time parameter determines start frame of the simulation
    // ignitionPoints stores list of coordinates where fire has newly spread
    // duration = how many more iterations to run
    public void computeSpread(int duration, LinkedList<Coordinate> ignitionPoints, int burnDuration)
    {
        fireSnapshots = new FireSnapshot[duration];
        ArrayList<Coordinate> burnedPoints = new ArrayList<>();
        ArrayList<Coordinate> burningPoints = new ArrayList<>();
        HashMap<Integer, HashMap<Integer, Integer>> fireMap = new HashMap<>();
        for (Coordinate startPoint : ignitionPoints)
        {
            burningPoints.add(startPoint);
            fireMap.putIfAbsent(startPoint.getX(), new HashMap<>());
            fireMap.get(startPoint.getX()).putIfAbsent(startPoint.getY(), burnDuration);
        }
        fireSnapshots[0] = new FireSnapshot(burnedPoints, burningPoints, fireMap);
        computeSpread(fireSnapshots[0], 1, duration-1, burnDuration);
    }

    public void computeSpread(FireSnapshot context, int time, int duration, int burnDuration)
    {
        // Deep copy variables
        ArrayList<Coordinate> burnedPoints = new ArrayList<>();
        burnedPoints.addAll(context.getBurned());
        ArrayList<Coordinate> burningPoints = new ArrayList<>();
        burningPoints.addAll(context.getBurning());
        HashMap<Integer, HashMap<Integer, Integer>> fireMap = new HashMap<>();
        for(Map.Entry<Integer, HashMap<Integer, Integer>> entryX : context.getMap().entrySet())
        {
            fireMap.putIfAbsent(entryX.getKey(), new HashMap<>());
            for(Map.Entry<Integer, Integer> entryY : entryX.getValue().entrySet())
            {
                fireMap.get(entryX.getKey()).putIfAbsent(entryY.getKey(), entryY.getValue());
            }
        }

        int numPoints = context.getBurning().size();
        for (int i = 0; i < numPoints; i++)
        {
            Coordinate startPoint = context.getBurning().get(i);
            for(Coordinate spreadPoint : getNeighbourhood(startPoint))
            {
                    if(spreadProbability(fireMap, startPoint, spreadPoint))
                    {
                        burningPoints.add(spreadPoint);
                        fireMap.putIfAbsent(spreadPoint.getX(), new HashMap<>());
                        fireMap.get(spreadPoint.getX()).put(spreadPoint.getY(), burnDuration);
                    }
            }

            // Decrease fire duration
            fireMap.get(startPoint.getX()).put(startPoint.getY(), fireMap.get(startPoint.getX()).get(startPoint.getY()) -1);
            if(fireMap.get(startPoint.getX()).get(startPoint.getY()) == 0)
            {
                burningPoints.remove(burningPoints.get(0));
                burnedPoints.add(startPoint);
            }
        }

        fireSnapshots[time] = new FireSnapshot(burnedPoints, burningPoints, fireMap);
        if(duration > 1)
        {
            computeSpread(fireSnapshots[time], time+1, duration-1, burnDuration);
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
    //TODO: Tweak probability once fire visualisation is complete
    //TODO: Account for wind
    //TODO: Scale probability with fire age (peaks midway through lifetime)
    public boolean spreadProbability(HashMap<Integer, HashMap<Integer, Integer>> context, Coordinate start, Coordinate destination)
    {
        double probability;
        //Sigmoid
        double x = densityGrid[destination.getX()][destination.getY()];
        double amplitude = 1;
        double steepness = 2;
        double shift = positveAverageDensity; //Equivalence point of sigmoid
        probability = amplitude/(1+Math.exp((0-steepness)*(x-shift)));

        //Modify probability by wind unit vector additively
        //Coordinate windVector = new Coordinate()
        //Coordinate spreadVector = new Coordinate(destination.getX()-start.getX(), destination.getY() - start.getY());

        double random = Math.random();
        if(random <= probability)
        {
            // If there's already a fire entry - do not spread
            if(context.containsKey(destination.getX()))
            {
                if(context.get(destination.getX()).containsKey(destination.getY()))
                {
                    return false;
                }
                return true;
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
        String outputString = "DensityGrid " +
                "dimX=" + dimX +
                ", dimY=" + dimY +
                ", gridSpacing=" + gridSpacing +
                ", grid:\n";

        for(int x = 0; x < dimX; x++)
        {
            for(int y = 0; y < dimY; y++)
            {
                outputString += "["+x+","+y+"]="+densityGrid[x][y]+" ";
            }
            outputString += "\n";
        }

        outputString += "\nAverage total density: " + totalAverageDensity + " square plant area per grid\n";
        outputString += "\nAverage positive density: " + positveAverageDensity + " square plant are per grid for non-empty grids only\n";

        outputString += "\nFire Spread\n";
        for(int i = 0; i < fireSnapshots.length; i++)
        {
            outputString += "Time: " + i + "\n";
            outputString += fireSnapshots[i] + "\n\n";
        }
        return outputString;
    }
}