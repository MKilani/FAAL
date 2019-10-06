package faal;


import java.util.ArrayList;
import java.util.List;

public class MatcherConfig4 {

	Double[] arrayMatcherConfig4 = new Double[1];
	List<Double> matcherConfig4 = new ArrayList<Double>();

	/**
	 * Set the factor A (see article) to be used in the default Corrected Global Similarity Score.
	 * 
	 * @param factorA (Double).
	 * <p>
	 * <p>
	 * Default: 7.71
	 */
	public void factorA(Double factorA) {
		
		arrayMatcherConfig4[0] = factorA;
	   }	
	


	/**
	 * Returns the a list with the parameters for the matcherConfig4 parameters.
	 */
	public List<Double> getmatcherConfig4() {

		if (arrayMatcherConfig4[0] == null) {
			// Set the factor A (see article) to be used in the default Corrected Global Similarity Score:
			matcherConfig4.add(7.71);
		} else {
			matcherConfig4.add(arrayMatcherConfig4[0]);
		}

		return matcherConfig4;
	}

}
