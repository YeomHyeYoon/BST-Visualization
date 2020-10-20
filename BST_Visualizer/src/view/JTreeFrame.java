package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;

import model.Node;
import model.Tree;

public class JTreeFrame extends JFrame implements ActionListener {
	
	public static final int TOOLBAR_HEIGHT = 40;
	
	private static Tree tree;
	
	private JPanel canvasPanel = new JPanel();
	private JButton pushButton = new JButton("PUSH");
	private JButton popButton = new JButton("POP");
	private JTextField textField = new JTextField(4);
	TreeViewFrame printer = new TreeViewFrame();
	
	private JTreeFrame() {
		tree = new Tree();
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int size = (int) (Math.min(screen.width, screen.height) * 0.75);
		
		setTitle("JTreeFrame");
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation((screen.width - size) / 2, (screen.height - size - TOOLBAR_HEIGHT) / 2);
		
		setLayout(new BorderLayout());
		initializeCanvas();
		initializeToolbar();
		
		
	}
	
	private void initializeCanvas() {
		canvasPanel = new JPanel();
		canvasPanel.setBackground(Color.WHITE);
		add(canvasPanel, BorderLayout.CENTER);
	}
	
	private void initializeToolbar() {
		JPanel panel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, TOOLBAR_HEIGHT);
			}
		};
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		panel.add(textField);
		panel.add(pushButton);
		panel.add(popButton);
		
		pushButton.addActionListener(this);
		popButton.addActionListener(this);
		
		add(panel, BorderLayout.SOUTH);
	}
	
	public static void main(final String[] argvs) {
		
		new JTreeFrame().setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String input = textField.getText().trim();
		
		if (input.length() > 0) {
			try {
				int key = Integer.parseInt(input);
				
				switch (event.getActionCommand()) {
				case "PUSH":

					
					switch (tree.push(key)) {
					case -1:
						JOptionPane.showMessageDialog(this, "더 이상 추가할 수 없습니다");
						break;
						
					case -2:
						JOptionPane.showMessageDialog(this, "이미 존재하는 값입니다");
						break;
						
					case -3:
						JOptionPane.showMessageDialog(this, "MAX HEIGHT");
						break;
						
					case 0:
						
						textField.setText("");
						canvasPanel.removeAll();
						canvasPanel.add(tree.asJTree());
						printer.nodeprint (tree.root);
						System.out.println(key);	
						
						validate();
						repaint();
					}
					break;
					
				case "POP":
					switch (tree.pop(key)) {
					case -1:
						JOptionPane.showMessageDialog(this, "존재하지 않는 값입니다");
						break;
						
					case 0:
						textField.setText("");
						canvasPanel.removeAll();
						canvasPanel.add(tree.asJTree());
						printer.nodeprint (tree.root);
						
						validate();
						repaint();
					}
					break;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "숫자만 입력 해주세요");
			}
		} else {
			JOptionPane.showMessageDialog(this, "숫자를 입력 해주세요!!");
		}
		
		textField.requestFocus();
	}
}
