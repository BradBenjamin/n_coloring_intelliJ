package org.example.Thread;

import java.util.*;


public class Main {
    static int NR_VERTICES = 10;

    public static void main(String[] args) {
        Graph graph = new Graph(NR_VERTICES);
        System.out.println("GRAPH INFORMATION:");
        System.out.println(graph);

        List<String> colours = new ArrayList<>();
        colours.add("pink");
        colours.add("red");
        colours.add("green");
        colours.add("blue");
        colours.add("yellow");
        colours.add("orange");
        colours.add("purple");
        System.out.println("The list of colours is " + colours + ".\n");

        long time1 = System.nanoTime();

        GraphColouring graph_colouring = new GraphColouring(graph, colours);
        graph_colouring.colourGraph(10);

        long time2 = System.nanoTime();

        System.out.println(graph_colouring);
        System.out.println(graph_colouring.visualCheck());

        double duration = (time2 - time1) / 1000000000.0;
        System.out.println("Duration: " + duration + " seconds.");
    }
}