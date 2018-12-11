package examples;

import java.util.List;
import faal.*;

public class Example3 {

	public static void main(String[] args) {

		String word1 = "θiɣatɛra";
		String word2 = "doxtəɾ";

		// Alignment with default functions:

		List<Alignment> resultsAlignments = FAAL.align(word1, word2, false, 0);

		System.out.println("=== Default functions ===");
		PrintAlignmentDefault(resultsAlignments);

		// Calculate Global Similarity Score / Length longest word
		// Global Similarity Score function = ((SumFeat) / (NrFeat * 7.71))
		// Length longest word = LongestWord

		String newFunction1 = "((SumFeat) / (NrFeat * 7.71)) / LongestWord";

		List<Alignment> resultsAlignments1 = FAAL.align(word1, word2, false, 2, newFunction1);

		System.out.println("");
		System.out.println("=== Global Similarity Score / Length longest word ===");
		PrintAlignmentScoreLenght(resultsAlignments1);

		// Calculate Corrected Global Similarity Score / Length longest word
		// Corrected Global Similarity Score function = ((SumFeat) / (NrFeat * 7.71)) - ((LenSeq - ShortestWord)/1.04 + ((LenAlign - LenSeq)/LenSeq)) * (1-(ShortestWord/LongestWord))
		// Length longest word = LongestWord

		String newFunction2 = "(((SumFeat) / (NrFeat * 7.71)) - ((LenSeq - ShortestWord)/1.04 + ((LenAlign - LenSeq)/LenSeq)) * (1-(ShortestWord/LongestWord))) / LongestWord";

		List<Alignment> resultsAlignments2 = FAAL.align(word1, word2, false, 2, newFunction2);

		System.out.println("");
		System.out.println("=== Corrected Global Similarity Score / Length longest word ===");
		PrintAlignmentScoreLenght(resultsAlignments2);
	}

	// Module to print the results of the alignment with default functions

	public static void PrintAlignmentDefault(List<Alignment> resultsAlignments) {

		System.out.println("=== Best Alignment ===");
		System.out.println(resultsAlignments.get(0).getWord1_WithDiacritics());
		System.out.println(resultsAlignments.get(0).getWord2_WithDiacritics());
		System.out.println(resultsAlignments.get(0).getWord1_WithoutDiacritics());
		System.out.println(resultsAlignments.get(0).getWord2_WithoutDiacritics());
		System.out.println("Global Similarity Score: " + resultsAlignments.get(0).getGlobalSimilarityScore());
		System.out.println("Corrected Global Similarity Score: " + resultsAlignments.get(0).getCorrectedGlobalSimilarityScore());
		System.out.println(resultsAlignments.get(0).getPhoneticPairs());
		System.out.println(resultsAlignments.get(0).getNrAttestationsPhoneticPairs());

	}

	// Module to print the results of the alignment Similarity Score / Length

	public static void PrintAlignmentScoreLenght(List<Alignment> resultsAlignments) {

		System.out.println("=== Best Alignment ===");
		System.out.println(resultsAlignments.get(0).getWord1_WithDiacritics());
		System.out.println(resultsAlignments.get(0).getWord2_WithDiacritics());
		System.out.println(resultsAlignments.get(0).getWord1_WithoutDiacritics());
		System.out.println(resultsAlignments.get(0).getWord2_WithoutDiacritics());
		System.out.println("Global Similarity Score: " + resultsAlignments.get(0).getGlobalSimilarityScore());
		System.out.println("New function Score / Length: " + resultsAlignments.get(0).getCorrectedGlobalSimilarityScore());
		System.out.println(resultsAlignments.get(0).getPhoneticPairs());
		System.out.println(resultsAlignments.get(0).getNrAttestationsPhoneticPairs());

	}
}