package abstractFactory.candidate;

interface Widget{
	void draw();
}

class MotifButton implements Widget{
	public void draw() {
		System.out.println("MotifButton");
	}
}

class MotifMenu implements Widget{
	public void draw() {
		System.out.println("MotifMenu");
	}
}


class WindowsButton implements Widget{
	public void draw() {
		System.out.println("WindowsButton");
	}
}

class WindowsMenu implements Widget{
	public void draw() {
		System.out.println("WindowsMenu");
	}
}


public class AbstractFactoryDemo {
	
	public static boolean motif = true;
	
	
	static void display_window_one(){
		Widget w[] = new Widget[2];
		if(motif){
			w[0] = new MotifButton();
			w[1] = new MotifMenu();		
		}
		else{
			w[0] = new WindowsButton();
			w[1] = new WindowsMenu();	
		}
		w[0].draw();
		w[1].draw();
	}
	
	static void display_window_two(){
		Widget w[] = new Widget[2];
		if(motif){
			w[0] = new MotifMenu();		
			w[1] = new MotifButton();
		}
		else{
			w[0] = new WindowsMenu();	
			w[1] = new WindowsButton();
		}
		w[0].draw();
		w[1].draw();
	}
	
	public static void main(String args[]){
		Widget w;
		if(motif)
			w = new MotifButton();
		else
			w = new WindowsButton();
		
		w.draw();
		display_window_one();
		display_window_two();
	}
}
