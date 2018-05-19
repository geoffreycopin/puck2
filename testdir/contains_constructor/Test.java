public class TestError extends Exception { }
public class Test {

	private int x;
	private int y;
	
	public Test(int x , int y)throws TestError{
		this.x=x;
		this.y=y;
	}
}
