package hollyrock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MovieMaker{

	private List<Actor> roles = new ArrayList<Actor>();

	public void hire(int choice){

		if(choice == 1)
			roles.add(new Comedian());
		else if(choice == 2)
			roles.add(new Tragedian());
		else
			roles.add(new Extra());
	}

	public void play(){
		for(Actor s: roles)
				s.act();
	}

	public static void main(String args[]){

		MovieMaker movie = new MovieMaker();

		try {

			int choice = -1;

			while(true){
				System.out.println("Comedian(1) Tragedian(2) Extra(3) Go(0): ");
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				choice = Integer.parseInt(bufferRead.readLine());

				if(choice == 0)
					break;
				movie.hire(choice);
			}

			movie.play();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
