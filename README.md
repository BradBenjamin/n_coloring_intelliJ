# N-Coloring Graph Problem (Java)

Tutorial to Running in IntelliJ:
1. Go to [https://sourceforge.net/projects/mpjexpress/], download MPJ Express and unzip it in C:/mpj folder
2. Open the git repository in IntelliJ
3. Go to File > Project Structure > Modules > Dependencies > '+' > JARs or Directories > C:/mpj/mpj-v0_44/mpj-v0_44. From this folder, add mpj.jar and starter.jar to dependencies.
4. Open build.gradle and make sure these lines are present:
`dependencies {
    //other deoendencies
    implementation files('C:/mpj/mpj-v0_44/mpj-v0_44/lib/mpj.jar')
    implementation files('C:/mpj/mpj-v0_44/mpj-v0_44/lib/starter.jar')
}`
6. Add the following Run Configurations:

Run Configuration 1: (for MPI running)
Name: MpiRun
Run on: LocalMachine
Module: -cp n_colloring_intelliJ.main
Main class: org.example.Main
Arguments: -
Environment Variables: -

Run Configuration 2: (for normal thread running)
Name: ThreadRun
Run on: LocalMachine
Module: -cp n_colloring_intelliJ.main
Main class: runtime.starter.MPJRun
Arguments: -np 4 -cp build/classes/java/main org.example.Main mpi
Environment Variables: MPJ_HOME=C:\mpj\mpj-v0_44\mpj-v0_44

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
