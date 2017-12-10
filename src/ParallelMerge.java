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
    private int SEQUENTIAL_CUTOFF = 1024;

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
        if (lenSx < SEQUENTIAL_CUTOFF) {
            Sorting.serialMerge2(array,sxLow,sxHigh,dxLow,dxHigh,aux,auxLow,auxHigh);
            return;

        } else {
            int sxMed = (sxHigh + sxLow) / 2; //mediano sottoarray piu' grande
            int dxInd = binarySearch(aux[sxMed], aux, dxLow, dxHigh); //posizione mediano sottoarray più piccolo
            int auxInd = (sxMed - sxLow) + (dxInd - dxLow); //numero elementi (indice) piu' piccoli del sxMed
            ParallelMerge left = new ParallelMerge(array, sxLow, sxMed, dxLow, dxInd, aux, auxLow,auxLow + auxInd);
            ParallelMerge right = new ParallelMerge(array, sxMed, sxHigh, dxInd, dxHigh, aux, auxLow + auxInd, auxHigh);
            left.fork();
            right.compute();
            left.join();
        }
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
    /*int[] arr = {0,2,5,60,1,3,4,5,20,30};
    int[] aux = new int[arr.length];
    System.arraycopy( arr, 0, aux, 0, arr.length );
    //System.out.println(binarySearch(8, arr, 2, 5));
    ParallelMerge parmerge = new ParallelMerge(arr, 0, 4, 4, arr.length, aux, 0, aux.length);
    parmerge.compute();
    out.println(Arrays.toString(arr));*/
    int r = 5;
    int a = r;
    a++;
    out.println("r = "+ r + " a = "+a);
    }
}


/*
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
        if (lenSx == 1 && lenDx == 1) {
            if (aux[sxLow] <= aux[dxLow]) {
                array[auxLow] = aux[sxLow];
                array[auxLow+1] = aux[dxLow];
            } else {
                array[auxLow] = aux[dxLow];
                array[auxLow+1] = aux[sxLow];
            }
            return;
        }
        if (lenSx == 1 && lenDx == 0) {
            array[auxLow] = aux[sxLow];
            return;
        } else {
            int sxMed = (sxHigh + sxLow) / 2; //mediano sottoarray piu' grande
            int dxInd = binarySearch(aux[sxMed], aux, dxLow, dxHigh); //posizione mediano sottoarray più piccolo
            int auxInd = (sxMed - sxLow) + (dxInd - dxLow); //numero elementi (indice) piu' piccoli del sxMed
            ParallelMerge left = new ParallelMerge(array, sxLow, sxMed, dxLow, dxInd, aux, auxLow,auxLow + auxInd);
            ParallelMerge right = new ParallelMerge(array, sxMed, sxHigh, dxInd, dxHigh, aux, auxLow + auxInd, auxHigh);
            left.fork();
            right.compute();
            left.join();
        }
    }
 */




