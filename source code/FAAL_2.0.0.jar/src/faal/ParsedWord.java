package faal;

public class ParsedWord {

	String parsedWord1;
	String parsedWord2;
	int[][] matrixResultComparison_WithSaliences;
	int[][] matrixResultComparison_WithoutSaliences;

	/**
	 * Stores the parsed word 1.
	 * 
	 * @param word1
	 *            (String): parsed word 1.
	 */
	public void parsedWord1(String word1) {
		parsedWord1 = word1;
	}

	/**
	 * Stores the parsed word 2.
	 * 
	 * @param word2
	 *            (String): parsed word 2.
	 */
	public void parsedWord2(String word2) {
		parsedWord2 = word2;
	}

	/**
	 * Stores matrix with the numbers of features in common, modified according to
	 * the saliences.
	 * 
	 * @param matrixResultComparisonWithSaliences
	 *            (int[][]): matrix with the numbers of features in common, modified
	 *            according to the saliences.
	 */
	public void matrixResultComparison_WithSaliences(int[][] matrixResultComparisonWithSaliences) {
		matrixResultComparison_WithSaliences = matrixResultComparisonWithSaliences;
	}

	/**
	 * Stores matrix with the numbers of features in common, without modifications.
	 * 
	 * @param matrixResultComparisonWithoutSaliences
	 *            (int[][]): matrix with the numbers of features in common, without
	 *            modifications.
	 */
	public void matrixResultComparison_WithoutSaliences(int[][] matrixResultComparisonWithoutSaliences) {
		matrixResultComparison_WithoutSaliences = matrixResultComparisonWithoutSaliences;
	}

	// =====

	// get

	/**
	 * Returns the parsed word 1.
	 */
	public String getParsedWord1() {
		return parsedWord1;
	}

	/**
	 * Returns the parsed word 2.
	 */
	public String getParsedWord2() {
		return parsedWord2;
	}

	/**
	 * Returns the feature matrix corrected according to the salience settings. The
	 * dimensions of the matrix correspond to the length of the parsed (i.e. without
	 * diacritics etc.) transcription of word 1 × the length of the parsed (i.e.
	 * without diacritics etc.) transcription of word 2.
	 */
	public int[][] getMatrixResultComparison_WithSaliences() {
		return matrixResultComparison_WithSaliences;
	}

	/**
	 * Returns the feature matrix, without modifications for the saliences. The
	 * dimensions of the matrix correspond to the length of the parsed (i.e. without
	 * diacritics etc.) transcription of word 1 × the length of the parsed (i.e.
	 * without diacritics etc.) transcription of word 2.
	 */
	public int[][] getMatrixResultComparison_WithoutSaliences() {
		return matrixResultComparison_WithoutSaliences;
	}

}
