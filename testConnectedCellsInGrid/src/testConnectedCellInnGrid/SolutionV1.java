package testConnectedCellInnGrid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class SolutionV1 {

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
			int count, 
			int[][] countAux, 
			int row, 
			int column,
			List<Cell> cells) {
		
		int maxColumn = matrix[row].length;
		Cell cell = new Cell(row, column);
		
		if(row == 0) {
			if(column == 0 && matrix[row][column] == 1) {
				cell.setGroup(1);
				count = countAux[row][column];
				
			} else if(column > 0 && matrix[row][column] == 1) {
				if(matrix[row][column-1] == 1) {
					cell.setGroup(getGroupTo(row, column-1, cells));
					count = countAux[row][column-1];
					
				} else {
					cell.setGroup(getLastGridNumber(cells, cell));
				}
			}
		} else if(row > 0) {
			if(column == 0 && matrix[row][column] == 1) { 
				if(matrix[row-1][column] == 1) {
					if(countAux[row-1][maxColumn-1] <= 1) {
						cell.setGroup(getGroupTo(row-1, column, cells));
						count = countAux[row-1][column];
						
					} else {
						cell.setGroup(getGroupTo(row-1, maxColumn-1, cells));
						count = countAux[row-1][maxColumn-1];
					}
				} else if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
					if(countAux[row-1][maxColumn-1] <= 1) {
						cell.setGroup(getGroupTo(row-1, column+1, cells));
						count = countAux[row-1][column+1];
						
					} else {
						cell.setGroup(getGroupTo(row-1, maxColumn-1, cells));
						count = countAux[row-1][maxColumn-1];
					}
				}
			} else if(column > 0 && matrix[row][column] == 1) {
				List<Cell> listAux = new ArrayList<>();
				Cell cellAux = null;
				
				if(matrix[row][column-1] == 1) {
					cell.setGroup(getGroupTo(row, column-1, cells));
					count = countAux[row][column-1];
					
				} else if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
					listAux = cells.stream().filter(c -> c.getGroup() == 1).collect(Collectors.toList());
					cellAux = listAux.get(listAux.size()-1);
					
					if(cellAux.getGroup() == getGroupTo(row-1, column, cells)) {
						cell.setGroup(getGroupTo(cellAux.getRow(), cellAux.getColumn(), cells));
						count = countAux[cellAux.getRow()][cellAux.getColumn()];
					} else {
						cell.setGroup(getGroupTo(row-1, column, cells));
						count = countAux[row-1][column+1];
					}
					
				} else if(matrix[row-1][column] == 1) {
					listAux = cells.stream().filter(c -> c.getGroup() == 1).collect(Collectors.toList());
					cellAux = listAux.get(listAux.size()-1);
					
					if(cellAux.getGroup() == getGroupTo(row-1, column, cells)) {
						cell.setGroup(getGroupTo(cellAux.getRow(), cellAux.getColumn(), cells));
						count = countAux[cellAux.getRow()][cellAux.getColumn()];
					} else {
						cell.setGroup(getGroupTo(row-1, column, cells));
						count = countAux[row-1][column];
					}
					
				} else if(matrix[row-1][column-1] == 1) {
					listAux = cells.stream().filter(c -> c.getGroup() == 1).collect(Collectors.toList());
					cellAux = listAux.get(listAux.size()-1);
					
					if(cellAux.getGroup() == getGroupTo(row-1, column, cells)) {
						cell.setGroup(getGroupTo(cellAux.getRow(), cellAux.getColumn(), cells));
						count = countAux[cellAux.getRow()][cellAux.getColumn()];
					} else {
						cell.setGroup(getGroupTo(row-1, column, cells));
						count = countAux[row-1][column-1];
					}
				} 
			}
		}
		cells.add(cell);
		
		return count;
	}


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
	 * @param cell
	 */
	private static int getLastGridNumber(List<Cell> cells, Cell cell) {
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
	}
	
    // Complete the connectedCell function below.
    static int connectedCell(int[][] matrix) {
    	int max = 0, 
			aux = 0;
    	
    	List<Cell> cellls = new ArrayList<>();
    	
    	int[][] countAux = new int[matrix.length][matrix[0].length];
    	int matrixMaxSlots = (matrix.length*matrix[0].length);
    	
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if(matrix[i][j] ==  1) {
					aux = getLastTotal(matrix, aux, countAux, i, j, cellls);
					aux++;
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

//        matrix[0][0] = 1;
//        matrix[0][1] = 1;
//        matrix[0][2] = 0;
//        matrix[0][3] = 0;
//        matrix[0][4] = 0;
//        
//        matrix[1][0] = 0;
//        matrix[1][1] = 1;
//        matrix[1][2] = 1;
//        matrix[1][3] = 0;
//        matrix[1][4] = 0;
//        
//        matrix[2][0] = 0;
//        matrix[2][1] = 0;
//        matrix[2][2] = 1;
//        matrix[2][3] = 0;
//        matrix[2][4] = 1;
//        
//        matrix[3][0] = 1;
//        matrix[3][1] = 0;
//        matrix[3][2] = 0;
//        matrix[3][3] = 0;
//        matrix[3][4] = 1;
//        
//        matrix[4][0] = 0;
//        matrix[4][1] = 1;
//        matrix[4][2] = 0;
//        matrix[4][3] = 1;
//        matrix[4][4] = 1;
        
        
        // -------------- 15 
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