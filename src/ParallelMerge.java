import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMerge extends RecursiveAction {

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
    public List<Node> nodeList = PsortPmerge.nodeList;
    public List<Edge> edgeList = PsortPmerge.edgeList;
    public int countFork = PsortPmerge.countFork;

    private Node nodeJ;

    ParallelMerge(int[] arr,int sxL,int sxH,int dxL, int dxH, int[] aux, int auxL, int auxH) {
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
            int dxInd = binarySearch(aux[sxMed], aux, dxLow, dxHigh); //posizione mediano sottoarray più piccolo
            int auxInd = (sxMed - sxLow) + (dxInd - dxLow); //numero elementi (indice) piu' piccoli del sxMed
            ParallelMerge left = new ParallelMerge(array, sxLow, sxMed, dxLow, dxInd, aux, auxLow,auxLow + auxInd);
            ParallelMerge right = new ParallelMerge(array, sxMed, sxHigh, dxInd, dxHigh, aux, auxLow + auxInd, auxHigh);
            left.compute();
            countFork++;
            edgeList.add(new Edge(this.getNode(), left.getNode()));
            right.compute();
            edgeList.add(new Edge(this.getNode(), right.getNode()));
            PsortPmerge.countFork++;
            //left.join();
            //nodeJ = new Node(Math.min(sxLow, dxLow), Math.max(sxHigh, dxHigh));
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

    /**
     * @param x   la chiave da cercare
     * @param arr l'array in cui cercare
     * @param l   indice low dell'array
     * @param h   indice di high dell'array
     * @return la posizione (inclusa) in cui gli elementi sono maggiori o uguali di x
     */
    public static int binarySearch(int x, int[] arr, int l, int h) {
        int low = l;
        int high = Math.max(l, h);
        while (low < high) {
            int mid = ((low + high) / 2);
            if (x <= arr[mid])
                high = mid;
            else
                low = mid + 1;
        }
        return high;
    }

}


/*
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
        } else {
            int sxMed = (sxHigh + sxLow) / 2; //mediano sottoarray piu' grande
            int dxInd = binarySearch(aux[sxMed], aux, dxLow, dxHigh); //posizione mediano sottoarray più piccolo
            int auxInd = (sxMed - sxLow) + (dxInd - dxLow); //numero elementi (indice) piu' piccoli del sxMed
            ParallelMerge left = new ParallelMerge(array, sxLow, sxMed, dxLow, dxInd, aux, auxLow,auxLow + auxInd);
            ParallelMerge right = new ParallelMerge(array, sxMed, sxHigh, dxInd, dxHigh, aux, auxLow + auxInd, auxHigh);
            left.fork();
            right.compute();
            left.join();
        }
    }
 */




