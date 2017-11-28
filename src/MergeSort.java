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
    static void serialMerge(int[] array, int low, int middle, int high) {

        int lenAux = high - low; //lunghezza array ausiliario
        int[] aux = new int[lenAux];

        for (int i = 0; i < lenAux; i++) {
            aux[i] = array[i + low];
        }

        int midAux = lenAux / 2; //middle array ausiliario
        int left = 0;
        int right = midAux;
        int current = low;

        while (left < midAux && right < lenAux) {
            if (aux[left] <= aux[right]) {
                array[current] = aux[left];
                left++;

            } else {
                array[current] = aux[right];
                right++;
            }
            current++;
        }
        while (right < lenAux) {
            array[current] = aux[right];
            right++;
            current++;
        }
        while (left < midAux) {
            array[current] = aux[left];
            left++;
            current++;
        }
    }

}

