// Main.java
import java.util.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputFile = "input.json";
        if (args.length > 0) inputFile = args[0];

        List<Graph> graphs = JSONReader.readGraphs(inputFile);
        List<String> outputs = new ArrayList<>();
        outputs.add("{");
        outputs.add("  \"results\": [");

        for (int gi = 0; gi < graphs.size(); gi++) {
            Graph g = graphs.get(gi);
            System.out.println("Processing graph id=" + g.id + " V=" + g.vertices + " E=" + g.edges.size());

            PrimMST.Stats p = PrimMST.run(g);
            KruskalMST.Stats k = KruskalMST.run(g);

            long primOps = p.comparisons + p.keyUpdates + p.queueOps;
            long kruskalOps = k.comparisons + k.finds + k.unions;

            boolean primConnected = (p.mst.size() == g.vertices - 1);
            boolean kruskalConnected = (k.mst.size() == g.vertices - 1);

            String primEdgesJson = buildEdgesJsonNamed(p.mst, g);
            String kruskalEdgesJson = buildEdgesJsonNamed(k.mst, g);

            outputs.add("    {");
            outputs.add("      \"graph_id\": " + g.id + ",");
            outputs.add("      \"input_stats\": {");
            outputs.add("        \"vertices\": " + g.vertices + ",");
            outputs.add("        \"edges\": " + g.edges.size());
            outputs.add("      },");
            // prim block
            outputs.add("      \"prim\": {");
            outputs.add("        \"mst_edges\": [");
            outputs.addAll(indentJsonLines(primEdgesJson, 10));
            outputs.add("        ],");
            outputs.add("        \"total_cost\": " + p.totalCost + ",");
            outputs.add("        \"operations_count\": " + primOps + ",");
            outputs.add(String.format("        \"execution_time_ms\": %.4f,", p.timeMs));
            outputs.add("        \"connected\": " + primConnected);
            outputs.add("      },");
            // kruskal block
            outputs.add("      \"kruskal\": {");
            outputs.add("        \"mst_edges\": [");
            outputs.addAll(indentJsonLines(kruskalEdgesJson, 10));
            outputs.add("        ],");
            outputs.add("        \"total_cost\": " + k.totalCost + ",");
            outputs.add("        \"operations_count\": " + kruskalOps + ",");
            outputs.add(String.format("        \"execution_time_ms\": %.4f,", k.timeMs));
            outputs.add("        \"connected\": " + kruskalConnected);
            outputs.add("      }");
            outputs.add("    }" + (gi < graphs.size()-1 ? "," : ""));
        }

        outputs.add("  ]");
        outputs.add("}");

        Files.write(Paths.get("output.json"), String.join("\n", outputs).getBytes("UTF-8"));
        System.out.println("✅ Wrote output.json successfully.");
    }

    // helper: edges array with vertex names
    static String buildEdgesJsonNamed(List<Edge> edges, Graph g) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            String fromName = g.nameOf(e.u);
            String toName = g.nameOf(e.v);
            sb.append("{\"from\": \"" + escapeJson(fromName) + "\", \"to\": \"" + escapeJson(toName) + "\", \"weight\": " + e.w + "}");
            if (i < edges.size() - 1) sb.append(",\n");
        }
        return sb.toString();
    }

    static List<String> indentJsonLines(String block, int spaces) {
        List<String> lines = new ArrayList<>();
        String[] arr = block.split("\\r?\\n");
        String pad = String.join("", Collections.nCopies(spaces, " "));
        for (String line : arr) lines.add(pad + line);
        return lines;
    }

    static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}