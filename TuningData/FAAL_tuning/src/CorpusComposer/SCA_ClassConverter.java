package CorpusComposer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class SCA_ClassConverter {
		
		public static void main(String[] args) throws Exception {
			int count_wrong_words = 0;
			String name = "SCA_classes";
			String data_1 = "";
			
			BufferedReader br = new BufferedReader(new FileReader("fileClasses/" + name + ".txt"));
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
			
			
				
				String name2 = "/Users/iome/Desktop/FAAL/varia/cleaned_1";
				String data_2 = "";
				
				BufferedReader br2 = new BufferedReader(new FileReader(name2 + ".txt"));
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
				
				
				String[] word_A = lines_corpus[i].split(";");
				String[] word_B = lines_corpus[i+1].split(";");
				
				/*
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
					
				}	*/	
				
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
					
					lines_corpus[i] = lines_corpus[i] + word_A[n]+ ";";
					
				}
				
				lines_corpus[i+1] = "";
				
				for (int n = 0; n < word_B.length; n++){
					
					lines_corpus[i+1] = lines_corpus[i+1] + word_B[n]+ ";";
					
				}
				
				lines_corpus[i] = lines_corpus[i].replace(";;", ";");
				lines_corpus[i+1] = lines_corpus[i+1].replace(";;", ";");
				
				lines_corpus[i] = "§" + lines_corpus[i] + "§";
				lines_corpus[i+1] = "§" + lines_corpus[i+1] + "§";
				
				lines_corpus[i] = lines_corpus[i].replace("§;", "");
				lines_corpus[i+1] = lines_corpus[i+1].replace("§;", "");
				
				lines_corpus[i] = lines_corpus[i].replace(";§", "");
				lines_corpus[i+1] = lines_corpus[i+1].replace(";§", "");
				
				lines_corpus[i] = lines_corpus[i].replace("§", "");
				lines_corpus[i+1] = lines_corpus[i+1].replace("§", "");
				
				lines_corpus[i] = lines_corpus[i].replace("ˈ", "");
				lines_corpus[i+1] = lines_corpus[i+1].replace("ˈ", "");
				
				
				
				//----------------------
				
				// 
				String list_vows = "iyɨʉɯuɪʏʊeøɘɵɤoɛœəɞʌɔæɶaɑɒɐɜ";
				
				String[] word_A1 = lines_corpus[i].split(";");
				String[] word_B1 = lines_corpus[i+1].split(";");
				
				String[] vows = list_vows.split("(?!^)");
				
				/*for (int n = 1; n < word_A1.length; n++){
					if (word_A1[n].equals("_") || word_B1[n].equals("_")){
						word_A1[n] = "";	
						word_B1[n] = "";	
					}
					
					
				}
				*/
				
				if ( word_A1[word_A1.length-1].equals("￤") && word_B1[word_B1.length-1].equals("￤")) {
					word_A1[word_A1.length-1] = "";	
					word_B1[word_B1.length-1] = "";	
				}
				
				if ( word_A1[word_A1.length-1].equals("-") && word_B1[word_B1.length-1].equals("￤")) {
					word_A1[word_A1.length-1] = "";	
					word_B1[word_B1.length-1] = "";	
				}
				
				if ( word_A1[word_A1.length-1].equals("￤") && word_B1[word_B1.length-1].equals("-")) {
					word_A1[word_A1.length-1] = "";	
					word_B1[word_B1.length-1] = "";	
				}
				
				
				for (int n = 1; n < word_A1.length; n++){
					for (int m = 0; m < vows.length; m++){
					
						if ((word_A1[n].equals("w") || word_A1[n].equals("j")) && word_B1[n].equals("-") && word_A1[n-1].contains(vows[m]) == true){
							word_A1[n-1] = word_A1[n-1] + word_A1[n] + "̯";
							word_A1[n] = "";	
							word_B1[n] = "";	
						}		
					}	
				}
				
				for (int n = 1; n < word_B1.length; n++){
					for (int m = 0; m < vows.length; m++){
					
						if ((word_B1[n].equals("w") || word_B1[n].equals("j")) && word_A1[n].equals("-") && word_B1[n-1].contains(vows[m]) == true){
							word_B1[n-1] = word_B1[n-1] + word_B1[n] + "̯";
							word_B1[n] = "";
							word_A1[n] = "";	
						}		
					}	
				}
				
				for (int n = 0; n < word_A1.length-1; n++){
					for (int m = 0; m < vows.length; m++){
					
						if ((word_A1[n].equals("w") || word_A1[n].equals("j")) && word_B1[n].equals("-") && word_A1[n+1].contains(vows[m]) == true){
							word_A1[n+1] = word_A1[n] + "͜" + word_A1[n+1];
							word_A1[n] = "";
							word_B1[n] = "";	
						}		
					}	
				}
				
				for (int n = 0; n < word_B1.length-1; n++){
					for (int m = 0; m < vows.length; m++){
					
						if ((word_B1[n].equals("w") || word_B1[n].equals("j")) && word_A1[n].equals("-") && word_B1[n+1].contains(vows[m]) == true){
							word_B1[n+1] = word_B1[n] + "͜" + word_B1[n+1];
							word_B1[n] = "";
							word_A1[n] = "";	
						}		
					}	
				}


				
//=====
				
				if (word_A1.length > 3) {
					
					for (int n = 2; n < word_A1.length; n++){
						for (int m = 0; m < vows.length; m++){
							for (int o = 0; o < vows.length; o++){
								for (int p = 0; p < vows.length; p++){							
								
									if (((word_A1[n].equals(vows[m]) || word_A1[n].equals("w") || word_A1[n].equals("j")) && word_A1[n-2].equals(vows[p]) ) && word_A1[n-1].equals(vows[o]) && (( word_A1[n-2].equals(vows[p]) || word_A1[n-2].equals("w") || word_A1[n-2].equals("j") ))&& word_A1[n].equals(vows[p])) {
									
										word_A1[n-2] = word_A1[n-2] + "͜" + word_A1[n-1] + word_A1[n] + "̯";
										word_A1[n-1] = "";	
										word_A1[n] = "";	
									}								
								}			
							}				
						}
					}	
				}
				
				
				
				if (word_B1.length > 3) {
					
					for (int n = 2; n < word_B1.length; n++){
						for (int m = 0; m < vows.length; m++){
							for (int o = 0; o < vows.length; o++){
								for (int p = 0; p < vows.length; p++){							
								
									if (((word_B1[n].equals(vows[m]) || word_B1[n].equals("w") || word_B1[n].equals("j") ) && word_B1[n-2].equals(vows[p])) && word_B1[n-1].equals(vows[o]) && ((word_B1[n-2].equals(vows[p]) || word_B1[n-2].equals("w") || word_B1[n-2].equals("j") ) && word_B1[n].equals(vows[p])) ) {
									
										word_B1[n-2] = word_B1[n-2] + "͜" + word_B1[n-1] + word_B1[n] + "̯";
										word_B1[n-1] = "";	
										word_B1[n] = "";	
									}								
								}			
							}				
						}
					}	
				}
				

				
				
				if (word_A1.length > 2) {
					
					for (int n = 1; n < word_A1.length; n++){
						for (int m = 0; m < vows.length; m++){
							for (int o = 0; o < vows.length; o++){
								
								if (word_A1[n-1].equals(vows[m]) && word_A1[n].equals(vows[o]) && 
										word_B1[n].equals("-")) {
									
									word_A1[n-1] = word_A1[n-1] + word_A1[n] + "̯";
									word_A1[n] = "";	
									word_B1[n] = "";	
																		
								}			
							}				
						}
					}	
				}
				
				
				if (word_B1.length > 2) {
					
					for (int n = 1; n < word_B1.length; n++){
						for (int m = 0; m < vows.length; m++){
							for (int o = 0; o < vows.length; o++){
								
								if (word_B1[n-1].equals(vows[m]) && word_B1[n].equals(vows[o]) && 
										word_A1[n].equals("-")) {
									
									word_B1[n-1] = word_B1[n-1] + word_B1[n] + "̯";
									word_B1[n] = "";	
									word_A1[n] = "";	
																	
								}			
							}				
						}
					}	
				}
				
				//=====
				int y = 0;
				
				
				
				
				lines_corpus[i] = "";
				
				for (int n = 0; n < word_A1.length; n++){
					
					lines_corpus[i] = lines_corpus[i]  + word_A1[n]+ ";";
					
				}
						
				lines_corpus[i+1] = "";
				
				for (int n = 0; n < word_B1.length; n++){
					
					lines_corpus[i+1] = lines_corpus[i+1]  + word_B1[n]+ ";";
					
				}

				lines_corpus[i] = lines_corpus[i].replace(";;", ";");
				lines_corpus[i+1] = lines_corpus[i+1].replace(";;", ";");
				
				lines_corpus[i] = "§" + lines_corpus[i] + "§";
				lines_corpus[i+1] = "§" + lines_corpus[i+1] + "§";
				
				lines_corpus[i] = lines_corpus[i].replace("§;", "");
				lines_corpus[i+1] = lines_corpus[i+1].replace("§;", "");
				
				lines_corpus[i] = lines_corpus[i].replace(";§", "");
				lines_corpus[i+1] = lines_corpus[i+1].replace(";§", "");
				
				lines_corpus[i] = lines_corpus[i].replace("§", "");
				lines_corpus[i+1] = lines_corpus[i+1].replace("§", "");
				
				lines_corpus[i] = lines_corpus[i].replace(";;", ";");
				lines_corpus[i+1] = lines_corpus[i+1].replace(";;", ";");
				
				lines_corpus[i] = "§" + lines_corpus[i] + "§";
				lines_corpus[i+1] = "§" + lines_corpus[i+1] + "§";
				
				lines_corpus[i] = lines_corpus[i].replace("§;", "");
				lines_corpus[i+1] = lines_corpus[i+1].replace("§;", "");
				
				lines_corpus[i] = lines_corpus[i].replace(";§", "");
				lines_corpus[i+1] = lines_corpus[i+1].replace(";§", "");
				
				lines_corpus[i] = lines_corpus[i].replace("§", "");
				lines_corpus[i+1] = lines_corpus[i+1].replace("§", "");
				
				if (lines_corpus[i].equals("v;i;-;j͜ə") && lines_corpus[i+1].equals("vʲ;ee̯;;-")) {
					
					lines_corpus[i] = "v;i;j͜ə";
					lines_corpus[i+1] = "vʲ;ee̯;-";
				}
				
				int count_A = 0;
				for (int x = 0; x < lines_corpus[i].length(); x++) {
				    if (lines_corpus[i].charAt(x) == ';') {
				    		count_A++;
				    		}
				}
				
				int count_B = 0;
				for (int x = 0; x < lines_corpus[i+1].length(); x++) {
				    if (lines_corpus[i+1].charAt(x) == ';') {
				    		count_B++;
				    		}
				}
				

				if( count_A != count_B) {
					count_wrong_words++;
					System.out.println(lines_corpus[i]);
					System.out.println(lines_corpus[i+1]);
					System.out.println(count_wrong_words);
					System.out.println("----");
				}
				
				
			int r = 0;	
				

			}
			
			System.out.print("end1");
			//print file
			
			try{
			    PrintWriter writerBenchmark = new PrintWriter("/Users/iome/Desktop/FAAL/TuningDatasets/Benchmarks/BDPA_Global_faal_benchmark.txt", "UTF-8");
		    	
			    for (int t = 0; t < lines_corpus.length; t++){
			    
			    	writerBenchmark.println(lines_corpus[t]);
		    
			    }
				writerBenchmark.close();
				
			} catch (IOException e) {
				   // do something


				}
				
				try{
				    PrintWriter writerInputs = new PrintWriter("/Users/iome/Desktop/FAAL/TuningDatasets/Inputs/BDPA_Global_faal_input_no_alignments.txt", "UTF-8");
			    	
				    for (int t = 0; t < lines_corpus.length; t++){
				    
				    	writerInputs.println(lines_corpus[t].replaceAll(";", "").replaceAll("-", ""));
			    
				    }
					writerInputs.close();
				
				
				
	} catch (IOException e) {
	   // do something


	}
			System.out.print("end");
			
		}
	
}
