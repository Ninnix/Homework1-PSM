import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParMergeSort extends RecursiveAction {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int high;


    ParMergeSort(int[] arr, int l, int h) {
        array = arr;
        low = l;
        high = h;
    }

    @Override
    protected void compute() {
        if ((high-low) > 1){
            int mid = (low+high)/2;
            ParMergeSort left = new ParMergeSort(array, low, mid);
            ParMergeSort right = new ParMergeSort(array, mid, high);
            left.fork();
            right.compute();
            left.join();
            ParallelMerge parMerge = new ParallelMerge(array, 0, low, high/2, high ); //TODO i parametri sono sbagliati
            parMerge.compute();
        }
    }
}
