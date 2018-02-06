package test;

class SuperTest extends Test { }

public class Test implements Foo{
	int r;
  void f() {
  }

  int m(int x) {
    return x * x;
  }
}

class X {
	Test pp;
}

interface Foo {

}

interface Too extends Foo {

} 
interface Boo extends Too {

}