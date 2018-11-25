//import JumpingJim.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class JumpingJim
{
    public static void main(String args[])
    {
        int row, column;
        int[][] matrix;
        File file = new File("input.txt");
        ArrayList<Node> nodes = new ArrayList<Node>();
        ArrayList<Edge> edges = new ArrayList<Edge>();

        try 
        {
            Scanner s = new Scanner(file);
            row = s.nextInt();
            column = s.nextInt();
            matrix = new int[row][column];

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

            // for (int x = 0; x < row; x++)
            // {
            //     for (int y = 0; y < column; y++)
            //     {
            //         System.out.print(matrix[x][y] + " ");
            //     }
            //     System.out.println();
            // }
            for (int i = 0; i < row; i++)
            {
                for (int j = 0; j < column; j++)
                {
                    nodes.add ( new Node ( i, j, matrix[i][j]) ); // passing row, col, and number
                }
            }
            for (int i = 0; i < row; i++)
            {
                for (int j = 0; j < column; j++)
                {
                    
                }
            }
            System.out.println( edges.size() );
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Input file is corrupted or incorrect");
        }



        // for (int i = 0; i < 5; i++)
        // {    
        //     Node node = new Node();
        // }
        // BFS();
    }

    // public static void BFS()
    // {
    //     Node node = new Node();
    // }
}