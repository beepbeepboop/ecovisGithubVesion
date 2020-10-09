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

    @Override
    public String toString()
    {
        return "["+x+","+y+"]";
    }
}
