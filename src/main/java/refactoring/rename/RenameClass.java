package refactoring.rename;

import app.Puck2Runner;
import graph.Edge;
import graph.Graph;
import graph.Node;
import org.extendj.ast.*;

import java.io.IOException;

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
	}

	private void updateSubClasses(Access newAccess) {
		for (Node n: getGraph().queryNodesTo(getId(), Edge.Type.IsA)) {
			ClassDecl subClass = (ClassDecl) n.getExtendjNode();
			subClass.setSuperClass(newAccess);
		}
	}
}
