package Graph;

public class Edge {

    private int id;
    private Node source;
    private Node target;
    private static volatile int count = 0;

    public Edge(Node source, Node target) {
        this.id = increaseCount();
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return "(" + source.toString() + "," + target.toString() + ")";
    }

    private synchronized int increaseCount() {
        return count++;
    }

    public int getId() {
        return this.id;
    }

    public Node getSource() {
        return this.source;
    }

    public Node getTarget() {
        return this.target;
    }
}


