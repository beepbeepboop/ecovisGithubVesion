import java.util.LinkedList;

public class Species
{
    //Class Variables
    private int speciesID, numPlants;
    private float minHeight, maxHeight, heightRatio;
    private String commonName, latinName;
    private LinkedList<Coordinate> coordinates;

    //Constructors
    public Species(int id, float min, float max, float hRatio, int num)
    {
        speciesID = id;
        minHeight = min;
        maxHeight = max;
        heightRatio = hRatio;
        numPlants = num;
        coordinates = new LinkedList<Coordinate>();
    }

    //Getters and Setters
    public int getSpeciesID(){return speciesID;}
    public int getNumPlants(){return numPlants;}
    public float getMinHeight(){return minHeight;}
    public float getMaxHeight(){return maxHeight;}
    public float getHeightRatio(){return heightRatio;}
    public String getCommonName(){return commonName;}
    public String getLatinName(){return latinName;}
    public LinkedList<Coordinate> getCoordinates(){return coordinates;}

    public void setSpeciesID(int id){speciesID = id;}
    public void setNumPlants(int num){numPlants = num;}
    public void setMinHeight(int min){minHeight = min;}
    public void setMaxHeight(int max){maxHeight = max;}
    public void setHeightRatio(int hRatio){heightRatio = hRatio;}
    public void setName(String com, String lat){commonName = com; latinName = lat;}
    public void addCoordinate(Coordinate c){coordinates.add(c);}

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