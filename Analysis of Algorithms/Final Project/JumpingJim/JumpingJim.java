
// JumpingJim.java
// takes in an input file called 'input.txt' and uses Dijkstra's algorithm to find the path
// outputs a file called 'output.txt'
// Howard Cheung

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
    }
}