package testConnectedCellInnGrid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class SolutionV1 {

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
			group = 0;
		} else {
			group = cells.stream().mapToInt(Cell::getGroup).max().getAsInt();
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
	 * @param newCount
	 * @param row
	 * @param column
	 * @param cells
	 */
	private static void updateConnectedCellCount(int newCount, int row, int column, List<Cell> cells) {
		cells.stream()
		.filter(c -> c.getRow() == row && c.getColumn() == column)
		.findFirst()
		.get().setCount(newCount);
	}

	/**
	 * 
	 * @param row
	 * @param column
	 * @param cells
	 * @param newGroup
	 */
	private static void updateConnectedCellGroup(int row, int column, List<Cell> cells, int newGroup) {
		cells.stream()
		.filter(c -> c.getRow() == row && c.getColumn() == column)
		.findFirst()
		.get()
		.setGroup(newGroup);
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
		Cell cellMaxCount = null;
		
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
					List<Cell> list = cells.stream()
							.filter(c -> c.getGroup() == getGroupTo(row-1, column, cells))
							.collect(Collectors.toList());
					
					cellMaxCount = Collections.max(list, Comparator.comparing(Cell::getCount));
					
					if(countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()] <= 1) {
						cell.setGroup(getGroupTo(row-1, column, cells));
						lastTotal = countAux[row-1][column];
						
					} else {
						cell.setGroup(cellMaxCount.getGroup());
						lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
					}
				} else if(matrix[row-1][column+1] == 1) {
					List<Cell> list = cells.stream()
							.filter(c -> c.getGroup() == getGroupTo(row-1, column+1, cells))
							.collect(Collectors.toList());
					
					cellMaxCount = Collections.max(list, Comparator.comparing(Cell::getCount));
					
					if(countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()] <= 1) {
						cell.setGroup(getGroupTo(row-1, column+1, cells));
						lastTotal = countAux[row-1][column+1];
						
					} else {
						cell.setGroup(cellMaxCount.getGroup());
						lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
					}
				} else {
					cell.setGroup(getLastGroupNumber(cells)+1);
				}
			} else if(column > 0 && matrix[row][column] == 1) {
				if(matrix[row][column-1] == 1) {
					cell.setGroup(getGroupTo(row, column-1, cells));
					lastTotal = countAux[row][column-1];
					
					if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
						List<Cell> list = cells.stream()
								.filter(c -> c.getGroup() == getGroupTo(row-1, column+1, cells))
								.collect(Collectors.toList());
						
						cellMaxCount = Collections.max(list, Comparator.comparing(Cell::getCount));
						
						if(cellMaxCount.getGroup() == getGroupTo(row-1, column+1, cells)) {
							cell.setGroup(cellMaxCount.getGroup());
							lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
						} 
						
						for (int i = column; i > 0; i--) {
							if(matrix[row][column-(1*i)] == 1
									&& getGroupTo(row, column-(1*i), cells) != getGroupTo(row-1, column+1, cells)) {
								countAux[row][column-(1*i)] = lastTotal+(1+i);
								
								updateConnectedCellGroup(row, column-(1*i), cells, cell.getGroup());
								updateConnectedCellCount(lastTotal+(1+i), row, column-(1*i), cells);
								
								cell.setGroup(getGroupTo(row-1, column+1, cells));
								lastTotal = countAux[row-1][column+1];
							}
						}
					} else if(matrix[row-1][column] == 1) {
						List<Cell> list = cells.stream()
								.filter(c -> c.getGroup() == getGroupTo(row-1, column, cells))
								.collect(Collectors.toList());
						
						cellMaxCount = Collections.max(list, Comparator.comparing(Cell::getCount));
						
						if(cellMaxCount.getGroup() == getGroupTo(row-1, column, cells)) {
							cell.setGroup(cellMaxCount.getGroup());
							lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
						} 
					}
					
				} else if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
					
					if(matrix[row-1][column-1] == 1) {
						List<Cell> list = cells.stream()
								.filter(c -> c.getGroup() == getGroupTo(row-1, column-1, cells))
								.collect(Collectors.toList());
						
						cellMaxCount = Collections.max(list, Comparator.comparing(Cell::getCount));
						
						cell.setGroup(getGroupTo(row-1, column-1, cells));
						lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
						
						if(getGroupTo(row-1, column+1, cells) != getGroupTo(row-1, column-1, cells)) {
							countAux[row-1][column+1] = lastTotal+2;
							
							updateConnectedCellGroup(row-1, column+1, cells, cell.getGroup());
							updateConnectedCellCount(lastTotal+1, row-1, column+1, cells);
						}
					} else {
						List<Cell> list = cells.stream()
								.filter(c -> c.getGroup() == getGroupTo(row-1, column+1, cells))
								.collect(Collectors.toList());
						
						cellMaxCount = Collections.max(list, Comparator.comparing(Cell::getCount));
						
						if(cellMaxCount.getGroup() == getGroupTo(row-1, column+1, cells)) {
							cell.setGroup(cellMaxCount.getGroup());
							lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
						} else {
							cell.setGroup(getGroupTo(row-1, column+1, cells));
							lastTotal = countAux[row-1][column+1];
						}
					}
				} else if(matrix[row-1][column] == 1) {
					List<Cell> list = cells.stream()
							.filter(c -> c.getGroup() == getGroupTo(row-1, column, cells))
							.collect(Collectors.toList());
					
					cellMaxCount = Collections.max(list, Comparator.comparing(Cell::getCount));
					
					if(cellMaxCount.getGroup() == getGroupTo(row-1, column, cells)) {
						cell.setGroup(cellMaxCount.getGroup());
						lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
					} else {
						cell.setGroup(getGroupTo(row-1, column, cells));
						lastTotal = countAux[row-1][column];
					}
				} else if(matrix[row-1][column-1] == 1) {
					List<Cell> list = cells.stream()
							.filter(c -> c.getGroup() == getGroupTo(row-1, column-1, cells))
							.collect(Collectors.toList());
					
					cellMaxCount = Collections.max(list, Comparator.comparing(Cell::getCount));
					
					if(cellMaxCount.getGroup() == getGroupTo(row-1, column-1, cells)) {
						cell.setGroup(cellMaxCount.getGroup());
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

//    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\PROGRAMAS\\DEV\\PROJETOS\\JAVA_WORKSPACE\\tests\\test_ammo_varejo\\test_cellsingrid\\test_file.txt"));
//
//        int n = scanner.nextInt();
//        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
//
//        int m = scanner.nextInt();
//        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

    	int n = 8;
    	int m = 9;
    	
        int[][] matrix = new int[n][m];
        
        // ------------- 5
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
//        matrix[0][0] = 0;
//        matrix[0][1] = 1;
//        matrix[0][2] = 1;
//        matrix[0][3] = 1;
//        matrix[0][4] = 1;
//        
//        matrix[1][0] = 1;
//        matrix[1][1] = 0;
//        matrix[1][2] = 0;
//        matrix[1][3] = 0;
//        matrix[1][4] = 1;
//        
//        matrix[2][0] = 1;
//        matrix[2][1] = 1;
//        matrix[2][2] = 0;
//        matrix[2][3] = 1;
//        matrix[2][4] = 0;
//        
//        matrix[3][0] = 0;
//        matrix[3][1] = 1;
//        matrix[3][2] = 0;
//        matrix[3][3] = 1;
//        matrix[3][4] = 1;
//        
//        matrix[4][0] = 0;
//        matrix[4][1] = 1;
//        matrix[4][2] = 1;
//        matrix[4][3] = 1;
//        matrix[4][4] = 0;
        

        // ------------- 9
        matrix[0][0] = 1;
		matrix[0][1] = 1;
		matrix[0][2] = 1;
		matrix[0][3] = 0;
		matrix[0][4] = 1;
		
		matrix[1][0] = 0;
		matrix[1][1] = 0;
		matrix[1][2] = 1;
		matrix[1][3] = 0;
		matrix[1][4] = 0;
		  
		matrix[2][0] = 1;
		matrix[2][1] = 1;
		matrix[2][2] = 0;
		matrix[2][3] = 1;
		matrix[2][4] = 0;
		  
		matrix[3][0] = 0;
		matrix[3][1] = 1;
		matrix[3][2] = 1;
		matrix[3][3] = 0;
		matrix[3][4] = 0;
		  
		matrix[4][0] = 0;
		matrix[4][1] = 0;
		matrix[4][2] = 0;
		matrix[4][3] = 0;
		matrix[4][4] = 0;
		 
		matrix[5][0] = 0;
		matrix[5][1] = 1;
		matrix[5][2] = 0;
		matrix[5][3] = 0;
		matrix[5][4] = 0;
		 
		matrix[6][0] = 0;
		matrix[6][1] = 0;
		matrix[6][2] = 1;
		matrix[6][3] = 1;
		matrix[6][4] = 0;
      
        // ------------ 29
//        matrix[0][0] = 0;
//		matrix[0][1] = 1;
//		matrix[0][2] = 0;
//		matrix[0][3] = 0;
//		matrix[0][4] = 0;
//		matrix[0][5] = 0;
//		matrix[0][6] = 1;
//		matrix[0][7] = 1;
//		matrix[0][8] = 0;
//		
//		matrix[1][0] = 1;
//		matrix[1][1] = 1;
//		matrix[1][2] = 0;
//		matrix[1][3] = 0;
//		matrix[1][4] = 1;
//		matrix[1][5] = 0;
//		matrix[1][6] = 0;
//		matrix[1][7] = 0;
//		matrix[1][8] = 1;
//		  
//		matrix[2][0] = 0;
//		matrix[2][1] = 0;
//		matrix[2][2] = 0;
//		matrix[2][3] = 0;
//		matrix[2][4] = 1;
//		matrix[2][5] = 0;
//		matrix[2][6] = 1;
//		matrix[2][7] = 0;
//		matrix[2][8] = 0;
//		  
//		matrix[3][0] = 0;
//		matrix[3][1] = 1;
//		matrix[3][2] = 1;
//		matrix[3][3] = 1;
//		matrix[3][4] = 0;
//		matrix[3][5] = 1;
//		matrix[3][6] = 0;
//		matrix[3][7] = 1;
//		matrix[3][8] = 1;
//		  
//		matrix[4][0] = 0;
//		matrix[4][1] = 1;
//		matrix[4][2] = 1;
//		matrix[4][3] = 1;
//		matrix[4][4] = 0;
//		matrix[4][5] = 0;
//		matrix[4][6] = 1;
//		matrix[4][7] = 1;
//		matrix[4][8] = 0;
//		 
//		matrix[5][0] = 0;
//		matrix[5][1] = 1;
//		matrix[5][2] = 0;
//		matrix[5][3] = 1;
//		matrix[5][4] = 1;
//		matrix[5][5] = 0;
//		matrix[5][6] = 1;
//		matrix[5][7] = 1;
//		matrix[5][8] = 0;
//		 
//		matrix[6][0] = 0;
//		matrix[6][1] = 1;
//		matrix[6][2] = 0;
//		matrix[6][3] = 0;
//		matrix[6][4] = 1;
//		matrix[6][5] = 1;
//		matrix[6][6] = 0;
//		matrix[6][7] = 1;
//		matrix[6][8] = 1;
//		
//		matrix[7][0] = 1;
//		matrix[7][1] = 0;
//		matrix[7][2] = 1;
//		matrix[7][3] = 1;
//		matrix[7][4] = 1;
//		matrix[7][5] = 1;
//		matrix[7][6] = 0;
//		matrix[7][7] = 0;
//		matrix[7][8] = 0;
        
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