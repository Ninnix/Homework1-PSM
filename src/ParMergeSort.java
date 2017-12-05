import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParMergeSort extends RecursiveAction {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int high;
    private int[] aux;
    private int s;



    ParMergeSort(int[] arr, int l, int h, int[] aux, int ss) {
        array = arr;
        low = l;
        high = h;
        s = ss;
        this.aux = aux;

    }

    @Override
    protected void compute() {
        int n = high - low + 1;
        if (n == 1) {
            //System.out.println(s+"\n\n");
            aux[s] = array[low];
        }else {
            int[] aux2 = new int[n];
            int mid = (low + high) / 2;
            int indexMid = mid - low + 1;
            ParMergeSort left = new ParMergeSort(array, low, mid, aux2, 0);
            ParMergeSort right = new ParMergeSort(array, mid + 1, high, aux2, indexMid+1);
            left.fork();
            right.compute();
            left.join();
            //ParallelMerge parMerge = new ParallelMerge(aux2, 1, indexMid, indexMid + 1, n, aux, s);
            //ParallelMerge.fjPool.invoke(parMerge);
            //parMerge.compute();
        }
    }
}
