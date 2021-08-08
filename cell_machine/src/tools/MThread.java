package tools;

import javax.swing.SwingUtilities;

import cell_machine.MFrame;
import cell_machine.MPanel;

public class MThread extends Thread{
	private MPanel gamePanel;// the frame that call the thread
	private int sleepMicroTime;
	private MFrame mothorFrame;// the frame that call this thread
	@SuppressWarnings("unused")
	private int cnt;
	private int stopFlag;
	
	public MThread(MPanel gamePanel, MFrame motherFrame) {
		this.gamePanel = gamePanel;
		this.mothorFrame = motherFrame;
		setSleepMicroTime(sleepMicroTime);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		stopFlag = 0;
		while(stopFlag == 0) {

			gamePanel.nextStep();
			try {
				Thread.sleep(sleepMicroTime);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Get InterruptedException in MTread.run()");
			}
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					mothorFrame.repaint();
				}
			});
			
		}
	}
	public void setStop() {
		stopFlag = 1;
	}
	
	public void setSleepMicroTime(int sleepMicroTime) {
		this.sleepMicroTime = sleepMicroTime;
	}
}
