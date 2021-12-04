import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *@brief Plays the game 2048, where the user's objective is to create a tile with the value of 2048 by adding other
 * tiles together.  The user can use the "wasd" or the arrow keys to move the game board in all four directions.
 * This game assumes a nice user who is taking time to make each move, as pressing the buttons too quickly for
 * a long enough period of time will result in the game freezing.
 *
 * Our base code was the paint() and drawTiles() methods for graphics purposes.
 *
 * @Hanna Vaidya, @Allison Tesh, @Alexandra Zolman
 */


public class Game extends JPanel implements KeyListener
{
    // static variables
    // creates new board object (array, size of grid, sides variable, aand score are part of this ADT)
    Board board = new Board();
    //creates new game object
    //only needed for the key listener and content pane
    static Game game = new Game();
    // JFrame is needed to create the canvas without using stdDraw
    static JFrame jframe = new JFrame();


    // main method: starts the whole game
    public static void main( String[] args ) {
        //adding a key listener
        jframe.addKeyListener(game);
        //adding a canvas
        jframe.getContentPane().add(game);
        //size of whole screen (game board + any empty space/text)
        jframe.setSize( 600, 425 );
        //actually makes everything appear
        jframe.setVisible(true);
        //prevents user from changing size of canvas
        jframe.setResizable(false);
        //ends the program if the window is closed
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * paint() draws the entire board
     * this was part 1 of the base code for the project, but we also added some things to this
     *
     * @param g1 is the Graphics object needed to draw everything
     */
    public void paint(Graphics g1) //takes in a graphics object g1
    {
        //super references the parent class
        super.paint(g1);
        //creates a new graphics object g2 using g1
        Graphics2D g2 = (Graphics2D) g1;
        //adding the text to the board
        g2.drawString( "2048", 250, 20 );
        //updates as the player makes move to display score
        g2.drawString("Score: " + board.getScore(),200 - 4 * String.valueOf(board.getScore()).length(),40);
        //updates if there is a new max
        g2.drawString("Highest Tile: " + board.getHighTile(),280 - 4 * String.valueOf(board.getHighTile()).length(),40);
        //If statement that informs player that they have reached 2048, but can still continue.
        if (board.getHighTile() >= 2048) {
            g2.drawString("Congratulations!", 435, 90);
            g2.drawString("You reached 2048!", 429, 110);
            g2.drawString("Press 'Escape' to exit", 423, 130);
            g2.drawString("Press 'Enter' to restart", 421, 150);
            g2.drawString("Or, continue playing", 424, 170);
        }
        g2.drawString( "Press 'Enter' to Start", 210, 315 );
        g2.drawString( "Use 'wasd' or Arrow Keys to move", 159, 335 );
        if (board.fullBoard()) //if every tile on the board is full, prompts the user to restart but does NOT end the game
        {
            g2.drawString( "Press 'Enter' to restart", 200, 355 );
        }
        g2.setColor(new Color(230, 190, 210 )); //light pink borders
        g2.fillRect(140, 50, 250, 250); //begins drawing the board tiles
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                drawTiles(g1, board.board[i][j], j * 60 + 150, i * 60 + 60 );
            }
        }
        if (board.gameOver()) //if the game ends (i.e. user has no more available moves) this runs
        {
            g2.setColor(new Color(230, 190, 210 )); // light pink borders
            g2.fillRect( 140, 50, 250, 250);
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    g2.setColor(Color.RED); //fills in every square with red and writes game over on them
                    g2.fillRoundRect(j * 60 + 150, i * 60 + 60, 50, 50, 5, 5);
                    g2.setColor( Color.black );
                    g1.drawString( "GAME", j * 60 + 157, i * 60 + 80 );
                    g1.drawString( "OVER", j * 60 + 158, i * 60 + 100 );
                }
            }
            g2.drawString( "Press 'Escape' to quit", 200, 375 ); //shows user how to exit if they don't wish to play longer
        }
    }
    /**
     * drawTiles() draws each individual tile
     * this was part 2 of the base code for this project, and we also added some things to this
     *
     * @param g is the Graphics object needed to draw the tiles
     * @param tile represents the characteristics of the tiles being drawn
     * @param x is the x-coordinate where the tile will be drawn
     * @param y is the y-coordinate where the tile will be drawn
     */
    public void drawTiles( Graphics g, Tile tile, int x, int y )
    {
        int tileValue = tile.getValue(); //gets the number on the tile
        int length = String.valueOf( tileValue ).length(); //finds the number of characters on a tile square
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(150, 150, 210 ) ); // purple background for blank tiles
        g2.fillRoundRect( x, y, 50, 50, 5, 5 ); //round rectangle, which is the tile
        if ( tileValue > 0 ) //does not draw anything if the square has no value associated with it
        {
            g2.setColor( tile.getColor() ); //sets rectangle color, which is based on value
            g2.fillRoundRect( x, y, 50, 50, 5, 5 ); //draws tile with color
            g2.setColor( Color.black ); //changes to black for writing value
            g.drawString( "" + tileValue, x + 24 - 3 * length, y + 29 ); //writes on the tile, values are slightly off but take in length to try to even it out
        }
    }


    /**
     * keyPressed() checks to see what keys are pressed
     *
     * @param user represents the key that the user presses
     */

    public void keyPressed( KeyEvent user )
    {
        //if w or up is pressed
        if ( user.getKeyChar() == 'w' || user.getKeyCode() == KeyEvent.VK_UP )
        {
            board.up(); //move game board up
            board.addTile(); //add new tiles
            jframe.repaint(); //repaints the game board, updated with changes
        }
        //if s or down is pressed
        else if ( user.getKeyChar() == 's' || user.getKeyCode() == KeyEvent.VK_DOWN )
        {
            board.down();
            board.addTile();
            jframe.repaint();
        }
        // if a or left is pressed
        else if ( user.getKeyChar() == 'a' || user.getKeyCode() == KeyEvent.VK_LEFT )
        {
            board.left();
            board.addTile();
            jframe.repaint();
        }
        // if d or right is pressed
        else if ( user.getKeyChar() == 'd' || user.getKeyCode() == KeyEvent.VK_RIGHT )
        {
            board.right();
            board.addTile();
            jframe.repaint();
        }
        //restarts the game if the user hits enter
        else if ( user.getKeyCode() == KeyEvent.VK_ENTER )
        {
            board = new Board(); //removes all old tiles
            board.addTile(); //spawns 1 new tile
            board.addTile(); //spawns a second new tile
            jframe.repaint(); //repaints board
        }
        //if the user presses escape, the game closes
        else if ( user.getKeyCode() == KeyEvent.VK_ESCAPE ) {
            System.exit(0);
        }
    }

    /**
     * keyReleased() is needed for the key listener to run correctly
     *
     * @param user represents the key that the user presses
     */
    @Override
  public void keyReleased( KeyEvent user ) //checks if the key has been released
   {
         // method needed, but no additional code needed
   }

    /**
     * keyTyped() is needed for the key listener to run correctly
     *
     * @param user represents the key that the user presses
     */
    @Override
   public void keyTyped( KeyEvent user ) //checks whether a key has been pressed
   {
        // method needed, but no additional code needed
   }
}