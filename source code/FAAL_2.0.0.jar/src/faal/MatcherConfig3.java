package faal;


import java.util.ArrayList;
import java.util.List;

public class MatcherConfig3 {

	String[] arrayMatcherConfig3 = new String[3];
	List<String> matcherConfig3 = new ArrayList<String>();

	/**
	 * Stores the path of the file <i>phon_features.txt</i>.
	 * 
	 * @param pathPhonologicalFeatures (String).
	 * <p>
	 * <p>
	 * Default: "config/phon_features.txt"
	 */
	public void pathPhonologicalFeatures(String pathPhonologicalFeatures) {
		
		arrayMatcherConfig3[0] = pathPhonologicalFeatures;
	   }	
	
	/**
	 * Stores the path of the file <i>phon_categories.txt</i>.
	 * 
	 * @param pathPhoneClass (String).
	 * <p>
	 * <p>
	 * Default: "config/phon_categories.txt"
	 */
	public void pathPhoneClass(String pathPhoneClass) {
		
		arrayMatcherConfig3[1] = pathPhoneClass;
	   }	
	
	/**
	 * Stores the path of the file <i>phon_diacritics.txt</i>.
	 * 
	 * @param pathPhoneClass (String).
	 * <p>
	 * <p>
	 * Default: "config/phon_diacritics.txt"
	 */
	public void pathPhoneticDiacritics(String pathPhoneticDiacritics) {
		
		arrayMatcherConfig3[2] = pathPhoneticDiacritics;
	   }


	/**
	 * Returns the a list with the parameters for the matcherConfig3 parameters.
	 */
	public List<String> getmatcherConfig3() {

		if (arrayMatcherConfig3[0] == null) {
			// location of the config file with list of phonological features
			matcherConfig3.add("config/phon_features.txt");
		} else {
			matcherConfig3.add(arrayMatcherConfig3[0]);
		}

		if (arrayMatcherConfig3[1] == null) {
			// location of the config file with phonological categories - only the first category is relevant
			matcherConfig3.add("config/phon_categories.txt");
		} else {
			matcherConfig3.add(arrayMatcherConfig3[1]);
		}
		
		if (arrayMatcherConfig3[2] == null) {
			// location of the file with the diacritics;
			matcherConfig3.add("config/phon_diacritics.txt");
		} else {
			matcherConfig3.add(arrayMatcherConfig3[2]);
		}


		return matcherConfig3;
	}

}
