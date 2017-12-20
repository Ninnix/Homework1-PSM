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
    private int SEQUENTIAL_CUTOFF = 512;
    private Node node;
    private Node nodeJ;
    public static List<Node> nodeList = Collections.synchronizedList(new ArrayList<Node>());
    public static List<Edge> edgeList = Collections.synchronizedList(new ArrayList<Edge>());
    public static volatile int countFork = 0;

    public GPsortPmerge(int[] arr, int l, int h, int[] aux) {
        array = arr;
        low = l;
        high = h;
        this.aux = aux;
        node = new Node(l, h);
        nodeList.add(this.node);
    }

    public Node getNode() {
        return node;
    }

    public Node getNodeJ() {
        return nodeJ;
    }

    @Override
    protected void compute() {
        int mid = (low + high) / 2;
        if ((high-low) <= 1) return;
        /**
        if ((high - low) < SEQUENTIAL_CUTOFF){
            MergeSort.mergeSort(array,low,high);
         } **/
         else {
            GPsortPmerge left = new GPsortPmerge(array, low, mid, aux);
            GPsortPmerge right = new GPsortPmerge(array, mid, high, aux);
            left.fork();
            countFork++;
            edgeList.add(new Edge(this.getNode(), left.getNode()));
            right.compute();
            edgeList.add(new Edge(this.getNode(), right.getNode()));
            left.join();
            for (int i = low; i < high; i++) {
                aux[i] = array[i];
            }
            GParallelMerge parmerge = new GParallelMerge(array, low, mid, mid, high, aux, low, high);
            Node nodeAux = parmerge.getNode();
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



