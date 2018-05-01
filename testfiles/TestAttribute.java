public class Foo {
    public Integer X;

    public void setX(int val) {
        X = val;
    }
}

public class Bar {
    public void b(Foo f) {
        f.X = 0;
    }

    public void c(Foo f) {
        f.setX(0);
    }
}