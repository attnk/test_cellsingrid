package solution;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Solution {

	/**
	 * 
	 * @param row
	 * @param column
	 * @param cells
	 * @return
	 */
	private static int getGroupTo(int row, int column, List<Cell> cells) {
		int result;
		
		try {
			result = cells.stream()
					.filter(c -> c.getRow() == row && c.getColumn() == column)
					.findFirst()
					.get().getGroup();
			
		} catch (NoSuchElementException e) {
			result = cells.stream().mapToInt(Cell::getGroup).max().getAsInt()+1;
		}
		
		return result;
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

	/**
	 * 
	 * @param cells
	 */
	private static int getLastGroupNumber(List<Cell> cells) {
		int group = 0;
		
		if(cells.isEmpty()) {
			group = 1;
		} else {
			group = cells.get(cells.size()-1).getGroup();
		}
		
		return group;
	}
	
	private static class Cell{
		
		private int column;
		private int row;
		private int group;
		private int count;
		
		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
			this.group = 0;
		}

		public int getColumn() {
			return column;
		}
		
		public int getRow() {
			return row;
		}
		
		public int getGroup() {
			return group;
		}
		
		public void setGroup(int group) {
			this.group = group;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
	}
	
	/**
     * 
     * @param matrix
     * @param count
     * @param countAux
     * @param row
     * @param column
     * @param cells
     * @return
     */
	private static int getLastTotal(
			int[][] matrix, 
			int[][] countAux, 
			int row, 
			int column,
			List<Cell> cells) {
		
		int maxColumn = matrix[row].length;
		Cell cell = new Cell(row, column);
		int lastTotal = 0;
		
		if(row == 0) {
			if(column == 0 && matrix[row][column] == 1) {
				cell.setGroup(1);
				lastTotal = countAux[row][column];
				
			} else if(column > 0 && matrix[row][column] == 1) {
				if(matrix[row][column-1] == 1) {
					cell.setGroup(getGroupTo(row, column-1, cells));
					lastTotal = countAux[row][column-1];
					
				} else {
					cell.setGroup(getLastGroupNumber(cells)+1);
				}
			}
		} else if(row > 0) {
			if(column == 0 && matrix[row][column] == 1) { 
				if(matrix[row-1][column] == 1) {
					if(countAux[row-1][maxColumn-1] <= 1) {
						cell.setGroup(getGroupTo(row-1, column, cells));
						lastTotal = countAux[row-1][column];
						
					} else {
						cell.setGroup(getGroupTo(row-1, maxColumn-1, cells));
						lastTotal = countAux[row-1][maxColumn-1];
					}
				} else if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
					if(countAux[row-1][maxColumn-1] <= 1) {
						cell.setGroup(getGroupTo(row-1, column+1, cells));
						lastTotal = countAux[row-1][column+1];
						
					} else {
						cell.setGroup(getGroupTo(row-1, maxColumn-1, cells));
						lastTotal = countAux[row-1][maxColumn-1];
					}
				} else {
					cell.setGroup(getLastGroupNumber(cells)+1);
				}
			} else if(column > 0 && matrix[row][column] == 1) {
				Cell cellMaxCount = Collections.max(cells, Comparator.comparing(Cell::getCount));
				
				if(matrix[row][column-1] == 1) {
					cell.setGroup(getGroupTo(row, column-1, cells));
					lastTotal = countAux[row][column-1];
					
					if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
						if(cellMaxCount.getGroup() == getGroupTo(row-1, column+1, cells)) {
							cell.setGroup(getGroupTo(cellMaxCount.getRow(), cellMaxCount.getColumn(), cells));
							lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
						} 
						
						if(getGroupTo(row, column-1, cells) != getGroupTo(row-1, column+1, cells)) {
							countAux[row][column-1] = lastTotal + 2;
							
							cells.stream()
							.filter(c -> c.getRow() == row && c.getColumn() == column-1)
							.findFirst()
							.get()
							.setGroup(cell.getGroup());
							
							cells.stream()
							.filter(c -> c.getRow() == row && c.getColumn() == column-1)
							.findFirst()
							.get().setCount(countAux[row][column-1]);
							
							cell.setGroup(getGroupTo(row-1, column+1, cells));
							lastTotal = countAux[row-1][column+1];
						}
						
					} else if(matrix[row-1][column] == 1) {
						if(cellMaxCount.getGroup() == getGroupTo(row-1, column, cells)) {
							cell.setGroup(getGroupTo(cellMaxCount.getRow(), cellMaxCount.getColumn(), cells));
							lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
						} 

						if(getGroupTo(row, column-1, cells) != getGroupTo(row-1, column, cells)) {
							countAux[row][column-1] = lastTotal + 2;
							
							cells.stream()
							.filter(c -> c.getRow() == row && c.getColumn() == column-1)
							.findFirst()
							.get()
							.setGroup(cell.getGroup());
							
							cells.stream()
							.filter(c -> c.getRow() == row && c.getColumn() == column-1)
							.findFirst()
							.get().setCount(countAux[row][column-1]);
							
							cell.setGroup(getGroupTo(row-1, column, cells));
							lastTotal = countAux[row-1][column];
						}
					}
					
				} else if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
					if(cellMaxCount.getGroup() == getGroupTo(row-1, column+1, cells)) {
						cell.setGroup(getGroupTo(cellMaxCount.getRow(), cellMaxCount.getColumn(), cells));
						lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
					} else {
						cell.setGroup(getGroupTo(row-1, column+1, cells));
						lastTotal = countAux[row-1][column+1];
					}
				} else if(matrix[row-1][column] == 1) {
					if(cellMaxCount.getGroup() == getGroupTo(row-1, column, cells)) {
						cell.setGroup(getGroupTo(cellMaxCount.getRow(), cellMaxCount.getColumn(), cells));
						lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
					} else {
						cell.setGroup(getGroupTo(row-1, column, cells));
						lastTotal = countAux[row-1][column];
					}
				} else if(matrix[row-1][column-1] == 1) {
					if(cellMaxCount.getGroup() == getGroupTo(row-1, column-1, cells)) {
						cell.setGroup(getGroupTo(cellMaxCount.getRow(), cellMaxCount.getColumn(), cells));
						lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
					} else {
						cell.setGroup(getGroupTo(row-1, column-1, cells));
						lastTotal = countAux[row-1][column-1];
					}
				} else {
					cell.setGroup(getLastGroupNumber(cells)+1);
				}
			}
		}
		cells.add(cell);
		
		return lastTotal;
	}
	
    // Complete the connectedCell function below.
    static int connectedCell(int[][] matrix) {
    	int max = 0, 
			aux = 0;
    	
    	List<Cell> cells = new ArrayList<>();
    	
    	int[][] countAux = new int[matrix.length][matrix[0].length];
    	int matrixMaxSlots = (matrix.length*matrix[0].length);
    	
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if(matrix[i][j] ==  1) {
					aux = getLastTotal(matrix, countAux, i, j, cells);
					aux++;
					cells.get(cells.size()-1).setCount(aux);
				} else {
					aux = 0;
				}
				countAux[i][j] = aux;
			}
			aux = 0;
		}
		max = Arrays.stream(matrixToArray(matrixMaxSlots, countAux)).max().getAsInt();
    	
    	return max;
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
