package refactoring.rename;

import app.Puck2Runner;
import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.Queries;
import org.extendj.ast.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RenameClass extends RenameBase {

	RenameClass(Integer id, String name, Graph graph) {
		super(id, name, graph);
	}

	@Override
	protected void refactorCode() {
		ClassDecl c = (ClassDecl) getGraph().getNode(getId()).getExtendjNode();
		c.setID(getNewName());

		updateSubClasses(c.createBoundAccess());
		updateFieldDeclarations(c.createBoundAccess());
		updateMethodParam(c.createBoundAccess());
		renameReferences(c.createBoundAccess());
		renameFileIfSameName(c.compilationUnit());
	}

	private void updateSubClasses(Access newAccess) {
		for (Node n: getGraph().queryNodesTo(getId(), Edge.Type.IsA)) {
			ClassDecl subClass = (ClassDecl) n.getExtendjNode();
			subClass.setSuperClass(newAccess);
		}
	}

	public ArrayList<ClassDecl> toRename() {
        return Queries.subClasses(getId(), getGraph()).stream()
                .map((n) -> (ClassDecl) getGraph().getNode(n).getExtendjNode() )
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void renameFileIfSameName(CompilationUnit cu) {
	    String lastComponent = Queries.lastComponent(getOldName());
	    if (cu.getClassSource().pathName().endsWith(lastComponent + ".java")) {
	        String dir = Paths.get(cu.getClassSource().pathName()).getParent().toString();
	        String newPAth = Paths.get(dir, getNewName() + ".java").toString();
	        cu.setClassSource(new FileClassSource(new SourceFilePath(newPAth), newPAth));
        }
    }
}
