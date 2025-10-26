// KruskalMST.java
import java.util.*;

public class KruskalMST {
    public static class Stats {
        public List<Edge> mst = new ArrayList<>();
        public long totalCost = 0;
        public long comparisons = 0;
        public long finds = 0;
        public long unions = 0;
        public double timeMs = 0;
    }

    public static Stats run(Graph g) {
        Stats stats = new Stats();
        int V = g.vertices;
        List<Edge> edges = new ArrayList<>(g.edges);
        long start = System.nanoTime();
        // sort edges; count comparisons roughly as (n log n) approx -> but we will increment when comparing in loop
        Collections.sort(edges);
        // We'll count comparisons when we iterate (each edge causes two finds and possible union)
        UnionFind uf = new UnionFind(V);
        for (Edge e : edges) {
            stats.comparisons++;
            int ru = uf.find(e.u);
            int rv = uf.find(e.v);
            stats.finds = uf.finds; // sync
            if (ru != rv) {
                boolean merged = uf.union(ru, rv);
                stats.unions = uf.unions;
                stats.mst.add(e);
                stats.totalCost += e.w;
                if (stats.mst.size() == V - 1) break;
            }
        }
        long end = System.nanoTime();
        stats.timeMs = (end - start) / 1_000_000.0;
        stats.finds = uf.finds;
        stats.unions = uf.unions;
        return stats;
    }
}