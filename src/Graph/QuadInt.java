package Graph;

/**
 * Classe delle quaterne di interi
 */
public class QuadInt implements GroupInt {

    private final int left1;
    private final int right1;
    private final int left2;
    private final int right2;

    /**
     * Costruttore QuadInt
     * @param l1 primo intero di sinistra
     * @param r1 primo intero di destra
     * @param l2 secondo intero di sinistra
     * @param r2 secondo intero di destra
     */
    public QuadInt(int l1, int r1, int l2, int r2) {
        this.left1 = l1;
        this.right1 = r1;
        this.left2 = l2;
        this.right2 = r2;
    }

    /**
     * Converte in stringa il QuadInt
     * @return Stringa che rappresenta il QuadInt
     */
    @Override
    public String toString() {
        return "[" + "(" + this.left1 + "," + this.right1 +")" + "," + "(" + this.left2 + "," +this.right2+ ")" + "]";
    }
}
