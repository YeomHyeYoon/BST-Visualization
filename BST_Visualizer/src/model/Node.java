package model;

public class Node {

	private int key;
	private Node left;
	private Node right;
	
	public Node(int key) {
		this.key = key;
		this.left = null;
		this.right = null;
	}

	public int getKey() {
		return key;
	}
	public Node getLeft() {
		return left;
	}
	public Node getRight() {
		return right;
	}

	public void setKey(int key) {
		this.key = key;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public void setRight(Node right) {
		this.right = right;
	}
}
