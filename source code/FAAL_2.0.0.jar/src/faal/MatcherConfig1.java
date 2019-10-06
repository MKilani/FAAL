package faal;

import java.util.ArrayList;
import java.util.List;

public class MatcherConfig1 {

	Boolean[] arrayMatcherConfig1 = new Boolean[8];
	List<Boolean> matcherConfig1 = new ArrayList<Boolean>();

	/**
	 * Print results.
	 * 
	 * @param printResults
	 *            (Boolean).
	 *            <p>
	 *            <p>
	 *            Default: true
	 */
	public void printResults(Boolean printResults) {

		arrayMatcherConfig1[0] = printResults;
	}

	/**
	 * Introduce a minimum limit of features that need to be attested in order to
	 * count the match.
	 * 
	 * @param minimumLimitFeatures
	 *            (Boolean).
	 *            <p>
	 *            <p>
	 *            Default: false
	 */
	public void minimumLimitFeatures(Boolean minimumLimitFeatures) {

		arrayMatcherConfig1[1] = minimumLimitFeatures;
	}

	/**
	 * Automatically detect morphemic boundaries and automatically adapting
	 * Global/Corrected Global Similarity Score used.
	 * 
	 * @param autoDetectMorphBound
	 *            (Boolean).
	 *            <p>
	 *            <p>
	 *            Default: true
	 */
	public void autoDetectMorphBound(Boolean autoDetectMorphBound) {

		arrayMatcherConfig1[2] = autoDetectMorphBound;
	}

	/**
	 * If semiconsonants do not match, they are not counted.
	 * 
	 * @param excludeSemicons
	 *            (Boolean).
	 *            <p>
	 *            <p>
	 *            Default: false
	 */
	public void excludeSemiconsonants(Boolean excludeSemicons) {

		arrayMatcherConfig1[3] = excludeSemicons;
	}

	/**
	 * If vowels do not match, they are not counted.
	 * 
	 * @param excludeVows
	 *            (Boolean).
	 *            <p>
	 *            <p>
	 *            Default: false
	 */
	public void excludeVowels(Boolean excludeVows) {

		arrayMatcherConfig1[4] = excludeVows;
	}

	/**
	 * If vowels do not match, they are not counted, except if they are at the
	 * beginning of a word.
	 * 
	 * @param excludeNonInitialVows
	 *            (Boolean).
	 *            <p>
	 *            <p>
	 *            Default: false
	 */
	public void excludeNonInitialVowels(Boolean excludeNonInitialVows) {

		arrayMatcherConfig1[5] = excludeNonInitialVows;
	}

	/**
	 * Display automatically recognized boundaries.
	 * 
	 * @param displayBoundaries
	 *            (Boolean).
	 *            <p>
	 *            <p>
	 *            Default: true
	 */
	public void displayBoundaries(Boolean displayBoundaries) {

		arrayMatcherConfig1[6] = displayBoundaries;
	}

	/**
	 * Match only phonemes belonging to the same category (consonants, vowels,
	 * semiconsonants).
	 * 
	 * @param matchOnlySameCategory
	 *            (Boolean).
	 *            <p>
	 *            <p>
	 *            Default: true
	 */
	public void matchOnlySameCategory(Boolean matchOnlySameCategory) {

		arrayMatcherConfig1[7] = matchOnlySameCategory;
	}

	/**
	 * Returns the a list with the parameters for the matcherConfig1 parameters.
	 */
	public List<Boolean> getmatcherConfig1() {

		if (arrayMatcherConfig1[0] == null) {
			// print results:
			matcherConfig1.add(true);
		} else {
			matcherConfig1.add(arrayMatcherConfig1[0]);
		}

		if (arrayMatcherConfig1[1] == null) {
			// introducing a minimum limit of features that need to be attested in order to
			// count the match:
			matcherConfig1.add(false);
		} else {
			matcherConfig1.add(arrayMatcherConfig1[1]);
		}

		if (arrayMatcherConfig1[2] == null) {
			// automatically detecting morphemic boundaries and automatically adapting
			// Global/Corrected Global Similarity Score used:
			matcherConfig1.add(true);
		} else {
			matcherConfig1.add(arrayMatcherConfig1[2]);
		}

		if (arrayMatcherConfig1[3] == null) {
			// if semiconsonants do not match, they are not counted:
			matcherConfig1.add(false);
		} else {
			matcherConfig1.add(arrayMatcherConfig1[3]);
		}

		if (arrayMatcherConfig1[4] == null) {
			// if vowels do not match, they are not counted:
			matcherConfig1.add(false);
		} else {
			matcherConfig1.add(arrayMatcherConfig1[4]);
		}

		if (arrayMatcherConfig1[5] == null) {
			// if vowels do not match, they are not counted, except if they are at the
			// beginning of a word:
			matcherConfig1.add(false);
		} else {
			matcherConfig1.add(arrayMatcherConfig1[5]);
		}

		if (arrayMatcherConfig1[6] == null) {
			// display automatically recognized boundaries:
			matcherConfig1.add(true);
		} else {
			matcherConfig1.add(arrayMatcherConfig1[6]);
		}

		if (arrayMatcherConfig1[7] == null) {
			// Match only phonemes belonging to the same category (consonants, vowels,
			// semiconsonants):
			matcherConfig1.add(true);
		} else {
			matcherConfig1.add(arrayMatcherConfig1[7]);
		}

		return matcherConfig1;
	}

}
