// Result.java
import java.util.*;
public class Result {
    public String name;
    public int vertices;
    public int edges;
    public PrimMST.Stats prim;
    public KruskalMST.Stats kruskal;
    public Result(String name, int v, int e) {
        this.name = name; this.vertices = v; this.edges = e;
    }
}