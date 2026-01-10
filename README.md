# N-Coloring Graph Problem (Java)

This project provides two parallel implementations for solving the **N-Graph Coloring Problem**:
1. **MPI (Message Passing Interface):** Distributed memory approach using `MPJ Express`.
2. **Multi-Threading:** Shared memory approach using Java Threads.

The goal is to color a graph with `N` nodes using `M` colors such that no two adjacent nodes share the same color.

## 📂 Project Structure

```text
src/main/java/org/example
├── MPI/                 # Distributed Implementation
│   ├── Main.java        # Entry point for MPI
│   ├── Graph.java       # Graph data structure
│   ├── Colours.java     # Color management
│   └── GraphColouring.java # Recursive MPI logic
│
└── Thread/              # Multi-threaded Implementation
    ├── Main.java        # Entry point for Threads
    ├── Graph.java       # Graph data structure
    └── GraphColouring.java # Recursive Thread logic
