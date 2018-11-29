
// Node.java
// contains the jump value, row & col info, and neighboring edges
// Howard Cheung

public class Node
{
    private int number; 
    private int col;
    private int row;

    private Node north;
    private Node south;
    private Node east;
    private Node west;

    private Edge northEdge;
    private Edge southEdge;
    private Edge eastEdge;
    private Edge westEdge;

    Node (int row, int col, int number) 
    {
        this.row = row; this.col = col; this.number = number; 

        this.north = this.south = this.east = this.west = null;
        this.northEdge = this.southEdge = this.eastEdge = this.westEdge = null; 
    }

    // member getters and setters
    public int getNumber() { return this.number; }
    public void setNumber(int number) { this.number = number; }

    public int getCol() { return this.col; }
    public void updateCol(int col) { this.col = col; }

    public int getRow() { return this.row; }
    public void updateRow(int row) { this.row = row; }

    // Neighbor Node getters and setters
    public Node peekNorth() { return this.north; }
    public void updateNorth(Node north) { this.north = north; }

    public Node peekSouth() { return this.south; }
    public void updateSouth(Node south) { this.south = south; }

    public Node peekEast() { return this.east; }
    public void updateEast(Node east) { this.east = east; }
    
    public Node peekWest() { return this.west; }
    public void updateWest(Node west) { this.west = west; }

    // Edge connection getters and setters
    public Edge peekNorthEdge() { return this.northEdge; }
    public void updateNorthEdge(Edge northEdge) { this.northEdge = northEdge; }

    public Edge peekSouthEdge() { return this.southEdge; }
    public void updateSouthEdge(Edge southEdge) { this.southEdge = southEdge; }
    
    public Edge peekEastEdge() { return this.eastEdge; }
    public void updateEastEdge(Edge eastEdge) { this.eastEdge = eastEdge; }

    public Edge peekWestEdge() { return this.westEdge; }
    public void updateWestEdge(Edge westEdge) { this.westEdge = westEdge; }

    public boolean loopCheck(Node another)
    {
        if ( another == this.north 
        || another == this.south 
        || another == this.east 
        || another == this.west ) { return true; }

        // else
        return false;
    }

    // computes direction from this Node to passed Node
    // returns 1-4 which is direction CCW from N (where N ==1)
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