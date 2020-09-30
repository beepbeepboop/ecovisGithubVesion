import java.util.LinkedList;

public class PlantModel
{
	private ElevationGrid elevationGrid;
	private LinkedList<Plant> undergrowthPlantList;
	private LinkedList<Plant> canopyPlantList;
	private PlantNode[][] grid;
	private int dimX, dimY;
	private float gridSpacing;

	public PlantModel(ElevationGrid elevGrid, LinkedList<Plant> undergrowthPlants, LinkedList<Plant> canopyPlants)
	{
		elevationGrid = elevGrid;
		undergrowthPlantList = undergrowthPlants;
		canopyPlantList = canopyPlants;
		dimX = elevationGrid.getDimX();
		dimY = elevationGrid.getDimY();
		grid = new PlantNode[dimX][dimY];
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
				grid[i][j] = new PlantNode();
			}
		}
	}

	// Populates the gridPosition variable in the plant and adds a reference to the plant in the local grid
	private void assignPlantsToGrid()
	{

		for(Plant plant : undergrowthPlantList)
		{
			grid[(int)plant.getX()][(int)plant.getY()].add(plant);
		}
		for(Plant plant : canopyPlantList)
		{
			grid[(int)plant.getX()][(int)plant.getY()].add(plant);//changed
		}
	}

	public int getDimX(){return dimX;}
	public int getDimY(){return dimY;}
	//public LinkedList<Plant> getPlants(int x, int y){return grid[x][y];}
	public PlantNode getNode(int x, int y){return grid[x][y];}
}
