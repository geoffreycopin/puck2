package graph.nodes;

import org.extendj.ast.Access;

public class Node {
    private Integer id;
    private String fullName;
    private Access extendjNode;

    public Node(Integer id, String fullName, Access extendjNode) {
        this.id = id;
        this.fullName = fullName;
        this.extendjNode = extendjNode;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Access getExtendjNode() {
        return extendjNode;
    }

    public String toString() {
        return String.format("<Node id=%d name=%s>", id, fullName);
    }
}
