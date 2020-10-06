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
    public Species(String cName, String lName){commonName=cName; latinName=lName;}

    //Getters and Setters
    public int getSpeciesID(){return speciesID;}
    public int getNumCanopyPlants(){return numCanopyPlants;}
    public int getNumUnderGrowthPlants(){return numUnderGrowthPlants;}
    public float getMinHeight(){return minHeight;}
    public float getMaxHeight(){return maxHeight;}
    public float getHeightRatio(){return heightRatio;}
    public String getCommonName(){return commonName;}
    public String getLatinName(){return latinName;}
    public String getColour(){return colour;}
    public int getUnderPos(){return underPos;}
    public int getCanopyPos(){return canopyPos;}

    public void setSpeciesID(int id){speciesID = id;}
    public void setNumCanopyPlants(int num){numCanopyPlants = num;}
    public void setNumUnderGrowthPlantsPlants(int num){numUnderGrowthPlants = num;}
    public void setMinHeight(int min){minHeight = min;}
    public void setMaxHeight(int max){maxHeight = max;}
    public void setHeightRatio(int hRatio){heightRatio = hRatio;}
    public void setName(String com, String lat){commonName = com; latinName = lat;}
    public void setCanopyPos(int pos){canopyPos = pos;}
    public void setUnderPos(int pos){underPos = pos;}

    public void populateSpc(int spcID, float minH, float maxH, float hr)
    {
        speciesID = spcID;
        minHeight = minH;
        maxHeight = maxH;
        heightRatio = hr;
    }
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