// TestRunner.java
import java.util.*;
public class TestRunner {
    public static void main(String[] args) throws Exception {
        List<Graph> graphs = JSONReader.readGraphs("input.json");
        int pass = 0, fail = 0;
        for (Graph g : graphs) {
            System.out.println("Testing: " + g.name);
            PrimMST.Stats p = PrimMST.run(g);
            KruskalMST.Stats k = KruskalMST.run(g);

            boolean ok = true;
            if (p.totalCost != k.totalCost) {
                System.out.println("FAIL: totalCost mismatch ("+p.totalCost+" vs "+k.totalCost+")");
                ok = false;
            }
            if (p.mst.size() != g.vertices -1) {
                System.out.println("FAIL: Prim MST edges count wrong: " + p.mst.size());
                ok = false;
            }
            if (k.mst.size() != g.vertices -1) {
                System.out.println("FAIL: Kruskal MST edges count wrong: " + k.mst.size());
                ok = false;
            }
            if (p.timeMs < 0 || k.timeMs < 0) {
                System.out.println("FAIL: negative time");
                ok = false;
            }
            if (ok) { pass++; System.out.println("PASS"); }
            else { fail++; }
        }
        System.out.println("Tests done. Pass: " + pass + " Fail: " + fail);
    }
}