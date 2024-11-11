public class Main {
    public static void main(String[] args) {
        int initialBudget = 485;
        ResourceMaximizationGraph graph = new ResourceMaximizationGraph(initialBudget);

        // Definice uzlů a zdrojů
        graph.addNode(10, 10);
        graph.addNode(15, 15);
        graph.addNode(3, 15);
        graph.addNode(1, 1);
        graph.addNode(3, 3);
        graph.addNode(14, 14);
        graph.addNode(4, 4);
        graph.addNode(5, 5);
        graph.addNode(19, 19);
        graph.addNode(20, 20);
        graph.addNode(100, 1);
        graph.addNode(500, 5);
        graph.addNode(12, 12);
        graph.addNode(43, 43);
        graph.addNode(36, 36);


        // Definice hran a nákladů
        graph.addEdge(10, 14, 48);
        graph.addEdge(15, 14, 21);
        graph.addEdge(1, 40, 4);
        graph.addEdge(3, 40, 30);
        graph.addEdge(14, 5, 132);
        graph.addEdge(4, 40, 78);
        graph.addEdge(5, 40, 145);
        graph.addEdge(4, 19, 57);
        graph.addEdge(4, 20, 30);
        graph.addEdge(5, 100, 193);
        graph.addEdge(100, 500, 12);
        graph.addEdge(100, 12, 150);
        graph.addEdge(12, 43, 23);
        graph.addEdge(12, 36, 27);


        // Výpočet maximálních zdrojů
        int maxResources = graph.maximizeResources(5);
        System.out.println("Maximální získané zdroje: " + maxResources);
    }
}