package cell_machine;

import java.awt.Color;
import java.awt.Graphics;


public class MCell {
	private boolean alive = false;
	private boolean isShowingSelf = false;
	private Color cellColor = Color.BLACK;
	
	
	public boolean isAlive() { return alive; }
	
	public void setAlive() { alive = true; }
	
	public void setDead() { alive = false; }
	
	public void setState(boolean state) { alive = state; }
	
	public void drawSquare(Graphics g, int x, int y, int length) {
		if(alive == true || isShowingSelf) {
			g.setColor(cellColor);
			g.fillRect(x, y, length, length);
			//System.out.println("fillRect\n");
		}
		else {
			//g.drawRect(x, y, length, length);
		}
	}
	
	public void setColor(Color color) {
		cellColor = color;
	}
	
	public void showSelf(Color color) {
		cellColor = color;
		isShowingSelf = true;
		
	}
	
	public void stopShowSelf() {
		isShowingSelf = false;
		cellColor = Color.BLACK;
	}
}
