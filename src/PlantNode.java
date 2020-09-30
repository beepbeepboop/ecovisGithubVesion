import java.util.LinkedList;
import java.lang.Math;

public class PlantNode
{
	private int[] numSpc;
	private LinkedList<Plant> plants;
	private float rad;
	private String colour;

	public PlantNode(){plants = new LinkedList<Plant>();}

	public PlantNode(int numS)
	{
		numSpc = new int[numS];
		plants = new LinkedList<Plant>();
	}

	public void setNumSpc(int n){numSpc = new int[n];}
	public void add(Plant p)
	{
		plants.add(p);//numSpc[p.getID()]++;
	}

	public LinkedList<Plant> getPlants()
	{
		return plants;
	}

	public void calcVis()
	{
		double totalArea=0;
		for(int i=0; i<plants.size(); i++)
		{
			totalArea+=Math.pow(plants.get(i).getCanopyRadius(),2);
		}
		rad = (float)Math.sqrt(totalArea);
		/*
		int idForColour = 0;
		String colourForCirc = "";

		switch (idForColour)
		{
			case 0:
				colourForCirc = "#14467532";
				break;
			case 1:
				colourForCirc = "#c2828232";
				break;
			case 2:
				colourForCirc = "#d0000032";
				break;
			case 3:
				colourForCirc = "#6ae07732";
				break;
			case 4:
				colourForCirc = "#006b0a32";
				break;
			case 5:
				colourForCirc = "#ccff0032";
				break;
			case 10:
				colourForCirc = "#20405532";
				break;
			case 11:
				colourForCirc = "#a282a232";
				break;
			case 12:
				colourForCirc = "#d0700b32";
				break;
			case 13:
				colourForCirc = "#2ae03732";
				break;
			case 14:
				colourForCirc = "#a06b0c32";
				break;
			case 15:
				colourForCirc = "#ace00062";
				break;
			default:
				colourForCirc = "#ff008f32";

		}
		*/
		 colour = "#a06b0c32";
	}

	public String getColour(){return colour;}
	public float getRadius(){return rad;}
}