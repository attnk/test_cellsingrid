package solution;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Solutionn {

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
