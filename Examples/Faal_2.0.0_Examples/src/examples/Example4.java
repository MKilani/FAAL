package examples;

import java.util.ArrayList;
import java.util.List;

import faal.*;

public class Example4 {

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
		// Set the personalised parameters for the IPA_Parser module:

		settingsConfigIPA.pathPhonologicalFeatures("config/phon_features.txt");
		settingsConfigIPA.pathPhoneticDiacritics("config/phon_diacritics.txt");
		settingsConfigIPA.pathFeaturesDiphthongs("config/features_diphthongs.txt");
		settingsConfigIPA.pathFeaturesCoarticulation("config/features_coarticulation.txt");
		settingsConfigIPA.pathPhonCategories("config/phon_categories.txt");
		settingsConfigIPA.saliencesMatchesPhonCategories("config/saliences_to_use_matches_phon_categories.txt");

		// Initialise the configIPA variable:

		configIPA = settingsConfigIPA.getConfigIPA();

		// ==================
		// Config 1

		// Set the MatcherConfig1 personalised parameters for the Matcher module:

		settingsMatcherConfig1.printResults(false); // by default, this value is true
		settingsMatcherConfig1.minimumLimitFeatures(false);
		settingsMatcherConfig1.autoDetectMorphBound(true);
		settingsMatcherConfig1.excludeSemiconsonants(false);
		settingsMatcherConfig1.excludeVowels(false);
		settingsMatcherConfig1.excludeNonInitialVowels(false);
		settingsMatcherConfig1.displayBoundaries(true);
		settingsMatcherConfig1.matchOnlySameCategory(true);

		// Initialise the matcherConfig1 variable:

		matcherConfig1 = settingsMatcherConfig1.getmatcherConfig1();

		// ==================
		// Config 2

		// Set the MatcherConfig2 personalised parameters for the Matcher module:

		settingsMatcherConfig2.similarityScore(0);
		settingsMatcherConfig2.limitFeaturesConsonants(23);
		settingsMatcherConfig2.limitFeaturesVowels(0);
		settingsMatcherConfig2.trialsWithMorphemicBoundaries(6);
		settingsMatcherConfig2.trialsWithoutMorphemicBoundaries(6);

		// Initialise the matcherConfig1 variable:

		matcherConfig2 = settingsMatcherConfig2.getmatcherConfig2();

		// ==================
		// Config 3

		// Set the MatcherConfig3 personalised parameters for the Matcher module:

		settingsMatcherConfig3.pathPhonologicalFeatures("config/phon_features.txt");
		settingsMatcherConfig3.pathPhoneClass("config/phon_categories.txt");
		settingsMatcherConfig3.pathPhoneticDiacritics("config/phon_diacritics.txt");

		// Initialise the matcherConfig3 variable:

		matcherConfig3 = settingsMatcherConfig3.getmatcherConfig3();

		// ==================
		// Config 4

		// Set the MatcherConfig4 personalised parameters for the Matcher module:

		settingsMatcherConfig4.factorA(7.71);

		// Initialise the matcherConfig1 variable:

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

		System.out.println("=== Japanese hɐkːekʲːɯː ~ Korean pekçj͜ʌɭgu ===");
		System.out.println("~~ Without Morphemic Boundaries ~~");

		word1 = "hɐkːekʲːɯː";
		word2 = "pekçj͜ʌɭgu";

		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);
		PrintAlignment(resultsAlignments);

		System.out.println();

		// -------

		System.out.println("=== Japanese hɐ￤kːe￤kʲːɯː ~ Korean pek￤çj͜ʌɭ￤gu ===");
		System.out.println("~~ Without Morphemic Boundaries ~~");

		word1 = "hɐ￤kːe￤kʲːɯː";
		word2 = "pek￤çj͜ʌɭ￤gu";

		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);
		PrintAlignment(resultsAlignments);

		System.out.println();

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