package cell_machine;



import javax.swing.JFrame;


public class MMain{
	
	
	public MMain() {
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MFrame frame = new MFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		//frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true); 
		frame.requestFocus();
	}
}
