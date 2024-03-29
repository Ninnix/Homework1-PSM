import Bench.*;
import Graph.*;
import java.io.IOException;
import java.util.Random;
import static java.lang.System.out;

public class Main {

    /**
     * Vengono analizzati i parametri dati in input e si eseguono le computazioni richieste.
     * @param args Numero di interi, tipologia di input(A o B), L’opzione di debugging se attivata o meno (true o false)
     */
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]); //Il valore n specificato da linea di comando;
        int lenght = n;
        int[] array = new int[lenght];
        boolean graphMode = Boolean.parseBoolean(args[2]);

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
            throw new IllegalArgumentException("Tipologia di input non corretta");
        }
        int[] a1 = array;                 //array del mergesort sequenziale
        int[] a2 = new int[array.length]; //array del mergesort parallelo con funzione di merge seriale
        System.arraycopy( array, 0, a2, 0, array.length );
        int[] a3 = new int[array.length]; //array del mergesort parallelo
        System.arraycopy( array, 0, a3, 0, array.length );
        if (graphMode == false) {
            speedup(a1, 0, a1.length);

        } else {
            graphPS(a2, 0, a2.length);
            Node.setCount(0); //azzero gli id dei nodi
            graphPP(a3, 0, a3.length);
        }
    }

    /**
     * stampa su schermo il tempo di esecuzione del mergesort seriale
     * @param array array da ordinare
     * @param low indice di partenza (incluso)
     * @param high indice di fine (escluso)
     * @return il tempo di esecuzione del mergesort seriale sulla porzione di array (low, high)
     */
    static long benchS(int[]array, int low, int high){
        long inizio = System.currentTimeMillis();
        MergeSort.mergeSort(array,low,high);
        long fine = System.currentTimeMillis();
        //out.println(Arrays.toString(array));
        out.println(check(array));
        out.println("Il mergesort seriale ha impiegato: " +(fine-inizio)+
                " millisecondi per ordinare " + array.length + " interi \n" );
        return fine-inizio;
    }

    /**
     * Stampa il tempo di esecuzione del mergesort parallelo con funzione di merge seriale
     * @param array array da ordinare
     * @param low indice di inizio
     * @param high indice di fine (escluso)
     * @return il tempo di esecuzione dell'algoritmo di mergesort parallelo
     * con funzione di merge seriale
     */
    static long benchPS(int[]array, int low, int high){
        PsortSmerge sort = new PsortSmerge(array, low, high);
        long inizio = System.currentTimeMillis();
        PsortSmerge.fjPool.invoke(sort);
        long fine = System.currentTimeMillis();
        //out.println(Arrays.toString(array));
        out.println(check(array));
        out.println("Il mergesort parallelo con funzione di merge seriale ha impiegato: " +(fine-inizio)+
                " millisecondi per ordinare " + array.length + " interi \n" );
        return fine-inizio;
    }

    /**
     * Stampa su schermo il tempo di esecuzione del mergesort totalmente parallelo
     * @param array array da ordinare
     * @param low indice inizio
     * @param high indice fine (escluso)
     * @return il tempo di esecuzione
     */
    static  long benchPP(int[]array, int low, int high){
        int[] aux = new int[array.length];
        System.arraycopy( array, 0, aux, 0, array.length );
        PsortPmerge sort2 = new PsortPmerge(array, low, high, aux);
        long inizio = System.currentTimeMillis();
        PsortPmerge.fjPool.invoke(sort2);
        long fine = System.currentTimeMillis();
        //out.println(Arrays.toString(array));
        out.println(check(array));
        out.println("Il mergesort parallelo ha impiegato: " +(fine-inizio)+
                " millisecondi per ordinare " + array.length + " interi \n" );
        return fine-inizio;
    }

    /**
     * Ripete per 5 volte l'esecuzione del mergesort seriale sul array di input
     * @param array
     * @param low
     * @param high
     * @return
     */
    static long multiS (int[]array, int low, int high){
        int[] a1 = array;
        int[] a2 = new int[array.length];
        System.arraycopy( array, 0, a2, 0, array.length );
        int[] a3 = new int[array.length];
        System.arraycopy( array, 0, a3, 0, array.length );
        int[] a4 = new int[array.length];
        System.arraycopy( array, 0, a3, 0, array.length );
        int[] a5 = new int[array.length];
        System.arraycopy( array, 0, a3, 0, array.length );
        long t1 = benchS(a1, low, high);
        long t2 = benchS(a2, low, high);
        long t3 = benchS(a3, low, high);
        long t4 = benchS(a4, low, high);
        long t5 = benchS(a5, low, high);
        long mediaS = (t1+t2+t3+t4+t5)/5;
        System.out.println("Il mergesort seriale ha impiegato in media " + mediaS + " millisecondi \n");
        return mediaS;
    }

    /**
     * Ripete per 5 volte l'esecuzione del mergesort parallelo con funzione di merge sequenziale sul array di input
     * @param array
     * @param low
     * @param high
     * @return
     */
    static long multiPS (int[]array, int low, int high){
        int[] a1 = array;
        int[] a2 = new int[array.length];
        System.arraycopy( array, 0, a2, 0, array.length );
        int[] a3 = new int[array.length];
        System.arraycopy( array, 0, a3, 0, array.length );
        int[] a4 = new int[array.length];
        System.arraycopy( array, 0, a3, 0, array.length );
        int[] a5 = new int[array.length];
        System.arraycopy( array, 0, a3, 0, array.length );
        long t1 = benchPS(a1, low, high);
        long t2 = benchPS(a2, low, high);
        long t3 = benchPS(a3, low, high);
        long t4 = benchPS(a4, low, high);
        long t5 = benchPS(a5, low, high);
        long mediaPS = (t1+t2+t3+t4+t5)/5;
        System.out.println("Il mergesort parallelo con funzione di merge sequenziale ha impiegato in media " + mediaPS + " millisecondi \n");
        return mediaPS;
    }

    /**
     * Ripete per 5 volte l'esecuzione del mergesort parallelo sul array di input
     * @param array
     * @param low
     * @param high
     * @return
     */
    static long multiPP (int[]array, int low, int high){
        int[] a1 = array;
        int[] a2 = new int[array.length];
        System.arraycopy( array, 0, a2, 0, array.length );
        int[] a3 = new int[array.length];
        System.arraycopy( array, 0, a3, 0, array.length );
        int[] a4 = new int[array.length];
        System.arraycopy( array, 0, a3, 0, array.length );
        int[] a5 = new int[array.length];
        System.arraycopy( array, 0, a3, 0, array.length );
        long t1 = benchPP(a1, low, high);
        long t2 = benchPP(a2, low, high);
        long t3 = benchPP(a3, low, high);
        long t4 = benchPP(a4, low, high);
        long t5 = benchPP(a5, low, high);
        long mediaPP = (t1+t2+t3+t4+t5)/5;
        System.out.println("Il mergesort  parallelo ha impiegato in media " + mediaPP + " millisecondi \n");
        return mediaPP;
    }

    /**
     * esegue 5 volte tutti e tre gli algoritmi su l'array di input e calcola lo speedup delle versioni parallele
     * @param array
     * @param low
     * @param high
     */
    static void speedup (int[]array, int low, int high) {
        int[] a1 = array;
        int[] a2 = new int[array.length];
        System.arraycopy( array, 0, a2, 0, array.length );
        int[] a3 = new int[array.length];
        System.arraycopy( array, 0, a3, 0, array.length );
        long tS = multiS(a1,0, a1.length);
        long tPS = multiPS(a2, 0, a2.length);
        long tPP = multiPP(a3, 0, a3.length);
        double spPS = ((double)tS / tPS);
        double spPP = ((double)tS / tPP);
        System.out.println("Il mergesort parallelo con funzione di merge sequenziale ha avuto speedup " + spPS + " rispetto al mergesort sequenziale \n");
        System.out.println("Il mergesort parallelo ha avuto speedup " + spPP + " rispetto al mergesort sequenziale \n");
    }

    /**
     * Crea il DAG del mergesort paralleo con funzione di merge sequenziale
     * @param array array da ordinare e su cui creare il DAG
     * @param low indice di partenza
     * @param high indice di fine(escluso)
     */
    static void graphPS (int[] array, int low, int high){
        GPsortSmerge graphSort = new GPsortSmerge(array, low, high);
        GPsortSmerge.fjPool.invoke(graphSort);
        try {
            DrawGraph.graphWrite(GPsortSmerge.nodeList, GPsortSmerge.edgeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Il mergesort parallelo con funzione di merge sequenziale ha effettuato " + GPsortSmerge.getCountFork() + " fork, con un cutoff sequenziale di " +
                GPsortSmerge.getSEQUENTIAL_CUTOFF()+ " per ordinare " + array.length + " interi \n");
        System.out.println("Il DAG di esecuzione del mergesort parallelo con funzione di merge sequenziale è stato rappresentato nel file graphPS.graphml\n");
    }

    /**
     * Crea il DAG del mergesort paralleo con funzione di merge parallela
     * @param array array da ordinare
     * @param low indice di inizio
     * @param high indice di fine(escluso)
     */
    static void graphPP (int[] array, int low, int high) {
        int[] aux = new int[array.length];
        GPsortPmerge graphSort2 = new GPsortPmerge(array, low, high, aux);
        GPsortPmerge.fjPool.invoke(graphSort2);
        try {
            DrawGraph.graphWrite2(GPsortPmerge.nodeList, GPsortPmerge.edgeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Il mergesort parallelo con funzione di merge parallela ha effettuato " + GPsortPmerge.getCountFork() + " fork, con un cutoff sequenziale per il sort di " +
                GPsortPmerge.getSEQUENTIAL_CUTOFF() + " e di " + GParallelMerge.getSEQUENTIAL_CUTOFF() + " per il merge parallelo," + " per ordinare " + array.length + " interi \n" );
        System.out.println("Il DAG di esecuzione del mergesort parallelo è stato rappresentato nel file graphPP.graphml\n");
    }

    /**
     * Verifica che l'ordinamento sia corretto, ovvero che rispetti l’ordine crescente
     * @param array array da ordinare
     * @return true se è ordinato, false altrimenti
     */
    static boolean check(int[] array) {
        boolean ans = true;
        for (int i = 0; i < array.length-1; i++) {
            if (array[i] > array[i + 1]) {
                ans = false;
                break;
            }
        }
        return ans;
    }

}
