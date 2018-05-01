public class Point {
    public int x;
    public int y;

    public void setX(int x) {
        this.x = X ;
    }
}


public class Test {
	
    public void foo() {
    	
        Point p = new Point(1, 1);
        int x = p.x;
        p.setX(2);
    }
}