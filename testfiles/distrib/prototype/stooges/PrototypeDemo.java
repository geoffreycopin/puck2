package prototype.candidate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

interface Stooge {
	void slap_stick();
}

class Larry implements Stooge{

	public void slap_stick() {
		System.out.println("Larry: poke eyes");
	}
}

class Moe implements Stooge{

	public void slap_stick() {
		System.out.println("Moe: slap head");
	}
	
}

class Curly implements Stooge{

	public void slap_stick() {
		System.out.println("Curly: suffer abuse");
	}
}

public class PrototypeDemo{
	
	static List<Stooge> roles = new ArrayList<Stooge>();
	
	public static void user_creator(int choice){
		
		if(choice == 1)
			roles.add(new Larry());
		else if(choice == 2)
			roles.add(new Moe());
		else
			roles.add(new Curly());
	}
	
	public static void main(String args[]){
		
		try {
			
			int choice;
			
			while(true){
				System.out.println("Larry(1) Moe(2) Curly(3) Go(0): ");
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				choice = Integer.parseInt(bufferRead.readLine());
				
				if(choice == 0)
					break;
				user_creator(choice);
			}
			
			for(Stooge s: roles)
				s.slap_stick();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}