package examples;

import java.util.List;
import faal.*;

public class Example5 {

	public static void main(String[] args) {

		List<Alignment> resultsAlignments = FAAL.align("ɒlɪgəʊ̯", "ɒlɪg￤ɑːki", false);

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

}