package p;
import java.util.List;

class A { void add (C c , C a) {};} 
class C {}
class B {
	A r;
	public void mb(){ 
		r.add(new C(), new C());
	}

}
