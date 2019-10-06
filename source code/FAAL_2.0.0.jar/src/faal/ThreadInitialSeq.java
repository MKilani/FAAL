package faal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ThreadInitialSeq implements Callable {
	
	List<List<List<Integer>>> initialSeqs;
	List<int[]> sequence;
	String line1;
	String word1;
	String line2;
	String word2;
	int nextCharacter;
	
    public ThreadInitialSeq(List<List<List<Integer>>> initialSeqs, List<int[]> sequence, String line1, String word1, String line2, String word2, int nextCharacter) {
        this.initialSeqs = initialSeqs;
    	this.sequence = sequence;
        this.line1 = line1;
        this.word1 = word1;
        this.line2 = line2;
        this.word2 = word2;
        this.nextCharacter = nextCharacter;
        
    }
	
	
	
	public Object call(){
		
		
        
        
        List<List<Integer>> listWord1PreTemp = new ArrayList<List<Integer>>();
  		List<List<Integer>> listWord2PreTemp = new ArrayList<List<Integer>>();

  		

		for (int z = 0; z < sequence.size(); z++) {

			if (sequence.get(z)[6] == 1) {

				List<Integer> listLine1 = new ArrayList<Integer>(0);
				List<Integer> listLine2 = new ArrayList<Integer>(0);

				List<Integer> item1 = new ArrayList<Integer>();
				List<Integer> item2 = new ArrayList<Integer>();

				line1 = line1 + word1.charAt(sequence.get(z)[1]);
				line2 = line2 + word2.charAt(sequence.get(z)[2]);

				listLine1.add(sequence.get(z)[1]);
				listLine2.add(sequence.get(z)[2]);

				nextCharacter = sequence.get(z)[5];

				for (int i = sequence.size() - 1; i > -1; i--) {

					if (sequence.get(i)[4] == nextCharacter) {
						nextCharacter = sequence.get(i)[5];
						listLine1.add(sequence.get(i)[1]);
						listLine2.add(sequence.get(i)[2]);

						line1 = line1 + word1.charAt(sequence.get(i)[1]);
						line2 = line2 + word2.charAt(sequence.get(i)[2]);
					}

				}

				int gap = 0;
				int difference = 0;

				line1 = line1 + ":";
				line2 = line2 + ":";

				for (int i = 0; i < listLine1.size(); i++) {
					if (i == 0) {
						difference = listLine1.get(i) - listLine2.get(i);
					} else {
						difference = (listLine1.get(i) - listLine1.get(i - 1))
								- (listLine2.get(i) - listLine2.get(i - 1));
					}
					;

					if (difference == 0) {
						if (i == 0) {
							line1 = line1 + word1.charAt(listLine1.get(i));
							line2 = line2 + word2.charAt(listLine2.get(i));
							item1.add(listLine1.get(i));
							item2.add(listLine2.get(i));
						} else {
							gap = listLine1.get(i) - listLine1.get(i - 1);
							if (gap == 1) {
								line1 = line1 + "-" + word1.charAt(listLine1.get(i));
								line2 = line2 + "-" + word2.charAt(listLine2.get(i));
								item1.add(listLine1.get(i));
								item2.add(listLine2.get(i));
							} else if (gap > 1) {
								for (int n = 1; n <= gap - 1; n++) {
									line1 = line1 + word1.charAt(listLine1.get(i - 1) + n) + "-0";
									line2 = line2 + "-0" + word2.charAt(listLine2.get(i - 1) + n);
									item1.add(listLine1.get(i - 1) + n);
									item1.add(-1);
									item2.add(-1);
									item2.add(listLine2.get(i - 1) + n);
								}

								line1 = line1 + "-" + word1.charAt(listLine1.get(i));
								line2 = line2 + "-" + word2.charAt(listLine2.get(i));
								item1.add(listLine1.get(i));
								item2.add(listLine2.get(i));

							}
						}
					}
					if (difference < 0) {

						if (i > 0) {
							gap = listLine1.get(i) - listLine1.get(i - 1);
						}
						if (gap > 1) {
							for (int n = 1; n < gap; n++) {
								line1 = line1 + "-" + word1.charAt(listLine1.get(i - 1) + n) + "-0";
								line2 = line2 + "-0-" + word2.charAt(listLine2.get(i - 1) + n);
								item1.add(listLine1.get(i - 1) + n);
								item1.add(-1);
								item2.add(-1);
								item2.add(listLine2.get(i - 1) + n);
							}
						}
						// ----
						for (int n = -1 * difference; n > 0; n--) {
							line1 = line1 + "-0";
							line2 = line2 + "-" + word2.charAt(listLine2.get(i) - n);
							item1.add(-1);
							item2.add(listLine2.get(i) - n);
						}

						line1 = line1 + "-" + word1.charAt(listLine1.get(i));
						line2 = line2 + "-" + word2.charAt(listLine2.get(i));
						item1.add(listLine1.get(i));
						item2.add(listLine2.get(i));
						// -------

					} else if (difference > 0) {

						if (i > 0) {
							gap = listLine2.get(i) - listLine2.get(i - 1);
						}
						if (gap > 1) {
							for (int n = 1; n < gap; n++) {
								line1 = line1 + "-" + word1.charAt(listLine1.get(i - 1) + n) + "-0";
								line2 = line2 + "-0-" + word2.charAt(listLine2.get(i - 1) + n);
								item1.add(listLine1.get(i - 1) + n);
								item1.add(-1);
								item2.add(-1);
								item2.add(listLine2.get(i - 1) + n);
							}
						}
						// ----
						for (int n = difference; n > 0; n--) {
							line2 = line2 + "-0";
							line1 = line1 + "-" + word1.charAt(listLine1.get(i) - n);
							item2.add(-1);
							item1.add(listLine1.get(i) - n);
						}

						line1 = line1 + "-" + word1.charAt(listLine1.get(i));
						line2 = line2 + "-" + word2.charAt(listLine2.get(i));
						item1.add(listLine1.get(i));
						item2.add(listLine2.get(i));
						// -------

					}

				}
				gap = 0;
				difference = 0;

				line1 = "";
				line2 = "";

				boolean alreadyListed_Pre = false;

				for (int g = 0; g < listWord1PreTemp.size(); g++) {
					if (listWord1PreTemp.get(g).equals(item1) & listWord2PreTemp.get(g).equals(item2)) {
						alreadyListed_Pre = true;
						break;
					}
				}

				if (alreadyListed_Pre == false) {
					listWord1PreTemp.add(item1);
					listWord2PreTemp.add(item2);
					alreadyListed_Pre = true;
				}

			}
			

		}
		
		initialSeqs.add(listWord1PreTemp);
		initialSeqs.add(listWord2PreTemp);
		
		
		
		return initialSeqs;
		
		
	}
		

}
