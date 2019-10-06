package faal;

import java.util.List;

public class Alignment {

	String word1_WithDiacritics;
	String word2_WithDiacritics;
	String word1_WithoutDiacritics;
	String word2_WithoutDiacritics;
	Double globalSimilarityScore;
	Double correctedGlobalSimilarityScore;
	List<String> phoneticPairs;
	List<Integer> nrAttestationsPhoneticPairs;
	
	

	/**
	 * Stores the aligned word 1, with diacritics.
	 * 
	 * @param alignmentWord1_WithDiacritics
	 *            (String): aligned word 1, with diacritics.
	 */
	public void word1_WithDiacritics(String alignmentWord1_WithDiacritics) {
		
		 word1_WithDiacritics = alignmentWord1_WithDiacritics;
	   }
	
	/**
	 * Stores the aligned word 2, with diacritics.
	 * 
	 * @param alignmentWord2_WithDiacritics
	 *            (String): aligned word 2, with diacritics.
	 */
	public void word2_WithDiacritics(String alignmentWord2_WithDiacritics) {
		
		word2_WithDiacritics = alignmentWord2_WithDiacritics;
	   }
	
	
	/**
	 * Stores the aligned word 1, without diacritics.
	 * 
	 * @param alignmentWord1_WithoutDiacritics
	 *            (String): aligned word 1, without diacritics.
	 */
	public void word1_WithoutDiacritics(String alignmentWord1_WithoutDiacritics) {
		
		word1_WithoutDiacritics = alignmentWord1_WithoutDiacritics;
	   }
	
	
	/**
	 * Stores the aligned word 2, without diacritics.
	 * 
	 * @param alignmentWord2_WithoutDiacritics
	 *            (String): aligned word 2, without diacritics.
	 */
	public void word2_WithoutDiacritics(String alignmentWord2_WithoutDiacritics) {
		
		word2_WithoutDiacritics = alignmentWord2_WithoutDiacritics;
	   }
	
	
	
	/**
	 * Stores the Global Similarity Score.
	 * 
	 * @param alignmentGlobalSimilarityScore
	 *            (Double): Global Similarity Score.
	 */
	public void globalSimilarityScore(Double alignmentGlobalSimilarityScore) {
		
		globalSimilarityScore = alignmentGlobalSimilarityScore;
	   }
	
	/**
	 * Stores the Corrected Global Similarity Score.
	 * 
	 * @param alignmentCorrectedGlobalSimilarityScore
	 *            (Double): Corrected Global Similarity Score.
	 */
	public void correctedGlobalSimilarityScore(Double alignmentCorrectedGlobalSimilarityScore) {
		
		correctedGlobalSimilarityScore = alignmentCorrectedGlobalSimilarityScore;
	   }
	
	/**
	 * Stores the list of phonetic pairs attested in the alignment.
	 * 
	 * @param alignmentPhoneticPairs
	 *            List<String>: Attested Phonetic Pairs.
	 */
	public void phoneticPairs(List<String> alignmentPhoneticPairs) {
		
		phoneticPairs = alignmentPhoneticPairs;
	   }

	/**
	 * Stores the numbers of attestations of the phonetic pairs identified by the algorithm.
	 * 
	 * @param alignmentNrAttestationsPhoneticPairs
	 *            List<Integer>: numbers of attestations of the phonetic pairs.
	 */
	public void nrAttestationsPhoneticPairs(List<Integer> alignmentNrAttestationsPhoneticPairs) {
		
		nrAttestationsPhoneticPairs = alignmentNrAttestationsPhoneticPairs;
	   }

	//get
	
	/**
	 * Returns the the aligned word 1, with diacritics.
	 */
	public String getWord1_WithDiacritics(){
	    return word1_WithDiacritics;
	}
	
	/**
	 * Returns the the aligned word 2, with diacritics.
	 */
	public String getWord2_WithDiacritics(){
	    return word2_WithDiacritics;
	}
	
	/**
	 * Returns the the aligned word 1, without diacritics.
	 */
	public String getWord1_WithoutDiacritics(){
	    return word1_WithoutDiacritics;
	}
	
	/**
	 * Returns the the aligned word 2, without diacritics.
	 */
	public String getWord2_WithoutDiacritics(){
	    return word2_WithoutDiacritics;
	}
	
	/**
	 * Returns the Global Similarity Score.
	 */
	public Double getGlobalSimilarityScore(){
	    return globalSimilarityScore;
	}
	
	/**
	 * Returns the Corrected Global Similarity Score.
	 */
	public Double getCorrectedGlobalSimilarityScore(){
	    return correctedGlobalSimilarityScore;
	}
	
	/**
	 * Returns the list of phonetic pairs attested within the alignment. Each item on the list corresponds to a phonetic pair, and it is stored as a string with the following syntax: "phoneme_A - phoneme_B"
	 */
	public List<String> getPhoneticPairs(){
	    return phoneticPairs;
	}
	
	/**
	 * Returns the numbers of attestations for each phonetic pairs identified by the algorithm.
	 */
	public List<Integer> getNrAttestationsPhoneticPairs(){
	    return nrAttestationsPhoneticPairs;
	}


}
