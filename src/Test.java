import java.util.Arrays;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        int lenght = 1000;
        int[] array = new int[lenght];

        for (int i = 0; i < lenght; i++) {
            Random rn = new Random();
            int random = rn.nextInt(100) ;
            array[i] = random;
        }
        System.out.println(Arrays.toString(array));
        System.out.println(check(array)); //stampa false
        MergeSort.mergeSort(array,0,array.length-1);
        System.out.println(Arrays.toString(array));
        System.out.println(check(array)); //stampa true
    }

    /**
     * verifica sequenzialmente se l'array e' correttamente ordinato
     * @param array
     * @return
     */
    static boolean check(int[] array) {
        boolean ans = true;
        for (int i = 0; i < array.length-1; i++){
            if (array[i] <= array[i + 1]) {
                ans = true;
            }
            else {
                ans = false;
                break;
            }
        }
        return ans;
    }

}
