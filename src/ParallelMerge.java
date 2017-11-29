import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMerge extends RecursiveAction {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int p1;
    private int r1;
    private int p2;
    private int r2;
    private int[] aux;

    /*ParallelMerge(int[] arr, int l, int m, int medM, int h, int[] aux) {
        array = arr;
        low = l;
        med = m;
        mediano = medM;
        high = h;
        this.aux = aux;
    }*/

    ParallelMerge(int[] arr, int l, int m, int medM, int h, int[] aux) {
        array = arr;
        p1 = l;
        r1 = m;
        p2 = medM;
        r2 = h;
        this.aux = aux;
    }

    @Override
    protected void compute() {
        /*int lMin = 0;                  //lunghezza sottoarray piu' piccolo
        int lMax = 0;                  //lunghezza sottoarray piu' grande
        if (r2 - r1 <= r1 - p1) { //sottoarray di destra e' piu' grande o sono uguali
            lMin = r1 - p1;
            lMax = r2 - r1;
        }else{                        // sottoarray di sinistra e' piu' grande
            lMin = r2 - r1;
            lMax = r1 - p1;
        }
        int[] min = new int[lMin];
        int[] max = new int[lMax];

        for (int i = 0; i < lMin; i++) {
            min[i] = array[p1+i];
        }
        for (int i = 0; i < lMax; i++) {
            max[i] = array[r1+i];
        }*/
        int q1;int q2;int q3;
        int n1 = r1 - p1+1;
        int n2 = r2 - p2+1;
        if (n1 < n2){
            int appoggio;

            appoggio = p1;
            p1 = p2;
            p2 = appoggio;

            appoggio = r1;
            r1 = r2;
            r2 = appoggio;

            appoggio = n1;
            n1 = n2;
            n2 = n1;
        }
        if (n1 == 0)
            return;
        else {
            q1 = (p1 + r1) / 2;
            q2 = binarySearch(array,array[q1], p2, r2);
            q3 = (q1-p1) + (q2-p2);
            aux[q3] = array[q1];
            ParallelMerge left = new ParallelMerge(array, p1, q1-1, p2, q2-1, aux);
            ParallelMerge right = new ParallelMerge(array, q1+1,r1,q2,r2,aux);
            left.fork();
            right.compute();
            left.join();
        }

    }


    public static int binarySearch(int[] a, int key, int l, int h) {
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
        System.out.println(binarySearch(a, 4, 0, 8));

    }
}



/**
 if(true){
 } else {
 if (r2 - r1 <= r1 - p1) {
 int mediano = (r1 - p1)/2;
 int destro = binarySearch(array, array[mediano], r1, r2);
 ParallelMerge left = new ParallelMerge(array, p1, mediano, r1, destro);
 ParallelMerge right = new ParallelMerge(array, mediano, r1, destro, r2);
 left.fork();
 right.compute();
 left.join();
 } else {
 int mediano2 = (r2 - r1)/2;
 int sinistro = binarySearch(array, array[mediano2], p1, r1);
 ParallelMerge left = new ParallelMerge(array, p1, sinistro, r1, mediano2);
 ParallelMerge right = new ParallelMerge(array, sinistro, r1, mediano2, r2);
 left.fork();
 right.compute();
 left.join();
 }
 }
 **/
