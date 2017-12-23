package Graph;

/**
 * Classe delle coppie di interi
 */
public class PairInt implements GroupInt{


    private final int left;
    private final int right;

    /**
     * Costruttore PairInt
     * @param l intero di sinistra
     * @param r intero di destra
     */
    public PairInt(int l, int r) {
        this.left = l;
        this.right = r;
    }

    /**
     * Converte in stringa il PairInt
     * @return Stringa che rappresenta il PairInt
     */
    @Override
    public String toString() {
        return "(" + this.left + "," + this.right +")";
    }
}
