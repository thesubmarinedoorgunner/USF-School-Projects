//import JumpingJim.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class JumpingJim
{
    public static void main(String args[])
    {
        int row, column;
        int[][] matrix;
        File file = new File("input.txt");
        // if (file != null)
        // {
        //     System.out.println("File read?");
        // }
        try 
        {
            Scanner s = new Scanner(file);
            row = s.nextInt();
            column = s.nextInt();
            matrix = new int[row][column];

            int i = 0, j = 0;

            while ( s.hasNextLine() )
            {
                int a = s.nextInt();
                matrix[i][j] = a;
                j++;
                if ( j == column ) { j = 0; i++; }
                if ( i == row ) { break; }
            }
            s.close();

            for (int x = 0; x < row; x++)
            {
                for (int y = 0; y < column; y++)
                {
                    System.out.print(matrix[x][y] + " ");
                }
                System.out.println();
            }
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