// JSONReader.java
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class JSONReader {
    // Parser tailored to the provided structure:
    // {
    //  "graphs": [
    //    {
    //      "id": 1,
    //      "nodes": ["A","B",...],
    //      "edges":[ {"from":"A","to":"B","weight":4}, ... ]
    //    },
    //    ...
    //  ]
    // }
    public static List<Graph> readGraphs(String path) throws IOException {
        String s = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
        // normalize spacing
        s = s.replace("\r", " ").replace("\n", " ");
        List<Graph> graphs = new ArrayList<>();

        // find the graphs array content
        Pattern graphsPattern = Pattern.compile("\"graphs\"\\s*:\\s*\\[(.*)\\]\\s*\\}", Pattern.DOTALL);
        Matcher gm = graphsPattern.matcher(s);
        String graphsBlock;
        if (gm.find()) {
            graphsBlock = gm.group(1);
        } else {
            // maybe the file already just contains the array or different spacing; try crude fallback:
            graphsBlock = s;
        }

        // pattern to extract each graph object (naive but works for expected input)
        Pattern graphPattern = Pattern.compile("\\{\\s*\"id\"\\s*:\\s*(\\d+)\\s*,\\s*\"nodes\"\\s*:\\s*\\[(.*?)\\]\\s*,\\s*\"edges\"\\s*:\\s*\\[(.*?)\\]\\s*\\}");
        Matcher m = graphPattern.matcher(graphsBlock);
        while (m.find()) {
            int id = Integer.parseInt(m.group(1));
            String nodesBlock = m.group(2).trim();
            String edgesBlock = m.group(3).trim();

            // parse node names (strings)
            List<String> nodeNames = new ArrayList<>();
            Pattern nodePattern = Pattern.compile("\"([^\"]+)\"");
            Matcher nm = nodePattern.matcher(nodesBlock);
            while (nm.find()) {
                nodeNames.add(nm.group(1));
            }

            Graph g = new Graph(id, nodeNames);

            // parse edges: {"from":"A","to":"B","weight":4}
            Pattern edgePattern = Pattern.compile("\\{\\s*\"from\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"to\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"weight\"\\s*:\\s*(\\-?\\d+)\\s*\\}");
            Matcher em = edgePattern.matcher(edgesBlock);
            while (em.find()) {
                String from = em.group(1);
                String to = em.group(2);
                int w = Integer.parseInt(em.group(3));
                g.addEdgeByNames(from, to, w);
            }

            graphs.add(g);
        }

        return graphs;
    }
}