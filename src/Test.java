import java.util.Arrays;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]); //Il valore n specificato da linea di comando;
        int lenght = n;
        int[] array = new int[lenght];

        if (args[1].equals("A")) { // Tipologia A. sequenze decrescenti contenenti i valori interi da n a 1;
            for (int i = 0; i < lenght; i++) {
                array[i] = n;
                n--;
            }

        }
        if (args[1].equals("B")){ // Tipologia B. sequenze di n numeri casuali.
            for (int i = 0; i < lenght; i++) {
                Random rn = new Random();
                int random = rn.nextInt(lenght) ;
                array[i] = random;
            }
        }
        else {
            // ECCEZIONE: tipologia di input non corretta
        }
        System.out.println(Arrays.toString(array));
        System.out.println(check(array));             //stampa false

        MergeSort.mergeSort(array,0,array.length-1);

        System.out.println(Arrays.toString(array));
        System.out.println(check(array));             //stampa true
    }

    /**
     * Metodo che verifica che l’output sia corretto, ovvero che rispetta l’ordine crescente
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
