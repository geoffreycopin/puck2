package factoryMethod.candidate;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * From hannemann
 *
 */

class GUIComponentCreator {
    
   private JLabel createLabel(){
    	 JLabel label = new JLabel("This is a JLabel.");
         return label;
    }
    
    private JButton createButton(){
    	final JButton button = new JButton("Click me!");
        button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button.setText("Thank you!");
			}
        });
        return button;
    }
    
    private static Point lastFrameLocation = new Point(0, 0);

    public final void showFrame(String title, int choice) {
        JFrame frame = new JFrame(title);
        
   		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {System.exit(0);}
		});
		    
		JPanel panel = new JPanel();
	
		if(choice == 0)
			panel.add(createButton());
		else
			panel.add(createLabel());
			
		frame.getContentPane().add(panel);
		frame.pack();    
		frame.setLocation(lastFrameLocation);
		lastFrameLocation.translate(75, 75);
		frame.setVisible(true);  
    }
}			

public class FactoryMethodDemo {

	public static void main(String[] args) {
        
        GUIComponentCreator creator1 = new GUIComponentCreator();
        GUIComponentCreator creator2 = new GUIComponentCreator();
        
        creator1.showFrame("Example 1: A JButton", 0);
        creator2.showFrame("Example 2: A JLabel", 1);
	}
}