package dataflow.examples.basics;


import dataflow.core.IntEvent;
import dataflow.generators.GenConst;

public class Printer {
	Printer(){
	}
	
	void add(IntEvent e) {};
}
public class Main {

	public static void main(String[] args) {
		
		Printer p = new Printer();
		p.add(new IntEvent(3));
		System.out.println(new IntEvent(3));
		
	}

}
