import java.util.Arrays;
import java.util.Random;

import static java.lang.System.out;

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
        else if (args[1].equals("B")){ // Tipologia B. sequenze di n numeri casuali.
            for (int i = 0; i < lenght; i++) {
                Random rn = new Random();
                int random = rn.nextInt(lenght) ;
                array[i] = random;
            }
        }
        else {
            // ECCEZIONE: tipologia di input non corretta
        }
        out.println(Arrays.toString(array)); // stampiamo l'array creata

        int[] a1 = array; //array del mergesort sequenziale
        int[] a2 = array; //array del mergesort parallelo con funzione di merge seriale
        benchS(a1, 0, array.length);
        benchPS(a2, 0, array.length);
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

    /**
     *
     * @param array
     * @param low
     * @param high
     */
    static void benchS(int[]array, int low, int high){
        long inizio = System.currentTimeMillis();
        MergeSort.mergeSort(array,0,array.length);
        long fine = System.currentTimeMillis();
        out.println(Arrays.toString(array));
        out.println(check(array));
        out.println("Il mergesort seriale ha impiegato: " +(fine-inizio)+
                " millisecondi per ordinare " + array.length + " numeri \n" );
    }

    /**
     *
     * @param array
     * @param low
     * @param high
     */
    static void benchPS(int[]array, int low, int high){
        ParallelSort sort = new ParallelSort(array, low, high);
        long inizio = System.currentTimeMillis();
        ParallelSort.fjPool.invoke(sort);
        long fine = System.currentTimeMillis();
        out.println(Arrays.toString(array));
        out.println(check(array));
        out.println("Il mergesort parallelo con funzione di merge seriale ha impiegato: " +(fine-inizio)+
                " millisecondi per ordinare " + array.length + " numeri \n" );
    }
}
