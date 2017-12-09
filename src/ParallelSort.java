import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelSort extends RecursiveAction {

    /**
     * Versione del Mergesort in cui le chiamate ricorsive sono eseguite in
     * parallelo, mentre la funzione di merge è ancora sequienziale
     */

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int high;
    private int SEQUENTIAL_CUTOFF = 512;

    ParallelSort(int[] arr, int l, int h) {
        array = arr;
        low = l;
        high = h;
    }

    @Override
    protected void compute() {
        if ((high - low) < SEQUENTIAL_CUTOFF){
            MergeSort.mergeSort(array, low, high);
        }
        else {
            int mid = (low + high) / 2;
            ParallelSort left = new ParallelSort(array, low, mid);
            ParallelSort right = new ParallelSort(array, mid, high);
            left.fork();
            right.compute();
            left.join();
            MergeSort.serialMerge(array, low, mid, high);
        }
    }
}



