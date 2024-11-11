import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResourceMaximizationGraph {
    private Map<Integer, Node> nodes = new HashMap<>();
    private int initialBudget;
    private int maxResources = 0;

    public ResourceMaximizationGraph(int initialBudget) {
        this.initialBudget = initialBudget;
    }

    public void addNode(int id, int resource) {
        nodes.put(id, new Node(id, resource));
    }

    public void addEdge(int fromId, int toId, int cost) {
        Node fromNode = nodes.get(fromId);
        Node toNode = nodes.get(toId);
        if (fromNode != null && toNode != null) {
            fromNode.addEdge(toNode, cost);
            toNode.addEdge(fromNode, cost); // Ensure bidirectional edges
        }
    }

    public int maximizeResources(int startId) {
        Set<Node> visited = new HashSet<>();
        dfs(nodes.get(startId), initialBudget, 0, visited, 0);
        return maxResources;
    }

    private void dfs(Node node, int currentBudget, int currentResources, Set<Node> visited, int time) {
        if (node == null || currentBudget <= 0 || visited.contains(node)) return;

        visited.add(node);
        int collectedResource = node.collectResource();
        int newResources = currentResources + collectedResource;
        maxResources = Math.max(maxResources, newResources);

        System.out.printf("[t_%d] h_i (N/A), u_i (%d) -> r=%d, z=%d%n", time, node.id, currentBudget, newResources);

        for (Edge edge : node.edges) {
            if (edge.cost <= currentBudget) {
                dfs(edge.to, currentBudget - edge.cost, newResources, visited, time + 1);
            }
        }

        visited.remove(node);
        node.resource = collectedResource; // Reset resource for other paths
    }
}