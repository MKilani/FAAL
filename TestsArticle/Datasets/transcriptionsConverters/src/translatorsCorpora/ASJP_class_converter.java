package translatorsCorpora;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ASJP_class_converter {
	
	public static void main(String[] args) throws Exception {
		
		//================================
		//--- SETTINGS
		//================================
		
		// Select which fields to generate: 1 = generate corpus to test; 2 = generate benchmark
		int option = 2;
		
		//location of the files and output
		
		//Location of the file encoding the correspondencies IPA - ASJP - here in the classesFiles folder in the root of the project
		String locationClassesFile = "/classesFiles/";
		
		//Location of the input files with the corpus to test
		String locationInputFiles = "/path/to/folder/";
		
		//Location of the benchmark files
		String locationBenchmarks = "/path/to/folder/";
				
		
		//Name of the file encoding the correspondences IPA - ASJP
		
		String nameClassesFile = "ASJP_classes";
			
			String sourceFile = "";
			String outputFile = "";
		
			//name of the faal input file to use as a model and of the ASJP-transcribed file for Jager's method as output - corpus to test
			if (option == 1) {
			sourceFile = "2_corpus_faal_input_no_alignments";
			outputFile = "2_corpus_jager_input_no_alignments";
			}
			
			//name of the faal input file to use as a model and of the ASJP-transcribed file Jager's method as output - benchmark
			
			if (option == 2) {
			sourceFile = "2_corpus_faal_benchmark";
			outputFile = "2_corpus_jager_benchmark";
			}

			//================================
			//--- END SETTINGS
			//================================
			
			
			
		
			
		String data_1 = "";
		
		BufferedReader br = new BufferedReader(new FileReader(locationClassesFile + nameClassesFile + ".txt"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    data_1 = sb.toString();
		} finally {
		    br.close();
		}
		
		String[] lines = data_1.split("\n");
		
		List<String> Class_ASJP = new ArrayList <>();
		List<String> Phone_ASJP = new ArrayList <>();
		
		for (int i = 0; i < lines.length; i++){
			
			String[] items = lines[i].split(";:;");
					
					String[] phones = items[1].split(";");
			
		
		
		for (int n = 0; n < phones.length; n++){
			
			Class_ASJP.add(items[0]);
			Phone_ASJP.add(phones[n]);
		}
			
		}
		

			
			String data_2 = "";
			
			BufferedReader br2 = null;
			
			if (option == 1) {
				br2 = new BufferedReader(new FileReader(locationInputFiles + sourceFile + ".txt"));
			}
			if (option == 2) {
				br2 = new BufferedReader(new FileReader(locationBenchmarks + sourceFile + ".txt"));
			}
			try {
			    StringBuilder sb = new StringBuilder();
			    String line = br2.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br2.readLine();
			    }
			    data_2 = sb.toString();
			} finally {
			    br2.close();
			}
		
			String[] lines_corpus = data_2.split("\n");
			
			
		for (int i = 1; i < lines_corpus.length; i = i +3){
			
			
			lines_corpus[i] = lines_corpus[i].replace("ː", "");
			lines_corpus[i] = lines_corpus[i].replace("~", "");
			lines_corpus[i] = lines_corpus[i].replace("̪", "");
			lines_corpus[i] = lines_corpus[i].replace("̥", "");
			lines_corpus[i] = lines_corpus[i].replace(":", "");
			lines_corpus[i] = lines_corpus[i].replace("ʰ", "");
			lines_corpus[i] = lines_corpus[i].replace("ʲ", "");
			lines_corpus[i] = lines_corpus[i].replace("ʷ", "");
			lines_corpus[i] = lines_corpus[i].replace("ˠ", "");
			lines_corpus[i] = lines_corpus[i].replace("ʼ", "");
			lines_corpus[i] = lines_corpus[i].replace("ˁ", "");
			lines_corpus[i] = lines_corpus[i].replace("˞", "");
			lines_corpus[i] = lines_corpus[i].replace("ˤ", "");
			
			
			

			
			lines_corpus[i+1] = lines_corpus[i+1].replace("ː", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace("~", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace("̪", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace("̥", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace(":", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace("ʰ", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace("ʲ", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace("ʷ", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace("ˠ", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace("ʼ", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace("ˁ", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace("˞", "");
			lines_corpus[i+1] = lines_corpus[i+1].replace("ˤ", "");
			
			
			
			String[] word_A = lines_corpus[i].split("(?!^)");
			String[] word_B = lines_corpus[i+1].split("(?!^)");
			
			for (int n = 0; n < word_A.length; n++){
				
				//diftonghs keeps only the core
				
				if (word_A[n].equals("͜") || word_A[n].equals("̯")){
					
					word_A[n] = "";
					word_A[n-1] = "";
					
				}
				
				//coarticulated: keeps only the first
				if (word_A[n].equals("͡")){
					
					word_A[n] = "";
					word_A[n+1] = "";
					
				}
				
			}
			
			for (int n = 0; n < word_B.length; n++){
				
				if (word_B[n].equals("͜") || word_B[n].equals("̯")){
					
					word_B[n] = "";
					word_B[n-1] = "";
					
				}
				if (word_B[n].equals("͡")){
					
					word_B[n] = "";
					word_B[n+1] = "";
					
				}
				
			}		
			
			for (int n = 0; n < word_A.length; n++){
				Boolean match = false;
				for (int m = 0; m < Phone_ASJP.size(); m++){
				
					if (word_A[n].equals(Phone_ASJP.get(m))){
						word_A[n] = Class_ASJP.get(m);
					}	
				}	
			}
			
			for (int n = 0; n < word_B.length; n++){
				Boolean match = false;
				for (int m = 0; m < Phone_ASJP.size(); m++){
				
					if (word_B[n].equals(Phone_ASJP.get(m))){
						word_B[n] = Class_ASJP.get(m);
					}	
				}
			}
			
			lines_corpus[i] = "";
			
			for (int n = 0; n < word_A.length; n++){
				
				lines_corpus[i] = lines_corpus[i] + word_A[n];
				
			}
			
			lines_corpus[i+1] = "";
			
			for (int n = 0; n < word_B.length; n++){
				
				lines_corpus[i+1] = lines_corpus[i+1] + word_B[n];
				
			}
			
			
			

		}
		
		System.out.print("end1");
		//print file
		
		try{
			
			PrintWriter writer = null;
			
			if (option == 1) {
				writer = new PrintWriter(locationInputFiles + outputFile + ".txt", "UTF-8");
			}
			if (option == 2) {
				writer = new PrintWriter(locationBenchmarks + outputFile + ".txt", "UTF-8");
			}

			
			
		    for (int t = 0; t < lines_corpus.length; t++){
		    
		    writer.println(lines_corpus[t]);
	    
		    }
	
		
	

    writer.close();
} catch (IOException e) {
   // do something


}
		System.out.print("end");
		
	}

}
