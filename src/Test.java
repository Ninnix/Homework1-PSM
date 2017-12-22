import Bench.*;
import Graph.GPsortPmerge;
import Graph.GPsortSmerge;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import static java.lang.System.out;


public class Test {

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]); //Il valore n specificato da linea di comando;
        int lenght = n;
        int[] array = new int[lenght];
        boolean graphMode = true;

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
        if (graphMode == false) {
            benchS(a1,0, a1.length);
            benchPS(a2, 0, a2.length);
            benchPP(a3, 0, a3.length);
            //benchAlg(a4, 0, a4.length);
        } else {
            graphPS(a2, 0, a2.length);
            //graphPP(a3, 0, a3.length);
        }
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
        PsortSmerge sort = new PsortSmerge(array, low, high);
        long inizio = System.currentTimeMillis();
        PsortSmerge.fjPool.invoke(sort);
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
        PsortPmerge sort2 = new PsortPmerge(array, low, high, aux);
        long inizio = System.currentTimeMillis();
        PsortPmerge.fjPool.invoke(sort2);
        long fine = System.currentTimeMillis();
        //out.println(Arrays.toString(array));
        out.println(check(array));
        out.println("Il mergesort parallelo ha impiegato: " +(fine-inizio)+
                " millisecondi per ordinare " + array.length + " numeri \n" );
    }

    //messo per testare che algoritmo usare
    static void benchAlg(int[]array, int low, int high){
        long inizio = System.currentTimeMillis();
        Sorting.insectionSort(array, low, high);
        //Sorting.countingSort(array);
        long fine = System.currentTimeMillis();
        out.println(Arrays.toString(array));
        out.println(check(array));
        out.println("L'algoritmo di sorting ha impiegato: " +(fine-inizio)+
                " millisecondi per ordinare " + array.length + " numeri \n" );
    }

    /**
     * Crea il DAG del mergesort paralleo con funzione di merge seriale
     * @param array
     * @param low
     * @param high
     */
    static void graphPS (int[] array, int low, int high){
        GPsortSmerge graphSort = new GPsortSmerge(array, low, high);
        GPsortSmerge.fjPool.invoke(graphSort);
        try {
            Utils.graphWrite(GPsortSmerge.nodeList, GPsortSmerge.edgeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Il DAG di esecuzione del mergesort parallelo con funzione di merge seriale è stato rappresentato nel file graph.graphml");
    }

    /**
     * Crea il DAG del mergesort paralleo con funzione di merge parallela
     * @param array
     * @param low
     * @param high
     */
    static void graphPP (int[] array, int low, int high) {
        int[] aux = new int[array.length];
        GPsortPmerge graphSort2 = new GPsortPmerge(array, low, high, aux);
        GPsortPmerge.fjPool.invoke(graphSort2);
        try {
            Utils.graphWrite(GPsortPmerge.nodeList, GPsortPmerge.edgeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Il DAG di esecuzione del mergesort parallelo con funzione di merge parallela è stato rappresentato nel file graph.graphml");
    }

    /**
     * Metodo che verifica che l'ordinamento sia corretto, ovvero che rispetta l’ordine crescente
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
