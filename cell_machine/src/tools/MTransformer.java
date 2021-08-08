package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import cell_machine.MCell;

public class MTransformer {
	@SuppressWarnings("unused")
	private final static int MAXINT = (1 << 31) - 1;
	
	public static MCell[][] fileToCells(File file){
		MCell[][] cells = null;
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String str = null;
			ArrayList<Integer> rows= new ArrayList<Integer>();
			ArrayList<Integer> cols = new ArrayList<Integer>();
			int gridHeight = 0, gridWidth = 0;
			//ArrayList<Boolean> states = new ArrayList<Boolean>();
			int row, col;
			if((str = br.readLine()) != null) {
				String[] paras = str.split(" ");
				gridHeight = Integer.valueOf(paras[0]);
				gridWidth = Integer.valueOf(paras[1]);
			}
			
			while((str = br.readLine()) != null) {
				String[] paras = str.split(" ");
				row = Integer.valueOf(paras[0]);
				col = Integer.valueOf(paras[1]);
				if(row < gridHeight && row >= 0 // filter for illegal input.
						&& col >= 0 && col < gridWidth) {
					rows.add(row);
					cols.add(col);
				}
			}
			// I am not sure about how to close the file.
			br.close();
			fr.close();
			cells = new MCell[gridHeight][gridWidth];
			for(row = 0; row < gridHeight; row++) {
				for(col = 0; col < gridWidth; col++) {
					cells[row][col] = new MCell();
				}
			}
			
			for(int i = 0; i < rows.size(); i++) {
				row = rows.get(i);
				col = cols.get(i);
				cells[row][col].setAlive();
				System.out.println("Input cells[" + row + "][" + col + "]");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception in MTransformer.fileToCells()");
		}

		return cells;
	}
	
	public static MCell[][] inputStreamToCells(InputStream fr){
		MCell[][] cells = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(fr));
			String str = null;
			ArrayList<Integer> rows= new ArrayList<Integer>();
			ArrayList<Integer> cols = new ArrayList<Integer>();
			int gridHeight = 0, gridWidth = 0;
			//ArrayList<Boolean> states = new ArrayList<Boolean>();
			int row, col;
			if((str = br.readLine()) != null) {
				String[] paras = str.split(" ");
				gridHeight = Integer.valueOf(paras[0]);
				gridWidth = Integer.valueOf(paras[1]);
			}
			
			while((str = br.readLine()) != null) {
				String[] paras = str.split(" ");
				row = Integer.valueOf(paras[0]);
				col = Integer.valueOf(paras[1]);
				if(row < gridHeight && row >= 0 // filter for illegal input.
						&& col >= 0 && col < gridWidth) {
					rows.add(row);
					cols.add(col);
				}
			}
			// I am not sure about how to close the file.
			br.close();
			fr.close();
			cells = new MCell[gridHeight][gridWidth];
			for(row = 0; row < gridHeight; row++) {
				for(col = 0; col < gridWidth; col++) {
					cells[row][col] = new MCell();
				}
			}
			
			for(int i = 0; i < rows.size(); i++) {
				row = rows.get(i);
				col = cols.get(i);
				cells[row][col].setAlive();
				System.out.println("Input cells[" + row + "][" + col + "]");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception in MTransformer.inputStreamToCells()");
		}

		return cells;
	}
	
	public static void cellsToFile(MCell[][] cells, File file) {
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			int gridHeight = cells.length;
			int gridWidth = cells[0].length;
			bw.write(gridHeight + " " + gridWidth + "\n");
			for(int row = 0; row < gridHeight; row++) {
				for(int col = 0; col < gridWidth; col++) {
					if(cells[row][col].isAlive()) {
						bw.write(row + " " + col + "\n");
					}
				}
			}
			bw.close();
			fw.close();
			
		}
		catch(Exception e) {
			System.out.println("Exception in MTransformer.cellsToFile()");
		}
	}
	
	public static boolean[][] cellsToBooleanArray(MCell[][] cells) {
		boolean[][] booleanCells;
		int gridHeight = cells.length;
		int gridWidth = cells[0].length;
		booleanCells = new boolean[gridHeight][gridWidth];
		for(int row = 0; row < gridHeight; row++) {
			for(int col = 0; col < gridWidth; col++) {
				booleanCells[row][col] = cells[row][col].isAlive();
			}
		}
		
		return booleanCells;
	}
	
	public static MCell[][] booleanArrayToCells(boolean[][] booleanCells){
		MCell[][] cells = null;
		int gridHeight = booleanCells.length;
		int gridWidth = booleanCells[0].length;
		cells = new MCell[gridHeight][gridWidth];
		for(int row = 0; row < gridHeight; row++) {
			for(int col = 0; col < gridWidth; col++) {
				cells[row][col] = new MCell();
				cells[row][col].setState(booleanCells[row][col]);
			}
		}
		return cells;
	}
	
	public static File stringToFile(String location) {
		File f = new File(location);
		return f;
	}
	
	public InputStream stringToInputStream(String location) {
		InputStream is = this.getClass().getResourceAsStream(location);
		return is;
	}
	
	
	
}
