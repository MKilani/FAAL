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
import java.util.Arrays;
import java.util.List;

public class IPA_Parser {

	/**
	 * Method corresponding to the IPA_parser module. It parses one pair of words at
	 * a time, using default values (see online documentation for their values).
	 * 
	 * @param word1Base
	 *            (String): IPA transcription of the first word to be aligned.
	 * @param word2Base
	 *            (String): IPA transcription of the second word to be aligned.
	 * 
	 * @return Result output: ParsedWord
	 *         <p>
	 *         The <i>ParsedWord</i > object contains the results of the parsing of
	 *         a word. The items stored in the <i>ParsedWord</i > class can be
	 *         called as follow:
	 *         <p>
	 *         0. .getParsedWord1() - String: Parsed (without diacritics etc.) IPA
	 *         transcription of word 1
	 *         <p>
	 *         1. .getParsedWord2() - String: Parsed (without diacritics etc.) IPA
	 *         transcription of word 2
	 *         <p>
	 *         2. .getMatrixResultComparison_WithSaliences() - int[][]: feature
	 *         matrix corrected according to the salience settings. The dimensions
	 *         of the matrix correspond to the length of the parsed (i.e. without
	 *         diacritics etc.) transcription of word 1 × the length of the parsed
	 *         (i.e. without diacritics etc.) transcription of word 2.
	 *         <p>
	 *         3. .getMatrixResultComparison_WithoutSaliences() - int[][]: basic
	 *         feature matrix without any salience correction. Dimension as previous
	 *         one.
	 *         <p>
	 *         <p>
	 */
	public static ParsedWord parseIPA(String word1Base, String word2Base) {

		ParsedWord results = new ParsedWord();
		List<String> configIPA = new ArrayList<String>();
		
		ConfigIPA settingsConfigIPA = new ConfigIPA();

		// Configs files:
		
		configIPA = settingsConfigIPA.getConfigIPA();

		results = parseIPA(word1Base, word2Base, configIPA);

		return results;

	}
	

	
	// =========================
	// =========================
	// =========================
	
	/**
	 * Method corresponding to the IPA_parser module. It parses one pair of words at
	 * a time, using default values (see online documentation for their values).
	 * 
	 * @param word1Base
	 *            (String): IPA transcription of the first word to be aligned.
	 * @param word2Base
	 *            (String): IPA transcription of the second word to be aligned.
	 * @param configIPA
	 *            (List&lt;String&gt;): String array list with variables for the
	 *            configuration of the IPA_parser.IPA_parser_new module. Instances
	 *            of this list can be initialized through the <i>ConfigIPA</i> class
	 *            - see the corresponding JavaDoc, and the documentation and
	 *            examples online.
	 * 
	 * @return Result output: ParsedWord
	 *         <p>
	 *         The <i>ParsedWord</i > object contains the results of the parsing of
	 *         a word. The items stored in the <i>ParsedWord</i > class can be
	 *         called as follow:
	 *         <p>
	 *         0. .getParsedWord1() - String: Parsed (without diacritics etc.) IPA
	 *         transcription of word 1
	 *         <p>
	 *         1. .getParsedWord2() - String: Parsed (without diacritics etc.) IPA
	 *         transcription of word 2
	 *         <p>
	 *         2. .getMatrixResultComparison_WithSaliences() - int[][]: feature
	 *         matrix corrected according to the salience settings. The dimensions
	 *         of the matrix correspond to the length of the parsed (i.e. without
	 *         diacritics etc.) transcription of word 1 × the length of the parsed
	 *         (i.e. without diacritics etc.) transcription of word 2.
	 *         <p>
	 *         3. .getMatrixResultComparison_WithoutSaliences() - int[][]: basic
	 *         feature matrix without any salience correction. Dimension as previous
	 *         one.
	 *         <p>
	 *         <p>
	 */
	public static ParsedWord parseIPA(String word1Base, String word2Base, List<String> configIPA) {


		// ￤ (U+FFE4) -> morphemic boundary
		// ┊(U+250A) -> limits alignments

		// variables & read config file

		// diphthongs are coded as 0 if nothing, + if rising, - if falling -> columns 0,
		// after the name

				
		
		String locationFilePhoneticFeatures = configIPA.get(0);
		String locationFilePhoneticDiacritics = configIPA.get(1);
		
		String locationFileFeaturesDiphthongs = configIPA.get(2);
		String locationFileFeaturesCoarticulation = configIPA.get(3);
		
		String locationPhonCategories = configIPA.get(4);
		String locationSaliencesMatchesPhonCategories = configIPA.get(5);

		String word1 = "";
		String word2 = "";

		String tablePhoneticFeatures;
		String tableFeaturesDiphthongs;
		String tableFeaturesCoarticulation;
		String diacriticsPhoneticFeatures;
		String saliencesFeaturesPhonCategoriesFile;

		String[] linesPhoneticFeatures;
		String[] linesFeaturesDiphthongs;
		String[] linesFeaturesCoarticulation;
		String[] linesPhoneticFeaturesProvisional;

		List<String> linesDiacriticsPhoneticFeatures = new ArrayList<>();

		List<List<String>> valuesPhoneticFeatures = new ArrayList<>();
		List<Integer> valuesFeaturesDiphthongs = new ArrayList<>();
		List<Integer> valuesFeaturesCoarticulation = new ArrayList<>();
		
		List<String> word1PhonCategory_FeatureA = new ArrayList<>();
		List<String> word1PhonCategory_FeatureB = new ArrayList<>();
		
		List<String> word2PhonCategory_FeatureA = new ArrayList<>();
		List<String> word2PhonCategory_FeatureB = new ArrayList<>();

		int lengthWord1 = word1.length();
		int lengthWord2 = word2.length();

		List<List<String>> featuresWord1 = new ArrayList<>();
		List<List<String>> featuresWord2 = new ArrayList<>();

		String[] phonesWord1;
		String[] phonesWord2;

		// check if there is any morphemic boundary, if one has and the other not, add
		// one at the end of the other
		// as if one has at least one morhemic boundary, both need at least one
		if (word1Base.indexOf("￤") > -1 & word2Base.indexOf("￤") < 0) {
			word2Base = word2Base + "￤";
		} else if (word1Base.indexOf("￤") < 0 & word2Base.indexOf("￤") > -1) {
			word1Base = word1Base + "￤";
		}
		// check if there is a morphemic boundary at the ned, if not add one
		if (!(Character.toString(word2Base.charAt(word2Base.length() - 1)).equals("￤"))) {
			word2Base = word2Base + "￤";
		}
		if (!(Character.toString(word1Base.charAt(word1Base.length() - 1)).equals("￤"))) {
			word1Base = word1Base + "￤";
		}

		phonesWord1 = word1Base.split("(?!^)");
		phonesWord2 = word2Base.split("(?!^)");

		
		// retrieve Phonological categories
		
		String filePhonCategories;
		String[] filePhonCategoriesLines;
		String[] filePhonCategoriesItems = null;
		List<String> phonologicalCategoriesCharacter = new ArrayList<>();
		List<String> phonologicalCategoriesFirstFeature = new ArrayList<>();
		List<String> phonologicalCategoriesSecondFeature = new ArrayList<>();
		
		filePhonCategories = Reader.readFile(locationPhonCategories);
		
		filePhonCategoriesLines = filePhonCategories.split("\n");

		for (int i = 0; i < filePhonCategoriesLines.length; i++) {

			if (filePhonCategoriesLines[i].indexOf("%") < 0) {

				filePhonCategoriesItems = filePhonCategoriesLines[i].split("	");
				phonologicalCategoriesCharacter.add(filePhonCategoriesItems[0]);
				phonologicalCategoriesFirstFeature.add(filePhonCategoriesItems[1]);
				phonologicalCategoriesSecondFeature.add(filePhonCategoriesItems[2]);
			}
		}
		
		
		// retrieve saliences to use for matches for Phonological categories
		
		
		String fileSaliencesMatchesPhonCategories;
		String[] fileSaliencesMatchesPhonCategoriesLines;
		String[] fileSaliencesMatchesPhonCategoriesItems;
		List<String> phonologicalCategoriesFeature_A1 = new ArrayList<>();
		List<String> phonologicalCategoriesFeature_B1 = new ArrayList<>();
		List<String> phonologicalCategoriesFeature_A2 = new ArrayList<>();
		List<String> phonologicalCategoriesFeature_B2 = new ArrayList<>();
		List<String> phonologicalCategoriesLocationSaliences = new ArrayList<>();
		
		fileSaliencesMatchesPhonCategories = Reader.readFile(locationSaliencesMatchesPhonCategories);
		
		fileSaliencesMatchesPhonCategoriesLines = fileSaliencesMatchesPhonCategories.split("\n");

		for (int i = 0; i < fileSaliencesMatchesPhonCategoriesLines.length; i++) {

			if (!(Character.toString(fileSaliencesMatchesPhonCategoriesLines[i].charAt(0)).equals("%"))) {

				fileSaliencesMatchesPhonCategoriesItems = fileSaliencesMatchesPhonCategoriesLines[i].split("	");
				
				phonologicalCategoriesFeature_A1.add(fileSaliencesMatchesPhonCategoriesItems[0]);
				phonologicalCategoriesFeature_A2.add(fileSaliencesMatchesPhonCategoriesItems[1]);
				phonologicalCategoriesFeature_B1.add(fileSaliencesMatchesPhonCategoriesItems[3]);
				phonologicalCategoriesFeature_B2.add(fileSaliencesMatchesPhonCategoriesItems[4]);
				
				phonologicalCategoriesLocationSaliences.add(fileSaliencesMatchesPhonCategoriesItems[6]);
			}
		}
		
		
		
		// ========================
		// read phonetic features

		tablePhoneticFeatures = Reader.readFile(locationFilePhoneticFeatures);

		linesPhoneticFeatures = tablePhoneticFeatures.split("\n");

		for (int i = 0; i < linesPhoneticFeatures.length; i++) {

			if (linesPhoneticFeatures[i].indexOf("%") < 0) {
				List<String> dataPhoneticFeatures = new ArrayList<String>(
						Arrays.asList(linesPhoneticFeatures[i].split("	")));

				valuesPhoneticFeatures.add(dataPhoneticFeatures);
			}
		}
		// read features modified by secondary elements of diphthongs
		
		tableFeaturesDiphthongs = Reader.readFile(locationFileFeaturesDiphthongs);

		linesFeaturesDiphthongs = tableFeaturesDiphthongs.split("\n");

		for (int i = 0; i < linesFeaturesDiphthongs.length; i++) {

			if (linesFeaturesDiphthongs[i].indexOf("%") < 0) {
				valuesFeaturesDiphthongs.add(Integer.parseInt(linesFeaturesDiphthongs[i]));
			}
		}
		
		// read features modified by secondary elements of coarticulated sequences
		
				tableFeaturesCoarticulation = Reader.readFile(locationFileFeaturesCoarticulation);

				linesFeaturesCoarticulation = tableFeaturesCoarticulation.split("\n");

				for (int i = 0; i < linesFeaturesCoarticulation.length; i++) {

					if (linesFeaturesCoarticulation[i].indexOf("%") < 0) {
						valuesFeaturesCoarticulation.add(Integer.parseInt(linesFeaturesCoarticulation[i]));
					}
				}
		
		// read diacritics

		diacriticsPhoneticFeatures = Reader.readFile(locationFilePhoneticDiacritics);

		linesPhoneticFeaturesProvisional = diacriticsPhoneticFeatures.split("\n");

		for (int i = 0; i < linesPhoneticFeaturesProvisional.length; i++) {

			if (linesPhoneticFeaturesProvisional[i].indexOf("%") < 0) {
				linesDiacriticsPhoneticFeatures.add(linesPhoneticFeaturesProvisional[i]);
			}
		}

		linesDiacriticsPhoneticFeatures.add("̯	2	0;1");// marked with 2 to distinguish it from normal diacritics
		linesDiacriticsPhoneticFeatures.add("͜	3	0;2");// marked with 3 to distinguish it from normal diacritics

		List<String> valuesDiacriticsPhoneticFeatures = new ArrayList<>();
		List<String> classDiacriticsPhoneticFeatures = new ArrayList<>();

		List<List<Integer>> diacriticsPhoneticFeaturesChanged = new ArrayList<>();
		List<List<String>> diacriticsPhoneticFeaturesChangedValue = new ArrayList<>();

		for (int i = 0; i < linesDiacriticsPhoneticFeatures.size(); i++) {
			String[] diacriticsSplit1;
			String[] diacriticsSplit2;
			String[] diacriticsSplit3;

			List<Integer> featuresChanged = new ArrayList<>();
			List<String> featuresChangedValue = new ArrayList<>();

			diacriticsSplit1 = linesDiacriticsPhoneticFeatures.get(i).split("	");
			diacriticsSplit2 = diacriticsSplit1[2].split(" ");

			valuesDiacriticsPhoneticFeatures.add(diacriticsSplit1[0]);
			classDiacriticsPhoneticFeatures.add(diacriticsSplit1[1]);

			for (int n = 0; n < diacriticsSplit2.length; n++) {

				diacriticsSplit3 = diacriticsSplit2[n].split(";");
				featuresChanged.add(Integer.parseInt(diacriticsSplit3[0]));
				featuresChangedValue.add(diacriticsSplit3[1]);

			}

			diacriticsPhoneticFeaturesChanged.add(featuresChanged);
			diacriticsPhoneticFeaturesChangedValue.add(featuresChangedValue);

		}

		// preliminary parsing -> diphthongs; coarticulation; diacritics that precede
		// the letter they refer to

		// diphthongs

		for (int i = 0; i < phonesWord1.length; i++) {
			if (phonesWord1[i].equals("͜")) {
				phonesWord1[i] = phonesWord1[i + 1];
				phonesWord1[i + 1] = "͜";
				i++;
			}
		}

		for (int i = 0; i < phonesWord2.length; i++) {

			if (phonesWord2[i].equals("͜")) {
				phonesWord2[i] = phonesWord2[i + 1];
				phonesWord2[i + 1] = "͜";
				i++;

			}
		}

		// coarticulation

		for (int i = 0; i < phonesWord1.length; i++) {
			if (phonesWord1[i].equals("͡")) {
				phonesWord1[i] = phonesWord1[i + 1];
				phonesWord1[i + 1] = "͡";
				i++;
			}
		}

		for (int i = 0; i < phonesWord2.length; i++) {

			if (phonesWord2[i].equals("͡")) {
				phonesWord2[i] = phonesWord2[i + 1];
				phonesWord2[i + 1] = "͡";
				i++;

			}
		}

		// diacritics that precede the letter they refer to

		for (int i = 0; i < phonesWord1.length; i++) {
			for (int n = 0; n < classDiacriticsPhoneticFeatures.size(); n++) {
				if (phonesWord1[i].equals(valuesDiacriticsPhoneticFeatures.get(n))
						& classDiacriticsPhoneticFeatures.get(n).equals("1")) {
					phonesWord1[i] = phonesWord1[i + 1];
					phonesWord1[i + 1] = valuesDiacriticsPhoneticFeatures.get(n);
					i++;
				}
			}
		}

		for (int i = 0; i < phonesWord2.length; i++) {
			for (int n = 0; n < classDiacriticsPhoneticFeatures.size(); n++) {
				if (phonesWord2[i].equals(valuesDiacriticsPhoneticFeatures.get(n))
						& classDiacriticsPhoneticFeatures.get(n).equals("1")) {
					phonesWord2[i] = phonesWord2[i + 1];
					phonesWord2[i + 1] = valuesDiacriticsPhoneticFeatures.get(n);
					i++;
				}
			}
		}

		
		// read salience features phonological categories
		
		
		List<List<Integer>> listSaliencesFeaturesCategories = new ArrayList<>();
		List<List<Integer>> listSaliencesFeaturesCategoriesValue = new ArrayList<>();
		
		
		for (int y = 0; y < phonologicalCategoriesLocationSaliences.size() ; y++) {
			
			String[] linesSaliencesFeaturesPhonCategoriesProvisional;
			List<String> linesSaliencesFeaturesPhonCategories = new ArrayList<>();
			
		

		
		String locationFileSaliences = phonologicalCategoriesLocationSaliences.get(y);
		
		//----

		saliencesFeaturesPhonCategoriesFile = Reader.readFile(locationFileSaliences);

		linesSaliencesFeaturesPhonCategoriesProvisional = saliencesFeaturesPhonCategoriesFile.split("\n");

		for (int i = 0; i < linesSaliencesFeaturesPhonCategoriesProvisional.length; i++) {

			if (linesSaliencesFeaturesPhonCategoriesProvisional[i].indexOf("%") < 0) {
				linesSaliencesFeaturesPhonCategories.add(linesSaliencesFeaturesPhonCategoriesProvisional[i]);
			}
		}

		List<Integer> saliencesFeaturesPhonCategories = new ArrayList<>();
		List<Integer> saliencesFeaturesPhonCategoriesValue = new ArrayList<>();

		for (int i = 0; i < linesSaliencesFeaturesPhonCategories.size(); i++) {

			String[] saliencePhonCategoriesSplit;

			saliencePhonCategoriesSplit = linesSaliencesFeaturesPhonCategories.get(i).split("	");

			saliencesFeaturesPhonCategories.add(Integer.parseInt(saliencePhonCategoriesSplit[0]));
			saliencesFeaturesPhonCategoriesValue.add(Integer.parseInt(saliencePhonCategoriesSplit[1]));

		}

		//----
		
		
		listSaliencesFeaturesCategories.add(saliencesFeaturesPhonCategories);
		listSaliencesFeaturesCategoriesValue.add(saliencesFeaturesPhonCategoriesValue);
		
		
		}

		
		// read phonetic features

		// ================
		// parsing
		// ------------
		


		// parse word A
		// compile feature list for word A
		

		for (int i = 0; i < phonesWord1.length; i++) {
			
			// diphthongs - modify according to diacritics
			for (int n = 0; n < valuesDiacriticsPhoneticFeatures.size(); n++) {

				if (phonesWord1[i].equals(valuesDiacriticsPhoneticFeatures.get(n))) {

					if (phonesWord1[i].equals("͜")) {
						phonesWord1[i - 2] = "$";
						
						//------
						for (int x = 0; x < valuesFeaturesDiphthongs.size(); x++) {
							int m = valuesFeaturesDiphthongs.get(x);
							
							if ((featuresWord1.get(featuresWord1.size() - 2).get(m).equals("+")
									& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("-"))
									|| (featuresWord1.get(featuresWord1.size() - 2).get(m).equals("-")
											& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("+"))) {
								featuresWord1.get(featuresWord1.size() - 1).set(m, "±");
							} else if ((featuresWord1.get(featuresWord1.size() - 2).get(m).equals("+")
									& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("0"))
									|| (featuresWord1.get(featuresWord1.size() - 2).get(m).equals("0")
											& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("+"))) {
								featuresWord1.get(featuresWord1.size() - 1).set(m, "⁰");
							} else if ((featuresWord1.get(featuresWord1.size() - 2).get(m).equals("-")
									& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("0"))
									|| (featuresWord1.get(featuresWord1.size() - 2).get(m).equals("0")
											& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("-"))) {
								featuresWord1.get(featuresWord1.size() - 1).set(m, "₀");
							}

						}
						

						featuresWord1.remove(featuresWord1.size() - 2);
						word1 = word1.substring(0, word1.length() - 2) + word1.substring(word1.length() - 1, word1.length());
						
						word1PhonCategory_FeatureA.remove(word1PhonCategory_FeatureA.size() - 2);
						word1PhonCategory_FeatureB.remove(word1PhonCategory_FeatureB.size() - 2);
						
						
						
					}
					if (phonesWord1[i].equals("̯")) {
						phonesWord1[i - 1] = "$";
						
						//------
						for (int x = 0; x < valuesFeaturesDiphthongs.size(); x++) {
							int m = valuesFeaturesDiphthongs.get(x);

							if ((featuresWord1.get(featuresWord1.size() - 2).get(m).equals("+")
									& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("-"))
									|| (featuresWord1.get(featuresWord1.size() - 2).get(m).equals("-")
											& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("+"))) {
								featuresWord1.get(featuresWord1.size() - 2).set(m, "±");
							} else if ((featuresWord1.get(featuresWord1.size() - 2).get(m).equals("+")
									& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("0"))
									|| (featuresWord1.get(featuresWord1.size() - 2).get(m).equals("0")
											& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("+"))) {
								featuresWord1.get(featuresWord1.size() - 2).set(m, "⁰");
							} else if ((featuresWord1.get(featuresWord1.size() - 2).get(m).equals("-")
									& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("0"))
									|| (featuresWord1.get(featuresWord1.size() - 2).get(m).equals("0")
											& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("-"))) {
								featuresWord1.get(featuresWord1.size() - 2).set(m, "₀");
							}

						}
						

						featuresWord1.remove(featuresWord1.size() - 1);
						word1 = word1.substring(0, word1.length() - 1);
						
						word1PhonCategory_FeatureA.remove(word1PhonCategory_FeatureA.size() - 1);
						word1PhonCategory_FeatureB.remove(word1PhonCategory_FeatureB.size() - 1);
					}
				}
			}
		

			// process coarticualted phones

			if (phonesWord1[i].equals("͡")) {
				phonesWord1[i - 1] = "$";
					
				for (int x = 0; x < valuesFeaturesCoarticulation.size(); x++) {
						int m = valuesFeaturesCoarticulation.get(x);

					if ((featuresWord1.get(featuresWord1.size() - 2).get(m).equals("+")
							& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("-"))
							|| (featuresWord1.get(featuresWord1.size() - 2).get(m).equals("-")
									& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("+"))) {
						featuresWord1.get(featuresWord1.size() - 2).set(m, "±");
					} else if ((featuresWord1.get(featuresWord1.size() - 2).get(m).equals("+")
							& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("0"))
							|| (featuresWord1.get(featuresWord1.size() - 2).get(m).equals("0")
									& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("+"))) {
						featuresWord1.get(featuresWord1.size() - 2).set(m, "⁰");
					} else if ((featuresWord1.get(featuresWord1.size() - 2).get(m).equals("-")
							& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("0"))
							|| (featuresWord1.get(featuresWord1.size() - 2).get(m).equals("0")
									& featuresWord1.get(featuresWord1.size() - 1).get(m).equals("-"))) {
						featuresWord1.get(featuresWord1.size() - 2).set(m, "₀");
					}

				}

				featuresWord1.remove(featuresWord1.size() - 1);
				word1 = word1.substring(0, word1.length() - 1);
				
				word1PhonCategory_FeatureA.remove(word1PhonCategory_FeatureA.size() - 1);
				word1PhonCategory_FeatureB.remove(word1PhonCategory_FeatureB.size() - 1);

			}

			// modify according to diacritics
			for (int n = 0; n < valuesDiacriticsPhoneticFeatures.size(); n++) {

				if (phonesWord1[i].equals(valuesDiacriticsPhoneticFeatures.get(n))) {
					for (int m = 0; m < diacriticsPhoneticFeaturesChanged.get(n).size(); m++) {

						featuresWord1.get(featuresWord1.size() - 1).set(diacriticsPhoneticFeaturesChanged.get(n).get(m),
								diacriticsPhoneticFeaturesChangedValue.get(n).get(m));

					}

				}

			}

			// modify according to vowel-semiconsonant-consonant
			
			// end modify according to vowel-semiconsonant-consonant
			
			

			if (phonesWord1[i].equals("￤")) {

				// morpheme boundary
				
				word1 = word1 + "￤";

				featuresWord1.add(new ArrayList<String>(valuesPhoneticFeatures.get(0)));
				featuresWord1.get(featuresWord1.size() - 1).remove(0);
				for (int m = 0; m < featuresWord1.get(featuresWord1.size() - 1).size(); m++) {

					featuresWord1.get(featuresWord1.size() - 1).set(m, "9");
				}
				
				word1PhonCategory_FeatureA.add("9");
				word1PhonCategory_FeatureB.add("9");

			}

			// IPA basic transcription - create new word without diacritics

			for (int n = 0; n < valuesPhoneticFeatures.size(); n++) {
				
				if (phonesWord1[i].equals(valuesPhoneticFeatures.get(n).get(0))) {

					
					
					
						
						word1PhonCategory_FeatureA.add(phonologicalCategoriesFirstFeature.get(n));
						word1PhonCategory_FeatureB.add(phonologicalCategoriesSecondFeature.get(n));
						
						
						word1 = word1 + valuesPhoneticFeatures.get(n).get(0);

						featuresWord1.add(new ArrayList<String>(valuesPhoneticFeatures.get(n)));
						featuresWord1.get(featuresWord1.size() - 1).remove(0);
						
						
						
						
						
					

				}
			}
		}

		// word B
		// compile feature list for word B
		for (int i = 0; i < phonesWord2.length; i++) {
			
			// diphthongs - modify according to diacritics
			for (int n = 0; n < valuesDiacriticsPhoneticFeatures.size(); n++) {

				if (phonesWord2[i].equals(valuesDiacriticsPhoneticFeatures.get(n))) {

					if (phonesWord2[i].equals("͜")) {
						phonesWord2[i - 2] = "$";
						
						//------
						for (int x = 0; x < valuesFeaturesDiphthongs.size(); x++) {
							int m = valuesFeaturesDiphthongs.get(x);

							if ((featuresWord2.get(featuresWord2.size() - 2).get(m).equals("+")
									& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("-"))
									|| (featuresWord2.get(featuresWord2.size() - 2).get(m).equals("-")
											& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("+"))) {
								featuresWord2.get(featuresWord2.size() - 1).set(m, "±");
							} else if ((featuresWord2.get(featuresWord2.size() - 2).get(m).equals("+")
									& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("0"))
									|| (featuresWord2.get(featuresWord2.size() - 2).get(m).equals("0")
											& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("+"))) {
								featuresWord2.get(featuresWord2.size() - 1).set(m, "⁰");
							} else if ((featuresWord2.get(featuresWord2.size() - 2).get(m).equals("-")
									& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("0"))
									|| (featuresWord2.get(featuresWord2.size() - 2).get(m).equals("0")
											& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("-"))) {
								featuresWord2.get(featuresWord2.size() - 1).set(m, "₀");
							}

						}
						

						featuresWord2.remove(featuresWord2.size() - 2);
						word2 = word2.substring(0, word2.length() - 2) + word2.substring(word2.length() - 1, word2.length());
						
						word2PhonCategory_FeatureA.remove(word2PhonCategory_FeatureA.size() - 2);
						word2PhonCategory_FeatureB.remove(word2PhonCategory_FeatureB.size() - 2);
						
					}
					if (phonesWord2[i].equals("̯")) {
						phonesWord2[i - 1] = "$";
						
						//------
						for (int x = 0; x < valuesFeaturesDiphthongs.size(); x++) {
							int m = valuesFeaturesDiphthongs.get(x);

							if ((featuresWord2.get(featuresWord2.size() - 2).get(m).equals("+")
									& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("-"))
									|| (featuresWord2.get(featuresWord2.size() - 2).get(m).equals("-")
											& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("+"))) {
								featuresWord2.get(featuresWord2.size() - 2).set(m, "±");
							} else if ((featuresWord2.get(featuresWord2.size() - 2).get(m).equals("+")
									& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("0"))
									|| (featuresWord2.get(featuresWord2.size() - 2).get(m).equals("0")
											& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("+"))) {
								featuresWord2.get(featuresWord2.size() - 2).set(m, "⁰");
							} else if ((featuresWord2.get(featuresWord2.size() - 2).get(m).equals("-")
									& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("0"))
									|| (featuresWord2.get(featuresWord2.size() - 2).get(m).equals("0")
											& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("-"))) {
								featuresWord2.get(featuresWord2.size() - 2).set(m, "₀");
							}

						}
						

						featuresWord2.remove(featuresWord2.size() - 1);
						word2 = word2.substring(0, word2.length() - 1);
						
						word2PhonCategory_FeatureA.remove(word2PhonCategory_FeatureA.size() - 1);
						word2PhonCategory_FeatureB.remove(word2PhonCategory_FeatureB.size() - 1);
					}
				}
			}
			

			// process coarticualted phones

			if (phonesWord2[i].equals("͡")) {
				phonesWord2[i - 1] = "$";
				
				for (int x = 0; x < valuesFeaturesCoarticulation.size(); x++) {
						int m = valuesFeaturesCoarticulation.get(x);

					if ((featuresWord2.get(featuresWord2.size() - 2).get(m).equals("+")
							& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("-"))
							|| (featuresWord2.get(featuresWord2.size() - 2).get(m).equals("-")
									& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("+"))) {
						featuresWord2.get(featuresWord2.size() - 2).set(m, "±");
					} else if ((featuresWord2.get(featuresWord2.size() - 2).get(m).equals("+")
							& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("0"))
							|| (featuresWord2.get(featuresWord2.size() - 2).get(m).equals("0")
									& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("+"))) {
						featuresWord2.get(featuresWord2.size() - 2).set(m, "⁰");
					} else if ((featuresWord2.get(featuresWord2.size() - 2).get(m).equals("-")
							& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("0"))
							|| (featuresWord2.get(featuresWord2.size() - 2).get(m).equals("0")
									& featuresWord2.get(featuresWord2.size() - 1).get(m).equals("-"))) {
						featuresWord2.get(featuresWord2.size() - 2).set(m, "₀");
					}

				}

				featuresWord2.remove(featuresWord2.size() - 1);
				word2 = word2.substring(0, word2.length() - 1);
				
				word2PhonCategory_FeatureA.remove(word2PhonCategory_FeatureA.size() - 1);
				word2PhonCategory_FeatureB.remove(word2PhonCategory_FeatureB.size() - 1);

			}

			for (int n = 0; n < valuesDiacriticsPhoneticFeatures.size(); n++) {

				if (phonesWord2[i].equals(valuesDiacriticsPhoneticFeatures.get(n))) {

					for (int m = 0; m < diacriticsPhoneticFeaturesChanged.get(n).size(); m++) {

						featuresWord2.get(featuresWord2.size() - 1).set(diacriticsPhoneticFeaturesChanged.get(n).get(m),
								diacriticsPhoneticFeaturesChangedValue.get(n).get(m));

					}

				}

			}

			// end modify according to diacritics
			
			// modify according to vowel-semiconsonant-consonant
			
			// end modify according to vowel-semiconsonant-consonant

			if (phonesWord2[i].equals("￤")) {

				// morpheme boundary

				word2 = word2 + "￤";

				featuresWord2.add(new ArrayList<String>(valuesPhoneticFeatures.get(0)));
				featuresWord2.get(featuresWord2.size() - 1).remove(0);

				for (int m = 0; m < featuresWord2.get(featuresWord2.size() - 1).size(); m++) {

					featuresWord2.get(featuresWord2.size() - 1).set(m, "9");
				}
				
				word2PhonCategory_FeatureA.add("9");
				word2PhonCategory_FeatureB.add("9");

			}

			for (int n = 0; n < valuesPhoneticFeatures.size(); n++) {

				// IPA basic transcription - create new word without diacritics
				if (phonesWord2[i].equals(valuesPhoneticFeatures.get(n).get(0))) {

					


						word2PhonCategory_FeatureA.add(phonologicalCategoriesFirstFeature.get(n));
						word2PhonCategory_FeatureB.add(phonologicalCategoriesSecondFeature.get(n));
						
						word2 = word2 + valuesPhoneticFeatures.get(n).get(0);

						featuresWord2.add(new ArrayList<String>(valuesPhoneticFeatures.get(n)));
						featuresWord2.get(featuresWord2.size() - 1).remove(0);
						
						
					

				}
			}
		}

		// count common features and make matrix

		lengthWord1 = word1.length();
		lengthWord2 = word2.length();

		int matrixResultComparison_WithSaliences[][] = new int[lengthWord1][lengthWord2];
		int matrixResultComparison_WithoutSaliences[][] = new int[lengthWord1][lengthWord2];

		for (int i = 0; i < lengthWord1; i++) {
			for (int n = 0; n < lengthWord2; n++) {

				matrixResultComparison_WithSaliences[i][n] = 0;
				matrixResultComparison_WithoutSaliences[i][n] = 0;

			}
		}
		

		// create new matrix with values modified according to salience settings
		for (int i = 0; i < lengthWord1; i++) {
			for (int n = 0; n < lengthWord2; n++) {

				List<Integer> saliencesFeatures = new ArrayList<>();
				List<Integer> saliencesFeaturesValue = new ArrayList<>();
				 
				
				for (int y = 0; y < phonologicalCategoriesFeature_A1.size(); y++) {
					
					
					if(
							(
							(phonologicalCategoriesFeature_A1.get(y).equals(word1PhonCategory_FeatureA.get(i)) &&  phonologicalCategoriesFeature_A2.get(y).equals(word2PhonCategory_FeatureA.get(n)))
								||
							(phonologicalCategoriesFeature_A1.get(y).equals(word2PhonCategory_FeatureA.get(n)) &&  phonologicalCategoriesFeature_A2.get(y).equals(word1PhonCategory_FeatureA.get(i)))	
							)
							&&
							(
							(phonologicalCategoriesFeature_B1.get(y).equals(word1PhonCategory_FeatureB.get(i)) &&  phonologicalCategoriesFeature_B2.get(y).equals(word2PhonCategory_FeatureB.get(n)))
							||
							(phonologicalCategoriesFeature_B1.get(y).equals(word2PhonCategory_FeatureB.get(n)) &&  phonologicalCategoriesFeature_B2.get(y).equals(word1PhonCategory_FeatureB.get(i)))	
							)
						) {
						
						saliencesFeatures = new ArrayList<>(listSaliencesFeaturesCategories.get(y));
						saliencesFeaturesValue = new ArrayList<>(listSaliencesFeaturesCategoriesValue.get(y));
						break;
					}
									
				}
				
				
					//------
				
				
				for (int o = 0; o < featuresWord2.get(0).size(); o++) {

					if (featuresWord1.get(i).get(o).equals(featuresWord2.get(n).get(o))) {

						matrixResultComparison_WithoutSaliences[i][n]++;

						for (int f = 0; f < saliencesFeatures.size(); f++) {
							if (o == saliencesFeatures.get(f)) {

								for (int g = 0; g < saliencesFeaturesValue.get(f); g++) {
									matrixResultComparison_WithSaliences[i][n]++;
								}
							}
						}

					} else if ((featuresWord1.get(i).get(o).equals("±") & (featuresWord2.get(n).get(o).equals("-")
							|| featuresWord2.get(n).get(o).equals("+") || featuresWord2.get(n).get(o).equals("±")))
							|| (featuresWord1.get(i).get(o).equals("⁰") & (featuresWord2.get(n).get(o).equals("0")
									|| featuresWord2.get(n).get(o).equals("+")
									|| featuresWord1.get(i).get(o).equals("⁰")))
							|| (featuresWord1.get(i).get(o).equals("₀") & (featuresWord2.get(n).get(o).equals("0")
									|| featuresWord2.get(n).get(o).equals("-")
									|| featuresWord1.get(i).get(o).equals("₀")))) {// &
																					// featuresWord1.get(i).get(1).equals("+")
																					// &
																					// featuresWord1.get(i).get(1).equals("+")){
						matrixResultComparison_WithoutSaliences[i][n]++;

						for (int f = 0; f < saliencesFeatures.size(); f++) {
							if (o == saliencesFeatures.get(f)) {

								for (int g = 0; g < saliencesFeaturesValue.get(f); g++) {
									matrixResultComparison_WithSaliences[i][n]++;
								}
							}
						}

					} else if (((featuresWord1.get(i).get(o).equals("-") || featuresWord1.get(i).get(o).equals("+"))
							& featuresWord2.get(n).get(o).equals("±"))
							|| ((featuresWord1.get(i).get(o).equals("0") || featuresWord1.get(i).get(o).equals("+"))
									& featuresWord2.get(n).get(o).equals("⁰"))
							|| ((featuresWord1.get(i).get(o).equals("0") || featuresWord1.get(i).get(o).equals("-"))
									& featuresWord2.get(n).get(o).equals("₀"))) {
						matrixResultComparison_WithoutSaliences[i][n]++;

						for (int f = 0; f < saliencesFeatures.size(); f++) {
							if (o == saliencesFeatures.get(f)) {

								for (int g = 0; g < saliencesFeaturesValue.get(f); g++) {
									matrixResultComparison_WithSaliences[i][n]++;
								}
							}
						}

					}

				}

				if (featuresWord1.get(i).get(0).equals("9") & featuresWord2.get(n).get(0).equals("9")) {

					for (int j = 0; j < 300; j++) {
						matrixResultComparison_WithSaliences[i][n]++;
					}

					matrixResultComparison_WithoutSaliences[i][n]++;

					for (int f = 0; f < saliencesFeaturesValue.size(); f++) {
						for (int g = 0; g < saliencesFeaturesValue.get(f); g++) {
							matrixResultComparison_WithSaliences[i][n]++;
						}
					}

				}

				if (featuresWord1.get(i).get(0).equals("9") & !featuresWord2.get(n).get(0).equals("9")) {// &
																											// featuresWord1.get(i).get(1).equals("+")
																											// &
																											// featuresWord1.get(i).get(1).equals("+")){

					matrixResultComparison_WithSaliences[i][n] = -1000;
					matrixResultComparison_WithoutSaliences[i][n] = -1000;

				}

				if (!featuresWord1.get(i).get(0).equals("9") & featuresWord2.get(n).get(0).equals("9")) {// &
																											// featuresWord1.get(i).get(1).equals("+")
																											// &
																											// featuresWord1.get(i).get(1).equals("+")){

					matrixResultComparison_WithSaliences[i][n] = -1000;
					matrixResultComparison_WithoutSaliences[i][n] = -1000;

				}
			}
		}

		// organize and return results
		
		ParsedWord resultParsing = new ParsedWord();
		
		
		
		resultParsing.parsedWord1(word1);
		resultParsing.parsedWord2(word2);
		resultParsing.matrixResultComparison_WithSaliences(matrixResultComparison_WithSaliences);
		resultParsing.matrixResultComparison_WithoutSaliences(matrixResultComparison_WithoutSaliences);
		

		return resultParsing;

	}

}
