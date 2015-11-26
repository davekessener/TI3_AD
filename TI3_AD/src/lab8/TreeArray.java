package lab8;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lab7.SearchTree;

/**
 * 
 */

/**
 * Project: AD_2_7
 *
 * @author Andreas M�ller
 * @MatrNr 209 918 2
 *
 * 18.11.2015
 */
public class TreeArray<T> implements SearchTree<T> {
	private Knoten<T>[] tree;
	public  Knoten<T> wurzel;
	
	@SuppressWarnings("unchecked")
	public TreeArray() {
		tree = new Knoten[0];
	}

	/* (non-Javadoc)
	 * @see lab7.SearchTree#insert(java.lang.Object)
	 */
	@Override
	public void insert(T v) {
		@SuppressWarnings("unchecked")
		Knoten<T>[] newArr = new Knoten[tree.length + 1];
		System.arraycopy(tree, 0, newArr, 0, tree.length);

		Knoten<T> newKnoten = createKnoten(v);
		newArr[tree.length] = newKnoten;
		
		newArr[newArr.length - 1] = newKnoten;
		
		if (newArr.length > 1) {
			System.out.println("INSERT");
			insert(newArr[0], newKnoten);
		} else {
			wurzel = newKnoten;
		}
		
		tree = newArr;
	}
	
	public Knoten<T> createKnoten (T value) {
		return new Knoten<T>(value);
	}
	
	@SuppressWarnings("unchecked")
	private void insert(Knoten<T> root, Knoten<T> node) {
		if (((Comparable<T>) node.value).compareTo(root.value) <= 0) {
			//Left
			if (root.left == null) {
				root.left = node;
				System.out.println("FOUNDLEFT!");
			} else {
				insert(root.left, node);
			}
		} else {
			//Right
			if (root.right == null) {
				root.right = node;
				System.out.println("FOUNDRIGHT!");
			} else {
				insert(root.right, node);
			}
		}
	}

	/* (non-Javadoc)
	 * @see lab7.SearchTree#remove(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void remove(T v) {
		int i = 0;
		boolean found = false;
		Knoten<T> toDelete = findNode(v, wurzel);
		
		remove(toDelete);
		
		while (!found && i < tree.length) {
			if (((Comparable<T>)tree[i].value).compareTo(v) == 0) {
				found = true;
				for (int j = i; j < tree.length; j++) {
					tree[j] = tree[j + 1];
				}
			} else {
				i++;
			}		
		}
		if (found) tree = Arrays.copyOf(tree, tree.length - 1);
	}
	
	private void remove (Knoten<T> toDelete) {		
		if (toDelete != null) {
			if (toDelete.left != null && toDelete.right != null) {
				// 2 Child
				Knoten<T> maxNode = toDelete.left;
				while (maxNode.right != null) maxNode = maxNode.right;
				toDelete.value = maxNode.value;
				Knoten<T> next = findNode(maxNode.value, toDelete.left);
				remove (next);
			} else if (toDelete.left != null && toDelete.right == null || 
					   toDelete.left == null && toDelete.right != null) {
				// 1 Child
				if (toDelete.left != null) {
					toDelete.value = toDelete.left.value;
					toDelete.left = null;
				} else {
					toDelete.value = toDelete.right.value;
					toDelete.right = null;
				}
			} else {
				// 0 Child
				toDelete = null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Knoten<T> findNode (T value, Knoten<T> node) {
		if (node == null) {
			return null;
		} else if (((Comparable<T>)node.value).compareTo(value) == 0) {
			return node;
		} else if (((Comparable<T>)node.value).compareTo(value) == 1) {
			return findNode(value, node.left);
		} else {
			return findNode(value, node.right);
		}
	}
	
	/* (non-Javadoc)
	 * @see lab7.SearchTree#contains(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(T v) {
		boolean isIn = false;
		for (int i = 0; i < tree.length; i++) {
			if (((Comparable<T>)tree[i].value).compareTo(v) == 0) isIn = true;
		}
		return isIn;
	}

	/* (non-Javadoc)
	 * @see lab7.SearchTree#clear()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		tree = new Knoten[0];
	}

	/* (non-Javadoc)
	 * @see lab7.SearchTree#size()
	 */
	@Override
	public int size() {
		return tree.length;
	}

	/* (non-Javadoc)
	 * @see lab7.SearchTree#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return tree.length == 0 ? true : false;
	}

	/* (non-Javadoc)
	 * @see lab7.SearchTree#preorder()
	 */
	@Override
	public List<T> preorder() { return preorder(tree[0]); }
	public List<T> preorder(Knoten<T> node) {
		List<T> elemPO = new ArrayList<T>();
		
		//Node
		elemPO.add(node.value);
		
		//Left Leaf
		if (node.left != null) {
			elemPO.addAll(preorder(node.left));
		}
		
		//Right Leaf
		if (node.right != null) {
			elemPO.addAll(preorder(node.right));
		}
		
		return elemPO;
	}

	/* (non-Javadoc)
	 * @see lab7.SearchTree#inorder()
	 */
	@Override
	public List<T> inorder() { return inorder(tree[0]); }
	public List<T> inorder(Knoten<T> node) {
		List<T> elemIO = new ArrayList<T>();
		
		//Left Leaf
		if (node.left != null) {
			elemIO.addAll(inorder(node.left));
		}
		
		//Root
		elemIO.add(node.value);
		
		//Right Leaf
		if (node.right != null) {
			elemIO.addAll(inorder(node.right));
		}
		
		return elemIO;
	}

	/* (non-Javadoc)
	 * @see lab7.SearchTree#postorder()
	 */
	@Override
	public List<T> postorder() { return postorder(tree[0]); }
	public List<T> postorder(Knoten<T> node) {
		List<T> elemPO = new ArrayList<T>();
		
		//Left Leaf
		if (node.left != null) {
			elemPO.addAll(postorder(node.left));
		}
		
		//Right Leaf
		if (node.right != null) {
			elemPO.addAll(postorder(node.right));
		}
		
		//Root
		elemPO.add(node.value);
		
		return elemPO;
	}
}
