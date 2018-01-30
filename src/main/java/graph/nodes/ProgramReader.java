package graph.nodes;

import graph.UniqueIdGenerator;
import org.extendj.ast.*;

import java.util.ArrayList;
import java.util.List;

public class ProgramReader {
    private UniqueIdGenerator idGenerator;

    public ProgramReader() {
        this(new UniqueIdGenerator());
    }

    public ProgramReader(UniqueIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public List<Node> read(Program p) {
        List<Node> nodes = new ArrayList<>();

        for (CompilationUnit cu: p.getCompilationUnitList()) {
            nodes.addAll(readClassDeclaractions(cu));
        }

        return nodes;
    }

    private List<Node> readClassDeclaractions(CompilationUnit unit) {
        List<Node> classDeclarations = new ArrayList<>();

        for (TypeDecl t: unit.getTypeDeclList()) {
            // TODO: remplacer par une classe ClassReader qui effectuerait un traitement un peu plus complet...
            if (t instanceof ClassDecl) {
                ClassDecl c = (ClassDecl) t;
                Integer id = idGenerator.generate();
                String name = c.fullName();
                Access extendjNode = c.createQualifiedAccess();
                classDeclarations.add(new Node(id, name, extendjNode));
            }
        }

        return classDeclarations;
    }
}
