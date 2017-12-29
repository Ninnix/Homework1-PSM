package Bench;

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

    public static void serialMerge2(int[] array, int low, int lmiddle, int rMiddle, int high, int[] aux, int auxL, int auxH) {

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

    /**
     * @param x   la chiave da cercare
     * @param arr l'array in cui cercare
     * @param l   indice low dell'array
     * @param h   indice di high dell'array
     * @return la posizione (inclusa) in cui gli elementi sono maggiori o uguali di x
     */
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
