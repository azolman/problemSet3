import java.util.Random;

/**
 *
 *
 *
 */

public class Board
{
    public Tile[][] board; //2D array for board
    int grid = 4; //used because it's a 4x4 grid
    int border = 0; //used for customizing the board movementswhen moves are made
    public int score = 0; //starts score at 0, is accessed by other methods

    //here
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

    public int getScore() //gets score to put at the top
    {
        return score;
    }


    public int getHighTile() //checks for a high tile to display at the top
    {
        int high = board[0][0].getValue();
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > high ) //runs through entire 2x2 array and sees if the value of a tile is above the high
                {
                    high = board[i][j].getValue();
                }
            }
        }
        return high; //returns the high, whether it has changed or not
    }

    public String toString()
    {
        String s = "";
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                s += board[i][j].toString() + " "; //takes an empty string, gets the value written on the tile for all tiles
            }
            s += "\n"; //allows the value to be accessed as a string
        }
        return s; //returns the value
    }


    public void spawn() //adds new tile
    {
        boolean empty = true;
        while (empty) //for empty spaces
        {
            Random rand = new Random();
            int row = rand.nextInt(4);
            int col = rand.nextInt(4);
            double x = rand.nextDouble();

            if (board[row][col].getValue() == 0) //in a random spot
            {
                if ( x < 0.2 ) //if x is below this number, spawn a 4
                {
                    board[row][col] = new Tile(4);
                }
                else //spawns a 2
                {
                    board[row][col] = new Tile(2); //higher chance a 2 is spawned than a 4
                }
                empty = false; //removes that space from being used
            }

        }

    }

    public boolean fullBoard() //checks if all spaces on the board are filled
    {
        int count = 0;
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > 0 ) //looks through every tile and if the value is above 2, increase the count
                {
                    count++;
                }
            }
        }
        if ( count == 16 ) //when the count is this number, return that the board is full
        {
            return true;
        }
        return false; //if the count does not reach 16, return that the board is not full
    }


    public boolean gameOver() //game ends when no combinations can be made anymore (i.e. tile cannot combine with neighboring tile)
    {
        int count = 0;
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > 0 ) //for all spaces, if there is a tile there...
                {
                    if ( i == 0 && j == 0 ) //basically this runs through all the tile locations (in this case at (0,0))  and checks if the values can be added together
                    {
                        if ( board[i][j].getValue() != board[i + 1][j].getValue() //checks the value one to the right
                                && board[i][j].getValue() != board[i][j + 1].getValue() ) //checks the value one down
                        {
                            count++; //if values are not the same (i.e. cannot be added) increase the count
                        }
                    }
                    else if ( i == 0 && j == 3 ) //this is the same as above, but specific to space (0,3)
                    {
                        if ( board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 3 && j == 3 ) //see above
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 3 && j == 0 ) //see above
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 0 && ( j == 1 || j == 2 ) ) //because the tiles you look at are teh same whether j is 1 or j is 2, they can be combined
                    {
                        if ( board[i][j].getValue() != board[i + 1][j].getValue() //checks row the right
                                && board[i][j].getValue() != board[i][j + 1].getValue() //checks column to the right
                                && board[i][j].getValue() != board[i][j - 1].getValue() ) //checks column to the left
                        {
                            count++;
                        }
                    }
                    else if ( i == 3 && ( j == 1 || j == 2 ) ) //see above
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( j == 0 && ( i == 1 || i == 2 ) ) //see above
                    {
                        if ( board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( j == 3 && ( i == 1 || i == 2 ) ) //see above
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
        if (count == 16) //if the count is 16 (meaning that no tile is next to a tile of the same number), return that the game is over
        {
            return true;
        }
        return false; //otherwise return that the game can still go
    }

    public void up() //goes through with the w or the up arrow
    {
        for ( int i = 0; i < grid; i++ ) //basically ensures that this stays within the 4x4 grid
        {
            border = 0; //makes sure that the top row doesn't move (doesn't run the first iteration of the loop)
            for ( int j = 0; j < grid; j++ )
            {
                if ( board[j][i].getValue() != 0 ) //for all filled tiles
                {
                    if ( border <= j ) //for all rows not at the top
                    {
                        verticalMove( j, i, "up" ); //uses the vertical move method to move every tile with a value up one space
                    }
                }
            }
        }
    }

    public void down() //goes through with the s key or the down arrow
    {
        for ( int i = 0; i < grid; i++ )
        {
            border = ( grid - 1 ); //customizes method for moving everything down (makes sure the bottom row doesn't move down)
            for ( int j = grid - 1; j >= 0; j-- )
            {
                if ( board[j][i].getValue() != 0 ) //for all nonempty tiles
                {
                    if ( border >= j ) //why does this swap the row and column ??
                    {
                        verticalMove( j, i, "down" ); //moves everything down
                    }
                }
            }
        }
    }

    // compare the values of two tiles
    // if the values of the tiles are the same or if the value of one of the tiles is
    // equal to 0 (signifying a plain tile), then the values of the tiles are added
    // moves the tiles in the appropriate direction (up or down)
    private void verticalMove( int row, int col, String direction ) //actually executes the up or down movement
    {
        Tile initial = board[border][col]; //figures out what column
        Tile compare = board[row][col]; //compares to another space on the board
        if ( initial.getValue() == 0 || initial.getValue() == compare.getValue() ) //only runs if one of the tiles is 0 or if they are the same value
        {
            if ( row > border || ( direction.equals( "down" ) && ( row < border ) ) )
            {
                int addScore = initial.getValue() + compare.getValue();
                if ( initial.getValue() != 0 )
                {
                    score += addScore;
                }
                initial.setValue( addScore ); //changes the value of the initial box to double if one of the boxes is not zero
                compare.setValue( 0 ); //gets rid of the other box once added
            }
        }
        else
        {
            if (direction.equals( "down" ) ) //if they are not equal or one is not zero, simply move everything down
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