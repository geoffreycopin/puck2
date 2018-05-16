package graph;

import app.Puck2Runner;
import org.junit.AfterClass;
import org.junit.Test;
import refactoring.rename.Rename;
import refactoring.rename.RenameBase;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRename {
    private static final String ressourcesDir = "src/test/resources";

    @Test
    public void renameClass() throws Exception {
        String projectPath = "testfiles/distrib/bridge/hannemann/BridgeDemo.java";
        rename("bridge.candidate.Screen", projectPath);
    }

    private void rename(String name, String projectPath) throws Exception {
        Puck2Runner runner = new Puck2Runner(projectPath);
        runner.run();
        Integer id = runner.getGraph().getNode(name).getId();
        RenameBase r = Rename.newRenameStartegy(name, "RENAMED", runner.getGraph());
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
