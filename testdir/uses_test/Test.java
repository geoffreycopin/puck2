class MemberClass { }

class ArgumentClass { }

class ReturnClass { }

class LocalVarClass { }

class UsingClass {
    MemberClass m;

    public ReturnClass method(ArgumentClass arg) {
        LocalVarClass v = new LocalVarClass();
        return new ReturnClass();
    }
}