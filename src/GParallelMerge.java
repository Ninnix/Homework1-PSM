import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class GParallelMerge extends RecursiveAction {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int[] aux;
    private int sxLow;
    private int sxHigh;
    private int dxLow;
    private int dxHigh;
    private int auxLow;
    private int auxHigh;
    private int SEQUENTIAL_CUTOFF = 1024;
    private Node node;
    public List<Node> nodeList = GPsortPmerge.nodeList;
    public List<Edge> edgeList = GPsortPmerge.edgeList;
    public int countFork = GPsortPmerge.countFork;

    private Node nodeJ;

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
        /**
        if (lenSx < SEQUENTIAL_CUTOFF) {
            Sorting.serialMerge2(array,sxLow,sxHigh,dxLow,dxHigh,aux,auxLow,auxHigh);
            return;
        } **/
         else {
            int sxMed = (sxHigh + sxLow) / 2; //mediano sottoarray piu' grande
            int dxInd = Utils.binarySearch(aux[sxMed], aux, dxLow, dxHigh); //posizione mediano sottoarray più piccolo
            int auxInd = (sxMed - sxLow) + (dxInd - dxLow); //numero elementi (indice) piu' piccoli del sxMed
            GParallelMerge left = new GParallelMerge(array, sxLow, sxMed, dxLow, dxInd, aux, auxLow,auxLow + auxInd);
            GParallelMerge right = new GParallelMerge(array, sxMed, sxHigh, dxInd, dxHigh, aux, auxLow + auxInd, auxHigh);
            left.fork();
            countFork++;
            edgeList.add(new Edge(this.getNode(), left.getNode()));
            right.compute();
            edgeList.add(new Edge(this.getNode(), right.getNode()));
            GPsortPmerge.countFork++;
            left.join();
            nodeJ = new Node(auxLow, auxHigh);
            nodeList.add(nodeJ);

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






