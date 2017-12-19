package Graph;

public class Node {

    private int id;
    private GroupInt label;
    private static int count = 0;

    public Node(int l, int r) {
        this.id = increaseCount();
        this.label = new PairInt(l, r);
    }

    public Node(int l1, int r1, int l2, int r2 ) {
        this.id = increaseCount();
        this.label = new QuadInt(l1, r1, l2, r2);
    }

    @Override
    public String toString() {
        return label.toString();
    }

    private synchronized int increaseCount() {
        return count++;
    }

    public int getId() {
        return id;
    }
}
