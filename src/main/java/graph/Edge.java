package graph;

import java.lang.annotation.Target;
import java.util.*;

public class Edge {
	private int sourceId;
	private int targetId;
	private Type type;

	public enum Type { IsA, Contains, Uses }

	public Edge(int source, int target, Type t) {
		this.sourceId = source;
		this.targetId = target;
		this.type = t;
	}

	public String toString() {
		return String.format("<Edge from=\"%d\" to=\"%d\" type=\"%s\"/>",
				sourceId, targetId, type.toString().toLowerCase());
	}

	public String getStringRepr(HashMap<Integer, Node> nodes) {
	    Node source = nodes.get(getSource());
	    Node target = nodes.get(getTarget());
	    if (source == null || target == null) {
	        return null;
        }
        return String.format("<Edge from=\"%s\" to=\"%s\" type=\"%s\"/>",
                source.getFullName(), target.getFullName(), getType().toString().toLowerCase());
    }

	public Type getType() {
		return type;
	}

	public int getSource() {
		return sourceId;
	}

	public String getSourceName(Map<Integer, Node> nodes) {
	    Node n = nodes.get(sourceId);
	    if (n == null) {
	        return null;
        }
        return n.getFullName();
    }

	public int getTarget() {
		return targetId;
	}

	public String getTargetName(Map<Integer, Node> nodes) {
	    Node n = nodes.get(targetId);
	    if (n == null) {
	        return null;
        }
        return n.getFullName();
    }

	@Override
	public boolean equals (Object e) {
		if (! (e instanceof Edge)) {
			return false;
		}
		Edge other = (Edge) e;
		return sourceId == other.sourceId && targetId == other.targetId && type == other.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sourceId, targetId);
	}
}
