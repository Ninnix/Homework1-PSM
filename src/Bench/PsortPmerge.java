package Bench;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class PsortPmerge extends RecursiveAction {

    /**
     * Versione del merge parallelo in cui anche la funzione di merge
     * viene eseguita in parallelo
     */

    public static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int high;
    private int[] aux;
    private int SEQUENTIAL_CUTOFF = 512;



    public PsortPmerge(int[] arr, int l, int h, int[] aux) {
        array = arr;
        low = l;
        high = h;
        this.aux = aux;
    }

    @Override
    protected void compute() {
        int mid = (low + high) / 2;
        // caso base
        if ((high - low) <= SEQUENTIAL_CUTOFF){
            if (SEQUENTIAL_CUTOFF == 1) return;
            Sorting.inserctionSort(array, low, high);
        }
        else{
            PsortPmerge left = new PsortPmerge(array, low, mid, aux);
            PsortPmerge right = new PsortPmerge(array, mid, high, aux);
            left.fork();
            //thread halving
            right.compute();
            left.join();
            //aggiornamento dell'array ausiliario
            for (int i = low; i < high; i++) {
                aux[i] = array[i];
            }
            //chiamata della funzione di merge per i due sottoarray left e right in parallelo
            ParallelMerge parmerge = new ParallelMerge(array, low, mid, mid, high, aux, low, high);
            parmerge.compute();
        }
    }
}
