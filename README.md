Assignment 3

Student: Abdikarim Nurmukhammed, SE-2430
Objective
The goal of this assignment is to apply **Prim’s** and **Kruskal’s** algorithms to optimize a city’s transportation network.  
Both algorithms are used to find the **Minimum Spanning Tree (MST)** — the minimum set of roads that connect all city districts with the lowest possible total construction cost.

Project Structure

daa_assignment3/
│
├── src/
│   ├── Edge.java
│   ├── Graph.java
│   ├── PrimAlgorithm.java
│   ├── KruskalAlgorithm.java
│   ├── JSONReader.java
│   └── TestRunner.java
│
├── input.json
├── output.json


Input Data (input.json)

```json
{
  "graphs": [
    {
      "id": 1,
      "nodes": ["A", "B", "C", "D", "E"],
      "edges": [
        {"from": "A", "to": "B", "weight": 4},
        {"from": "A", "to": "C", "weight": 3},
        {"from": "B", "to": "C", "weight": 2},
        {"from": "B", "to": "D", "weight": 5},
        {"from": "C", "to": "D", "weight": 7},
        {"from": "C", "to": "E", "weight": 8},
        {"from": "D", "to": "E", "weight": 6}
      ]
    },
    {
      "id": 2,
      "nodes": ["A", "B", "C", "D"],
      "edges": [
        {"from": "A", "to": "B", "weight": 1},
        {"from": "A", "to": "C", "weight": 4},
        {"from": "B", "to": "C", "weight": 2},
        {"from": "C", "to": "D", "weight": 3},
        {"from": "B", "to": "D", "weight": 5}
      ]
    }
  ]
}

Output Results (output.json)

{
  "results": [
    {
      "graph_id": 1,
      "input_stats": {"vertices": 5, "edges": 7},
      "prim": {
        "mst_edges": [
          {"from": "A", "to": "C", "weight": 3},
          {"from": "C", "to": "B", "weight": 2},
          {"from": "B", "to": "D", "weight": 5},
          {"from": "D", "to": "E", "weight": 6}
        ],
        "total_cost": 16,
        "operations_count": 37,
        "execution_time_ms": 0.8815,
        "connected": true
      },
      "kruskal": {
        "mst_edges": [
          {"from": "B", "to": "C", "weight": 2},
          {"from": "A", "to": "C", "weight": 3},
          {"from": "B", "to": "D", "weight": 5},
          {"from": "D", "to": "E", "weight": 6}
        ],
        "total_cost": 16,
        "operations_count": 30,
        "execution_time_ms": 0.2245,
        "connected": true
      }
    },
    {
      "graph_id": 2,
      "input_stats": {"vertices": 4, "edges": 5},
      "prim": {
        "mst_edges": [
          {"from": "A", "to": "B", "weight": 1},
          {"from": "B", "to": "C", "weight": 2},
          {"from": "C", "to": "D", "weight": 3}
        ],
        "total_cost": 6,
        "operations_count": 27,
        "execution_time_ms": 0.0130,
        "connected": true
      },
      "kruskal": {
        "mst_edges": [
          {"from": "A", "to": "B", "weight": 1},
          {"from": "B", "to": "C", "weight": 2},
          {"from": "C", "to": "D", "weight": 3}
        ],
        "total_cost": 6,
        "operations_count": 20,
        "execution_time_ms": 0.0073,
        "connected": true
      }
    }
  ]
}

 Algorithm Explanation

 Prim’s Algorithm
	1.	Starts from any node.
	2.	At each step, selects the smallest edge connecting the tree to a new vertex.
	3.	Repeats until all vertices are included.

	•	Time Complexity: O(V²) or O(E log V) with priority queue.
	•	Best for: Dense graphs (many connections).

 Kruskal’s Algorithm
	1.	Sorts all edges by increasing weight.
	2.	Adds edges to the MST if they do not create a cycle (checked with Union-Find).
	3.	Stops when all vertices are connected.

	•	Time Complexity: O(E log E).
	•	Best for: Sparse graphs (few connections).

Comparison Table

Algorithm	Total Cost (Graph 1)	Total Cost (Graph 2)	Operations	Execution Time (ms)	Efficiency
Prim’s	16	6	37 / 27	0.8815 / 0.0130	Fast on dense graphs
Kruskal’s	16	6	30 / 20	0.2245 / 0.0073	Fast on sparse graphs

Both algorithms produced identical MST results, confirming correctness.

How to Run the Code

In terminal:

cd src
javac *.java
java TestRunner

Conclusion
	•	Both Prim’s and Kruskal’s algorithms found MSTs with the same total cost.
	•	Prim’s algorithm performs slightly slower but is good for dense graphs.
	•	Kruskal’s algorithm is faster for sparse graphs.
	•	The program successfully processes multiple graphs from a single JSON file and writes all results into output.json.
