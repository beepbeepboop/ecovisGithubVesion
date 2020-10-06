
public class Plant
{
	//Class Variables
	private int speciesID;
	private float x, y, z;
	private float height, canopyRadius;

	private int xIndex;
	private int yIndex;

	private int visible;

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
		visible = 0;
	}

	//Getters and Setters
	public int getID(){return speciesID;}
	public float getX(){return x;}
	public float getY(){return y;}
	public float getZ(){return z;}
	public float getHeight(){return height;}
	public float getCanopyRadius(){return canopyRadius;}
	public int getXIndex(){return xIndex;}
	public int getYIndex(){return yIndex;}
	public boolean getVisible(){return visible ==0;}

	public void setID(int idSet){speciesID = idSet;}
	public void setX(float xSet){x = xSet;}
	public void setY(float ySet){y = ySet;}
	public void setZ(float zSet){z = zSet;}
	public void setHeight(float hSet){height = hSet;}
	public void setCanopyRadius(float crSet){canopyRadius = crSet;}
	public void setXIndex(int x){xIndex = x;}
	public void setYIndex(int y){yIndex = y;}
	public void incVisable(){
		visible++;}
	public void decVisable(){
		visible--;}

	//Test equals method
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
						"\nCanopy Radius: "+canopyRadius+
						"\nGrid Indices: ["+xIndex+","+yIndex+"]\n");
	}
}