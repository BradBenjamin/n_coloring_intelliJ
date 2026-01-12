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
Name: ThreadRun        
Run on: LocalMachine            
Module: -cp n_colloring_intelliJ.main           
Main class: org.example.Main           
Arguments: -        
Environment Variables: -        

Run Configuration 2: (for normal thread running)
Name: MpiRun       
Run on: LocalMachine           
Module: -cp n_colloring_intelliJ.main         
Main class: runtime.starter.MPJRun           
Arguments: -np 4 -cp build/classes/java/main org.example.Main mpi          
Environment Variables: MPJ_HOME=C:\mpj\mpj-v0_44\mpj-v0_44         

This project provides two parallel implementations for solving the **N-Graph Coloring Problem**:
1. **MPI (Message Passing Interface):** Distributed memory approach using `MPJ Express`.
2. **Multi-Threading:** Shared memory approach using Java Threads.

## Approach 1: Shared Memory (Multi-Threading)
Target Environment: Single Multi-Core Machine

This implementation uses Java's Fork/Join Framework to split the workload across CPU cores sharing the same RAM.

**Architecture:** Recursive Task Parallelism    

 - ForkJoinPool: Manages a pool of worker threads (defaulting to the number of CPU cores).          
 - RecursiveTask: The problem is modeled as a task that returns an int[] (the solution).           
 - Forking: If the problem is large (many nodes left to color), the algorithm splits the work: it keeps one color choice for the current thread and forks new tasks for other color choices.      
 - Compute: If the problem is small (few nodes left), it solves it sequentially to avoid thread overhead.         

**Key Mechanisms**
 - Atomic Reference: Uses AtomicReference<int[]> finalResult as a global flag. All threads check this variable before doing work. If it is not null, they abort immediately, ensuring no CPU time is wasted after a solution is found.
 - Work Stealing: The ForkJoinPool automatically balances the load; idle threads "steal" tasks from busy threads, keeping all cores utilized.

 ## Approach 2: Distributed Memory (MPI)
Target Environment: Computer Clusters / Supercomputers

This implementation treats separate processes (often on different physical machines) as a team. They cannot share memory and must communicate by sending "messages" (data packets) over a network.

**Architecture:** Master-Worker Pattern
 - *Master (Rank 0):* Manages the recursion tree. When it reaches a branching point, it delegates a specific subtree to a waiting Worker process.
 - *Workers (Rank>0):* Sit in an infinite loop waiting for tasks. Upon receiving a task, they attempt to solve it and return the result.

**Key Mechanisms**
Dynamic Load Balancing: Tasks are not pre-assigned. The Master calculates a deterministic "destination rank" based on the recursion depth (power) and color index. This spreads work across the cluster without a central queue.

 **`RESULTS`**   

| Graph size    | Time | Nr_colors |
| -------- | ------- | ------------|
| 10       | 0.0047 s    |     5      |
|   20 | 0.012 s    |       5 |
|  50    | 0.317    |   9 |




