import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMerge extends RecursiveAction {

    /**
     * Versione del Mergesort in cui le chiamate ricorsive sono eseguite in parallelo mentre
     * la funzione di merge è ancora sequienziale
     */

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] arr;
    private int low;
    private int high;


    ParallelMerge(int[] array, int min, int max) {
        arr = array;
        low = min;
        high = max;

    }


    @Override
    protected void compute() {
        if ((high-low) > 1){
            int mid = (low+high)/2;
            ParallelMerge left = new ParallelMerge(arr, low, mid);
            ParallelMerge right = new ParallelMerge(arr, mid, high);
            left.fork();
            right.compute();
            left.join();
            MergeSort.Serialmerge(arr, low, mid, high);
        }
    }

}



