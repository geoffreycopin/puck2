import java.util.ArrayList;

public class Point {
    public int x;
    public int y;
    public static int cpt ;
    

    public void setX(int x) {
        this.x = x ;
    }
    public void getX() {
        return this.x ;
    }
    
    public void setY(int y) {
        this.y = y ;
    }
    
    public static void addPoint() {
    	cpt++;
    }
    
 
    
 
}

class Cercle{
	int a;
	
	int returnNumber() {
		return 2;
	}
	int getA() { return a;}
}

class Triangle{
	int f,t,g;
	Triangle(int f, int t , int g ){
		this.t=t;
		this.f=f;
		this.g=g;
	}
	int getF() { return f;}
	void getG( { return g;}
	void SetF(int f) { this.f=f;}
	void SetG(int g) { this.g=g;}
}

public class Test {
	
    public int foo() {
    	int i=0;
        Point p = new Point(1, 1);
        Point.addPoint();
        int x = p.x;
        p.setX(3);
         
        //if block
        if (p.getX() > 2) {
        	p.setY(4);
        }
        
        //class interne
        class interne{
        	
        }
        
        //while
        while(new Cercle().returnNumber() > 2) {}
        while (i < 2) {
        	Cercle c =new Cercle();
        	c.getA();
        	i++;
        }
        //enhanced for
        for (Triangle a : new ArrayList<Triangle>()) {	
        }
        
        
        //switch
        Triangle t = new Triangle(1,2,3);
        switch (t.getF()){
          case 1 :
          t.SetF(0);
           break;        
          default:
          t.SetG(4);

        }
        
        //return 
        return t.getG();
        
        
    }
}