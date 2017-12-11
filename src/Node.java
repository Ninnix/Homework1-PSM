

public class Node {

    private int id;
    private PairInt label;
    private static int count = 0;

    public Node(int l, int r) {
        this.id = increaseCount();
        this.label = new PairInt(l, r);
    }

    @Override
    public String toString() {
        return "(" + label.left + "," +label.right +")";
    }

    private synchronized int increaseCount() {
        return count++;
    }

    private class PairInt {

        private final int left;
        private final int right;

        public PairInt(int l, int r) {
            this.left = l;
            this.right = r;
        }
    }

    public int getId() {
        return id;
    }

    public static void main(String[] args) {
        Node n1 = new Node(1,2);
        Node n2 = new Node(1,3);
        Node n3 = new Node(1,5);
    }
}
