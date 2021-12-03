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

    /**
     * Board() creates the board array and iterates through the array when called
     *
     */
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

    /**
     * getScore() is a getter method to get the total score value
     *
     * @return int totalScore so that it may be displayed at the top of the screen
     */
    public int getScore()
    {
        return totalScore;
    }

    /**
     * getHighTile() is a getter method to get the value of the highest tile in the board at the time of calling
     *
     * @return int highTile so that the value of the highest tile may be displayed at the top of the screen
     */
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

    /**
     * addTile() adds a new tile to the board every time a turn is taken
     * the tile is either a 2 or a 4 (generated randomly) and is placed in a random empty space on the board
     *
     */
    public void addTile()
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

    /**
     * fullBoard() checks if all the spaces on the board have been filled
     *
     * @return true if all the spaces have been taken up, will then either display the nudge to
     * restart the game or show GAME OVER
     * @return false if there are still empty spaces on the board
     */
    public boolean fullBoard()
    {
        int tileCount = 0;
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > 0 ) //looks through every tile and if the value is above 2, increase the count
                {
                    tileCount++;
                }
            }
        }
        if (tileCount == 16) //when the count is this number, return that the board is full
        {
            return true;
        }
        return false; //if the count does not reach 16, return that the board is not full
    }

    /**
     * gameOver() ends the game when no combinations can be made anymore (i.e. tile cannot combine
     * with the neighboring tile)
     *
     * @return true if none of the neighboring tiles can combine
     * @return false if the game can still go on and moves can still be made
     */
    public boolean gameOver()
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

    /**
     * up() calls the upDownMove method if the tiles are actually able to move up (by combining, or if
     * there is an empty space)
     * if the tiles cannot move up, it will not call the method
     *
     */
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

    /**
     * down() calls the upDownMove method if the tiles are actually able to move down (by combining, or if
     * there is an empty space)
     * if the tiles cannot move down, it will not call the method
     *
     */
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
    /**
     * left() calls the leftRightMove method if the tiles are actually able to move left (by combining, or if
     * there is an empty space)
     * if the tiles cannot move left, it will not call the method
     *
     */
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

    /**
     * right() calls the leftRightMove method if the tiles are actually able to move right (by combining, or if
     * there is an empty space)
     * if the tiles cannot move right, it will not call the method
     *
     */
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
    /**
     * leftRightMove() compares the values of two tiles
     *
     *
     * if the values of the tiles are the same or if the value of one of the tiles is
     * equal to 0 (signifying a plain tile), then the values of the tiles are added
     *
     * moves the tiles in the appropriate direction (left or right)
     *
     * @param row takes in the row location of the tile
     * @param column takes in the column location of the tile
     * @param direction takes in what direction the tile is supposed to shift
     *
     */
    private void leftRightMove( int row, int column, String direction )
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

    /**
     * upDowntMove() compares the values of two tiles
     *
     *
     * if the values of the tiles are the same or if the value of one of the tiles is
     * equal to 0 (signifying a plain tile), then the values of the tiles are added
     *
     * moves the tiles in the appropriate direction (up or down)
     *
     * @param row takes in the row location of the tile
     * @param column takes in the column location of the tile
     * @param direction takes in what direction the tile is supposed to shift
     *
     */
    private void upDownMove( int row, int column, String direction )
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