package Frame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class MainFrame {
	private JFrame frame = new JFrame();
	private JButton record = new JButton("   ¼��  ");
	private JButton querymanage = new JButton("��ѯ/����");
	private JButton exit = new JButton("   �뿪  ");
	
	public void setStyle(String style) {
		if (style.equals("Windows"))
			style = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		else if (style.equals("Metal")) {
			style = "javax.swing.plaf.metal.MetalLookAndFeel";
		} else {
			style = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";// Motif���
		}
		try {
			UIManager.setLookAndFeel(style);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(frame.getContentPane());
	}
	
	class RecordAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new RecordFrame().init();
			frame.setVisible(false);
		}
	}
	
	class QMAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new QueryManagementFrame().init();
			frame.setVisible(false);
		}
	}
	
	class ExitAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
				int response = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ�˳��������", "��ʾ", JOptionPane.YES_NO_OPTION);
				if(response == 0)
					System.exit(0);
		}
	}
	
	public void init(){
		  //���ùر�ʱʲôҲ����
	  frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	  //�����رհ�ť�ĵ������
	  frame.addWindowListener(new WindowAdapter(){
	   //new һ��WindowAdapter �� ��дwindowClosing����
	   //WindowAdapter�Ǹ���������  ���忴jdk�İ����ĵ�
		  public void windowClosing(WindowEvent e) {
	    //����д�Ի���
			  int response = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ�˳��������","��ʾ",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			  if(response==JOptionPane.YES_OPTION) 
				  System.exit(0);
		  }
	  });
		//frame.getContentPane().setVisible(false);
		frame.getContentPane().setBackground(java.awt.Color.white);
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("pic/tb.png");
		Cursor cursor = tk.createCustomCursor(image, new Point(0,0), "love");
		frame.setCursor(cursor);
		Image image1 = tk.createImage("pic/pig.png"); /* image.gif�����ͼ�� */
		frame.setIconImage(image1);
		
		record.setFont(new Font("����", Font.PLAIN, 14));

		//record.addActionListener(new RunAction());
		
		querymanage.setFont(new Font("�� ��", Font.PLAIN, 14));
		//	querymanage.addActionListener(new RunAction());
		
		exit.setFont(new Font("��", Font.PLAIN, 14));
		//	exit.addActionListener(new RunAction());
		
		record.addActionListener(new RecordAction());
		querymanage.addActionListener(new QMAction());
		exit.addActionListener(new ExitAction());
		
		Box main = Box.createVerticalBox();
		Box pic = Box.createHorizontalBox();
		Box black = Box.createHorizontalBox();
		
		main.add(Box.createVerticalStrut(15));
		main.add(record);
		main.add(Box.createVerticalStrut(15));
		main.add(querymanage);
		main.add(Box.createVerticalStrut(15));
		main.add(exit);
		
		ImageIcon ima = new ImageIcon("pic/main.jpg");
		JLabel label = new JLabel(ima);
		pic.add(Box.createHorizontalStrut(15));
		pic.add(label);
		pic.add(Box.createHorizontalStrut(35));
		
		black.add(Box.createHorizontalStrut(5));
		
		frame.add(main, BorderLayout.CENTER);
		frame.add(pic, BorderLayout.WEST);
		frame.add(black,BorderLayout.EAST);
		
		setStyle("Windows");
		frame.pack();
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2;
		frame.setLocation(w,h);// ���ô˴��������ָ�������λ�á�Ϊnull��ʾ����Ļ����
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainFrame().init();
	}
}
