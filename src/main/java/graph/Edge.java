package graph;

import java.lang.annotation.Target;
import java.util.Optional;

public class Edge {
    private Integer sourceId;
    private Integer targetId;
    private Type type;

    public enum Type { IsA, Contains, Uses }

    public Edge(Integer source, Integer target, Type t) {
        this.sourceId = source;
        this.targetId = target;
        this.type = t;
    }

    public String toString() {
        return String.format("<Edge from=%d to=%d type=%s/>",
                sourceId, targetId, type.toString().toLowerCase());
    }
}
