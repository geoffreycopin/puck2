package statement;

import test.SuperTest;

import java.util.List;
import java.util.Set;

class Statement<E> {
    Set<E> hey;
  class X {
    void doThing() {
    }
  }

  Double f(List<X> list) {
    for (X item : list) {
      item.doThing();
    }
  }
}
