package model;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;


public class Tree {

	public static final int MAX_SIZE = 20;
	public static final int MAX_HEIGHT = 15;
	
	static int size;
	public static Node root;
	
	public Tree() {
		size = 0;
		root = null;
	}
	
	public boolean contains(int key) {
		if (root == null) {
			// Ʈ���� ����ִٸ�
			return false;
		} else {
			Node cursor = root;
			
			// ���� ���� ã���� ����
			while(cursor.getKey() != key) {
				
				// ���� ���Ͽ� Ʈ�� �Ʒ��� ã�� ������
				cursor = (key < cursor.getKey()) ? cursor.getLeft() : cursor.getRight();
				
				// ������ �ƴϸ� ��� ����
				if (cursor != null) { 
					continue;
				} else {
					// Ʈ�� ������ ��ã�´ٸ�
					return false;
				}
			}
			
			return true;
		}
	}
	
	
	/**
	 * @return -1: FULL TREE, -2: DUPLICATED KEY, -3: MAX HEIGHT, 0: SUCCESS
	 */
	public int push(int key) {
		if (size < MAX_SIZE) {
			
			if (getHeight(root) < MAX_HEIGHT) {
				if (contains(key)) {
					// �ߺ���
					return -2;
				} else {
					root = push(root, key);
					size += 1;
					return 0;
				}
			} else {
				// Ʈ�� ���� ����
				return -3;
			}
		} else {
			// Ʈ�� ����
			return -1;
		}
	}
	
	
	private Node push(Node node, int key) {
		if (node != null) {
			
			// Ʈ�� ������ ���� ���ϸ� Recursive�ϰ� ��������.
			if (key < node.getKey()) { 
				node.setLeft(push(node.getLeft(), key));
			} else {
				node.setRight(push(node.getRight(), key));
			}

			// �� ��带 �����Ѵ�
			return node; 
		} else {
			// Ʈ�� ���� �ٴ����� �� ��带 ���� �� ������
			return new Node(key);
		}
	}
	
	/**
	 * @return -1: non-exist, 0: success
	 */
	public int pop(int key) {
		if (contains(key)) {
			root = pop(root, key);
			size -= 1;
			return 0;
		} else {
			return -1;
		}
	}
	private Node pop(Node node, int key) {
		if (node != null) {
			if (key < node.getKey()) {
				// ã�������� �� ���ϸ� �Ʒ��� �˻���(��������)
				node.setLeft(pop(node.getLeft(), key));
			} else if (key > node.getKey()){
				// ã�������� �� ���ϸ� �Ʒ��� �˻���(��������)
				node.setRight(pop(node.getRight(), key));
			} else {
				if (node.getLeft() == null) {
					// �ڽ� �ϳ� ���̶��(���� �ϳ���)
					return node.getRight();
				} else if (node.getRight() == null) {
					// �ڽ� �ϳ� ���̶��(���� �ϳ���)
					return node.getLeft();
				} else {
					// Success�� ��� �� ������Ʈ ��
					node.setKey(successor(node.getRight()));
					
					// Successor ��� ���� ��
					node.setRight(pop(node.getRight(), node.getKey()));
				}
			}
		}
		
		return node;
	}
	
	// Successor : Right Subtree�� ���� ���� ���� ã��
	private int successor(Node node) {
		int key = node.getKey();
		
		while (node.getLeft() != null) {
			key = node.getLeft().getKey();
			node = node.getLeft();
		}
		
		return key;
	}
	
	
	// Ʈ���� ���̸� ���մϴ�
	private int getHeight(Node node) {
		if (node != null) {
			int lh = getHeight(node.getLeft());
			int rh = getHeight(node.getRight());
			
			return (lh > rh) ? (lh + 1) : (rh + 1);
		} else {
			return 0;
		}
	}
	
	public int getHeight() {
		return getHeight(root);
	}
	
	
	// Ʈ�� Ŭ������ Ʈ�� ��� ��ȯ�մϴ�
	public void fillJTree(DefaultMutableTreeNode parentContainer, Node value) {
		if (value != null) {
			DefaultMutableTreeNode container = new DefaultMutableTreeNode(value.getKey());
			
			parentContainer.add(container);
			
			fillJTree(container, value.getLeft());
			fillJTree(container, value.getRight());
			
		}
	}
	
	
	public JTree asJTree() {
		if (root != null) {
			DefaultMutableTreeNode container = new DefaultMutableTreeNode(root.getKey());
			fillJTree(container, root.getLeft());
			fillJTree(container, root.getRight());
			
			JTree tree = new JTree(container);
			for (int row = 0 ; row < tree.getRowCount() ; ++row) {
				tree.expandRow(row);
			}
			
			return tree;			
		} else {
			return new JTree();
		}
	}
}
