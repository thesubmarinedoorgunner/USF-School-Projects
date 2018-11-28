
import java.util.Scanner;
import java.util.ArrayList;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

public class GraphSet
{
    private int row, column;
    private int[][] matrix;
    private Node[][] nodeMatrix;
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();

    Dijkstra dijkstra;
    private ArrayList<Edge> path;
    
    private File file; //= new File("input.txt");
    private String toFile = "";
    BufferedWriter output = null;

    GraphSet() { }

    public void readFile()
    {
        try 
        {
            file = new File("input.txt");
            Scanner s = new Scanner(file);
            row = s.nextInt();
            column = s.nextInt();

            // initialize both matrix and nodeMatrix based on row and column from first line
            matrix = new int[row][column];
            nodeMatrix = new Node[row][column];

            int r = 0, c = 0;

            while ( s.hasNextLine() )
            {
                int integer = s.nextInt();
                matrix[r][c] = integer;
                c++;
                if ( c == column ) { c = 0; r++; }
                if ( r == row ) { break; }
                // will discard anything that goes past the specified rows
            }
            s.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Input file is corrupted or incorrect");
        }
    }

    public void setGraph()
    {
        try 
        {
            // creating a new Node object for every item in the matrix, held in the nodes ArrayList
            // also adds Node objects created to the 2D nodeMatrix 
            for (int i = 0; i < row; i++)
            {
                for (int j = 0; j < column; j++)
                {
                    Node node = new Node( i, j, matrix[i][j] );
                    nodes.add( node ); // passing row, col, and number
                    nodeMatrix[i][j] = node;
                }
            }

            // creates the Edge object, adds to the edges ArrayList, and sets the pointer for direction and edge
            for (int i = 0; i < row; i++)
            {
                for (int j = 0; j < column; j++)
                {
                    int number = nodeMatrix[i][j].getNumber();

                    if ( i - number >= 0 ) // north check
                    {
                        Edge edge = new Edge( nodeMatrix[i][j], nodeMatrix[i - number][j] );
                        edges.add ( edge );
                        nodeMatrix[i][j].updateNorth( nodeMatrix[i - number][j] );
                        nodeMatrix[i][j].updateNorthEdge( edge );
                    }

                    if ( j - number >=0 ) // west check
                    {
                        Edge edge = new Edge( nodeMatrix[i][j], nodeMatrix[i][j - number] );
                        edges.add( edge );
                        nodeMatrix[i][j].updateWest( nodeMatrix[i][j - number] );
                        nodeMatrix[i][j].updateWestEdge( edge );
                    }

                    if ( i + number < row ) // south check
                    {
                        Edge edge = new Edge( nodeMatrix[i][j], nodeMatrix[i + number][j] );
                        edges.add( edge);
                        nodeMatrix[i][j].updateSouth( nodeMatrix[i + number][j] );
                        nodeMatrix[i][j].updateSouthEdge( edge );
                    }

                    if ( j + number < column ) // east check
                    {
                        Edge edge = new Edge( nodeMatrix[i][j], nodeMatrix[i][j + number] );
                        edges.add( edge );
                        nodeMatrix[i][j].updateEast( nodeMatrix[i][j + number] ); 
                        nodeMatrix[i][j].updateEastEdge( edge );
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in creating the graph (nodes and/or edges");
        }
    }

    public void findPath()
    {
        try 
        {
            dijkstra = new Dijkstra( nodes, edges, nodeMatrix[0][0], nodeMatrix[row -1][column -1] );
            path = dijkstra.getPath(); 

            for (int i = 0; i < path.size(); i++)
            {
                if ( path.get(i).getDirection() == 1 )
                {
                    System.out.print("N");
                    toFile += "N";
                }    
                if ( path.get(i).getDirection() == 2 )
                {
                    System.out.print("E");
                    toFile += "E";
                } 
                if ( path.get(i).getDirection() == 3 )
                {
                    System.out.print("S");
                    toFile += "S";
                }
                if ( path.get(i).getDirection() == 4 )
                {
                    System.out.print("W");
                    toFile += "W";
                }
                System.out.print(" ");
                toFile += " ";
            }
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something wrong with Dijkstra");
        }

    }
    public void writeFile()
    {
        // also prints out the path for your convinience
        try 
        {
            file = new File("output.txt");
            BufferedWriter output = null;

            output = new BufferedWriter( new FileWriter( file ) );
            output.write( toFile );

            if ( output != null) { output.close(); }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not write to file");
        }
    }

}