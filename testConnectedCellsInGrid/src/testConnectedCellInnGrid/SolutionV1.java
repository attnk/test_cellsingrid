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
	 * @param row
	 * @param column
	 * @param cells
	 * @return
	 */
	private static Cell getMaxClountCell(int row, int column, List<Cell> cells) {
		Cell cellMaxCount = null;
		
		List<Cell> list = cells.stream()
				.filter(c -> c.getGroup() == getGroupTo(row, column, cells))
				.collect(Collectors.toList());
		
		try {
			cellMaxCount = Collections.max(list, Comparator.comparing(Cell::getCount));
		} catch (ClassCastException | NoSuchElementException e) {
			cellMaxCount = cells.stream()
					.filter(c -> c.getRow() == row && c.getColumn() == column)
					.findFirst().get();
		}
		
		return cellMaxCount;
	}

	/**
	 * 
	 * @param countAux
	 * @param row
	 * @param column
	 * @param cells
	 * @param currentCell
	 * @param cellMaxCount
	 * @return
	 */
	private static int getLastTotal(
			int[][] countAux, 
			int row, 
			int column, 
			List<Cell> cells, 
			Cell currentCell,
			Cell cellMaxCount) {
		int lastTotal;
		
		if(countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()] <= 1) {
			currentCell.setGroup(getGroupTo(row, column, cells));
			lastTotal = countAux[row][column];
			
		} else {
			currentCell.setGroup(cellMaxCount.getGroup());
			lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
		}
		
		return lastTotal;
	}
	
	/**
	 * 
	 * @param countAux
	 * @param row
	 * @param column
	 * @param cells
	 * @param cell
	 * @param lastTotal
	 */
	private static void updateRightUpDiagonalConnectedCell(
			int[][] countAux, 
			int row, 
			int column, 
			List<Cell> cells, 
			Cell cell,
			int lastTotal) {
		
		if(getGroupTo(cell.getRow()-1, cell.getColumn()+1, cells) != getGroupTo(row, column, cells)) {
			countAux[cell.getRow()-1][cell.getColumn()+1] = lastTotal+2;
			
			updateConnectedCellGroup(cell.getRow()-1, cell.getColumn()+1, cells, cell.getGroup());
			updateConnectedCellCount(lastTotal+1, cell.getRow()-1, cell.getColumn()+1, cells);
		}
	}

	/**
	 * 
	 * @param matrix
	 * @param countAux
	 * @param row
	 * @param column
	 * @param cells
	 * @param cell
	 * @param lastTotal
	 */
	private static void updateLeftConnectedCells(
			int[][] matrix, 
			int[][] countAux, 
			int row, 
			int column,
			List<Cell> cells, 
			Cell cell, 
			int lastTotal) {
		
		for (int i = cell.getColumn(); i > 0; i--) {
			if(matrix[cell.getRow()][cell.getColumn()-(1*i)] == 1
					&& getGroupTo(cell.getRow(), cell.getColumn()-(1*i), cells) != getGroupTo(row, column, cells)) {
				
				countAux[cell.getRow()][cell.getColumn()-(1*i)] = lastTotal+(1+i);
				
				updateConnectedCellGroup(cell.getRow(), cell.getColumn()-(1*i), cells, cell.getGroup());
				updateConnectedCellCount(lastTotal+(1+i), cell.getRow(), cell.getColumn()-(1*i), cells);
				
				cell.setGroup(getGroupTo(row, column, cells));
				lastTotal = countAux[row][column];
			}
		}
	}

	/**
	 * 
	 * @param countAux
	 * @param cells
	 * @param cell
	 * @return
	 */
	private static int processLeftUpDiagonalConnectedCellLastTotal(
			int[][] countAux, 
			List<Cell> cells, 
			Cell cell) {
		
		int lastTotal;
		int row = cell.getRow();
		int column = cell.getColumn();
		Cell cellMaxCount = getMaxClountCell(row-1, column-1, cells);
		
		if(cellMaxCount.getGroup() == getGroupTo(row-1, column-1, cells)) {
			cell.setGroup(cellMaxCount.getGroup());
			lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
			
		} else {
			cell.setGroup(getGroupTo(row-1, column-1, cells));
			lastTotal = countAux[row-1][column-1];
		}
		return lastTotal;
	}

	/**
	 * 
	 * @param countAux
	 * @param cells
	 * @param cell
	 * @return
	 */
	private static int processUpConnectedCellLastTotal(
			int[][] countAux, 
			List<Cell> cells,
			Cell cell) {
		
		int lastTotal;
		Cell cellMaxCount;
		int row = cell.getRow(); 
		int column = cell.getColumn();
		
		cellMaxCount = getMaxClountCell(row-1, column, cells);
		
		if(cellMaxCount.getGroup() == getGroupTo(row-1, column, cells)) {
			cell.setGroup(cellMaxCount.getGroup());
			lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
			
		} else {
			cell.setGroup(getGroupTo(row-1, column, cells));
			lastTotal = countAux[row-1][column];
		}
		return lastTotal;
	}

	/**
	 * 
	 * @param matrix
	 * @param countAux
	 * @param cells
	 * @param cell
	 * @return
	 */
	private static int processRightUpDiagonalConnectedCellLastTotal(
			int[][] matrix, 
			int[][] countAux, 
			List<Cell> cells, 
			Cell cell) {
		
		int lastTotal;
		Cell cellMaxCount;
		int row = cell.getRow();
		int column = cell.getColumn();
		
		if(matrix[row-1][column-1] == 1) {
			cellMaxCount = getMaxClountCell(row-1, column-1, cells);
			
			cell.setGroup(getGroupTo(row-1, column-1, cells));
			lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
			
			updateRightUpDiagonalConnectedCell(countAux, row-1, column-1, cells, cell, lastTotal);
			
		} else {
			cellMaxCount = getMaxClountCell(row-1, column+1, cells);
			
			if(cellMaxCount.getGroup() == getGroupTo(row-1, column+1, cells)) {
				cell.setGroup(cellMaxCount.getGroup());
				lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
				
			} else {
				cell.setGroup(getGroupTo(row-1, column+1, cells));
				lastTotal = countAux[row-1][column+1];
			}
		}
		return lastTotal;
	}

	/**
	 * 
	 * @param matrix
	 * @param countAux
	 * @param cells
	 * @param maxColumn
	 * @param cell
	 * @return
	 */
	private static int processLeftConnectedCellLastTotal(
			int[][] matrix, 
			int[][] countAux, 
			List<Cell> cells, 
			int maxColumn, 
			Cell cell) {
		
		int lastTotal;
		Cell cellMaxCount;
		int row = cell.getRow(); 
		int column =  cell.getColumn();
		
		cell.setGroup(getGroupTo(row, column-1, cells));
		lastTotal = countAux[row][column-1];
		
		if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
			cellMaxCount = getMaxClountCell(row-1, column+1, cells);
			
			if(cellMaxCount.getGroup() == getGroupTo(row-1, column+1, cells)) {
				cell.setGroup(cellMaxCount.getGroup());
				lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
			}
			
			updateLeftConnectedCells(matrix, countAux, row-1, column+1, cells, cell, lastTotal);
			
		} else if(matrix[row-1][column] == 1) {
			cellMaxCount = getMaxClountCell(row-1, column, cells);
			
			if(cellMaxCount.getGroup() == getGroupTo(row-1, column, cells)) {
				cell.setGroup(cellMaxCount.getGroup());
				lastTotal = countAux[cellMaxCount.getRow()][cellMaxCount.getColumn()];
			} 
		}
		return lastTotal;
	}

	/**
	 * 
	 * @param matrix
	 * @param countAux
	 * @param cells
	 * @param cell
	 * @return
	 */
	private static int getFirstColumnLastTotal(
			int[][] matrix, 
			int[][] countAux, 
			List<Cell> cells,
			Cell cell) {
		
		int lastTotal = 0;
		Cell cellMaxCount;
		int row = cell.getRow(); 
		int column = cell.getColumn();
		
		if(matrix[row-1][column] == 1) {
			cellMaxCount = getMaxClountCell(row-1, column, cells);
			lastTotal = getLastTotal(countAux, row-1, column, cells, cell, cellMaxCount);
			
		} else if(matrix[row-1][column+1] == 1) {
			cellMaxCount = getMaxClountCell(row-1, column+1, cells);
			lastTotal = getLastTotal(countAux, row-1, column+1, cells, cell, cellMaxCount);
			
		} else {
			cell.setGroup(getLastGroupNumber(cells)+1);
		}
		
		return lastTotal;
	}

	/**
	 * 
	 * @param matrix
	 * @param countAux
	 * @param cells
	 * @param cell
	 * @return
	 */
	private static int getFirstRowLastTotal(
			int[][] matrix, 
			int[][] countAux, 
			List<Cell> cells,
			Cell cell) {
		
		int lastTotal = 0;
		int row = cell.getRow();
		int column = cell.getColumn();
		
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
		
		return lastTotal;
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
			lastTotal = getFirstRowLastTotal(matrix, countAux, cells, cell);
			
		} else if(row > 0) {
			if(column == 0 && matrix[row][column] == 1) {
				lastTotal = getFirstColumnLastTotal(matrix, countAux, cells, cell);
				
			} else if(column > 0 && matrix[row][column] == 1) {
				if(matrix[row][column-1] == 1) {
					lastTotal = processLeftConnectedCellLastTotal(
							matrix, countAux, cells, maxColumn,cell);
					
				} else if(column < maxColumn-1 && matrix[row-1][column+1] == 1) {
					lastTotal = processRightUpDiagonalConnectedCellLastTotal(
							matrix, countAux, cells, cell);
					
				} else if(matrix[row-1][column] == 1) {
					lastTotal = processUpConnectedCellLastTotal(countAux, cells, cell);
					
				} else if(matrix[row-1][column-1] == 1) {
					lastTotal = processLeftUpDiagonalConnectedCellLastTotal(countAux, cells, cell);
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


    public static void main(String[] args) throws IOException {

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
//        matrix[0][0] = 1;
//		matrix[0][1] = 1;
//		matrix[0][2] = 1;
//		matrix[0][3] = 0;
//		matrix[0][4] = 1;
//		
//		matrix[1][0] = 0;
//		matrix[1][1] = 0;
//		matrix[1][2] = 1;
//		matrix[1][3] = 0;
//		matrix[1][4] = 0;
//		  
//		matrix[2][0] = 1;
//		matrix[2][1] = 1;
//		matrix[2][2] = 0;
//		matrix[2][3] = 1;
//		matrix[2][4] = 0;
//		  
//		matrix[3][0] = 0;
//		matrix[3][1] = 1;
//		matrix[3][2] = 1;
//		matrix[3][3] = 0;
//		matrix[3][4] = 0;
//		  
//		matrix[4][0] = 0;
//		matrix[4][1] = 0;
//		matrix[4][2] = 0;
//		matrix[4][3] = 0;
//		matrix[4][4] = 0;
//		 
//		matrix[5][0] = 0;
//		matrix[5][1] = 1;
//		matrix[5][2] = 0;
//		matrix[5][3] = 0;
//		matrix[5][4] = 0;
//		 
//		matrix[6][0] = 0;
//		matrix[6][1] = 0;
//		matrix[6][2] = 1;
//		matrix[6][3] = 1;
//		matrix[6][4] = 0;
      
        // ------------ 29
        matrix[0][0] = 0;
		matrix[0][1] = 1;
		matrix[0][2] = 0;
		matrix[0][3] = 0;
		matrix[0][4] = 0;
		matrix[0][5] = 0;
		matrix[0][6] = 1;
		matrix[0][7] = 1;
		matrix[0][8] = 0;
		
		matrix[1][0] = 1;
		matrix[1][1] = 1;
		matrix[1][2] = 0;
		matrix[1][3] = 0;
		matrix[1][4] = 1;
		matrix[1][5] = 0;
		matrix[1][6] = 0;
		matrix[1][7] = 0;
		matrix[1][8] = 1;
		  
		matrix[2][0] = 0;
		matrix[2][1] = 0;
		matrix[2][2] = 0;
		matrix[2][3] = 0;
		matrix[2][4] = 1;
		matrix[2][5] = 0;
		matrix[2][6] = 1;
		matrix[2][7] = 0;
		matrix[2][8] = 0;
		  
		matrix[3][0] = 0;
		matrix[3][1] = 1;
		matrix[3][2] = 1;
		matrix[3][3] = 1;
		matrix[3][4] = 0;
		matrix[3][5] = 1;
		matrix[3][6] = 0;
		matrix[3][7] = 1;
		matrix[3][8] = 1;
		  
		matrix[4][0] = 0;
		matrix[4][1] = 1;
		matrix[4][2] = 1;
		matrix[4][3] = 1;
		matrix[4][4] = 0;
		matrix[4][5] = 0;
		matrix[4][6] = 1;
		matrix[4][7] = 1;
		matrix[4][8] = 0;
		 
		matrix[5][0] = 0;
		matrix[5][1] = 1;
		matrix[5][2] = 0;
		matrix[5][3] = 1;
		matrix[5][4] = 1;
		matrix[5][5] = 0;
		matrix[5][6] = 1;
		matrix[5][7] = 1;
		matrix[5][8] = 0;
		 
		matrix[6][0] = 0;
		matrix[6][1] = 1;
		matrix[6][2] = 0;
		matrix[6][3] = 0;
		matrix[6][4] = 1;
		matrix[6][5] = 1;
		matrix[6][6] = 0;
		matrix[6][7] = 1;
		matrix[6][8] = 1;
		
		matrix[7][0] = 1;
		matrix[7][1] = 0;
		matrix[7][2] = 1;
		matrix[7][3] = 1;
		matrix[7][4] = 1;
		matrix[7][5] = 1;
		matrix[7][6] = 0;
		matrix[7][7] = 0;
		matrix[7][8] = 0;
        
        // só para print da matriz usada
        for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j]);
				System.out.print(" | ");
			}
			System.out.println();
		}

    	System.out.println("\n");
        
        int result = connectedCell(matrix);

        System.out.println(result);
    }
}