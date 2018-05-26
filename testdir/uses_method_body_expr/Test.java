public class Num {
	int getNum() {
		return 34;
	}
	
}

public class NumGen extends Num{
	int var =0 ;
	int getNum1() {
		return 1;
	}
	int getNum2() {
		return 2;
	}
	int getNum3() {
		return 3;
	}
}


public class Test {
	
	public void function(int var ) {
		NumGen num = new NumGen();
		
		int x = num.var;
		
		//GTexpr
		if( num.getNum1() > 2) var =2;
		//MultExpr
		if( num.getNum2()* num.getNum3() > 3) var =2;
		
		
		//cast
		if( ((Num)num).getNum() == 3) var=56;
		
		
	}

}
