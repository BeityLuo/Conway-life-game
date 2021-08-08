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
		menus.put("�ļ�", new JMenu("�ļ�"));
		//menus.put("����", new JMenu("����"));
		menus.put("���", new JMenu("���"));
		for(JMenu jm : menus.values()) {
			add(jm);
		}
		
		fileItems.put("��", new JMenuItem("��"));
		fileItems.put("����", new JMenuItem("����"));
		fileItems.put("���Ϊ", new JMenuItem("���Ϊ"));

		
		//fileItems.put("�����ʼ����", new JMenuItem("�����ʼ����"));
		//fileItems.put("��ʼ�������Ϊ", new JMenuItem("��ʼ�������Ϊ"));
		//windowItems.put("����ɫ", new JMenuItem("����ɫ"));
		//windowItems.put("ǰ��ɫ", new JMenuItem("ǰ��ɫ"));
		
		
		mouduleItems.put("С�ɴ�-��", new JMenuItem("С�ɴ�-��"));
		mouduleItems.put("С�ɴ�-��", new JMenuItem("С�ɴ�-��"));
		mouduleItems.put("С�ɴ�-��", new JMenuItem("С�ɴ�-��"));
		mouduleItems.put("С�ɴ�-��", new JMenuItem("С�ɴ�-��"));
		mouduleItems.put("С�����-����", new JMenuItem("С�����-����"));
		mouduleItems.put("С�����-����", new JMenuItem("С�����-����"));
		mouduleItems.put("С�����-����", new JMenuItem("С�����-����"));
		mouduleItems.put("С�����-����", new JMenuItem("С�����-����"));
		mouduleItems.put("��˹�������ǹ-����", new JMenuItem("��˹�������ǹ-��"));
		mouduleItems.put("���������-��", new JMenuItem("���������-��"));
		
		
		for(JMenuItem mi : fileItems.values()) {
			menus.get("�ļ�").add(mi);
		}
//		for(JMenuItem mi : windowItems.values()) {
//			menus.get("����").add(mi);
//		}
		for(JMenuItem mi : mouduleItems.values()) {
			menus.get("���").add(mi);
		}
	}
	public File openFile(MPanel gamePanel) {
		System.out.println("Calling openFile()");
		JFileChooser fileChooser = new JFileChooser();
        // ����Ĭ����ʾ���ļ���Ϊ��ǰ�ļ���
        fileChooser.setCurrentDirectory(new File("."));
        // �����ļ�ѡ���ģʽ��ֻѡ�ļ���ֻѡ�ļ��С��ļ����ļ�����ѡ��
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // �����Ƿ������ѡ
        fileChooser.setMultiSelectionEnabled(false);
        gamePanel.stopThread();
        int result = fileChooser.showOpenDialog(this);
        File file = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            // ��������"ȷ��", ���ȡѡ����ļ�·��
            file = fileChooser.getSelectedFile();
        }
        return file;
	}
}







