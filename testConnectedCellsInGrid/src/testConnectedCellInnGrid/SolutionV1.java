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
    	
//    	if(matrixMaxSlots == Arrays.stream(
//    			matrixToArray(matrixMaxSlots, matrix))
//    			.filter(x -> x == 1).count()) {
//    		
//    		max = matrixMaxSlots;
//    	} else {
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
//    	}
    	
    	return max;
    }

    /**
     * 
     * @param matrix
     * @param count
     * @param countAux
     * @param row
     * @param column
     * @return
     */
	private static int getLastTotal(
			int[][] matrix, 
			int count, 
			int[][] countAux, 
			int row, 
			int column) {
		
		int maxColumn = matrix[row].length;
		
		if(row == 0) {
			if(column == 0 && matrix[row][column] == 1) {
				count = countAux[row][column];
				
			} else if(column > 0 && matrix[row][column] == 1) {
				if(matrix[row][column-1] == 1) {
					count = countAux[row][column-1];
					
				}
			}
		} else if(row > 0) {
			if(column == 0 && matrix[row][column] == 1) { 
				if(matrix[row-1][column] == 1) {
					count = countAux[row-1][maxColumn-1] <= 1 ?
							countAux[row-1][column] : countAux[row-1][maxColumn-1];
							
				} else if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
					count = countAux[row-1][maxColumn-1] <= 1 ? 
							countAux[row-1][column+1] : countAux[row-1][maxColumn-1];
				}
			} else if(column > 0 && matrix[row][column] == 1) {
				
				if(matrix[row][column-1] == 1) {
					count = countAux[row][column-1];
					
				} else if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
					count = countAux[row-1][column+1];
					
				} else {
					// verificar se já existe alguém contabilizado
					int aux = 0, 
						position = column-1;
					
					while(position >= 0) {
						if(matrix[row][position] == 0) {
							aux++;
						}
						position--;
					}
					
					for(int i = (column - aux); i < aux; i++) {
						if(matrix[row-1][i] == 1 && count < countAux[row][i > 0 ? i-1 : i]) {
								count = countAux[row][i > 0 ? i-1 : i];
						}
					}
					
					if(count == 0) {
						if(matrix[row-1][column] == 1) {
							count = countAux[row-1][column];
							
						} else if(matrix[row-1][column-1] == 1) {
							count = countAux[row-1][column-1];
							
						} 
					}
				}
			}
		}
		return count;
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
				System.out.print(matrix[i][j]);
				System.out.print(" | ");
				
				auxArray[posAuxArray] = matrix[i][j];
				posAuxArray++;
			}
			System.out.println();
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

    	int n = 5;
    	int m = 5;
    	
        int[][] matrix = new int[n][m];

        matrix[0][0] = 0;
        matrix[0][1] = 1;
        matrix[0][2] = 1;
        matrix[0][3] = 1;
        matrix[0][4] = 1;
        
        matrix[1][0] = 1;
        matrix[1][1] = 0;
        matrix[1][2] = 0;
        matrix[1][3] = 0;
        matrix[1][4] = 1;
        
        matrix[2][0] = 1;
        matrix[2][1] = 1;
        matrix[2][2] = 0;
        matrix[2][3] = 1;
        matrix[2][4] = 0;
        
        matrix[3][0] = 0;
        matrix[3][1] = 1;
        matrix[3][2] = 0;
        matrix[3][3] = 1;
        matrix[3][4] = 1;
        
        matrix[4][0] = 0;
        matrix[4][1] = 1;
        matrix[4][2] = 1;
        matrix[4][3] = 1;
        matrix[4][4] = 0;
        
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