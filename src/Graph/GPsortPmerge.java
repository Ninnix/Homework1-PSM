package Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class GPsortPmerge extends RecursiveAction {

    public static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int high;
    private int[] aux;
    private static int SEQUENTIAL_CUTOFF = 1;
    static volatile int countFork = 0;
    private Node node; //il nodo "attuale"
    private Node nodeJ; //il nodo per il join degli figli
    public static List<Node> nodeList = Collections.synchronizedList(new ArrayList<Node>());
    public static List<Edge> edgeList = Collections.synchronizedList(new ArrayList<Edge>());

    public static Object semaphore = new Object(); //semaforo per la sincronizzazione su countFork


    public GPsortPmerge(int[] arr, int l, int h, int[] aux) {
        array = arr;
        low = l;
        high = h;
        this.aux = aux;
        node = new Node(l, h);
        nodeList.add(this.node);
    }

    /**
     * Ritorna il nodo attuale
     * @return node
     */
    public Node getNode() {
        return node;
    }

    /**
     * Ritorna il nodo di join
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
     * Ritorna il numero di fork eseguite
     * @return countFork
     */
    public static int getCountFork() {
        return countFork;
    }

    @Override
    protected void compute() {
        int mid = (low + high) / 2;
        if ((high-low) <= 1) return; //caso base per cutoff = 1

        //caso base per cutoff >1
        /*
        if ((high - low) < SEQUENTIAL_CUTOFF){
            MergeSort.mergeSort(array,low,high);
         } */

         else {
            GPsortPmerge left = new GPsortPmerge(array, low, mid, aux);
            GPsortPmerge right = new GPsortPmerge(array, mid, high, aux);
            //cambio colore al figlio sinistro dal momento che rappresenta un nuovo thread
            left.getNode().changeColor();
            //per via del thread halving setto lo stesso colore del padre al figlio destro
            right.getNode().setColor(this.getNode().getColor());
            left.fork();
            //accesso sincronizzato alla variabile countFork
            synchronized (semaphore){
                countFork++;
            }
            //aggiunta arco figlio sinistro
            edgeList.add(new Edge(this.getNode(), left.getNode()));
            //thread halving
            right.compute();
            //aggiunta arco figlio destro
            edgeList.add(new Edge(this.getNode(), right.getNode()));
            left.join();
            //aggiornamento dell'array ausiliario
            for (int i = low; i < high; i++) {
                aux[i] = array[i];
            }
            GParallelMerge parmerge = new GParallelMerge(array, low, mid, mid, high, aux, low, high);
            //nodo ausiliario per una migliore resa grafica del DAG
            Node nodeAux = parmerge.getNode();
            //Stesso colore del padre dato che si tratta del nodo di join
            nodeAux.setColor(right.getNode().getColor());

            /*
            aggiunta degli archi dal figlio destro e di sinistra al nodo di join
            facendo attenzione al caso in cui uno dei figli o entrambi siano delle foglie
             */

            if (Objects.equals(left.getNodeJ(), null)){
                edgeList.add(new Edge(left.getNode(), nodeAux));
            }else
                edgeList.add(new Edge(left.getNodeJ(), nodeAux));

            if (Objects.equals(right.getNodeJ(), null)){
                edgeList.add(new Edge(right.getNode(), nodeAux));
            }else
                edgeList.add(new Edge(right.getNodeJ(), nodeAux));
            parmerge.compute();
            if (Objects.equals(parmerge.getNodeJ(), null))
                nodeJ = parmerge.getNode();
            else nodeJ = parmerge.getNodeJ();
        }
    }

}




