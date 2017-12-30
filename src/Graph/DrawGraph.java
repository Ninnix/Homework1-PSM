package Graph;

import Graph.Edge;
import Graph.Node;
import java.io.*;
import java.util.List;

public class DrawGraph {

    /**
     * Scrive il file graph.graphml che può essere visualizzato su https://www.yworks.com/yed-live/
     * @param nodes
     * @param edges
     * @return
     */
    public static String graphToString(List<Node> nodes, List<Edge> edges) {
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<graphml\n" +
                " xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n" +
                " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                " xmlns:y=\"http://www.yworks.com/xml/graphml\"\n" +
                " xmlns:yed=\"http://www.yworks.com/xml/yed/3\"\n" +
                " xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd\">\n" +
                "  <key for=\"node\" id=\"d1\" yfiles.type=\"nodegraphics\"/>\n" +
                "  <graph edgedefault=\"directed\" id=\"G\">\n";
        String stringNodes = nodeToString(nodes);
        String stringEdges = edgeToString(edges);
        return header + stringNodes + stringEdges + "  </graph>\n" +
                "</graphml>\n";
    }

    /**
     * Trasforma una lista di oggetti di tipo node in una stringa di tipo xml
     * @param nodes Una lista di node
     * @return Una stringa per ogni nodo
     */
    public static String nodeToString(List<Node> nodes) {
        String out = "";
        for (Node node:nodes) {
            out = out +
                    "    <node id=\"n" + node.getId() + "\">\n" +
                    "      <data key=\"d1\">\n" +
                    "        <y:ShapeNode>\n" +
                    "          <y:Shape type=\"rectangle\"/>\n" +
                    "          <y:Geometry height=\"30.0\" width=\"30.0\" x=\"0.0\" y=\"0.0\"/>\n" +
                    "          <y:Fill color=\""+ node.getColor() +"\" transparent=\"false\"/>\n" +
                    "          <y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>\n" +
                    "          <y:NodeLabel>"+ node.toString() +"</y:NodeLabel>\n" +
                    "        </y:ShapeNode>\n" +
                    "      </data>\n" +
                    "    </node>\n";
        }
        return out;
    }

    /**
     * Trasforma una lista di archi in una stringa xml
     * @param edges
     * @return
     */
    public static String edgeToString(List<Edge> edges) {
        String out = "";
        for (Edge edge:edges) {
            out = out + "   <edge source=\"n" + edge.getSource().getId() + "\" target=\"n" + edge.getTarget().getId() +"\"/>\n";
        }
        return out;
    }

    /**
     * Scrittura del DAG del mergesort parallelo con funzione di merge seriale
     * @param nodes
     * @param edges
     * @throws IOException
     */
    public static void graphWrite(List<Node> nodes, List<Edge> edges) throws IOException {
        String out = graphToString(nodes, edges);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("graphPS.graphml"), "utf-8"))) {
            writer.write(out);
        }
    }

    /**
     * Scrittura del DAG del mergesort parallelo
     * @param nodes
     * @param edges
     * @throws IOException
     */
    public static void graphWrite2(List<Node> nodes, List<Edge> edges) throws IOException {
        String out = graphToString(nodes, edges);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("graphPP.graphml"), "utf-8"))) {
            writer.write(out);
        }
    }
}
