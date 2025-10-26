// Edge.java
public class Edge implements Comparable<Edge> {
    public int u, v;
    public int w;
    public Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.w, other.w);
    }
    public String toString() {
        return String.format("{u:%d,v:%d,w:%d}", u, v, w);
    }
}