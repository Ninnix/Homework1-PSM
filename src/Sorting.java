import java.util.Arrays;
import static java.lang.System.out;

public class Sorting {

    public static void insectionSort (int[] array, int low, int high){
        for (int i = low; i < high; i++){
            int x = i;
            int j = i-1;
            for(; j >= low; j--) {
                if(array[j]>array[x]) {
                    int k = array[x];
                    array[x] = array[j];
                    array[j] = k;
                    x = j;
                } else break;
            }
        }
    }

    //versione piu' veloce del insertionSort
    public void BinInsertionSort(int array[]) {
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


    public static void countingSort(int[] arr) {
        //Cacolo degli elementi max e min
        int max = arr[0];
        int min = arr[0];
        int i = 1;
        for(; i < arr.length; i++) {
            if(arr[i] > max)
                max = arr[i];
            else if(arr[i] < min)
                min = arr[i];
        }
        int[] arr2 = new int[max-min+1];
        for(i = 0; i < arr2.length; i++)
            arr2[i]=0;    //inizializza a zero gli elementi di C
        for(i=0; i < arr.length; i++)
            arr2[arr[i]-min]++;                    //aumenta il numero di volte che si è incontrato il valore
        //Ordinamento in base al contenuto dell'array delle frequenze C
        int k=0;                             //indice per l'array A
        for(i=0; i<arr2.length; i++) {
            while(arr2[i]>0){                     //scrive C[i] volte il valore (i+min) nell'array A
                arr[k]=i+min;
                k++;
                arr2[i]--;
            }
        }
    }

    public static void main(String[] args) {
        final int[] arr = {37, 23, 0, 17, 12, 72, 31, 46, 100, 88, 54 };
        new Sorting().BinInsertionSort(arr);
        out.println(Arrays.toString(arr));
    }


}
