import java.util.Arrays;
import static java.lang.System.out;

public class Sorting {

    /**
     *
     * @param array
     * @param low
     * @param high
     */
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

}
