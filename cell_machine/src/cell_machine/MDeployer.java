package cell_machine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;



public class MDeployer {
	protected MCell[][] cells;
	protected int width, height;// not the pixel width/height
	private boolean[][] currentStates;
	
	
	public MDeployer(int height, int width, int GRIDSIZE) {
		
		this.width = width;
		this.height = height;
		cells = new MCell[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j ++){
				cells[i][j] = new MCell();
			}
		}
		
		currentStates = new boolean[height][width];
		setCurrenStates();
		
		System.out.println("This is contructor:\nWidth = " + width + "\nHeight = " + height);

	}
	
	public void initilize(int height, int width) {
		this.width = width;
		this.height = height;
	}
	
	public void setSize(int height, int width) {
		System.out.println("Calling deployer.setSize()...");
		MCell[][] oldCells = cells;
		cells = new MCell[height][width];
		currentStates = new boolean[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j ++){
				cells[i][j] = new MCell();
				if(i < this.height && j < this.width) {
					cells[i][j].setState(oldCells[i][j].isAlive());
				}
			}
		}
		this.width = width;
		this.height = height;
	}
	
	
	private void setCurrenStates() {
		for ( int row = 0; row < height; row++ ) {
			for ( int col = 0; col < width; col++ ) {
				currentStates[row][col] = cells[row][col].isAlive();
			}
		}
	}
	
	public void setCells(MCell[][] cells) {
		if(cells != null) {
			//MCell[][] oldCells = this.cells;
			
			
			this.cells = cells;
			height = cells.length;
			width = cells[0].length;
			currentStates = new boolean[height][width];
			setCurrenStates();
		}
		else {
			System.out.println("MDepoyer.setCells() received null MCell[][]");
		}
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean[][] getState(){
		return currentStates;
	}
	
	public MCell getCellS(int x, int y) {// get the cell at (x, y) on screen
		return cells[y / MFrame.GRIDSIZE][x / MFrame.GRIDSIZE]; 
	}
	
	public MCell getCell(int row, int col) { return cells[row][col]; }
	
	public MCell[][] getCells() { return cells; }
	
	public MCell[] getNeighbors(int x, int y) {
		ArrayList<MCell> list = new ArrayList<MCell>();
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				int x1 = x + i, y1 = y + j;
				if(!(x1 < 0 || x1 >= height || y1 < 0 || y1 >= width || i == 0 & j == 0)) {
					list.add(cells[x1][y1]);
				}
			}
		}
		return list.toArray(new MCell[list.size()]);
	}
	
	public void paintAll(Graphics g) {
		//System.out.println("Calling paintAll()");
		g.setColor(Color.BLACK);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				cells[i][j].drawSquare(g, j * MFrame.GRIDSIZE, i * MFrame.GRIDSIZE, MFrame.GRIDSIZE);
			}
		}
		g.setColor(Color.GRAY);
		for(int i = 0; i < height + 1; i++) {
			
			g.drawLine(0, i * MFrame.GRIDSIZE, width * MFrame.GRIDSIZE, i * MFrame.GRIDSIZE);
		}
		for(int i = 0; i < width + 1; i++) {
			g.drawLine(i * MFrame.GRIDSIZE, 0, i * MFrame.GRIDSIZE,  height * MFrame.GRIDSIZE);
		}
	}
	
	public void setAlive(int row, int col) {
		cells[row][col].setAlive();
		currentStates[row][col] = true;
	}
	
	public void setDead(int row, int col) {
		cells[row][col].setDead();
		currentStates[row][col] = false;
	}
	
	public void setAllDead() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				cells[i][j].setDead();
				currentStates[i][j] = false;
			}
		}
	}
	
	public void reverse(int x, int y) {
		System.out.println("Calling MDeployer.reverse(), cells[" 
			+ y/MFrame.GRIDSIZE+ "][" + x/MFrame.GRIDSIZE + "]");
		if(cells[y / MFrame.GRIDSIZE][x / MFrame.GRIDSIZE].isAlive()) {
			cells[y / MFrame.GRIDSIZE][x / MFrame.GRIDSIZE].setDead();
			currentStates[y / MFrame.GRIDSIZE][x / MFrame.GRIDSIZE] = false;
		}
		else {
			cells[y / MFrame.GRIDSIZE][x / MFrame.GRIDSIZE].setAlive();
			currentStates[y / MFrame.GRIDSIZE][x / MFrame.GRIDSIZE] = true;
		}
		
	}
	
	public void highlightSelectedCells(int[] coordinates) {
		int row1 = coordinates[1] / MFrame.GRIDSIZE;
		int col1 = coordinates[0] / MFrame.GRIDSIZE;
		int row2 = coordinates[3] / MFrame.GRIDSIZE;
		int col2 = coordinates[2] / MFrame.GRIDSIZE;
		for(int row = row1; row <= row2; row++) {
			for(int col = col1; col <= col2; col++) {
				if(cells[row][col].isAlive()) {
					cells[row][col].setColor(Color.GREEN);
				}
			}
		}
	}
	
	public void stopHighlightSelectedCells(int[] coordinates) {
		int row1 = coordinates[1] / MFrame.GRIDSIZE;
		int col1 = coordinates[0] / MFrame.GRIDSIZE;
		int row2 = coordinates[3] / MFrame.GRIDSIZE;
		int col2 = coordinates[2] / MFrame.GRIDSIZE;
		row2 = row2 >= height ? height - 1 : row2;// incase the height and width of deployer has been changed
		col2 = col2 >= width ? width - 1 : col2;
		
		for(int row = row1; row <= row2; row++) {
			for(int col = col1; col <= col2; col++) {
				cells[row][col].setColor(Color.BLACK);
			}
		}
	}
	
	public void deleteSelectedCells(int[] coordinates) {
		int row1 = coordinates[1] / MFrame.GRIDSIZE;
		int col1 = coordinates[0] / MFrame.GRIDSIZE;
		int row2 = coordinates[3] / MFrame.GRIDSIZE;
		int col2 = coordinates[2] / MFrame.GRIDSIZE;
		row2 = row2 >= height ? height - 1 : row2;
		col2 = col2 >= width ? width - 1 : col2;
		
		for(int row = row1; row <= row2; row++) {
			for(int col = col1; col <= col2; col++) {
				if(cells[row][col].isAlive()) {
					cells[row][col].setDead();
				}
			}
		}
	}
	
	public MCell[][] getSelectedAreaCells(int[] coordinates) {
		int row1 = coordinates[1] / MFrame.GRIDSIZE;
		int col1 = coordinates[0] / MFrame.GRIDSIZE;
		int row2 = coordinates[3] / MFrame.GRIDSIZE;
		int col2 = coordinates[2] / MFrame.GRIDSIZE;
		MCell[][] selectedCells = new MCell[row2 - row1 + 1][col2 - col1 + 1];
		
		for(int row = 0; row <= row2 - row1; row++) {
			for(int col = 0; col <= col2 - col1; col++) {
				selectedCells[row][col] = new MCell();
				selectedCells[row][col].setState(cells[row + row1][col + col1].isAlive());
			}
		}
		
		return selectedCells;
	}
	
	public void addCells(MCell[][] addedCells, int[] coordinates) {
		int selectedRow = coordinates[1] / MFrame.GRIDSIZE;
		int selectedCol = coordinates[0] / MFrame.GRIDSIZE;
		int addedCellsHeight = addedCells.length;
		int addedCellsWidth = addedCells[0].length;
		int originRow = selectedRow - addedCellsHeight / 2;
		int originCol = selectedCol - addedCellsWidth / 2;
		int endRow = originRow + addedCellsHeight;
		int endCol = originCol + addedCellsWidth;

		for(int row = originRow; row < endRow; row++) {
			for(int col = originCol; col < endCol; col++) {
				if(row >= 0 && row < height && col >= 0 && col < width) {
					if(addedCells[row - originRow][col - originCol].isAlive())
					cells[row][col].setAlive();
				}
			}
		}
	
		
	}
	
	public void highlightCell(int[] coordinates) {
		int row = coordinates[1] / MFrame.GRIDSIZE;
		int col = coordinates[0] / MFrame.GRIDSIZE;
		cells[row][col].showSelf(Color.PINK);
	}
	
	public void stopHighlightCell(int[] coordinates) {
		int row = coordinates[1] / MFrame.GRIDSIZE;
		int col = coordinates[0] / MFrame.GRIDSIZE;
		cells[row][col].stopShowSelf();
	}
}
