package refactoring;

import app.Puck2Runner;
import graph.CodeGenerator;
import graph.Edge;
import graph.Graph;
import graph.Node;
import org.extendj.ast.*;

import java.io.IOException;
import java.io.ObjectInputStream;

public class RenameClass extends RefactoringBase {
	private String newName;
	private String oldName;

	public static void main(String args[]) {
		Puck2Runner runner = new Puck2Runner("testfiles/Test.java");
		try {
			runner.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RenameClass rn = new RenameClass("test.Test", "ParentClass", runner.getGraph());
		rn.refactor();
		RenameClass rn1 = new RenameClass("test.SuperTest", "Class", runner.getGraph());
		rn1.refactor();
		System.out.println(rn1.getGraph().getProgram().prettyPrint());
		try {
			new CodeGenerator(rn1.getGraph().getProgram()).generateCode("refactoring_output");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RenameClass(String oldName, String newName, Graph graph) {
		super(graph);
		this.oldName = oldName;
		this.newName = newName;
	}

	@Override
	public void refactor() {
		ClassDecl c = (ClassDecl) getGraph().getNode(oldName).getExtendjNode();
		c.setID(newName);

		getGraph().renameNode(oldName, newName);

		updateSubClasses(c.createBoundAccess());
		updateFieldDeclarations(c.createBoundAccess());
		updateMethodParam(c.createBoundAccess());
	}

	public void updateSubClasses(Access newAccess) {
		for (Node n: getGraph().queryTo(oldName, Edge.Type.IsA)) {
			ClassDecl subClass = (ClassDecl) n.getExtendjNode();
			subClass.setSuperClass(newAccess);
		}
	}

	public void updateFieldDeclarations(Access newAccess) {
		for (Node n: getGraph().queryTo(oldName, Edge.Type.Uses)) {
			if (n.getExtendjNode() instanceof FieldDeclarator) {
				FieldDeclarator f = (FieldDeclarator) n.getExtendjNode();
				f.fieldDecl().setTypeAccess(newAccess);
			}
		}
	}

	public void updateMethodParam(Access newAccess) {

		for (Node n: getGraph().queryTo(oldName, Edge.Type.Uses)) {
			if (n.getExtendjNode() instanceof MethodDecl) {

				MethodDecl f = (MethodDecl) n.getExtendjNode();
				for ( ParameterDeclaration p : f.getParameterList()) {
					if(p.getTypeAccess().type().fullName().equals(oldName)) {
						p.setTypeAccess(newAccess);
					}
				}
			}
		}
	}
//	public void updateMethodBody(Access newAccess) {
//
//		for (Node n: getGraph().queryTo(oldName, Edge.Type.Uses)) {
//			if (n.getExtendjNode() instanceof MethodDecl) {
//
//				MethodDecl f = (MethodDecl) n.getExtendjNode();
//				for ( ParameterDeclaration p : f.getParameterList()) {
//					if(p.getTypeAccess().type().fullName().equals(oldName)) {
//						p.setTypeAccess(newAccess);
//					}
//				}
//			}
//		}
//	}
}
