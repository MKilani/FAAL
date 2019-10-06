package examples;

import java.util.ArrayList;
import java.util.List;
import faal.*;

public class Example6 {

	public static void main(String[] args) throws Exception {

		// Variables

		ConfigIPA settingsConfigIPA = new ConfigIPA();

		MatcherConfig1 settingsMatcherConfig1 = new MatcherConfig1();
		MatcherConfig2 settingsMatcherConfig2 = new MatcherConfig2();
		MatcherConfig3 settingsMatcherConfig3 = new MatcherConfig3();
		MatcherConfig4 settingsMatcherConfig4 = new MatcherConfig4();

		List<String> configIPA = new ArrayList<String>();

		List<Boolean> matcherConfig1 = new ArrayList<Boolean>();
		List<Integer> matcherConfig2 = new ArrayList<Integer>();
		List<String> matcherConfig3 = new ArrayList<String>();
		List<Double> matcherConfig4 = new ArrayList<Double>();

		// ==================
		// Set the and initialise the configIPA parameters for the IPA_Parser module.
		// The default values are used:

		configIPA = settingsConfigIPA.getConfigIPA();

		// ==================
		// Config 1

		// Set the and initialise the matcherConfig1 parameters for the Matcher module.
		// The default values are used, except for the printResult option:

		settingsMatcherConfig1.printResults(false);

		matcherConfig1 = settingsMatcherConfig1.getmatcherConfig1();

		// ==================
		// Config 2

		// Set the and initialise the matcherConfig2 parameters for the Matcher module.
		// The default values are used:

		matcherConfig2 = settingsMatcherConfig2.getmatcherConfig2();

		// ==================
		// Config 3

		// Set the and initialise the matcherConfig3 parameters for the Matcher module.
		// The default values are used:

		matcherConfig3 = settingsMatcherConfig3.getmatcherConfig3();

		// ==================
		// Config 4

		// Set the and initialise the matcherConfig4 parameters for the Matcher module.
		// The default values are used:

		matcherConfig4 = settingsMatcherConfig4.getmatcherConfig4();

		// ==================
		// Settings concerning the use of an external function for the calculation of
		// the Corrected Global Similarity Score
		// Since no external function is used, the variable optionExternalFunction is
		// set to 0
		// while the variable externalFunction is set to "".

		Integer optionExternalFunction = 0;
		String externalFunction = "";

		// ==================
		// Create a variable to store the results

		List<Alignment> resultsAlignments = new ArrayList<>();

		// ==================
		// Create a variable to store the words to be aligned

		String word1 = "kentum";
		String word2 = "hekaton";

		// ==================
		// ------------------------------
		// Call the IPA_Parser module

		ParsedWord stringParsed = new ParsedWord();
		stringParsed = IPA_Parser.parseIPA(word1, word2, configIPA);

		// Divide the results into dedicated variables

		String word1Parsed = (String) stringParsed.getParsedWord1();
		String word2Parsed = (String) stringParsed.getParsedWord2();

		String word1Unparsed = word1;
		String word2Unparsed = word2;

		int matrixResultComparison_WithSaliences[][] = stringParsed.getMatrixResultComparison_WithSaliences();
		int matrixResultComparison_WithoutSaliences[][] = stringParsed.getMatrixResultComparison_WithoutSaliences();

		// ==================
		// ------------------------------
		// Call the Matcher module

		resultsAlignments = Matcher.match(word1Parsed, word2Parsed, matrixResultComparison_WithSaliences,
				matrixResultComparison_WithoutSaliences, word1Unparsed, word2Unparsed, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4, optionExternalFunction, externalFunction);

		// ==================
		// ------------------------------
		// Print Results

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