
import java.util.ArrayList;
import java.util.HashMap;


// need an update function to check for which node to update
// also how to handle edge cases like the corners, especially the starting value
public class Node
{
    // private Node previous;
    // private Node next;
    private int number; 

    //
    private Node north;
    private Node south;
    private Node east;
    private Node west;
    private int col;
    private int row;
    // 

    // Node() { this.previous = this.next = null; this.number = -1; }
    Node () { this.north = this.south = this.east = this.west = null; this.number = -1;}
    // Node (Node previous, Node next, int number)
    // {
    //     this.previous = previous;
    //     this.next = next;
    //     this.number = number;
    // }

    // public Node getPrevious() { return this.previous; }
    // public void setPrevious(Node previous) { this.previous = previous; }

    // public Node getNext() { return this.next; }
    // public void setNext(Node next) { this.next = next; }

    public int getNumber() { return this.number; }
    public void setNumber(int number) { this.number = number; }


    public int getCol() { return this.col; }
    public void updateCol(int col) { this.col = col; }

    public int getRow() { return this.row; }
    public void updateRow(int row) { this.row = row; }

    public Node peekNorth() { return this.north; }
    public void updateNorth(Node north) { this.north = north; }

    public Node peekSouth() { return this.south; }
    public void updateSouth(Node south) { this.south = south; }

    public Node peekEast() { return this.east; }
    public void updateEast(Node east) { this.east = east; }
    
    public Node peekWest() { return this.west; }
    public void updateWest(Node west) { this.west = west; }

    public boolean loopCheck(Node another)
    {
        if ( another == this.north 
        || another == this.south 
        || another == this.east 
        || another == this.west ) { return true; }

        // else
        return false;
    }

    public int getDirection(Node another)
    {
        // edge case with the bottom right node ?

        int another_col = another.getCol();
        int another_row = another.getRow();

        if ( this.col == another_col )
        {
            if ( this.row > another_row ) { return 1; } // north
            else if ( this.row < another_row ) { return 3; } // south
        }
        else if ( this.row == another_row )
        {
            if ( this.col > another_col ) { return 4; } // west
            else if( this.col < another_col ) { return 2; } // east
        }
        
        // none match which is a hella problem
        return -1; 
    }


}