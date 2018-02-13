package test.aa;

class SuperTest extends Test { }

public class Test implements Foo,Boo{
	SuperTest r;
  void f() {
  }

  int m(int x, Foo r) {
    return x * x;
  }
  int m(SuperTest x,SuperTest y){
	  return (int) x;
  }
}


interface Foo {
void t();
}

interface Boo extends Foo{
void t();
}
