public class QuadInt implements GroupInt {

    private final int left1;
    private final int right1;
    private final int left2;
    private final int right2;

    public QuadInt(int l1, int r1, int l2, int r2) {
        this.left1 = l1;
        this.right1 = r1;
        this.left2 = l2;
        this.right2 = r2;
    }

    @Override
    public String toString() {
        return "[" + "(" + this.left1 + "," + this.right1 +")" + "," + "(" + this.left2 + "," +this.right2+ ")" + "]";
    }
}
