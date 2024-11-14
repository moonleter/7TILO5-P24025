import java.util.HashSet;
import java.util.Set;

class CurrentSearchState {
    private int remainingBudget;
    private int totalResourcesCollected;
    private Set<Node> visitedNodes;

    public CurrentSearchState(int initialBudget) {
        this.remainingBudget = initialBudget;
        this.totalResourcesCollected = 0;
        this.visitedNodes = new HashSet<>();
    }

    public int getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(int remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public int getTotalResourcesCollected() {
        return totalResourcesCollected;
    }

    public void setTotalResourcesCollected(int totalResourcesCollected) {
        this.totalResourcesCollected = totalResourcesCollected;
    }

    public Set<Node> getVisitedNodes() {
        return visitedNodes;
    }

    public void setVisitedNodes(Set<Node> visitedNodes) {
        this.visitedNodes = visitedNodes;
    }
}