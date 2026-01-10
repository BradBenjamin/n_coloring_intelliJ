package org.example.MPI;

import java.util.*;

public class Graph {
    private final int numberOfNodes;
    private final List<List<Integer>> edges;

    public Graph(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        this.edges = new ArrayList<>(numberOfNodes);

        // Pre-fill the lists to avoid null checks later
        for (int i = 0; i < numberOfNodes; i++) {
            this.edges.add(new ArrayList<>());
        }
        createEdges();
    }

    private void createEdges() {
        Random random = new Random();
        // Use a clearer density metric (e.g., 20% of max edges)
        int targetEdges = (int) (Math.pow(numberOfNodes, 2) / 5);

        for (int i = 0; i < targetEdges; i++) {
            int u = random.nextInt(numberOfNodes);
            int v = random.nextInt(numberOfNodes);
            addEdge(u, v);
        }
    }

    private void addEdge(int u, int v) {
        // Simple check: no self-loops, no duplicates
        if (u == v || edges.get(u).contains(v)) return;

        edges.get(u).add(v);
        edges.get(v).add(u); // Undirected
    }

    public List<Integer> getNeighbors(int node) {
        return edges.get(node);
    }

    public int size() {
        return numberOfNodes;
    }
}