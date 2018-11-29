
// Edge.java
// Edge class containing information about source and target Node classes
// Howard Cheung

public class Edge
{
    private Node source;
    private Node target;
    private int direction;
    // direction? 
    // direction is counterclockwise from N (1-4)

    Edge(Node source, Node target) 
    { 
        this.source = source; 
        this.target = target; 
        this.direction = source.getDirection(target);
    }

    public Node getSource() { return this.source; }
    public void setSource(Node source) { this.source = source; }

    public Node getTarget() { return this.target; }
    public void setTarget(Node target) { this.target = target; }

    public int getDirection() { return this.direction; }

}