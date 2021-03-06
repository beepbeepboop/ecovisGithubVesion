public class Species
{
	//Class Variables
	private int speciesID, numCanopyPlants, numUnderGrowthPlants;
	private float minHeight, maxHeight, heightRatio;
	private String commonName, latinName;
	private String colour;

	private int underPos;
	private int canopyPos;

	//Constructors
	public Species(int id, float min, float max, float hRatio)
	{
		speciesID = id;
		minHeight = min;
		maxHeight = max;
		heightRatio = hRatio;
		numCanopyPlants = 0;
		numUnderGrowthPlants = 0;
		underPos = -1;
		canopyPos = -1;
	}
	public Species(int spcID, String cName, String lName){speciesID = spcID; commonName=cName; latinName=lName;}

	//Getters and Setters
	public int getSpeciesID(){return speciesID;}
	public int getNumCanopyPlants(){return numCanopyPlants;}
	public int getNumUnderGrowthPlants(){return numUnderGrowthPlants;}
	public String getCommonName(){return commonName;}
	public String getLatinName(){return latinName;}
	public String getColour(){return colour;}
	public int getUnderPos(){return underPos;}
	public int getCanopyPos(){return canopyPos;}

	public void setSpeciesID(int id){speciesID = id;}
	public void setNumCanopyPlants(int num){numCanopyPlants = num;}
	public void setNumUnderGrowthPlantsPlants(int num){numUnderGrowthPlants = num;}
	public void setName(String com, String lat){commonName = com; latinName = lat;}
	public void setCanopyPos(int pos){canopyPos = pos;}
	public void setUnderPos(int pos){underPos = pos;}
	public void setColour(String c){colour = c;}

	//To String for debugging
	public String toString()
	{
		return new String(
				"Species Info:"+
						"\nCommon Name: "+commonName+
						"\nLatin Name: "+latinName+
						"\nMin & Max Height: "+minHeight+" Min, "+maxHeight+" Max"+
						"\nCanopy to Height Ratio: "+heightRatio+"\n");
	}
}