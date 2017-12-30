package Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class GPsortSmerge extends RecursiveAction {

    /**
     * Versione del Mergesort in cui le chiamate ricorsive sono eseguite in
     * parallelo, mentre la funzione di merge è ancora sequienziale
     */

    public static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int high;
    private static int SEQUENTIAL_CUTOFF = 1;
    private static int countFork = 0;
    private Node node; //il nodo "attuale"
    private Node nodeJ; //se istanziato, il nodo per effettuare il join dei due figli
    public static List<Node> nodeList = Collections.synchronizedList(new ArrayList<Node>());
    public static List<Edge> edgeList = Collections.synchronizedList(new ArrayList<Edge>());
    private static Object semaphore = new Object(); //semaphoro per la sincronizzazione su countfork

    public GPsortSmerge(int[] arr, int l, int h) {
        array = arr;
        low = l;
        high = h;
        node = new Node(l, h);
        nodeList.add(this.node);
    }

    /**
     * Ritorna il nodo attuale
     * @return node
     */
    private Node getNode() {
        return this.node;
    }

    /**
     * Riorna il nodo usato per il join fra i due figli
     * @return nodej
     */
    public Node getNodeJ() {
        return nodeJ;
    }

    /**
     * Ritorna il SEQUENTIAL_CUTOFF utilizzato
     * @return
     */
    public static int getSEQUENTIAL_CUTOFF() {
        return SEQUENTIAL_CUTOFF;
    }

    /**
     * RItorna il numero di fork effettuate
     * @return countFork
     */
    public static int getCountFork() {
        return countFork;
    }

    @Override
    protected void compute() {
        if ((high-low) <= 1) return;  //caso base per cutoff = 1

        //caso base per cutoff maggiori >1
        /*
        if ((high - low) < SEQUENTIAL_CUTOFF){
            Sorting.inserctionSort(array, low, high);
        }*/

        else {
            int mid = (low + high) / 2;
            GPsortSmerge left = new GPsortSmerge(array, low, mid);
            GPsortSmerge right = new GPsortSmerge(array, mid, high);
            //do un nuovo colore al figlio sinistro
            left.getNode().changeColor();
            //per via del thread halving, do lo stesso colore al nodo destro
            right.getNode().setColor(this.getNode().getColor());
            left.fork();
            //Diamo un accesso sincronizzato alla variabile countfork
            synchronized (semaphore){
                countFork++;
            }
            //arco verso il figlio sinistro
            edgeList.add(new Edge(this.getNode(), left.getNode()));
            //thread halving
            right.compute();
            //arco verso il figlio destro
            edgeList.add(new Edge(this.getNode(), right.getNode()));
            left.join();
            //creazione nodo di join
            nodeJ = new Node(low, high);
            //aggiunta di nodej all'insieme dei nodi del DAG
            nodeList.add(nodeJ);
            //setto a nodej lo stesso colore di node(che è uguale al colore di del figlio destro)
            this.getNodeJ().setColor(right.getNode().getColor());

            /*
            Aggiunta degli archi dal figlio destro e sinistro al nodo di join nodej
            prestando attenzione al fatto o meno che i figli siano o meno foglie (nel caso
            in cui fossero delle foglie nodej allora non esisterebbe)
             */
            if (Objects.equals(left.getNodeJ(), null)) {
                edgeList.add(new Edge(left.getNode(), nodeJ));
            } else
                edgeList.add(new Edge(left.getNodeJ(), nodeJ));

            if (Objects.equals(right.getNodeJ(), null)) {
                edgeList.add(new Edge(right.getNode(), nodeJ));
            } else
                edgeList.add(new Edge(right.getNodeJ(), nodeJ));

            //fusione dei due array
            Bench.MergeSort.serialMerge(array, low, mid, high);
        }
    }
}



