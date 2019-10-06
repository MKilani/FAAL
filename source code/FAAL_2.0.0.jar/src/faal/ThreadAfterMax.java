package faal;

import java.util.List;
import java.util.concurrent.Callable;

public class ThreadAfterMax implements Callable {

	int[] bestPairs;
	List<int[]> sequence_Tail;
	int[][] matrixResultComparison;
	int sequenceCount_Tail;
	int index_Tail;
	int lengthWord1;
	int lengthWord2;
	

    public ThreadAfterMax(int lengthWord1, int lengthWord2, int[] bestPairs, List<int[]> sequence_Tail, int[][] matrixResultComparison, int sequenceCount_Tail, int index_Tail) {
        this.bestPairs = bestPairs;
        this.sequence_Tail = sequence_Tail;
        this.matrixResultComparison = matrixResultComparison;
        this.sequenceCount_Tail = sequenceCount_Tail;
        this.index_Tail = index_Tail;
        this.lengthWord1 = lengthWord1;
        this.lengthWord2 = lengthWord2;
    }
	
	public Object call(){
		
		// -----
					// =========
					// loop to build the matches - after max value
					for (int a = 0; a < bestPairs.length; a++) {
						bestPairs[a] = 0;
					}

					for (int z = 0; z < sequence_Tail.size(); z++) {
						for (int a = 0; a < bestPairs.length; a++) {
							bestPairs[a] = 0;
						}
						if (sequence_Tail.get(z)[1] + 1 < lengthWord1 & sequence_Tail.get(z)[2] + 1 < lengthWord2) {
							// search for max values
							for (int i = sequence_Tail.get(z)[1] + 1; i < lengthWord1; i++) {
								int n = sequence_Tail.get(z)[2] + 1;
								// for(int n = sequence_Tail.get(z)[2]+1; n < lengthWord2; n++){
								// int check = 0;
								for (int a = 0; a < bestPairs.length; a++) {
									// check = matrixResultComparison[i][n];
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
								// };
							}
							;

							// for(int i = sequence_Tail.get(z)[1]+1; i < lengthWord1; i++){

							for (int n = sequence_Tail.get(z)[2] + 1; n < lengthWord2; n++) {
								int i = sequence_Tail.get(z)[1] + 1;
								// int check = 0;
								for (int a = 0; a < bestPairs.length; a++) {
									// check = matrixResultComparison[i][n];
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
								// };
							}
							;
							// ---- store max values
							for (int i = sequence_Tail.get(z)[1] + 1; i < lengthWord1; i++) {
								for (int n = sequence_Tail.get(z)[2] + 1; n < lengthWord2; n++) {
									for (int x = 0; x < bestPairs.length; x++) {
										if (matrixResultComparison[i][n] == bestPairs[x]) {

											int[] sequenceData_Tail = { sequenceCount_Tail, i, n, matrixResultComparison[i][n],
													index_Tail, z, 0 };
											sequence_Tail.add(sequenceData_Tail);
											sequenceCount_Tail++;
											index_Tail++;
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
							sequence_Tail.get(z)[6] = 9;
						}
						for (int a = 0; a < bestPairs.length; a++) {
							bestPairs[a] = 0;
						}
					}
					;

		
		return sequence_Tail;
		
		   } 
	
}
