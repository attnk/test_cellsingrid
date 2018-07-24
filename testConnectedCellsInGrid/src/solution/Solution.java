package solution;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {

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
     * @param row
     * @param column
     * @return
     */
    private static int getLastTotal(
			int[][] matrix, 
			int aux, 
			int[][] countAux, 
			int row, 
			int column) {
		
		int maxColumn = matrix[row].length;
		
		if(row == 0) {
			if(column == 0 && matrix[row][column] == 1) {
				aux = countAux[row][column];
				
			} else if(column > 0 && matrix[row][column] == 1) {
				if(matrix[row][column-1] == 1) {
					aux = countAux[row][column-1];
					
				}
			}
		} else if(row > 0) {
			if(column == 0 && matrix[row][column] == 1) { 
				if(matrix[row-1][column] == 1) {
					aux = countAux[row-1][maxColumn-1] <= 1 ?
							countAux[row-1][column] : countAux[row-1][maxColumn-1];
							
				} else if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
					aux = countAux[row-1][column+1];
				}
			} else if(column > 0 && matrix[row][column] == 1) {
				if(matrix[row][column-1] == 1) {
					aux = countAux[row][column-1];
					
				} else if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
					aux = countAux[row-1][column+1];
					
				} else if(matrix[row-1][column] == 1) {
					aux = countAux[row-1][column];
					
				} else if(matrix[row-1][column-1] == 1) {
					aux = countAux[row-1][column-1];
					
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

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int m = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[][] matrix = new int[n][m];

        for (int i = 0; i < n; i++) {
            String[] matrixRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < m; j++) {
                int matrixItem = Integer.parseInt(matrixRowItems[j]);
                matrix[i][j] = matrixItem;
            }
        }

        int result = connectedCell(matrix);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
