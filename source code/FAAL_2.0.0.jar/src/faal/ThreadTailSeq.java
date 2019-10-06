package faal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ThreadTailSeq implements Callable {
	
	
	
	List<List<List<Integer>>> tailSeqs;
	List<int[]> sequence_Tail;
	String line1;
	String word1;
	String line2;
	String word2;
	int nextCharacter_Tail;
	int lengthWord1;
	int lengthWord2;
	
    public ThreadTailSeq(int lengthWord1, int lengthWord2, List<List<List<Integer>>> tailSeqs, List<int[]> sequence_Tail, String line1, String word1, String line2, String word2, int nextCharacter_Tail) {
        this.tailSeqs = tailSeqs;
    	this.sequence_Tail = sequence_Tail;
        this.line1 = line1;
        this.word1 = word1;
        this.line2 = line2;
        this.word2 = word2;
        this.nextCharacter_Tail = nextCharacter_Tail;
        this.lengthWord1 = lengthWord1;
        this.lengthWord2 = lengthWord2;
        
    }
	
	
	
	
	public Object call(){
		
		List<List<Integer>> listWord1PostTemp = new ArrayList<List<Integer>>();
  		List<List<Integer>> listWord2PostTemp = new ArrayList<List<Integer>>();
  		
		// System.out.println("-------");
		// build final sequence
		for (int z = 0; z < sequence_Tail.size(); z++) {
			if (sequence_Tail.get(z)[6] == 9) {

				List<Integer> line1Tail_Inverted = new ArrayList<Integer>(0);
				List<Integer> line2Tail_Inverted = new ArrayList<Integer>(0);
				List<Integer> line1_Tail = new ArrayList<Integer>(0);
				List<Integer> line2_Tail = new ArrayList<Integer>(0);

				List<Integer> item1_2 = new ArrayList<Integer>();
				List<Integer> item2_2 = new ArrayList<Integer>();

				line1Tail_Inverted.add(sequence_Tail.get(z)[1]);
				line2Tail_Inverted.add(sequence_Tail.get(z)[2]);

				nextCharacter_Tail = sequence_Tail.get(z)[5];

				for (int i = sequence_Tail.size() - 1; i > -1; i--) {

					if (sequence_Tail.get(i)[4] == nextCharacter_Tail) {
						nextCharacter_Tail = sequence_Tail.get(i)[5];
						line1Tail_Inverted.add(sequence_Tail.get(i)[1]);
						line2Tail_Inverted.add(sequence_Tail.get(i)[2]);

					}

				}

				for (int i = line1Tail_Inverted.size() - 1; i > -1; i--) {
					line1_Tail.add(line1Tail_Inverted.get(i));
					line2_Tail.add(line2Tail_Inverted.get(i));
				}

				for (int i = 0; i < line1Tail_Inverted.size(); i++) {
					line1 = line1 + word1.charAt(line1Tail_Inverted.get(i));
					line2 = line2 + word2.charAt(line2Tail_Inverted.get(i));
				}

				int gap = 0;
				int difference = 0;

				line1 = line1 + "::";
				line2 = line2 + "::";

				for (int i = 0; i < line1_Tail.size(); i++) {
					if (i == 0) {
						difference = line1_Tail.get(i) - line2_Tail.get(i);
					} else {
						difference = (line1_Tail.get(i) - line1_Tail.get(i - 1))
								- (line2_Tail.get(i) - line2_Tail.get(i - 1));
					}
					;

					if (difference == 0) {
						if (i == 0) {
							line1 = line1 + word1.charAt(line1_Tail.get(i));
							line2 = line2 + word2.charAt(line2_Tail.get(i));
							item1_2.add(line1_Tail.get(i));
							item2_2.add(line2_Tail.get(i));

						} else {
							gap = line1_Tail.get(i) - line1_Tail.get(i - 1);
							if (gap == 1) {
								line1 = line1 + "-" + word1.charAt(line1_Tail.get(i));
								line2 = line2 + "-" + word2.charAt(line2_Tail.get(i));
								item1_2.add(line1_Tail.get(i));
								item2_2.add(line2_Tail.get(i));

							} else if (gap > 1) {
								for (int n = 1; n <= gap - 1; n++) {
									line1 = line1 + "-" + word1.charAt(line1_Tail.get(i - 1) + n) + "-0";
									line2 = line2 + "-0-" + word2.charAt(line2_Tail.get(i - 1) + n);
									item1_2.add(line1_Tail.get(i - 1) + n);
									item1_2.add(-1);
									item2_2.add(-1);
									item2_2.add(line2_Tail.get(i - 1) + n);
								}

								line1 = line1 + "-" + word1.charAt(line1_Tail.get(i));
								line2 = line2 + "-" + word2.charAt(line2_Tail.get(i));
								item1_2.add(line1_Tail.get(i));
								item2_2.add(line2_Tail.get(i));

							}
						}
					}
					if (difference < 0) {

						if (i > 0) {
							gap = line1_Tail.get(i) - line1_Tail.get(i - 1);
						}
						if (gap > 1) {
							for (int n = 1; n < gap; n++) {
								line1 = line1 + "-" + word1.charAt(line1_Tail.get(i - 1) + n) + "-0";
								line2 = line2 + "-0-" + word2.charAt(line2_Tail.get(i - 1) + n);
								item1_2.add(line1_Tail.get(i - 1) + n);
								item1_2.add(-1);
								item2_2.add(-1);
								item2_2.add(line2_Tail.get(i - 1) + n);
							}
						}
						// ----
						for (int n = -1 * difference; n > 0; n--) {
							if (item1_2.size() > 0) {
								line1 = line1 + "-0";
								line2 = line2 + "-" + word2.charAt(line2_Tail.get(i) - n);
								item1_2.add(-1);
								item2_2.add(line2_Tail.get(i) - n);
							}
						}

						line1 = line1 + "-" + word1.charAt(line1_Tail.get(i));
						line2 = line2 + "-" + word2.charAt(line2_Tail.get(i));
						item1_2.add(line1_Tail.get(i));
						item2_2.add(line2_Tail.get(i));
						// -------

					} else if (difference > 0) {

						if (i > 0) {
							gap = line2_Tail.get(i) - line2_Tail.get(i - 1);
						}
						if (gap > 1) {
							for (int n = 1; n < gap; n++) {
								line1 = line1 + "-" + word1.charAt(line1_Tail.get(i - 1) + n) + "-0";
								line2 = line2 + "-0-" + word2.charAt(line2_Tail.get(i - 1) + n);
								item1_2.add(line1_Tail.get(i - 1) + n);
								item1_2.add(-1);
								item2_2.add(-1);
								item2_2.add(line2_Tail.get(i - 1) + n);
							}
						}
						// ----
						for (int n = difference; n > 0; n--) {
							if (item1_2.size() > 0) {
								line2 = line2 + "-0";
								line1 = line1 + "-" + word1.charAt(line1_Tail.get(i) - n);
								item2_2.add(-1);
								item1_2.add(line1_Tail.get(i) - n);
							}
						}

						line1 = line1 + "-" + word1.charAt(line1_Tail.get(i));
						line2 = line2 + "-" + word2.charAt(line2_Tail.get(i));
						item1_2.add(line1_Tail.get(i));
						item2_2.add(line2_Tail.get(i));
						// -------

					}

				}

				if (item1_2.get(item1_2.size() - 1) != lengthWord1 - 1) {
					for (int i = item1_2.get(item1_2.size() - 1) + 1; i < lengthWord1; i++) {
						item1_2.add(i);
						item2_2.add(-1);
						line1 = line1 + ":" + word1.charAt(i);
						line2 = line2 + "-0";
					}
				} else if (item2_2.get(item2_2.size() - 1) != lengthWord2 - 1) {
					for (int i = item2_2.get(item2_2.size() - 1) + 1; i < lengthWord2; i++) {
						item1_2.add(-1);
						item2_2.add(i);
						line1 = line1 + "-0";
						line2 = line2 + ":" + word2.charAt(i);
					}
				}

				gap = 0;
				difference = 0;

				line1 = "";
				line2 = "";

				boolean alreadyListed_Post = false;
				for (int g = 0; g < listWord1PostTemp.size(); g++) {
					if (listWord1PostTemp.get(g).equals(item1_2) & listWord2PostTemp.get(g).equals(item2_2)) {
						alreadyListed_Post = true;
						break;
					}
				}

				if (alreadyListed_Post == false) {
					listWord1PostTemp.add(item1_2);
					listWord2PostTemp.add(item2_2);
					alreadyListed_Post = true;
				}

			}
		}
		
		
		tailSeqs.add(listWord1PostTemp);
		tailSeqs.add(listWord2PostTemp);
		
		
		
		return tailSeqs;
		
		
		
	}

}
