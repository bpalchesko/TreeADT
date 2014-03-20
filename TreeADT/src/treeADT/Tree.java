package treeADT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Defines abstract data type Tree
 * 
 * @author Brad Palchesko
 * @version 1.0
 * @param <V>
 */
public class Tree<V> {

	private V value;
	private ArrayList<Tree<V>> children;

	/**
	 * Constructs node of Tree.
	 * 
	 * @param value
	 * @param children
	 */
	@SafeVarargs
	public Tree(V value, Tree<V>... children) {
		this.value = value;
		this.children = new ArrayList<Tree<V>>();
		for (Tree<V> c : children) {
			this.children.add(c);
		}
	}

	/**
	 * Returns the value in this node of the Tree.
	 * 
	 * @return value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * Returns the first child of this tree, if applicable.
	 * 
	 * @return first child node or null if none
	 */
	public Tree<V> firstChild() {
		if (children.size() > 0) return children.get(0);
		else return null;
	}

	/**
	 * Returns the last child of this tree, if applicable.
	 * 
	 * @return last child node or null if none
	 */
	public Tree<V> lastChild() {
		if (children.size() > 0) { 
			return children.get(numberOfChildren() - 1);
		} else return null;
	}

	/**
	 * Returns the number of (immediate) children of this node.
	 * 
	 * @return number of children
	 */
	public int numberOfChildren() {
		return children.size();
	}

	/**
	 * Returns the child of this node with the corresponding index.
	 * 
	 * @param index
	 * @return corresponding child node
	 * @throws NoSuchElementException
	 */
	public Tree<V> child(int index) throws NoSuchElementException {
		if (index < 0 || index >= children.size()) {
			throw new NoSuchElementException();
		}
		return children.get(index);
	}

	/**
	 * Returns an iterator for the children of this tree.
	 * 
	 * @return tree iterator
	 */
	public Iterator<Tree<V>> children() {
		return children.iterator();
	}

	/**
	 * Returns whether this node has children.
	 * 
	 * @return boolean whether leaf
	 */
	public boolean isLeaf() {
		if (this.numberOfChildren() == 0) return true;
		else return false;
	}

	/**
	 * Returns whether this tree (including this node as root), contains the
	 * given node.
	 * 
	 * @param node
	 * @return
	 */
	private boolean contains(Tree<V> node) {
		if (this == node) return true;
		for (Tree<V> c : children) {
			if (c.contains(node)) return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Tree)) return false;
		Tree tree = (Tree) object;
		if (!this.value.equals(tree.getValue())) return false;
		if (numberOfChildren() != tree.numberOfChildren()) return false;
		for (int i = 0; i < numberOfChildren(); i++) {
			if (!(child(i).equals(tree.child(i)))) return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return toString(this, "");
	}

	/**
	 * Aggregates the tree's toString output.
	 * 
	 * @param t
	 * @param indent
	 * @return formatted string
	 */
	private String toString(Tree<V> t, String indent) {
		Iterator<Tree<V>> iterator = t.children.iterator();
		String output = indent + t.getValue().toString() + '\n';
		while (iterator.hasNext()) {
			output += toString(iterator.next(), indent + "  ");
		}
		return output;
	}

	/**
	 * Sets the value in this node of the Tree.
	 * 
	 * @param value
	 */
	public void setValue(V value) {
		this.value = value;
	}

	/**
	 * Adds the given node as the last child of this tree, if valid.
	 * 
	 * @param newChild
	 * @throws IllegalArgumentException
	 */
	public void addChild(Tree<V> newChild) throws IllegalArgumentException {
		if (this.contains(newChild)) { 
			throw new IllegalArgumentException();
		}
		if (newChild.contains(this)) { 
			throw new IllegalArgumentException();
		}
		children.add(newChild);
	}

	/**
	 * Adds the given node at the given children index of this tree, if valid.
	 * 
	 * @param index
	 * @param newChild
	 * @throws IllegalArgumentException
	 */
	public void addChild(int index, Tree<V> newChild)
			throws IllegalArgumentException {
		if (index < 0 || index >= numberOfChildren()) {
			throw new IllegalArgumentException();
		}
		if (this.contains(newChild)) {
			throw new IllegalArgumentException();
		}
		if (newChild.contains(this)) {
			throw new IllegalArgumentException();
		}
		ArrayList<Tree<V>> newChildren = new ArrayList<Tree<V>>();
		for (int i = 0; i < index; i++) {
			newChildren.add(child(i));
		}
		newChildren.add(newChild);
		for (int j = index + 1; j <= numberOfChildren(); j++) {
			newChildren.add(child(j - 1));
		}
		children = newChildren;
	}

	/**
	 * Adds the given nodes starting at the end of the list of children, if
	 * valid.
	 * 
	 * @param children
	 * @throws IllegalArgumentException
	 */
	public void addChildren(Tree<V>... children)
			throws IllegalArgumentException {
		for (Tree<V> c : children) {
			addChild(c);
		}
	}

	/**
	 * Removes and returns the child of this tree located at the given index.
	 * 
	 * @param index
	 * @return
	 * @throws NoSuchElementException
	 */
	public Tree<V> removeChild(int index) throws NoSuchElementException {
		if (index < 0 || index >= children.size()) {
			throw new NoSuchElementException();
		}
		Tree<V> old = child(index);
		ArrayList<Tree<V>> newChildren = new ArrayList<Tree<V>>();
		for (int i = 0; i < index; i++) {
			newChildren.add(child(i));
		}
		for (int j = index; j < numberOfChildren() - 1; j++) {
			newChildren.add(child(j + 1));
		}
		children = newChildren;
		return old;
	}

}
