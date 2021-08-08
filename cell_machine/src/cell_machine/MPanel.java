package cell_machine;

import java.awt.Graphics;

import java.io.File;
import java.io.InputStream;

import javax.swing.JPanel;

import tools.MMouseFollower;
import tools.MThread;
import tools.MTransformer;

@SuppressWarnings("unused")
public class MPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MDeployer deployer;
	@SuppressWarnings("unused")
	private int width, height;
	private boolean[][] originalState;
	private boolean[][] copiedCells;
	private MThread windowThread;
	@SuppressWarnings("unused")
	private int sleepMicroTime;
	private MMouseFollower mouseFollower;
	@SuppressWarnings("unused")
	private boolean isSelectingACell;
	private MCell[][] moduleCells;

	public MPanel(int width, int height, int gridSize, MFrame frame) {
		windowThread = new MThread(this, frame);
		setSize(width, height);
		int gridHeight = height / gridSize;
		int gridWidth = width / gridSize;
		deployer = new MDeployer(gridHeight, gridWidth, gridSize);
		originalState = new boolean[gridHeight][gridWidth];
		mouseFollower = new MMouseFollower();
		
		@SuppressWarnings("unused")
		MCell[][] cells = deployer.getCells();
	}
	
	
	public void reverse(int x, int y) {
		deployer.reverse(x, y);
		originalState[y / MFrame.GRIDSIZE][x / MFrame.GRIDSIZE] = 
				originalState[y / MFrame.GRIDSIZE][x / MFrame.GRIDSIZE] 
				== true ? false : true;
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		deployer.paintAll(g);
		mouseFollower.paintAll(g);
	}
	
	public void nextStep() {
		System.out.println("Calling nextStep");
		int gridWidth = deployer.getWidth();
		int gridHeight = deployer.getHeight();
		boolean[][] oldStates = new boolean[gridHeight][gridWidth];
		MCell[][] cells = deployer.getCells();
		for ( int row = 0; row < gridHeight; row++ ) {
			for ( int col = 0; col < gridWidth; col++ ) {
				oldStates[row][col] = cells[row][col].isAlive();
			}
		}
		for ( int row = 0; row < gridHeight; row++ ) {
			for ( int col = 0; col < gridWidth; col++ ) {
				
				MCell cell = cells[row][col];
				int cnt = 0;
				for(int i = -1; i < 2; i++) {
					for(int j = -1; j < 2; j++) {
						int x1 = row + i, y1 = col + j;
						if(!(x1 < 0 || x1 >= gridHeight || y1 < 0 
								|| y1 >= gridWidth || i == 0 && j == 0)) {
							if(oldStates[x1][y1] == true) {
								cnt++;
							}
						}
					}
				}
				if ( cell.isAlive() ) {
					if ( cnt <2 || cnt >3 ) {
						deployer.setDead(row, col);
					}
				}
				else if ( cnt == 3 ) {
					deployer.setAlive(row, col);
				}
			}
		}
		
	}
	
	public void clearAll() {
		deployer.setAllDead();
	}
	
	public void setAlive(int x, int y) {
		int gridHeight = y / MFrame.GRIDSIZE;
		int gridWidth = x / MFrame.GRIDSIZE;
		System.out.println("Set cells[" + gridHeight 
				+ "][" + gridWidth + "] alive\n" );
		deployer.setAlive(gridHeight, gridWidth);
		originalState[gridHeight][gridWidth] = true;
	}
	
	public void setDead(int x, int y) {
		int gridHeight = y / MFrame.GRIDSIZE;
		int gridWidth = x / MFrame.GRIDSIZE;
		System.out.println("Set cells[" + gridHeight 
				+ "][" + gridWidth + "] alive\n" );
		deployer.setDead(gridHeight, gridWidth);
		originalState[gridHeight][gridWidth] = false;
	}
	
	public void setGridSize(int gridHeight, int gridWidth) {
		deployer.setSize(gridHeight, gridWidth);
		originalState = new boolean[gridHeight][gridWidth];
		mouseFollower.setSize(gridHeight * MFrame.GRIDSIZE, gridWidth * MFrame.GRIDSIZE);
	}
	
	
	public void saveState() {// save the current state
		System.out.println("Calling saveState()...");
		int gridHeight = deployer.getHeight();
		int gridWidth = deployer.getWidth();
		MCell[][] cells = deployer.getCells();
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				originalState[i][j] = cells[i][j].isAlive();
			}
		}
	}
	
	public void loadState() {// load the state has been saved
		int gridHeight = deployer.getHeight();
		int gridWidth = deployer.getWidth();
		MCell[][] cells = deployer.getCells();
		boolean[][] states = deployer.getState();
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				 cells[i][j].setState(originalState[i][j]);
				 states[i][j] = originalState[i][j];
			}
		}
	}
	
	public void setSleepMicroTime(int sleepMicroTime) {
		this.sleepMicroTime = sleepMicroTime;
		windowThread.setSleepMicroTime(sleepMicroTime);
	}
	
	public void runThread(MFrame frame) {
		
		windowThread.start();
		
	}
	
	public void stopThread() {
		windowThread.setStop();
	}
	
	public void setRefreshTime(int time) {
		windowThread.setSleepMicroTime(time);
	}

	public MCell[][] getCells(){
		return deployer.getCells();
	}

	public void setCells(MCell[][] cells) {
		// TODO Auto-generated method stub
		deployer.setCells(cells);
	}
	
	public void mouseOrigin(int x, int y) {
		mouseFollower.setOrigin(x, y);
	}
	
	public void mouseEnd(int x, int y) {
		mouseFollower.setEnd(x, y);
	}
	
	public void mouseSelectingNode(int x, int y) {
		mouseFollower.setCurrentLocation(x, y);
	}
	
	public void showSelected() {
		int[] coordinates = new int[4];
		coordinates = mouseFollower.getSelectedArea();
		deployer.highlightSelectedCells(coordinates);
	}
	
	public void stopShowSelected() {
		
		int[] coordinates = new int[4];
		coordinates = mouseFollower.getSelectedArea();
		deployer.stopHighlightSelectedCells(coordinates);
	}
	
	public void deleteSelectedCells() {
		System.out.println("Pressed delete or backspace");
		int[] coordinates = new int[4];
		coordinates = mouseFollower.getSelectedArea();
		deployer.deleteSelectedCells(coordinates);
	}
	
	public void copySelectedCells() {
		System.out.println("Pressed c");
		int[] coordinates = new int[4];
		coordinates = mouseFollower.getSelectedArea();
		boolean[][] cells = MTransformer.cellsToBooleanArray(
				deployer.getSelectedAreaCells(coordinates));
		copiedCells = cells;
	}
	
	public void pasteSelectedCells() {
		System.out.println("Pressed v");
		int[] coordinates = new int[2];
		coordinates = mouseFollower.getOrigin();
		MCell[][] cells = MTransformer.booleanArrayToCells(copiedCells);
		deployer.addCells(cells, coordinates);
	}
	
	public void showSelectedCell() {
		int[] coordinates = new int[2];
		coordinates = mouseFollower.getOrigin();
		deployer.highlightCell(coordinates);
	}
	
	public void stopShowSelectedCell() {
		int[] coordinates = new int[2];
		coordinates = mouseFollower.getOrigin();
		deployer.stopHighlightCell(coordinates);
	}
	
	public void loadModule(String location) {
//		File f = MTransformer.stringToFile(location);
//		moduleCells = MTransformer.fileToCells(f);
		MTransformer transformer = new MTransformer();
		InputStream is = transformer.stringToInputStream(location);
		moduleCells = MTransformer.inputStreamToCells(is);
	}
	
	public void addModule() {
		int[] coordinates = new int[2];
		coordinates = mouseFollower.getOrigin();
		deployer.addCells(moduleCells, coordinates);
		moduleCells = null;
	}
	
}
