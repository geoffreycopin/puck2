package state.candidate;

import java.io.*;

/*
 * Original Chain candidate
 */
/*
  class Chain {
 
	private int state;

	public Chain()     { state = 0; }
	public void pull() {
		if (state == 0) {
			state = 1;
			System.out.println( "   low speed" );
		} else if (state == 1) {
			state = 2;
			System.out.println( "   medium speed" );
		} else if (state == 2) {
			state = 3;
			System.out.println( "   high speed" );
		} else {
			state = 0;
			System.out.println( "   turning off" );
		}   
	}   
}
*/
/*
 * Chain candidate with method extracted
 */
class Chain {
	
	void pullLow(){
		state = 1;
		System.out.println( "   low speed" );
	}
	
	void pullMedium(){
		state = 2;
		System.out.println( "   medium speed" );
	}
	
	void pullHigh(){
		state = 3;
		System.out.println( "   high speed" );
	}
	
	void pullOff(){
		state = 0;
		System.out.println( "   turning off" );
		
	}
	
	private int state;

	public Chain()     { state = 0; }
	
	public void pull() {
		if (state == 0) {
			pullLow();
		} else if (state == 1) {
			pullMedium();
		} else if (state == 2) {
			pullHigh();
		} else {
			pullOff();
		}   
	}   
}

public class StateDemo {
	public static void main( String[] args ) throws IOException {
		InputStreamReader is = new InputStreamReader( System.in );
		int ch;
		Chain chain = new Chain();
		while (true) {
			System.out.print( "Press <Enter>" );
			ch = is.read();    ch = is.read();
			chain.pull();
		}   
	}   
}