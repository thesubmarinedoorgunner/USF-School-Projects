
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

//import com.sun.tools.javac.code.Attribute.Array;

public class Dijkstra
{
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private Node base, target;

    private ArrayList<Node> queue;
    private HashMap<Node, DijkstraContainer> container;
    private ArrayList<Edge> path = new ArrayList<Edge>();

    Dijkstra(ArrayList<Node> nodes, ArrayList<Edge> edges, Node base, Node target)
    {
        this.nodes = nodes;
        this.edges = edges;
        this.base = base;
        this.target = target;

        this.queue = new ArrayList<>(this.nodes);
        this.container = new HashMap<Node, DijkstraContainer>();

        setInfinAndNull();
        calculateDijkstra();
        setPath();
    }


    public ArrayList<Edge> getPath() { return this.path; }

    public void setInfinAndNull()
    {
        for (Node node : nodes)
        {
            DijkstraContainer temp;
            if ( node == base )
            {
                temp = new DijkstraContainer(0);
                container.put(node, temp);
            }
            else 
            {
                temp = new DijkstraContainer();
                container.put(node, temp);
            }
        }
    }

    public void calculateDijkstra()
    {
        while ( !queue.isEmpty() )
        {
            Node u = findMin();
            // if ( u = null ) { return; }
            if ( u == this.target ) { return; } // target is the next shortest; can terminate
            ArrayList<Edge> neighbors = getNeighbors( u );

            for (Edge edge : neighbors)
            {
                Node v = edge.getTarget();
                int alt = container.get( u ).getDist() + 1;

                if (alt < container.get( v ).getDist() )
                {
                    container.get( v ).setDist( alt );
                    container.get( v ).setPrev( u );
                    container.get( v ).updateEdge( edge );
                }
            }
            neighbors.clear();
            queue.remove( u );

        }
    }

    public Node findMin()
    {
        int min = Integer.MAX_VALUE;
        Node isMin = null;

        for (int i = 0; i < queue.size(); i++)
        {
            if ( container.get( queue.get(i) ).getDist() < min )
            {
                min = container.get( queue.get(i) ).getDist();
                isMin = queue.get(i);
            }
        }

        return isMin;
    }

    public ArrayList<Edge> getNeighbors(Node u)
    {
        ArrayList<Edge> neighbors = new ArrayList<>();

        if ( u.peekNorth() != null ) { neighbors.add( u.peekNorthEdge() ); }
        if ( u.peekEastEdge() != null ) { neighbors.add( u.peekEastEdge() ); }
        if ( u.peekSouthEdge() != null ) { neighbors.add( u.peekSouthEdge() ); }
        if ( u.peekWestEdge() != null ) { neighbors.add( u.peekWestEdge() ); }

        return neighbors;
    }

    public void setPath()
    {
        try 
        {
            Stack<Edge> reversePath = new Stack<Edge>();
            Node u = this.target;
            while ( u != this.base )
            {
                reversePath.push( container.get( u ).retrieveEdge() );
                u = container.get( u ).getPrev();
            }

            while( !reversePath.empty() )
            {
                path.add( reversePath.pop() );
            }
        } 
        catch (Exception e) {
            System.out.println("Something happened.");
        }
    }

    public class DijkstraContainer
	{
		private int dist;
		private Node prev;
        private Edge edge;
        
		DijkstraContainer()
		{
			this.dist = Integer.MAX_VALUE;
			this.prev = null;
			this.edge = null; 
		}
		DijkstraContainer(int zero)
		{
			this.dist = zero;
			this.prev = null;
			this.edge = null;
		}
		
		public int getDist() { return this.dist; }
		public void setDist(int dist) { this.dist = dist; }
		
		public Node getPrev() { return this.prev; }
		public void setPrev(Node prev) { this.prev = prev; }
		
		public Edge retrieveEdge() { return this.edge; }
		public void updateEdge(Edge edge) { this.edge = edge; }
    }
    
}