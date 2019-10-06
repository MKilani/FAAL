
# FAAL 2.0.0

FAAL is a new algorithm for the alignment of phonetically transcribed lexical items based on the comparison of phonetic features. The algorithm can be run on any pair of phonetically transcribed words without requiring any specific setting or tuning, although various parameters of its implementation as .jar library can indeed be modified by the user, if needed.

(I will add the reference to the article once it will be accepted)

## Organisation of the repository

- config : folder with config files to be used together with the FAAL_2.0.0.jar library
- CONTRIBUTING.md : notes on how to contribute
- Example : folder containing the java projects with the code of the examples presented here below
- FAAL_2.0.0.jar : library contianing the FAAL lagorithm, to be used together with the files in the folder config
- LICENCE.md : licence
- NOTICE.md : note about third party code used in FAAL
- OnlineDoc : additional docs about the functioning of FAAL
- README.md : readme file ( the present file )
- TestArticle : folder containing the datasets and codes used in the test case discussed in the article, as well as the scripts to analyse the results - see readme.md within the folder for details
- TuningDatasets : folder containing the datasets and codes used in the tuning of FAAL discussed in the article, as well as the scripts to analyse the results - see readme.md within the folder for details


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Installing

Create a folder called lib in your project folder.
Copy to this folder the jar file.
Add the jar to your classpath (in Eclipse: Select all the jar files, then right click on one of them and select Build Path -> Add to Build Path. Then refresh your project)
Create a folder called config in your project folder.
Copy the configuration file (distributed in the folder config) into this folder.

The resulting tree of your project should be:

```
+---project folder
	|
	+---src
	|	+---your code
	|
 	+---lib
 	|	+---FAAL_2.0.0.jar
 	|
 	+---config
 		+---saliences
 			+---salience_cat_1.txt
 			+---etc. ...
 		+---external_corr_glob_sim_score_function.txt
 		+---features_coarticulation.txt
 		+---features_diphthongs.txt
 		+---phon_categories.txt
 		+---phon_diacritics.txt
 		+---phon_features.txt
 		+---saliences_to_use_matches_phon_categories.txt
```


## Examples - basic implementation

FAAL can be used either as it is, using the default parameters, or by setting various options.

Eight examples presenting different ways to use and customise FAAL are presented here below.
Their topic are the following:

- __Example 1__ : basic implementation with default values
- __Example 2__ : basic default implenentation with external Corrected Global Similarity Score from file
- __Example 3__ : basic default implenentation with external Corrected Global Similarity Score set programamtically
- __Example 4__ : implementation with personalized settings
- __Example 5__ : example of the use of morphemic boundary markers with the basic implementation with default values
- __Example 6__ : example in which each of the submodules of FAAL is called indipendently
- __Example 7__ : example showing how to modify FAAL in order to compare tones on the basis of their form - the example aligns three words in three Bai dialects
- __Example 8__ : example showing how to modify FAAL in order to compare tones on the basis of their historical origin, and how to use the Similarity Scores to identify the best match between phonologically similar alternatives - the example aligns three similar words in Punjabi (tonal) with three words in Hindi (non-tonal) identifying the correct match for each of them.

### Example 1

The easiest way to use FAAL consists in using the method FAAL.faal(String, String, Boolean).
This method requires three arguments:

- word1 (String): IPA transcription of the first word to be aligned.
- word2 (String): IPA transcription of the second word to be aligned.
- printResults (Boolean): print the results in the console - True = print all the alignments found; False = print only the best alignment.

The method returns a List<Alignment>.

Each item in the List<Alignment> corresponds to an alignment. They are organized according to their Global or Corrected Global Similarity score. The
items stored in the _Alignment_ class can be called as follow:


- .getWord1_WithDiacritics() - String: Returns the aligned word
1, with diacritics.
- .getWord2_WithDiacritics() - String: Returns the aligned word
2, with diacritics.
- .getWord1_WithoutDiacritics() - String: Returns the aligned
word 1, without diacritics.
- .getWord2_WithoutDiacritics() - String: Returns the aligned
word 2, without diacritics.
- .getGlobalSimilarityScore() - Double: Returns the Global
Similarity Score.
- .getCorrectedGlobalSimilarityScore() - Double: Returns the
Corrected Global Similarity Score.
- .getPhoneticPairs() - List&lt;String&gt;: Returns the list of
phonetic pairs attested within the alignment. Each item on the list
corresponds to a phonetic pair, and it is stored as a string with the
following syntax: "phoneme_A - phoneme_B"
- .getNrAttestationsPhoneticPairs() - List&lt;Integer&gt;: Returns
the number of attestations within the alignment for each phonetic
pair of point 6. here above.

Here a minimal working example that prints all the alignments found by the algorithm using the default settings:

```java
package examples;

import java.util.List;
import faal.*;

public class Example1 {

	public static void main(String[] args) {

		List<Alignment> resultsAlignments = FAAL.align("kentum", "hekaton", true);

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
```

The best result, printed after the alignments found by the algorithm, is:

```
=== Best Alignment ===
0	0┊	k	e	n	t	u	m┊	￤
h	e┊	k	a	0	t	o	n┊	￤
:0:0┊:k:e:n:t:u:m┊:￤
:h:e┊:k:a:0:t:o:n┊:￤
Global Similarity Score: 19.31628682601445
Corrected Global Similarity Score: 19.268667778395404
[k - k, e - a, t - t, u - o, m - n]
[1, 1, 1, 1, 1]
```

### Example 2

FAAL can be used with an external Corrected Global Similarity Score. Such function can be defined in the file external_corr_glob_sim_score_function.txt:

```
+---project folder
 	|
 	+---config
 		+---external_corr_glob_sim_score_function.txt
```

In this case, a fourth argument must be added:

- word1 (String): IPA transcription of the first word to be aligned.
- word2 (String): IPA transcription of the second word to be aligned.
- printResults (Boolean): print the results in the console - True = print all the alignments found; False = print only the best alignment.
- optionExternalFunction (Integer): select if using or not an external function - 0 = use default settings (with automatic selection of Global or Corrected Global Similarity Score); 1 = use external function from config. folder; 2 = use function provided as next argument; 3 = use only the Global Similarity Score; 4 = use only the default Corrected Global Similarity Score

The following minimal example aligns the English word tongue /tʌŋ/ with the Latin word lingua /liŋgʷa/ the external corrected global similarity score present in the file, which is the same as the default one. The information about how to modify such functions can be found in the file external_corr_glob_sim_score_function.txt itself.


```java
package examples;

import java.util.List;
import faal.*;

public class Example2 {

	public static void main(String[] args) {

		List<Alignment> resultsAlignments = FAAL.align("tʌŋ", "liŋgʷa", true, 1);

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
```

The result, printed after the alignments found by the algorithm, should be:

```
=== Best Alignment ===
┊t	ʌ	ŋ┊	0	0	￤
┊l	i	ŋ┊	gʷ	a	￤
┊:t:ʌ:ŋ┊:0:0:￤
┊:l:i:ŋ┊:g:a:￤
Global Similarity Score: 7.476375764313508
Corrected Global Similarity Score: 7.209709097646841
[t - l, ʌ - i, ŋ - ŋ]
[1, 1, 1]
```

### Example 3

A new Corrected Global Similarity Score function can also be set programmatically. In this case, the argument _optionExternalFunction_ should be set to 2, and a fifth argument with the function to use must be added:

- word1 (String): IPA transcription of the first word to be aligned.
- word2 (String): IPA transcription of the second word to be aligned.
- printResults (Boolean): print the results in the console - True = print all the alignments found; False = print only the best alignment.
- optionExternalFunction (Integer): select if using or not an external function - 0 = use default settings (with automatic selection of Global or Corrected Global Similarity Score); 1 = use external function from config. folder; 2 = use function provided as next argument; 3 = use only the Global Similarity Score; 4 = use only the default Corrected Global Similarity Score
- externalFunction (String): alternative function to use for the calculation of the Corrected Global Similarity Score. See readme.md and config/external_corr_glob_sim_score_function.txt for details about the options and syntax of the formula.

The new function must be passed as a string. Basic mathematical operators are recognized, and the following values for the alignment can be retrieved from FAAL and used in the new function:

```
SumFeat = total number of features in common between the two words
NrFeat = number of phonetic features taken into consideration
LongestWord = length of the longest of the two words being aligned
ShortestWord = length of the shortest of the two words being aligned
LenSeq = length of the longest matching sequence, without counting initial or final unmatched pairs.
LenWord1 = length word 1
LenWord2 = length word 2
LenAlign = length of the whole alignment, counting initial or final unmatched pairs.

```

In the default version of FAAL, both the Global and the Corrected Global Similarity Scores are conceived as scores that cumulate the similarities of each pair in the alignment. Therefore, the Global or the Corrected Global Similarity Score of a perfect match word1 = _t_ -  word2 = _t_ is _x_, then the score of a perfect match word1 = _tt_ - word2 =  _tt_ will be _2x_, that of a of a perfect match word1 = _ttt_ - word2 =  _ttt_ will be _3x_, and so on.

This approach allows to higher scores for longer words, which is justified by the fact that obtaining a good but not perfect match with longer words is often more significant (e.g. if we are testing the probability of accidental similarities in two lexical lists) than obtaining perfect matches with shorter word - for instance the imperfect match between Greek [θiɣatɛra] = "daughter" and Scots [doxtəɾ] = "daughter" is more significant, from a historical linguistic perspective, than the perfect match between English [hi:] = "he" and Hebrew [hi:] = "she", because of course the probability of having an accidental perfect match is lower with longer words. 

In some cases, however, it can be useful to have also an average score obtained by dividing the general similarity score by the length of the longest word.

In this current example we will test two functions that provide such a score, one based on the Global Similarity Score, and the other based on the Corrected Global Similarity Score. They are the following:

```java
"((SumFeat) / (NrFeat * 7.71)) / LongestWord"
```

where "((SumFeat) / (NrFeat * 7.71))" is the default Global Similarity Score function and "LongestWord" is the variable storing the length of the longest word (see here above),

and:

```java
"(((SumFeat) / (NrFeat * 7.71)) - ((LenSeq - ShortestWord)/1.04 + ((LenAlign - LenSeq)/LenSeq)) * (1-(ShortestWord/LongestWord))) / LongestWord"
```

where "(((SumFeat) / (NrFeat * 7.71)) - ((LenSeq - ShortestWord)/1.04 + ((LenAlign - LenSeq)/LenSeq)) * (1-(ShortestWord/LongestWord)))" is the default Corrected Global Similarity Score function and "LongestWord" is, again, the variable storing the length of the longest word.

In the following code the words θiɣatɛra and doxtəɾ are aligned first according to the default function, and then according to the two functions just described.

```java
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
```

The results are the following:

```
=== Default functions ===
=== Best Alignment ===
┊θ	i	ɣ	a	t	ɛ	r┊	a	￤
┊d	o	x	0	t	ə	ɾ┊	0	￤
┊:θ:i:ɣ:a:t:ɛ:r┊:a:￤
┊:d:o:x:0:t:ə:ɾ┊:0:￤
Global Similarity Score: 22.943301834352418
Corrected Global Similarity Score: 22.667202933253517
[θ - d, i - o, ɣ - x, t - t, ɛ - ə, r - ɾ]
[1, 1, 1, 1, 1, 1]

=== Global Similarity Score / Length longest word ===
=== Best Alignment ===
┊θ	i	ɣ	a	t	ɛ	r┊	a	￤
┊d	o	x	0	t	ə	ɾ┊	0	￤
┊:θ:i:ɣ:a:t:ɛ:r┊:a:￤
┊:d:o:x:0:t:ə:ɾ┊:0:￤
Global Similarity Score: 22.943301834352418
New function Score / Length: 2.8679127292940523
[θ - d, i - o, ɣ - x, t - t, ɛ - ə, r - ɾ]
[1, 1, 1, 1, 1, 1]

=== Corrected Global Similarity Score / Length longest word ===
=== Best Alignment ===
┊θ	i	ɣ	a	t	ɛ	r┊	a	￤
┊d	o	x	0	t	ə	ɾ┊	0	￤
┊:θ:i:ɣ:a:t:ɛ:r┊:a:￤
┊:d:o:x:0:t:ə:ɾ┊:0:￤
Global Similarity Score: 22.943301834352418
New function Score / Length: 2.8334003666566896
[θ - d, i - o, ɣ - x, t - t, ɛ - ə, r - ɾ]
[1, 1, 1, 1, 1, 1]
```



### Example 4

FAAL can also be used with personalized settings. In order to do so, more arguments can be passed:

- word1 (String): IPA transcription of the first word to be aligned.
- word2 (String): IPA transcription of the second word to be aligned.
- optionExternalFunction (Integer): select if using or not an external function - 0 = do not use any external function; 1 = use external function from config. folder; 2 = use function provided as next argument
- configIPA (List<String>): String array list with variables for the configuration of the IPA_parser.IPA_parser_new module. Instances of this list can be initialized through the _ConfigIPA_ class - see the corresponding JavaDoc, and the documentation and examples online.
- matcherConfig1 (<Boolean>): Boolean array list with variables for the configuration of the FAAL.faal and Matcher.Matcher_new modules. Instances of this list can be initialized through the _MatcherConfig1_ class - see the corresponding JavaDoc, and the documentation and examples online.
- matcherConfig2 (List<Integer>): Integer array list with variables for the configuration of the FAAL.faal and Matcher.Matcher_new modules. Instances of this list can be initialized through the _MatcherConfig2_ class - see the corresponding JavaDoc, and the documentation and examples online.
- matcherConfig3 (List<String>): String array list with variables for the configuration of the FAAL.faal and Matcher.Matcher_new modules. Instances of this list can be initialized through the _MatcherConfig3_ class - see the corresponding JavaDoc, and the documentation and examples online.
- matcherConfig4 (List<Double>): Double array list storing the factors used in the calculation of the Corrected Global Similarity score. It is used in the configuration of the FAAL.faal and Matcher.Matcher_new modules. Instances of this list can be initialized through the _MatcherConfig4_ class - see the corresponding JavaDoc, and the documentation and examples online.

In this examples, all the parameters are set to their default values. The _.printResults( )_ option in the MatcherConfig1 class is set to false.

settingsMatcherConfig1.printResults(false)

In the current example morphological boundaries are explicitly indicated. To do so, the character ￤ (U+FFE4) must be used.

In this example, two polymorphemic words from Japanese and Korean, namely [hɐkːekʲːɯː] and [pekçjʌɭgu], both meaning "leukocyte", are compared. As appears from their Kanji/Hanja spelling 白血球, they are both formed of three distinct (etymologically Chinese) morphemes, and therefore they can be transcribed as hɐ￤kːe￤kʲːɯː and pek￤çj͜ʌɭ￤gu respectively (note also the transcription of the diphthong j͜ʌ in the Korean word). However, the same marker can be used also to explicitly indicate the boundaries of prefixes, suffixes, and distinct morphemic elements in compound words - see next example. The settings used here are the same of the default configuration. Each of them, however, can be modified and tuned by the user.

Here a minimal working example, without and with the morphemic boundary marker ￤ (U+FFE4):


```java
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
		settingsMatcherConfig1.matchSameCategory(true);

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
```

The results are:

```
=== Japanese hɐkːekʲːɯː ~ Korean pekçj͜ʌɭgu ===
~~ Without Morphemic Boundaries ~~
=== Best Alignment ===
┊h	ɐ	kː	0	e	0	kʲː	ɯː┊	￤
┊p	e	k	ç	j͜ʌ	ɭ	g	u┊	￤
┊:h:ɐ:k:0:e:0:k:ɯ┊:￤
┊:p:e:k:ç:ʌ:ɭ:g:u┊:￤
Global Similarity Score: 21.42857142857143
Corrected Global Similarity Score: 20.9478021978022
[h - p, ɐ - e, kː - k, e - j͜ʌ, kʲː - g, ɯː - u]
[1, 1, 1, 1, 1, 1]

=== Japanese hɐ￤kːe￤kʲːɯː ~ Korean pek￤çj͜ʌɭ￤gu ===
~~ Without Morphemic Boundaries ~~
=== Best Alignment ===
┊h	ɐ	0	￤	kː	e	0	￤	kʲː	ɯː┊	￤
┊p	e	k	￤	ç	j͜ʌ	ɭ	￤	g	u┊	￤
┊:h:ɐ:0:￤:k:e:0:￤:k:ɯ┊:￤
┊:p:e:k:￤:ç:ʌ:ɭ:￤:g:u┊:￤
Global Similarity Score: 19.839725773577914
Corrected Global Similarity Score: 19.358956542808684
[h - p, ɐ - e, kː - ç, e - j͜ʌ, kʲː - g, ɯː - u]
[1, 1, 1, 1, 1, 1]
```

The use of the morphemic boundary markers explicitly indicates that the kː of hɐkːekʲːɯː is the first consonant of a second morpheme kːe, and therefore it allows FAAL to correctly align such kː with its correct etymological counterpart ç in pekçj͜ʌɭgu. Instead, when these words are aligned without morphemic boundaries, the kː of hɐkːekʲːɯː is the k of pekçj͜ʌɭgu, which is indeed more similar, in absolute terms.

Note that both these alignments are correct, in principle. The point if what one is looking for. I the aim is to have an etymologically correct alignment, then using morphemic boundary markers allows for a more accurate result. If instead one is looking for the best alignment from a purely phonological perspective, then it is clear that kː is closer to k than to ç, and therefore the alignment without morphemic boundary markers is preferable.


### Example 5

The morphemic boundaries can be used also to compare words with differing numbers of morphemes. In the present example, the following pair is compared:

```
ɒlɪgəʊ̯ (i.e. UK English oligo- )
ɒlɪg￤ɑːki (i.e. UK English oligarchy )
```

Note that the final -əʊ̯ (for the use of ̯ - U+032F to transcribe diphthongs see online documentation) or the morpheme ɒlɪgəʊ̯ is dropped when the following morpheme starts with a vowel, hence the transcription ɒlɪg￤ɑːki.

These two words can be compared using the basic implementation of FAAL illustrated in Example 1:


```java
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
```

The results are the following:

```
=== Best Alignment ===
┊ɒ	l	ɪ	g┊	əʊ̯	￤	0	0	0	0
┊ɒ	l	ɪ	g┊	0	￤	ɑː	k	i	￤
┊:ɒ:l:ɪ:g┊:ə:￤:0:0:0:0
┊:ɒ:l:ɪ:g┊:0:￤:ɑ:k:i:￤
Global Similarity Score: 15.508615897720956
Corrected Global Similarity Score: 15.497626886731945
[ɒ - ɒ, l - l, ɪ - ɪ, g - g]
[1, 1, 1, 1]
```

Note that the introduction of the morphemic boundary, marked with ￤, allows FAAL to recognize that the əʊ̯ of ɒlɪgəʊ̯ and the ɑː of ɒlɪgɑːki are not part of the same morphemes and therefore should not be aligned, although they are indeed similar and are located exactly in the same position.

### Example 6

All the sub-modules of FAAL can be called independently, as shown in the following code using the words of Example 1 above:

```java
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
```

As it appears, the results are the same obtained in Example 1:

```
=== Best Alignment ===
0	0┊	k	e	n	t	u	m┊	￤
h	e┊	k	a	0	t	o	n┊	￤
:0:0┊:k:e:n:t:u:m┊:￤
:h:e┊:k:a:0:t:o:n┊:￤
Global Similarity Score: 19.31628682601445
Corrected Global Similarity Score: 19.268667778395404
[k - k, e - a, t - t, u - o, m - n]
[1, 1, 1, 1, 1]
```



## Examples - customising the config files

All the examples just presented are equivalent, as they all use the default values.

FAAL, however, can also be customized, and both the settings and the configuration files can be modified by the user for instance to include phonemes or features not considered in the default settings (such a prosodic features), to modify the values of specific parameters (e.g., to emphasise the matches between one or more specific features), or to modify the algorithm to other kinds of feature-based comparisons altogether. A detailed description of all the parameters and files that can be customised can be found in the file FAAL_library_user_manual.pdf available in the GitHub repository.

The following examples show how the config files of FAAL can be modified to different ways to include tones in the comparison. Further details about these two examples can be found in the article.

### Example 7 - Tones: contour-based

The first example shows a possible way to modify FAAL so that the algorithm will include tones in the comparison, and will compare them primarily on the basis of their form (level tone, falling tone, rising tone, dipping tone, peaking tone), and secondarily on the basis of their pitch level or levels.

The aim is to compare two words taken from three different Bai dialects. They are taken from List's and Prokić's BDPA database, for which see:

```
http://alignments.lingpy.org

List, Johann-Mattis and Jelena Prokić. (2014). A benchmark database of phonetic alignments in historical linguistics and dialectology. In: Proceedings of the International Conference on Language Resources and Evaluation (LREC), 26 — 31 May 2014, Reykjavik. 288-294.

Wang, F. (2006): Comparison of languages in contact. The distillation method and the case of Bai. Taipei: INstitue of Linguistics Academia Sinica.

Allen, B. (2007): Bai dialect survey. SIL International. ULR: http://www.sil.org/silesr/2007/silesr2007-012.pdf
```

The words are the following (note that the non-standard IPA character ɿ has been changed into ɨ - using other transcriptions, like ɯ, would also be possible):

Qīlǐqiáo:
```
s	ɯ	³³	ʦ	ɨ	³¹	ʦ	ɨ	³³
```

Dàshí
```
ʂ	ɯ	³³	t	i	³¹
```

Mǎzhělóng
```
s	əw̯	³³	ʦ	i	⁴⁴
```

And according to List's and Prokić's BDPA database ( phonalign_130.msa - see Multiple Alignments, meaning: "fingernail"), their correct alignment is:

```
Qīlǐqiáo	s	ɯ	³³	ʦ	ɨ	³¹	ʦ	ɨ	³³
Dàshí		ʂ	ɯ	³³	t	i	³¹	-	-	-
Mǎzhělóng	s	əw̯	³³	-	-	-	ʦ	i	⁴⁴
```

These three words are a perfect test case: as it appears, the Qīlǐqiáo word ends with two syllables that are identical, except for their tones.
By contrast, the Dàshí and Mǎzhělóng words end only with one syllable, which respectively correspond to the second and third syllable of the Qīlǐqiáo word.

Since the two final syllables of the Qīlǐqiáo words are identical except for the tone, the basic form of FAAL would be unable identify the correct alignment for the corresponging syllables of the Dàshí and Mǎzhělóng. Rather, if morphemic boundaries are used (see below), for both the alignments Qīlǐqiáo-Dàshí and Qīlǐqiáo-Mǎzhělóng the default version of FAAL will produce two possible alignments with exactly the same similarity scores, one in which the last syllable of the Dàshí/Mǎzhělóng word is aligned with the second syllable of the Qīlǐqiáo word, and one in which the same syllable is aligned with the third syllable of the Qīlǐqiáo word. 


This can be easily tested, comparing the words in the following forms:

```
Qīlǐqiáo	sɯ￤ʦɨ￤ʦɨ
Dàshí		ʂɯ￤ti	
```

and

```
Qīlǐqiáo	sɯ￤ʦɨ￤ʦɨ
Mǎzhělóng	səw̯￤ʦi
```

the following basic implementation of the algorithm can be used:

```
FAAL.align("sɯ￤ʦɨ￤ʦɨ", "ʂɯ￤ti", true)
```

and 

```
FAAL.align("sɯ￤ʦɨ￤ʦɨ", "səw̯￤ʦi", true)
```

which results in the following alignments:

```
========
┊s	ɯ	￤	ʦ	ɨ┊	￤	ʦ	ɨ	￤
┊ʂ	ɯ	￤	t	i┊	￤	0	0	0
┊:s:ɯ:￤:ʦ:ɨ┊:￤:ʦ:ɨ:￤
┊:ʂ:ɯ:￤:t:i┊:￤:0:0:0
Global Similarity Score:
14.855475264035576
Corrected Global Similarity Score:
14.68880859736891
Attested Pairs:
[s - ʂ, ɯ - ɯ, ʦ - t, ɨ - i]
Number of Attestations of the Attested Pairs:
[1, 1, 1, 1]
----
========
┊s	ɯ	￤	ʦ	ɨ	￤	ʦ	ɨ┊	￤
┊ʂ	ɯ	￤	0	0	0	t	i┊	￤
┊:s:ɯ:￤:ʦ:ɨ:￤:ʦ:ɨ┊:￤
┊:ʂ:ɯ:￤:0:0:0:t:i┊:￤
Global Similarity Score:
14.855475264035576
Corrected Global Similarity Score:
14.214449623009935
Attested Pairs:
[s - ʂ, ɯ - ɯ, ʦ - t, ɨ - i]
Number of Attestations of the Attested Pairs:
[1, 1, 1, 1]
```

and:

```
========
┊s	ɯ	￤	ʦ	ɨ┊	￤	ʦ	ɨ	￤
┊s	əw̯	￤	ʦ	i┊	￤	0	0	0
┊:s:ɯ:￤:ʦ:ɨ┊:￤:ʦ:ɨ:￤
┊:s:ə:￤:ʦ:i┊:￤:0:0:0
Global Similarity Score:
15.128775245506763
Corrected Global Similarity Score:
14.962108578840096
Attested Pairs:
[s - s, ɯ - əw̯, ʦ - ʦ, ɨ - i]
Number of Attestations of the Attested Pairs:
[1, 1, 1, 1]
----
========
┊s	ɯ	￤	ʦ	ɨ	￤	ʦ	ɨ┊	￤
┊s	əw̯	￤	0	0	0	ʦ	i┊	￤
┊:s:ɯ:￤:ʦ:ɨ:￤:ʦ:ɨ┊:￤
┊:s:ə:￤:0:0:0:ʦ:i┊:￤
Global Similarity Score:
15.128775245506763
Corrected Global Similarity Score:
14.487749604481122
Attested Pairs:
[s - s, ɯ - əw̯, ʦ - ʦ, ɨ - i]
Number of Attestations of the Attested Pairs:
[1, 1, 1, 1]
```


As it appears, the global similarity score of both alignments in both cases is the same, while the corrected global similarity score is better when the last syllable of the Dàshí and Mǎzhělóng words are aligned with the second syllable of the Qīlǐqiáo form. This normal is, because since there is no difference between the last two syllables of the Qīlǐqiáo word, the corrected global similarity score will favour the alignment that are more compact, with less gaps within both words.

Better results can be obtained if tones are considered, and if FAAL's settings are modified accordingly.

First, one has to find a way to describe tones in a way that can be parsed and compared by FAAL.
Various solutions are possible. 

First of all, tones can be conceptualized as additional (prosodic) features of the phonemes to which they are associated.

As discussed in the article, FAAL encodes phonemes as vectors of features.

Therefore, a way to include tones into FAAL is to encode them through a series of new, additional features added to the already existing vectors of features of the various phonemes.

In particular, since in this example tones will be compared primarily on the basis of their form, and secondarily on the basis of their pitch level or levels, the following sets of additional binary features can be devised:

Contour-related features:

> 1. level tone 	2. falling tone	 3. rising tone		4. dipping tone		5. peaking tone

Level-related features:

> 6. Initial Level of the tone: position 1	7. Mid level of the tone: position 1	8. Final level of the tone: position 1
> 9. Initial Level of the tone: position 2	10. Mid level of the tone: position 2	11. Final level of the tone: position 2
> 12. Initial Level of the tone: position 3	13. Mid level of the tone: position 3	14. Final level of the tone: position 3
> 15. Initial Level of the tone: position 4	16. Mid level of the tone: position 4	17. Final level of the tone: position 4
> 18. Initial Level of the tone: position 5	19. Mid level of the tone: position 5	20. Final level of the tone: position 5


The following table gives a few examples of tones described according to this system of binary features:

|  | 1. | 2. | 3. | 4. | 5. | | 6. | 7. | 8. | 9. | 10. | 11. | 12. | 13. | 14. | 15. | 16. | 17. | 18. | 19. | 20. |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| ˥˥ | 1 | 0 | 0 | 0 | 0 | | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 1 |
| ˧˧ | 1 | 0 | 0 | 0 | 0 | | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 1 | 0 | 0 | 0 | 0 | 0 | 0 |
| ˦˨ | 0 | 1 | 0 | 0 | 0 | | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 0 | 0 | 1 | 0 | 0 | 0 | 0 | 0 |
| ˦˥ | 0 | 0 | 1 | 0 | 0 | | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 0 | 0 | 0 | 1 |
| ˨˩˦ | 0 | 0 | 0 | 1 | 0 | | 0 | 1 | 0 | 1 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 0 | 0 |
| ˩˦˧ | 0 | 0 | 0 | 0 | 1 | | 1 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 1 | 0 | 0 | 0 | 0 |

The first tone, ˥˥, can be described as a level tone [+ feature 1.], starting from level 5 [+ feature 18.] and ending at level 5 [+ feature 20.].

The second tone, ˧˧, can also be described as a level tone [+ feature 1.], starting from level 3 [+ feature 12.] and ending at level 3 [+ feature 14.].

The third tone, ˦˨, can also be described as a falling tone [+ feature 2.], starting from level 4 [+ feature 15.] and ending at level 2 [+ feature 11.].

The fourth tone, ˦˥, can also be described as a rising tone [+ feature 3.], starting from level 4 [+ feature 15.] and ending at level 5 [+ feature 20.].

The fifth tone, ˨˩˦, can also be described as a dipping tone [+ feature 4.], starting from level 2 [+ feature 9.], dipping to level 1 [+ feature 7.], and ending at level 4 [+ feature 17.].

Finally, the sixth tone, ˩˦˧, can also be described as a rising tone [+ feature 5.], starting from level 1 [+ feature 6.], rising to level 4 [+ feature 16.], and ending at level 3 [+ feature 14.].


In order to implement these additional features into FAAL, new versions of the following config files need to be created:
_phon_features.txt_; _phon_diacritics.txt_ ; _saliences_to_use_matches_phon_categories.txt_ , as well as new versions of the following files within the folder _config/saliences_ : _salience_cat_1.txt_ ; _salience_cat_2.txt_ ; _salience_cat_3.txt_ .

Note that it is better to create new distinct config files and to point to them when running the algorithm (see below), rather than modifying the existing default ones.

This will allow to switch from one configuration to the other by just changing the settings of FAAL related with the config files, rather than by modifying every time the files themselves.

The new config files can be found in the folder _config_contour_tones_.

They have been created as follow:

_phon_features_contour_tones.txt_ is created starting from _phon_features.txt_ . phon_features.txt contains the feature vectors for each of the phonemes recognized by default by FAAL. The structure of the file is simple, and corresponds to a matrix where each line correspond to a phoneme, and each column except the first corresponds to a specific feature. The first column contains the Unicode characters representing the various phonemes. Columns are separated by a horizontal tab character (HT - U+0009). Lines starting with % are ignored, and can be used to add comments.

The default version of _phon_features.txt_ encodes 27 features. 20 additional columns corresponding to the new features for tones need to be added. Probably, the easiest way to do that is to use a spreadsheet editor.

By default, the value of all these new features can be set to 0, as this configuration represents the default case, where no tone is associated to the phoneme.

The resulting file should look like this (only a few lines are reproduced here):

```
%	0.	1.	2.	3.	4.	5.	6.	7.	8.	9.	10.	11.	12.	13.	14.	15.	16.	17.	18.	19.	20.	21.	22.	23.	24.	25.	26.	27.	28.	29.	30.	31.	32.	33.	34.	35.	36.	37.	38.	39.	40.	41.	42.	43.	44.	45.	46.	47.
p	0	-	+	-	-	-	-	-	-	-	-	-	-	+	-	-	-	0	0	0	-	-	0	0	0	0	0	-	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
b	0	-	+	-	-	-	-	-	-	-	+	-	-	+	-	-	-	0	0	0	-	-	0	0	0	0	0	-	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
ɸ	0	-	+	-	+	+	-	-	-	-	-	-	-	+	-	-	-	0	0	0	-	-	0	0	0	0	0	-	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
β	0	-	+	-	+	+	-	-	-	-	+	-	-	+	-	-	-	0	0	0	-	-	0	0	0	0	0	-	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
...
```

Then, the saliences for these new features need to be configured. To do that, the files _salience_cat_1_contour_tones.txt_; _salience_cat_2_contour_tones.txt_; _salience_cat_3_contour_tones.txt in the _saliences_ subfolder need to be created. Moreover, a new file _saliences_to_use_matches_phon_categories_contour_tones.txt_ explicitly pointing the these salience files needs to be created as well.

Also in this case, the default files _salience_cat_1.txt_; _salience_cat_2.txt_; _salience_cat_3.txt_ can be used as starting points.

These files all share the same structure: there are two columns, and each line corresponds to a feature. The first column contains the number corresponding to the feature (see files _phon_features.txt_ and _phon_features_contour_tones.txt_), while the second column correspond to the multiplying factor of the salience to be applied to the feature.
As a good practice, each row is preceded by a line of comment (introduced by %) given a brief description of the feature.

Since the contour of the tones will be the primary comparing factor, the salience of the corresponding features can be increased. It this example we will set it to 5, which is a low but still significant value, compared with the other saliences. Since the levels of the tones will be only a secondary criterion in the comparison, their salience can be low - in this example we set it to 1.

In this example, we treat tones as features of vocalic phonemes only, therefore specific saliences need to be set only in the case a vowel is involved. As appears from the file _saliences_to_use_matches_phon_categories.txt_ , matches between between vowels are modified by the file _salience_cat_2.txt. Therefore, the following lines have to be added to the new config file _salience_cat_2_contour_tones.txt_:

```
% 28 - Tone_contour - level_tone
28	5
% 29 - Tone_contour - falling_tone
29	5
% 30 - Tone_contour - rising_tone
30	5
% 31 - Tone_contour - dipping_tone
31	5
% 32 - Tone_contour - peaking_tone
32	5
% 33 - Tone_start - position 1
33	1
% 34 - Tone_mid - position 1
34	1
% 35 - Tone_end - position 1
35	1
% 36 - Tone_start - position 2
36	1
% 37 - Tone_mid - position 2
37	1
% 38 - Tone_end - position 2
38	1
% 39 - Tone_start - position 3
39	1
% 40 - Tone_mid - position 3
40	1
% 41 - Tone_end - position 3
41	1
% 42 - Tone_start - position 4
41	1
% 43 - Tone_mid - position 4
43	1
% 44 - Tone_end - position 4
44	1
% 45 - Tone_start - position 5
45	1
% 46 - Tone_mid - position 5
46	1
% 47 - Tone_end - position 5
47	1
```

By contrast, the features related with the tones should be ignored when a consonant or a semiconsonant is involved, or when a vowel is matched with a non-vowel. As appears from the file _saliences_to_use_matches_phon_categories.txt_, such matches are modified by the salience files _salience_cat_1.txt and _salience_cat_3.txt. Therefore, the saliences of features related to tones have to be set to 0 in the new files _salience_cat_1_contour_tones.txt_ and _salience_cat_3_contour_tones.txt_. These files can be created by adding the following lines to the files _salience_cat_1.txt_; _salience_cat_3.txt_.

```
% 28 - Tone_contour - level_tone
28	0
% 29 - Tone_contour - falling_tone
29	0
% 30 - Tone_contour - rising_tone
30	0
% 31 - Tone_contour - dipping_tone
31	0
% 32 - Tone_contour - peaking_tone
32	0
% 33 - Tone_start - position 1
33	0
% 34 - Tone_mid - position 1
34	0
% 35 - Tone_end - position 1
35	0
% 36 - Tone_start - position 2
36	0
% 37 - Tone_mid - position 2
37	0
% 38 - Tone_end - position 2
38	0
% 39 - Tone_start - position 3
39	0
% 40 - Tone_mid - position 3
40	0
% 41 - Tone_end - position 3
41	0
% 42 - Tone_start - position 4
41	0
% 43 - Tone_mid - position 4
43	0
% 44 - Tone_end - position 4
44	0
% 45 - Tone_start - position 5
45	0
% 46 - Tone_mid - position 5
46	0
% 47 - Tone_end - position 5
47	0
```

Finally, a new file _saliences_to_use_matches_phon_categories_contour_tones.txt_ that explicitely points to these new salience files need to be created. To do so, it is enough to create a new file based on the file _saliences_to_use_matches_phon_categories.txt_ in which all references in the last column to the files _config/saliences/salience_cat_1.txt_ ; _config/saliences/salience_cat_2.txt_ ; _config/saliences/salience_cat_3.txt_ are changed to references to the files _config_contour_tones/saliences/salience_cat_1_contour_tones.txt_ ; _config_contour_tones/saliences/salience_cat_2_contour_tones.txt_ ; _config_contour_tones/saliences/salience_cat_3_contour_tones.txt_ respectively.

Then, a transcription system to mark these features has to be devised. Again, various solutions are possible. In this example the following system is used:

The following signs are used to indicate the Contour-related features of the tone:

| char. | feature |
| --- | --- |
| ̄ (U+0304) | level tone |
| ̂ (U+0302) | falling tone |
| ̌ (U+030C) | rising tone |
| ᷉ (U+1DC9) | dipping tone |
| ᷈ (U+1DC8) | peaking tone |

To mark the level of the tones at the beginning, in the middle and at the end superscript numerals, regular numerals, and subscript numerals are used, as follow:

|  | Initial | Mid | Final |
| --- | --- | --- | --- |
| ˩ | ¹ | 1 | ₁ |
| ˨ | ² | 2 | ₂ |
| ˧ | ³ | 3 | ₃ |
| ˦ | ⁴ | 4 | ₄ |
| ˥ | ⁵ | 5 | ₅ |

Therefore, the tones of the table above can be transcribed as follow:

| tone | transcription |
| --- | --- |
| ˥˥ | ̄⁵₅ |
| ˧˧ | ̄³₃ |
| ˦˨ | ̂⁴₂ |
| ˦˥ | ̌⁴₅ |
| ˨˩˦ | ᷉²1₄ |
| ˩˦˧ | ᷈¹4₃ |

Since we interpret tones as features of the associated phonemes, we can treat their transcriptions as diacritics of the associated phonemes. Therefore, a file _phon_diacritics_contour_tones.txt_ needs to be created, starting from the default file _phon_diacritics.txt_.

This file has three columns, and each line corresponds to a diacritic. The first column contains the diacritic character, the second column contains an index indicating if the diacritic character is written before (marked as 1) or after (marked as 0) the phoneme it modifies. Finally, the third column contains the features of the main phonemes that are modified by the diacritic. The format of this last column is: nr_of_the_feature_to_be_modified - semicolon - new_value_of_the_feature. When more than one feature is modified by the same diacritic, all the features are listed in the third column according to the format just described, separated by a space (U+0020).

In order to create the file _phon_diacritics_contour_tones.txt_, the following lines can be added to the file _phon_diacritics.txt_:

```
̄	0	28;+	
̂	0	29;+	
̌	0	30;+	
᷉	0	31;+	
᷈	0	32;+
¹	0	33;+
1	0	34;+
₁	0	35;+
²	0	36;+
2	0	37;+
₂	0	38;+
³	0	39;+
3	0	40;+
₃	0	41;+
⁴	0	42;+
4	0	43;+
₄	0	44;+
⁵	0	45;+
5	0	46;+
₅	0	47;+
```

One this files have been created, they need be saved in the folder config.

Then, FAAL can be used with this new configuration. The algorithm needs to be used with personalized settings (see Example 4 above).

In the following example, the words Qīlǐqiáo: sɯ̄³₃￤ʦɨ̂³₁￤ʦɨ̄³₃￤ and Dàshí ʂɯ̄³₃￤tî³₁￤ , and Qīlǐqiáo: sɯ̄³₃￤ʦɨ̂³₁￤ʦɨ̄³₃￤ and Mǎzhělóng səw̯̄³₃￤ʦī⁴₄￤ are compared.

```java
package examples;

import java.util.ArrayList;
import java.util.List;

import faal.*;

public class Example7 {

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
		// The location of the new config files to compare the Bai tones needs to be
		// set here:
	
		settingsConfigIPA.pathPhonologicalFeatures("config_contour_tones/phon_features_contour_tones.txt");
		settingsConfigIPA.pathPhoneticDiacritics("config_contour_tones/phon_diacritics_contour_tones.txt");
		settingsConfigIPA.saliencesMatchesPhonCategories("config_contour_tones/saliences_to_use_matches_phon_categories_contour_tones.txt");

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

		settingsMatcherConfig3.pathPhonologicalFeatures("config_contour_tones/phon_features_contour_tones.txt");
		settingsMatcherConfig3.pathPhoneticDiacritics("config_contour_tones/phon_diacritics_contour_tones.txt");

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

		System.out.println("=== Qīlǐqiáo: sɯ̄³₃￤ʦɨ̂³₁￤ʦɨ̄³₃￤ ~ Dàshí ʂɯ̄³₃￤tî³₁￤ ===");

		word1 = "sɯ̄³₃￤ʦɨ̂³₁￤ʦɨ̄³₃￤";
		word2 = "ʂɯ̄³₃￤tî³₁￤";
		resultsAlignments = FAAL.align(word1, word2, optionExternalFunction, configIPA, matcherConfig1, matcherConfig2,
				matcherConfig3, matcherConfig4);
		PrintAlignment(resultsAlignments);

		System.out.println();

		// -------

		System.out.println("=== Qīlǐqiáo: sɯ̄³₃￤ʦɨ̂³₁￤ʦɨ̄³₃￤ ~ Mǎzhělóng səw̯̄³₃￤ʦī⁴₄￤ ===");

		word1 = "sɯ̄³₃￤ʦɨ̂³₁￤ʦɨ̄³₃￤";
		word2 = "səw̯̄³₃￤ʦī⁴₄￤";
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
```

The results are:

```
=== Qīlǐqiáo: sɯ̄³₃￤ʦɨ̂³₁￤ʦɨ̄³₃￤ ~ Dàshí ʂɯ̄³₃￤tî³₁￤ ===
=== Best Alignment ===
┊s	ɯ̄³₃	￤	ʦ	ɨ̂³₁┊	￤	ʦ	ɨ̄³₃	￤
┊ʂ	ɯ̄³₃	￤	t	î³₁┊	￤	0	0	0
┊:s:ɯ:￤:ʦ:ɨ┊:￤:ʦ:ɨ:￤
┊:ʂ:ɯ:￤:t:i┊:￤:0:0:0
Global Similarity Score: 8.881863380890618
Corrected Global Similarity Score: 8.715196714223952
[s - ʂ, ɯ̄³₃ - ɯ̄³₃, ʦ - t, ɨ̂³₁ - î³₁]
[1, 1, 1, 1]

=== Qīlǐqiáo: sɯ̄³₃￤ʦɨ̂³₁￤ʦɨ̄³₃￤ ~ Mǎzhělóng səw̯̄³₃￤ʦī⁴₄￤ ===
=== Best Alignment ===
┊s	ɯ̄³₃	￤	ʦ	ɨ̂³₁	￤	ʦ	ɨ̄³₃┊	￤
┊s	əw̯̄³₃	￤	0	0	0	ʦ	ī⁴₄┊	￤
┊:s:ɯ:￤:ʦ:ɨ:￤:ʦ:ɨ┊:￤
┊:s:ə:￤:0:0:0:ʦ:i┊:￤
Global Similarity Score: 9.030479896238651
Corrected Global Similarity Score: 8.38945425521301
[s - s, ɯ̄³₃ - əw̯̄³₃, ʦ - ʦ, ɨ̄³₃ - ī⁴₄]
[1, 1, 1, 1]
```

In both cases, these settings allow FAAL to identify the best alignment.


### Example 8 - Tones: etymology-based


Tones can also be compared, and integrated into FAAL, from a perspective that considers their etymological origin, rather than their form, as in the previous example.

Such an approach can be particularly suited in the case of tonal languages which are particularly divergent in their tonal systems, or when comparing a non-tonal language with a related language that developed tones.

This example focuses on the latter case, by showing how FAAL can be configured to compare and align words in Hindi and Punjabi.

Moreover, this example shows how the coefficient of similarity can be used not only to identify the best alignment among various possibly alignments of two words, but also to identify the best pair among various possible pairs of phonemically similar words.

Hindi, like most Indo-Aryan languages, is non-tonal. Punjabi, by contrast, developed a system of three tones, namely high-falling (usually transcribed as `), low-rising (usually transcribed as ´), and level/neutral (usually not transcribed). 

It is usually considered that the high-falling tone emerged as a consequence of the devoicing and loss of aspiration of _preceding_ voiced aspirated consonants in initial position. By contrast, the low-rising tone is consider to have emerged from the loss of aspiration of _following_ voiced aspirated consonants in non-initial position.

Minimal pairs exist in Punjabi, and many of the words forming these minimal pairs have non-tonal cognates in Hindi. A very good example, covering all the three tones of Punjabi, is the following:

| Meaning | Punjabi | Hindi |
| --- | --- | --- |
| horse | [kòːɽaː] | [gʰoːɽɑː] |
| leper | [kóːɽaː] | [koːɽʰi] |
| whip | [koːɽaː] | [koːɽɑː] |

The default configuration of FAAL can correctly align these words, but since the Punjabi forms differ only in their tones, the similarity scores between each Hindi word and any Punjabi words would be the same. The following table illustrates these results, obtained with the default configuration of FAAL:

|  | [kòːɽaː]<br>'horse' | [kóːɽaː]<br>'leper' | [koːɽaː]<br>'whip' |  |
| --- | --- | --- | --- | --- |
| [gʰoːɽɑː]<br>'horse' | 15.12414304243098<br>15.12414304243098 | 15.12414304243098<br>15.12414304243098 | 15.12414304243098<br>15.12414304243098 | Glob. Sim. Score <br> Corr. Glob. Sim. Score |
| [koːɽʰi]<br>'leper' | 15.480822679266259<br>15.480822679266259 | 15.480822679266259<br>15.480822679266259 | 15.480822679266259<br>15.480822679266259 | Glob. Sim. Score <br> Corr. Glob. Sim. Score |
| [koːɽɑː]<br>'whip' | 15.133407448582545<br>15.133407448582545 | 15.133407448582545<br>15.133407448582545 | 15.133407448582545<br>15.133407448582545 | Glob. Sim. Score <br> Corr. Glob. Sim. Score |


It is clear that the Similarity Scores obtained with the default configuration of FAAL can be used to correctly align these words, but it cannot be used to identify which one, among the Punjabi words, is the closer match for each of the Hindi words.

This issue can be solved by modifying FAAL so that the algorithm can integrate the Punjabi tones into the comparison process.

As above, a series of new config files needs to be created. Since here the form of the tones is not significant, however, a simpler approach can be devised.

In this case the high-falling and low-rising tones can be conceptualised as a reflex of the features of the associated consonant from which the derive, historically. 

First of all, the very opposition between "presence of high-falling or low-rising tone" vs "absence of such tones/level tone" can be seen as a feature of the associated consonant.

Moreover, the high-falling tone can be seen as a reflex of the historical aspiration and voiceness of the previous consonant, while the low-rising tone can be seen as a reflex of the historical aspiration of the following consonant.

Therefore, we can represent these two tones through diacritics linked to the associated consonants. Such diacritics would represent a feature [+ tone], and would modify the features related to aspiration and voice of the associated consonants according to their historical origins.

This approach can be implemented in FAAL as follow (for details about the structure of the config files involved, see Example 7).

First, a new version of the file _phon_features.txt_ needs to be created. The new file is called _phon_features_punjabi_tones.txt_, and can be created by adding one column (no. 28) to the file _phon_features.txt_. This columns represents the new feature _tone_, and its default value can be set to - for all rows/phonemes.

Then, the salience for this new feature has to be set. 

This can be done through three new config files: _salience_cat_1_punjabi_tones.txt_, _salience_cat_2_punjabi_tones.txt_, and _salience_cat_3_punjabi_tones.txt_.

Since Punjabi tones are strictly related with consonants, and matches involving consonants are modifies by the salience file _salience_cat_1.txt_ (see the file _saliences_to_use_matches_phon_categories.txt_ ), the file _salience_cat_1_punjabi_tones.txt_ can be created by adding the following lines to the file _salience_cat_1.txt_. This new feature will not be an important factor in the alignment process, and its main function will only be to introduce a slight distinction between theoretically possible minimal pairs differing only because of the presence/absence of a tone. Therefore, its salience can be set to 1.

```
% 28 - Tone - yes/no
28	1
```

The features modified by the tones, instead, can be set to 2, instead of the default 1. This is to compensate for the addition of the feature "tone", that will always mismatch when a Punjabi tonal word will be compared with a non-tonal Hindi word and therefore would otherwise nullify one of matches in the features derived by the presence of the tones themselves. The lines corresponding lines therefore have to be modified as follow:

```
% 10 - Laryng. - voice
10	2
%
% 11 - Laryng. - spread glottis
11	2
%
% 12 - Laryng. - constr glottis
12	2
```

By contrast, within the approach adopted in this example, the presence of tones is irrelevant for matches involving vowels, or matches involving phonemes with different "feature Manner - delayed release" (for details on this point, see file _saliences_to_use_matches_phon_categories.txt_ ). Therefore in these case the salience of the new feature _tone_ can be set to 0, and therefore the files _salience_cat_2_punjabi_tones.txt_ and _salience_cat_3_punjabi_tones.txt_ can be created by adding the following lines to the files _salience_cat_2.txt_, and _salience_cat_3.txt_:

```
% 28 - Tone - yes/no
28	0
```

No modification of any other feature is needed.

Finally, a new file _saliences_to_use_matches_phon_categories_punjabi_tones.txt_ that explicitely points to these new salience files need to be created. To do so, it is enough to create a new file based on the file _saliences_to_use_matches_phon_categories.txt_ in which all references in the last column to the files _config/saliences/salience_cat_1.txt_ ; _config/saliences/salience_cat_2.txt_ ; _config/saliences/salience_cat_3.txt_ are changed to references to the files _config_punjabi_tones/saliences/salience_cat_1_punjabi_tones.txt_ ; _config_punjabi_tones/saliences/salience_cat_2_punjabi_tones.txt_ ; _config_punjabi_tones/saliences/salience_cat_3_punjabi_tones.txt_ respectively.

Then, a way to transcribe the tones had to be devised. Since in the systems adopted here tones are conceptualized in relation with nearby consonants, they can be transcribed as diacritics following (in the case of the high-falling tone) or preceding (in the case of the low_rising tone) such consonants. In this example, the characters ˨ (U+02E8) and ꜓ (U+A713) will be used to represent the high-falling tone and the low-rising tone respectively. Since the level tone does not reflect any modification of any associated phoneme, it is left unmarked.

Therefore, the three Punjabi words mentioned above will be transcribed as follow:

| Meaning | Punjabi | Transcription |
| --- | --- | --- |
| horse | [kòːɽaː] | koː˨ɽaː |
| leper | [kóːɽaː] | k꜓oːɽaː |
| whip | [koːɽaː] | koːɽaː |

Finally, in order to make FAAL correctly parse these characters, a new file _phon_diacritics_punjabi_tones.txt_ is needed. This file explain how the characters ˨ and ꜓ interact with the surrounding phonemes, and which features of the associated phonemes they modify. This file can be created by adding the following lines to the file _phon_diacritics.txt_ (for the structure of this file, see above Example 7):

```
˨	1	28;+ 11;+ 12;-
꜓	0	28;+ 11;+ 12;- 10;+
```

These lines must be interpreted as follow:

- the low-rising tone, marked with ˨, should be treated as a diacritic modifying the _following_ consonant. In its case, therefore, the value of the first column must be set to 1 . Obviously, ˨ represents a tone, therefore the new feature _tone_ (no. 28) of the associated, following consonant must be modified accordingly, hence 28;+ . Moreover, the presence of this tone reflects the loss of aspiration of the following consonant. 
In other words, the presence of the tone indicates that, historically, the following consonant was aspirated, and therefore the relevant features of such consonant must be modified accordingly. Aspiration, however, is not encoded as a single feature, but rather as a combination of [+ spread glottis], hence 11;+, and of the obvious opposite [- constricted glottis], hence 12;-.

- by contrast, the high-falling tone, marked with ꜓, should be treated as a diacritic modifying the _preceding_ consonant. In its case, therefore, the value of the first column must be set to 0 . Here as well ꜓ represents a tone, therefore the new feature _tone_ (no. 28) of the associated, preceding consonant must be modified accordingly, hence 28;+ . Moreover, the presence of this tone reflects the loss of aspiration and the devoicing of the following consonant. 

In other words, the presence of the low-rising tone, marked with ˨, indicates that, historically, the following consonant was aspirated, and therefore the relevant features of such consonant must be modified accordingly. Aspiration, however, is not encoded as a single feature, but rather as a combination of [+ spread glottis], hence 11;+, and of the obvious opposite [- constricted glottis], hence 12;-.
Instead, the presence of the high-falling tone, marked with ꜓, indicates that, historically, the previous consonant was aspirated, that is [+ spread glottis] hence 11;+, and [- constricted glottis] hence 12;-, and it was originally [+ voice], hence 10;+ .

As in the previous example, all these files must be saved in the config folder.

The files created in this example can be found in the folder _config_punjabi_tones_.

These new settings can be used to compare the three Punjabi and Hindi words as follow:

```java
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
```

The results are summarized in the following table:


|  | [kòːɽaː]<br>'horse' | [kóːɽaː]<br>'leper' | [koːɽaː]<br>'whip' |  |
| --- | --- | --- | --- | --- |
| [gʰoːɽɑː]<br>'horse' | __14.642873115971197__<br>__14.642873115971197__ | 14.616038284359766<br>14.616038284359766 | 14.629455700165481<br>14.629455700165481 | Glob. Sim. Score <br> Corr. Glob. Sim. Score |
| [koːɽʰi]<br>'leper' | 14.9559461514379<br>14.9559461514379 | __14.98278098304933__<br>__14.98278098304933__ | 14.978308511114092<br>14.978308511114092 | Glob. Sim. Score <br> Corr. Glob. Sim. Score |
| [koːɽɑː]<br>'whip' | 14.624983228230242<br>14.624983228230242 | 14.63392817210072<br>14.63392817210072 | __14.647345587906436__<br>__14.647345587906436__ | Glob. Sim. Score <br> Corr. Glob. Sim. Score |


As it appears, with this configuration FAAL is able to differentiate between the three Punjabi words, and the best matches it identifies for each of the Hindi words (in bold in the table above) are indeed the correct ones.

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Current Version

2.0.0 - 15 October 2018

## License

This project is licensed under the Apache License Version 2.0 - see the [LICENSE.md](LICENSE.md) [NOTICE.md](NOTICE.md) files for details

## Acknowledgments

I would like to thanks the anonymous reviewers of my article, whose comments greately helped me in improving FAAL.

