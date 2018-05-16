package proxy.candidate;

class Image{
    static int s_next = 1;
    int m_id;
    public Image(){
	m_id = s_next++;
	System.out.println(" $$ ctor : " + m_id);
	
    }

    protected void finalize(){
	System.out.println("dtor: " + m_id);
    }
    
    void draw(){
	System.out.println("drawing image" + m_id);
    }
    
    
}

class Main{
	
	public static void drawingApp(Image [] images){
		for(int i=-1; true;){
		    System.out.println("Exit[0], Image[1-5]");
		    i = Integer.parseInt(System.console().readLine());
		    if(i==0)
			break;
		    
		    images[i-1].draw();
		}
	}
	
    public static void main(String[] args){
		Image[] images = new Image[5];
		for(int i =0; i<5; i++){
		    images[i] = new Image();
		}

		drawingApp(images);
    }
}