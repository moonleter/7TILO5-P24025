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

        SearchState searchState = new SearchState(initialBudget);
        searchState.visitedNodes.add(startNode);
        searchState.totalResourcesCollected += startNode.collectResource();

        System.out.println("[t_" + stepCounter++ + "] h_0 (0), u_" + startNode.id + " (" + startNode.initialResource + ") -> r=" + searchState.budget + ", z=" + searchState.totalResourcesCollected);

        performGreedyBestFirstSearch(startNode, searchState);

        System.out.println("Maximální získané zdroje: " + searchState.totalResourcesCollected);
        return searchState.totalResourcesCollected;
    }

    private void performGreedyBestFirstSearch(Node startNode, SearchState searchState) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>((n1, n2) -> {
            double ratio1 = (double) n1.resource / (n1.edges.isEmpty() ? 1 : n1.edges.stream().mapToInt(e -> e.cost).average().orElse(1));
            double ratio2 = (double) n2.resource / (n2.edges.isEmpty() ? 1 : n2.edges.stream().mapToInt(e -> e.cost).average().orElse(1));
            return Double.compare(ratio2, ratio1);
        });
        priorityQueue.add(startNode);

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();

            List<Edge> sortedEdges = new ArrayList<>(currentNode.edges);
            sortedEdges.sort((e1, e2) -> {
                double resourceCostRatio1 = (double) e1.to.resource / e1.cost;
                double resourceCostRatio2 = (double) e2.to.resource / e2.cost;
                return Double.compare(resourceCostRatio2, resourceCostRatio1);
            });

            for (Edge edge : sortedEdges) {
                Node fromNode = edge.from;
                Node toNode = edge.to;

                if (fromNode == currentNode && !searchState.visitedNodes.contains(toNode) && searchState.budget >= edge.cost) {
                    if (toNode.resource > 0) {
                        searchState.budget -= edge.cost;
                        searchState.totalResourcesCollected += toNode.collectResource();
                        searchState.budget += toNode.initialResource;
                        searchState.visitedNodes.add(toNode);

                        System.out.println("[t_" + stepCounter++ + "] h_" + fromNode.id + " (" + edge.cost + "), u_" + toNode.id + " (" + toNode.initialResource + ") -> r=" + searchState.budget + ", z=" + searchState.totalResourcesCollected);

                        priorityQueue.add(toNode);
                    }
                }
            }
        }
    }
}