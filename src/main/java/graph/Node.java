package graph;

import org.extendj.ast.Access;

public class Node {
    private Integer id;
    private String fullName;
    private Type type;
    private Access extendjNode;

    public enum Type { Package, Class, Interface, Attribute, Method, MethodBody }

    public Node(Integer id, String fullName, Type type, Access extendjNode) {
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

    public Access getExtendjNode() {
        return extendjNode;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        String formatStr = "<Node id=%d name=%s type=%s/>";
        return String.format(formatStr, id, fullName, type.toString().toLowerCase());
    }
}
