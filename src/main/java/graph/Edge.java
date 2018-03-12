package graph;

import java.io.Serializable;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Optional;

public class Edge implements Serializable {
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

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Boolean Contains(List<Edge> e) {
    	for(Edge r : e) {
    		if(r.sourceName==this.sourceName && r.targetName==this.targetName && r.type==this.type) return true;

    	}
    	return false;
    }
}
