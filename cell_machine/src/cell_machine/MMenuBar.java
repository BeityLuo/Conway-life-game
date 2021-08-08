package cell_machine;


import java.io.File;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MMenuBar extends JMenuBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LinkedHashMap<String, JMenu> menus = new LinkedHashMap<String, JMenu>();
	public LinkedHashMap<String, JMenuItem> fileItems = new LinkedHashMap<String, JMenuItem>();
	public LinkedHashMap<String, JMenuItem> windowItems = new LinkedHashMap<String, JMenuItem>();
	public LinkedHashMap<String, JMenuItem> mouduleItems = new LinkedHashMap<String, JMenuItem>();
	
	
	public MMenuBar() {
		menus.put("文件", new JMenu("文件"));
		//menus.put("窗口", new JMenu("窗口"));
		menus.put("组件", new JMenu("组件"));
		for(JMenu jm : menus.values()) {
			add(jm);
		}
		
		fileItems.put("打开", new JMenuItem("打开"));
		fileItems.put("保存", new JMenuItem("保存"));
		fileItems.put("另存为", new JMenuItem("另存为"));

		
		//fileItems.put("保存初始界面", new JMenuItem("保存初始界面"));
		//fileItems.put("初始界面另存为", new JMenuItem("初始界面另存为"));
		//windowItems.put("背景色", new JMenuItem("背景色"));
		//windowItems.put("前景色", new JMenuItem("前景色"));
		
		
		mouduleItems.put("小飞船-右", new JMenuItem("小飞船-右"));
		mouduleItems.put("小飞船-左", new JMenuItem("小飞船-左"));
		mouduleItems.put("小飞船-上", new JMenuItem("小飞船-上"));
		mouduleItems.put("小飞船-下", new JMenuItem("小飞船-下"));
		mouduleItems.put("小滑翔机-右下", new JMenuItem("小滑翔机-右下"));
		mouduleItems.put("小滑翔机-右上", new JMenuItem("小滑翔机-右上"));
		mouduleItems.put("小滑翔机-左下", new JMenuItem("小滑翔机-左下"));
		mouduleItems.put("小滑翔机-左上", new JMenuItem("小滑翔机-左上"));
		mouduleItems.put("高斯帕滑翔机枪-左下", new JMenuItem("高斯帕滑翔机枪-左"));
		mouduleItems.put("蝴蝶滑翔机-右", new JMenuItem("蝴蝶滑翔机-右"));
		
		
		for(JMenuItem mi : fileItems.values()) {
			menus.get("文件").add(mi);
		}
//		for(JMenuItem mi : windowItems.values()) {
//			menus.get("窗口").add(mi);
//		}
		for(JMenuItem mi : mouduleItems.values()) {
			menus.get("组件").add(mi);
		}
	}
	public File openFile(MPanel gamePanel) {
		System.out.println("Calling openFile()");
		JFileChooser fileChooser = new JFileChooser();
        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));
        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(false);
        gamePanel.stopThread();
        int result = fileChooser.showOpenDialog(this);
        File file = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            file = fileChooser.getSelectedFile();
        }
        return file;
	}
}







