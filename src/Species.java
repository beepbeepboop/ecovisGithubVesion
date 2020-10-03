public class Species
{
    //Class Variables
    private int speciesID, numPlants;
    private float minHeight, maxHeight, heightRatio;
    private String commonName, latinName;
    private String colour;

    //Constructors
    public Species(int id, float min, float max, float hRatio, int num)
    {
        speciesID = id;
        minHeight = min;
        maxHeight = max;
        heightRatio = hRatio;
        numPlants = num;
    }
    public Species(String cName, String lName){commonName=cName; latinName=lName;}

    //Getters and Setters
    public int getSpeciesID(){return speciesID;}
    public int getNumPlants(){return numPlants;}
    public float getMinHeight(){return minHeight;}
    public float getMaxHeight(){return maxHeight;}
    public float getHeightRatio(){return heightRatio;}
    public String getCommonName(){return commonName;}
    public String getLatinName(){return latinName;}
    public String getColour(){return colour;}

    public void setSpeciesID(int id){speciesID = id;}
    public void setNumPlants(int num){numPlants = num;}
    public void setMinHeight(int min){minHeight = min;}
    public void setMaxHeight(int max){maxHeight = max;}
    public void setHeightRatio(int hRatio){heightRatio = hRatio;}
    public void setName(String com, String lat){commonName = com; latinName = lat;}
    public void populateSpc(int spcID, int numP, float minH, float maxH, float hr)
    {
        speciesID = spcID;
        numPlants = numP;
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
                "\nNumber of plants in this species: "+numPlants+
                "\nMin & Max Height: "+minHeight+" Min, "+maxHeight+" Max"+
                "\nCanopy to Height Ratio: "+heightRatio+"\n");
    }
}