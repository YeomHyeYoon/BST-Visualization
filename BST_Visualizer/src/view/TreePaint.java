package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Node;



public class TreePaint extends JPanel {
	
    private Node root;

    public TreePaint (Node root) {
        this.root = root;
        d = 20; 
        rows = (2 * d);
        cols = 2 << d;
    }

    private int d;
    private int rows;
    private int cols;
	
	// @override 
    public void paint (Graphics g) {
        Dimension dim = getSize ();
        int xf = dim.width / cols;
        int yf = dim.height / rows;
        int fontsize = (xf + yf) / 2;
        g.setFont (g.getFont().deriveFont (fontsize* 1.5f));
        xyPrint (root, dim.width/2, dim.width/2, 1, xf, yf, g);
    }
    
    void xyPrint (Node n, int x, int dx, int y, int xf, int yf, Graphics g) {
    	
        g.drawString ("" + n.getKey(), x - xf, (y+1) * yf);
        g.setColor (Color.BLUE);
        if (n.getLeft() != null) {
            g.drawLine (x - (dx/2) + xf, (y+2) * yf, x, (y+1) * yf); // line:Up
            xyPrint (n.getLeft(), x - dx/2, dx/2, y + 2, xf, yf, g);
        }
        if (n.getRight() != null) {
            g.drawLine (x + xf, (y+1) * yf, x + (dx/2), (y+2) * yf); // line down
            xyPrint (n.getRight(), x + dx/2, dx/2, y + 2, xf, yf, g);
        }
    }

}
