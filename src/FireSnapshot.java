import java.util.ArrayList;
import java.util.HashMap;

public class FireSnapshot
{
    private ArrayList<Coordinate> burned;
    private ArrayList<Coordinate> burning;
    private HashMap<Integer, HashMap<Integer, Integer>> fireMap;

    public FireSnapshot(){}

    public FireSnapshot(ArrayList<Coordinate> burned, ArrayList<Coordinate> burning, HashMap<Integer, HashMap<Integer, Integer>> fireMap)
    {
        this.burned = burned;
        this.burning = burning;
        this.fireMap = fireMap;
    }

    public ArrayList<Coordinate> getBurned() { return burned; }
    public ArrayList<Coordinate> getBurning() { return burning; }
    public HashMap<Integer, HashMap<Integer, Integer>> getMap() { return fireMap; }

    @Override
    public String toString()
    {
        String temp = "Burned:\n";
        for(Coordinate c : burned)
        {
            temp += c;
        }
        temp += "\nBurning:\n";
        for(Coordinate c : burning) {
            temp += c;
            temp += "(";
            temp += fireMap.get(c.getX()).get(c.getY());
            temp += ")";
        }
        return temp;
    }
}
