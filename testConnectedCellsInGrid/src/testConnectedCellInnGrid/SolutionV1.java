package testConnectedCellInnGrid;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class SolutionV1 {

    // Complete the connectedCell function below.
    static int connectedCell(int[][] matrix) {
    	int max = 0, 
			aux = 0;
    	
    	int[][] countAux = new int[matrix.length][matrix[0].length];
    	int matrixMaxSlots = (matrix.length*matrix[0].length);
    	
    	if(matrixMaxSlots == Arrays.stream(
    			matrixToArray(matrixMaxSlots, matrix))
    			.filter(x -> x == 1).count()) {
    		
    		max = matrixMaxSlots;
    	} else {
    		for (int i = 0; i < matrix.length; i++) {
    			for (int j = 0; j < matrix[i].length; j++) {
    				if(matrix[i][j] ==  1) {
    					aux = getLastTotal(matrix, aux, countAux, i, j);
    					aux++;
    				} else {
    					aux = 0;
    				}
    				countAux[i][j] = aux;
    			}
    		}
    		max = Arrays.stream(matrixToArray(matrixMaxSlots, countAux)).max().getAsInt();
    	}
    	
    	return max;
    }

    /**
     * 
     * @param matrix
     * @param aux
     * @param countAux
     * @param matrixRow
     * @param matrixColumn
     * @return
     */
	private static int getLastTotal(
			int[][] matrix, 
			int aux, 
			int[][] countAux, 
			int matrixRow, 
			int matrixColumn) {
		
		if(matrixRow == 0) {
			if(matrixColumn == 0 ) {
				if (matrix[matrixRow][matrixColumn] == 1) {
					aux = countAux[matrixRow][matrixColumn];
				}
			} else if(matrixColumn > 0 
					&& matrix[matrixRow][matrixColumn] == 1 
					&& matrix[matrixRow][matrixColumn-1] == 1) {
				
				aux = countAux[matrixRow][matrixColumn-1];
			}
		} else if(matrixRow > 0) {
			if(matrixColumn == 0 
					&& matrix[matrixRow][matrixColumn] == 1 
					&& matrix[matrixRow-1][matrixColumn] == 1) {
				
				aux = countAux[matrixRow-1][matrixColumn];
			} else if(matrixColumn > 0 && matrix[matrixRow][matrixColumn] == 1) {
				if(matrix[matrixRow][matrixColumn-1] == 1) {
					aux = countAux[matrixRow][matrixColumn-1];
					
				} else if(matrix[matrixRow-1][matrixColumn] == 1) {
					aux = countAux[matrixRow-1][matrixColumn];
					
				} else if(matrix[matrixRow-1][matrixColumn-1] == 1) {
					aux = countAux[matrixRow-1][matrixColumn-1];
					
				}
			}
		}
		return aux;
	}

	/**
	 * 
	 * @param arraySize
	 * @param matrix
	 * @return
	 */
	private static int[] matrixToArray(int arraySize, int[][] matrix) {
		int[] auxArray = new int[arraySize];
    	int posAuxArray = 0;
    	
        for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				auxArray[posAuxArray] = matrix[i][j];
				posAuxArray++;
			}
		}
		return auxArray;
	}

//    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\PROGRAMAS\\DEV\\PROJETOS\\JAVA_WORKSPACE\\tests\\test_ammo_varejo\\test_cellsingrid\\test_file.txt"));
//
//        int n = scanner.nextInt();
//        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
//
//        int m = scanner.nextInt();
//        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

    	int n = 4;
    	int m = 5;
    	
        int[][] matrix = new int[n][m];

        matrix[0][0] = 1;
        matrix[0][1] = 1;
        matrix[0][2] = 1;
        matrix[0][3] = 1;
        matrix[0][4] = 1;
        
        matrix[1][0] = 1;
        matrix[1][1] = 1;
        matrix[1][2] = 1;
        matrix[1][3] = 1;
        matrix[1][4] = 1;
        
        matrix[2][0] = 1;
        matrix[2][1] = 1;
        matrix[2][2] = 1;
        matrix[2][3] = 1;
        matrix[2][4] = 1;
        
        matrix[3][0] = 1;
        matrix[3][1] = 1;
        matrix[3][2] = 1;
        matrix[3][3] = 1;
        matrix[3][4] = 1;
        
//        for (int i = 0; i < n; i++) {
//            String[] matrixRowItems = scanner.nextLine().split(" ");
//            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
//
//            for (int j = 0; j < m; j++) {
//                int matrixItem = Integer.parseInt(matrixRowItems[j]);
//                matrix[i][j] = matrixItem;
//            }
//        }

        // TODO: só para print da matriz usada
        for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j]);
				System.out.print(" | ");
			}
			System.out.println();
		}

    	System.out.println("\n");
        
        // para o teste aqui deveria ser result = 5
        int result = connectedCell(matrix);

        System.out.println(result);
        
//        bufferedWriter.write(String.valueOf(result));
//        bufferedWriter.newLine();
//
//        bufferedWriter.close();
//
//        scanner.close();
    }
}