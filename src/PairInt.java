public class PairInt implements GroupInt{

    private final int left;
    private final int right;

    public PairInt(int l, int r) {
        this.left = l;
        this.right = r;
    }

    @Override
    public String toString() {
        return "(" + this.left + "," + this.right +")";
    }
}
