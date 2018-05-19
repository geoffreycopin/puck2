import java.util.ArrayList;
public class Point{	
int x,y;
public Point(int x, int y) {
	this.x=x;
	this.y=y;
}

public void SetX(int o) {
	this.x=o;
}

}

public class Carre{
final static int cpt=0;

public static void addPoint() {
	cpt++;
}
}




public class Test {
	
	public listPoint() {
	//ParTypeAccess
	ArrayList<Point> p = new ArrayList<>();
	p.add(new Point(1,1));
	//Dot : method access
	p.get(0).SetX(5);
	//Dot : static method call 
	Carre.addPoint();
	
	
	
	}
}
