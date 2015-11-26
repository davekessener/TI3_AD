/**
 * 
 */
package lab8;

import java.util.List;

/**
 * Project: AD_2_8
 *
 * @author Andreas M�ller
 * @MatrNr 209 918 2
 *
 * 24.11.2015
 */
public class IntTreeArray extends TreeArray<Integer>{
	
	public boolean lifted = false;
	
	@Override
	public void insert (Integer value) {
		
		if (findNode(value, wurzel) == null) {
			super.insert(value);
			lifted = false;
		}
		
	}
	
	@Override
	public void remove (Integer value) {
		
		super.remove(value);
		lifted = false;
		
	}
	
	public int sumBetweenTwo (int min, int max) {
		
		Knoten<Integer> current = wurzel;
		IntKnoten knoten_m;
		IntKnoten knoten_M;
		
		if (!lifted) {
			((IntKnoten)wurzel).calcWeight(0);
			lifted = true;
		}
		
		knoten_m = (IntKnoten)getNearMin(min, current);
		knoten_M = (IntKnoten)getNearMax(max, current);
		
		return knoten_M.weight - knoten_m.weight + knoten_m.value;
		
	}
	
	public Knoten<Integer> getNearMin (int min, Knoten<Integer> cur) {
		
		Knoten<Integer> knoten_m; 
		
		if (((Comparable<Integer>)cur.value).compareTo(min) == 0) {
			return cur;
		} else if (cur.left != null && ((Comparable<Integer>)cur.value).compareTo(min) == 1) {
			knoten_m = getNearMin(min, cur.left);
			return knoten_m == null ? cur : knoten_m;
		} else if (cur.right != null && ((Comparable<Integer>)cur.value).compareTo(min) == -1) {
			knoten_m = getNearMin(min, cur.right);
			return knoten_m == null ? cur : knoten_m;
		} else {
			if (((Comparable<Integer>)cur.value).compareTo(min) == 1) {
				return cur;
			} else {
				return null;
			}
		}
		
	}
	
public Knoten<Integer> getNearMax (int max, Knoten<Integer> cur) {
		
		Knoten<Integer> knoten_M; 
		
		if (((Comparable<Integer>)cur.value).compareTo(max) == 0) {
			return cur;
		} else if (cur.left != null && ((Comparable<Integer>)cur.value).compareTo(max) == 1) {
			knoten_M = getNearMin(max, cur.left);
			return knoten_M == null ? cur : knoten_M;
		} else if (cur.right != null && ((Comparable<Integer>)cur.value).compareTo(max) == -1) {
			knoten_M = getNearMin(max, cur.right);
			return knoten_M == null ? cur : knoten_M;
		} else {
			if (((Comparable<Integer>)cur.value).compareTo(max) == -1) {
				return cur;
			} else {
				return null;
			}
		}
		
	}
	
	@Override
	public Knoten<Integer> createKnoten(Integer value) {
		return new IntKnoten(value);
	}
	
	public static void _main (String[] args) {
		IntTreeArray ita = new IntTreeArray();
		ita.insert(4);
		ita.insert(2);
		ita.insert(1);
		ita.insert(3);
		ita.insert(5);
		ita.insert(6);
		ita.insert(7);
		
		List<Integer> list = ita.inorder();
		
		for (Integer integer : list) {
			System.out.println(""+integer);
		}
		
		System.out.println("betw  1 , 3 (6): " + ita.sumBetweenTwo(1, 3));
		System.out.println("betw  3 , 5 (12): " + ita.sumBetweenTwo(3, 5));
		System.out.println("betw  3 , 7 (25): " + ita.sumBetweenTwo(3, 7));
		System.out.println("betw  6 , 7 (13): " + ita.sumBetweenTwo(6, 7));
	}
}
