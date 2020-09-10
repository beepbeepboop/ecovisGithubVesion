
public class Plant
{
    //Class Variables
    private int speciesID;
    private float x, y, z;
    private float height, canopyRadius;

    //Constructors
    public Plant(){}

    public Plant(int id, float xPos, float yPos, float zPos, float h, float canopyR)
    {
        speciesID = id;
        x = xPos;
        y = yPos;
        z = zPos;
        height = h;
        canopyRadius = canopyR;
    }

    //Getters and Setters
    public int getID(){return speciesID;}
    public float getX(){return x;}
    public float getY(){return y;}
    public float getZ(){return z;}
    public float getHeight(){return height;}
    public float getCanopyRadius(){return canopyRadius;}

    public void setID(int idSet){speciesID = idSet;}
    public void setX(float xSet){x = xSet;}
    public void setY(float ySet){y = ySet;}
    public void setZ(float zSet){z = zSet;}
    public void setHeight(float hSet){height = hSet;}
    public void setCanopyRadius(float crSet){canopyRadius = crSet;}


    public boolean equals(Plant p)
    {
        if(x == p.x){return true;}
        return false;
    }

    //To String for debugging
    public String toString()
    {
        return new String(
        "Plant Info:"+
        "\nSpecies ID: "+speciesID+
        "\nPosition: "+x+"x, "+y+"y, "+z+"z"+
        "\nHeight: "+height+
        "\nCanopy Radius: "+canopyRadius+"\n");
    }
}