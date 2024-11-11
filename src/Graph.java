public class Graph {
    private static final int INF = Integer.MAX_VALUE;  // Reprezentace nekonečna pro nedostupné cesty
    private int[][] adjacencyMatrix;
    private int numNodes;

    // Konstruktor pro inicializaci matice sousednosti
    public Graph(int numNodes) {
        this.numNodes = numNodes;
        adjacencyMatrix = new int[numNodes][numNodes];

        // Inicializace matice, kde všechno nastavíme na nekonečno
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (i == j) {
                    adjacencyMatrix[i][j] = 0;  // Náklady z uzlu na sebe sama jsou 0
                } else {
                    adjacencyMatrix[i][j] = INF;
                }
            }
        }
    }

    // Metoda pro přidání hrany mezi dvěma uzly s váhou
    public void addEdge(int source, int destination, int weight) {
        adjacencyMatrix[source][destination] = weight;
        adjacencyMatrix[destination][source] = weight;  // Pokud je graf neorientovaný, nastavíme i opačnou cestu
    }

    // Metoda pro výpis matice sousednosti
    public void printAdjacencyMatrix() {
        System.out.println("Adjacency Matrix:");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (adjacencyMatrix[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(adjacencyMatrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    // Příklad používání
    public static void main(String[] args) {
        int numNodes = 7;  // Počet uzlů (můžeš přizpůsobit podle potřeby)
        Graph graph = new Graph(numNodes);

        // Přidání hran podle tvého grafu (uzly jsou indexované od 0)
        graph.addEdge(0, 1, 4);      // Hrana mezi uzly 1 a 2 s náklady 4
        graph.addEdge(1, 2, 30);     // Hrana mezi uzly 2 a 3 s náklady 30
        graph.addEdge(1, 3, 40);     // Hrana mezi uzly 2 a 4 s náklady 40
        graph.addEdge(0, 4, 193);    // Hrana mezi uzly 1 a 5 s náklady 193
        graph.addEdge(4, 3, 145);    // Hrana mezi uzly 5 a 4 s náklady 145
        graph.addEdge(4, 5, 132);    // Hrana mezi uzly 5 a 10 s náklady 132
        graph.addEdge(5, 3, 57);     // Hrana mezi uzly 10 a 4 s náklady 57
        graph.addEdge(4, 6, 12);     // Hrana mezi uzly 5 a 12 s náklady 12

        // Výpis matice sousednosti
        graph.printAdjacencyMatrix();
    }
}
