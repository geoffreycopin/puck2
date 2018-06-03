public class C {
    C attr;
}

public class D {
    public void useAttr(C param) {
        C var = param.attr;
    }

    public C asseAttr2(C param2) {
        return param2.attr;
    }
}