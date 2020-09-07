public class Species
{
    //Class Variables
    private int speciesID;
    private String commonName, latinName;

    //Constructors
    public Species(int id, String cName, String lName)
    {
        speciesID = id;
        commonName = cName;
        latinName = lName;
    }

    //Getters and Setters
    public int getSpeciesID(){return speciesID;}
    public String getCommonName(){return commonName;}
    public String getLatinName(){return latinName;}

    public void setSpeciesID(int id){speciesID = id;}
    public void setCommonName(String com){commonName = com;}
    public void setLatinName(String lat){latinName = lat;}


    //To String for debugging
    public String toString()
    {
        return new String(
        "Species Info:"+
        "\nCommon Name: "+commonName+
        "\nLatin Name: "+latinName+"\n");
    }
}