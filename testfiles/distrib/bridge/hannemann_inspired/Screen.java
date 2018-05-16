package bridge.candidate;


public class Screen {
	void draw(){}
}

class WelcomeScreenText extends Screen{

	void welcome(){
		printText();
	}
	void printText(){
		System.out.println("Welcome");
	}
}

class WelcomeScreenGraphic extends Screen{

	void welcome(){
		printGraphic();
	}
	void printGraphic(){
		System.out.println("\\/\\/€|_<0/\\/\\€");
	}
}


class InfoScreenText extends Screen{

	void info(){
		printText();
	}
	void printText(){
		System.out.println("Info");
	}
}

class InfoScreenGraphic extends Screen{

	void info(){
		printGraphic();
	}
	void printGraphic(){
		System.out.println("||\\||=O");
	}
}

// public class ScreenDemo{

// 	public static void main(String[] args){
// 		Screen[] screens = {new WelcomeScreenText(),
// 			new WelcomeScreenGraphic(),
// 			new InfoScreenGraphic(),
// 			new InfoScreenText()};

// 		for(Screen s: screens){

// 		}

// 	}
// }
