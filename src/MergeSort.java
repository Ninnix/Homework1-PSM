public class MergeSort {

    /**
     * Versione del mergesort completamente seriale
     * @param array
     * @param low
     * @param high
     */
    static void mergeSort(int[] array, int low, int high) {
        if (low < high) {
            int middle = (low + high)/2;
            mergeSort(array, low, middle);
            mergeSort(array, middle + 1, high);
            merge(array, low, middle, high);
        }
    }

    /**
     * implementazione del merge seriale
     * @param array
     * @param low
     * @param middle
     * @param high
     */
    static void merge (int[] array, int low, int middle, int high) {

        int[] aux = new int[array.length];
        for(int i = low; i <= high; i++){
            aux[i] = array[i];
        }

        int left = low;
        int right = middle + 1;
        int current = low;


        while (left <= middle && right <= high) {
            if(aux[left] <= aux[right]){
                array[current] = aux[left];
                left++;

            }else{
                array[current] = aux[right];
                right++;
            }
            current ++;
        }

        int remaining = middle - left;
        for (int i = 0; i <= remaining; i++) {
            array[current+i] = aux[left+ i];
        }
    }

}
