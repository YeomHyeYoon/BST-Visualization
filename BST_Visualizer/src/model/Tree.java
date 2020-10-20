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
			// 트리가 비어있다면
			return false;
		} else {
			Node cursor = root;
			
			// 같은 값을 찾을때 까지
			while(cursor.getKey() != key) {
				
				// 값을 비교하여 트리 아래로 찾아 내려감
				cursor = (key < cursor.getKey()) ? cursor.getLeft() : cursor.getRight();
				
				// 리프가 아니면 계속 진행
				if (cursor != null) { 
					continue;
				} else {
					// 트리 끝까지 못찾는다면
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
					// 중복값
					return -2;
				} else {
					root = push(root, key);
					size += 1;
					return 0;
				}
			} else {
				// 트리 높이 꽉참
				return -3;
			}
		} else {
			// 트리 꽉참
			return -1;
		}
	}
	
	
	private Node push(Node node, int key) {
		if (node != null) {
			
			// 트리 끝까지 값을 비교하며 Recursive하게 내려간다.
			if (key < node.getKey()) { 
				node.setLeft(push(node.getLeft(), key));
			} else {
				node.setRight(push(node.getRight(), key));
			}

			// 원 노드를 리턴한다
			return node; 
		} else {
			// 트리 끝에 다달으면 새 노드를 생성 후 리턴함
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
				// 찾을때까지 값 비교하며 아래로 검색함(좌측으로)
				node.setLeft(pop(node.getLeft(), key));
			} else if (key > node.getKey()){
				// 찾을때까지 값 비교하며 아래로 검색함(우측으로)
				node.setRight(pop(node.getRight(), key));
			} else {
				if (node.getLeft() == null) {
					// 자식 하나 뿐이라면(우측 하나뿐)
					return node.getRight();
				} else if (node.getRight() == null) {
					// 자식 하나 뿐이라면(좌측 하나뿐)
					return node.getLeft();
				} else {
					// Success로 노드 값 업데이트 후
					node.setKey(successor(node.getRight()));
					
					// Successor 노드 삭제 함
					node.setRight(pop(node.getRight(), node.getKey()));
				}
			}
		}
		
		return node;
	}
	
	// Successor : Right Subtree중 가장 작은 값을 찾음
	private int successor(Node node) {
		int key = node.getKey();
		
		while (node.getLeft() != null) {
			key = node.getLeft().getKey();
			node = node.getLeft();
		}
		
		return key;
	}
	
	
	// 트리의 높이를 구합니다
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
	
	
	// 트리 클래스를 트리 뷰로 변환합니다
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
