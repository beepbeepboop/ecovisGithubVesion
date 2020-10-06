import java.util.LinkedList;

public class NodeSpc
{
	int id;
	int amount;
	LinkedList<Plant> plants;

	NodeSpc(){amount=0; plants = new LinkedList<Plant>();}
	NodeSpc(int spcID){id = spcID; amount=0; plants = new LinkedList<Plant>();}
	public void add(Plant p)
	{
		plants.add(p);
		amount++;
	}

	public int getId(){return id;}
	public int getAmount() {return amount;}
	public LinkedList<Plant> getPlants() {return plants;}

}