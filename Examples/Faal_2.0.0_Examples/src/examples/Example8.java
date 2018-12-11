package examples;

import java.util.ArrayList;
import java.util.List;

import faal.*;

public class Example8 {

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
		// Set the personalised parameters for the IPA_Parser module.
		// The location of the new config files to compare the Punjabi tones needs to be
		// set here:
		
		settingsConfigIPA.pathPhonologicalFeatures("config_punjabi_tones/phon_features_punjabi_tones.txt");
		settingsConfigIPA.pathPhoneticDiacritics("config_punjabi_tones/phon_diacritics_punjabi_tones.txt");
		settingsConfigIPA.saliencesMatchesPhonCategories("config_punjabi_tones/saliences_to_use_matches_phon_categories_punjabi_tones.txt");

		// Initialise the configIPA variable:

		configIPA = settingsConfigIPA.getConfigIPA();

		// ==================
		// Config 1

		// Set the MatcherConfig1 personalised parameters for the Matcher module.
		// Only one parameter is modified - the alignments found by the algorithm will
		// not be printed in the console.
		// For all of the other parameters, the default values are used:

		settingsMatcherConfig1.printResults(false);

		// Initialise the matcherConfig1 variable:

		matcherConfig1 = settingsMatcherConfig1.getmatcherConfig1();

		// ==================
		// Config 2

		// Set the MatcherConfig2 personalised parameters for the Matcher module.

		// No parameter is modified, and the matcherConfig2 variable is initialized with
		// default values are used:

		matcherConfig2 = settingsMatcherConfig2.getmatcherConfig2();

		// ==================
		// Config 3

		// Set the MatcherConfig3 personalised parameters for the Matcher module.
		// Two parameters need to be modified, so that the algorithm points to the new
		// files.
		// For all of the other parameters, the default values are used:

		settingsMatcherConfig3.pathPhonologicalFeatures("config_punjabi_tones/phon_features_punjabi_tones.txt");
		settingsMatcherConfig3.pathPhoneticDiacritics("config_punjabi_tones/phon_diacritics_punjabi_tones.txt");

		// Initialise the matcherConfig3 variable:

		matcherConfig3 = settingsMatcherConfig3.getmatcherConfig3();

		// ==================
		// Config 4

		// Set the MatcherConfig4 personalised parameters for the Matcher module.

		// No parameter is modified, and the matcherConfig4 variable is initialized with
		// default values are used:

		matcherConfig4 = settingsMatcherConfig4.getmatcherConfig4();

		// ==================
		// Settings concerning the use of an external function for the calculation of
		// the Corrected Global Similarity Score
		// Since no external function is used, the variable is set to 0

		Integer optionExternalFunction = 0;

		// ==================
		// Create a variable to store the results

		List<Alignment> resultsAlignments = new ArrayList<>();

		// ==================
		// Create a variable to store the words to be aligned

		String word1 = "";
		String word2 = "";

		// ==================
		// ------------------------------
		// ==================

		System.out.println("=== Hindi: gʰoːɽɑː ~ Punjabi k꜓oɽaː ===");

		word1 = "gʰoːɽɑː";
		word2 = "k꜓oɽaː";
		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);
		PrintAlignment(resultsAlignments);

		System.out.println();

		// -------

		System.out.println("=== Hindi: gʰoːɽɑː ~ Punjabi ko˨ɽaː ===");

		// prepare new alignment
		word1 = "gʰoːɽɑː";
		word2 = "ko˨ɽaː";

		// align words
		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);

		// print alignment with the best score (see below module PrintAlignment)
		PrintAlignment(resultsAlignments);

		System.out.println();

		// -------

		System.out.println("=== Hindi: gʰoːɽɑː ~ Punjabi koɽaː ===");

		word1 = "gʰoːɽɑː";
		word2 = "koɽaː";
		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);
		PrintAlignment(resultsAlignments);

		System.out.println();

		// ==============

		System.out.println("=== Hindi: koːɽʰi ~ Punjabi k꜓oɽaː ===");

		word1 = "koːɽʰi";
		word2 = "k꜓oɽaː";
		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);
		PrintAlignment(resultsAlignments);

		System.out.println();

		// -------

		System.out.println("=== Hindi: koːɽʰi ~ Punjabi ko˨ɽaː ===");

		word1 = "koːɽʰi";
		word2 = "ko˨ɽaː";
		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);
		PrintAlignment(resultsAlignments);

		System.out.println();

		// -------

		System.out.println("=== Hindi: koːɽʰi ~ Punjabi koɽaː ===");

		word1 = "koːɽʰi";
		word2 = "koɽaː";
		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);
		PrintAlignment(resultsAlignments);

		System.out.println();

		// ==============

		System.out.println("=== Hindi: koːɽɑː ~ Punjabi k꜓oɽaː ===");

		word1 = "koːɽɑː";
		word2 = "k꜓oɽaː";
		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);
		PrintAlignment(resultsAlignments);

		System.out.println();

		// -------

		System.out.println("=== Hindi: koːɽɑː ~ Punjabi ko˨ɽaː ===");

		word1 = "koːɽɑː";
		word2 = "ko˨ɽaː";
		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);
		PrintAlignment(resultsAlignments);

		System.out.println();

		// -------

		System.out.println("=== Hindi: koːɽɑː ~ Punjabi koɽaː ===");

		word1 = "koːɽɑː";
		word2 = "koɽaː";
		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);
		PrintAlignment(resultsAlignments);
	}

	// Module to print the results of the various alignments

	public static void PrintAlignment(List<Alignment> resultsAlignments) {

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
