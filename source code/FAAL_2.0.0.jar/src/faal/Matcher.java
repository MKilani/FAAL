/* 
 * Copyright 2018 Marwan Kilani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package faal;

import java.util.ArrayList;
import java.util.List;

import exp4j.Expression;
import exp4j.ExpressionBuilder;

public class Matcher {

	/**
	 * Method corresponding to the Matcher module. It parses one pair of words at a
	 * time, using default values (see online documentation for their values). It
	 * returns an array list of possible alignments organized according to their
	 * Global Similarity Score.
	 * 
	 * @param word1Parsed
	 *            (String): parsed (i.e. without diacritics etc) transcription of
	 *            word A (returned by IPA_parser.IPA_parser_new - output 0).
	 * @param word2Parsed
	 *            (String): parsed (i.e. without diacritics etc) transcription of
	 *            word B (returned by IPA_parser.IPA_parser_new - output 1).
	 * @param matrixResultComparison
	 *            (int[][]): matching features matrix corrected according to the
	 *            salience settings (returned by IPA_parser.IPA_parser_new - output
	 *            2).
	 * @param matrixResultComparisonOriginal
	 *            (int[][]): basic matching features matrix with- out any salience
	 *            corrections (returned by IPA_parser.IPA_parser_new - output 3).
	 * @param word1Unparsed
	 *            (String): IPA transcription of the first word to be aligned.
	 * @param word2Unparsed
	 *            (String): IPA transcription of the second word to be aligned.
	 * @return Result output: List&lt;Alignment&gt;
	 *         <p>
	 *         <p>
	 *         Each <i>Alignment</i > item within the List corresponds to an
	 *         alignment obtained. They are organized according to their Global or Corrected
	 *         Global Similarity score (see documentation online). 
	 *         The items stored in the <i>Alignment</i > class can be called as follow:
	 *         <p>
	 *         <p>
	 *         0. .getWord1_WithDiacritics() - String: Returns the aligned word 1, with diacritics.
	 *         <p>
	 *         1. .getWord2_WithDiacritics() - String: Returns the aligned word 2, with diacritics.
	 *         <p>
	 *         2. .getWord1_WithoutDiacritics() - String: Returns the aligned word 1, without diacritics.
	 *         <p>
	 *         3. .getWord2_WithoutDiacritics() - String: Returns the aligned word 2, without diacritics.
	 *         <p>
	 *         4. .getGlobalSimilarityScore() - Double: Returns the Global Similarity Score.
	 *         <p>
	 *         5. .getCorrectedGlobalSimilarityScore() - Double: Returns the Corrected Global Similarity Score.
	 *         <p>
	 *         6. .getPhoneticPairs() - List&lt;String&gt;: Returns the list of phonetic pairs attested within the
	 *         alignment. Each item on the list corresponds to a phonetic pair, and
	 *         it is stored as a string with the following syntax: "phoneme_A -
	 *         phoneme_B"
	 *         <p>
	 *         7. .getNrAttestationsPhoneticPairs() - List&lt;Integer&gt;: Returns the number of attestations within the alignment
	 *         for each phonetic pair of point 6. here above.
	 *         <p>
	 */
	public static List<Alignment> match(String word1Parsed, String word2Parsed, int[][] matrixResultComparison,
			int[][] matrixResultComparisonOriginal, String word1Unparsed, String word2Unparsed) {

		List<Alignment> results = new ArrayList<>();
		List<Boolean> matcherConfig1 = new ArrayList<Boolean>();
		List<Integer> matcherConfig2 = new ArrayList<Integer>();
		List<String> matcherConfig3 = new ArrayList<String>();
		List<Double> matcherConfig4 = new ArrayList<Double>();
		
		MatcherConfig1 settingsMatcherConfig1 = new MatcherConfig1();
		MatcherConfig2 settingsMatcherConfig2 = new MatcherConfig2();
		MatcherConfig3 settingsMatcherConfig3 = new MatcherConfig3();
		MatcherConfig4 settingsMatcherConfig4 = new MatcherConfig4();

		// Configs files:
		
		matcherConfig1 = settingsMatcherConfig1.getmatcherConfig1();
		matcherConfig2 = settingsMatcherConfig2.getmatcherConfig2();
		matcherConfig3 = settingsMatcherConfig3.getmatcherConfig3();
		matcherConfig4 = settingsMatcherConfig4.getmatcherConfig4();

		// Parameters concerning the use of an external Corrected Global Similarity
		// Score function

		// use of an external function: 0 = use the default function/do not use any
		// external function; 1 = use function from config file; 2 = use function from
		// argument
		Integer optionFunction = 0;

		// Since no function is passed as argument, the parameter externalFunction is
		// "".
		String externalFunction = "";

		results = match(word1Parsed, word2Parsed, matrixResultComparison, matrixResultComparisonOriginal, word1Unparsed,
				word2Unparsed, matcherConfig1, matcherConfig2, matcherConfig3, matcherConfig4, optionFunction,
				externalFunction);

		return results;

	}
	

	// =========================
	// =========================
	// =========================

	/**
	 * Method corresponding to the Matcher module. It parses one pair of words at a
	 * time, using personalized values (see online documentation for detailed
	 * description). It returns an array list of possible alignments organized
	 * according to their Global or Corrected Global Similarity Score, or according
	 * to an external function.
	 * 
	 * @param word1Parsed
	 *            (String): parsed (i.e. without diacritics etc) transcription of
	 *            word A (returned by IPA_parser.IPA_parser_new - output 0).
	 * @param word2Parsed
	 *            (String): parsed (i.e. without diacritics etc) transcription of
	 *            word B (returned by IPA_parser.IPA_parser_new - output 1).
	 * @param matrixResultComparison
	 *            (int[][]): matching features matrix corrected according to the
	 *            salience settings (returned by IPA_parser.IPA_parser_new - output
	 *            2).
	 * @param matrixResultComparisonOriginal
	 *            (int[][]): basic matching features matrix with- out any salience
	 *            corrections (returned by IPA_parser.IPA_parser_new - output 3).
	 * @param word1Unparsed
	 *            (String): IPA transcription of the first word to be aligned.
	 * @param word2Unparsed
	 *            (String): IPA transcription of the second word to be aligned.
	 * @param matcherConfig1
	 *            (List&lt;Boolean&gt;): Boolean array list with variables for the
	 *            configuration of the FAAL.faal and Matcher.Matcher_new modules.
	 *            Instances of this list can be initialized through the
	 *            <i>MatcherConfig1</i> class - see the corresponding JavaDoc, and
	 *            the documentation and examples online.
	 * @param matcherConfig2
	 *            (List&lt;Integer&gt;): Integer array list with variables for the
	 *            configuration of the FAAL.faal and Matcher.Matcher_new modules.
	 *            Instances of this list can be initialized through the
	 *            <i>MatcherConfig2</i> class - see the corresponding JavaDoc, and
	 *            the documentation and examples online.
	 * @param matcherConfig3
	 *            (List&lt;String&gt;): String array list with variables for the
	 *            configuration of the FAAL.faal and Matcher.Matcher_new modules.
	 *            Instances of this list can be initialized through the
	 *            <i>MatcherConfig3</i> class - see the corresponding JavaDoc, and
	 *            the documentation and examples online.
	 * @param matcherConfig4
	 *            (List&lt;Double&gt;): Double array list storing the factors used
	 *            in the calculation of the Corrected Global Similarity score. It is
	 *            used in the configuration of the FAAL.faal and Matcher.Matcher_new
	 *            modules. Instances of this list can be initialized through the
	 *            <i>MatcherConfig4</i> class - see the corresponding JavaDoc, and
	 *            the documentation and examples online.
	 * @param optionFunction
	 *            (Integer): select if using or not an external function - 0 = do
	 *            not use external function; 1 = use external function from config.
	 *            folder; 2 = use function provided as next argument
	 * @param externalFunction
	 *            (String): alternative function to use for the calculation of the
	 *            Corrected Global Similarity Score. See readme.md and
	 *            config/external_corr_glob_sim_score_function.txt for details about
	 *            the options and syntax of the formula.
	 * 
	 * @return Result output: List&lt;Alignment&gt;
	 *         <p>
	 *         <p>
	 *         Each <i>Alignment</i > item within the List corresponds to an
	 *         alignment obtained. They are organized according to their Global or Corrected
	 *         Global Similarity score (see documentation online). 
	 *         The items stored in the <i>Alignment</i > class can be called as follow:
	 *         <p>
	 *         <p>
	 *         0. .getWord1_WithDiacritics() - String: Returns the aligned word 1, with diacritics.
	 *         <p>
	 *         1. .getWord2_WithDiacritics() - String: Returns the aligned word 2, with diacritics.
	 *         <p>
	 *         2. .getWord1_WithoutDiacritics() - String: Returns the aligned word 1, without diacritics.
	 *         <p>
	 *         3. .getWord2_WithoutDiacritics() - String: Returns the aligned word 2, without diacritics.
	 *         <p>
	 *         4. .getGlobalSimilarityScore() - Double: Returns the Global Similarity Score.
	 *         <p>
	 *         5. .getCorrectedGlobalSimilarityScore() - Double: Returns the Corrected Global Similarity Score.
	 *         <p>
	 *         6. .getPhoneticPairs() - List&lt;String&gt;: Returns the list of phonetic pairs attested within the
	 *         alignment. Each item on the list corresponds to a phonetic pair, and
	 *         it is stored as a string with the following syntax: "phoneme_A -
	 *         phoneme_B"
	 *         <p>
	 *         7. .getNrAttestationsPhoneticPairs() - List&lt;Integer&gt;: Returns the number of attestations within the alignment
	 *         for each phonetic pair of point 6. here above.
	 *         <p>
	 */
	public static List<Alignment> match(String word1Parsed, String word2Parsed, int[][] matrixResultComparison,
			int[][] matrixResultComparisonOriginal, String word1Unparsed, String word2Unparsed,
			List<Boolean> matcherConfig1, List<Integer> matcherConfig2, List<String> matcherConfig3,
			List<Double> matcherConfig4, Integer optionFunction, String externalFunction) {

		List<List<String>> indexPhoneticPairs = new ArrayList<>();
		List<List<Integer>> indexPhoneticPairsValue = new ArrayList<>();

		List<List<String>> attestedPhoneticPairs = new ArrayList<>();
		List<List<Integer>> nrAttestationsPhoneticPairs = new ArrayList<>();

		String word1 = word1Parsed;
		String word2 = word2Parsed;
		

		// read config 1

		boolean printResults = matcherConfig1.get(0);
		boolean limitMinimumFeaturesConsonants = matcherConfig1.get(1);
		boolean limitMinimumFeaturesVowels = matcherConfig1.get(1);
		boolean autodetectMorphologicalBoundaries = matcherConfig1.get(2);
		boolean ignoreSemiconsonantMismatches = matcherConfig1.get(3);
		boolean ignoreVowelsMismatches = matcherConfig1.get(4);
		boolean ignoreVowelsMismatchesExceptFirst = matcherConfig1.get(5);
		boolean displayAutomaticallyRecognizedMorphologicalBoundaries = matcherConfig1.get(6);
		boolean matchesOnlyPhonesSameCategory = matcherConfig1.get(7);

		// read config 2

		int selectSimilarityScore = matcherConfig2.get(0); // if 0 = use globalSimilaryScore, if 1 = use
															// correctedGlobalSimilaryScore
		int limitSignificanceConsonants = matcherConfig2.get(1);
		int limitSignificanceVowels = matcherConfig2.get(2);
		int nrTrialsWithMorphemicBoundaries = matcherConfig2.get(3);
		int nrTrialsWithoutMorphemicBoundaries = matcherConfig2.get(4);

		// read config 3

		String locationFileFeatures = matcherConfig3.get(0);// phon_features.txt
		String locationFileCategoriesPhones = matcherConfig3.get(1);// "cons_vows_semi.txt";
		String locationFilePhoneticDiacritics = matcherConfig3.get(2);// "phon_diacritics.txt";

		// read config 4

		List<Double> factors = new ArrayList<>();

		for (int z = 0; z < matcherConfig4.size(); z++) {

			factors.add(matcherConfig4.get(0));

		}

		
		// ----------

		// settings - varia

		if (ignoreVowelsMismatchesExceptFirst == true) {
			ignoreVowelsMismatches = false;
		}

		String line1 = "";
		String line2 = "";

		int nrFeatures = 0;

		List<int[]> indexesMatchingSequence = new ArrayList<>();
		List<int[]> indexesMatchingSequenceOriginal = new ArrayList<>();

		List<int[]> indexesMorphemicBoundaries = new ArrayList<>();
		List<int[]> indexesMorphemicBoundariesOriginal = new ArrayList<>();

		int nrOfTrials = nrTrialsWithoutMorphemicBoundaries;

		// autodetect morphemic boundaries

		if (autodetectMorphologicalBoundaries == true) {

			if ((word1.substring(0, word1.length() - 1)).indexOf("￤") > -1
					|| (word2.substring(0, word2.length() - 1)).indexOf("￤") > -1) {
				selectSimilarityScore = 0;
				nrOfTrials = nrTrialsWithMorphemicBoundaries;
			} else {
				selectSimilarityScore = 1;
			}
		}

		// print Similarity Score used

		if (printResults == true) {
			System.out.print("Similarity Score used: " );
			if (optionFunction == 3) {
				System.out.println("Basic Global Similarity Score used");
			} else if (optionFunction == 4) {
				System.out.println("Corrected Global Similarity Score used");
			}else if (selectSimilarityScore == 0) {
				System.out.println(selectSimilarityScore + " - morphemic boundary detected -> Basic Global Similarity Score used");
			} else if (selectSimilarityScore == 1) {
				System.out.println(selectSimilarityScore + " - no morphemic boundary detected -> Corrected Global Similarity Score used");
			}
		}

		// Alignment - Variables 1

		boolean newItem = true;

		int nextCharacter;
		int nextCharacter_Tail;

		int lengthWord1 = word1.length();
		int lengthWord2 = word2.length();

		int sequenceCount = 0;

		int index = 0;
		int previousIndex = -1;

		int sequenceCount_Tail = 0;

		int index_Tail = 0;

		List<Alignment> results = new ArrayList<>();

		// store number of feautres used

		String fileFeatures;
		String[] linesFeaturesProvisional;
		String[] itemsFeaturesProvisional;

		fileFeatures = Reader.readFile(locationFileFeatures);

		linesFeaturesProvisional = fileFeatures.split("\n");

		for (int i = 0; i < linesFeaturesProvisional.length; i++) {

			if (linesFeaturesProvisional[i].indexOf("%") < 0) {
				
				itemsFeaturesProvisional = linesFeaturesProvisional[i].split("	");
				
				nrFeatures = itemsFeaturesProvisional.length - 1; // nr of feature - slot for the character
				
				break;
			}
		}
		// retrieve consonants-semiconsonant-vowels

		String fileCategoriesPhones;
		String[] fileCategoriesPhonesLines;
		String[] fileCategoriesPhonesItems;
		List<String> classCategoriesPhonesCharacter = new ArrayList<>();
		List<String> classCategoriesPhonesValue = new ArrayList<>();

		fileCategoriesPhones = Reader.readFile(locationFileCategoriesPhones);

		fileCategoriesPhonesLines = fileCategoriesPhones.split("\n");

		for (int i = 0; i < fileCategoriesPhonesLines.length; i++) {

			if (fileCategoriesPhonesLines[i].indexOf("%") < 0) {

				fileCategoriesPhonesItems = fileCategoriesPhonesLines[i].split("	");
				classCategoriesPhonesCharacter.add(fileCategoriesPhonesItems[0]);
				classCategoriesPhonesValue.add(fileCategoriesPhonesItems[1]);
			}
		}
		// count morphem boundaries

		Integer highestNrMorphemicBoundaries;

		List<Integer> listNrsMorphemicBoundaries = countMorphemicBoundaries(0, lengthWord1, lengthWord2, word1, word2);

		// Variables 2

		int[] bestPairs = new int[nrOfTrials];

		double sumFeaturesInCommon = 0.0;
		double globalSimilaryScore = 0.0;
		double correctedGlobalSimilaryScore = 0.0;

		for (int n = 0; n < bestPairs.length; n++) {
			bestPairs[n] = 0;
		}
		;

		List<List<Double>> similarityScoreMatches = new ArrayList<List<Double>>();
		List<List<Double>> similarityScoreMatchesOriginal = new ArrayList<List<Double>>();

		List<int[]> sequence = new ArrayList<int[]>();
		List<int[]> sequence_Tail = new ArrayList<int[]>();

		List<List<Integer>> listWord1Pre = new ArrayList<List<Integer>>();
		List<List<Integer>> listWord2Pre = new ArrayList<List<Integer>>();

		List<List<Integer>> listWord1Post = new ArrayList<List<Integer>>();
		List<List<Integer>> listWord2Post = new ArrayList<List<Integer>>();

		List<List<Integer>> matchWord1 = new ArrayList<List<Integer>>();
		List<List<Integer>> matchWord2 = new ArrayList<List<Integer>>();
		List<List<Integer>> matchWord1Original = new ArrayList<List<Integer>>();
		List<List<Integer>> matchWord2Original = new ArrayList<List<Integer>>();

		// Check words' length
		// -- if 1-letter long

		if (lengthWord1 == 1 || lengthWord2 == 1) {

			if (lengthWord1 == 1) {

				for (int i = 0; i < lengthWord2; i++) {

					List<Integer> matchWord1Provisional1 = new ArrayList<Integer>();
					List<Integer> matchWord2Provisional1 = new ArrayList<Integer>();

					for (int n = 0; n < lengthWord2; n++) {
						matchWord2Provisional1.add(n);
						matchWord1Provisional1.add(-1);
					}

					matchWord1Provisional1.set(i, 0);

					matchWord1.add(matchWord1Provisional1);
					matchWord2.add(matchWord2Provisional1);
				}

			} else {

				for (int i = 0; i < lengthWord1; i++) {

					List<Integer> matchWord1Provisional1 = new ArrayList<Integer>();
					List<Integer> matchWord2Provisional1 = new ArrayList<Integer>();

					for (int n = 0; n < lengthWord1; n++) {
						matchWord1Provisional1.add(n);
						matchWord2Provisional1.add(-1);
					}

					matchWord2Provisional1.set(i, 0);

					matchWord2.add(matchWord2Provisional1);
					matchWord1.add(matchWord1Provisional1);

				}

			}
		} else { // if more than 1 letter long

			// search for max values
			for (int i = 0; i < lengthWord1; i++) {
				for (int n = 0; n < lengthWord2; n++) {

					for (int a = 0; a < bestPairs.length; a++) {
						if (matrixResultComparison[i][n] > 0 & matrixResultComparison[i][n] == bestPairs[a]) {
							break;
						}
						if (matrixResultComparison[i][n] < 0) {
							break;
						}
						if (matrixResultComparison[i][n] > bestPairs[a]) {
							if (a < bestPairs.length) {
								for (int x = bestPairs.length - 1; x > a; x--) {
									bestPairs[x] = bestPairs[x - 1];
								}
							}
							bestPairs[a] = matrixResultComparison[i][n];
							break;
						}
					}
					;
				}
				;
			}
			;
			// store max values
			outerloop: for (int x = 0; x < bestPairs.length; x++) {
				for (int i = 0; i < lengthWord1; i++) {
					for (int n = 0; n < lengthWord2; n++) {

						//
						if (x == 1 & sequence.size() > nrOfTrials + 1) {// the +1 is to compensate the addition of a
																		// morphological boundary at the end

							for (int f = 1; f < bestPairs.length; f++) {
								bestPairs[f] = 0;
							}

							break outerloop;
						}

						if (matrixResultComparison[i][n] == bestPairs[x]) {

							int[] sequenceData = { sequenceCount, i, n, matrixResultComparison[i][n], index,
									previousIndex, 0 };
							sequence.add(sequenceData);
							sequence_Tail.add(sequenceData);
							sequenceCount++;
							sequenceCount_Tail++;
							index++;
							index_Tail++;
						}
						;
					}
					;
				}
				;
			}
			;

			// loop to build the matches - before max value
			for (int a = 0; a < bestPairs.length; a++) {
				bestPairs[a] = 0;
			}

			for (int z = 0; z < sequence.size(); z++) {
				if (sequence.get(z)[1] > 0 & sequence.get(z)[2] > 0) {
					// search for max values
					for (int n = 0; n < sequence.get(z)[2]; n++) {
						int i = sequence.get(z)[1] - 1;
						for (int a = 0; a < bestPairs.length; a++) {
							if (matrixResultComparison[i][n] > 0 & matrixResultComparison[i][n] == bestPairs[a]) {
								break;
							}
							if (matrixResultComparison[i][n] < 0) {
								break;
							}
							if (matrixResultComparison[i][n] > bestPairs[a]) {
								if (a < bestPairs.length) {
									for (int x = bestPairs.length - 1; x > a; x--) {
										bestPairs[x] = bestPairs[x - 1];
									}
								}
								bestPairs[a] = matrixResultComparison[i][n];
								break;
							}
						}
						;
					}
					;
					for (int i = 0; i < sequence.get(z)[1]; i++) {
						int n = sequence.get(z)[2] - 1;
						for (int a = 0; a < bestPairs.length; a++) {
							if (matrixResultComparison[i][n] > 0 & matrixResultComparison[i][n] == bestPairs[a]) {
								break;
							}
							if (matrixResultComparison[i][n] < 0) {
								break;
							}
							if (matrixResultComparison[i][n] > bestPairs[a]) {
								if (a < bestPairs.length) {
									for (int x = bestPairs.length - 1; x > a; x--) {
										bestPairs[x] = bestPairs[x - 1];
									}
								}
								bestPairs[a] = matrixResultComparison[i][n];
								break;
							}
						}
						;
					}
					;
					// - store max values
					for (int i = 0; i < sequence.get(z)[1]; i++) {
						for (int n = 0; n < sequence.get(z)[2]; n++) {
							for (int x = 0; x < bestPairs.length; x++) {
								if (matrixResultComparison[i][n] == bestPairs[x]) {

									int[] sequenceData = { sequenceCount, i, n, matrixResultComparison[i][n], index, z,
											0 };
									sequence.add(sequenceData);
									sequenceCount++;
									index++;
								}
								;
							}
							;
						}
						;
					}
					;
					for (int a = 0; a < bestPairs.length; a++) {
						bestPairs[a] = 0;
					}
				} else {
					sequence.get(z)[6] = 1;
				}
			}
			;

			// -----
			// =========
			// loop to build the matches - after max value
			for (int a = 0; a < bestPairs.length; a++) {
				bestPairs[a] = 0;
			}

			for (int z = 0; z < sequence_Tail.size(); z++) {
				for (int a = 0; a < bestPairs.length; a++) {
					bestPairs[a] = 0;
				}
				if (sequence_Tail.get(z)[1] + 1 < lengthWord1 & sequence_Tail.get(z)[2] + 1 < lengthWord2) {
					// search for max values
					for (int i = sequence_Tail.get(z)[1] + 1; i < lengthWord1; i++) {
						int n = sequence_Tail.get(z)[2] + 1;
						// for(int n = sequence_Tail.get(z)[2]+1; n < lengthWord2; n++){
						// int check = 0;
						for (int a = 0; a < bestPairs.length; a++) {
							// check = matrixResultComparison[i][n];
							if (matrixResultComparison[i][n] > 0 & matrixResultComparison[i][n] == bestPairs[a]) {
								break;
							}
							if (matrixResultComparison[i][n] < 0) {
								break;
							}
							if (matrixResultComparison[i][n] > bestPairs[a]) {
								if (a < bestPairs.length) {
									for (int x = bestPairs.length - 1; x > a; x--) {
										bestPairs[x] = bestPairs[x - 1];
									}
								}
								bestPairs[a] = matrixResultComparison[i][n];
								break;
							}
						}
						;
						// };
					}
					;

					// for(int i = sequence_Tail.get(z)[1]+1; i < lengthWord1; i++){

					for (int n = sequence_Tail.get(z)[2] + 1; n < lengthWord2; n++) {
						int i = sequence_Tail.get(z)[1] + 1;
						// int check = 0;
						for (int a = 0; a < bestPairs.length; a++) {
							// check = matrixResultComparison[i][n];
							if (matrixResultComparison[i][n] > 0 & matrixResultComparison[i][n] == bestPairs[a]) {
								break;
							}
							if (matrixResultComparison[i][n] < 0) {
								break;
							}
							if (matrixResultComparison[i][n] > bestPairs[a]) {
								if (a < bestPairs.length) {
									for (int x = bestPairs.length - 1; x > a; x--) {
										bestPairs[x] = bestPairs[x - 1];
									}
								}
								bestPairs[a] = matrixResultComparison[i][n];
								break;
							}
						}
						;
						// };
					}
					;
					// ---- store max values
					for (int i = sequence_Tail.get(z)[1] + 1; i < lengthWord1; i++) {
						for (int n = sequence_Tail.get(z)[2] + 1; n < lengthWord2; n++) {
							for (int x = 0; x < bestPairs.length; x++) {
								if (matrixResultComparison[i][n] == bestPairs[x]) {

									int[] sequenceData_Tail = { sequenceCount_Tail, i, n, matrixResultComparison[i][n],
											index_Tail, z, 0 };
									sequence_Tail.add(sequenceData_Tail);
									sequenceCount_Tail++;
									index_Tail++;
								}
								;
							}
							;
						}
						;
					}
					;
					for (int a = 0; a < bestPairs.length; a++) {
						bestPairs[a] = 0;
					}
				} else {
					sequence_Tail.get(z)[6] = 9;
				}
				for (int a = 0; a < bestPairs.length; a++) {
					bestPairs[a] = 0;
				}
			}
			;

			// ==========
			// Build alignments

			// ---------
			// Build initial sequence

			for (int z = 0; z < sequence.size(); z++) {

				if (sequence.get(z)[6] == 1) {

					List<Integer> listLine1 = new ArrayList<Integer>(0);
					List<Integer> listLine2 = new ArrayList<Integer>(0);

					List<Integer> item1 = new ArrayList<Integer>();
					List<Integer> item2 = new ArrayList<Integer>();

					line1 = line1 + word1.charAt(sequence.get(z)[1]);
					line2 = line2 + word2.charAt(sequence.get(z)[2]);

					listLine1.add(sequence.get(z)[1]);
					listLine2.add(sequence.get(z)[2]);

					nextCharacter = sequence.get(z)[5];

					for (int i = sequence.size() - 1; i > -1; i--) {

						if (sequence.get(i)[4] == nextCharacter) {
							nextCharacter = sequence.get(i)[5];
							listLine1.add(sequence.get(i)[1]);
							listLine2.add(sequence.get(i)[2]);

							line1 = line1 + word1.charAt(sequence.get(i)[1]);
							line2 = line2 + word2.charAt(sequence.get(i)[2]);
						}

					}

					int gap = 0;
					int difference = 0;

					line1 = line1 + ":";
					line2 = line2 + ":";

					for (int i = 0; i < listLine1.size(); i++) {
						if (i == 0) {
							difference = listLine1.get(i) - listLine2.get(i);
						} else {
							difference = (listLine1.get(i) - listLine1.get(i - 1))
									- (listLine2.get(i) - listLine2.get(i - 1));
						}
						;

						if (difference == 0) {
							if (i == 0) {
								line1 = line1 + word1.charAt(listLine1.get(i));
								line2 = line2 + word2.charAt(listLine2.get(i));
								item1.add(listLine1.get(i));
								item2.add(listLine2.get(i));
							} else {
								gap = listLine1.get(i) - listLine1.get(i - 1);
								if (gap == 1) {
									line1 = line1 + "-" + word1.charAt(listLine1.get(i));
									line2 = line2 + "-" + word2.charAt(listLine2.get(i));
									item1.add(listLine1.get(i));
									item2.add(listLine2.get(i));
								} else if (gap > 1) {
									for (int n = 1; n <= gap - 1; n++) {
										line1 = line1 + word1.charAt(listLine1.get(i - 1) + n) + "-0";
										line2 = line2 + "-0" + word2.charAt(listLine2.get(i - 1) + n);
										item1.add(listLine1.get(i - 1) + n);
										item1.add(-1);
										item2.add(-1);
										item2.add(listLine2.get(i - 1) + n);
									}

									line1 = line1 + "-" + word1.charAt(listLine1.get(i));
									line2 = line2 + "-" + word2.charAt(listLine2.get(i));
									item1.add(listLine1.get(i));
									item2.add(listLine2.get(i));

								}
							}
						}
						if (difference < 0) {

							if (i > 0) {
								gap = listLine1.get(i) - listLine1.get(i - 1);
							}
							if (gap > 1) {
								for (int n = 1; n < gap; n++) {
									line1 = line1 + "-" + word1.charAt(listLine1.get(i - 1) + n) + "-0";
									line2 = line2 + "-0-" + word2.charAt(listLine2.get(i - 1) + n);
									item1.add(listLine1.get(i - 1) + n);
									item1.add(-1);
									item2.add(-1);
									item2.add(listLine2.get(i - 1) + n);
								}
							}
							// ----
							for (int n = -1 * difference; n > 0; n--) {
								line1 = line1 + "-0";
								line2 = line2 + "-" + word2.charAt(listLine2.get(i) - n);
								item1.add(-1);
								item2.add(listLine2.get(i) - n);
							}

							line1 = line1 + "-" + word1.charAt(listLine1.get(i));
							line2 = line2 + "-" + word2.charAt(listLine2.get(i));
							item1.add(listLine1.get(i));
							item2.add(listLine2.get(i));
							// -------

						} else if (difference > 0) {

							if (i > 0) {
								gap = listLine2.get(i) - listLine2.get(i - 1);
							}
							if (gap > 1) {
								for (int n = 1; n < gap; n++) {
									line1 = line1 + "-" + word1.charAt(listLine1.get(i - 1) + n) + "-0";
									line2 = line2 + "-0-" + word2.charAt(listLine2.get(i - 1) + n);
									item1.add(listLine1.get(i - 1) + n);
									item1.add(-1);
									item2.add(-1);
									item2.add(listLine2.get(i - 1) + n);
								}
							}
							// ----
							for (int n = difference; n > 0; n--) {
								line2 = line2 + "-0";
								line1 = line1 + "-" + word1.charAt(listLine1.get(i) - n);
								item2.add(-1);
								item1.add(listLine1.get(i) - n);
							}

							line1 = line1 + "-" + word1.charAt(listLine1.get(i));
							line2 = line2 + "-" + word2.charAt(listLine2.get(i));
							item1.add(listLine1.get(i));
							item2.add(listLine2.get(i));
							// -------

						}

					}
					gap = 0;
					difference = 0;

					line1 = "";
					line2 = "";

					boolean alreadyListed_Pre = false;

					for (int g = 0; g < listWord1Pre.size(); g++) {
						if (listWord1Pre.get(g).equals(item1) & listWord2Pre.get(g).equals(item2)) {
							alreadyListed_Pre = true;
							break;
						}
					}

					if (alreadyListed_Pre == false) {
						listWord1Pre.add(item1);
						listWord2Pre.add(item2);
						alreadyListed_Pre = true;
					}

				}
				;

			}

			line1 = "";
			line2 = "";

			// System.out.println("-------");
			// build final sequence
			for (int z = 0; z < sequence_Tail.size(); z++) {
				if (sequence_Tail.get(z)[6] == 9) {

					List<Integer> line1Tail_Inverted = new ArrayList<Integer>(0);
					List<Integer> line2Tail_Inverted = new ArrayList<Integer>(0);
					List<Integer> line1_Tail = new ArrayList<Integer>(0);
					List<Integer> line2_Tail = new ArrayList<Integer>(0);

					List<Integer> item1_2 = new ArrayList<Integer>();
					List<Integer> item2_2 = new ArrayList<Integer>();

					line1Tail_Inverted.add(sequence_Tail.get(z)[1]);
					line2Tail_Inverted.add(sequence_Tail.get(z)[2]);

					nextCharacter_Tail = sequence_Tail.get(z)[5];

					for (int i = sequence_Tail.size() - 1; i > -1; i--) {

						if (sequence_Tail.get(i)[4] == nextCharacter_Tail) {
							nextCharacter_Tail = sequence_Tail.get(i)[5];
							line1Tail_Inverted.add(sequence_Tail.get(i)[1]);
							line2Tail_Inverted.add(sequence_Tail.get(i)[2]);

						}

					}

					for (int i = line1Tail_Inverted.size() - 1; i > -1; i--) {
						line1_Tail.add(line1Tail_Inverted.get(i));
						line2_Tail.add(line2Tail_Inverted.get(i));
					}

					for (int i = 0; i < line1Tail_Inverted.size(); i++) {
						line1 = line1 + word1.charAt(line1Tail_Inverted.get(i));
						line2 = line2 + word2.charAt(line2Tail_Inverted.get(i));
					}

					int gap = 0;
					int difference = 0;

					line1 = line1 + "::";
					line2 = line2 + "::";

					for (int i = 0; i < line1_Tail.size(); i++) {
						if (i == 0) {
							difference = line1_Tail.get(i) - line2_Tail.get(i);
						} else {
							difference = (line1_Tail.get(i) - line1_Tail.get(i - 1))
									- (line2_Tail.get(i) - line2_Tail.get(i - 1));
						}
						;

						if (difference == 0) {
							if (i == 0) {
								line1 = line1 + word1.charAt(line1_Tail.get(i));
								line2 = line2 + word2.charAt(line2_Tail.get(i));
								item1_2.add(line1_Tail.get(i));
								item2_2.add(line2_Tail.get(i));

							} else {
								gap = line1_Tail.get(i) - line1_Tail.get(i - 1);
								if (gap == 1) {
									line1 = line1 + "-" + word1.charAt(line1_Tail.get(i));
									line2 = line2 + "-" + word2.charAt(line2_Tail.get(i));
									item1_2.add(line1_Tail.get(i));
									item2_2.add(line2_Tail.get(i));

								} else if (gap > 1) {
									for (int n = 1; n <= gap - 1; n++) {
										line1 = line1 + "-" + word1.charAt(line1_Tail.get(i - 1) + n) + "-0";
										line2 = line2 + "-0-" + word2.charAt(line2_Tail.get(i - 1) + n);
										item1_2.add(line1_Tail.get(i - 1) + n);
										item1_2.add(-1);
										item2_2.add(-1);
										item2_2.add(line2_Tail.get(i - 1) + n);
									}

									line1 = line1 + "-" + word1.charAt(line1_Tail.get(i));
									line2 = line2 + "-" + word2.charAt(line2_Tail.get(i));
									item1_2.add(line1_Tail.get(i));
									item2_2.add(line2_Tail.get(i));

								}
							}
						}
						if (difference < 0) {

							if (i > 0) {
								gap = line1_Tail.get(i) - line1_Tail.get(i - 1);
							}
							if (gap > 1) {
								for (int n = 1; n < gap; n++) {
									line1 = line1 + "-" + word1.charAt(line1_Tail.get(i - 1) + n) + "-0";
									line2 = line2 + "-0-" + word2.charAt(line2_Tail.get(i - 1) + n);
									item1_2.add(line1_Tail.get(i - 1) + n);
									item1_2.add(-1);
									item2_2.add(-1);
									item2_2.add(line2_Tail.get(i - 1) + n);
								}
							}
							// ----
							for (int n = -1 * difference; n > 0; n--) {
								if (item1_2.size() > 0) {
									line1 = line1 + "-0";
									line2 = line2 + "-" + word2.charAt(line2_Tail.get(i) - n);
									item1_2.add(-1);
									item2_2.add(line2_Tail.get(i) - n);
								}
							}

							line1 = line1 + "-" + word1.charAt(line1_Tail.get(i));
							line2 = line2 + "-" + word2.charAt(line2_Tail.get(i));
							item1_2.add(line1_Tail.get(i));
							item2_2.add(line2_Tail.get(i));
							// -------

						} else if (difference > 0) {

							if (i > 0) {
								gap = line2_Tail.get(i) - line2_Tail.get(i - 1);
							}
							if (gap > 1) {
								for (int n = 1; n < gap; n++) {
									line1 = line1 + "-" + word1.charAt(line1_Tail.get(i - 1) + n) + "-0";
									line2 = line2 + "-0-" + word2.charAt(line2_Tail.get(i - 1) + n);
									item1_2.add(line1_Tail.get(i - 1) + n);
									item1_2.add(-1);
									item2_2.add(-1);
									item2_2.add(line2_Tail.get(i - 1) + n);
								}
							}
							// ----
							for (int n = difference; n > 0; n--) {
								if (item1_2.size() > 0) {
									line2 = line2 + "-0";
									line1 = line1 + "-" + word1.charAt(line1_Tail.get(i) - n);
									item2_2.add(-1);
									item1_2.add(line1_Tail.get(i) - n);
								}
							}

							line1 = line1 + "-" + word1.charAt(line1_Tail.get(i));
							line2 = line2 + "-" + word2.charAt(line2_Tail.get(i));
							item1_2.add(line1_Tail.get(i));
							item2_2.add(line2_Tail.get(i));
							// -------

						}

					}

					if (item1_2.get(item1_2.size() - 1) != lengthWord1 - 1) {
						for (int i = item1_2.get(item1_2.size() - 1) + 1; i < lengthWord1; i++) {
							item1_2.add(i);
							item2_2.add(-1);
							line1 = line1 + ":" + word1.charAt(i);
							line2 = line2 + "-0";
						}
					} else if (item2_2.get(item2_2.size() - 1) != lengthWord2 - 1) {
						for (int i = item2_2.get(item2_2.size() - 1) + 1; i < lengthWord2; i++) {
							item1_2.add(-1);
							item2_2.add(i);
							line1 = line1 + "-0";
							line2 = line2 + ":" + word2.charAt(i);
						}
					}

					gap = 0;
					difference = 0;

					line1 = "";
					line2 = "";

					boolean alreadyListed_Post = false;
					for (int g = 0; g < listWord1Post.size(); g++) {
						if (listWord1Post.get(g).equals(item1_2) & listWord2Post.get(g).equals(item2_2)) {
							alreadyListed_Post = true;
							break;
						}
					}

					if (alreadyListed_Post == false) {
						listWord1Post.add(item1_2);
						listWord2Post.add(item2_2);
						alreadyListed_Post = true;
					}

				}
			}

			// join initial and final sequences

			for (int i = 0; i < listWord1Pre.size(); i++) {
				for (int n = 0; n < listWord1Post.size(); n++) {

					List<Integer> match1_Pre_Post = new ArrayList<Integer>();
					List<Integer> match2_Pre_Post = new ArrayList<Integer>();

					if (listWord1Pre.get(i).get(listWord1Pre.get(i).size() - 1) == listWord1Post.get(n).get(0)
							& listWord2Pre.get(i).get(listWord2Pre.get(i).size() - 1) == listWord2Post.get(n).get(0)) {

						for (int a = 0; a < listWord1Pre.get(i).size(); a++) {
							match1_Pre_Post.add(listWord1Pre.get(i).get(a));
							match2_Pre_Post.add(listWord2Pre.get(i).get(a));
						}

						for (int a = 1; a < listWord1Post.get(n).size(); a++) {
							match1_Pre_Post.add(listWord1Post.get(n).get(a));
							match2_Pre_Post.add(listWord2Post.get(n).get(a));
						}

						for (int a = 0; a < match1_Pre_Post.size(); a++) {
							line1 = line1 + ":" + match1_Pre_Post.get(a);
							line2 = line2 + ":" + match2_Pre_Post.get(a);
						}

						line1 = "";
						line2 = "";

						newItem = true;
						if (matchWord1.size() < 1) {
							matchWord1.add(match1_Pre_Post);
							matchWord2.add(match2_Pre_Post);
						} else {
							for (int m = 0; m < matchWord1.size(); m++) {
								if (matchWord1.get(m).equals(match1_Pre_Post)
										& matchWord2.get(m).equals(match2_Pre_Post)) {
									newItem = false;
								}
							}
							if (newItem == true) {
								matchWord1.add(match1_Pre_Post);
								matchWord2.add(match2_Pre_Post);
							}

						}
					}
				}
			}

			// delete double matches
			newItem = false;
		}

		// ====================
		// calculate similarity of various pairs
		// distinguish between Global or Corrected Global Similarity Scores

		for (int i = 0; i < matchWord1.size(); i++) {

			// varables - 3

			List<String> indexPhonPairsItem = new ArrayList<>();
			List<Integer> indexPhonPairsValueItem = new ArrayList<>();

			int[] matchingSequence_Start_End = { -1, -1 };

			// possible pairs -> letter word 1 (= A) - letter word 2 (= B), morphemic
			// boundary = MB, letter = x, gap = 0

			boolean MB0 = false; // morphemic boundary - 0
			boolean x0_MB0 = false; // match letter-0 before morphemic boundary - 0
			boolean x0A_MB0 = false; // match letter-0 before morphemic boundary - 0
			boolean x0B_MB0 = false; // match letter-0 before morphemic boundary - 0
			boolean x0A_MB0B = false; // match letter-0 before morphemic boundary - 0
			boolean x0B_MB0A = false; // match letter-0 before morphemic boundary - 0
			boolean xx_MB0 = false; // match letter-letter before morphemic boundary - 0

			int semiconsonant = 0;
			int vowel = 0;

			String typeOfMatch;
			double valuePairFromComparisonMatrix;
			double valuePairFromComparisonMatrixOriginal = 0;

			double coefNeg = 0; // the coefNeg is used to identify those alignments that are wrong on the basis
								// of the morpheme boundaries, i.e. matching a morph. bound. and a letter

			// ----------
			// define type of pair

			for (int n = 0; n < matchWord1.get(i).size(); n++) {

				typeOfMatch = findTypeOfMatch(i, n, matchWord1, matchWord2, word1, word2);

				if (typeOfMatch.equals("x0") || typeOfMatch.equals("0x") || typeOfMatch.equals("0￤")
						|| typeOfMatch.equals("￤0")) {

					valuePairFromComparisonMatrix = 0;
				} else {

					valuePairFromComparisonMatrix = (double) matrixResultComparison[matchWord1.get(i).get(n)][matchWord2
							.get(i).get(n)];
					valuePairFromComparisonMatrixOriginal = (double) matrixResultComparisonOriginal[matchWord1.get(i)
							.get(n)][matchWord2.get(i).get(n)];

				}

				/// ----------
				// -CASE 1-

				if (typeOfMatch.equals("xx")) {

					int valuePair_1 = -1;
					int valuePair_2 = -1;

					for (int f = 0; f < classCategoriesPhonesValue.size(); f++) {
						if (matchWord1.get(i).get(n) > -1) {
							if (String.valueOf(word1.charAt((matchWord1.get(i).get(n)))).equals(
									classCategoriesPhonesCharacter.get(f)) & classCategoriesPhonesValue.get(f).equals("-")) {
								valuePair_1 = 0; // cons
							} else if (String.valueOf(word1.charAt((matchWord1.get(i).get(n)))).equals(
									classCategoriesPhonesCharacter.get(f)) & classCategoriesPhonesValue.get(f).equals("±")) {
								valuePair_1 = 1; // semicons
							} else if (String.valueOf(word1.charAt((matchWord1.get(i).get(n)))).equals(
									classCategoriesPhonesCharacter.get(f)) & classCategoriesPhonesValue.get(f).equals("+")) {
								valuePair_1 = 2; // vows
							}

							if (String.valueOf(word2.charAt((matchWord2.get(i).get(n)))).equals(
									classCategoriesPhonesCharacter.get(f)) & classCategoriesPhonesValue.get(f).equals("-")) {
								valuePair_2 = 0; // cons
							} else if (String.valueOf(word2.charAt((matchWord2.get(i).get(n)))).equals(
									classCategoriesPhonesCharacter.get(f)) & classCategoriesPhonesValue.get(f).equals("±")) {
								valuePair_2 = 1; // semicons
							} else if (String.valueOf(word2.charAt((matchWord2.get(i).get(n)))).equals(
									classCategoriesPhonesCharacter.get(f)) & classCategoriesPhonesValue.get(f).equals("+")) {
								valuePair_2 = 2; // vows
							}
						}
					}

					int limitSignificanceUsed = -1;

					if (limitSignificanceConsonants <= limitSignificanceVowels
							& (valuePair_1 == 0 || valuePair_2 == 0 || valuePair_1 == 1 || valuePair_2 == 1)) { // if
																												// one
																												// of
																												// the
																												// two
																												// is a
																												// cons
																												// or a
																												// semicons

						limitSignificanceUsed = 0; // -> use limitSignificanceConsonants
					} else if (limitSignificanceConsonants <= limitSignificanceVowels
							& (valuePair_1 == 2 & valuePair_2 == 2)) { // if both are vows

						limitSignificanceUsed = 1; // -> use limitSignificanceVowels
					}

					else if (limitSignificanceConsonants > limitSignificanceVowels
							& (valuePair_1 == 2 || valuePair_2 == 2)) { // if one of the two is a cons or a semicons

						limitSignificanceUsed = 1; // -> use limitSignificanceVowels
					} else if (limitSignificanceConsonants > limitSignificanceVowels
							& ((valuePair_1 == 0 || valuePair_1 == 1) & (valuePair_2 == 0 || valuePair_2 == 1))) { // if
																													// both
																													// are
																													// cons

						limitSignificanceUsed = 0; // -> use limitSignificanceConsonants
					}

					if (limitSignificanceUsed == 0) {
						if (limitMinimumFeaturesConsonants == true
								& valuePairFromComparisonMatrixOriginal <= limitSignificanceConsonants) { // if the
																											// feature
																											// index of
																											// the match
																											// is below
																											// the
																											// feature
																											// limit,
																											// its value
																											// is
																											// reduced
																											// to 0 and
																											// not
																											// counted
							matchWord1.get(i).set(n, matchWord1.get(i).get(n) - 2000);
							matchWord2.get(i).set(n, matchWord2.get(i).get(n) - 2000);
							valuePairFromComparisonMatrix = 0;
						}
					} else if (limitSignificanceUsed == 1) {
						if (limitMinimumFeaturesVowels == true
								& valuePairFromComparisonMatrixOriginal <= limitSignificanceVowels) { // if the feature
																										// index of the
																										// match is
																										// below the
																										// feature
																										// limit, its
																										// value is
																										// reduced to 0
																										// and not
																										// counted
							matchWord1.get(i).set(n, matchWord1.get(i).get(n) - 2000);
							matchWord2.get(i).set(n, matchWord2.get(i).get(n) - 2000);
							valuePairFromComparisonMatrix = 0;
						}
					}

					// match only same class of phonemes

					if (matchesOnlyPhonesSameCategory == true) {
						if ((valuePair_1 == 2 || valuePair_2 == 2) & (valuePair_1 != valuePair_2) & (!(i == 0 || n == 0
								|| n == matchWord1.get(i).size() - 2 || i == matchWord1.size() - 2))) {
							coefNeg = 1000;
						}
						if ((valuePair_1 == 2 || valuePair_2 == 2) & (valuePair_1 != valuePair_2) & (i == 0 || n == 0)
								& !(n == matchWord1.get(i).size() - 2) || (i == matchWord1.size() - 2)) {

							matrixResultComparisonOriginal[matchWord1.get(i).get(n)][matchWord2.get(i).get(n)] = 0;
							matrixResultComparison[matchWord1.get(i).get(n)][matchWord2.get(i).get(n)] = 0;
						}
					}

					if ((valuePair_1 == 2 & valuePair_2 == 2) || ((valuePair_1 < 2 & valuePair_2 < 2))) {// ||
																											// valuePair_1
																											// == 1) &&
																											// (valuePair_2
																											// == 0 ||
																											// valuePair_2
																											// == 1))){
						if (matchWord1.get(i).get(n) > -1) {

							boolean alreadyListed = false;
							String lettersOfThePair = "";
							
							String word1DividedSegments = "";
							String word2DividedSegments = "";
							
							for (int h = 0; h < word1.length(); h++) {
								if (!String.valueOf(word1.charAt(h)).equals("￤")) {
								word1DividedSegments = word1DividedSegments + ":" + String.valueOf(word1.charAt(h));
								}
							}
							
							for (int h = 0; h < word2.length(); h++) {
								if (!String.valueOf(word2.charAt(h)).equals("￤")) {
								word2DividedSegments = word2DividedSegments + ":" + String.valueOf(word2.charAt(h));
								}
							}
							
							
							String segmentsWord1WithDiacritics = buildWord(word1DividedSegments, word1Unparsed, locationFilePhoneticDiacritics);
							String segmentsWord2WithDiacritics = buildWord(word2DividedSegments, word2Unparsed, locationFilePhoneticDiacritics);

							segmentsWord1WithDiacritics = segmentsWord1WithDiacritics.replaceAll("￤", "	￤");
							segmentsWord2WithDiacritics = segmentsWord2WithDiacritics.replaceAll("￤", "	￤");
							
							String[] segmentsWord1 = segmentsWord1WithDiacritics.split("	");
							String[] segmentsWord2 = segmentsWord2WithDiacritics.split("	");

							if (matchWord1.get(i).get(n) < -1) {
								lettersOfThePair = segmentsWord1[(matchWord1.get(i).get(n)) + 2000]
										+ " - " + segmentsWord2[(matchWord2.get(i).get(n)) + 2000];
							} else {
								lettersOfThePair = segmentsWord1[(matchWord1.get(i).get(n))] + " - "
										+ segmentsWord2[(matchWord2.get(i).get(n))];
							}
							for (int f = 0; f < indexPhonPairsItem.size(); f++) {

								if (lettersOfThePair.equals(indexPhonPairsItem.get(f))) {
									alreadyListed = true;
									indexPhonPairsValueItem.set(f, indexPhonPairsValueItem.get(f) + 1);
								}
							}

							if (alreadyListed == false) {

								indexPhonPairsItem.add(lettersOfThePair);
								indexPhonPairsValueItem.add(1);
							}
						}

						if (valuePairFromComparisonMatrix > 0) {
							if (matchingSequence_Start_End[0] < 0) {
								matchingSequence_Start_End[0] = n;
							}
							matchingSequence_Start_End[1] = n;
						}

						if (MB0 == false) {

							sumFeaturesInCommon = calculateFeaturesInCommon(1, sumFeaturesInCommon,
									valuePairFromComparisonMatrix);
							xx_MB0 = true;

						} else if (MB0 == true & (xx_MB0 == false || x0_MB0 == false)) {

							sumFeaturesInCommon = calculateFeaturesInCommon(1, sumFeaturesInCommon,
									valuePairFromComparisonMatrix);
							xx_MB0 = true;

							if (x0A_MB0B == true || x0B_MB0A == true) {

								coefNeg = 1000;
							}

						} else if (MB0 == true & (xx_MB0 == true || x0_MB0 == true)) {

							sumFeaturesInCommon = calculateFeaturesInCommon(1, sumFeaturesInCommon,
									valuePairFromComparisonMatrix);

						}

					}
					// -CASE 2-

				} else if (typeOfMatch.equals("x0") || typeOfMatch.equals("0x")) {

					if (ignoreSemiconsonantMismatches == true) {
						if (typeOfMatch.equals("x0")) {
							for (int f = 0; f < classCategoriesPhonesValue.size(); f++) {
								if (matchWord1.get(i).get(n) > -1) {
									if (String.valueOf(word1.charAt((matchWord1.get(i).get(n))))
											.equals(classCategoriesPhonesCharacter.get(f))
											& classCategoriesPhonesValue.get(f).equals("±")) {
										matchWord1.get(i).set(n, matchWord1.get(i).get(n) - 1000);
										valuePairFromComparisonMatrix = 0;
										semiconsonant = semiconsonant + 1;
									}
								}
							}
						}
						if (typeOfMatch.equals("0x")) {
							for (int f = 0; f < classCategoriesPhonesValue.size(); f++) {
								if (matchWord2.get(i).get(n) > -1) {
									if (String.valueOf(word2.charAt((matchWord2.get(i).get(n))))
											.equals(classCategoriesPhonesCharacter.get(f))
											& classCategoriesPhonesValue.get(f).equals("±")) {
										matchWord2.get(i).set(n, matchWord2.get(i).get(n) - 1000);
										valuePairFromComparisonMatrix = 0;
										semiconsonant = semiconsonant + 1;
									}
								}
							}
						}
					}

					if ((ignoreVowelsMismatches == true || (ignoreVowelsMismatchesExceptFirst == true & n > 0))
							& matchWord1.get(i).get(n) > -2 & matchWord2.get(i).get(n) > -2) {
						if (typeOfMatch.equals("x0")) {
							for (int f = 0; f < classCategoriesPhonesValue.size(); f++) {
								if (matchWord1.get(i).get(n) > -1) {
									if (String.valueOf(word1.charAt((matchWord1.get(i).get(n))))
											.equals(classCategoriesPhonesCharacter.get(f))
											& classCategoriesPhonesValue.get(f).equals("+")) {
										matchWord1.get(i).set(n, matchWord1.get(i).get(n) - 1000);
										valuePairFromComparisonMatrix = 0;
										vowel = vowel + 1;
									}
								}
							}
						}
						if (typeOfMatch.equals("0x")) {
							for (int f = 0; f < classCategoriesPhonesValue.size(); f++) {
								if (matchWord2.get(i).get(n) > -1) {
									if (String.valueOf(word2.charAt((matchWord2.get(i).get(n))))
											.equals(classCategoriesPhonesCharacter.get(f))
											& classCategoriesPhonesValue.get(f).equals("+")) {
										matchWord2.get(i).set(n, matchWord2.get(i).get(n) - 1000);
										valuePairFromComparisonMatrix = 0;
										vowel = vowel + 1;
									}
								}
							}
						}
					}

					if (MB0 == false) {

						sumFeaturesInCommon = calculateFeaturesInCommon(0, sumFeaturesInCommon,
								valuePairFromComparisonMatrix);
						x0_MB0 = true;
						if (typeOfMatch.equals("x0")) {
							x0A_MB0 = true;
						}

						if (typeOfMatch.equals("0x")) {
							x0B_MB0 = true;
						}

					} else if (MB0 == true & (xx_MB0 == false || x0_MB0 == false)) {

						sumFeaturesInCommon = calculateFeaturesInCommon(0, sumFeaturesInCommon,
								valuePairFromComparisonMatrix);
						x0_MB0 = true;

						if (typeOfMatch.equals("x0")) {
							x0A_MB0 = true;
						}

						if (typeOfMatch.equals("0x")) {
							x0B_MB0 = true;
						}

					} else if (MB0 == true & (xx_MB0 == true || x0_MB0 == true)) {

						sumFeaturesInCommon = calculateFeaturesInCommon(1, sumFeaturesInCommon,
								valuePairFromComparisonMatrix);

					}

					// -CASE 3-

				} else if (typeOfMatch.equals("x￤") || typeOfMatch.equals("￤x")) {

					sumFeaturesInCommon = calculateFeaturesInCommon(2, sumFeaturesInCommon,
							valuePairFromComparisonMatrix);
					coefNeg = 1000;
					MB0 = true;

					// -CASE 4-

				} else if (typeOfMatch.equals("￤0") || typeOfMatch.equals("0￤")) {

					sumFeaturesInCommon = calculateFeaturesInCommon(0, sumFeaturesInCommon,
							valuePairFromComparisonMatrix);
					if (xx_MB0 == true & typeOfMatch.equals("0￤")) {
						coefNeg = 1000;
					}
					if (xx_MB0 == true & typeOfMatch.equals("￤0")) {
						coefNeg = 1000;
					}
					if (x0A_MB0 == true & typeOfMatch.equals("0￤")) {
						x0A_MB0B = true;
					}
					if (x0B_MB0 == true & typeOfMatch.equals("￤0")) {
						x0B_MB0A = true;
					}
					MB0 = true;

					// -CASE 5-

				} else if (typeOfMatch.equals("￤￤")) {

					sumFeaturesInCommon = calculateFeaturesInCommon(0, sumFeaturesInCommon,
							valuePairFromComparisonMatrix);

					MB0 = false;
					x0_MB0 = false;
					xx_MB0 = false;
					x0A_MB0 = false;
					x0B_MB0 = false;
					x0A_MB0B = false;
					x0B_MB0A = false;
				}
			}

			// Calculate length of the match

			indexesMatchingSequence.add(matchingSequence_Start_End);

			indexesMorphemicBoundaries.add(matchingSequence_Start_End);

			int lengthMatchingSequence = 0;

			int nrMorphemicBoundariesWord1 = 0;
			int nrMorphemicBoundariesWord2 = 0;

			for (int m = indexesMatchingSequence.get(i)[0]; m < indexesMatchingSequence.get(i)[1] + 1; m++) {

				if (m < 0) {
					m = 0;
				} // to deal with cases in which there is no match

				if (matchWord1.get(i).get(m) > -1) {
					if (String.valueOf(word1.charAt(matchWord1.get(i).get(m))).equals("￤")) {
						nrMorphemicBoundariesWord1 = nrMorphemicBoundariesWord1 + 1;
					}
				}

				if (matchWord2.get(i).get(m) > -1) {
					if (String.valueOf(word2.charAt(matchWord2.get(i).get(m))).equals("￤")) {
						nrMorphemicBoundariesWord2 = nrMorphemicBoundariesWord2 + 1;
					}
				}

			}

			if (nrMorphemicBoundariesWord1 >= nrMorphemicBoundariesWord2) {
				highestNrMorphemicBoundaries = nrMorphemicBoundariesWord1;
			} else {
				highestNrMorphemicBoundaries = nrMorphemicBoundariesWord2;
			}

			// ----count vowels in the Corrected Global matching
			// string-------------------------------------

			int nrVowelsMatchingSequenceWord1 = 0;
			int nrVowelsMatchingSequenceWord2 = 0;
			int nrVowelsMatchingSequence = 0;

			for (int m = indexesMatchingSequence.get(i)[0]; m < indexesMatchingSequence.get(i)[1] + 1; m++) {

				if (m < 0) {
					m = 0;
				} // to deal with cases in which there is no match

				if (matchWord1.get(i).get(m) < -100 & matchWord1.get(i).get(m) > -1500) {
					for (int f = 0; f < classCategoriesPhonesValue.size(); f++) {
						if (String.valueOf(word1.charAt(matchWord1.get(i).get(m) + 1000))
								.equals(classCategoriesPhonesCharacter.get(f)) & classCategoriesPhonesValue.get(f).equals("+")) {

							nrVowelsMatchingSequenceWord1 = nrVowelsMatchingSequenceWord1 + 1;
						}
					}

				}

				if (matchWord2.get(i).get(m) < -100 & matchWord2.get(i).get(m) > -1500) {
					for (int f = 0; f < classCategoriesPhonesValue.size(); f++) {
						if (String.valueOf(word2.charAt(matchWord2.get(i).get(m) + 1000))
								.equals(classCategoriesPhonesCharacter.get(f)) & classCategoriesPhonesValue.get(f).equals("+")) {

							nrVowelsMatchingSequenceWord2 = nrVowelsMatchingSequenceWord2 + 1;
						}
					}
				}

			}

			if (nrVowelsMatchingSequenceWord1 >= nrVowelsMatchingSequenceWord2) {
				nrVowelsMatchingSequence = nrVowelsMatchingSequenceWord1;
			} else {
				nrVowelsMatchingSequence = nrVowelsMatchingSequenceWord2;
			}
			// -------------------------
			// ----count semivowels in the Corrected Global matching
			// string-------------------------------------

			int nrSemiconsonantsMatchingSequenceWord1 = 0;
			int nrSemiconsonantsMatchingSequenceWord2 = 0;
			int nrSemiconsonantsMatchingSequence = 0;

			for (int m = indexesMatchingSequence.get(i)[0]; m < indexesMatchingSequence.get(i)[1] + 1; m++) {

				if (m < 0) {
					m = 0;
				} // to deal with cases in which there is no match

				if (matchWord1.get(i).get(m) < -100 & matchWord1.get(i).get(m) > -1500) {

					for (int j = 0; j < classCategoriesPhonesCharacter.size(); j++) {
						if (String.valueOf(word1.charAt(matchWord1.get(i).get(m) + 1000))
								.equals(classCategoriesPhonesCharacter.get(j)) & classCategoriesPhonesValue.get(j).equals("±")) { // if
																														// the
																														// feature
																														// index
																														// of
																														// the
																														// match
																														// is
																														// below
																														// the
																														// feature
																														// limit,
																														// its
																														// value
																														// is
																														// reduced
																														// to
																														// 0
																														// and
																														// not
																														// counted

							nrSemiconsonantsMatchingSequenceWord1 = nrSemiconsonantsMatchingSequenceWord1 + 1;
						}
					}
				}

				if (matchWord2.get(i).get(m) < -100 & matchWord2.get(i).get(m) > -1500) {

					for (int j = 0; j < classCategoriesPhonesCharacter.size(); j++) {
						if (String.valueOf(word2.charAt(matchWord2.get(i).get(m) + 1000))
								.equals(classCategoriesPhonesCharacter.get(j)) & classCategoriesPhonesValue.get(j).equals("±")) { // if
																														// the
																														// feature
																														// index
																														// of
																														// the
																														// match
																														// is
																														// below
																														// the
																														// feature
																														// limit,
																														// its
																														// value
																														// is
																														// reduced
																														// to
																														// 0
																														// and
																														// not
																														// counted
							nrSemiconsonantsMatchingSequenceWord2 = nrSemiconsonantsMatchingSequenceWord2 + 1;
						}
					}
				}

			}

			if (nrSemiconsonantsMatchingSequenceWord1 >= nrSemiconsonantsMatchingSequenceWord2) {
				nrSemiconsonantsMatchingSequence = nrSemiconsonantsMatchingSequenceWord1;
			} else {
				nrSemiconsonantsMatchingSequence = nrSemiconsonantsMatchingSequenceWord2;
			}
			// -------------------------

			lengthMatchingSequence = indexesMatchingSequence.get(i)[1] + 1 - indexesMatchingSequence.get(i)[0]
					- highestNrMorphemicBoundaries - nrSemiconsonantsMatchingSequence - nrVowelsMatchingSequence;

			listNrsMorphemicBoundaries = countMorphemicBoundaries(0, lengthWord1, lengthWord2, word1, word2);

			highestNrMorphemicBoundaries = listNrsMorphemicBoundaries.get(0);

			int lengthWord1MinusMorphemicBoundaries = lengthWord1 - listNrsMorphemicBoundaries.get(1);
			int lengthWord2MinusMorphemicBoundaries = lengthWord2 - listNrsMorphemicBoundaries.get(2);

			//find longest and shortest word
			double longestWord = 0.0;
			double shortestWord = 0.0;

			if (lengthWord1MinusMorphemicBoundaries >= lengthWord2MinusMorphemicBoundaries) {
				longestWord = (double) lengthWord1MinusMorphemicBoundaries;
				shortestWord = (double) lengthWord2MinusMorphemicBoundaries;
			} else {
				longestWord = (double) lengthWord2MinusMorphemicBoundaries;
				shortestWord = (double) lengthWord1MinusMorphemicBoundaries;
			}
			
			//find length of the whole match
			double lenghtAlignmentMinusMorphemicBoundaries = 0.0;
			lenghtAlignmentMinusMorphemicBoundaries = matchWord1.get(i).size()-highestNrMorphemicBoundaries;
			
			//cast nrFeatures into double
			double nrFeaturesDouble = (double) nrFeatures;
			
			// calculate global similarity score according to default function  
			
			String defaultFunctionGlobal = "((SumFeat) / (NrFeat * 7.71))";
			
			Expression gf = new ExpressionBuilder(defaultFunctionGlobal)
					.variables("SumFeat", "NrFeat", "LongestWord", "ShortestWord", "LenSeq", "LenWord1", "LenWord2", "LenAlign").build()
					.setVariable("SumFeat", sumFeaturesInCommon).setVariable("NrFeat", nrFeaturesDouble)
					.setVariable("LongestWord", longestWord)
					.setVariable("ShortestWord", shortestWord)
					.setVariable("LenSeq", (double) lengthMatchingSequence)
					.setVariable("LenWord1", lengthWord1MinusMorphemicBoundaries)
					.setVariable("LenWord2", lengthWord2MinusMorphemicBoundaries)
					.setVariable("LenAlign",lenghtAlignmentMinusMorphemicBoundaries);
			globalSimilaryScore = gf.evaluate();

			// calculate corrected global similarity score:

			if (optionFunction == 0 || optionFunction == 3 || optionFunction == 4) {
				
				// according to default function 
				
				String defaultFunctionCorrectedGlobal = "((SumFeat) / (NrFeat * 7.71)) - ((LenSeq - ShortestWord)/1.04 + ((LenAlign - LenSeq)/LenSeq)) * (1-(ShortestWord/LongestWord))";
				
				Expression e = new ExpressionBuilder(defaultFunctionCorrectedGlobal)
						.variables("SumFeat", "NrFeat", "LongestWord", "ShortestWord", "LenSeq", "LenWord1", "LenWord2", "LenAlign").build()
						.setVariable("SumFeat", sumFeaturesInCommon).setVariable("NrFeat", nrFeaturesDouble)
						.setVariable("LongestWord", longestWord)
						.setVariable("ShortestWord", shortestWord)
						.setVariable("LenSeq", (double) lengthMatchingSequence)
						.setVariable("LenWord1", lengthWord1MinusMorphemicBoundaries)
						.setVariable("LenWord2", lengthWord2MinusMorphemicBoundaries)
						.setVariable("LenAlign",lenghtAlignmentMinusMorphemicBoundaries);
				correctedGlobalSimilaryScore = e.evaluate();
				
				
			} else if (optionFunction == 1) {

				// read external function - as config file

				String fileExternalFunction = "config/external_corr_glob_sim_score_function.txt";

				String fileExternalFunctionRead = Reader.readFile(fileExternalFunction);

				String[] linesExternalFunction = fileExternalFunctionRead.split("\n");

				for (int a = 0; a < linesExternalFunction.length; a++) {

					if (linesExternalFunction[a].indexOf("%") < 0) {

						Expression e = new ExpressionBuilder(linesExternalFunction[a])
								.variables("SumFeat", "NrFeat", "LongestWord", "ShortestWord", "LenSeq", "LenWord1", "LenWord2", "LenAlign").build()
								.setVariable("SumFeat", sumFeaturesInCommon).setVariable("NrFeat", nrFeaturesDouble)
								.setVariable("LongestWord", longestWord)
								.setVariable("ShortestWord", shortestWord)
								.setVariable("LenSeq", (double) lengthMatchingSequence)
								.setVariable("LenWord1", lengthWord1MinusMorphemicBoundaries)
								.setVariable("LenWord2", lengthWord2MinusMorphemicBoundaries)
								.setVariable("LenAlign",lenghtAlignmentMinusMorphemicBoundaries);
						correctedGlobalSimilaryScore = e.evaluate();

					}
				}

			} else if (optionFunction == 2) {

				// external function - as argument
				
				Expression e = new ExpressionBuilder(externalFunction)
						.variables("SumFeat", "NrFeat", "LongestWord", "ShortestWord", "LenSeq", "LenWord1", "LenWord2", "LenAlign").build()
						.setVariable("SumFeat", sumFeaturesInCommon).setVariable("NrFeat", nrFeaturesDouble)
						.setVariable("LongestWord", longestWord)
						.setVariable("ShortestWord", shortestWord)
						.setVariable("LenSeq", (double) lengthMatchingSequence)
						.setVariable("LenWord1", lengthWord1MinusMorphemicBoundaries)
						.setVariable("LenWord2", lengthWord2MinusMorphemicBoundaries)
						.setVariable("LenAlign",lenghtAlignmentMinusMorphemicBoundaries);
				correctedGlobalSimilaryScore = e.evaluate();

			}

			// Mark wrong alignments
			if (globalSimilaryScore < 0) {

				globalSimilaryScore = 0;
				correctedGlobalSimilaryScore = 0 - correctedGlobalSimilaryScore;

			}

			globalSimilaryScore = globalSimilaryScore - coefNeg;
			correctedGlobalSimilaryScore = correctedGlobalSimilaryScore - coefNeg;

			// build results without diacritics

			List<Double> similarityScores = new ArrayList<Double>();

			similarityScores.add(globalSimilaryScore);
			similarityScores.add(correctedGlobalSimilaryScore);

			similarityScoreMatches.add(similarityScores);

			sumFeaturesInCommon = 0.0;

			line1 = "";
			line2 = "";

			indexPhoneticPairs.add(indexPhonPairsItem);
			indexPhoneticPairsValue.add(indexPhonPairsValueItem);
		}

		// =================
		// organize alignments by most similar to less similar

		similarityScoreMatchesOriginal.add(similarityScoreMatches.get(0));
		matchWord1Original.add(matchWord1.get(0));
		matchWord2Original.add(matchWord2.get(0));
		indexesMatchingSequenceOriginal.add(indexesMatchingSequence.get(0));
		indexesMorphemicBoundariesOriginal.add(indexesMorphemicBoundaries.get(0));
		attestedPhoneticPairs.add(indexPhoneticPairs.get(0));
		nrAttestationsPhoneticPairs.add(indexPhoneticPairsValue.get(0));

		for (int i = 1; i < similarityScoreMatches.size(); i++) {
			for (int n = 0; n < similarityScoreMatchesOriginal.size(); n++) {
				if (similarityScoreMatches.get(i).get(selectSimilarityScore) < similarityScoreMatchesOriginal
						.get(similarityScoreMatchesOriginal.size() - 1).get(selectSimilarityScore)) {
					similarityScoreMatchesOriginal.add(similarityScoreMatches.get(i));
					matchWord1Original.add(matchWord1.get(i));
					matchWord2Original.add(matchWord2.get(i));
					attestedPhoneticPairs.add(indexPhoneticPairs.get(i));
					nrAttestationsPhoneticPairs.add(indexPhoneticPairsValue.get(i));
					indexesMatchingSequenceOriginal.add(indexesMatchingSequence.get(i));
					indexesMorphemicBoundariesOriginal.add(indexesMorphemicBoundaries.get(i));
					break;
				} else if (similarityScoreMatches.get(i).get(selectSimilarityScore)
						.equals(similarityScoreMatchesOriginal.get(n).get(selectSimilarityScore))) {
					if (selectSimilarityScore == 0) {
						int a = 0;

						if (similarityScoreMatchesOriginal.get(n).get(1) < similarityScoreMatches.get(i).get(1)) {
							a = n;
						} else {
							a = n + 1;
						}

						similarityScoreMatchesOriginal.add(a, similarityScoreMatches.get(i));
						matchWord1Original.add(a, matchWord1.get(i));
						matchWord2Original.add(a, matchWord2.get(i));
						attestedPhoneticPairs.add(a, indexPhoneticPairs.get(i));
						nrAttestationsPhoneticPairs.add(a, indexPhoneticPairsValue.get(i));
						indexesMatchingSequenceOriginal.add(a, indexesMatchingSequence.get(i));
						indexesMorphemicBoundariesOriginal.add(a, indexesMorphemicBoundaries.get(i));
						break;

					} else {

						similarityScoreMatchesOriginal.add(n, similarityScoreMatches.get(i));
						matchWord1Original.add(n, matchWord1.get(i));
						matchWord2Original.add(n, matchWord2.get(i));
						attestedPhoneticPairs.add(n, indexPhoneticPairs.get(i));
						nrAttestationsPhoneticPairs.add(n, indexPhoneticPairsValue.get(i));
						indexesMatchingSequenceOriginal.add(n, indexesMatchingSequence.get(i));
						indexesMorphemicBoundariesOriginal.add(n, indexesMorphemicBoundaries.get(i));
						break;
					}
				} else if (similarityScoreMatches.get(i).get(selectSimilarityScore) > similarityScoreMatchesOriginal
						.get(n).get(selectSimilarityScore)) {

					similarityScoreMatchesOriginal.add(n, similarityScoreMatches.get(i));
					matchWord1Original.add(n, matchWord1.get(i));
					matchWord2Original.add(n, matchWord2.get(i));
					attestedPhoneticPairs.add(n, indexPhoneticPairs.get(i));
					nrAttestationsPhoneticPairs.add(n, indexPhoneticPairsValue.get(i));
					indexesMatchingSequenceOriginal.add(n, indexesMatchingSequence.get(i));
					indexesMorphemicBoundariesOriginal.add(n, indexesMorphemicBoundaries.get(i));
					break;
				}

			}
		}

		// build and print results in the console

		for (int i = 0; i < similarityScoreMatchesOriginal.size(); i++) {

			if (i == 0 || (similarityScoreMatchesOriginal.get(i).get(0) > 0)) {

				for (int n = 0; n < matchWord1Original.get(i).size(); n++) {

					if (displayAutomaticallyRecognizedMorphologicalBoundaries == true) {
						if (n == indexesMorphemicBoundariesOriginal.get(i)[0]) {
							line1 = line1 + "┊";
							line2 = line2 + "┊";
						}

					}

					if (matchWord1Original.get(i).get(n) == -1) {
						line1 = line1 + ":0";
					} else if (matchWord1Original.get(i).get(n) < -1 & matchWord1Original.get(i).get(n) > -1500) {
						line1 = line1 + ";" + word1.charAt((matchWord1Original.get(i).get(n)) + 1000);
					} else if (matchWord1Original.get(i).get(n) < -1500) {
						line1 = line1 + "·" + word1.charAt((matchWord1Original.get(i).get(n)) + 2000);
					} else {
						line1 = line1 + ":" + word1.charAt(matchWord1Original.get(i).get(n));
					}
					if (matchWord2Original.get(i).get(n) == -1) {
						line2 = line2 + ":0";
					} else if (matchWord2Original.get(i).get(n) < -1 & matchWord2Original.get(i).get(n) > -1500) {
						line2 = line2 + ";" + word2.charAt((matchWord2Original.get(i).get(n)) + 1000);
					} else if (matchWord2Original.get(i).get(n) < -1500) {
						line2 = line2 + "·" + word2.charAt((matchWord2Original.get(i).get(n)) + 2000);
					} else {
						line2 = line2 + ":" + word2.charAt(matchWord2Original.get(i).get(n));
					}

					if (displayAutomaticallyRecognizedMorphologicalBoundaries == true) {
						if (n == indexesMorphemicBoundariesOriginal.get(i)[1]) {
							line1 = line1 + "┊";
							line2 = line2 + "┊";
						}

					}
				}

				// ============
				// organize and return results

				// if there is no alignment

				if (similarityScoreMatchesOriginal.get(0).get(0) < 0) {

					line1 = "";
					line2 = "";

					line1 = line1 + word1.charAt(0);

					for (int a = 1; a < lengthWord1; a++) {
						line1 = line1 + ":" + word1.charAt(a);
					}
					for (int a = 0; a < lengthWord2; a++) {
						line1 = line1 + ":" + "0";
					}

					line2 = "0";
					for (int a = 1; a < lengthWord1; a++) {
						line2 = line2 + ":" + "0";
					}
					for (int a = 0; a < lengthWord2; a++) {
						line2 = line2 + ":" + word2.charAt(a);
					}

					similarityScoreMatchesOriginal.get(i).set(0, 0.0);
					similarityScoreMatchesOriginal.get(i).set(1, 0.0);

					List<String> indexPhonPairsItem = new ArrayList<>();
					List<Integer> indexPhonPairsValueItem = new ArrayList<>();

					indexPhonPairsItem.add("0 - 0");
					indexPhonPairsValueItem.add(0);

					attestedPhoneticPairs.set(i, indexPhonPairsItem);
					nrAttestationsPhoneticPairs.set(i, indexPhonPairsValueItem);
				}

				if (printResults == true) {
					System.out.println("========");
				}

				// build alignments with diacritics

				String word1WithDiacritics;
				String word2WithDiacritics;

				if (!word1Unparsed.substring(word1Unparsed.length() - 1).equals("￤")) {
					word1Unparsed = word1Unparsed + "￤";
				}

				if (!word2Unparsed.substring(word2Unparsed.length() - 1).equals("￤")) {
					word2Unparsed = word2Unparsed + "￤";
				}

				word1WithDiacritics = buildWord(line1, word1Unparsed, locationFilePhoneticDiacritics);
				word2WithDiacritics = buildWord(line2, word2Unparsed, locationFilePhoneticDiacritics);

				//

				int firstWord1 = 0;
				int firstWord2 = 0;

				// vows = 2
				// vows = 0

				boolean foundWord1 = false;
				boolean foundWord2 = false;

				for (int f = 0; f < classCategoriesPhonesValue.size(); f++) {

					if (line1.substring(1, 2).equals(classCategoriesPhonesCharacter.get(f))) {
						firstWord1 = Integer.parseInt(classCategoriesPhonesValue.get(f).replaceAll("-", "0").replaceAll("±", "1").replaceAll("\\+", "2"));
						foundWord1 = true;
					}

					if (line2.substring(1, 2).equals(classCategoriesPhonesCharacter.get(f))) {
						firstWord2 = Integer.parseInt(classCategoriesPhonesValue.get(f).replaceAll("-", "0").replaceAll("±", "1").replaceAll("\\+", "2"));
						foundWord2 = true;
					}

				}

				if (foundWord1 == false) {
					firstWord1 = -1;
				}
				;
				if (foundWord2 == false) {
					firstWord2 = -1;
				}
				;

				if ((firstWord1 == 2 & firstWord2 != 2 & firstWord2 > -1)) {

					String[] lettersWord1 = line1.split(":");
					line1 = ":" + lettersWord1[1].substring(0, 1) + ":0┊";

					for (int t = 2; t < lettersWord1.length; t++) {
						line1 = line1 + ":" + lettersWord1[t];
					}

					String[] lettersWord1WithDiacritics = word1WithDiacritics.split("	");
					word1WithDiacritics = lettersWord1WithDiacritics[0].substring(0, 1) + "	0┊";

					for (int t = 1; t < lettersWord1WithDiacritics.length; t++) {
						word1WithDiacritics = word1WithDiacritics + "	" + lettersWord1WithDiacritics[t];
					}

					line2 = ":0" + line2;
					word2WithDiacritics = "0	" + word2WithDiacritics;

				}

				if ((firstWord2 == 2 & firstWord1 != 2 & firstWord1 > -1)) {

					String[] lettersWord2 = line2.split(":");
					line2 = ":" + lettersWord2[1].substring(0, 1) + ":0┊";

					for (int t = 2; t < lettersWord2.length; t++) {
						line2 = line2 + ":" + lettersWord2[t];
					}

					String[] lettersWord2WithDiacritics = word2WithDiacritics.split("	");
					word2WithDiacritics = lettersWord2WithDiacritics[0].substring(0, 1) + "	0┊";

					for (int t = 1; t < lettersWord2WithDiacritics.length; t++) {
						word2WithDiacritics = word2WithDiacritics + "	" + lettersWord2WithDiacritics[t];
					}

					line1 = ":0" + line1;
					word1WithDiacritics = "0	" + word1WithDiacritics;

				}

				// --- last

				// vows = 2
				// vows = 0

				int lastWord1 = 0;
				int lastWord2 = 0;

				boolean foundWord1_1 = false;
				boolean foundWord2_1 = false;

				for (int f = 0; f < classCategoriesPhonesValue.size(); f++) {

					if (line1.substring(line1.length() - 3, line1.length() - 2)
							.equals(classCategoriesPhonesCharacter.get(f))) {
						lastWord1 = Integer.parseInt(classCategoriesPhonesValue.get(f).replaceAll("-", "0").replaceAll("±", "1").replaceAll("\\+", "2"));
						foundWord1_1 = true;
					}

					if (line2.substring(line2.length() - 3, line2.length() - 2)
							.equals(classCategoriesPhonesCharacter.get(f))) {
						lastWord2 = Integer.parseInt(classCategoriesPhonesValue.get(f).replaceAll("-", "0").replaceAll("±", "1").replaceAll("\\+", "2"));
						foundWord2_1 = true;
					}

				}

				if (foundWord1_1 == false) {
					lastWord1 = -1;
				}
				;
				if (foundWord2_1 == false) {
					lastWord2 = -1;
				}
				;

				
				
				//===
				
				if ((lastWord1 == 2 & lastWord2 != 2 & lastWord2 > -1)) {

					String[] lettersWord1 = line1.split(":");
					String[] lettersWord1WithDiacritics = word1WithDiacritics.split("	");

					String[] lettersWord2 = line2.split(":");
					String[] lettersWord2WithDiacritics = word2WithDiacritics.split("	");

					line1 = "";
					for (int t = 1; t < lettersWord1.length - 2; t++) {
						line1 = line1 + ":" + lettersWord1[t];
					}

					line1 = line1.replace(":┊:", "┊:");

					line1 = line1 + ":" + lettersWord1[lettersWord1.length - 2].substring(0, 1) + ":0:￤";// .substring(letters_word.length-1,
																											// letters_word.length)
																											// +
																											// "┊:0:￤";

					word1WithDiacritics = lettersWord1WithDiacritics[0];
					for (int t = 1; t < lettersWord1WithDiacritics.length - 2; t++) {
						word1WithDiacritics = word1WithDiacritics + "	" + lettersWord1WithDiacritics[t];
					}

					word1WithDiacritics = word1WithDiacritics + "	"
							+ lettersWord1WithDiacritics[lettersWord1WithDiacritics.length - 2].substring(0, 1)
							+ "	0	￤";

					line2 = "";

					for (int t = 1; t < lettersWord2.length - 2; t++) {
						line2 = line2 + ":" + lettersWord2[t];
					}
					line2 = line2.replace(":┊:", "┊:");

					line2 = line2 + ":0:" + lettersWord2[lettersWord2.length - 2]+ ":￤"; //.substring(0, 1) + ":￤";

					// ----
					word2WithDiacritics = lettersWord2WithDiacritics[0];

					for (int t = 1; t < lettersWord2WithDiacritics.length - 2; t++) {
						word2WithDiacritics = word2WithDiacritics + "	" + lettersWord2WithDiacritics[t];
					}

					word2WithDiacritics = word2WithDiacritics + "	0	"
							+ lettersWord2WithDiacritics[lettersWord2WithDiacritics.length - 2] + "	￤"; //.substring(0, 1) + "	￤";

					
					
				}

				// ---------

				if ((lastWord2 == 2 & lastWord1 != 2 & lastWord1 > -1)) {

					String[] lettersWord1 = line1.split(":");
					String[] lettersWord1WithDiacritics = word2WithDiacritics.split("	");

					String[] lettersWord2 = line2.split(":");
					String[] lettersWord2WithDiacritics = word1WithDiacritics.split("	");

					line1 = "";
					for (int t = 1; t < lettersWord1.length - 2; t++) {
						line1 = line1 + ":" + lettersWord1[t];
					}

					line1 = line1.replace(":┊:", "┊:");

					line1 = line1 + ":" + lettersWord1[lettersWord1.length - 2].substring(0, 1) + ":0:￤";// .substring(letters_word.length-1,
																											// letters_word.length)
																											// +
																											// "┊:0:￤";

					word1WithDiacritics = lettersWord2WithDiacritics[0];
					for (int t = 1; t < lettersWord2WithDiacritics.length - 2; t++) {
						word1WithDiacritics = word1WithDiacritics + "	" + lettersWord2WithDiacritics[t];
					}

					word1WithDiacritics = word1WithDiacritics + "	"
							+ lettersWord2WithDiacritics[lettersWord2WithDiacritics.length - 2].substring(0, 1)
							+ "	0	￤";

					line2 = "";

					for (int t = 1; t < lettersWord2.length - 2; t++) {
						line2 = line2 + ":" + lettersWord2[t];
					}
					line2 = line2.replace(":┊:", "┊:");

					line2 = line2 + ":0:" + lettersWord2[lettersWord2.length - 2].substring(0, 1) + ":￤";

					// ----
					word2WithDiacritics = lettersWord1WithDiacritics[0];

					for (int t = 1; t < lettersWord1WithDiacritics.length - 2; t++) {
						word2WithDiacritics = word2WithDiacritics + "	" + lettersWord1WithDiacritics[t];
					}

					word2WithDiacritics = word2WithDiacritics + "	0	"
							+ lettersWord1WithDiacritics[lettersWord1WithDiacritics.length - 2] + "	￤"; //.substring(0, 1) + "	￤";

				}

				if (printResults == true) {
					System.out.println(word1WithDiacritics);
					System.out.println(word2WithDiacritics);
					System.out.println(line1);
					System.out.println(line2);
					System.out.println("Global Similarity Score:");
					System.out.println(similarityScoreMatchesOriginal.get(i).get(0));
					System.out.println("Corrected Global Similarity Score:");
					System.out.println(similarityScoreMatchesOriginal.get(i).get(1));
					System.out.println("Attested Pairs:");
					System.out.println(attestedPhoneticPairs.get(i));
					System.out.println("Number of Attestations of the Attested Pairs:");
					System.out.println(nrAttestationsPhoneticPairs.get(i));
					System.out.println("----");
				}

				Alignment newAlignment = new Alignment();
				
				newAlignment.word1_WithDiacritics(word1WithDiacritics);
				newAlignment.word2_WithDiacritics(word2WithDiacritics);
				newAlignment.word1_WithoutDiacritics(line1);
				newAlignment.word2_WithoutDiacritics(line2);
				newAlignment.globalSimilarityScore(similarityScoreMatchesOriginal.get(i).get(0));
				newAlignment.correctedGlobalSimilarityScore(similarityScoreMatchesOriginal.get(i).get(1));
				newAlignment.phoneticPairs(attestedPhoneticPairs.get(i));
				newAlignment.nrAttestationsPhoneticPairs(nrAttestationsPhoneticPairs.get(i));
				

				results.add(newAlignment);


				line1 = "";
				line2 = "";
			}
		}

		return results;
	}

	public static double calculateFeaturesInCommon(int option, double sumFeaturesInCommon,
			double valuePairFromComparisonMatrix) {

		if (option == 0) {
			// nothing
		} else if (option == 1) {
			sumFeaturesInCommon = sumFeaturesInCommon + valuePairFromComparisonMatrix;
		} else if (option == 2) {
			sumFeaturesInCommon = sumFeaturesInCommon - 1;
		}

		return sumFeaturesInCommon;
	}

	public static String findTypeOfMatch(int i, int n, List<List<Integer>> matchWord1, List<List<Integer>> matchWord2,
			String word1, String word2) {

		String typeLetterWord1 = "";
		String typeLetterWord2 = "";
		String typeCombined = "";

		// ----

		if (matchWord1.get(i).get(n) <= -1) {

			typeLetterWord1 = "0";

		} else if (matchWord1.get(i).get(n) > -1) {

			if (String.valueOf(word1.charAt((matchWord1.get(i).get(n)))).equals("￤")) {

				typeLetterWord1 = "￤";

			} else if (!String.valueOf(word1.charAt((matchWord1.get(i).get(n)))).equals("￤")) {

				typeLetterWord1 = "x";
			}
		}

		// -----

		if (matchWord2.get(i).get(n) <= -1) {

			typeLetterWord2 = "0";

		} else if (matchWord2.get(i).get(n) > -1) {

			if (String.valueOf(word2.charAt((matchWord2.get(i).get(n)))).equals("￤")) {

				typeLetterWord2 = "￤";

			} else if (!String.valueOf(word2.charAt((matchWord2.get(i).get(n)))).equals("￤")) {

				typeLetterWord2 = "x";
			}
		}

		typeCombined = typeLetterWord1 + typeLetterWord2;

		return typeCombined;
	}

	public static List<Integer> countMorphemicBoundaries(int a, int b, int c, String word1, String word2) {// b limite
																											// conto non
																											// incluso,
																											// ultimo è
																											// b-1 // b
																											// per
																											// parola 1,
																											// c per
																											// parola 2

		int nrMorphemicBoundariesWord1 = 0;
		int nrMorphemicBoundariesWord2 = 0;
		int highestNrMorphemicBoundaries = 0;

		for (int i = a; i < b; i++) {

			if (String.valueOf(word1.charAt(i)).equals("￤")) {
				nrMorphemicBoundariesWord1++;
			}
		}

		for (int i = a; i < c; i++) {
			if (String.valueOf(word2.charAt(i)).equals("￤")) {
				nrMorphemicBoundariesWord2++;
			}

		}

		if (nrMorphemicBoundariesWord1 >= nrMorphemicBoundariesWord2) {

			highestNrMorphemicBoundaries = nrMorphemicBoundariesWord1;
		} else {

			highestNrMorphemicBoundaries = nrMorphemicBoundariesWord2;

		}

		List<Integer> resultsNrsMorphemicBoundaries = new ArrayList<Integer>();

		resultsNrsMorphemicBoundaries.add(highestNrMorphemicBoundaries);
		resultsNrsMorphemicBoundaries.add(nrMorphemicBoundariesWord1);
		resultsNrsMorphemicBoundaries.add(nrMorphemicBoundariesWord2);

		return resultsNrsMorphemicBoundaries;

	}

	public static String buildWord(String word, String unparsedWord, String locationFilePhoneticDiacritics) {

		// read diacritics

		List<String> filePhoneticDiacriticsLines = new ArrayList<>();

		String filePhoneticDiacriticsRead = Reader.readFile(locationFilePhoneticDiacritics);

		String[] filePhoneticDiacriticsLinesProvisional = filePhoneticDiacriticsRead.split("\n");

		for (int i = 0; i < filePhoneticDiacriticsLinesProvisional.length; i++) {

			if (filePhoneticDiacriticsLinesProvisional[i].indexOf("%") < 0) {
				filePhoneticDiacriticsLines.add(filePhoneticDiacriticsLinesProvisional[i]);
			}
		}

		filePhoneticDiacriticsLines.add("̯	2	0;1");// marked with 2 to distinguish it from normal diacritics
		filePhoneticDiacriticsLines.add("͜	3	0;2");// marked with 3 to distinguish it from normal diacritics

		List<String> valuesPhoneticDiacritics = new ArrayList<>();
		List<String> classPhoneticDiacritics = new ArrayList<>();

		List<List<Integer>> phoneticDiacritics_FeaturesChanged = new ArrayList<>();
		List<List<String>> phoneticDiacritics_FeaturesChangedValue = new ArrayList<>();

		for (int i = 0; i < filePhoneticDiacriticsLines.size(); i++) {
			String[] diacrititics_Split1;
			String[] diacrititics_Split2;
			String[] diacrititics_Split3;

			List<Integer> featuresChanged = new ArrayList<>();
			List<String> featuresChangedValue = new ArrayList<>();

			diacrititics_Split1 = filePhoneticDiacriticsLines.get(i).split("	");
			diacrititics_Split2 = diacrititics_Split1[2].split(" ");

			valuesPhoneticDiacritics.add(diacrititics_Split1[0]);
			classPhoneticDiacritics.add(diacrititics_Split1[1]);

			for (int n = 0; n < diacrititics_Split2.length; n++) {

				diacrititics_Split3 = diacrititics_Split2[n].split(";");
				featuresChanged.add(Integer.parseInt(diacrititics_Split3[0]));
				featuresChangedValue.add(diacrititics_Split3[1]);

			}

			phoneticDiacritics_FeaturesChanged.add(featuresChanged);
			phoneticDiacritics_FeaturesChangedValue.add(featuresChangedValue);

		}

		String segment = "";
		String[] splittedWord = word.split("(?!^)");
		String[] splittedUnparsedWord = unparsedWord.split("(?!^)");

		for (int r = 0; r < splittedUnparsedWord.length; r++) {

			if (splittedUnparsedWord[r].equals("͜")) {
				String letter_A = splittedUnparsedWord[r - 1];
				splittedUnparsedWord[r - 1] = splittedUnparsedWord[r + 1];
				splittedUnparsedWord[r + 1] = letter_A;
			}

		}

		String result = "";
		String sequenceInitialDiacritics = "";
		
		for (int z = 0; z < unparsedWord.length(); z ++) {
			boolean initialDiacritic = false;
			for (int m = 0; m < classPhoneticDiacritics.size(); m++) {
				
				if (classPhoneticDiacritics.get(m).equals("1")) {

				
					if (String.valueOf(unparsedWord.charAt(z)).equals(valuesPhoneticDiacritics.get(m))) {
						initialDiacritic = true;
						sequenceInitialDiacritics = sequenceInitialDiacritics + valuesPhoneticDiacritics.get(m);
					}
				
				}
						
			}
			
			if (initialDiacritic == false) {
				break;
			}
			
		}	
		
		
		
		
		
		int g = splittedUnparsedWord.length - 1;

		for (int i = splittedWord.length - 1; i > -1; i--) {
			if (splittedWord[i].equals("0")) {
				segment = "	0" + segment;

			} else if (splittedWord[i].equals("┊")) {
				segment = "┊" + segment;

			} else if (splittedWord[i].equals("·")) {
				segment = "·" + segment;

			} else if (splittedWord[i].equals(";")) {
				segment = ";" + segment;

			} else if (!splittedWord[i].equals(":")) {

				for (int n = g; n > -1; n--) {
					segment = splittedUnparsedWord[n] + segment;
					if (splittedWord[i].equals(splittedUnparsedWord[n])) {
						if (i > 0) {
							result = "	" + segment + result;
						} else {
							result = segment + result;
						}
						segment = "";
						g = n - 1;
						break;
					}
				}
			}
		}
		result = segment + result;
		result = result.replaceFirst("	", "");
		
		
		
		result = sequenceInitialDiacritics + result;
		
		
		
		result = result.replaceAll(sequenceInitialDiacritics + "┊", "┊" + sequenceInitialDiacritics);
		result = result.replaceAll(sequenceInitialDiacritics + "0", "0" + sequenceInitialDiacritics);	
			
			
			for (int m = 0; m < classPhoneticDiacritics.size(); m++) {
				String precedingDiacritic = "";
				
				if (classPhoneticDiacritics.get(m).equals("1")) {

					for (int z = 0; z < result.length(); z ++) {
						
					precedingDiacritic = valuesPhoneticDiacritics.get(m);
					
					result = result.replace(precedingDiacritic + "	0", "	0" + precedingDiacritic);
					
					result = result.replace(precedingDiacritic + "┊	0", "┊	0" + precedingDiacritic);
					
					
					}
							
				}
				
			}
			
		
		

		for (int i = 0; i < result.length(); i++) {

			if (result.substring(i, i + 1).equals("͜")) {
				String letter_A = result.substring(i - 1, i);
				result = result.substring(0, i - 1) + result.substring(i + 1, i + 2) + "͜" + letter_A
						+ result.substring(i + 2, result.length());
				i = i + 1;
			}

		}

		
		
		for (int z = 0; z < result.length(); z ++) {
		
		for (int i = 0; i < classPhoneticDiacritics.size(); i++) {
			if (classPhoneticDiacritics.get(i).equals("1")) {

				result = result.replace(valuesPhoneticDiacritics.get(i) + "	",
						"	" + valuesPhoneticDiacritics.get(i));
				result = result.replace(valuesPhoneticDiacritics.get(i) + "┊	",
						"┊	" + valuesPhoneticDiacritics.get(i));
			}
		}

		}
		
		

		return result;
	}
}
