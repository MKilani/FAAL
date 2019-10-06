package faal;

import java.util.ArrayList;
import java.util.List;

public class ConfigIPA {
	
	String[] arrayConfigIPA = new String[6];
	List<String> configIPA = new ArrayList<String>();
	
	/**
	 * Stores the path of the file <i>phon_features.txt</i>.
	 * 
	 * @param pathPhonologicalFeatures (String).
	 * <p>
	 * <p>
	 * Default: "config/phon_features.txt"
	 */
	public void pathPhonologicalFeatures(String pathPhonologicalFeatures) {
		
		arrayConfigIPA[0] = pathPhonologicalFeatures;
	   }
	
	/**
	 * Stores the path of the file <i>phon_diacritics.txt</i>.
	 * 
	 * @param pathPhoneticDiacritics (String).
	 * <p>
	 * <p>
	 * Default: "config/phon_diacritics.txt"
	 */
	public void pathPhoneticDiacritics(String pathPhoneticDiacritics) {
		
		arrayConfigIPA[1] = pathPhoneticDiacritics;
	   }	
	
	
	/**
	 * Stores the path of the file <i>features_diphthongs.txt</i>.
	 * 
	 * @param pathFeaturesDiphthongs (String).
	 * <p>
	 * <p>
	 * Default: "config/features_diphthongs.txt"
	 */
	public void pathFeaturesDiphthongs(String pathFeaturesDiphthongs) {
		
		arrayConfigIPA[2] = pathFeaturesDiphthongs;
	   }
	
	/**
	 * Stores the path of the file <i>features_coarticulation.txt</i>.
	 * 
	 * @param pathFeaturesCoarticulation (String).
	 * <p>
	 * <p>
	 * Default: "config/features_coarticulation.txt"
	 */
	public void pathFeaturesCoarticulation(String pathFeaturesCoarticulation) {
		
		arrayConfigIPA[3] = pathFeaturesCoarticulation;
	   }
	
	
	/**
	 * Stores the path of the file <i>phon_categories.txt</i>.
	 * 
	 * @param pathPhoneClass (String).
	 * <p>
	 * <p>
	 * Default: "config/phon_categories.txt"
	 */
	public void pathPhonCategories(String pathPhonCategories) {
		
		arrayConfigIPA[4] = pathPhonCategories;
	   }
	
	/**
	 * Stores the path of the file <i>saliences_to_use_matches_phon_categories.txt</i>.
	 * 
	 * @param pathPhoneClass (String).
	 * <p>
	 * <p>
	 * Default: "config/saliences_to_use_matches_phon_categories.txt"
	 */
	public void saliencesMatchesPhonCategories(String saliencesMatchesPhonCategories) {
		
		arrayConfigIPA[5] = saliencesMatchesPhonCategories;
	   }
	
	
	
	/**
	 * Returns the a list with the parameters for the ConfigIPA parameters.
	 */
	public List<String> getConfigIPA(){
		
		if (arrayConfigIPA[0] == null) {
			// location of the file with phonological features:
			configIPA.add("config/phon_features.txt");
		}else {
			configIPA.add(arrayConfigIPA[0]);
		}
		
		if (arrayConfigIPA[1] == null) {
			// location of the file with phonetic diacritics:
			configIPA.add("config/phon_diacritics.txt");
		}else {
			configIPA.add(arrayConfigIPA[1]);
		}
		
		if (arrayConfigIPA[2] == null) {
			// location of the file with configs for the features modified by secondary elements of diphthongs
			configIPA.add("config/features_diphthongs.txt");
		}else {
			configIPA.add(arrayConfigIPA[2]);
		}
		
		if (arrayConfigIPA[3] == null) {
			// location of the file with configs for the features modified by secondary elements of a coarticulated group
			configIPA.add("config/features_coarticulation.txt");
		}else {
			configIPA.add(arrayConfigIPA[3]);
		}
		
		if (arrayConfigIPA[4] == null) {
			// location of the config file with parameteres for phonological categories
			configIPA.add("config/phon_categories.txt");
		}else {
			configIPA.add(arrayConfigIPA[4]);
		}
		
		if (arrayConfigIPA[5] == null) {
			// location of the config file with parameteres matches of phonological categories
			configIPA.add("config/saliences_to_use_matches_phon_categories.txt");
		}else {
			configIPA.add(arrayConfigIPA[5]);
		}
		
	    return configIPA;
	}

}



