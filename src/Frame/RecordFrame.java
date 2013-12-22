package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Dialog.EachDialog;

import com.eltima.components.ui.DatePicker;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class RecordFrame {
	private JFrame frame = new JFrame("录入系统");
	private DatePicker datepicker = new DatePicker(null,"yyyy-MM-dd",null,null);
	private JTextField money = new JTextField(20);
	private JLabel moneyLabel = new JLabel("消费金额：");
	private JLabel resionLabel = new JLabel("消费项：");
	private JComboBox resion = new JComboBox(new String[]{"吃饭","零食","衣物","洗漱化妆用品","家电","交通","娱乐","礼品","旅游","其他"});
	private JLabel dateLabel = new JLabel("消费日期：");
	private JLabel addLabel = new JLabel("补充说明：");
	private JLabel addaddLabel = new JLabel("** 请不要输入“;” **");
	private JTextArea add = new JTextArea(2,1);
	private JScrollPane pane = new JScrollPane(add);
	private JButton button1 = new JButton("提交");
	private JButton button2 = new JButton("重置");
	private JButton button3 = new JButton("首页");
	private JButton button4 = new JButton("进入查询/管理");
	private JButton button5 = new JButton("离开");
	private JLabel label = new JLabel("若想修改某项请双击待修改项！");
	private DefaultTableModel model = null;
	private JTable table = null;
	private Frame parent;
	private String money1,add1;
	private JLabel love = new JLabel("I   Love   You  !");

	
	public RecordFrame(){
		
	}
	public RecordFrame(Frame parent){
		this.parent = parent;
	}
	
	public void setStyle(String style) {
		if (style.equals("Windows"))
			style = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		else if (style.equals("Metal")) {
			style = "javax.swing.plaf.metal.MetalLookAndFeel";
		} else {
			style = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";// Motif风格
		}
		try {
			UIManager.setLookAndFeel(style);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(frame.getContentPane());
	}
	
	class FirstAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MainFrame().init();
			frame.setVisible(false);
		}
		
	}
	
	class SecendAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new QueryManagementFrame().init();
			frame.setVisible(false);
		}
		
	}
	
	class ThirdAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int response = JOptionPane.showConfirmDialog(null, "您确定要退出该软件？","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			  if(response==JOptionPane.YES_OPTION) 
				  System.exit(0);
		}
		
	}
	
	class TextAction implements KeyListener {
		public void keyReleased(KeyEvent e){

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			Pattern p = Pattern.compile("[0-9,.]");
			Matcher m = p.matcher(String.valueOf(e.getKeyChar()));
			boolean b = m.matches();
			if(!b){    //如果输入的字符匹配数字为false则此次按键事件作废，就不会打印出你按的键的值了
			    e.consume();
			}
			
		     if(String.valueOf(e.getKeyChar()).equals(".") &&  money.getText().contains(".")){
		    	 e.consume();
		     }
		     if(String.valueOf(e.getKeyChar()).equals(".") && money.getText().equals("")){
		    	 money.setText("0");
		     }
		     if(!String.valueOf(e.getKeyChar()).equals(".") &&  money.getText().equals("0")){
		    	 e.consume();
		     }
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	
	}
	
	class FTAction implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if(money.getText().equals(""));
			else
				if(Double.parseDouble(money.getText()) == 0){
					JOptionPane.showMessageDialog(null,"消费金额请不要为零值！","提示", JOptionPane.WARNING_MESSAGE);
					money.setText("");
					money.requestFocus();
				}
		}
		
	}
	
	
	
	class submitAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Date time = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timestr = sf.format(time);
			SimpleDateFormat newsf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				try {
					Date date = newsf.parse(datepicker.getText());
					if(!money.getText().equals("") && !resion.getSelectedItem().equals("") && !datepicker.getText().equals("")){	
						Directory dir = FSDirectory.open(new File("index"));
						IndexWriter writer = new IndexWriter(dir,new WhitespaceAnalyzer(),IndexWriter.MaxFieldLength.UNLIMITED);
						Document doc = new Document();
						doc.add(new Field("resion",resion.getSelectedItem().toString(),Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
						doc.add(new NumericField("money",Field.Store.YES, true).setDoubleValue(Double.valueOf(money.getText())));
						doc.add(new NumericField("datepicker",Field.Store.YES, true).setLongValue(date.getTime()));
						doc.add(new Field("addtext",add.getText(),Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
						doc.add(new NumericField("time",Field.Store.YES, true).setLongValue(time.getTime()));
						IndexSearcher searcher = new IndexSearcher(dir);
						TermQuery tqy = new TermQuery(new Term("resion",resion.getSelectedItem().toString()));
						NumericRangeQuery qy = NumericRangeQuery.newLongRange("datepicker",date.getTime(), date.getTime(), true, true);
						BooleanQuery query = new BooleanQuery();
						query.add(tqy, BooleanClause.Occur.MUST);
						query.add(qy, BooleanClause.Occur.MUST);
						TopDocs matches = searcher.search(query,10);
						TopDocs mat = searcher.search(tqy,10);	
					/*	
						System.out.println(date.getTime()/24/3600);
						System.out.println("geshu:"+matches.totalHits);
						System.out.println(searcher.doc(mat.scoreDocs[0].doc).get("datepicker"));
					*/	
						//利用DefaultTableModel的addRow(Vector rowData)方法
						if(matches.totalHits == 0){
							writer.addDocument(doc);
							writer.close();
							searcher.close();						
						//	dir.close();
							String[] rowData = {money.getText(),resion.getSelectedItem().toString(),datepicker.getText(),
									add.getText(),timestr,"双击删除"};
							model.addRow(rowData);
							
							 int row = table.getRowCount() - 1;
							 table.setRowSelectionInterval(row, row);
							 table.scrollRectToVisible(table.getCellRect(row, 0, true));
							 
							money1 = money.getText();
							add1 = add.getText();
							money.setText("");
							add.setText("");
						}else{				
							writer.close();
							searcher.close();
							JOptionPane.showMessageDialog(null,"你可能已经输入了相同的记录，请检查并重新输入！","提示", JOptionPane.WARNING_MESSAGE);
						}	
					}else{
						JOptionPane.showMessageDialog(null,"消费金额、消费项和消费日期不能为空，请填写完整！","提示", JOptionPane.WARNING_MESSAGE);
					}	
				} catch (java.text.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
	
	class resetAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			money.setText("");
			resion.setSelectedIndex(0);
			datepicker.getInnerTextField().setText("");
			add.setText("");
			money.requestFocus();
		}
	}
	//
	public void init(){
		//System.out.println();
		  //设置关闭时什么也不做
		  frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		  //监听关闭按钮的点击操作
		  frame.addWindowListener(new WindowAdapter(){
		   //new 一个WindowAdapter 类 重写windowClosing方法
		   //WindowAdapter是个适配器类  具体看jdk的帮助文档
			  public void windowClosing(WindowEvent e) {
		    //这里写对话框
				  int response = JOptionPane.showConfirmDialog(null, "您确定要退出该软件？","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				  if(response==JOptionPane.YES_OPTION) 
					  System.exit(0);
			  }
		  });
		  
		  Object[][] data = null;
		  Object[] title = {"消费金额","消费项","消费日期","说明","记录修改时间","操作"};
		  model = new DefaultTableModel(data,title){
				private static final long serialVersionUID = 1L;
				public boolean isCellEditable(int row,int col){
						return false;
				}
			};
			table = new JTable(model);
			  JScrollPane tablePane = new JScrollPane(table);
				tablePane.setPreferredSize(new Dimension(380, 380));
				tablePane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();   
			renderer.setHorizontalAlignment(JLabel.CENTER);
		  table.setShowHorizontalLines(false);
		  table.setShowVerticalLines(false);
		  table.setRowHeight(20);
		  table.getColumnModel().getColumn(0).setMaxWidth(80);
		  table.getColumnModel().getColumn(0).setMinWidth(80);
		  table.getColumnModel().getColumn(1).setMaxWidth(70);
		  table.getColumnModel().getColumn(1).setMinWidth(70);
		  table.getColumnModel().getColumn(2).setMaxWidth(100);
		  table.getColumnModel().getColumn(2).setMinWidth(100);
		  table.getColumnModel().getColumn(4).setMaxWidth(210);
		  table.getColumnModel().getColumn(4).setMinWidth(210);
		  table.getColumnModel().getColumn(5).setMaxWidth(60);
		  table.getColumnModel().getColumn(5).setMinWidth(60);
		  
		  

		  table.getTableHeader().setReorderingAllowed(false);
		  table.getTableHeader().setResizingAllowed(false);
		  
		  

		  table.getColumnModel().getColumn(0).setCellRenderer(renderer);
		  table.getColumnModel().getColumn(1).setCellRenderer(renderer);
		  table.getColumnModel().getColumn(2).setCellRenderer(renderer);
		  table.getColumnModel().getColumn(4).setCellRenderer(renderer);
		  table.getColumnModel().getColumn(5).setCellRenderer(renderer);
		  
		  table.setCellSelectionEnabled(true);

		  table.addMouseListener(new MouseAdapter() {
				public void mouseReleased(java.awt.event.MouseEvent e) {
					
					if (e.getClickCount() == 2
							&& SwingUtilities.isLeftMouseButton(e)) {
						int row = table.rowAtPoint(e.getPoint());
						int column = table.columnAtPoint(e.getPoint());
						String str = "";
						if(column == 0) str = "修改金额";
						if(column == 1) str = "修改选项";
						if(column == 2) str = "修改日期";
						if(column == 3) str = "修改说明";
						if(column == 5){
							int numb = JOptionPane.showConfirmDialog(null,"您确定删除该记录?","删除",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
							if(numb == 0){
								try{
									SimpleDateFormat newsf = new SimpleDateFormat("yyyy-MM-dd");
										Date date = new Date();
										try {
											date = newsf.parse(table.getValueAt(row, 2).toString());
										} catch (java.text.ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									Directory dir = FSDirectory.open(new File("index"));
									IndexWriter writer = new IndexWriter(dir,new WhitespaceAnalyzer(),IndexWriter.MaxFieldLength.UNLIMITED);
									IndexSearcher searcher = new IndexSearcher(dir);
									TermQuery tqy = new TermQuery(new Term("resion",table.getValueAt(row, 1).toString()));
									Query qy = NumericRangeQuery.newLongRange("datepicker", date.getTime(), date.getTime(), true, true);
									BooleanQuery query = new BooleanQuery();
									query.add(tqy, BooleanClause.Occur.MUST);
									query.add(qy, BooleanClause.Occur.MUST);
									writer.deleteDocuments(query);
									writer.optimize();
									writer.commit();
									writer.close();
								//	dir.close();
									model.removeRow(row);
									
								}catch(Exception el){
									el.printStackTrace();
								}
							}
						}else if(column != 4){
							JDialog textData = new JDialog(parent,str,true);
							if(column != 3){
								textData.setMaximumSize(new Dimension(250, 100));
								textData.setMinimumSize(new Dimension(250, 100));
							}else{
								textData.setMaximumSize(new Dimension(600, 200));
								textData.setMinimumSize(new Dimension(600, 200));
							}
							textData.add(new EachDialog().getBox(row,column,table, textData, table.getValueAt(row, 0).toString(), 
									table.getValueAt(row, 1).toString(), table.getValueAt(row, 2).toString(), 
									table.getValueAt(row, 3).toString(),table.getValueAt(row, 4).toString(),false));
							int ww = (Toolkit.getDefaultToolkit().getScreenSize().width -
									textData.getSize().width) / 2;
							int hh = (Toolkit.getDefaultToolkit().getScreenSize().height - 
									textData.getSize().height) / 2;
							textData.setLocation(ww,hh);// 设置此窗口相对于指定组件的位置。为null表示在屏幕中央
							textData.pack();
							textData.setVisible(true);
						}
					}
				}
		  });
					  
		  money.addKeyListener(new TextAction());
		  money.addFocusListener(new FTAction());
		  
		  datepicker.getInnerTextField().setEditable(false);
		  
		  Toolkit tk = Toolkit.getDefaultToolkit();
		  Image image = tk.createImage("pic/money.png"); /* image.gif是你的图标 */
		  frame.setIconImage(image);
		  Image image1 = tk.createImage("pic/tb.png");
		  Cursor cursor = tk.createCustomCursor(image1, new Point(0,0), "love");
		  frame.setCursor(cursor);
		  
		  pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		  add.setLineWrap(true);
		  
		  label.setForeground(Color.red);
		  addaddLabel.setForeground(Color.red);
		  addaddLabel.setFont(new Font("隶书", Font.PLAIN, 16));
		  
		  love.setFont(new Font("Serif", Font.ITALIC, 30));
		  love.setForeground(Color.red);
		  
		  button1.addActionListener(new submitAction());
		  button2.addActionListener(new resetAction());
		  button3.addActionListener(new FirstAction());
		  button4.addActionListener(new SecendAction());
		  button5.addActionListener(new ThirdAction());
		  
		  Box up = Box.createHorizontalBox();
		  Box down = Box.createHorizontalBox();
		  Box left = Box.createVerticalBox();
		  Box right = Box.createVerticalBox();
		  Box center = Box.createVerticalBox();		  
		  
		  Box writeZero = Box.createHorizontalBox();
		  Box writeFirst = Box.createHorizontalBox();
		  Box writeSecond = Box.createHorizontalBox();
		  Box writeThird = Box.createHorizontalBox();
		  Box writeForth = Box.createHorizontalBox();
		  Box writeFifth = Box.createHorizontalBox();
		  
		  up.add(Box.createVerticalStrut(10));
		  down.add(Box.createVerticalStrut(10));
		  left.add(Box.createHorizontalStrut(10));
		  right.add(Box.createHorizontalStrut(10));
		  
		  
		  writeZero.add(Box.createHorizontalStrut(40));
		  writeZero.add(love);
		  writeZero.add(Box.createHorizontalStrut(200));
		  writeZero.add(button3);
		  writeZero.add(Box.createHorizontalStrut(15));
		  writeZero.add(button4);
		  writeZero.add(Box.createHorizontalStrut(55));
		  writeZero.add(button5);
		  
		  writeFirst.add(moneyLabel);
		  writeFirst.add(money);
		  writeFirst.add(Box.createHorizontalStrut(35));
		  writeFirst.add(resionLabel);
		  writeFirst.add(resion);
		  writeFirst.add(Box.createHorizontalStrut(35));
		  writeFirst.add(dateLabel);
		  writeFirst.add(datepicker);
		  
		  writeSecond.add(addLabel);
		  writeSecond.add(pane);
		  writeSecond.add(addaddLabel);
		  
		  writeThird.add(button1);
		  writeThird.add(Box.createHorizontalStrut(55));
		  writeThird.add(button2);
		  
		  writeForth.add(tablePane);
		  
		  writeFifth.add(label);
		  
		  center.add(writeZero);
		  center.add(Box.createVerticalStrut(25));
		  center.add(writeFirst);
		  center.add(Box.createVerticalStrut(15));
		  center.add(writeSecond);
		  center.add(Box.createVerticalStrut(15));
		  center.add(writeThird);
		  center.add(Box.createVerticalStrut(10));
		  center.add(writeForth);
		  center.add(Box.createVerticalStrut(10));
		  center.add(writeFifth);
		  center.add(Box.createVerticalStrut(10));
		  
		  
		  
		  	frame.add(up,BorderLayout.NORTH);
		  	frame.add(down,BorderLayout.SOUTH);
		  	frame.add(left,BorderLayout.WEST);
		  	frame.add(right,BorderLayout.EAST);
		  	frame.add(center,BorderLayout.CENTER);
		  
			setStyle("Windows");
			frame.pack();
			int w = (Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2;
			int h = (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2;
			frame.setLocation(w,h);// 设置此窗口相对于指定组件的位置。为null表示在屏幕中央
			frame.setResizable(false);
			frame.pack();
			frame.setVisible(true);
			money.requestFocus();
	}

	
	public static void main(String[] args) {
		new RecordFrame().init();
	}
}
