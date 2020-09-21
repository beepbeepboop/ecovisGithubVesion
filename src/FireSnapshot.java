import java.util.ArrayList;

public class FireSnapshot
{
    private ArrayList<Coordinate> burned;
    private ArrayList<Coordinate> burning;

    public FireSnapshot(){}

    public FireSnapshot(ArrayList<Coordinate> burned, ArrayList<Coordinate> burning)
    {
        this.burned = burned;
        this.burning = burning;
    }

    public ArrayList<Coordinate> getBurned() { return burned; }
    public ArrayList<Coordinate> getBurning() { return burning; }

    @Override
    public String toString()
    {
        String temp = "Burned:\n";
        for(Coordinate c : burned)
        {
            temp += c;
        }
        temp += "\nBurning:\n";
        for(Coordinate c : burning)
        {
            temp += c;
        }
        return temp;
    }
}
