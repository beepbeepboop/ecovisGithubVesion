public class Species
{
    //Class Variables
    private int speciesID, numPlants;
    private float minHeight, maxHeight, heightRatio;
    private String commonName, latinName;

    //Constructors
    public Species(){}

    public Species(int id, float min, float max, float hRatio, int num)
    {
        speciesID = id;
        minHeight = min;
        maxHeight = max;
        heightRatio = hRatio;
        numPlants = num;
    }

    //Getters and Setters
    public int getSpeciesID(){return speciesID;}
    public int getNumPlants(){return numPlants;}
    public float getMinHeight(){return minHeight;}
    public float getMaxHeight(){return maxHeight;}
    public float getHeightRatio(){return heightRatio;}
    public String getCommonName(){return commonName;}
    public String getLatinName(){return latinName;}

    public void setSpeciesID(int id){speciesID = id;}
    public void setNumPlants(int num){numPlants = num;}
    public void setMinHeight(int min){minHeight = min;}
    public void setMaxHeight(int max){maxHeight = max;}
    public void setHeightRatio(int hRatio){heightRatio = hRatio;}
    public void setCommonName(String n){commonName = n;}
    public void setLatinName(String n){latinName = n;}
}