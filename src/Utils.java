import java.util.ArrayList;

public class Utils {

    public String graphToString(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<gexf xmlns=\"http://www.gexf.net/1.2draft\" version=\"1.2\">\n" +
                "<graph mode=\"static\" defaultedgetype=\"directed\">\n";
        String stringNodes = nodeToString(nodes);
        String stringEdges = edgeToString(edges);
        return header + stringNodes + stringEdges + "    </graph>\n" +
                "</gexf>\n";
    }

    public String nodeToString(ArrayList<Node> nodes) {
        String out = "        <nodes>\n";
        for (Node node:nodes) {
            out = out + "            <node id=" + node.getId() +" label=" + node.toString() + " />\n";
        }
        return out + "        </nodes>\n";
    }

    public String edgeToString(ArrayList<Edge> edges) {
        String out = "        <edges>\n";
        for (Edge edge:edges) {
            out = out + "            <edge id=" + edge.getId() + "source=" + edge.getSource().getId() + "target=" + edge.getTarget().getId() + " />\n";
        }
        return out + "        </edges>\n";
    }
}
