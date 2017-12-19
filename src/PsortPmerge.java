
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class PsortPmerge extends RecursiveAction {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int high;
    private int[] aux;
    private int SEQUENTIAL_CUTOFF = 512;

    PsortPmerge(int[] arr, int l, int h, int[] aux) {
        array = arr;
        low = l;
        high = h;
        this.aux = aux;
    }

    @Override
    protected void compute() {
        int mid = (low + high) / 2;
        //if ((high-low) <= 1) return;
        if ((high - low) < SEQUENTIAL_CUTOFF){
            MergeSort.mergeSort(array,low,high);
        }
        else{
            PsortPmerge left = new PsortPmerge(array, low, mid, aux);
            PsortPmerge right = new PsortPmerge(array, mid, high, aux);
            left.fork();
            right.compute();
            left.join();
            for (int i = low; i < high; i++) {
                aux[i] = array[i];
            }
            ParallelMerge parmerge = new ParallelMerge(array, low, mid, mid, high, aux, low, high);
            parmerge.compute();
        }
    }
}
