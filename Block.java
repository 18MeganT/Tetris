import java.awt.Color;
/**
 * Block class maintains information about a block.
 * This class is extremely flexible to multiple games;
 * such as Tile Game, Tetris, and Tic-Tac-Toe.
 *
 * @author  Susan King     Added documentation
 * @author  Megan Tjandrasuwita      Wrote implementation for the methods
 * @version January 5, 2017
 */
public class Block
{
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;
    private String text;

    /**
     * Constructs a blue block.
     */
    public Block()
    {
        color = Color.BLUE;
        grid = null;
        location = null;
    }

    /**
     * Gets the color of this block.
     * 
     * @return the color of this block
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Sets the color of this block to newColor.
     * 
     * @param newColor  the new color of this block
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * Gets the grid of this block, or null if this block is not contained
     * in a grid.
     * 
     * @return the grid
     */
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }

    /**
     * Gets the location of this block, or null if this block is not contained
     * in a grid.
     * 
     * @return this block's location, or null if this block is not in the grid
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Removes this block from its grid.
     *
     * @precondition  this block is contained in a grid
     */
    public void removeSelfFromGrid()
    {
        grid.remove(location);
        grid = null;
        location = null;
    }

    /**
     * Puts this block into location loc of grid gr.
     * If there is another block at loc, it is removed.
     * 
     * @precondition  (1) this block is not contained in a grid
     *                (2) loc is valid in gr
     *               
     * @param gr  the grid to place this block
     * @param loc the location to place this block
     */
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        Object x = gr.get(loc);
        if (x != null)
        {
            ((Block) x).removeSelfFromGrid();
        }
        gr.put(loc, this);
        grid = gr;
        location = loc;
    }

    /**
     * Moves this block to newLocation.
     * If there is another block at newLocation, it is removed.
     *
     * @precondition  (1) this block is contained in a grid
     *                (2) newLocation is valid in the grid of this block
     *                
     * @param newLocation  the location that the block is to be moved
     */
    public void moveTo(Location newLocation)
    {
        MyBoundedGrid<Block> g = grid;
        removeSelfFromGrid();
        putSelfInGrid(g, newLocation);
    }

    /**
     * Returns a string with the location and color of this block.
     * 
     * @return location and color information about the block
     */
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }

    /**
     * Determines whether a block is anchored
     * 
     * @precondition there is no block occupying the location below
     *               the current block's location
     * @return true if the block is connected to other blocks 
     *              preventing it from falling; otherwise, false
     *         
     */
    public boolean isAnchored()
    {     
        if (checkAllLeft(location) || checkAllRight(location))
        {
            return true;
        }
        Location above = new Location(location.getRow() - 1, location.getCol());
        Location left = new Location(location.getRow(), location.getCol() - 1);
        Location right = new Location(location.getRow(), location.getCol() + 1);
        //this if statement is to deal with exceptions that 
        //usually occur with blocks from s and z tetrads
        if (grid.isValid(above) && (grid.get(above) == null ||
            grid.get(above).getColor() != this.color))
        {
            Location a = new Location(location.getRow() + 1,
                                      location.getCol() + 1);
            Location b = new Location(location.getRow() + 1,
                                      location.getCol() - 1);
            if (checkRight(location) && grid.get(a).getColor() == this.color)
            {
                return sideBySideTest(location);
            }
            if (checkLeft(location) && grid.get(b).getColor() == this.color)
            {
                return sideBySideTest(location);
            }
        }
        while (grid.isValid(above) && grid.get(above) !=  null
                && grid.get(above).getColor() == this.color)
        {
            if (checkAllLeft(above) || checkAllRight(above))
            {
                return true;
            }
            above = new Location(above.getRow() - 1, above.getCol());
        }
        return false;
    }

    /**
     * Checks the location to the left of a certain location 
     * with a block, and if the location is valid and contains a block,
     * check if the block is the same color. 
     * 
     * @param  loc a Location
     * @return true if the block to the left is the same color
     *              otherwise, false
     */
    public boolean checkLeft(Location loc)
    {
        Block b = grid.get(loc);
        Location left = new Location(loc.getRow(), loc.getCol() - 1);
        if (grid.isValid(left) && grid.get(left) != null)
        {
            Block l = grid.get(left);
            if (l.getColor() == b.getColor())
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Keeps checking the locations to the left of a certain location 
     * as long as those locations have a block of the same color as the
     * block on the current location. Checks there is an anchor beneath 
     * at least one of these locations.
     * 
     * @param  loc a Location
     * @return true if at least one block of the same color to the left
     *              has an anchor beneath it; otherwise, false
     */
    public boolean checkAllLeft(Location loc)
    {
        while (checkLeft(loc))
        {
            Location left = new Location(loc.getRow(), loc.getCol() - 1);
            if (isAnchorBelow(left))
            {
                return true;
            }
            loc = new Location(loc.getRow(), loc.getCol() - 1);
        }
        return false;
    }
    
    /**
     * Check the location to the right of a certain location 
     * with a block, and if the location is valid and contains a block,
     * check if the block is the same color. 
     * 
     * @param  loc a Location
     * @return true if the block to the right is the same color
     *              otherwise, false
     */
    public boolean checkRight(Location loc)
    {
        Block b = grid.get(loc);
        Location right = new Location(loc.getRow(), loc.getCol() + 1); 
        if (grid.isValid(right) && grid.get(right) != null)
        {
            Block r = grid.get(right);
            if (r.getColor() == b.getColor())
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Keeps checking the locations to the right of a certain location 
     * as long as those locations have a block of the same color as the
     * block on the current location. Checks there is an anchor beneath 
     * at least one of these locations.
     * 
     * @param  loc a Location
     * @return true if at least one block of the same color to the right
     *              has an anchor beneath it; otherwise, false
     */
    public boolean checkAllRight(Location loc)
    {
        while (checkRight(loc))
        {
            Location right = new Location(loc.getRow(), loc.getCol() + 1);
            if (isAnchorBelow(right))
            {
                return true;
            }
            loc = new Location(loc.getRow(), loc.getCol() + 1);
        }
        return false;
    }
    
    /**
     * Checks if there is a block just below a block at a certain location
     * 
     * @precondition there is a block at location l
     * @param  loc a location with a block
     * @return true if there is a block of a different color (acts
     *         like an anchor) below the block at location l; otherwise,
     *         false
     *         
     */
    public boolean isAnchorBelow(Location loc)
    {
        if (loc.getRow() == 19)
        {
            return true;
        }
        Block b1 = grid.get(loc);
        Location below = new Location(loc.getRow() + 1, loc.getCol());
        if (grid.get(below) != null)
        {
            return true;
        }
        return false;
    }
    
    /**
     * If the block to the left or the right that is the same 
     * color as the current block is anchored, then the current 
     * block is also anchored
     * 
     * @param  loc a location with a block
     * @return true if the block is anchored; otherwise,
     *         false
     *         
     */
    public boolean sideBySideTest(Location loc)
    {
        Location left = new Location(loc.getRow(), loc.getCol() - 1);
        Location right = new Location(loc.getRow(), loc.getCol() + 1);
        if (checkLeft(location))
        {
            if (grid.get(left).isAnchored())
            {
                return true;
            }
        }
        if (checkRight(location))
        {
            if (grid.get(right).isAnchored())
            {
                return true;
            }
        }
        return false;
    }
}