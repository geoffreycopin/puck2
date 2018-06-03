package mediator.candidate;

class Node {
	private int m_val;
	private Node m_next;
	
	public Node(int v){
		m_val = v;
		m_next = null;
	}
	
	void add_node(Node n){
		if(m_next != null)
			m_next.add_node(n);
		else
			m_next = n;
	}
	
	void traverse(){
		System.out.print(this.m_val+" ");
		if(m_next != null)
			m_next.traverse();
		else
			System.out.println();
	}
	
	void remove_node(int v){
		if(m_next != null){
			if(m_next.m_val == v)
				m_next = m_next.m_next;
			else
				m_next.remove_node(v);
		}
	}
}
