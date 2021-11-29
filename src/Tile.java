import java.awt.Color;

/**
 *
 *
 *
 */

public class Tile
{
    int value;
    Color tileColor;

// constructs a plain tile with a value of 0 while will function as the blank tiles
    public Tile()
    {
        value = 0;
    }

    // constructs a tile with a value of integer parameter number
    public Tile(int num)
    {
        value = num;
    }

// getter method to return the tile's value
    public int getValue()
    {
        return value;
    }

    // setter method to set the tile's value
    // used when adding two tiles together
    public void setValue( int value )
    {
        this.value = value;
    }


// represents each tile as a string
    public String toString()
    {
        return "" + value;
    }


// setter method to assign each tile a color based on its value
// different valued tiles are set to different colors
    public void setColor()
    {
        if ( this.getValue() == 2 )
        {
            tileColor = new Color( 238, 228, 238 );
        }
        else if ( this.getValue() == 4 )
        {
            tileColor = new Color( 250, 224, 250 );
        }
        else if ( this.getValue() == 8 )
        {
            tileColor = new Color( 250, 190, 200 );
        }
        else if ( this.getValue() == 16 )
        {
            tileColor = new Color( 246, 160, 200 );
        }
        else if ( this.getValue() == 32 )
        {
            tileColor = new Color( 246, 124, 200 );
        }
        else if ( this.getValue() == 64 )
        {
            tileColor = new Color( 246, 0, 200 );
        }
        else if ( this.getValue() == 128 )
        {
            tileColor = new Color( 120, 150, 250 );
        }
        else if ( this.getValue() == 256 )
        {
            tileColor = new Color( 50, 120, 250 );
        }
        else if ( this.getValue() == 512 )
        {
            tileColor = new Color( 20, 150, 250 );
        }
        else if ( this.getValue() == 1024 )
        {
            tileColor = new Color( 20, 200, 250 );
        }
        else
        {
            tileColor = new Color( 20, 250, 250 );
        }
    }

// getter method to return the tile's color
    public Color getColor()
    {
        this.setColor();
        return tileColor;
    }
}