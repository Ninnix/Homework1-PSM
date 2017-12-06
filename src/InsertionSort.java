import java.util.Arrays;
import static java.lang.System.out;

public class InsertionSort {

    public static void main(String[] args) {
        final int[] arr = {37, 23, 0, 17, 12, 72, 31, 46, 100, 88, 54 };
        new InsertionSort().insertionSort(arr);
        out.println(Arrays.toString(arr));
    }

    public static void insertionSort(int array[]) {
        for (int i = 1; i < array.length; i++) {
            int x = array[i];
            // Trova la locazione corretta dove inserire grazie alla ricerca binaria
            int j = Math.abs(binarySearch(x, array, 0, i));

            //Shifta l'array di una posizione a destra dalla posizione in cui inserire
            System.arraycopy(array, j, array, j+1, i-j);

            //mette l'elemento nella posizione corretta
            array[j] = x;
        }
    }

    //duplicata dalla classe ParallelMerge
    public static int binarySearch(int x, int[] arr, int l, int h) {
        int low = l;
        int high = Math.max(l, h);
        while (low < high) {
            int mid = ((low + high) / 2);
            if (x <= arr[mid])
                high = mid;
            else
                low = mid + 1;
        }
        return high;
    }
}