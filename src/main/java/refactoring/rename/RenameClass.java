package refactoring.rename;

import app.Puck2Runner;
import graph.Edge;
import graph.Graph;
import graph.Node;
import org.extendj.ast.*;

import java.io.IOException;

public class RenameClass extends RenameBase {

	public static void main(String args[]) {
		Puck2Runner runner = new Puck2Runner("testfiles/Test.java");
		try {
			runner.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		runner.displayGraph();
	}

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

	private void updateFieldDeclarations(Access newAccess) {
		for (Node n: getGraph().queryNodesTo(getId(), Edge.Type.Uses)) {
			if (n.getExtendjNode() instanceof FieldDeclarator) {
				FieldDeclarator f = (FieldDeclarator) n.getExtendjNode();
				f.fieldDecl().setTypeAccess(newAccess);
			}
		}
	}

	private void updateMethodParam(Access newAccess) {
		for (Node n: getGraph().queryNodesTo(getId(), Edge.Type.Uses)) {
			if (n.getExtendjNode() instanceof ParameterDeclaration) {
				ParameterDeclaration p = (ParameterDeclaration) n.getExtendjNode();
				if(p.getTypeAccess().type().fullName().equals(getOldName())) {
				    p.setTypeAccess(newAccess);
				}
			}
		}
	}
}
