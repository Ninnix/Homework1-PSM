public class MergeSort {

    /**
     * Versione del mergesort completamente sequenziale
     *
     * @param array
     * @param low
     * @param high
     */
    static void mergeSort(int[] array, int low, int high) {
        if (high - low > 1) {
            int middle = (low + high) / 2;
            mergeSort(array, low, middle);
            mergeSort(array, middle, high);
            serialMerge(array, low, middle, high);
        }
    }

    /**
     * implementazione della funzione di merge sequenziale
     *
     * @param array
     * @param low    indice di inizio primo array
     * @param middle indice di fine primo array(non compreso nel primo) e inizio secondo array(quindi compreso)
     * @param high   indice fine secondo array (quindi non compreso)
     */
    static void serialMerge2(int[] array, int low, int middle, int high) {

        int[] aux = new int[array.length];
        for (int i = low; i < high; i++) {
            aux[i] = array[i];
        }

        int left = low;
        int right = middle;
        int current = low;


        while (left < middle && right < high) {
            if (aux[left] <= aux[right]) {
                array[current] = aux[left];
                left++;

            } else {
                array[current] = aux[right];
                right++;
            }
            current++;
        }
        if (left == middle) {
            while (right < high) {
                array[current] = aux[right];
                right++;
                current++;
            }
        } else {
            while (left < middle) {
                array[current] = aux[left];
                left++;
                current++;
            }
        }
    }

    /**
     * implementazione della funzione di merge sequenziale
     * @param array
     * @param low
     * @param middle
     * @param high
     */
    static void serialMerge(int[] array, int low, int middle, int high) {

        int[] aux = new int[array.length];
        for (int i = low; i < high; i++) {
            aux[i] = array[i];
        }

        int left = low;
        int right = middle;
        int current = low;


        while (left < middle && right < high) {
            if (aux[left] <= aux[right]) {
                array[current] = aux[left];
                left++;

            } else {
                array[current] = aux[right];
                right++;
            }
            current++;
        }

        int remaining = middle - left;
        for (int i = 0; i < remaining; i++) {
            array[current + i] = aux[left + i];
        }
    }

}

