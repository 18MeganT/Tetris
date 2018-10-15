import java.io.*;
import java.util.*;
/**
 * Creates the Tetris game.
 * 
 * @author Megan Tjandrasuwita
 * @version 12/29/2016
 */
public class Tetris implements ArrowListener
{
    private MyBoundedGrid<Block> gr;
    private BlockDisplay display;
    private Tetrad t;
    private int score;
    private int numCompletedRows;
    private int level;
    private String tetradName;

    /**
     * Constructor for objects of class Tetris
     */
    public Tetris()
    {
        score = 0;
        numCompletedRows = 0;
        level = 1;
        tetradName = "";
        gr = new MyBoundedGrid<Block>(20, 10);
        display = new BlockDisplay(gr);
        display.setTitle("Tetris - Level: " + level + 
            " - Score: " + score + 
            " - Next Tetrad: " + tetradName);
        display.setArrowListener(this);
        t = new Tetrad(gr);
        display.showBlocks();
        interactWithUser();
        if (t.getHasLost())
        {
            display.setTitle("You've lost, try again");
        }
        else
        {
            display.setTitle("You've won!! :D");
        }
    }

    /**
     * Rotates the tetrad 90 degrees counterclockwise if the
     * new locations are empty and valid.
     */
    public void upPressed()
    {
        t.rotate();
        display.showBlocks();
    }

    /**
     * Moves the tetrad one space down if the new locations 
     * are empty and valid.
     */
    public void downPressed()
    {
        t.translate(1, 0);
        display.showBlocks();
    }

    /**
     * Moves the tetrad one space to the left if the new locations
     * are empty and valid.
     */
    public void leftPressed()
    {
        t.translate(0, -1);
        display.showBlocks();
    }

    /**
     * Moves the tetrad one space to the right if the new locations
     * are empty and valid.
     */
    public void rightPressed()
    {
        t.translate(0, 1);
        display.showBlocks();
    }

    /**
     * Moves the tetrad as far down as possible.
     */
    public void spacePressed()
    {
        while (t.translate(1, 0))
        {
            //nothing here
        }
        display.showBlocks();
    }

    /**
     * Repeatedly pauses for 1 second, moves the active tetrad down, and redraws
     * the display. When the active tetrad cannot be shifted anymore, it creates
     * a new active tetrad
     */
    public void play()
    {
        while (level <= 4 && !t.getHasLost())
        {
            //determines the next tetrad here so that Tetris
            //knows which one is coming up
            int nextTetrad = (int)(Math.random() * 7);
            updateName(nextTetrad);
            if (level == 1)
            { 
                while (t.translate(1, 0))
                {
                    try
                    {
                        //Pause for 1000 milliseconds.
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        //ignore
                    }
                    display.showBlocks();
                }
            }
            else if (level == 2)
            { 
                while (t.translate(1, 0))
                {
                    try
                    {
                        //Pause for 1000 milliseconds.
                        Thread.sleep(800);
                    }
                    catch (InterruptedException e)
                    {
                        //ignore
                    }
                    display.showBlocks();
                }
            }
            else if (level == 3)
            { 
                while (t.translate(1, 0))
                {
                    try
                    {
                        //Pause for 1000 milliseconds.
                        Thread.sleep(650);
                    }
                    catch (InterruptedException e)
                    {
                        //ignore
                    }
                    display.showBlocks();
                }
            }
            else if (level == 4)
            { 
                while (t.translate(1, 0))
                {
                    try
                    {
                        //Pause for 1000 milliseconds.
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e)
                    {
                        //ignore
                    }
                    display.showBlocks();
                }
            }
            clearCompletedRows();
            t = new Tetrad(gr, nextTetrad);
            display.showBlocks();
        }
    }

    /**
     * Repeatedly pauses for 1 second, moves the active tetrad down, and redraws
     * the display. When the active tetrad cannot be shifted anymore, it creates
     * a new active tetrad. In the gravity variant, any floating blocks after
     * rows are cleared are dropped 
     */
    public void playWithGravity()
    {
        while (level <= 4 && !t.getHasLost())
        {
            //determines the next tetrad here so that Tetris
            //knows which one is coming up
            int nextTetrad = (int)(Math.random() * 7);
            updateName(nextTetrad);
            if (level == 1)
            { 
                while (t.translate(1, 0))
                {
                    try
                    {
                        //Pause for 1000 milliseconds.
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        //ignore
                    }
                    display.showBlocks();
                }
            }
            else if (level == 2)
            { 
                while (t.translate(1, 0))
                {
                    try
                    {
                        //Pause for 800 milliseconds.
                        Thread.sleep(800);
                    }
                    catch (InterruptedException e)
                    {
                        //ignore
                    }
                    display.showBlocks();
                }
            }
            else if (level == 3)
            { 
                while (t.translate(1, 0))
                {
                    try
                    {
                        //Pause for 650 milliseconds.
                        Thread.sleep(650);
                    }
                    catch (InterruptedException e)
                    {
                        //ignore
                    }
                    display.showBlocks();
                }
            }
            else if (level == 4)
            { 
                while (t.translate(1, 0))
                {
                    try
                    {
                        //Pause for 500 milliseconds.
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e)
                    {
                        //ignore
                    }
                    display.showBlocks();
                }
            }
            clearCompletedRows();
            //gravity effect
            while (dropFloatingBlocks())
            {
                clearCompletedRows();
            }
            t = new Tetrad(gr, nextTetrad);
            display.showBlocks();
        }
    }

    /**
     * Tests whether a every cell in a row is occupied
     * 
     * @param  row a row in the grid
     * @return true if every cell in a row is occupied; otherwise,
     *         false
     */
    private boolean isCompletedRow(int row)
    {
        for (int c = 0; c < 10; c++)
        {
            Location loc = new Location(row, c);
            if (gr.get(loc) == null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes a row and moves every block above the row down a row
     * 
     * @precondition the row is filled with blocks and row is
     *               within the range [0, number of rows)
     * @param row the row that is removed
     */
    private void clearRow(int row)
    {
        for (int i = 0; i < 10; i++)
        {
            Location loc = new Location(row, i);
            Block b = gr.get(loc);
            b.removeSelfFromGrid();
        }
        for (int r = row - 1; r >= 0; r--)
        {
            for (int c = 0; c < 10; c++)
            {
                Location loc = new Location(r, c);
                Block b = gr.get(loc);
                if (b != null)
                {
                    b.moveTo(new Location(r + 1, c));
                }
            }
        }
    }

    /**
     * Clears all completed rows, updates the score and level.
     */
    private void clearCompletedRows()
    {
        int count = 0;
        for (int r = 0; r < 20; r++)
        {
            if (isCompletedRow(r))
            {
                clearRow(r);
                count++;
            }
        }
        numCompletedRows += count;
        updateScoreAndLevel(count);
    }

    /**
     * Updates the score if necessary after a row(s) is cleared
     * based on the level. Afterwards, also update the level if necessary.
     * 
     * @param num the number of rows cleared after one move
     * 
     */
    private void updateScoreAndLevel(int num)
    {
        if (num == 1)
        {
            score += 40 * level;
        }
        else if (num == 2)
        {
            score += 100 * level;
        }
        else if (num == 3)
        {
            score += 300 * level;
        }
        else if (num == 4)
        {
            score += 1200 * level;
        }
        if (numCompletedRows >= 10)
        {
            level++;
            numCompletedRows = 0;
        }
        display.setTitle("Tetris - Level: " + level + 
            " - Score: " + score);
    }

    /**
     * Update tetrad name based on the random choice
     */
    private void updateName(int num)
    {
        if (num == 0)
        {
            tetradName = "I";
        }
        else if (num == 1)
        {
            tetradName = "T";
        }
        else if (num == 2)
        {
            tetradName = "O";
        }
        else if (num == 3)
        {
            tetradName = "L";
        }
        else if (num == 4)
        {
            tetradName = "J";
        }
        else if (num == 5)
        {
            tetradName = "S";
        }
        else if (num == 6)
        {
            tetradName = "Z";
        }
        display.setTitle("Tetris - Level: " + level + 
            " - Score: " + score + 
            " - Next Tetrad: " + tetradName);
    }

    /**
     * Drops down any floating block after all filled rows
     * have been cleared.
     * 
     * @return true if there are any floating blocks; otherwise,
     *         false
     */
    public boolean dropFloatingBlocks()
    {
        boolean isAnyFloating = false;
        for (int r = 18; r >= 0; r--)
        {
            for (int c = 0; c < 10; c++)
            {
                Location loc = new Location(r, c);
                Block x = gr.get(loc);
                if (x != null)
                {
                    Location newLoc = new Location(r + 1, c);
                    if (gr.get(newLoc) == null && !x.isAnchored())
                    {
                        while (gr.isValid(newLoc) && gr.get(newLoc) == null)
                        {
                            //show the floating block dropping down
                            try
                            {
                                //Pause for 800 milliseconds.
                                Thread.sleep(800);
                            }
                            catch (InterruptedException e)
                            {
                                //ignore
                            }
                            x.moveTo(newLoc);
                            display.showBlocks();
                            newLoc = new Location(newLoc.getRow() + 1,
                                newLoc.getCol());
                        }
                        isAnyFloating = true;
                    }
                }
            }
        }
        return isAnyFloating;       
    }

    /**
     * Allow the user to decide if they want to play regular
     * Tetris or Tetris with gravity variant.
     */
    public void interactWithUser()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Would you like to play regular Tetris or " + 
            "Tetris with gravity variant?\nType in 1 " +
            "for regular, 2 for the variant: ");
        try
        {
            if (in.nextInt() == 1)
            {
                System.out.println("You chose regular Tetris\n");
                play();
            }
            else 
            {
                System.out.println("You chose Tetris with gravity" +
                                      " variant\n");
                playWithGravity();
            }
        }
        catch (InputMismatchException e)
        {
            interactWithUser();
        }
    }
}
