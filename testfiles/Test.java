package test;

class SuperTest extends Test { }

public class Test implements Foo{
	int r;
  void f() {
  }

  int m(int x) {
    return x * x;
  }
  int m(double x){
	  return (int) x;
  }
}


interface Foo {
void t();
}
