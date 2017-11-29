import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParMergeSort extends RecursiveAction {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int mid;
    private int medMax;
    private int high;


    ParMergeSort(int[] arr, int l, int h) {
        array = arr;
        low = l;
        high = h;
        mid = (l + h) / 2;
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
            ParallelMerge parMerge = new ParallelMerge(array, low, mid, 0, high ); //TODO manca da passare il medMax(ma all' inizio non lo abbiamo)
            parMerge.compute();
        }
    }
}
