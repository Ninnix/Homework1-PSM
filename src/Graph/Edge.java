package Graph;

public class Edge {

    /**
     * Struttura dati per gestire gli archi del DAG
     */

    private int id;
    private Node source;
    private Node target;
    private static volatile int count = 0;

    public Edge(Node source, Node target) {
        this.id = increaseCount();
        this.source = source;
        this.target = target;
    }

    /**
     * Ritorna una descrizione in stringa dell'arco
     * @return Una stringa che rappresenta l'arco
     */
    @Override
    public String toString() {
        return "(" + source.toString() + "," + target.toString() + ")";
    }

    private synchronized int increaseCount() {
        return count++;
    }

    /**
     * Ritorna l'id del nodo
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Ritorna il nodo sorgente dell'arco
     * @return source
     */
    public Node getSource() {
        return this.source;
    }

    /**
     * Ritorna il nodo destinazione dell'arco
     * @return target
     */
    public Node getTarget() {
        return this.target;
    }
}


