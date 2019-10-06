package faal;

import java.util.List;
import java.util.concurrent.Callable;

public class ThreadBeforeMax implements Callable {

	int[] bestPairs;
	List<int[]> sequence;
	int[][] matrixResultComparison;
	int sequenceCount;
	int index;

    public ThreadBeforeMax(int[] bestPairs, List<int[]> sequence, int[][] matrixResultComparison, int sequenceCount, int index) {
        this.bestPairs = bestPairs;
        this.sequence = sequence;
        this.matrixResultComparison = matrixResultComparison;
        this.sequenceCount = sequenceCount;
        this.index = index;
    }
	
	public Object call(){
		
		// loop to build the matches - before max value
		for (int a = 0; a < bestPairs.length; a++) {
			bestPairs[a] = 0;
		}

		for (int z = 0; z < sequence.size(); z++) {
			if (sequence.get(z)[1] > 0 & sequence.get(z)[2] > 0) {
				// search for max values
				for (int n = 0; n < sequence.get(z)[2]; n++) {
					int i = sequence.get(z)[1] - 1;
					for (int a = 0; a < bestPairs.length; a++) {
						if (matrixResultComparison[i][n] > 0 & matrixResultComparison[i][n] == bestPairs[a]) {
							break;
						}
						if (matrixResultComparison[i][n] < 0) {
							break;
						}
						if (matrixResultComparison[i][n] > bestPairs[a]) {
							if (a < bestPairs.length) {
								for (int x = bestPairs.length - 1; x > a; x--) {
									bestPairs[x] = bestPairs[x - 1];
								}
							}
							bestPairs[a] = matrixResultComparison[i][n];
							break;
						}
					}
					;
				}
				;
				for (int i = 0; i < sequence.get(z)[1]; i++) {
					int n = sequence.get(z)[2] - 1;
					for (int a = 0; a < bestPairs.length; a++) {
						if (matrixResultComparison[i][n] > 0 & matrixResultComparison[i][n] == bestPairs[a]) {
							break;
						}
						if (matrixResultComparison[i][n] < 0) {
							break;
						}
						if (matrixResultComparison[i][n] > bestPairs[a]) {
							if (a < bestPairs.length) {
								for (int x = bestPairs.length - 1; x > a; x--) {
									bestPairs[x] = bestPairs[x - 1];
								}
							}
							bestPairs[a] = matrixResultComparison[i][n];
							break;
						}
					}
					;
				}
				;
				// - store max values
				for (int i = 0; i < sequence.get(z)[1]; i++) {
					for (int n = 0; n < sequence.get(z)[2]; n++) {
						for (int x = 0; x < bestPairs.length; x++) {
							if (matrixResultComparison[i][n] == bestPairs[x]) {

								int[] sequenceData = { sequenceCount, i, n, matrixResultComparison[i][n], index, z,
										0 };
								sequence.add(sequenceData);
								sequenceCount++;
								index++;
							}
							;
						}
						;
					}
					;
				}
				;
				for (int a = 0; a < bestPairs.length; a++) {
					bestPairs[a] = 0;
				}
			} else {
				sequence.get(z)[6] = 1;
			}
		}
		;
		
		return sequence;
		
		   } 
	
}
