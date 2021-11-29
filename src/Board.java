import java.util.Random;

/**
 *
 *
 *
 */

public class Board
{
    public Tile[][] board;
    int grid = 4;
    int border = 0;
    public int score = 0;

    // default constructor for the board
    // sets up a 4x4 matrix for the board
    public Board()
    {
        board = new Tile[4][4];
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                board[i][j] = new Tile();
            }
        }
    }

    // *for testing*, this method sets up a board where player has already lost
    public Board( int lose, int grid )
    {
        this.grid = grid;
        board = new Tile[grid][grid];
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                board[i][j] = new Tile( ( lose + i + j ) * ( i + j ) );
            }
        }
    }

 // getter method to return the board
    public Tile[][] getBoard()
    {
        return board;
    }

    // getter method to return the score
    public int getScore()
    {
        return score;
    }

// getter method to return the highest-valued tile on the board
    public int getHighTile()
    {
        int high = board[0][0].getValue();
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > high )
                {
                    high = board[i][j].getValue();
                }
            }
        }
        return high;
    }

    // returns the board as a string
    public String toString()
    {
        String s = "";
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                s += board[i][j].toString() + " ";
            }
            s += "\n";
        }
        return s;
    }

// spawns a 2 or a 4 at an empty space in the board every time a turn is taken
    public void spawn()
    {
        boolean empty = true;
        while (empty)
        {
            Random rand = new Random();
            int row = rand.nextInt(4);
            int col = rand.nextInt(4);
            double x = rand.nextDouble();

            if (board[row][col].getValue() == 0)
            {
                if ( x < 0.2 )
                {
                    board[row][col] = new Tile(4);
                    empty = false;
                }
                else
                {
                    board[row][col] = new Tile(2);
                    empty = false;
                }
            }

        }

    }

    // checks to see if every space on the board is taken
    // if so, the board will give a suggestion to restart by pressing "Enter" to the player
    public boolean fullBoard()
    {
        int count = 0;
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > 0 )
                {
                    count++;
                }
            }
        }
        if ( count == 16 )
        {
            return true;
        }
        return false;
    }

    // checks to see if the game is over by checking to see if all tiles can combine with the
    // tiles next to it (if not, game is over)
    public boolean gameOver()
    {
        int count = 0;
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > 0 )
                {
                    if ( i == 0 && j == 0 )
                    {
                        if ( board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 0 && j == 3 )
                    {
                        if ( board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 3 && j == 3 )
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 3 && j == 0 )
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 0 && ( j == 1 || j == 2 ) )
                    {
                        if ( board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 3 && ( j == 1 || j == 2 ) )
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( j == 0 && ( i == 1 || i == 2 ) )
                    {
                        if ( board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( j == 3 && ( i == 1 || i == 2 ) )
                    {
                        if ( board[i][j].getValue() != board[i][j - 1].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue() )
                        {
                            count++;
                        }
                    }
                    else
                    {
                        if ( board[i][j].getValue() != board[i][j - 1].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue() )
                        {
                            count++;
                        }
                    }
                }
            }
        }
        if (count == 16)
        {
            return true;
        }
        return false;
    }

    // called when a "w" or the up arrow is pressed on the keyboard
    // goes through the entire board and calls the verticalMove method with an
    // "up" parameter for each tile
    public void up()
    {
        for ( int i = 0; i < grid; i++ )
        {
            border = 0;
            for ( int j = 0; j < grid; j++ )
            {
                if ( board[j][i].getValue() != 0 )
                {
                    if ( border <= j )
                    {
                        verticalMove( j, i, "up" );
                    }
                }
            }
        }
    }

    // called when an "s" or the down arrow is pressed on the keyboard
    // goes through the entire board and calls the verticalMove method with a "down"
    // parameter for each tile
    public void down()
    {
        for ( int i = 0; i < grid; i++ )
        {
            border = ( grid - 1 );
            for ( int j = grid - 1; j >= 0; j-- )
            {
                if ( board[j][i].getValue() != 0 )
                {
                    if ( border >= j )
                    {
                        verticalMove( j, i, "down" );
                    }
                }
            }
        }
    }

    // compare the values of two tiles
    // if the values of the tiles are the same or if the value of one of the tiles is
    // equal to 0 (signifying a plain tile), then the values of the tiles are added
    // moves the tiles in the appropriate direction (up or down)
    private void verticalMove( int row, int col, String direction )
    {
        Tile initial = board[border][col];
        Tile compare = board[row][col];
        if ( initial.getValue() == 0 || initial.getValue() == compare.getValue() )
        {
            if ( row > border || ( direction.equals( "down" ) && ( row < border ) ) )
            {
                int addScore = initial.getValue() + compare.getValue();
                if ( initial.getValue() != 0 )
                {
                    score += addScore;
                }
                initial.setValue( addScore );
                compare.setValue( 0 );
            }
        }
        else
        {
            if (direction.equals( "down" ) )
            {
                border--;
            }
            else
            {
                border++;
            }
            verticalMove( row, col, direction );
        }
    }

    // called when an "a" or the left arrow is pressed on the keyboard
    // goes through the entire board and calls the horizontalMove method with a
    // "left" parameter for each tile
    public void left()
    {
        for ( int i = 0; i < grid; i++ )
        {
            border = 0;
            for ( int j = 0; j < grid; j++ )
            {
                if ( board[i][j].getValue() != 0 )
                {
                    if ( border <= j )
                    {
                        horizontalMove( i, j, "left" );
                    }
                }
            }
        }
    }

    // called when a "d" or the right arrow is pressed on the keyboard
    // goes through the entire board and calls the horizontalMove method with
    // a "right" parameter for each tile
    public void right()
    {
        for ( int i = 0; i < grid; i++ )
        {
            border = ( grid - 1 );
            for ( int j = ( grid - 1 ); j >= 0; j-- )
            {
                if ( board[i][j].getValue() != 0 )
                {
                    if ( border >= j )
                    {
                        horizontalMove( i, j, "right" );
                    }
                }
            }
        }
    }


    // compare the values of two tiles
    // if the values of the tiles are the same or if the value of one of the tiles is
    // equal to 0 (signifying a plain tile), then the values of the tiles are added
    // moves the tiles in the appropriate direction (left or right)
    private void horizontalMove( int row, int col, String direction )
    {
        Tile initial = board[row][border];
        Tile compare = board[row][col];
        if ( initial.getValue() == 0 || initial.getValue() == compare.getValue() )
        {
            if ( col > border || ( direction.equals( "right" ) && ( col < border ) ) )
            {
                int addScore = initial.getValue() + compare.getValue();
                if ( initial.getValue() != 0 )
                {
                    score += addScore;
                }
                initial.setValue( addScore );
                compare.setValue( 0 );
            }
        }
        else
        {
            if ( direction.equals( "right" ) )
            {
                border--;
            }
            else
            {
                border++;
            }
            horizontalMove( row, col, direction );
        }
    }
}