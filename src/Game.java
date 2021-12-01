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
 * @Hanna Vaidya, @Allison Tesh, @Alexandra Zolman
 */


// check length variable for creating tiles
public class Game extends JPanel implements KeyListener
{
    // main method: starts the whole game
    public static void main( String[] args ) {
        setUp();
    }
    // static variables
    Board game = new Board();
    static Game newGame = new Game();
    // JFrame is needed to create the canvas without using stdDraw
    static JFrame frame = new JFrame( "2048" );
    String gameBoard = game.toString();

    // sets up the graphics
    // sets up the key listener
    public static void setUp()
    {
        frame.addKeyListener( newGame );
        frame.getContentPane().add( newGame );
        frame.setSize( 600, 425 ); //size of whole screen (game board + any empty space/text)
        frame.setVisible( true ); //actually makes everything appear
        frame.setResizable( false ); //prevents user from changing size of canvas
    }

// checks to see what keys are pressed
    public void keyPressed( KeyEvent user )
    {
        if ( user.getKeyChar() == 'w' || user.getKeyCode() == KeyEvent.VK_UP ) //if w or up is pressed
        {
            game.up(); //move game board up
            game.spawn(); //add new tiles
            gameBoard = game.toString(); //converts everything to strings so the numbers can be added
            frame.repaint(); //repaints the game board, updated with changes
        }
        else if ( user.getKeyChar() == 's' || user.getKeyCode() == KeyEvent.VK_DOWN ) //functions identically to above function, but moves game board down
        {
            game.down();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
        else if ( user.getKeyChar() == 'a' || user.getKeyCode() == KeyEvent.VK_LEFT ) //same as above, game board goes left
        {
            game.left();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
        else if ( user.getKeyChar() == 'd' || user.getKeyCode() == KeyEvent.VK_RIGHT ) //same as above, game board goes right
        {
            game.right();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
        else if ( user.getKeyCode() == KeyEvent.VK_ENTER ) //restarts the game if the user hits enter
        {
            game = new Board(); //removes all old tiles
            game.spawn(); //spawns 1 new tile
            game.spawn(); //spawns a second new tile
            frame.repaint(); //repaints board
        }
        else if ( user.getKeyCode() == KeyEvent.VK_ESCAPE ) { //if the user presses escape, the game closes
            System.exit(0);
        }
    }

    // method for the key listener to run correctly
    @Override
  public void keyReleased( KeyEvent user ) //checks if the key has been released
   {
         // method needed, but no additional code needed
   }

    // method for the key listener to run correctly
    @Override
   public void keyTyped( KeyEvent user ) //checks whether a key has been pressed
   {
        // method needed, but no additional code needed
   }

    public void paint(Graphics g1) //takes in a graphics object g1
    {
        super.paint(g1); //super references the parent class
        Graphics2D g2 = (Graphics2D) g1; //creates a new graphics object g2 using g1
        g2.drawString( "2048", 250, 20 ); //adding the text to the board
        g2.drawString("Score: " + game.getScore(),200 - 4 * String.valueOf(game.getScore()).length(),40); //updates as the player makes move to display score
        g2.drawString("Highest Tile: " + game.getHighTile(),280 - 4 * String.valueOf(game.getHighTile()).length(),40); //updates if there is a new max
        //If statement that informs player that they have reached 2048, but can still continue.
        if (game.getHighTile() >= 2048) {
            g2.drawString("Congratulations!", 435, 90);
            g2.drawString("You reached 2048!", 429, 110);
            g2.drawString("Press 'Escape' to exit", 423, 130);
            g2.drawString("Press 'Enter' to restart", 421, 150);
            g2.drawString("Or, continue playing", 424, 170);
        }
        g2.drawString( "Press 'Enter' to Start", 210, 315 );
        g2.drawString( "Use 'wasd' or Arrow Keys to move", 159, 335 );
        if (game.fullBoard()) //if every tile on the board is full, prompts the user to restart but does NOT end the game
        {
            g2.drawString( "Press 'Enter' to restart", 200, 355 );
        }
        g2.setColor(Color.gray);
        g2.fillRect(140, 50, 250, 250); //begins drawing the board tiles
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                drawTiles(g1, game.board[i][j], j * 60 + 150, i * 60 + 60 );
            }
        }
        if (game.gameOver()) //if the game ends (i.e. user has no more available moves) this runs
        {
            g2.setColor(Color.gray);
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

    public void drawTiles( Graphics g, Tile tile, int x, int y ) //for actually drawing the tiles, takes in a graphics and tiles object, as well as a coordinate
    {
        int tileValue = tile.getValue(); //gets the number on the tile
        int length = String.valueOf( tileValue ).length(); //finds the number of characters on a tile square
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor( Color.lightGray );
        g2.fillRoundRect( x, y, 50, 50, 5, 5 ); //round rectangle, which is the tile
        if ( tileValue > 0 ) //does not draw anything if the square has no value associated with it
        {
            g2.setColor( tile.getColor() ); //sets rectangle color, which is based on value
            g2.fillRoundRect( x, y, 50, 50, 5, 5 ); //draws tile with color
            g2.setColor( Color.black ); //changes to black for writing value
            g.drawString( "" + tileValue, x + 24 - 3 * length, y + 29 ); //writes on the tile, values are slightly off but take in length to try to even it out
        }
    }
}