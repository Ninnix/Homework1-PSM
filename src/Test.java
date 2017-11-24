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

        MergeSort.mergeSort(array,0,array.length -1);
        System.out.println(Arrays.toString(array));


    }

}
