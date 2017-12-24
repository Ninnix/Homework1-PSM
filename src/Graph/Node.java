package Graph;

public class Node {

    private int id;
    private GroupInt label;
    private static volatile int count = 0;

    /**
     * Costruttore Node con due parametri
     * @param l
     * @param r
     */
    public Node(int l, int r) {
        this.id = increaseCount();
        this.label = new PairInt(l, r);
    }

    /**
     * Costruttore Node con quattro parametri
     * @param l1
     * @param r1
     * @param l2
     * @param r2
     */
    public Node(int l1, int r1, int l2, int r2 ) {
        this.id = increaseCount();
        this.label = new QuadInt(l1, r1, l2, r2);
    }

    public static void setCount(int count) {
        Node.count = count;
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
