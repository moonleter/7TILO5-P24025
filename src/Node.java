import java.util.ArrayList;
import java.util.List;

class Node {
    private int id;
    private int resource;
    private int initialResource;
    private List<Edge> edges = new ArrayList<>();
    private boolean resourceCollected = false;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getInitialResource() {
        return initialResource;
    }

    public void setInitialResource(int initialResource) {
        this.initialResource = initialResource;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void setResourceCollected(boolean collected) {
        this.resourceCollected = collected;
    }

    public boolean isResourceCollected() {
        return resourceCollected;
    }
}