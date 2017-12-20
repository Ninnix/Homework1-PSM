package Bench;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class PsortSmerge extends RecursiveAction {

    /**
     * Versione del Mergesort in cui le chiamate ricorsive sono eseguite in
     * parallelo, mentre la funzione di merge è ancora sequienziale
     */

    public static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int high;
    private int SEQUENTIAL_CUTOFF = 512;

    public PsortSmerge(int[] arr, int l, int h) {
        array = arr;
        low = l;
        high = h;
    }

    @Override
    protected void compute() {
        //if ((high-low) <= 1) return;
        if ((high - low) < SEQUENTIAL_CUTOFF){
            Sorting.insectionSort(array, low, high);
        }
        else {
            int mid = (low + high) / 2;
            PsortSmerge left = new PsortSmerge(array, low, mid);
            PsortSmerge right = new PsortSmerge(array, mid, high);
            left.fork();
            right.compute();
            left.join();
            MergeSort.serialMerge(array, low, mid, high);
        }
    }
}