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

public class FAAL {

	/**
	 * Method containing the whole FAAL algorithm, using default values (see online
	 * documentation for their values).
	 * 
	 * @param word1
	 *            (String): IPA transcription of the first word to be aligned.
	 * @param word2
	 *            (String): IPA transcription of the second word to be aligned.
	 * @param printResults
	 *            (Boolean): print the results in the console - True = print all the
	 *            alignments found; False = print only the best alignment.
	 *            <p>
	 * @return Result output: List&lt;Alignment&gt;
	 *         <p>
	 *         Each <i>Alignment</i > item within the List corresponds to an
	 *         alignment obtained. They are organized according to their Global or
	 *         Corrected Global Similarity score (see documentation online). The
	 *         items stored in the <i>Alignment</i > class can be called as follow:
	 *         <p>
	 *         <p>
	 *         0. .getWord1_WithDiacritics() - String: Returns the aligned word
	 *         1, with diacritics.
	 *         <p>
	 *         1. .getWord2_WithDiacritics() - String: Returns the aligned word
	 *         2, with diacritics.
	 *         <p>
	 *         2. .getWord1_WithoutDiacritics() - String: Returns the aligned
	 *         word 1, without diacritics.
	 *         <p>
	 *         3. .getWord2_WithoutDiacritics() - String: Returns the aligned
	 *         word 2, without diacritics.
	 *         <p>
	 *         4. .getGlobalSimilarityScore() - Double: Returns the Global
	 *         Similarity Score.
	 *         <p>
	 *         5. .getCorrectedGlobalSimilarityScore() - Double: Returns the
	 *         Corrected Global Similarity Score.
	 *         <p>
	 *         6. .getPhoneticPairs() - List&lt;String&gt;: Returns the list of
	 *         phonetic pairs attested within the alignment. Each item on the list
	 *         corresponds to a phonetic pair, and it is stored as a string with the
	 *         following syntax: "phoneme_A - phoneme_B"
	 *         <p>
	 *         7. .getNrAttestationsPhoneticPairs() - List&lt;Integer&gt;: Returns
	 *         the number of attestations within the alignment for each phonetic
	 *         pair of point 6. here above.
	 *         <p>
	 */
	public static List<Alignment> align(String word1, String word2, Boolean printResults) {

		List<Alignment> alignResults = new ArrayList<>();

		List<String> configIPA = new ArrayList<String>();

		ConfigIPA settingsConfigIPA = new ConfigIPA();

		// Configs files IPA_Parser:

		configIPA = settingsConfigIPA.getConfigIPA();

		// Configs files Matcher:

		List<Boolean> matcherConfig1 = new ArrayList<Boolean>();
		List<Integer> matcherConfig2 = new ArrayList<Integer>();
		List<String> matcherConfig3 = new ArrayList<String>();
		List<Double> matcherConfig4 = new ArrayList<Double>();

		MatcherConfig1 settingsMatcherConfig1 = new MatcherConfig1();
		MatcherConfig2 settingsMatcherConfig2 = new MatcherConfig2();
		MatcherConfig3 settingsMatcherConfig3 = new MatcherConfig3();
		MatcherConfig4 settingsMatcherConfig4 = new MatcherConfig4();

		//Print results:
		
		settingsMatcherConfig1.printResults(printResults);
		
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

		alignResults = align(word1, word2, optionFunction, externalFunction, configIPA, matcherConfig1,
				matcherConfig2, matcherConfig3, matcherConfig4);

		return alignResults;

	}

	// =========================
	// =========================
	// =========================
	/**
	 * Method containing the whole FAAL algorithm, using default values but with the
	 * option of using an external Global Corrected Similarity Score function (see
	 * online documentation for their values).
	 * 
	 * @param word1
	 *            (String): IPA transcription of the first word to be aligned.
	 * @param word2
	 *            (String): IPA transcription of the second word to be aligned.
	 * @param printResults
	 *            (Boolean): print the results in the console - True = print all the
	 *            alignments found; False = print only the best alignment.
	 * @param optionFunction
	 *            (Integer): select if using or not an external function - 0 = use default settings 
	 *            (with automatic selection of Global or Corrected Global Similarity Score); 
	 *            1 = use external function from config. folder; 2 = use function provided 
	 *            as next argument; 3 = use only the Global Similarity Score; 4 = use only the
	 *            default Corrected Global Similarity Score
	 *            <p>
	 * @return Result output: List&lt;Alignment&gt;
	 *         <p>
	 *         Each <i>Alignment</i > item within the List corresponds to an
	 *         alignment obtained. They are organized according to their Global or
	 *         Corrected Global Similarity score (see documentation online). The
	 *         items stored in the <i>Alignment</i > class can be called as follow:
	 *         <p>
	 *         <p>
	 *         0. .getWord1_WithDiacritics() - String: Returns the aligned word
	 *         1, with diacritics.
	 *         <p>
	 *         1. .getWord2_WithDiacritics() - String: Returns the aligned word
	 *         2, with diacritics.
	 *         <p>
	 *         2. .getWord1_WithoutDiacritics() - String: Returns the aligned
	 *         word 1, without diacritics.
	 *         <p>
	 *         3. .getWord2_WithoutDiacritics() - String: Returns the aligned
	 *         word 2, without diacritics.
	 *         <p>
	 *         4. .getGlobalSimilarityScore() - Double: Returns the Global
	 *         Similarity Score.
	 *         <p>
	 *         5. .getCorrectedGlobalSimilarityScore() - Double: Returns the
	 *         Corrected Global Similarity Score.
	 *         <p>
	 *         6. .getPhoneticPairs() - List&lt;String&gt;: Returns the list of
	 *         phonetic pairs attested within the alignment. Each item on the list
	 *         corresponds to a phonetic pair, and it is stored as a string with the
	 *         following syntax: "phoneme_A - phoneme_B"
	 *         <p>
	 *         7. .getNrAttestationsPhoneticPairs() - List&lt;Integer&gt;: Returns
	 *         the number of attestations within the alignment for each phonetic
	 *         pair of point 6. here above.
	 *         <p>
	 */
	public static List<Alignment> align(String word1, String word2, Boolean printResults,
			Integer optionFunction) {

		List<Alignment> alignResults = new ArrayList<>();

		List<String> configIPA = new ArrayList<String>();

		ConfigIPA settingsConfigIPA = new ConfigIPA();

		// Configs files IPA_Parser:

		configIPA = settingsConfigIPA.getConfigIPA();

		// Configs files Matcher:

		List<Boolean> matcherConfig1 = new ArrayList<Boolean>();
		List<Integer> matcherConfig2 = new ArrayList<Integer>();
		List<String> matcherConfig3 = new ArrayList<String>();
		List<Double> matcherConfig4 = new ArrayList<Double>();

		MatcherConfig1 settingsMatcherConfig1 = new MatcherConfig1();
		MatcherConfig2 settingsMatcherConfig2 = new MatcherConfig2();
		MatcherConfig3 settingsMatcherConfig3 = new MatcherConfig3();
		MatcherConfig4 settingsMatcherConfig4 = new MatcherConfig4();
		
		// Parameters concerning the use of an external Corrected Global Similarity
		// Score function

		
		if (optionFunction == 3) {
			settingsMatcherConfig1.autoDetectMorphBound(false);
			settingsMatcherConfig2.similarityScore(0);
		}
		
		
		if (optionFunction == 4) {
			settingsMatcherConfig1.autoDetectMorphBound(false);
			settingsMatcherConfig2.similarityScore(1);
		}

		// Since no function is passed as argument, the parameter externalFunction is
		// "".
		String externalFunction = "";
		
		//Print results:
		
		settingsMatcherConfig1.printResults(printResults);
				
		// Configs files:

		matcherConfig1 = settingsMatcherConfig1.getmatcherConfig1();
		matcherConfig2 = settingsMatcherConfig2.getmatcherConfig2();
		matcherConfig3 = settingsMatcherConfig3.getmatcherConfig3();
		matcherConfig4 = settingsMatcherConfig4.getmatcherConfig4();



		alignResults = align(word1, word2, optionFunction, externalFunction, configIPA, matcherConfig1,
				matcherConfig2, matcherConfig3, matcherConfig4);

		return alignResults;

	}

	// =========================
	// =========================
	// =========================
	/**
	 * Method containing the whole FAAL algorithm, using default values but with the
	 * option of using an external Global Corrected Similarity Score function (see
	 * online documentation for their values).
	 * 
	 * @param word1
	 *            (String): IPA transcription of the first word to be aligned.
	 * @param word2
	 *            (String): IPA transcription of the second word to be aligned.
	 * @param printResults
	 *            (Boolean): print the results in the console - True = print all the
	 *            alignments found; False = print only the best alignment.
	 * @param optionFunction
	 *            (Integer): select if using or not an external function - 0 = use default settings 
	 *            (with automatic selection of Global or Corrected Global Similarity Score); 
	 *            1 = use external function from config. folder; 2 = use function provided 
	 *            as next argument; 3 = use only the Global Similarity Score; 4 = use only the
	 *            default Corrected Global Similarity Score
	 * @param externalFunction
	 *            (String): alternative function to use for the calculation of the
	 *            Corrected Global Similarity Score. See readme.md and
	 *            config/external_corr_glob_sim_score_function.txt for details about
	 *            the options and syntax of the formula.
	 *            <p>
	 * @return Result output: List&lt;Alignment&gt;
	 *         <p>
	 *         Each <i>Alignment</i > item within the List corresponds to an
	 *         alignment obtained. They are organized according to their Global or
	 *         Corrected Global Similarity score (see documentation online). The
	 *         items stored in the <i>Alignment</i > class can be called as follow:
	 *         <p>
	 *         <p>
	 *         0. .getWord1_WithDiacritics() - String: Returns the aligned word
	 *         1, with diacritics.
	 *         <p>
	 *         1. .getWord2_WithDiacritics() - String: Returns the aligned word
	 *         2, with diacritics.
	 *         <p>
	 *         2. .getWord1_WithoutDiacritics() - String: Returns the aligned
	 *         word 1, without diacritics.
	 *         <p>
	 *         3. .getWord2_WithoutDiacritics() - String: Returns the aligned
	 *         word 2, without diacritics.
	 *         <p>
	 *         4. .getGlobalSimilarityScore() - Double: Returns the Global
	 *         Similarity Score.
	 *         <p>
	 *         5. .getCorrectedGlobalSimilarityScore() - Double: Returns the
	 *         Corrected Global Similarity Score.
	 *         <p>
	 *         6. .getPhoneticPairs() - List&lt;String&gt;: Returns the list of
	 *         phonetic pairs attested within the alignment. Each item on the list
	 *         corresponds to a phonetic pair, and it is stored as a string with the
	 *         following syntax: "phoneme_A - phoneme_B"
	 *         <p>
	 *         7. .getNrAttestationsPhoneticPairs() - List&lt;Integer&gt;: Returns
	 *         the number of attestations within the alignment for each phonetic
	 *         pair of point 6. here above.
	 *         <p>
	 */
	public static List<Alignment> align(String word1, String word2, Boolean printResults,
			Integer optionFunction, String externalFunction) {

		List<Alignment> alignResults = new ArrayList<>();

		List<String> configIPA = new ArrayList<String>();

		ConfigIPA settingsConfigIPA = new ConfigIPA();

		// Configs files IPA_Parser:

		configIPA = settingsConfigIPA.getConfigIPA();

		// Configs files Matcher:

		List<Boolean> matcherConfig1 = new ArrayList<Boolean>();
		List<Integer> matcherConfig2 = new ArrayList<Integer>();
		List<String> matcherConfig3 = new ArrayList<String>();
		List<Double> matcherConfig4 = new ArrayList<Double>();

		MatcherConfig1 settingsMatcherConfig1 = new MatcherConfig1();
		MatcherConfig2 settingsMatcherConfig2 = new MatcherConfig2();
		MatcherConfig3 settingsMatcherConfig3 = new MatcherConfig3();
		MatcherConfig4 settingsMatcherConfig4 = new MatcherConfig4();
		
		// Parameters concerning the use of an external Corrected Global Similarity
				// Score function
				
				if (optionFunction == 3) {
					settingsMatcherConfig1.autoDetectMorphBound(false);
					settingsMatcherConfig2.similarityScore(0);
				}
				
				if (optionFunction == 4) {
					settingsMatcherConfig1.autoDetectMorphBound(false);
					settingsMatcherConfig2.similarityScore(1);
				}
				
		//Print results:
		
		settingsMatcherConfig1.printResults(printResults);

		// Configs files:

		matcherConfig1 = settingsMatcherConfig1.getmatcherConfig1();
		matcherConfig2 = settingsMatcherConfig2.getmatcherConfig2();
		matcherConfig3 = settingsMatcherConfig3.getmatcherConfig3();
		matcherConfig4 = settingsMatcherConfig4.getmatcherConfig4();

		alignResults = align(word1, word2, optionFunction, externalFunction, configIPA, matcherConfig1,
				matcherConfig2, matcherConfig3, matcherConfig4);

		return alignResults;

	}

	// =========================
	// =========================
	// =========================
	/**
	 * Method containing the whole FAAL algorithm, using personalized values (see
	 * online documentation for detailed description).
	 * 
	 * @param word1
	 *            (String): IPA transcription of the first word to be aligned.
	 * @param word2
	 *            (String): IPA transcription of the second word to be aligned.
	 * @param optionFunction
	 *            (Integer): select if using or not an external function - 0 = do
	 *            not use any external function; 1 = use external function from
	 *            config. folder; 2 = use function provided as next argument
	 * @param configIPA
	 *            (List&lt;String&gt;): String array list with variables for the
	 *            configuration of the IPA_parser.IPA_parser_new module. Instances
	 *            of this list can be initialized through the <i>ConfigIPA</i> class
	 *            - see the corresponding JavaDoc, and the documentation and
	 *            examples online.
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
	 *            <p>
	 * @return Result output: List&lt;Alignment&gt;
	 *         <p>
	 *         Each <i>Alignment</i > item within the List corresponds to an
	 *         alignment obtained. They are organized according to their Global or
	 *         Corrected Global Similarity score (see documentation online). The
	 *         items stored in the <i>Alignment</i > class can be called as follow:
	 *         <p>
	 *         <p>
	 *         0. .getWord1_WithDiacritics() - String: Returns the aligned word
	 *         1, with diacritics.
	 *         <p>
	 *         1. .getWord2_WithDiacritics() - String: Returns the aligned word
	 *         2, with diacritics.
	 *         <p>
	 *         2. .getWord1_WithoutDiacritics() - String: Returns the aligned
	 *         word 1, without diacritics.
	 *         <p>
	 *         3. .getWord2_WithoutDiacritics() - String: Returns the aligned
	 *         word 2, without diacritics.
	 *         <p>
	 *         4. .getGlobalSimilarityScore() - Double: Returns the Global
	 *         Similarity Score.
	 *         <p>
	 *         5. .getCorrectedGlobalSimilarityScore() - Double: Returns the
	 *         Corrected Global Similarity Score.
	 *         <p>
	 *         6. .getPhoneticPairs() - List&lt;String&gt;: Returns the list of
	 *         phonetic pairs attested within the alignment. Each item on the list
	 *         corresponds to a phonetic pair, and it is stored as a string with the
	 *         following syntax: "phoneme_A - phoneme_B"
	 *         <p>
	 *         7. .getNrAttestationsPhoneticPairs() - List&lt;Integer&gt;: Returns
	 *         the number of attestations within the alignment for each phonetic
	 *         pair of point 6. here above.
	 *         <p>
	 */
	public static List<Alignment> align(String word1, String word2, Integer optionFunction,
			List<String> configIPA, List<Boolean> matcherConfig1, List<Integer> matcherConfig2,
			List<String> matcherConfig3, List<Double> matcherConfig4) {

		ParsedWord stringParsed = new ParsedWord();
		List<Alignment> alignResults = new ArrayList<>();
		String externalFunction = "";

		// IPA-parser
		stringParsed = IPA_Parser.parseIPA(word1, word2, configIPA);

		String word1Parsed = (String) stringParsed.getParsedWord1();
		String word2Parsed = (String) stringParsed.getParsedWord2();

		String word1Unparsed = word1;
		String word2Unparsed = word2;

		int matrixResultComparison_WithSaliences[][] = stringParsed.getMatrixResultComparison_WithSaliences();
		int matrixResultComparison_WithoutSaliences[][] = stringParsed.getMatrixResultComparison_WithoutSaliences();

		// matcher
		alignResults = Matcher.match(word1Parsed, word2Parsed, matrixResultComparison_WithSaliences,
				matrixResultComparison_WithoutSaliences, word1Unparsed, word2Unparsed, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4, optionFunction, externalFunction);

		return alignResults;

	}

	// =========================
	// =========================
	// =========================
	/**
	 * Method containing the whole FAAL algorithm, using personalized values (see
	 * online documentation for detailed description).
	 * 
	 * @param word1
	 *            (String): IPA transcription of the first word to be aligned.
	 * @param word2
	 *            (String): IPA transcription of the second word to be aligned.
	 * @param optionFunction
	 *            (Integer): select if using or not an external function - 0 = do
	 *            not use any external function; 1 = use external function from
	 *            config. folder; 2 = use function provided as next argument
	 * @param externalFunction
	 *            (String): alternative function to use for the calculation of the
	 *            Corrected Global Similarity Score. See readme.md and
	 *            config/external_corr_glob_sim_score_function.txt for details about
	 *            the options and syntax of the formula.
	 * @param configIPA
	 *            (List&lt;String&gt;): String array list with variables for the
	 *            configuration of the IPA_parser.IPA_parser_new module. Instances
	 *            of this list can be initialized through the <i>ConfigIPA</i> class
	 *            - see the corresponding JavaDoc, and the documentation and
	 *            examples online.
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
	 *            <p>
	 * @return Result output: List&lt;Alignment&gt;
	 *         <p>
	 *         Each <i>Alignment</i > item within the List corresponds to an
	 *         alignment obtained. They are organized according to their Global or
	 *         Corrected Global Similarity score (see documentation online). The
	 *         items stored in the <i>Alignment</i > class can be called as follow:
	 *         <p>
	 *         <p>
	 *         0. .getWord1_WithDiacritics() - String: Returns the aligned word
	 *         1, with diacritics.
	 *         <p>
	 *         1. .getWord2_WithDiacritics() - String: Returns the aligned word
	 *         2, with diacritics.
	 *         <p>
	 *         2. .getWord1_WithoutDiacritics() - String: Returns the aligned
	 *         word 1, without diacritics.
	 *         <p>
	 *         3. .getWord2_WithoutDiacritics() - String: Returns the aligned
	 *         word 2, without diacritics.
	 *         <p>
	 *         4. .getGlobalSimilarityScore() - Double: Returns the Global
	 *         Similarity Score.
	 *         <p>
	 *         5. .getCorrectedGlobalSimilarityScore() - Double: Returns the
	 *         Corrected Global Similarity Score.
	 *         <p>
	 *         6. .getPhoneticPairs() - List&lt;String&gt;: Returns the list of
	 *         phonetic pairs attested within the alignment. Each item on the list
	 *         corresponds to a phonetic pair, and it is stored as a string with the
	 *         following syntax: "phoneme_A - phoneme_B"
	 *         <p>
	 *         7. .getNrAttestationsPhoneticPairs() - List&lt;Integer&gt;: Returns
	 *         the number of attestations within the alignment for each phonetic
	 *         pair of point 6. here above.
	 *         <p>
	 */
	public static List<Alignment> align(String word1, String word2, Integer optionFunction,
			String externalFunction, List<String> configIPA, List<Boolean> matcherConfig1, List<Integer> matcherConfig2,
			List<String> matcherConfig3, List<Double> matcherConfig4) {

		ParsedWord stringParsed = new ParsedWord();
		List<Alignment> alignResults = new ArrayList<>();

		// IPA-parser
		stringParsed = IPA_Parser.parseIPA(word1, word2, configIPA);

		String word1Parsed = (String) stringParsed.getParsedWord1();
		String word2Parsed = (String) stringParsed.getParsedWord2();

		String word1Unparsed = word1;
		String word2Unparsed = word2;

		int matrixResultComparison_WithSaliences[][] = stringParsed.getMatrixResultComparison_WithSaliences();
		int matrixResultComparison_WithoutSaliences[][] = stringParsed.getMatrixResultComparison_WithoutSaliences();

		// matcher
		alignResults = Matcher.match(word1Parsed, word2Parsed, matrixResultComparison_WithSaliences,
				matrixResultComparison_WithoutSaliences, word1Unparsed, word2Unparsed, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4, optionFunction, externalFunction);

		return alignResults;

	}
}
