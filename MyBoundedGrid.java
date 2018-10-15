 import java.util.ArrayList;

/**
 * MyBoundedGrid is the grid on which the game is played
 * and on which the blocks exist. It is a rectangular grid
 * with a finite number of rows and columns.
 * 
 * @author  Susan King
 * @author  Megan Tjandrasuwita     Wrote implementation for the methods
 * @version January 5, 2017 
 * 
 * @param <E> the elements that may be put in the grid are any objects
 */
public class MyBoundedGrid<E>
{
    /**
     * The 2-D array that is used to store the grid's elements.
     */
    private Object[][] occupantArray; 

    /**
     * Constructs an empty MyBoundedGrid with the given dimensions.
     * 
     * @param rows  the grid's number of rows;  rows > 0 
     * @param cols  the grid's number of cols;  cols > 0
     */
    public MyBoundedGrid(int rows, int cols)
    {
        occupantArray = new Object[rows][cols];
    }

    /**
     * Retrieves the number of rows.
     * 
     * @return the grid's row count
     */
    public int getNumRows()
    {
        return occupantArray.length;
    }

    /**
     * Retrieves the number of columns.
     * 
     * @return the grid's columns count
     */
    public int getNumCols()
    {
        return occupantArray[0].length;
    }

    /**
     * Determines whether a location is valid.
     * 
     * @param  loc   the location in quesion.  loc != null
     * @return true  if loc is valid in this grid; otherwise, 
     *         false 
     */
    public boolean isValid(Location loc)
    {
        if (loc.getRow() >= 0 && loc.getRow() < getNumRows())
        {
            if (loc.getCol() >= 0 && loc.getCol() < getNumCols())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves an element from this grid at a location, or
     * null if the location is unoccupied.
     * 
     * @param loc is a valid location in this grid
     * 
     * @return the object at location loc 
     *         or null if the location is unoccupied
     */
    public E get(Location loc)
    {
        Object occupant = occupantArray[loc.getRow()][loc.getCol()];
        return (E) occupant;
    }

    /**
     * Puts an element at location loc on this grid.  Plus
     * returns the object previously at that location, or
     * null if the location is unoccupied.
     * 
     * @param loc is a valid location in this grid
     * @param obj  the object to put at location loc
     * 
     * @return the object at location loc 
     *         or null if the location is unoccupied
     */
    public E put(Location loc, E obj)
    {
        Object x = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        return (E) x;
    }

    /**
     * Removes an element from this grid at a location. Plus
     * returns the object previously at that location, or
     * null if the location is unoccupied.
     * 
     * @param loc is a valid location in this grid
     * 
     * @return the object that was at location loc 
     *         or null if the location is unoccupied
     */
    public E remove(Location loc)
    {
        Object x = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = null;
        return (E) x;
    }

    /**
     * Returns all the occupied location in this grid.
     * 
     * @return all the occupied locations in an arry list; 
     *         0 <= list.size < getNumRows() * getNumCols()
     */
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> x = new ArrayList<Location>();
        for (int r = 0; r < occupantArray.length; r++)
        {
            for (int c = 0; c < occupantArray[0].length; c++)
            {
                if (occupantArray[r][c] != null)
                {
                    x.add(new Location(r, c));
                }
            }
        }
        return x;
    }
}