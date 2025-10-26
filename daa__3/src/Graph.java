// Graph.java
import java.util.*;
public class Graph {
    public String name; // optional, not used for id
    public int id; // graph id from input (if any)
    public int vertices;
    public List<Edge> edges;
    public List<String> nodeNames; // index -> name
    public Map<String,Integer> nameToIndex; // name -> index

    public Graph(int id, List<String> nodeNames) {
        this.id = id;
        this.nodeNames = new ArrayList<>(nodeNames);
        this.vertices = nodeNames.size();
        this.edges = new ArrayList<>();
        this.nameToIndex = new HashMap<>();
        for (int i = 0; i < nodeNames.size(); i++) {
            nameToIndex.put(nodeNames.get(i), i);
        }
    }

    public void addEdgeByNames(String from, String to, int w) {
        Integer u = nameToIndex.get(from);
        Integer v = nameToIndex.get(to);
        if (u == null || v == null) {
            throw new IllegalArgumentException("Unknown node name: " + from + " or " + to);
        }
        edges.add(new Edge(u, v, w));
    }

    public void addEdge(int u, int v, int w) {
        edges.add(new Edge(u, v, w));
    }

    public List<List<Edge>> adjacencyList() {
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < vertices; i++) adj.add(new ArrayList<>());
        for (Edge e : edges) {
            adj.get(e.u).add(new Edge(e.u, e.v, e.w));
            adj.get(e.v).add(new Edge(e.v, e.u, e.w));
        }
        return adj;
    }

    public String nameOf(int idx) {
        return nodeNames.get(idx);
    }
}