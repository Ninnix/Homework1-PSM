import java.util.Arrays;
import static java.lang.System.out;

public class Sorting {

    /**
     * @param array
     * @param low
     * @param high
     */
    public static void insectionSort(int[] array, int low, int high) {
        for (int i = low; i < high; i++) {
            int x = i;
            int j = i - 1;
            for (; j >= low; j--) {
                if (array[j] > array[x]) {
                    int k = array[x];
                    array[x] = array[j];
                    array[j] = k;
                    x = j;
                } else break;
            }
        }
    }

    static void serialMerge2(int[] array, int low, int lmiddle, int rMiddle, int high, int[] aux, int auxL, int auxH) {

        int left = low;
        int right = rMiddle;
        int current = auxL;

        while (left < lmiddle && right < high) {
            if (aux[left] <= aux[right]) {
                array[current] = aux[left];
                left++;
            } else {
                array[current] = aux[right];
                right++;
            }
            current++;
        }

        while (left < lmiddle) {
            array[current] = aux[left];
            left++;
            current++;
        }

        while (right < high) {
            array[current] = aux[right];
            current++;
            right++;
        }
    }

    public static void main(String[] args){
        int[] a = {1,3,4,6,8,29,2,3,5,7,32};
        int[] b = {1,3,4,6,8,29,2,3,5,7,32};
        //serialMerge2(a, 0,6,6,11,b,0,a.length);
        insectionSort(a, 0, a.length);
        out.println(Arrays.toString(a));
    }

}
