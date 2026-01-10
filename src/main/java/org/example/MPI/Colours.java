package org.example.MPI;

public class Colours {
    private final String[] colors;

    public Colours(int n) {
        this.colors = new String[n];
    }

    public void addColor(int code, String color) {
        if (code >= 0 && code < colors.length) {
            colors[code] = color;
        }
    }

    public int getNumberOfColours() {
        return colors.length;
    }

    public String visualCheck(int[] codes, Graph graph) {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph Coloring Solution:\n");

        for (int i = 0; i < codes.length; i++) {
            String colorName = colors[codes[i]].toUpperCase();
            sb.append("Node ").append(i).append(" [").append(colorName).append("] connects to: ");

            for (int neighbor : graph.getNeighbors(i)) {
                String neighborColor = colors[codes[neighbor]].toUpperCase();
                sb.append("(").append(neighbor).append("-").append(neighborColor).append(") ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}