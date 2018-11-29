// JumpingJim.java
// Howard Cheung
// takes in an input file called 'input.txt' and uses Dijkstra's algorithm to find the path
// outputs a file called 'output.txt'

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/////
import java.io.BufferedWriter;
import java.io.FileWriter;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
import java.io.Writer;
/////


// BE VERY CAREFUL ABOUT CHEKCING WHETHER THE LAST NODE IS == TO ROW && COLUMN THAT WAS PASSED IN THROUGH THE TEXT FILE
// BECAUSE THE VALUE FOR ROW AND COL THAT THE LAST NODE HAS IS 1 LESS THAN BOTH B/C OF INDICES 

// ALSO CORNERS CAN BE 0 JUMP VALUE AND LAST NODE DOES NOT HAVE TO BE 0 JUMP VALUE
// IF YOU DON'T CHECK FOR ROW AND COLUMN, THEN YOU COULD END UP LOOPING BACK

public class JumpingJim
{
    public static void main(String args[])
    {
        long start = System.currentTimeMillis(); 

        GraphSet gs = new GraphSet();
        gs.readFile();
        gs.setGraph();
        gs.findPath();
        gs.writeFile();

        long end = System.currentTimeMillis();
        System.out.println("Takes " + (end- start) + "ms to run Dijkstra's");

        // int row, column;
        // int[][] matrix;
        // Node[][] nodeMatrix;
        // File file = new File("input.txt");
        // ArrayList<Node> nodes = new ArrayList<Node>();
        // ArrayList<Edge> edges = new ArrayList<Edge>();

        // long start = System.currentTimeMillis(); 
        // //long start = System.nanoTime();

        // try 
        // {
        //     Scanner s = new Scanner(file);
        //     row = s.nextInt();
        //     column = s.nextInt();
        //     matrix = new int[row][column];
        //     nodeMatrix = new Node[row][column];

        //     int r = 0, c = 0;

        //     while ( s.hasNextLine() )
        //     {
        //         int integer = s.nextInt();
        //         matrix[r][c] = integer;
        //         c++;
        //         if ( c == column ) { c = 0; r++; }
        //         if ( r == row ) { break; }
        //         // will discard anything that goes past the specified rows
        //     }
        //     s.close();

        //     // for (int x = 0; x < row; x++)
        //     // {
        //     //     for (int y = 0; y < column; y++)
        //     //     {
        //     //         System.out.print(matrix[x][y] + " ");
        //     //     }
        //     //     System.out.println();
        //     // }
            
        //     // creating a new Node object for every item in the matrix, held in the nodes ArrayList
        //     // also adds Node objects created to the 2D nodeMatrix 
        //     for (int i = 0; i < row; i++)
        //     {
        //         for (int j = 0; j < column; j++)
        //         {
        //             Node node = new Node( i, j, matrix[i][j] );
        //             nodes.add( node ); // passing row, col, and number
        //             nodeMatrix[i][j] = node;
        //         }
        //     }

        //     // creates the Edge object, adds to the edges ArrayList, and sets the pointer for direction and edge
        //     for (int i = 0; i < row; i++)
        //     {
        //         for (int j = 0; j < column; j++)
        //         {
        //             int number = nodeMatrix[i][j].getNumber();

        //             if ( i - number >= 0 ) // north check
        //             {
        //                 Edge edge = new Edge( nodeMatrix[i][j], nodeMatrix[i - number][j] );
        //                 edges.add ( edge );
        //                 nodeMatrix[i][j].updateNorth( nodeMatrix[i - number][j] );
        //                 nodeMatrix[i][j].updateNorthEdge( edge );
        //             }

        //             if ( j - number >=0 ) // west check
        //             {
        //                 Edge edge = new Edge( nodeMatrix[i][j], nodeMatrix[i][j - number] );
        //                 edges.add( edge );
        //                 nodeMatrix[i][j].updateWest( nodeMatrix[i][j - number] );
        //                 nodeMatrix[i][j].updateWestEdge( edge );
        //             }

        //             if ( i + number < row ) // south check
        //             {
        //                 Edge edge = new Edge( nodeMatrix[i][j], nodeMatrix[i + number][j] );
        //                 edges.add( edge);
        //                 nodeMatrix[i][j].updateSouth( nodeMatrix[i + number][j] );
        //                 nodeMatrix[i][j].updateSouthEdge( edge );
        //             }

        //             if ( j + number < column ) // east check
        //             {
        //                 Edge edge = new Edge( nodeMatrix[i][j], nodeMatrix[i][j + number] );
        //                 edges.add( edge );
        //                 nodeMatrix[i][j].updateEast( nodeMatrix[i][j + number] ); 
        //                 nodeMatrix[i][j].updateEastEdge( edge );
        //             }
        //         }
        //     }
        //     Dijkstra dijkstra = new Dijkstra( nodes, edges, nodeMatrix[0][0], nodeMatrix[row -1][column -1] );
        //     ArrayList<Edge> path = dijkstra.getPath();

        //     // prints out the path
        //     // Order is counterclockwise (1-4) starting from North

        //     String toFile = "";
        //     for (int i = 0; i < path.size(); i++)
        //     {
        //         if ( path.get(i).getDirection() == 1 )
        //         {
        //             System.out.print("N");
        //             toFile += "N";
        //         }    
        //         if ( path.get(i).getDirection() == 2 )
        //         {
        //             System.out.print("E");
        //             toFile += "E";
        //         } 
        //         if ( path.get(i).getDirection() == 3 )
        //         {
        //             System.out.print("S");
        //             toFile += "S";
        //         }
        //         if ( path.get(i).getDirection() == 4 )
        //         {
        //             System.out.print("W");
        //             toFile += "W";
        //         }
        //         System.out.print(" ");
        //         toFile += " ";
        //     }
        //     System.out.println();


            
        //     long end = System.currentTimeMillis();
        //     //long end = System.nanoTime();
            
        //     System.out.println("Takes " + (end- start) + " ms to run Dijkstra's");

        //     file = new File("output.txt");
        //     BufferedWriter output = null;

        //     output = new BufferedWriter( new FileWriter( file ) );
        //     output.write( toFile );

        //     if ( output != null) { output.close(); }


        //     // System.out.println( nodes.size() );
        //     // System.out.println( edges.size() );

        //     // HARDCODED POINTER CHECK FOR THE PROJECT MATRIX
        //     // if ( nodeMatrix[1][1].peekNorth() != nodeMatrix[0][1]
        //     // || nodeMatrix[1][1].peekEast() != nodeMatrix[1][2]
        //     // || nodeMatrix[1][1].peekWest() != nodeMatrix[1][0]
        //     // || nodeMatrix[1][1].peekSouth() != nodeMatrix[2][1] )
        //     // {
        //     //     System.out.println("We have a problem");
        //     // }
        // } 
        // catch (Exception e) {
        //     e.printStackTrace();
        //     System.out.println("Input file is corrupted or incorrect");
        // }

    }

}