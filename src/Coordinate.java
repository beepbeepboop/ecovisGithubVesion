public class Coordinate
{
    private int x, y;
    public Coordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public boolean matches(Coordinate other) { return (x==other.getX() && y==other.getY()); }

    //Only used for unit vector cardinal points
    public double scaleFactor() { return (x != 0 && y != 0) ? 1/(Math.sqrt(2)) : 1; }

    @Override
    public String toString()
    {
        return "["+x+","+y+"]";
    }
}
