
import java.io.*;
import java.util.List;

public class Utils {

    public static String graphToString(List<Node> nodes, List<Edge> edges) {
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<gexf xmlns=\"http://www.gexf.net/1.2draft\" version=\"1.2\">\n" +
                "<graph mode=\"static\" defaultedgetype=\"directed\">\n";
        String stringNodes = nodeToString(nodes);
        String stringEdges = edgeToString(edges);
        return header + stringNodes + stringEdges + "    </graph>\n" +
                "</gexf>\n";
    }

    public static String nodeToString(List<Node> nodes) {
        String out = "        <nodes>\n";
        for (Node node:nodes) {
            out = out + "            <node id=" + '"' + node.getId() + '"' +" label=" + '"' + node.toString() + '"' + " />\n";
        }
        return out + "        </nodes>\n";
    }

    public static String edgeToString(List<Edge> edges) {
        String out = "        <edges>\n";
        for (Edge edge:edges) {
            out = out + "            <edge id=" + '"' + edge.getId() + '"' + " source=" + '"' + edge.getSource().getId() + '"' + " target=" + '"' + edge.getTarget().getId() + '"' + " />\n";
        }
        return out + "        </edges>\n";
    }

    public static void graphWrite(List<Node> nodes, List<Edge> edges) throws IOException {
        String out = graphToString(nodes, edges);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("graph.gexf"), "utf-8"))) {
            writer.write(out);
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
