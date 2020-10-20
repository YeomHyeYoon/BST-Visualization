package view;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Node;

class TreeViewFrame {
	
	JFrame jf;
	
	public TreeViewFrame() {
		jf = new JFrame ("TreeViewFrame");
        jf.setSize (650, 520);
        jf.setLocationRelativeTo (null);
        jf.setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
        jf.setVisible (true);
		
	}

    void nodeprint (Node root) {   
        
        jf.add (new TreePaint(root));
        jf.validate();
		jf.repaint();
      
    }
}