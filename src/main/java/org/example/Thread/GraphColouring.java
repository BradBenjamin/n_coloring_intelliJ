package org.example.Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GraphColouring {
    private Graph graph_to_colour;
    private List<String> available_colours;
    private List<String> colours_of_the_nodes;


    public GraphColouring(Graph graph, List<String> colours){
        this.graph_to_colour = graph;
        this.available_colours = colours;

        colours_of_the_nodes = new ArrayList<>();
        for (Integer node: graph_to_colour.getNodes()){
            colours_of_the_nodes.add(" ");
        }

    }

    public void colourGraph(Integer number_of_threads){
        Lock lock = new ReentrantLock();
        List<String> partial_colours_of_the_nodes = new ArrayList<>();
        for (Integer node: graph_to_colour.getNodes()){
            partial_colours_of_the_nodes.add(" ");
        }
        partial_colours_of_the_nodes.set(0, available_colours.get(0));
        findSolution(number_of_threads, 0, lock, partial_colours_of_the_nodes);
    }

    public void findSolution(Integer number_of_threads, Integer node, Lock lock, List<String> partial_colours_of_the_nodes){
        if (oneSolutionHasBeenFound()) {
//            System.out.println("ok1");
            return;
        }

        if (node + 1 == graph_to_colour.size()) {
            if (colorIsValid(node, partial_colours_of_the_nodes)) {
                lock.lock();
                if (!oneSolutionHasBeenFound()) {
                    colours_of_the_nodes = partial_colours_of_the_nodes;
                }
                lock.unlock();
            }
//            System.out.println("ok2");
            return;
        }

        List<Thread> threads = new ArrayList<>();
        List<String> valid_colours = new ArrayList<>();

        for (String colour: available_colours){
            partial_colours_of_the_nodes.set(node + 1, colour);
            if (colorIsValid(node + 1, partial_colours_of_the_nodes)){
                if (number_of_threads > 0 ){
                    number_of_threads -= 1;
                    Integer final_number_of_threads = number_of_threads;
                    Thread thread = new Thread(()-> findSolution(final_number_of_threads, node + 1, lock, partial_colours_of_the_nodes));
                    thread.start();
                    threads.add(thread);
                }
                else {
                    valid_colours.add(colour);
                }
            }
        }

        for (String colour: valid_colours){
            partial_colours_of_the_nodes.set(node + 1, colour);
            findSolution(number_of_threads, node + 1, lock, partial_colours_of_the_nodes);
        }

        for (Thread thread : threads){
            try {
                thread.join();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }


    }

    public boolean colorIsValid(int current_node, List<String> partial_colours_of_the_nodes){
//        System.out.println("THE COLOUR OF THE CURRENT NODE IS " + partial_colours_of_the_nodes.get(current_node));
        for (int node = 0; node < current_node; node++){
//            System.out.println("Node to be checked: "+node + ". COLOUR OF THE NODE: " + partial_colours_of_the_nodes.get(node));
            if (graph_to_colour.getNodesFromEdges(current_node).contains(node) && partial_colours_of_the_nodes.get(current_node).equals(partial_colours_of_the_nodes.get(node)))
                return false;
        }
        return true;
    }

    public boolean oneSolutionHasBeenFound(){
        for (var colour: colours_of_the_nodes){
            if (colour.equals(" ")) return false;
        }
        for (var node:graph_to_colour.getNodes()){
            if (!colorIsValid(node, colours_of_the_nodes)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String text =  "The colours of the nodes are:\n";
        for (Integer node: graph_to_colour.getNodes()){
            text += "For node " + node + ", the colour is " + colours_of_the_nodes.get(node) + ".\n";
        }
        return text;
    }

    public String visualCheck(){
        String text = "The edges of the graph represented by the colours of the nodes:\n";
        for (var node: graph_to_colour.getNodes()){
            text += "Node " + node + " has the colour " + colours_of_the_nodes.get(node).toUpperCase() + ".\n";
            text += "For node " + node + ", the edges are: ";
            for (var n: graph_to_colour.getNodesFromEdges(node)){
                text += "(" + colours_of_the_nodes.get(node) + ", " + colours_of_the_nodes.get(n) + ") ";
            }
            text += "\n\n";
        }
        return text;
    }
}