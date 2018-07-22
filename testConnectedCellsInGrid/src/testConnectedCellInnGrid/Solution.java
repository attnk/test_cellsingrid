package testConnectedCellInnGrid;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Solution {

    // Complete the connectedCell function below.
    static int connectedCell(int[][] matrix) {
    	int max = 0, 
			aux = 0,  
			maxRows = matrix.length, 
			maxColumns = matrix[0].length;
    	
    	
    	for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if(matrix[i][j] ==  1) {
					if(i == 0) {
						if(j == 0 && matrix[i][j] == 1) {
							max++;
						} else if(j > 0 && matrix[i][j-1] == 1) {
							max++;
						}
					} else if(i > 0) {
						if(j == 0 && 
							matrix[i][j] == 1 &&
							matrix[i-1][j] == 1) {
							
							max++;
						} else if(j > 0 && 
								matrix[i][j] == 1 && 
								(matrix[i][j-1] == 1 || 
								matrix[i-1][j] == 1 ||
								matrix[i-1][j-1] == 1)) {
							
							max++;
						}
					}
				}
			}
		}
    	
    	return max;
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
    	int m = 4;
    	
        int[][] matrix = new int[n][m];

        matrix[0][0] = 1;
        matrix[0][1] = 1;
        matrix[0][2] = 1;
        matrix[0][3] = 0;
        
        matrix[1][0] = 0;
        matrix[1][1] = 0;
        matrix[1][2] = 1;
        matrix[1][3] = 0;
        
        matrix[2][0] = 1;
        matrix[2][1] = 0;
        matrix[2][2] = 0;
        matrix[2][3] = 1;
        
        matrix[3][0] = 0;
        matrix[3][1] = 1;
        matrix[3][2] = 0;
        matrix[3][3] = 0;
        
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