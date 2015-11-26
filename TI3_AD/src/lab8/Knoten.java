package lab8;

/**
 * 
 */

/**
 * Project: AD_2_7
 *
 * @author Andreas Müller
 * @MatrNr 209 918 2
 *
 * 18.11.2015
 */
public class Knoten<T> {
	public T value;
	public Knoten<T> left;
	public Knoten<T> right;
	
	public Knoten (T insertValue) {
		this.value = insertValue;
		this.left = null;
		this.right = null;
	}
}