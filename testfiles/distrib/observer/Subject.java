package observer.candidate;

class DivObs{
	private int div_;
	public DivObs(int d) {
		div_ = d;
	}
	void update(int v){
		System.out.println(v + " div " + div_ +" is " + (v/div_));
	}
}

class ModObs{
	private int div_;
	public ModObs(int d) {
		div_ = d;
	}
	void update(int v){
		System.out.println(v + " mod " + div_ +" is " + (v%div_));
	}
}

class Subject {
	private int val_;
	private DivObs div_;
	private ModObs mod_;
	
	public Subject(){
		div_ = new DivObs(4);
		mod_ = new ModObs(3);
	}
	
	public void setVal(int v){
		val_ = v;
		div_.update(v);
		mod_.update(v);
	}
}

class Main{
	public static void main(String[] args){
		Subject subj = new Subject();
		subj.setVal(14);
	}
}