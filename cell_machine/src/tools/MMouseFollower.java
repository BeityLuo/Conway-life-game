package tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MMouseFollower {
	private int originX, originY;
	private int currentX, currentY;
	private int endX, endY;
	
	private final int SELECTING = 0, END = 1;
	private int mouseState;
	
	public MMouseFollower() {
		
	}
	
	public void setSize(int height, int width) {
		endX = endX >= width ? width : endX;
		endY = endY >= height ? height : endY;
		if(originX >= height || originY >= width) {
			originX = originY = 0;
			endX = 0;
			endY = 0;
		}
		
	}
	
	public void setOrigin(int x, int y) {
		originX = x;
		originY = y;
		mouseState = END;
	}
	
	public void setEnd(int x, int y) {
		
		endX = x;
		endY = y;
		mouseState = END;
	}
	
	public void setCurrentLocation(int x, int y) {
		currentX = x;
		currentY = y;
		mouseState = SELECTING;
	}
	
	public void paintAll(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		if(mouseState == SELECTING) {
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.ORANGE);
			
			//drawRect()'s height and width can't be minus
			if(currentX - originX > 0 && currentY - originY > 0) {
				System.out.println("Paint mouseChooingRect");
				g2.drawRect(originX, originY, currentX - originX, currentY - originY);
			}
			else if(currentX - originX > 0 && currentY - originY < 0) {
				g2.drawRect(originX, currentY, currentX - originX, originY - currentY);
			}
			else if(currentX - originX < 0 && currentY - originY > 0) {
				g2.drawRect(currentX, originY, originX - currentX, currentY - originY);
			}
			else {
				g2.drawRect(currentX, currentY, originX - currentX, originY - currentY);

			}
		}
	}
	
	public int[] getSelectedArea() {
		int[] coordinates = new int[4];

		coordinates[0] = originX < endX ? 
				originX : endX;
		coordinates[1] = originY < endY ?
				originY : endY;
		coordinates[2] = originX < endX ?
				endX : originX;
		coordinates[3] = originY < endY ?
				endY : originY;
		return coordinates;
	}
	
	public int[] getOrigin() {
		int[] coordinates = new int[2];
		coordinates[0] = originX;
		coordinates[1] = originY;
		return coordinates;
	}
}
