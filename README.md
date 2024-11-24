# Graph Matrix Implementation

Java implementation of graph data structures and algorithms using adjacency matrices. This project provides a set of tools for graph manipulation and analysis, including multiple pathfinding algorithms and cycle detection.

## Features

### Graph Operations
- Create weighted directed graphs with a specified number of vertices
- Add weighted edges between vertices
- Visualize the graph structure through adjacency matrix representation

### Algorithms
- **Shortest Path Finding**:
  - Bellman-Ford algorithm for detecting negative cycles and finding shortest paths
  - Dijkstra's algorithm for efficient shortest path calculation in non-negative weighted graphs
- **Graph Analysis**:
  - Cycle detection using Depth-First Search (DFS)
  - Tree height calculation from any starting node
  - Pre-order graph traversal

## Implementation Details

The project consists of two main classes:
- `Grafo`: Contains all graph operations and algorithms
- `Main`: Provides example usage and testing scenarios

### Data Structure
- Uses an adjacency matrix representation (`int[][]`)
- Infinity is represented by `Integer.MAX_VALUE`
- Vertices are zero-indexed internally but displayed one-indexed in output

## Usage Example

```java
// Create a graph with 13 vertices
Grafo grafo = new Grafo(13);

// Add weighted edges
grafo.agregarArista(0, 1, 200);  // Add edge from vertex 1 to 2 with weight 200
grafo.agregarArista(0, 12, 250); // Add edge from vertex 1 to 13 with weight 250

// Find shortest path using Dijkstra's algorithm
grafo.caminoMasCortoDijkstra(0, 2); // Find shortest path from vertex 1 to 3

// Detect cycles
List<List<Integer>> ciclos = grafo.encontrarCiclos();
```

## Output

The program provides detailed output for all operations:
- Adjacency matrix visualization
- Shortest path routes and distances
- Cycle detection results
- Tree height calculations
- Pre-order traversal sequences

## Requirements

- Java Development Kit (JDK) 8 or higher

## Building and Running

1. Compile the project:
```bash
javac Main.java
```

2. Run the program:
```bash
java Main
```

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

- Implemented as part of a university project for graph theory and algorithms
- Based on classical graph theory algorithms and data structures
