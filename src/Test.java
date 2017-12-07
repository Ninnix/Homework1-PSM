import java.util.Arrays;
import java.util.Random;

import static java.lang.System.lineSeparator;
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
                int random = rn.nextInt(1000) ;
                array[i] = random;
            }
        }
        else {
            // ECCEZIONE: tipologia di input non corretta
        }
        //out.println(Arrays.toString(array)); // stampiamo l'array creata
        int[] a1 = array; //array del mergesort sequenziale
        int[] a2 = new int[array.length]; //array del mergesort parallelo con funzione di merge seriale
        System.arraycopy( array, 0, a2, 0, array.length );
        int[] a3 = new int[array.length];
        System.arraycopy( array, 0, a3, 0, array.length );
        int[] a4 = new int[array.length];
        System.arraycopy( array, 0, a4, 0, array.length );
        int [] a5 = new int[array.length];
        System.arraycopy( array, 0, a5, 0, array.length );
        //benchS(a1,0, a1.length);
        benchPS(a2, 0, a2.length);
        benchPP(a3, 0, a3.length);
        //benchC(a4, 0, a4.length);
        //benchIns(a1,0,1);
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
        MergeSort.mergeSort(array,low,high);
        long fine = System.currentTimeMillis();
        //out.println(Arrays.toString(array));
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
        //out.println(Arrays.toString(array));
        out.println(check(array));
        out.println("Il mergesort parallelo con funzione di merge seriale ha impiegato: " +(fine-inizio)+
                " millisecondi per ordinare " + array.length + " numeri \n" );
    }

    /**
     *
     * @param array
     * @param low
     * @param high
     */
    static void benchPP(int[]array, int low, int high){
        int[] aux = new int[array.length];
        System.arraycopy( array, 0, aux, 0, array.length );
        ParMergeSort sort2 = new ParMergeSort(array, low, high, aux);
        long inizio = System.currentTimeMillis();
        ParMergeSort.fjPool.invoke(sort2);
        long fine = System.currentTimeMillis();
        //out.println(Arrays.toString(array));
        out.println(check(array));
        out.println("Il mergesort parallelo ha impiegato: " +(fine-inizio)+
                " millisecondi per ordinare " + array.length + " numeri \n" );
    }

    //messo per testare il CountingSort
    static void benchC(int[]array, int low, int high){
        long inizio = System.currentTimeMillis();
        CountingSort.countingSort2(array, low, high);
        long fine = System.currentTimeMillis();
        out.println(Arrays.toString(array));
        out.println(check(array));
        out.println("il CountingSort ha impiegato: " +(fine-inizio)+
                " millisecondi per ordinare " + array.length + " numeri \n" );
    }

    static void benchIns(int[]array, int low, int high){
        long inizio = System.currentTimeMillis();
        Sorting.InsectionSort(array, low, high);
        long fine = System.currentTimeMillis();
        out.println(Arrays.toString(array));
        out.println(check(array));
        out.println("L'insertion Sort ha impiegato: " +(fine-inizio)+
                " millisecondi per ordinare " + array.length + " numeri \n" );
    }
}
