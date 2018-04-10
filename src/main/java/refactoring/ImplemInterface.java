package refactoring;

import java.io.IOException;

import org.extendj.ast.Access;
import org.extendj.ast.BodyDecl;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.FieldDeclarator;
import org.extendj.ast.InterfaceDecl;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.ParameterDeclaration;

import app.Puck2Runner;
import graph.CodeGenerator;
import graph.Edge;
import graph.Graph;
import graph.Node;

public class ImplemInterface extends RefactoringBase {
	public ImplemInterface(Graph graph, String implem,String classname) {
		super(graph);
		// TODO Auto-generated constructor stub
		this.implem=implem;
		this.classname=classname;
	}

	private String implem;
	private String classname;


	public static void main(String args[]) {
		Puck2Runner runner = new Puck2Runner("testfiles/Test.java");
		try {
			runner.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImplemInterface rn = new ImplemInterface(runner.getGraph(),"test.Foo", "test.SuperTest");
		rn.refactor();
		
		//System.out.println(rn.getGraph().getProgram().prettyPrint());
		try {
			new CodeGenerator(rn.getGraph().getProgram()).generateCode("refactoring_output");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void refactor() {
		ClassDecl c = (ClassDecl) getGraph().getNode(classname).getExtendjNode();
		InterfaceDecl i = (InterfaceDecl) getGraph().getNode(implem).getExtendjNode();
		c.setImplements(i.createBoundAccess(), c.getNumImplements());
		for ( BodyDecl d : i.getBodyDeclList()) {
			c.addBodyDecl(d);
		}
		
	
		
	}



}
