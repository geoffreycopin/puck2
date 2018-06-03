package bridge.candidate;

import java.util.Date;

abstract class Screen {

    abstract public void printText(String text);
    abstract public void printLine();
    abstract public void printDecor();   
    
    public void drawText(String text) {
        printText(text);
        printLine();
    }
    
    public void drawTextBox(String text) {

        int length = text.length();

        for(int i=0; i<length+4; i++) {
            printDecor();
        }

        printLine();
        printDecor();
        printText(" "+text+" ");
        printDecor();
        printLine();
        
        for(int i=0; i<length+4; i++) {
            printDecor();
        }

        printLine(); 
    }
}

class CrossCapitalGreetingScreen extends Screen {
    public void printLine() {
        System.out.println();
    }
     
    public void printDecor() {
        System.out.print("X");
    }
    
    public void printText(String text) {
        System.out.print(text.toUpperCase());
    }

    public void drawGreeting() {
        drawTextBox("Greetings!");
    }
}

class CrossCapitalInformationScreen extends Screen {

	@Override
	public void printText(String text) {
		System.out.print(text.toUpperCase());
	}

	@Override
	public void printLine() {
		System.out.println();
	}

	@Override
	public void printDecor() {
		 System.out.print("X");
	}

    public void drawInfo() {  
        Date date = new Date();
        drawTextBox("Current system time: "+date);
    }
}

class StarGreetingScreen extends Screen {

	@Override
	public void printText(String text) {
		 System.out.print(text);

	}

	@Override
	public void printLine() {
		 System.out.println();

	}

	@Override
	public void printDecor() {
		 System.out.print("*");

	}

    public void drawGreeting() {
        drawTextBox("Greetings!");
    }
}

class StarInformationScreen extends Screen {
    public void printLine() {
        System.out.println();
    }
    
     
    public void printDecor() {
        System.out.print("*");
    }
    
    
    public void printText(String text) {
        System.out.print(text);
    }

    public void drawInfo() {  
        Date date = new Date();
        drawTextBox("Current system time: "+date);
    }
}
 
public class BridgeDemo {
       
    public static void main(String[] args) { 
 
        StarGreetingScreen sgs = new StarGreetingScreen();
        CrossCapitalGreetingScreen cgs = new CrossCapitalGreetingScreen();
        StarInformationScreen sis = new StarInformationScreen();
        CrossCapitalInformationScreen cis = new CrossCapitalInformationScreen();  
        
        System.out.println("Starting test:\n");
        
        sgs.drawText("\nScreen 1 (StarGreetingScreen):");
        sgs.drawGreeting();
        
        cgs.drawText("\nScreen 2 (CrossCapitalGreetingsScreen):");
        cgs.drawGreeting();
        
        sis.drawText("\nScreen 3 (StarInformationScreen):");
        sis.drawInfo();

        cis.drawText("\nScreen 4 (CrossCapitalInformationScreen):");
        cis.drawInfo();
    }
}
