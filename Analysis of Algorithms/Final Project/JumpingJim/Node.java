
import java.util.ArrayList;
import java.util.HashMap;

public class Node
{
    private Node previous;
    private Node next;
    private int number; 

    Node() { this.previous = this.next = null; this.number = -1; }
    Node (Node previous, Node next, int number)
    {
        this.previous = previous;
        this.next = next;
        this.number = number;
    }

    public Node getPrevious() { return this.previous; }
    public void setPrevious(Node previous) { this.previous = previous; }

    public Node getNext() { return this.next; }
    public void setNext(Node next) { this.next = next; }

    public int getNumber() { return this.number; }
    public void setNumber(int number) { this.number = number; }
}