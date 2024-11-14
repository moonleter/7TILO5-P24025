import java.util.*;

public class ResourceMaximizationGraph {
    private Map<Integer, Node> nodes = new HashMap<>();
    private int initialBudget;
    private int stepCounter = 1;

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
        Node startNode = nodes.get(startId);
        if (startNode == null) return 0;

        CurrentSearchState currentSearchState = new CurrentSearchState(initialBudget);
        currentSearchState.visitedNodes.add(startNode);
        currentSearchState.totalResourcesCollected += startNode.collectResource();

        System.out.println("[t_" + stepCounter++ + "] h_0 (0), u_" + startNode.id + " (" + startNode.initialResource + ") -> r=" + currentSearchState.remainingBudget + ", z=" + currentSearchState.totalResourcesCollected);

        performGreedyBestFirstSearch(startNode, currentSearchState);

        System.out.println("Maximální získané zdroje: " + currentSearchState.totalResourcesCollected);
        return currentSearchState.totalResourcesCollected;
    }

    private void performGreedyBestFirstSearch(Node startNode, CurrentSearchState currentSearchState) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>((n1, n2) -> {
            double ratio1 = (double) n1.resource / (n1.edges.isEmpty() ? 1 : n1.edges.stream().mapToInt(e -> e.cost).average().orElse(1));
            double ratio2 = (double) n2.resource / (n2.edges.isEmpty() ? 1 : n2.edges.stream().mapToInt(e -> e.cost).average().orElse(1));
            return Double.compare(ratio2, ratio1);
        });
        priorityQueue.add(startNode);

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();

            List<Edge> sortedEdges = new ArrayList<>(currentNode.edges);
            sortedEdges.forEach(e -> e.to = e.to == currentNode ? e.from : e.to);


            for (Edge edge : sortedEdges) {
                Node fromNode = edge.from;
                Node toNode = edge.to;

                if (fromNode == currentNode && !currentSearchState.visitedNodes.contains(toNode) && currentSearchState.remainingBudget >= edge.cost) {
                    if (toNode.resource > 0) {
                        currentSearchState.remainingBudget -= edge.cost;
                        currentSearchState.totalResourcesCollected += toNode.collectResource();
                        currentSearchState.remainingBudget += toNode.initialResource;
                        currentSearchState.visitedNodes.add(toNode);

                        System.out.println("[t_" + stepCounter++ + "] h_" + fromNode.id + " (" + edge.cost + "), u_" + toNode.id + " (" + toNode.initialResource + ") -> r=" + currentSearchState.remainingBudget + ", z=" + currentSearchState.totalResourcesCollected);

                        priorityQueue.add(toNode);
                    }
                }
            }
        }
    }
}