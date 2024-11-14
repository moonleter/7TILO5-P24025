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
        currentSearchState.getVisitedNodes().add(startNode);
        currentSearchState.setTotalResourcesCollected(currentSearchState.getTotalResourcesCollected() + startNode.collectResource());
        startNode.setResourceCollected(true);

        System.out.println("[t_" + stepCounter++ + "] h_0 (0), u_" + startNode.getId() + " (" + startNode.getInitialResource() + ") -> r=" + currentSearchState.getRemainingBudget() + ", z=" + currentSearchState.getTotalResourcesCollected());

        performResourceMaximization(startNode, currentSearchState);

        System.out.println("Maximální získané zdroje: " + currentSearchState.getTotalResourcesCollected());
        return currentSearchState.getTotalResourcesCollected();
    }

    private void performResourceMaximization(Node startNode, CurrentSearchState currentSearchState) {
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>((e1, e2) -> {
            double ratio1 = (double) e1.getTo().getResource() / e1.getCost();
            double ratio2 = (double) e2.getTo().getResource() / e2.getCost();
            return Double.compare(ratio2, ratio1);
        });

        priorityQueue.addAll(startNode.getEdges());

        while (!priorityQueue.isEmpty() && currentSearchState.getRemainingBudget() > 0) {
            Edge edge = priorityQueue.poll();
            Node fromNode = edge.getFrom();
            Node toNode = edge.getTo();

            if (!currentSearchState.getVisitedNodes().contains(toNode) && currentSearchState.getRemainingBudget() >= edge.getCost()) {
                currentSearchState.setRemainingBudget(currentSearchState.getRemainingBudget() - edge.getCost());
                currentSearchState.setTotalResourcesCollected(currentSearchState.getTotalResourcesCollected() + toNode.collectResource());
                toNode.setResourceCollected(true);
                currentSearchState.getVisitedNodes().add(toNode);

                System.out.println("[t_" + stepCounter++ + "] h_" + fromNode.getId() + " (" + edge.getCost() + "), u_" + toNode.getId() + " (" + toNode.getInitialResource() + ") -> r=" + currentSearchState.getRemainingBudget() + ", z=" + currentSearchState.getTotalResourcesCollected());

                priorityQueue.addAll(toNode.getEdges());
            }
        }
    }
}