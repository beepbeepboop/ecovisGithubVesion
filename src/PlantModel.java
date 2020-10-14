import java.util.HashMap;
import java.util.LinkedList;

public class PlantModel
{
	private ElevationGrid elevationGrid;
	private Plant[] undergrowthPlantList;
	private Plant[] canopyPlantList;
	private Species[] species;
	private LinkedList<Plant>[][] plants;
	private int dimX, dimY;
	private float gridSpacing;

	public PlantModel(ElevationGrid elevGrid, Plant[] undergrowthPlants, Plant[] canopyPlants, Species[] s)
	{
		elevationGrid = elevGrid;
		undergrowthPlantList = undergrowthPlants;
		canopyPlantList = canopyPlants;
		dimX = elevationGrid.getDimX();
		dimY = elevationGrid.getDimY();
		plants = new LinkedList[dimX][dimY];
		gridSpacing = elevationGrid.getGridSpacing();
		species = s;
		initialiseGrid();
		assignPlantsToGrid();
	}

	// Runs through grid and creates new linkedLists of Plant objects
	private void initialiseGrid()
	{
		for(int i = 0; i < dimX; i++)
		{
			for(int j = 0; j < dimY; j++) {plants[i][j] = new LinkedList<Plant>();}
		}
	}

	// Populates the gridPosition variable in the plant and adds a reference to the plant in the local grid
	private void assignPlantsToGrid()
	{
		for(Plant plant : undergrowthPlantList) {plants[(int)plant.getX()][(int)plant.getY()].add(plant);}
		for(Plant plant : canopyPlantList) {plants[(int)plant.getX()][(int)plant.getY()].add(plant);}
	}

	public int getDimX(){return dimX;}
	public int getDimY(){return dimY;}
	//public LinkedList<Plant> getPlants(int x, int y){return grid[x][y];}
	public float getGridSpacing(){return gridSpacing;}
	public LinkedList[][] getGrid(){return plants;}

}
