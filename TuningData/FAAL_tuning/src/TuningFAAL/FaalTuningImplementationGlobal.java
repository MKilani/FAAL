package TuningFAAL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import faal.*;

public class FaalTuningImplementationGlobal {

	public static void main(String[] args) throws Exception {

		// ================
		// --- settings

		// location of the file to analyse (the files are provided in the folder: TestsArticle/Datasets/Inputs )
		
		String nameDataset = "path/to/data/dataset/1 or 2_corpus_faal_input_no_alignments.txt";
				
		// location where the results of the alignment should be saved. The results obtained in the tests are provided in the folder: TestsArticle/Datasets/Results
		
		String nameResult = "path/to/folder/with/results/1 or 2_faal_results.txt"; 
				
		// ===============

		String data1 = "";

		// Read dataset and divide into lines

		BufferedReader br = new BufferedReader(new FileReader(nameDataset));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			data1 = sb.toString();
		} finally {
			br.close();
		}

		String[] items = data1.split("\n");

		// -----Run FAAl

		try {

			// -- create writer
			PrintWriter writer = new PrintWriter(nameResult, "UTF-8");

			for (int i = 1; i < items.length; i = i + 3) {

				String word1 = items[i];
				String word2 = items[i + 1];

				// -- set and run FAAL

				List<Alignment> preliminaryListResultsAlignments = new ArrayList<>();

				preliminaryListResultsAlignments = FAAL.align(word1, word2, false, 3);
				
				List<Alignment> listResultsAlignments = new ArrayList<>();
				
				listResultsAlignments.add(preliminaryListResultsAlignments.get(0));
				
				double globalScoreFirstAlignment = preliminaryListResultsAlignments.get(0).getGlobalSimilarityScore();
				
				if (preliminaryListResultsAlignments.size() > 1) {
					double globalScoreSecondAlignment = preliminaryListResultsAlignments.get(1).getGlobalSimilarityScore();
					
					if (globalScoreFirstAlignment == globalScoreSecondAlignment) {
						listResultsAlignments.add(preliminaryListResultsAlignments.get(1));
					}
				}
				

				// -- reorganize results so that they are ready to be analysed by the
				// AnalyserFaal.java script
				
				String resultWord1 = "";
				String resultWord2 = "";
				String resultPair = "";

				for (int z = 0; z < listResultsAlignments.size(); z++) {
				
					String divider = "";
					
					if (z > 0) {
						divider = "//";
					}
					
					String pairProvisional = "";
					String resultWord1_Provisional = "";
					String resultWord2_Provisional = "";
	
					pairProvisional = listResultsAlignments.get(z).getWord1_WithoutDiacritics() + " == "
							+ listResultsAlignments.get(z).getWord2_WithoutDiacritics();
					resultWord1_Provisional = (String) listResultsAlignments.get(z).getWord1_WithDiacritics();
					resultWord2_Provisional = (String) listResultsAlignments.get(z).getWord2_WithDiacritics();
	
					resultWord1_Provisional = resultWord1_Provisional.replace("┊", "");
					resultWord2_Provisional = resultWord2_Provisional.replace("┊", "");
	
					resultWord1_Provisional = resultWord1_Provisional.replace("￤", "");
					resultWord2_Provisional = resultWord2_Provisional.replace("￤", "");
	
					resultWord1_Provisional = resultWord1_Provisional.replace("	￤", "");
					resultWord2_Provisional = resultWord2_Provisional.replace("	￤", "");
	
					resultWord1_Provisional = resultWord1_Provisional.replace("͜	", "͜");
					resultWord2_Provisional = resultWord2_Provisional.replace("͜	", "͜");
	
					resultWord1_Provisional = resultWord1_Provisional.replace("	", ";");
					resultWord2_Provisional = resultWord2_Provisional.replace("	", ";");
	
					resultWord1_Provisional = resultWord1_Provisional.replace(";;", ";￤;");
					resultWord2_Provisional = resultWord2_Provisional.replace(";;", ";￤;");
	
					if (Character.toString(resultWord1_Provisional.charAt(resultWord1_Provisional.length() - 1)).equals(";")
							&& Character.toString(resultWord2_Provisional.charAt(resultWord2_Provisional.length() - 1)).equals("0")){
						resultWord1_Provisional = resultWord1_Provisional + "￤";
					}
					
					if (Character.toString(resultWord2_Provisional.charAt(resultWord2_Provisional.length() - 1)).equals(";")
							&& Character.toString(resultWord1_Provisional.charAt(resultWord1_Provisional.length() - 1)).equals("0")){
						resultWord2_Provisional = resultWord2_Provisional + "￤";
					}
					
					resultPair = resultPair + divider + pairProvisional;
					resultWord1 = resultWord1 + divider + resultWord1_Provisional;
					resultWord2 = resultWord2 + divider + resultWord2_Provisional;
	
					
				}
				
				// -- print results
				
				System.out.println(resultPair);
				System.out.println(resultWord1);
				System.out.println(resultWord2);

				// -- write results to file

				writer.println(items[i - 1]);
				writer.println(resultWord1);
				writer.println(resultWord2);
			}

			writer.close();

		} catch (IOException e) {

			System.out.println("error!");

		}

	}
}
