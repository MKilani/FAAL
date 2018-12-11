package resultsAnalyser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AnalyserFaal {

public static void main(String[] args) throws Exception {
	
	//*************************
	// Settings

	String inputFileResultAnalysis = "2_faal_results.txt"; //file with the results obtained by FAAL - those obtained in the tests are provided in the folder: TestsArticle/Datasets/Results
	String benchmarkFile = "2_corpus_faal_benchmark.txt"; //benchmark file with the correct alignments for FAAL - provided in the folder: TestsArticle/Datasets/Benchmarks
	String suffixMethod_Results = "faal_2.txt"; //suffix indicating the method to use in the names of the output files (suggested: faal + _ + nrCorpus)
	
	//folder containing the files with the results of the aligning methods
	//String folderInput = "/path/to/input/file";
	String folderInput = "/Users/iome/Desktop/FAAL/TestsArticle/Datasets/Results/";
	
	//folder containing the files with the benchmarks
	//String folderBenchmark = "/path/to/benchmark/file";
	String folderBenchmark = "/Users/iome/Desktop/FAAL/TestsArticle/Datasets/Benchmarks/";
	
	//folder where the results of the current analysis will be saved - the results obtained in the tests are provided in the folder: TestsArticle/Datasets/AssessmentResults
	//String folderOutput = "/path/to/output/folder/";
	String folderOutput = "/Users/iome/Desktop/FAAL/TestsArticle/Datasets/AssessmentResults/";
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
		List<String> resultLines = new ArrayList <>();
		
		String[] benchmark = data2.split("\n");
		
		//-- prepare data
		
		int f = 0;
		
		for (int i = 1; i < lines.length; i = i+3){
			
			lines[i] = lines[i].replace("0", "-");
			
			lines[i+1] = lines[i+1].replace("0", "-");
			
			lines[i] = lines[i]+"§";
			lines[i+1] = lines[i+1]+"§";
			
			lines[i] = lines[i].replace(";§", "");
			lines[i+1] = lines[i+1].replace(";§", "");
			

			lines[i] = lines[i].replace("§", "");
			lines[i+1] = lines[i+1].replace("§", "");
			
			resultLines.add(lines[i-1]);
			resultLines.add(lines[i]);
			resultLines.add(lines[i+1]);
			
			f++;
			
		}
		
		//-- analyse data
		
		int totalItems = 0;
		int goodMatches = 0;
		int badMatches = 0;
		
		List<String> badMatchesList = new ArrayList <>();
		List<String> goodMatchesList = new ArrayList <>();
		
		int totalPairs = 0;
		int goodPairs = 0;
		
		int totalPairsCons = 0;
		int goodPairsCons = 0;
		
		
		for (int i = 1; i < resultLines.size()-1; i = i + 3){
			
			//-- count good matches
			
			if(resultLines.get(i).equals(benchmark[i]) && resultLines.get(i+1).equals(benchmark[i+1])){
				
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
