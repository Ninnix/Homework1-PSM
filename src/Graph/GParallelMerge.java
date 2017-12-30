package Graph;

import Bench.Sorting;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class GParallelMerge extends RecursiveAction {

    public static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int[] aux;
    private int sxLow;
    private int sxHigh;
    private int dxLow;
    private int dxHigh;
    private int auxLow;
    private int auxHigh;
    private static int SEQUENTIAL_CUTOFF = 1;
    private Node node; //nodo attuale
    private Node nodeJ; //nodo di join
    public List<Node> nodeList = GPsortPmerge.nodeList;
    public List<Edge> edgeList = GPsortPmerge.edgeList;

    GParallelMerge(int[] arr, int sxL, int sxH, int dxL, int dxH, int[] aux, int auxL, int auxH) {
        array = arr;
        this.aux = aux;
        sxLow = sxL;
        sxHigh = sxH;
        dxLow = dxL;
        dxHigh = dxH;
        auxLow = auxL;
        auxHigh = auxH;
        node = new Node(sxLow, sxHigh, dxLow, dxHigh);
        nodeList.add(node);
    }

    public Node getNode() {
        return node;
    }

    public Node getNodeJ() {
        return nodeJ;
    }

    public static int getSEQUENTIAL_CUTOFF() {
        return SEQUENTIAL_CUTOFF;
    }

    @Override
    protected void compute() {
        int lenSx = sxHigh - sxLow;
        int lenDx = dxHigh - dxLow;
        if (lenSx < lenDx) { //scambio gli indici in modo da avere sempre array di sinistra piu' grande
            int appoggio;

            appoggio = sxLow;
            sxLow = dxLow;
            dxLow = appoggio;

            appoggio = sxHigh;
            sxHigh = dxHigh;
            dxHigh = appoggio;

            appoggio = lenSx;
            lenSx = lenDx;
            lenDx = appoggio;
        }
        //caso base per cutoff = 1
        if (lenSx == 1 && lenDx == 1) {
            if (aux[sxLow] <= aux[dxLow]) {
                array[auxLow] = aux[sxLow];
                array[auxLow+1] = aux[dxLow];
            } else {
                array[auxLow] = aux[dxLow];
                array[auxLow+1] = aux[sxLow];
            }
            return;
        }
        if (lenSx == 1 && lenDx == 0) {
            array[auxLow] = aux[sxLow];
            return;
        }

        //caso base per cutoff > 1
        /*
        if (lenSx < SEQUENTIAL_CUTOFF) {
            Sorting.serialMerge2(array,sxLow,sxHigh,dxLow,dxHigh,aux,auxLow,auxHigh);
            return;
        } */

         else {
            int sxMed = (sxHigh + sxLow) / 2; //mediano sottoarray piu' grande
            int dxInd = Sorting.binarySearch(aux[sxMed], aux, dxLow, dxHigh); //posizione mediano nel sottoarray di destra
            int auxInd = (sxMed - sxLow) + (dxInd - dxLow); //numero elementi (indice) piu' piccoli del sxMed
            GParallelMerge left = new GParallelMerge(array, sxLow, sxMed, dxLow, dxInd, aux, auxLow,auxLow + auxInd);
            GParallelMerge right = new GParallelMerge(array, sxMed, sxHigh, dxInd, dxHigh, aux, auxLow + auxInd, auxHigh);
            //cambiamo colore al figlio sinistro
            left.getNode().changeColor();
            //settiamo lo stesso colore al figlio destro
            right.getNode().setColor(this.getNode().getColor());
            left.fork();
            //accesso sincronizzato a countFork
            synchronized (GPsortPmerge.semaphore){
                GPsortPmerge.countFork++;
            }
            //aggiunto arco figlio sinistro
            edgeList.add(new Edge(this.getNode(), left.getNode()));
            //thread halving
            right.compute();
            //aggiunta arco figlio destro
            edgeList.add(new Edge(this.getNode(), right.getNode()));
            left.join();
            nodeJ = new Node(auxLow, auxHigh);
            nodeList.add(nodeJ);
            //setto al nodo di join lo stesso colore del nodo attuale
            this.getNodeJ().setColor(right.getNode().getColor());

            /*
            aggiunta degli archi dal figlio destro e di sinistra al nodo di join
            facendo attenzione al caso in cui uno dei figli o entrambi siano delle foglie
             */

            if (Objects.equals(left.getNodeJ(), null)){
                edgeList.add(new Edge(left.getNode(), nodeJ));
            }else
                edgeList.add(new Edge(left.getNodeJ(), nodeJ));

            if (Objects.equals(right.getNodeJ(), null)){
                edgeList.add(new Edge(right.getNode(), nodeJ));
            }else
                edgeList.add(new Edge(right.getNodeJ(), nodeJ));
        }
    }
}






