
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
}
