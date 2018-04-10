package test;

class Class extends ParentClass {
  int r;

  void superMethod(ParentClass t, Foo f) {
  }
}

public class ParentClass implements Foo {
  int r;

  Class f;

  void f() {
  }

  int m(int x) {
    return x * x;
  }

  int m(double x) {
    return (int) x;
  }

  int m(Foo f) {
  }
}

interface Foo {
  void t();

  void t(ParentClass t);
}