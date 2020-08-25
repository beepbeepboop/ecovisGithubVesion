public class Species
{
    //Class Variables
    private int speciesID;
    private String commonName, latinName;

    //Constructors
    public Species(){}

    public Species(int id, String cName, String lName)
    {
        speciesID = id;
        commonName = cName;
        latinName = lName;
    }

    //Getters and Setters
    public int getID(){return speciesID;}
    public String getCommonName(){return commonName;}
    public String getLatinName(){return latinName;}

    public void setID(int id){speciesID = id;}
    public void setCommonName(String n){commonName = n;}
    public void setLatinName(String n){latinName = n;}
}