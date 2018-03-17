package p;

        

class A { public void ma(C c, C d){} }

class C {}
        

class B {

        

            private A a;

        

            public void mb(){ a.ma(new C() , new C()); }

}
//puck/pucketendj/src/main/jrag/jrrt/util/     