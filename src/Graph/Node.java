package Graph;

import java.util.Random;

public class Node {

    private int id;
    private GroupInt label;
    private String color = "#FFCC00";
    String[] colors = {"#F0F8FF","#DC143C","#FF8C00","#1E90FF","#008000","#FFA500", "#32CD32", "#FF4500", "#6A5ACD", "#008080","#4682B4","#CD853F", "#DB7093", "#CD5C5C", "#DAA520","#87CEFA", "#FFC0CB", "#FA8072", "#DEB887" };
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

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void changeColor() {
        Random rn = new Random();
        int random = rn.nextInt(colors.length) ;
        String color = colors[random];
        this.color = color;
    }
}
