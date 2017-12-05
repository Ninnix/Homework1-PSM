import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import static java.lang.System.out;

public class ParallelMerge extends RecursiveAction {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int[] aux;
    private int sxLow;
    private int sxHigh;
    private int dxLow;
    private int dxHigh;
    private int auxLow;
    private int auxHigh;

    ParallelMerge(int[] arr,int sxL,int sxH,int dxL, int dxH, int[] aux, int auxL, int auxH) {
        array = arr;
        this.aux = aux;
        sxLow = sxL;
        sxHigh = sxH;
        dxLow = dxL;
        dxHigh = dxH;
        auxLow = auxL;
        auxHigh = auxH;
    }

    @Override
    protected void compute() {
        int lenSx = sxHigh - sxLow;
        int lenDx = dxHigh - dxLow;
        if (lenSx < lenDx) { //scambio gli indici in modo da avere sempre array di sinistra piu' grande
            int appoggio;

            appoggio = sxLow;
            sxLow = dxLow;
            dxLow = appoggio;

            appoggio = sxHigh;
            sxHigh = dxHigh;
            dxHigh = appoggio;

            appoggio = lenSx;
            lenSx = lenDx;
            lenDx = appoggio;
        }
        if (lenSx == 1 && lenDx == 0) {
            array[auxLow] = aux[sxLow];
            return;
        } else {
            int sxMed = (sxHigh + sxLow) / 2; //mediano sottoarray piu' grande
            int dxInd = binarySearch(aux[sxMed], aux, dxLow, dxHigh);
            int auxInd = (sxMed - sxLow) + (dxInd - dxLow); //numero elementi (indice) piu' piccoli del sxMed
            ParallelMerge left = new ParallelMerge(array, sxLow, sxMed, dxLow, dxInd, aux, auxLow, auxInd);
            ParallelMerge right = new ParallelMerge(array, sxMed, sxHigh, dxInd, dxHigh, aux, auxInd, auxHigh);
            left.compute();
            right.compute();
            /**
            for (int i = auxLow; i < auxInd; i++) {
                array[i] = aux[dxLow];
                //sxLow++;
            }
            for (int j = auxInd; j < auxHigh; j++) {
                array[j] = aux[sxLow];
                //dxLow++;
            }**/
        }

        //left.fork();
        //right.compute();
        //left.join();

        /*int lMin = 0;                  //lunghezza sottoarray piu' piccolo
        int lMax = 0;                  //lunghezza sottoarray piu' grande
        if (r2 - r1 <= r1 - p1) { //sottoarray di destra e' piu' grande o sono uguali
            lMin = r1 - p1;
            lMax = r2 - r1;
        }else{                        // sottoarray di sinistra e' piu' grande
            lMin = r2 - r1;
            lMax = r1 - p1;
        }
        int[] min = new int[lMin];
        int[] max = new int[lMax];

        for (int i = 0; i < lMin; i++) {
            min[i] = array[p1+i];
        }
        for (int i = 0; i < lMax; i++) {
            max[i] = array[r1+i];
        }
        int q1;
        int q2;
        int q3;
        int n1 = r1 - p1 + 1;
        int n2 = r2 - p2 + 1;
        if (n1 < n2) {
            int appoggio;

            appoggio = p1;
            p1 = p2;
            p2 = appoggio;

            appoggio = r1;
            r1 = r2;
            r2 = appoggio;

            appoggio = n1;
            n1 = n2;
            n2 = appoggio;
        }
        if (n1 == 0)
            return;
        else {
            q1 = (p1 + r1) / 2;
            q2 = binarySearch(array[q1], array, p2, r2);
            q3 = s + (q1 - p1) + (q2 - p2);
            aux[q3] = array[q1];
            ParallelMerge left = new ParallelMerge(array, p1, q1 - 1, p2, q2 - 1, aux, s);
            ParallelMerge right = new ParallelMerge(array, q1 + 1, r1, q2, r2, aux, q3 + 1);
            left.fork();
            right.compute();
            left.join();

        }
        */
    }

    /**
     * @param x   la chiave da cercare
     * @param arr l'array in cui cercare
     * @param l   indice low dell'array
     * @param h   indice di high dell'array
     * @return la posizione (inclusa) in cui gli elementi sono maggiori o uguali di x
     */
    public static int binarySearch(int x, int[] arr, int l, int h) {
        int low = l;
        int high = Math.max(l, h);
        while (low < high) {
            int mid = ((low + high) / 2);
            if (x <= arr[mid])
                high = mid;
            else
                low = mid + 1;
        }
        return high;
    }

public static void main(String[] args) {
    int[] arr = {5,6,1,2};
    int[] aux = new int[arr.length];
    System.arraycopy( arr, 0, aux, 0, arr.length );
    //System.out.println(binarySearch(8, arr, 2, 5));
    ParallelMerge parmerge = new ParallelMerge(arr, 0, 2, 2, arr.length, aux, 0, aux.length);
    parmerge.compute();
    out.println(Arrays.toString(arr));
    }
}




