package faal;



import java.util.ArrayList;
import java.util.List;

public class MatcherConfig2 {

	Integer[] arrayMatcherConfig2 = new Integer[5];
	List<Integer> matcherConfig2 = new ArrayList<Integer>();

	/**
	 * Select the Similarity Score to be used.
	 * 
	 * @param similarityScore (Integer):
	 * 							0 = use globalSimilaryScore <p>
	 * 							1 = use correctedGlobalSimilaryScore <p><p>
	 * 							if morphemic boundaries are present, better 0 ; if no morphemic boundary is present, better 1.
	 *            <p>
	 *            <p>
	 *            Default: 0
	 */
	public void similarityScore(Integer similarityScore) {

		arrayMatcherConfig2[0] = similarityScore;
	}

	/**
	 * Set limit of features in common for consonants under which the features are not significant and not counted any more.
	 * 
	 * @param limitFeaturesCons (Integer)
	 *            <p>
	 *            <p>
	 *            Default: 23
	 */
	public void limitFeaturesConsonants(Integer limitFeaturesCons) {

		arrayMatcherConfig2[1] = limitFeaturesCons;
	}	
	
	/**
	 * Set limit of features in common for vowels under which the features are not significant and not counted any more.
	 * 
	 * @param limitFeaturesVows (Integer)
	 *            <p>
	 *            <p>
	 *            Default: 0
	 */
	public void limitFeaturesVowels(Integer limitFeaturesVows) {

		arrayMatcherConfig2[2] = limitFeaturesVows;
	}
	
	/**
	 * Set number of trials when morphemic boundaries are present.
	 * 
	 * @param trialsWithMorphBound (Integer)
	 *            <p>
	 *            <p>
	 *            Default: 6
	 */
	public void trialsWithMorphemicBoundaries(Integer trialsWithMorphBound) {

		arrayMatcherConfig2[3] = trialsWithMorphBound;
	}
	
	/**
	 * Set number of trials when morphemic boundaries are not present.
	 * 
	 * @param trialsWithoutMorphBound (Integer)
	 *            <p>
	 *            <p>
	 *            Default: 6
	 */
	public void trialsWithoutMorphemicBoundaries(Integer trialsWithoutMorphBound) {

		arrayMatcherConfig2[4] = trialsWithoutMorphBound;
	}
	


	/**
	 * Returns the a list with the parameters for the matcherConfig2 parameters.
	 */
	public List<Integer> getmatcherConfig2() {

		if (arrayMatcherConfig2[0] == null) {
			// Select the Similarity Score to be used: 0 = use
			// globalSimilaryScore, 1 = use correctedGlobalSimilaryScore (-> if morphemic
			// boundaries are present, better 0 ; if no morphemic boundary is present,
			// better 1)
			matcherConfig2.add(0);
		} else {
			matcherConfig2.add(arrayMatcherConfig2[0]);
		}

		if (arrayMatcherConfig2[1] == null) {
			// Set limit of features in common for cons under which the features are not
			// significant and not counted any more
			matcherConfig2.add(23);
		} else {
			matcherConfig2.add(arrayMatcherConfig2[1]);
		}	

		if (arrayMatcherConfig2[2] == null) {
			// Set limit of features in common for vowels under which the features are not
			// significant and not counted any more
			matcherConfig2.add(0);
		} else {
			matcherConfig2.add(arrayMatcherConfig2[2]);
		}
		
		if (arrayMatcherConfig2[3] == null) {
			// nr of trials with morphemic boundaries
			matcherConfig2.add(6);
		} else {
			matcherConfig2.add(arrayMatcherConfig2[3]);
		}
		
		if (arrayMatcherConfig2[4] == null) {
			// nr of trials without morphemic boundary
			matcherConfig2.add(6);
		} else {
			matcherConfig2.add(arrayMatcherConfig2[4]);
		}		
		

		return matcherConfig2;
	}

}
