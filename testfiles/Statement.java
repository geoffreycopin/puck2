import java.util.List;

class Statement {
  static class X {
    void doThing() {
    }
  }

  void f(List<X> list) {
    for (X item : list) {
      item.doThing();
    }
  }
}
