package graph;

import java.lang.annotation.Target;
import java.util.Optional;

public class Edge {
    private String sourceName;
    private String targetName;
    private Type type;

    public enum Type { IsA, Contains, Uses }

    public Edge(String source, String target, Type t) {
        this.sourceName = source;
        this.targetName = target;
        this.type = t;
    }

    public String toString() {
        return String.format("<Edge from=%s to=%s type=%s/>",
                sourceName, targetName, type.toString().toLowerCase());
    }

    public Type getType() {
        return type;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getTargetName() {
        return targetName;
    }
}
