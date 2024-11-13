import java.util.HashSet;
import java.util.Set;

class SearchState {
    int budget;
    int totalResourcesCollected;
    Set<Node> visitedNodes;

    public SearchState(int initialBudget) {
        this.budget = initialBudget;
        this.totalResourcesCollected = 0;
        this.visitedNodes = new HashSet<>();
    }
}