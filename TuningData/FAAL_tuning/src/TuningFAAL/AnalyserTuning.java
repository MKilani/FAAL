package TuningFAAL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AnalyserTuning {

public static void main(String[] args) throws Exception {
	
	//*************************
	// Settings

	String inputFileResultAnalysis = "BDPA_Covington_faal_results.txt"; //file with the results obtained by FAAL - those obtained in the tests are provided in the folder: TestsArticle/Datasets/Results
	String benchmarkFile = "BDPA_Covington_faal_benchmark.txt"; //benchmark file with the correct alignments for FAAL - provided in the folder: TestsArticle/Datasets/Benchmarks
	String suffixMethod_Results = "_Covington_Corrected_Score_faal_results.txt"; //suffix indicating the method to use in the names of the output files (suggested: faal + _ + nrCorpus)
	
	//folder containing the files with the results of the aligning methods
	String folderInput = "/Users/iome/Desktop/FAAL/TuningDatasets/Results/";
	
	//folder containing the files with the benchmarks
	String folderBenchmark = "/Users/iome/Desktop/FAAL/TuningDatasets/Benchmarks/";
	
	//folder where the results of the current analysis will be saved - the results obtained in the tests are provided in the folder: TestsArticle/Datasets/AssessmentResults
	String folderOutput = "/Users/iome/Desktop/FAAL/TuningDatasets/AssessmentResults/";
	
	//**************************
	
	String data1 = "";
	String data2 = "";
			

		//-- read files
	
		BufferedReader br = new BufferedReader(new FileReader(folderInput + inputFileResultAnalysis));
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
		
		BufferedReader br1 = new BufferedReader(new FileReader(folderBenchmark + benchmarkFile));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br1.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br1.readLine();
		    }
		    data2 = sb.toString();
		} finally {
		    br1.close();
		}
		
		String[] lines = data1.split("\n");
		List<List<String>> resultLines = new ArrayList <>();
		
		List<List<String>> listAlignments = new ArrayList <>();
		
		
		
		
		for (int i = 0; i < lines.length; i++) {
			
			List<String> alignments = new ArrayList <>();
			
			String[] itemsLines = lines[i].split("//");
			
			if (itemsLines.length == 1) {
				alignments.add(itemsLines[0]);
			}else {
				alignments.add(itemsLines[0]);
				alignments.add(itemsLines[1]);
			}
			
			listAlignments.add(alignments);
			
			
		}
		
		String[] benchmark = data2.split("\n");
		
		//-- prepare data
		
		int f = 0;
		
		for (int i = 1; i < listAlignments.size(); i = i+3){
			
			List<String> resultAlignment1 = new ArrayList <>();
			List<String> resultAlignment2 = new ArrayList <>();
			List<String> resultAlignment3 = new ArrayList <>();
			
			for (int z = 0; z < listAlignments.get(i).size(); z++) {
				
				String alignemntA = listAlignments.get(i).get(z);
				String alignemntB = listAlignments.get(i+1).get(z);
			
				
				alignemntA = alignemntA.replace("0", "-");
				
				alignemntB = alignemntB.replace("0", "-");
				
				alignemntA = alignemntA+"§";
				alignemntB = alignemntB+"§";
				
				if (alignemntA.contains(";￤§") && alignemntB.contains(";-§")) {
					alignemntA = alignemntA.replace(";￤§", "§");
					alignemntB = alignemntB.replace(";-§", "§");
				}
				
				if (alignemntA.contains(";-§") && alignemntB.contains(";￤§")) {
					alignemntA = alignemntA.replace(";-§", "§");
					alignemntB = alignemntB.replace(";￤§", "§");
				}
				
				
				alignemntA = alignemntA.replace(";§", "");
				alignemntB = alignemntB.replace(";§", "");
	
				alignemntA = alignemntA.replace("§", "");
				alignemntB = alignemntB.replace("§", "");

				listAlignments.get(i).set(z, alignemntA);
				listAlignments.get(i+1).set(z, alignemntB);
				
				resultAlignment1.add(listAlignments.get(i-1).get(0));
				resultAlignment2.add(listAlignments.get(i).get(z));
				resultAlignment3.add(listAlignments.get(i+1).get(z));
				
				if (i == 961) {
					int n = 0;
					n++;
				}
				
				f++;
			}

			resultLines.add(resultAlignment1);
			resultLines.add(resultAlignment2);
			resultLines.add(resultAlignment3);
			
		}
		
		//-- analyse data
		
		int totalItems = 0;
		int goodMatches = 0;
		int badMatches = 0;
		
		List<String> badMatchesList = new ArrayList <>();
		List<String> goodMatchesList = new ArrayList <>();
		
		
		for (int i = 1; i < resultLines.size()-1; i = i + 3){
			
			if (!(resultLines.get(i-1).get(0).equals(benchmark[i-1]))) {
				int g =0;
				g++;
			}
			
			//-- count good matches
			
			boolean goodMatch = false;
			
			for (int z = 0; z < resultLines.get(i).size(); z++) {
				
				String[] lettersResultLineA = resultLines.get(i).get(z).split(";");
				String[] lettersResultLineB = resultLines.get(i+1).get(z).split(";");
				String[] lettersBenchmarkA = benchmark[i].split(";");
				String[] lettersBenchmarkB = benchmark[i+1].split(";");
				
				for (int q = 0; q < lettersResultLineA.length; q++) {
					if (lettersResultLineA[q].equals("-") || lettersResultLineB[q].equals("-")) {
						lettersResultLineA[q] = "0";
						lettersResultLineB[q] = "0";
					}
				}
				
				for (int q = 0; q < lettersBenchmarkA.length; q++) {
					if (lettersBenchmarkA[q].equals("-") || lettersBenchmarkB[q].equals("-")) {
						lettersBenchmarkA[q] = "0";
						lettersBenchmarkB[q] = "0";
					}
				}
				
				
				String resultLineCleanedA = "";
				String resultLineCleanedB = "";
				String benchmarkCleanedA = "";
				String benchmarkCleanedB = "";
				
				resultLineCleanedA = String.join(";",lettersResultLineA);
				resultLineCleanedB = String.join(";",lettersResultLineB);
				
				benchmarkCleanedA = String.join(";",lettersBenchmarkA);
				benchmarkCleanedB = String.join(";",lettersBenchmarkB);
				
				
				
				if(resultLineCleanedA.equals(benchmarkCleanedA) && resultLineCleanedB.equals(benchmarkCleanedB)){
					goodMatch = true;
				}
				
			}
			
			
			if(goodMatch == true){
				
				goodMatches++;
				
				goodMatchesList.add(benchmark[i-1]);
				goodMatchesList.add(benchmark[i]);
				goodMatchesList.add(benchmark[i+1]);
				goodMatchesList.add("===  " + resultLines.get(i));
				goodMatchesList.add("===  " + resultLines.get(i+1));
				
			}else{ //-- count bad matches
				
				badMatches++;
				
				badMatchesList.add(benchmark[i-1]);
				badMatchesList.add(benchmark[i]);
				badMatchesList.add(benchmark[i+1]);
				badMatchesList.add("===  " + resultLines.get(i));
				badMatchesList.add("===  " + resultLines.get(i+1));
				
				
			}

			totalItems++;
		}
		
		//-- calculate percentage good matches
		
		double percentageGood = (double)goodMatches/(double)totalItems;
		
		//-- print results
		
		System.out.println("total items: " + totalItems);
		System.out.println("good matches: " + goodMatches);
		System.out.println("bad matches: " + badMatches);
		System.out.println("good matches %: " + percentageGood);
		
		//-- write results
		
		try{
		    PrintWriter writer = new PrintWriter(folderOutput + "stats_" + suffixMethod_Results, "UTF-8");
	    	
		    
		    writer.println("total items: " + totalItems);
		    writer.println("good matches: " + goodMatches);
		    writer.println("bad matches: " + badMatches);
		    writer.println("good matches %: " + percentageGood);

		    writer.close();
			} catch (IOException e) {
				System.out.println("error!");
			}
		
		try{
		    PrintWriter writer = new PrintWriter(folderOutput + "good_matches_" + suffixMethod_Results, "UTF-8");
	    	
		    
		    for (int i = 0; i < goodMatchesList.size(); i++){
		    	
		    	writer.println(goodMatchesList.get(i));
		    }

		    writer.close();
			} catch (IOException e) {
				System.out.println("error!");
			}
		
		try{
		    PrintWriter writer = new PrintWriter(folderOutput + "bad_matches_" + suffixMethod_Results, "UTF-8");
	    	
		    
		    for (int i = 0; i < badMatchesList.size(); i++){
		    	
		    	writer.println(badMatchesList.get(i));
		    }

		    writer.close();
			} catch (IOException e) {
				System.out.println("error!");
			}
		
		
		
}
	
}
