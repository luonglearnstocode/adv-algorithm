package lab5;

import java.io.File;

/**
 *
 * @author Luong Nguyen
 */
public class Graph {
    
    /* tell how many nodes a graph has */
    public int nodes() {
        return -1;
        
    }
    
    /* read a graph from the file */
    public boolean readGraph(File file) {
        return false;
    }
    
    /* print a graph */
    public void printGraph() {
    }
    
    /* Depth First Search
    * start is the node from where the search begins
    * visited is an array of all the visited nodes
    * pred is an describing the search path
    */
    void dfs(int start, boolean visited[], int pred[]) {
        
    }
    
    /* find a maze solution */
    private static int mazeSolution(int from, int to, int pred[], int steps[]) {
        int i, n, node;
        // first count how many edges between the end and the start
        node = to;
        n = 1;
        while ((node = pred[node]) != from) {
            n++;
        }
        // then reverse pred[] array to steps[] array
        node = to;
        i = n;
        while (i >= 0) {
            steps[i--] = node;
            node = pred[node];
        }
        // include also the end vertex
        return (n + 1);
    }
    private final static String FILE = "maze.grh";
    private final static int FROM = 0;
    private final static int TO = 15;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Graph g = new Graph();
        // read the graph. and do the depth-first search
        System.out.println("Graph Adjacent list");
        g.readGraph(new File(FILE));
        g.printGraph();
        boolean visited[] = new boolean[g.nodes()];
        int pred[] = new int[g.nodes()];
        g.dfs(FROM, visited, pred);
        // then check if there is a solution by looking from the backwards to the start
        int steps[] = new int[g.nodes()];
        System.out.println("\nMaze solution from " + FROM + " to " + TO);;
        int n = mazeSolution(FROM, TO, pred, steps);
        for (int i = 0; i < n; i++) {
            System.out.print(steps[i] + " ");
        }
        System.out.println();
    }
}
