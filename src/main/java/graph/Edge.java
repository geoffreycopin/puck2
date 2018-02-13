package graph;

import java.lang.annotation.Target;
import java.util.List;
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
    
    public boolean equal (Edge o){
    	return  o.sourceId==this.sourceId && o.targetId==this.targetId && o.type==this.type;
    }
    
    public boolean containsEdge(List<Edge> l){
    	for ( Edge r: l){
    		if ( r.equal(this)) return true;
    	}
    	return false;
    }
}
