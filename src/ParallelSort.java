import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelSort extends RecursiveAction {

    /**
     * Versione del Mergesort in cui le chiamate ricorsive sono eseguite in
     * parallelo, mentre la funzione di merge è ancora sequienziale
     */

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int low;
    private int high;
    private int SEQUENTIAL_CUTOFF = 512;
    private Node node;
    private Node nodeJ;
    public static List<Node> nodeList = Collections.synchronizedList(new ArrayList<Node>());
    public static List<Edge> edgeList = Collections.synchronizedList(new ArrayList<Edge>());


    ParallelSort(int[] arr, int l, int h) {
        array = arr;
        low = l;
        high = h;
        node = new Node(l, h);
        nodeList.add(this.node);
    }

    private Node getNode() {
        return this.node;
    }

    @Override
    protected void compute() {
        if ((high-low) <= 1) return;
        /**
        if ((high - low) < SEQUENTIAL_CUTOFF){
            Sorting.insectionSort(array, low, high);
        }**/
        else {
            int mid = (low + high) / 2;
            ParallelSort left = new ParallelSort(array, low, mid);
            ParallelSort right = new ParallelSort(array, mid, high);
            left.fork();
            edgeList.add(new Edge(this.getNode(), left.getNode()));
            right.compute();
            edgeList.add(new Edge(this.getNode(), right.getNode()));
            left.join();
            nodeJ = new Node(low, high);
            nodeList.add(nodeJ);

            if (Objects.equals(left.nodeJ, null)){
                edgeList.add(new Edge(left.getNode(), nodeJ));
            }else
                edgeList.add(new Edge(left.nodeJ, nodeJ));

            if (Objects.equals(right.nodeJ, null)){
                edgeList.add(new Edge(right.getNode(), nodeJ));
            }else
                edgeList.add(new Edge(right.nodeJ, nodeJ));

            MergeSort.serialMerge(array, low, mid, high);
        }
    }
}



