/**
 * 
 */
package lab8;

/**
 * Project: AD_2_8
 *
 * @author Andreas Müller
 * @MatrNr 209 918 2
 *
 * 24.11.2015
 */
public class IntKnoten extends Knoten<Integer>{

	public int weight;
	
	public IntKnoten (Integer value) {
		super(value);
		
		weight = value;
	}
	
	public int calcWeight (int transWeight) {
		if (left == null) {
			weight = value + transWeight;
		} else {
			weight = value + ((IntKnoten)left).calcWeight(transWeight);
		}
		
		if (right == null) {
			return weight;
		} else {
			return ((IntKnoten)right).calcWeight(weight);
		}
	}
}
