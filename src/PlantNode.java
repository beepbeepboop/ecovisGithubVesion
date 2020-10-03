import java.util.LinkedList;
import java.lang.Math;

public class PlantNode
{
	private NodeSpc[] nodeSpcs;
	private float rad;
	private String colour;

	public PlantNode(){}

	public PlantNode(int numS)
	{
		nodeSpcs = new NodeSpc[numS];
		for(int i=0; i<numS; i++){nodeSpcs[i] = new NodeSpc(i);}
	}
	public void add(Plant p){nodeSpcs[p.getID()].add(p);}

	public LinkedList<Plant> getPlants(int spcID){return nodeSpcs[spcID].getPlants();}

	/*
	public void calcVis()
	{
		double totalArea=0;
		for(int i=0; i<plants.size(); i++)
		{
			totalArea+=Math.pow(plants.get(i).getCanopyRadius(),2);
		}
		rad = (float)Math.sqrt(totalArea);

		//Implement colour Calc
		colour = "#a06b0c32";
	}*/

	public String getColour(){return colour;}
	public float getRadius(){return rad;}
}