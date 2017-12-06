import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParMergeSort extends RecursiveAction {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int high;
    private int[] aux;

    ParMergeSort(int[] arr, int l, int h, int[] aux) {
        array = arr;
        low = l;
        high = h;
        this.aux = aux;

    }

    @Override
    protected void compute() {
        if ((high-low) > 1){
            int mid = (low+high)/2;
            ParMergeSort left = new ParMergeSort(array, low, mid, aux);
            ParMergeSort right = new ParMergeSort(array, mid, high, aux);
            left.fork();
            right.compute();
            //left.join();
            ParallelMerge parmerge = new ParallelMerge(array, low, mid, mid, high, aux,low, high);
            parmerge.compute();
            for (int i = low; i< high; i++)
                aux[i] = array[i];
        }
    }
}
