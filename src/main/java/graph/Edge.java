package graph;

import java.lang.annotation.Target;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
		return String.format("<Edge from=\"%s\" to=\"%s\" type=\"%s\"/>",
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



	@Override
	public boolean equals (Object e) {
		if (e ==null )return false;
		if (e ==this) return true;

		if  ( ! (e instanceof Edge) ) return false;
		Edge r= (Edge) e;

		return (r.sourceName).equals(this.sourceName) && r.targetName==this.targetName && r.type==this.type;	    	
	}

	@Override
	public int hashCode() {
		return Objects.hash(sourceName,targetName,type);
	}
}
