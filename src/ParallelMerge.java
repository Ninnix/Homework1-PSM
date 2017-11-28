import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMerge extends RecursiveAction {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int mid;
    private int medMax;
    private int high;


    ParallelMerge(int[] arr, int l, int m, int medM, int h) {
        array = arr;
        low = l;
        mid = m;
        medMax = medM;
        high = h;

    }

    @Override
    protected void compute() {
        int lMin = 0;                  //lunghezza sottoarray piu' piccolo
        int lMax = 0;                  //lunghezza sottoarray piu' grande
        if (high - mid <= mid - low) { //sottoarray di destra e' piu' grande o sono uguali
            lMin = mid - low;
            lMax = high - mid;
        }else{                        // sottoarray di sinistra e' piu' grande
            lMin = high - mid;
            lMax = mid - low;
        }
        int[] min = new int[lMin];
        int[] max = new int[lMax];

        for (int i = 0; i < lMin; i++) {
            min[i] = array[low+i];
        }
        for (int i = 0; i < lMax; i++) {
            max[i] = array[mid+i];
        }

        /**
        if(true){
        } else {
            if (high - mid <= mid - low) {
                int mediano = (mid - low)/2;
                int destro = indexOf(array, array[mediano], mid, high);
                ParallelMerge left = new ParallelMerge(array, low, mediano, mid, destro);
                ParallelMerge right = new ParallelMerge(array, mediano, mid, destro, high);
                left.fork();
                right.compute();
                left.join();
            } else {
                int mediano2 = (high - mid)/2;
                int sinistro = indexOf(array, array[mediano2], low, mid);
                ParallelMerge left = new ParallelMerge(array, low, sinistro, mid, mediano2);
                ParallelMerge right = new ParallelMerge(array, sinistro, mid, mediano2, high);
                left.fork();
                right.compute();
                left.join();
            }
        }
         **/
    }

    public static int indexOf(int[] a, int key, int l, int h) {
        int lo = l;
        int hi = h - 1;
        int result = 0;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else {
                while (a[mid - 1] == a[mid]) {
                    mid--;
                }
                return mid;
            }
            result = mid;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] a = {1 ,1, 2, 3, 5, 5, 5, 5};
        System.out.println(indexOf(a, 0, 0, 7));
    }
}
