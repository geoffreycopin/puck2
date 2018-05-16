package decorator.candidate;

public class A {
	public void doIt() { System.out.print( 'A' ); }
}
class AwithX extends A {
	public  void doIt() { super.doIt();  doX(); }
	public void doX()  { System.out.print( 'X' ); }
}

class AwithY extends A {
	public void doIt() { super.doIt();  doY(); }
	public void doY()  { System.out.print( 'Y' ); }
}

class AwithXY extends AwithX {
	private AwithY obj = new AwithY();
	public void doIt() {
		super.doIt();
		obj.doY();
	}  
}

class AwithYX extends AwithY {
	private AwithX obj = new AwithX();
	public void doIt() {
		super.doIt();
		obj.doX();
	}  
}