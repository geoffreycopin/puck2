package graph;

import app.Puck2Runner;
import org.extendj.ast.*;
import org.junit.AfterClass;
import org.junit.Test;
import refactoring.rename.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRename {
    private static final String ressourcesDir = "src/test/resources";

    @Test
    public void isValidIdentifierOk() {
        assertTrue(Rename.isValidJavaIdentifier("String"));
        assertTrue(Rename.isValidJavaIdentifier("i3"));
        assertTrue(Rename.isValidJavaIdentifier("αρετη"));
        assertTrue(Rename.isValidJavaIdentifier("MAX_VALUE"));
        assertTrue(Rename.isValidJavaIdentifier("isLetterOrDigit"));
    }

    @Test
    public void invalidJavaIdentifier() {
        assertFalse(Rename.isValidJavaIdentifier(""));
        assertFalse(Rename.isValidJavaIdentifier("test@"));
        assertFalse(Rename.isValidJavaIdentifier("3i"));
    }

    @Test
    public void renameClass() throws Exception {
        String projectPath = "testfiles/distrib/bridge/hannemann/BridgeDemo.java";
        rename("bridge.candidate.Screen", projectPath);
    }

    private HashSet<String> nodesToIDSet(List<ASTNode<ASTNode>> nodes) {
       return nodes.stream()
               .map(this::nodeId)
               .collect(Collectors.toCollection(HashSet::new));
    }

    private String nodeId(ASTNode<ASTNode> node) {
        if (node instanceof VarAccess) {
            VarAccess v = (VarAccess) node;
            return nodeId(v.getParent());
        } else if (node instanceof MethodAccess) {
            MethodAccess m = (MethodAccess) node;
            return m.toString();
        } else if (node instanceof Dot) {
            Dot d = (Dot) node;
            return d.toString();
        } else if (node instanceof ClassDecl) {
            return ((ClassDecl) node).fullName();
        }
        return null;
    }

    private void rename(String name, String projectPath) throws Exception {
        Puck2Runner runner = new Puck2Runner(projectPath);
        runner.run();
        Integer id = runner.getGraph().getNode(name).getId();
        RenameBase r = Rename.newRenameStartegy(name, "RENAMED", runner.getGraph()).get(0);
        r.refactor();
        Set<Node> depsPre = new HashSet<>(runner.getGraph().queryNodesTo(id));
        CodeGenerator codeGen = new CodeGenerator(runner.getProgram(), projectPath);
        codeGen.generateCode(ressourcesDir);

        Puck2Runner runnerPost = new Puck2Runner(ressourcesDir);
        runnerPost.run();
        Set<Node> depsPost = new HashSet<>(runnerPost.getGraph().queryNodesTo(id));

        Set<Node> depsPreCopy = new HashSet<>(depsPre);
        depsPre.removeAll(depsPost);
        depsPost.removeAll(depsPreCopy);

        assertEquals(depsPost, new HashSet<>());
        assertEquals(depsPre, new HashSet<>());

        assertTrue(runnerPost.getGraph().getNode(id).getFullName().endsWith("RENAMED"));
    }

    @AfterClass
    public static void cleanup() {
        File root = new File(ressourcesDir);
        if (root.listFiles() == null) {
            return;
        }
        for (File f: root.listFiles()) {
            removeRec(f);
        }
    }

    private static void removeRec(File f) {
        File[] childs = f.listFiles();
        if (childs != null) {
            for (File child: childs) {
                removeRec(child);
            }
        }
        f.delete();
    }
}
