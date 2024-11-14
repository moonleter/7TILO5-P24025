import java.util.HashSet;
import java.util.Set;

class CurrentSearchState {
    int remainingBudget;
    int totalResourcesCollected;
    Set<Node> visitedNodes;

    public CurrentSearchState(int initialBudget) {
        this.remainingBudget = initialBudget;
        this.totalResourcesCollected = 0;
        this.visitedNodes = new HashSet<>();
    }
}