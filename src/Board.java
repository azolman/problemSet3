import java.util.Random;

/**
 * @brief This file controls the movement of the board (i.e. up, down, left, right/ horizontal and down),
 * as well as containing the methods that get the score, high tiles, create new tiles, and check
 * the state of anything on the board.
 * @Alexandra Zolman, @Allison Tesh, @Hanna Vaidya
 */

public class Board
{
    public Tile[][] board; //2D array for board
    int gameGrid = 4; //used because it's a 4x4 grid
    int sides = 0; //used for customizing the board movements when moves are made, used to make sure the tiles never move out of the array
    public int totalScore = 0; //starts score at 0, is accessed by other methods

    public Board()
    {
        board = new Tile[4][4]; //creates a new 4x4 array
        for ( int i = 0; i < board.length; i++ ) //runs through the rows and columns
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                board[i][j] = new Tile(); //populates the array
            }
        }
    }

    public int getScore() //gets score to put at the top
    {
        return totalScore;
    }

    public int getHighTile() //checks for a high tile to display at the top
    {
        int highTile = board[0][0].getValue();
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > highTile ) //runs through entire 2x2 array and sees if the value of a tile is above the high
                {
                    highTile = board[i][j].getValue();
                }
            }
        }
        return highTile; //returns the high, whether it has changed or not
    }

    public void addTile() //adds new tile
    {
        boolean empty = true;
        while (empty) //for empty spaces
        {
            Random rand = new Random();
            int row = rand.nextInt(4);
            int col = rand.nextInt(4);
            double x = rand.nextDouble();

            // randomly adds a 2 or 4 in a random space that has not been filled
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
        if (count == 16) //when the count is this number, return that the board is full
        {
            return true;
        }
        return false; //if the count does not reach 16, return that the board is not full
    }


    public boolean gameOver() //game ends when no combinations can be made anymore (i.e. tile cannot combine with neighboring tile)
    {
        int tileCount = 0;
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
                            tileCount++; //if values are not the same (i.e. cannot be added) increase the count
                        }
                    }
                    else if ( i == 0 && j == 3 ) //this is the same as above, but specific to space (0,3)
                    {
                        if ( board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            tileCount++;
                        }
                    }
                    else if ( i == 3 && j == 3 ) //see above
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            tileCount++;
                        }
                    }
                    else if ( i == 3 && j == 0 ) //see above
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue() )
                        {
                            tileCount++;
                        }
                    }
                    else if ( i == 0 && ( j == 1 || j == 2 ) ) //because the tiles you look at are teh same whether j is 1 or j is 2, they can be combined
                    {
                        if ( board[i][j].getValue() != board[i + 1][j].getValue() //checks row the right
                                && board[i][j].getValue() != board[i][j + 1].getValue() //checks column to the right
                                && board[i][j].getValue() != board[i][j - 1].getValue() ) //checks column to the left
                        {
                            tileCount++;
                        }
                    }
                    else if ( i == 3 && ( j == 1 || j == 2 ) ) //see above
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            tileCount++;
                        }
                    }
                    else if ( j == 0 && ( i == 1 || i == 2 ) ) //see above
                    {
                        if ( board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue() )
                        {
                            tileCount++;
                        }
                    }
                    else if ( j == 3 && ( i == 1 || i == 2 ) ) //see above
                    {
                        if ( board[i][j].getValue() != board[i][j - 1].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue() )
                        {
                            tileCount++;
                        }
                    }
                    else
                    {
                        if ( board[i][j].getValue() != board[i][j - 1].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue() )
                        {
                            tileCount++;
                        }
                    }
                }
            }
        }
        if (tileCount == 16) //if the count is 16 (meaning that no tile is next to a tile of the same number), return that the game is over
        {
            return true;
        }
        return false; //otherwise return that the game can still go
    }
    public void up() //goes through with the w or the up arrow
    {
        for ( int i = 0; i < gameGrid; i++ ) //basically ensures that this stays within the 4x4 grid
        {
            sides = 0; //makes sure that the top row doesn't move (doesn't run the first iteration of the loop)
            for ( int j = 0; j < gameGrid; j++ )
            {
                if ( board[j][i].getValue() != 0 ) //for all filled tiles
                {
                    if ( sides <= j ) //for all rows not at the top
                    {
                        upDownMove( j, i, "up" ); //uses the vertical move method to move every tile with a value up one space
                    }
                }
            }
        }
    }
    public void down() //goes through with the s key or the down arrow
    {
        for ( int i = 0; i < gameGrid; i++ )
        {
            sides = ( gameGrid - 1 ); //customizes method for moving everything down (makes sure the bottom row doesn't move down)
            for ( int j = gameGrid - 1; j >= 0; j-- )
            {
                if ( board[j][i].getValue() != 0 ) //for all nonempty tiles
                {
                    if ( sides >= j )
                    {
                        upDownMove( j, i, "down" ); //moves everything down
                    }
                }
            }
        }
    }

    public void left() // called when an "a" or the left arrow is pressed on the keyboard
    {
        for ( int i = 0; i < gameGrid; i++ )
        {
            sides = 0;
            for ( int j = 0; j < gameGrid; j++ ) //runs through entire board and shifts all nonzero tiles left
            {
                if ( board[i][j].getValue() != 0 )
                {
                    if ( sides <= j )
                    {
                        leftRightMove( i, j, "left" ); //horizontal move function, input = left
                    }
                }
            }
        }
    }

    public void right()  // called when a "d" or the right arrow is pressed on the keyboard
    {
        for ( int i = 0; i < gameGrid; i++ ) //runs the same way as the left method, but shifts nonzero tiles to the right instead
        {
            sides = ( gameGrid - 1 );
            for ( int j = ( gameGrid - 1 ); j >= 0; j-- )
            {
                if ( board[i][j].getValue() != 0 )
                {
                    if ( sides >= j )
                    {
                        leftRightMove( i, j, "right" );
                    }
                }
            }
        }
    }

    // compare the values of two tiles
    // if the values of the tiles are the same or if the value of one of the tiles is
    // equal to 0 (signifying a plain tile), then the values of the tiles are added
    // moves the tiles in the appropriate direction (left or right)
    private void leftRightMove( int row, int column, String direction ) //takes in location and direction of shift
    {
        Tile initial = board[row][sides];
        Tile compare = board[row][column];
        if ( initial.getValue() == 0 || initial.getValue() == compare.getValue() ) //if the initial tile is a zero or is the same as the one it is compared to
        {
            if ( column > sides || ( direction.equals( "right" ) && ( column < sides ) ) )
            {
                int addScore = initial.getValue() + compare.getValue();
                if ( initial.getValue() != 0 )
                {
                    totalScore += addScore; //add scores
                }
                initial.setValue( addScore );
                compare.setValue( 0 ); //essentially gets rid of the other tile
            }
        }
        else //if they are not equal or one is not zero, simply move everything down
        {
            if ( direction.equals( "right" ) )
            {
                sides--;
            }
            else
            {
                sides++;
            }
            leftRightMove( row, column, direction );
        }
    }

    private void upDownMove( int row, int column, String direction ) //actually executes the up or down movement
    {
        Tile initial = board[sides][column]; //figures out what column
        Tile compare = board[row][column]; //compares to another space on the board
        if ( initial.getValue() == 0 || initial.getValue() == compare.getValue() ) //only runs if one of the tiles is 0 or if they are the same value
        {
            if ( row > sides || ( direction.equals( "down" ) && ( row < sides ) ) )
            {
                int addScore = initial.getValue() + compare.getValue();
                if ( initial.getValue() != 0 )
                {
                    totalScore += addScore; //updates score if the tiles have values
                }
                initial.setValue( addScore ); //changes the value of the initial box to double if one of the boxes is not zero
                compare.setValue( 0 ); //gets rid of the other box once added
            }
        }
        else
        {
            if (direction.equals( "down" ) ) //if they are not equal or one is not zero, simply move everything down
            {
                sides--;
            }
            else
            {
                sides++;
            }
            upDownMove( row, column, direction );
        }
    }

}