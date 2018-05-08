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


public class Test {
	
    public void foo() {
    	
        Point p = new Point(1, 1);
        Point.addPoint();
        int x = p.x;
        p.setX(3);
         
        if (p.getX() > 2) {
        	p.setY(4);
        }
        
    }
}