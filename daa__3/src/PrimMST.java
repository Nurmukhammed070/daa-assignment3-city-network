// PrimMST.java
import java.util.*;

public class PrimMST {
    public static class Stats {
        public List<Edge> mst = new ArrayList<>();
        public long totalCost = 0;
        public long comparisons = 0;
        public long keyUpdates = 0;
        public long queueOps = 0;
        public double timeMs = 0;
    }

    public static Stats run(Graph g) {
        Stats stats = new Stats();
        int V = g.vertices;
        if (V == 0) return stats;
        List<List<Edge>> adj = g.adjacencyList();
        boolean[] inMST = new boolean[V];
        int[] key = new int[V];
        int[] parent = new int[V];
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        key[0] = 0;

        long start = System.nanoTime();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0])); // {key, vertex}
        pq.offer(new int[]{0, 0}); stats.queueOps++;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll(); stats.queueOps++;
            int k = cur[0], u = cur[1];
            if (inMST[u]) continue;
            inMST[u] = true;
            if (parent[u] != -1) {
                stats.mst.add(new Edge(parent[u], u, k));
                stats.totalCost += k;
            }
            for (Edge e : adj.get(u)) {
                int v = e.v;
                stats.comparisons++; // compare key[v] with e.w
                if (!inMST[v] && e.w < key[v]) {
                    key[v] = e.w;
                    parent[v] = u;
                    pq.offer(new int[]{key[v], v}); stats.queueOps++;
                    stats.keyUpdates++;
                }
            }
        }
        long end = System.nanoTime();
        stats.timeMs = (end - start) / 1_000_000.0;

        // check connectivity: mst size should be V-1 for connected graph
        if (stats.mst.size() != V - 1) {
            // disconnected
        }
        return stats;
    }
}