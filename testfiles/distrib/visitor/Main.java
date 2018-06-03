package visitor.candidate;

interface BinaryTree {
	
	int sum();
	String traverse();
	
}

class BinaryTreeLeaf implements BinaryTree {  
    
	protected int value;
	
	public BinaryTreeLeaf(int value) { this.value = value; } 
	
	public int getValue() {return value; }

	public int sum() {
		return getValue();
	}

	public String traverse() {
		return ""+getValue();
	}
}

class BinaryTreeNode implements BinaryTree { 
    
	protected BinaryTree left;

	protected BinaryTree right;

	public BinaryTree getLeft() {
	    return left;
	}

	public BinaryTree getRight() {
	    return right;
	}

	public BinaryTreeNode(BinaryTree left, BinaryTree right) {
		this.left = left;
		this.right = right;
	}

	public int sum() {
		return getLeft().sum() + getRight().sum();
	}

	public String traverse() {
		return "{" + getLeft().traverse() + ", " +getRight().traverse() + "}"; 
	}
}

public class Main { 
	
	public static void main(String[] args) { 
	    
	    System.out.println("Building the tree (1): leaves");
		
		BinaryTreeLeaf one   = new BinaryTreeLeaf(1);
		BinaryTreeLeaf two   = new BinaryTreeLeaf(2);
		BinaryTreeLeaf three = new BinaryTreeLeaf(3);
		
	    System.out.println("Building the tree (1): regular nodes");
		
		BinaryTreeNode regN = new BinaryTreeNode(one, two);
		BinaryTreeNode root = new BinaryTreeNode(regN, three);
		
        System.out.println("The tree now looks like this: ");
        System.out.println("         regN                 ");
        System.out.println("        /    \\               ");
        System.out.println("    regN      3               ");
        System.out.println("   /    \\                    ");
        System.out.println("  1      2                    ");
		            
        System.out.println("Visitor 1: SumVisitor, collects the sum of leaf");
        System.out.println("values. Result should be 6.");
				            
		System.out.println(root.sum());  
		
        System.out.println("Visitor 2: TraversalVisitor, collects a tree");
        System.out.println("representation. Result should be {{1,2},3}.");
		
		System.out.println(root.traverse());  
	}
}