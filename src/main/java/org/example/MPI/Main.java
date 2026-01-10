package org.example.MPI;

import mpi.*;

public class Main {
    public static void main(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        // 1. Setup Data
        int N = 10; // Nodes
        int C = 3;

        Graph graph = new Graph(N);
        Colours colours = new Colours(C);
        colours.addColor(0, "red");
        colours.addColor(1, "green");
        colours.addColor(2, "blue");

        if (rank == 0) {
            System.out.println("Master process started with " + size + " workers.");
            try {
                long startTime = System.nanoTime();

                String result = GraphColouring.colourGraphMain(size, graph, colours);

                long endTime = System.nanoTime();
                System.out.println(result);
                System.out.printf("Execution time: %.4f seconds%n", (endTime - startTime) / 1e9);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        } else {
            // Workers just need to listen
            GraphColouring.colourGraphChild(rank, size, graph, colours.getNumberOfColours());
        }

        MPI.Finalize();
    }
}