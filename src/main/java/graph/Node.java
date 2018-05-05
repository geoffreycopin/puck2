package graph;

import org.extendj.ast.ASTNode;
import org.extendj.ast.Access;

public class Node {
    private Integer id;
    private String fullName;
    private Type type;
    private ASTNode<ASTNode> extendjNode;

    public enum Type {Package, Class, Interface, Attribute, Method, MethodBody}

    public Node(Integer id, String fullName, Type type, ASTNode<ASTNode> extendjNode) {
        this.id = id;
        this.fullName = fullName;
        this.extendjNode = extendjNode;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public ASTNode<ASTNode> getExtendjNode() {
        return extendjNode;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        String formatStr = "<Node name=\"%s\" type=\"%s\"/>";
        return String.format(formatStr, fullName, type.toString().toLowerCase(), id);
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Node)) {
            return false;
        }
        Node other = (Node) o;
        return id.equals(other.id) && fullName.equals(other.fullName) && type == other.type;
    }
}
