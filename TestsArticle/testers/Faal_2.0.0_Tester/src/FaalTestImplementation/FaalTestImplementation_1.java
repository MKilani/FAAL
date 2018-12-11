package FaalTestImplementation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import faal.*;

public class FaalTestImplementation_1 {

	public static void main(String[] args) throws Exception {

		// ================
		// --- settings

		// location of the file to analyse (the files are provided in the folder: TestsArticle/Datasets/Inputs )
		
		String nameDataset = "path/to/data/dataset/1_corpus_faal_input_no_alignments.txt"; 
				
		// location where the results of the alignment should be saved. The results obtained in the tests are provided in the folder: TestsArticle/Datasets/Results
		
		String nameResult = "path/to/folder/with/results/1_faal_results.txt"; 
				
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

				List<Alignment> listResultsAlignments = new ArrayList<>();

				listResultsAlignments = FAAL.align(word1, word2, false, 3);

				// -- reorganize results so that they are ready to be analysed by the
				// AnalyserFaal.java script

				String pairProvisional = "";
				String resultWord1_Provisional = "";
				String resultWord2_Provisional = "";

				pairProvisional = listResultsAlignments.get(0).getWord1_WithoutDiacritics() + " == "
						+ listResultsAlignments.get(0).getWord2_WithoutDiacritics();
				resultWord1_Provisional = (String) listResultsAlignments.get(0).getWord1_WithDiacritics();
				resultWord2_Provisional = (String) listResultsAlignments.get(0).getWord2_WithDiacritics();

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
				// -- print results

				System.out.println(pairProvisional);
				System.out.println(resultWord1_Provisional);
				System.out.println(resultWord2_Provisional);

				// -- write results to file

				writer.println(items[i - 1]);
				writer.println(resultWord1_Provisional);
				writer.println(resultWord2_Provisional);

			}

			writer.close();

		} catch (IOException e) {

			System.out.println("error!");

		}

	}
}
