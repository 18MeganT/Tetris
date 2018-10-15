import java.awt.Color;
/**
 * Creates a tetrad of blocks
 * 
 * @author Megan Tjandrasuwita
 * @version 12/29/2016
 */
public class Tetrad
{
    private Block [] blocks;
    private MyBoundedGrid<Block> gr;
    private boolean hasLost;
    
    /**
     * Constructor for objects of class Tetrad
     * 
     * @param grid the MyBoundedGrid<Block> that Tetris takes place on.
     * @param choice an int that determines which tetrad will spawn
     */
    public Tetrad(MyBoundedGrid<Block> grid, int choice)
    {
        gr = grid;
        blocks = new Block[4];
        if (choice == 0)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.RED);
            }
            Location [] locs = {new Location(1, 4), new Location(0, 4), 
                                new Location(2, 4), new Location(3, 4)};
            hasLost = !notOverlapping(locs);
            addToLocations(gr, locs);
        }
        else if (choice == 1)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.GRAY);                
            }
            Location [] locs = {new Location(0, 4), new Location(0, 3),
                                new Location(0, 5), new Location(1, 4)};
            hasLost = !notOverlapping(locs);
            addToLocations(gr, locs);
        }
        else if (choice == 2)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.CYAN);                
            }
            Location [] locs = {new Location(0, 4), new Location(0, 5),
                                new Location(1, 4), new Location(1, 5)};
            hasLost = !notOverlapping(locs);
            addToLocations(gr, locs);
        }
        else if (choice == 3)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.YELLOW);                
            }
            Location [] locs = {new Location(1, 4), new Location(0, 4),
                                new Location(2, 4), new Location(2, 5)};
            hasLost = !notOverlapping(locs);
            addToLocations(gr, locs);
        }
        else if (choice == 4)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.MAGENTA);                
            }
            Location [] locs = {new Location(1, 5), new Location(0, 5),
                                new Location(2, 5), new Location(2, 4)};
            hasLost = !notOverlapping(locs);
            addToLocations(gr, locs);
        }
        else if (choice == 5)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();                
            }
            Location [] locs = {new Location(0, 4), new Location(0, 5),
                                new Location(1, 4), new Location(1, 3)};
            hasLost = !notOverlapping(locs);
            addToLocations(gr, locs);
        }
        else if (choice == 6)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.GREEN);                
            }
            Location [] locs = {new Location(0, 5), new Location(0, 4),
                                new Location(1, 5), new Location(1, 6)};
            hasLost = !notOverlapping(locs);
            addToLocations(gr, locs);
        }
    }
    
    /**
     * Constructor for objects of class Tetrad
     * 
     * @param grid the MyBoundedGrid<Block> that Tetris takes place on.
     */
    public Tetrad(MyBoundedGrid<Block> grid)
    {
        this(grid, (int)(Math.random() * 7));
    }

    /**
     * Determines whether the new tetrad will overlap with other blocks.
     * 
     * @param  locs the array of locations that the tetrad will occupy
     * @return true if all the elements at the locations in locs are null before
     *              the tetrad is added to the grid; otherwise,
     *         false
     */
    public boolean notOverlapping(Location [] locs)
    {
        for (int i = 0; i < 4; i++)
        { 
            if (gr.get(locs[i]) != null)
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns the value of hasLost
     * 
     * @return true if hasLost is true; otherwise,
     *         false
     */
    public boolean getHasLost()
    {
        return hasLost;
    }
    
    /**
     * Adds the blocks of a tetrad to certain locations.
     * 
     * @param  grid the grid on which tetris is played
     * @param  locs an array of locations where each block is placed.
     */
    private void addToLocations(MyBoundedGrid<Block> grid, Location [] locs)
    {
        for (int i = 0; i < 4; i++)
        {
            blocks[i].putSelfInGrid(grid, locs[i]);          
        }
    }

    /**
     * Removes the tetrad blocks from the grid and returns the lcoations
     * where the blocks were.
     * 
     * @precondition the tetrad blocks are in the grid
     * @return an array of Locations where the blocks were
     */
    private Location [] removeBlocks()
    {
        Location [] locs = new Location[blocks.length];
        for (int i = 0; i < blocks.length; i++)
        {
            if (blocks[i].getGrid() != null)
            {
                locs[i] = blocks[i].getLocation();
                blocks[i].removeSelfFromGrid();
            }
        }
        return locs;
    }

    /**
     * Determines whether the locations in locs are
     * valid and empty in the grid.
     * 
     * @return true if the locations in locs are valid
     *              and empty in the grid; otherwise,
     *         false
     */
    private boolean areEmpty(MyBoundedGrid<Block> grid,
    Location[] locs)
    {
        for (int i = 0; i < locs.length; i++)
        {
            if (!grid.isValid(locs[i]) || grid.get(locs[i]) != null)
            {
                return false;
            } 
        }
        return true;
    }

    /**
     * Moves the tetrad a certain number of rows down and a certain
     * number of columns to the right as long as the new positions are
     * valid and empty.
     * 
     * @return true if the tetrad was successfully translated; otherwise,
     *         false
     * @param deltaRow the number of rows  down
     * @param deltaCol the number of columns
     */
    public boolean translate(int deltaRow, int deltaCol)
    {
        Location [] newLocs = new Location[4];
        Location [] locs = removeBlocks();
        for (int i = 0; i < 4; i++)
        {
            Location l = locs[i];
            int row = l.getRow();
            int col = l.getCol();
            Location newLoc = new Location(row + deltaRow, col + deltaCol);
            newLocs[i] = newLoc;
        }
        if (areEmpty(gr, newLocs) == true)
        {
            addToLocations(gr, newLocs);
            return true;
        }
        addToLocations(gr, locs);
        return false;
    }

    /**
     * Rotates the tetrad 90 degrees clockwise around a certain pivot point.
     * 
     * @precondition the pivot point is the first position in the array of blocks.
     * @return true if the rotation is successful; otherwise,
     *         false
     */
    public boolean rotate()
    {
        Location [] newLocs = new Location[4];
        Location [] oldLocs = removeBlocks();
        Location center = oldLocs[0];
        int cRow = center.getRow();
        int cCol = center.getCol();
        for (int i = 0; i < 4; i++)
        {
            int row = oldLocs[i].getRow();
            int col = oldLocs[i].getCol();
            newLocs[i] = new Location(cRow - cCol + col, cRow + cCol - row);
        }
        if (areEmpty(gr, newLocs))
        {
            addToLocations(gr, newLocs);
            return true;
        }
        addToLocations(gr, oldLocs);
        return false;
    }
    
    
}
