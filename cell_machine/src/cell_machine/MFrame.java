package cell_machine;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;

import javax.swing.JTextField;

import tools.MTransformer;



public class MFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int gridWidth = 80, gridHeight = 80;//juset for initialize
	public static int GRIDSIZE = 20;//juset for initialize
	private int sleepMicroTime = 300;//juset for initialize
	
	private MPanel gamePanel;
	private MToolBar toolBar;
	private MMenuBar menuBar;
	private MFrame thisFrame = this;
	private boolean isOriginal = true;// mark if the current state is the original state
	private boolean isRunning = false;
	
	private final int PEN = 0, ERASER = 1, SELECTOR = 2, LOADMODULE = 3;
	private int penState = SELECTOR;// mark the current state of pen
	private File file;
	
	private LinkedHashMap<String, String> modules = new LinkedHashMap<String, String>();
	
	public MFrame() {		
		toolBar = new MToolBar();
		menuBar = new MMenuBar();
		toolBar.setPara(gridHeight, gridWidth, GRIDSIZE);
		gamePanel = new MPanel(gridWidth * GRIDSIZE, 
				gridHeight * GRIDSIZE, GRIDSIZE, this);
		gamePanel.setSleepMicroTime(sleepMicroTime);
		file = null;
		
		
		addModules();
		
		
		addPanelListeners();
		addButtonListeners();
		addTextFieldListeners();
		addFileMenuListeners();
		addModuleMenuListeners();
		addKeyboardListeners();
		
		setLayout(new BorderLayout());
		add(BorderLayout.WEST, toolBar);
		add(BorderLayout.CENTER, gamePanel);

		setJMenuBar(menuBar);
	//	requestFocus();
	}
	
	private void addModules() {
		modules.put("小飞船-右", "/ship-right.cmf");
		modules.put("小飞船-左", "/ship-left.cmf");
		modules.put("小飞船-上", "/ship-up.cmf");
		modules.put("小飞船-下", "/ship-down.cmf");
		modules.put("小滑翔机-右下", "/glider-lowerRight.cmf");
		modules.put("小滑翔机-右上", "/glider-upperRight.cmf");
		modules.put("小滑翔机-左下", "/glider-lowerLeft.cmf");
		modules.put("小滑翔机-左上", "/glider-upperLeft.cmf");
		modules.put("高斯帕滑翔机枪-左下", "/GospersGliderGun-lowerLeft.cmf");
		modules.put("蝴蝶滑翔机-右", "/butterflyGliger-right.cmf");
//		modules.put("小飞船-左", "src\\modules\\ship-left.cmf");
//		modules.put("小飞船-上", "src\\modules\\ship-up.cmf");
//		modules.put("小飞船-下", "src\\modules\\ship-down.cmf");
//		modules.put("小滑翔机-右下", "src\\modules\\glider-lowerRight.cmf");
//		modules.put("小滑翔机-右上", "src\\modules\\glider-upperRight.cmf");
//		modules.put("小滑翔机-左下", "src\\modules\\glider-lowerLeft.cmf");
//		modules.put("小滑翔机-左上", "src\\modules\\glider-upperLeft.cmf");
//		modules.put("高斯帕滑翔机枪-左下", "src\\modules\\GospersGliderGun-lowerLeft.cmf");
//		modules.put("蝴蝶滑翔机-右", "src\\modules\\butterflyGliger-right.cmf");
	}
	
	private void addPanelListeners() {
		gamePanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				int x= e.getX();
				int y = e.getY();
				isOriginal = true;
				if(penState == PEN) {
					gamePanel.reverse(x, y);
					repaint();
				}
				else if(penState == ERASER){
					gamePanel.setDead(x, y);
					repaint();
				}
				else if(penState == SELECTOR) {
					gamePanel.showSelectedCell();
				}
				else if(penState == LOADMODULE) {
					gamePanel.addModule();
					penState = SELECTOR;
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				System.out.println("Calling mousePressed..");
				gamePanel.stopShowSelected();
				gamePanel.stopShowSelectedCell();
				gamePanel.mouseOrigin(e.getX(), e.getY());
				
				if(penState == PEN && penState == ERASER) {
					// save the current state for reload
					gamePanel.saveState();
				}
				else if(penState == SELECTOR) {
					
					// stop before get new mouse position
					
					toolBar.buttons.get("选择").requestFocus();
				}
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseReleased(e);
				gamePanel.mouseEnd(e.getX(), e.getY());
				if(penState == SELECTOR) {
					gamePanel.showSelected();
					repaint();
				}
				if(penState == LOADMODULE) {
					repaint();
				}
			}
			
		});
		gamePanel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				int x = e.getX();
				int y = e.getY();
				super.mouseDragged(e);
				isOriginal = true;
				if(penState == PEN) {
					gamePanel.setAlive(x, y);
				}
				else if(penState == ERASER){
					gamePanel.setDead(x, y);
				}
				else if(penState == SELECTOR) {
					gamePanel.mouseSelectingNode(e.getX(), e.getY());
					
				}
				repaint();
			}
			
		});
	}
	
	private void addButtonListeners() {
		toolBar.buttons.get("选择").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				penState = SELECTOR;
			}
			
		});
		toolBar.buttons.get("画笔").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				penState = PEN;
				
			}
		});
		toolBar.buttons.get("擦除").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				penState = ERASER;
				
			}
		});
		toolBar.buttons.get("下一步").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(isOriginal) {	
					gamePanel.saveState();
					isOriginal = false;
				}
				gamePanel.nextStep();
				repaint();
			}
		});
		toolBar.buttons.get("清空").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.clearAll();
				repaint();
			}
		});
		toolBar.buttons.get("恢复初始").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					gamePanel.loadState();
					isOriginal = true;
					repaint();
			}
		});
		toolBar.buttons.get("开始").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(isOriginal) {	
					gamePanel.saveState();
					isOriginal = false;
				}
				if(isRunning == false) {
					gamePanel.runThread(thisFrame);
					isRunning = true;
				}
				
			}
		});
		toolBar.buttons.get("暂停").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.stopThread();
				isRunning = false;
			}
		});
	}
	private void addTextFieldListeners() {
		JTextField jf0 = toolBar.textFields.get("网格宽");
		JTextField jf1 = toolBar.textFields.get("网格高");
		JTextField jf2 = toolBar.textFields.get("周期");
		JTextField jf3 = toolBar.textFields.get("边长");
		
		
		jf0.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				System.out.println("The width of Grid is: " + jf0.getText());
				gridWidth = Integer.valueOf(jf0.getText());
				gamePanel.setGridSize(gridHeight, gridWidth);
				repaint();
			}
		});
		
		jf1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				System.out.println("The height of Grid is: " + jf1.getText());
				gridHeight = Integer.valueOf(jf1.getText());
				gamePanel.setGridSize(gridHeight, gridWidth);
				repaint();
			}
		});
		
		jf2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				 TODO Auto-generated9+++;/-/- method stub
				gamePanel.setRefreshTime(Integer.valueOf(jf2.getText()));
			}
		});
		
		jf3.addActionListener(new ActionListener() {
			//网格边长
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				GRIDSIZE = Integer.valueOf(jf3.getText());
				repaint();
			}
		});
	}
	
	private void addFileMenuListeners() {
		JMenu fileMenu = menuBar.menus.get("文件");
		fileMenu.getItem(0).addActionListener(new ActionListener() {
			// This is MenuItem "打开"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Calling listener of menus.open");
				File f = menuBar.openFile(gamePanel);
				MCell[][] cells = MTransformer.fileToCells(f);
				int gridHeight = cells.length;
				int gridWidth = cells[0].length;
				toolBar.setPara(gridHeight, gridWidth, GRIDSIZE);
				gamePanel.setCells(cells);
				file = f;
				repaint();
			}
		});
		fileMenu.getItem(1).addActionListener(new ActionListener() {
			// This is MenuItem "保存当前界面"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Calling listener of menus.save");
				MCell[][] cells = gamePanel.getCells();
				if(file == null) {// if current file is null, then open a new file.
					file = menuBar.openFile(gamePanel);
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				MTransformer.cellsToFile(cells, file);
			}
		});
		fileMenu.getItem(2).addActionListener(new ActionListener() {
			// This is MenuItem "另存为当前界面"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Calling listener of menus.saveAs");
				MCell[][] cells = gamePanel.getCells();

				file = menuBar.openFile(gamePanel);
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MTransformer.cellsToFile(cells, file);
			}
		});
		
		
		
	}
	
	private void addModuleMenuListeners() {
		JMenu mouduleMenu = menuBar.menus.get("组件");
		mouduleMenu.getItem(0).addActionListener(new ActionListener() {
			// This is MenuItem "小飞船"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.loadModule(modules.get("小飞船-右"));
				penState = LOADMODULE;
				
			}
		});
		mouduleMenu.getItem(1).addActionListener(new ActionListener() {
			// This is MenuItem "小飞船"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.loadModule(modules.get("小飞船-左"));
				penState = LOADMODULE;
				
			}
		});
		mouduleMenu.getItem(2).addActionListener(new ActionListener() {
			// This is MenuItem "小飞船"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.loadModule(modules.get("小飞船-上"));
				penState = LOADMODULE;
				
			}
		});
		mouduleMenu.getItem(3).addActionListener(new ActionListener() {
			// This is MenuItem "小飞船"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.loadModule(modules.get("小飞船-下"));
				penState = LOADMODULE;
				
			}
		});
		mouduleMenu.getItem(4).addActionListener(new ActionListener() {
			// This is MenuItem "小飞船"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.loadModule(modules.get("小滑翔机-右下"));
				penState = LOADMODULE;
				
			}
		});
		mouduleMenu.getItem(5).addActionListener(new ActionListener() {
			// This is MenuItem "小飞船"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.loadModule(modules.get("小滑翔机-右上"));
				penState = LOADMODULE;
				
			}
		});
		mouduleMenu.getItem(6).addActionListener(new ActionListener() {
			// This is MenuItem "小飞船"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.loadModule(modules.get("小滑翔机-左下"));
				penState = LOADMODULE;
				
			}
		});
		mouduleMenu.getItem(7).addActionListener(new ActionListener() {
			// This is MenuItem "小飞船"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.loadModule(modules.get("小滑翔机-左上"));
				penState = LOADMODULE;
				
			}
		});
		mouduleMenu.getItem(8).addActionListener(new ActionListener() {
			// This is MenuItem "小飞船"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.loadModule(modules.get("高斯帕滑翔机枪-左下"));
				penState = LOADMODULE;
				
			}
		});
		mouduleMenu.getItem(9).addActionListener(new ActionListener() {
			// This is MenuItem "小飞船"
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gamePanel.loadModule(modules.get("蝴蝶滑翔机-右"));
				penState = LOADMODULE;
				
			}
		});
	}
	
	private void addKeyboardListeners() {
		
		toolBar.buttons.get("选择").addKeyListener(new KeyListener() {
	
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(penState == SELECTOR) {
					System.out.println("Calling KeyListener.keyTyped");
					System.out.println("按下："+ (int)arg0.getKeyChar());
					switch(arg0.getKeyChar()) {
						case(8): //delete
							gamePanel.deleteSelectedCells();
							gamePanel.stopShowSelected();
							repaint();
							break;
						case(127)://backspace
							gamePanel.deleteSelectedCells();
							gamePanel.stopShowSelected();
							repaint();
							break;
						case(99)://copy(c)
							gamePanel.copySelectedCells();
							break;
						case(118)://paste(v)
							gamePanel.pasteSelectedCells();
							repaint();
							break;
					}

				}
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
