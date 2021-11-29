import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * assumes a nice user who is actually trying to win the game
 * please do not press the buttons too quickly; the program will break :)
 */


// need to extend JPanel for graphics capability
// need to implement KeyListener for better keyboard capability (includes arrow and enter keys)
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
        frame.setSize( 600, 425 );
        frame.setVisible( true );
        frame.setResizable( false );
    }

// checks to see what keys are pressed
    public void keyPressed( KeyEvent user )
    {
        if ( user.getKeyChar() == 'w' || user.getKeyCode() == KeyEvent.VK_UP )
        {
            game.up();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
        else if ( user.getKeyChar() == 's' || user.getKeyCode() == KeyEvent.VK_DOWN )
        {
            game.down();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
        else if ( user.getKeyChar() == 'a' || user.getKeyCode() == KeyEvent.VK_LEFT )
        {
            game.left();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
        else if ( user.getKeyChar() == 'd' || user.getKeyCode() == KeyEvent.VK_RIGHT )
        {
            game.right();
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
        else if ( user.getKeyCode() == KeyEvent.VK_ENTER )
        {
            game = new Board();
            game.spawn();
            game.spawn();
            frame.repaint();
        }
        else if ( user.getKeyCode() == KeyEvent.VK_DELETE ) {
            System.exit(0);
        }
    }

    // method for the key listener to run correctly
    @Override
  public void keyReleased( KeyEvent user )
   {
         // method needed, but no additional code needed
   }

    // method for the key listener to run correctly
    @Override
   public void keyTyped( KeyEvent user )
   {
        // method needed, but no additional code needed
   }

    // paints the pop-up panel with all necessary game information (including graphics)
    // makes sure everything is repainted when game is over
    // JPanel is needed for this particular method
    public void paint(Graphics g1)
    {
        super.paint(g1);
        Graphics2D g2 = (Graphics2D) g1;
        g2.drawString( "2048", 250, 20 );
        g2.drawString("Score: " + game.getScore(),200 - 4 * String.valueOf(game.getScore()).length(),40);
        g2.drawString("Highest Tile: " + game.getHighTile(),280 - 4 * String.valueOf(game.getHighTile()).length(),40);
        g2.drawString( "Press 'Enter' to Start", 210, 315 );
        g2.drawString( "Use 'wasd' or Arrow Keys to move", 180, 335 );
        if (game.fullBoard())
        {
            g2.drawString( "Press 'Enter' to restart", 200, 355 );
        }
        g2.setColor( Color.gray );
        g2.fillRect( 140, 50, 250, 250 );
        for ( int i = 0; i < 4; i++ )
        {
            for ( int j = 0; j < 4; j++ )
            {
                drawTiles(g1, game.board[i][j], j * 60 + 150, i * 60 + 60 );
            }
        }
        if ( game.gameOver() )
        {
            g2.setColor( Color.gray );
            g2.fillRect( 140, 50, 250, 250 );
            for ( int i = 0; i < 4; i++ )
            {
                for ( int j = 0; j < 4; j++ )
                {
                    g2.setColor( Color.RED );
                    g2.fillRoundRect( j * 60 + 150, i * 60 + 60, 50, 50, 5, 5 );
                    g2.setColor( Color.black );
                    g1.drawString( "GAME", j * 60 + 160, i * 60 + 75 );
                    g1.drawString( "OVER", j * 60 + 160, i * 60 + 95 );
                }
            }
            g2.drawString( "Press 'Delete' to quit", 200, 375 );
        }
    }

    // draws an individual tile (called from the paint method)s
    // parameters are the graphics parameter, the tile parameter, and the (x,y) location
    public void drawTiles( Graphics g, Tile tile, int x, int y )
    {
        int tileValue = tile.getValue();
        int length = String.valueOf( tileValue ).length();
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor( Color.lightGray );
        g2.fillRoundRect( x, y, 50, 50, 5, 5 );
        g2.setColor( Color.black );
        if ( tileValue > 0 )
        {
            g2.setColor( tile.getColor() );
            g2.fillRoundRect( x, y, 50, 50, 5, 5 );
            g2.setColor( Color.black );
            g.drawString( "" + tileValue, x + 25 - 3 * length, y + 25 );
        }
    }
}