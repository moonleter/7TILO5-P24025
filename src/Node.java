import java.util.ArrayList;
import java.util.List;

class Node {
    int id;
    int resource;
    int initialResource;
    List<Edge> edges = new ArrayList<>();

    public Node(int id, int resource) {
        this.id = id;
        this.resource = resource;
        this.initialResource = resource;
    }

    public void addEdge(Node target, int cost) {
        edges.add(new Edge(this, target, cost));
    }

    public int collectResource() {
        int collected = resource;
        resource = 0;
        return collected;
    }
}