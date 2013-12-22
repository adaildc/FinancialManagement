package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import Dialog.EachDialog;
import Frame.RecordFrame.FirstAction;
import Frame.RecordFrame.SecendAction;
import Frame.RecordFrame.TextAction;
import Frame.RecordFrame.ThirdAction;
import Model.containBooleanModel;

import com.eltima.components.ui.DatePicker;

public class QueryManagementFrame{
	private JFrame frame = new JFrame("查询管理系统");
	private DatePicker datepicker1 = new DatePicker(null,"yyyy-MM-dd",null,null);
	private DatePicker datepicker2 = new DatePicker(null,"yyyy-MM-dd",null,null);
	private DatePicker datepicker3 = new DatePicker(null,"yyyy-MM-dd",null,null);
	private DatePicker datepicker4 = new DatePicker(null,"yyyy-MM-dd",null,null);
	private JTextField money = new JTextField(20);
	private JTextField money1 = new JTextField(20);
	private JTextField money2 = new JTextField(20);
	private JLabel moneyLabel = new JLabel("消费金额选择：");
	private JLabel resionLabel = new JLabel("消费项选择：");
	private JComboBox qq = new JComboBox(new String[]{" ="," <"," >"," <="," >="});
	private JComboBox resion = new JComboBox(new String[]{"吃饭","零食","衣物","洗漱化妆用品","家电","交通","娱乐","礼品","旅游","其他"});
	private JLabel dateLabel = new JLabel("消费日期选择：");
	private JLabel addLabel = new JLabel("说明选择：");
	private JLabel timeLabel = new JLabel("记录时间选择：");
	private JTextArea add = new JTextArea(2,1);
	private JScrollPane pane = new JScrollPane(add);
	private JButton button1 = new JButton("查询");
	private JButton button2 = new JButton("重置");
	private JButton button3 = new JButton("首页");
	private JButton button4 = new JButton("进入录入系统");
	private JButton button5 = new JButton("离开");
	private JButton b1 = new JButton("清空");
	private JButton b2 = new JButton("清空");
	private JRadioButton qx = new JRadioButton("全选");
	private JRadioButton rb1 = new JRadioButton("区段选择");
	private JRadioButton rb2 = new JRadioButton("单天选择");
	private JRadioButton rb3 = new JRadioButton("单边选择");
	private JRadioButton rb4 = new JRadioButton("区段选择");
	private JCheckBox cb1 = new JCheckBox("吃饭");
	private JCheckBox cb2 = new JCheckBox("零食");
	private JCheckBox cb3 = new JCheckBox("衣物");
	private JCheckBox cb4 = new JCheckBox("洗漱化妆用品");
	private JCheckBox cb5 = new JCheckBox("家电");
	private JCheckBox cb6 = new JCheckBox("交通");
	private JCheckBox cb7 = new JCheckBox("娱乐");
	private JCheckBox cb8 = new JCheckBox("礼品");
	private JCheckBox cb9 = new JCheckBox("旅游");
	private JCheckBox cb10 = new JCheckBox("其他");
	private JLabel lc = new JLabel("从");
	private JLabel ld = new JLabel("到");
	private JLabel lc1 = new JLabel("从");
	private JLabel ld1 = new JLabel("到");
	private JLabel label1 = new JLabel("若想修改某项请双击待修改项！");
	private JLabel label2 = new JLabel("** 填入的文字必须和记录过的文字完全相同！否则无效 ** ");
	private JLabel label3 = new JLabel("** 进行多项选择可用“；”隔开各项 **  ");
	private JLabel label4 = new JLabel("** 输入“\\”可以查询所有的非空说明！ **  ");
	private JLabel lhj = new JLabel("合计：");
	private JLabel ly = new JLabel("元");
	private JTextField total = new JTextField(20);
	private JButton buttonselect = new JButton("删除所选");
	private JButton buttonall = new JButton("全选");
	private JButton buttonnone = new JButton("全不选");
	private containBooleanModel model = null;
	private JTable tTable = null;
	private Frame parent;
	private Object[][] data = null;
	
	public void TableDialog(Frame parent){
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
			new RecordFrame().init();
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
	
	class TextAction1 implements KeyListener {
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
			
		     if(String.valueOf(e.getKeyChar()).equals(".") &&  money1.getText().contains(".")){
		    	 e.consume();
		     }
		     if(String.valueOf(e.getKeyChar()).equals(".") && money1.getText().equals("")){
		    	 money1.setText("0");
		     }
		     if(!String.valueOf(e.getKeyChar()).equals(".") &&  money1.getText().equals("0")){
		    	 e.consume();
		     }
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	
	}
	
	class TextAction2 implements KeyListener {
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
			
		     if(String.valueOf(e.getKeyChar()).equals(".") &&  money2.getText().contains(".")){
		    	 e.consume();
		     }
		     if(String.valueOf(e.getKeyChar()).equals(".") && money2.getText().equals("")){
		    	 money2.setText("0");
		     }
		     if(!String.valueOf(e.getKeyChar()).equals(".") &&  money2.getText().equals("0")){
		    	 e.consume();
		     }
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	
	}
	
	class JCRButtonAction1 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			datepicker1.setEnabled(true);
			datepicker2.setEnabled(true);
			datepicker3.setEnabled(false);
		}
		
	}
	
	class JCRButtonAction2 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			datepicker1.setEnabled(false);
			datepicker2.setEnabled(false);
			datepicker3.setEnabled(true);
		}
		
	}
	
	class JCRButtonAction3 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			qq.setEnabled(true);
			money.setEnabled(true);
			money1.setEnabled(false);
			money2.setEnabled(false);
		}
		
	}
	
	class JCRButtonAction4 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			qq.setEnabled(false);
			money.setEnabled(false);
			money1.setEnabled(true);
			money2.setEnabled(true);
		}
		
	}
	
	class JCRButtonAction5 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(qx.isSelected() == true){
				cb1.setSelected(true);
				cb2.setSelected(true);
				cb3.setSelected(true);
				cb4.setSelected(true);
				cb5.setSelected(true);
				cb6.setSelected(true);
				cb7.setSelected(true);
				cb8.setSelected(true);
				cb9.setSelected(true);
				cb10.setSelected(true);
			}else{
				cb1.setSelected(false);
				cb2.setSelected(false);
				cb3.setSelected(false);
				cb4.setSelected(false);
				cb5.setSelected(false);
				cb6.setSelected(false);
				cb7.setSelected(false);
				cb8.setSelected(false);
				cb9.setSelected(false);
				cb10.setSelected(false);
			}
		}
		
	}
	
	class b1Action implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			datepicker1.getInnerTextField().setText("");
			datepicker2.getInnerTextField().setText("");
			datepicker3.getInnerTextField().setText("");
		}
		
	}
	
	class b2Action implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			datepicker4.getInnerTextField().setText("");
		}
		
	}
	
	class deleteSelectAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int numb = JOptionPane.showConfirmDialog(null,"您确定删除选择的记录?","删除",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(numb == 0){
				try{
					
					int num = tTable.getRowCount();
					SimpleDateFormat newsf = new SimpleDateFormat("yyyy-MM-dd");
					for(int i = 0;i < num;i++){
						if(Boolean.valueOf(tTable.getValueAt(i, 0).toString())){
							String res = tTable.getValueAt(i, 2).toString();
							Date newDatePic = newsf.parse(tTable.getValueAt(i, 3).toString());
							Directory dir = FSDirectory.open(new File("index"));
							IndexWriter writer = new IndexWriter(dir,new WhitespaceAnalyzer(),IndexWriter.MaxFieldLength.UNLIMITED);
							IndexSearcher searcher = new IndexSearcher(dir);
							TermQuery tqy = new TermQuery(new Term("resion",res));
							Query qy = NumericRangeQuery.newLongRange("datepicker", newDatePic.getTime(), newDatePic.getTime(), true, true);
							BooleanQuery query = new BooleanQuery();
							query.add(tqy, BooleanClause.Occur.MUST);
							query.add(qy, BooleanClause.Occur.MUST);
							writer.deleteDocuments(query);
							writer.optimize();
							writer.commit();
							writer.close();
						}
					}
					for(int i = 0; i < num && num > 0;i++){
						if(Boolean.valueOf(tTable.getValueAt(i, 0).toString())){
							model.removeRow(i);
							num--;
							i--;
						}
					}
				}catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		
	}
	
	class selectAllAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int num = tTable.getRowCount();
			for(int i = 0;i < num;i++){
				tTable.setValueAt(true, i, 0);
			}
		}
	}
	
	class selectNoneAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int num = tTable.getRowCount();
			for(int i = 0;i < num;i++){
				tTable.setValueAt(false, i, 0);
			}
		}
	}
	
	class QueryAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				try {
					Directory dir = FSDirectory.open(new File("index"));
					IndexSearcher searcher = new IndexSearcher(dir);
					Query dateQuery = new MatchAllDocsQuery(),resionQuery = new MatchAllDocsQuery(),
							moneyQuery = new MatchAllDocsQuery(),addQuery = new MatchAllDocsQuery(),timeQuery = new MatchAllDocsQuery();
					Query rq1,rq2,rq3,rq4,rq5,rq6,rq7,rq8,rq9,rq10;
					Query aq1,aq2;
					BooleanQuery finalQuery = new BooleanQuery();
					if(rb1.isSelected()){
						if(datepicker1.getText().equals("") && datepicker2.getText().equals("")){
							dateQuery = new MatchAllDocsQuery();
						}
						if(datepicker1.getText().equals("") && !datepicker2.getText().equals("")){
							SimpleDateFormat newsf = new SimpleDateFormat("yyyy-MM-dd");
							Date date = newsf.parse(datepicker2.getText());
							dateQuery = NumericRangeQuery.newLongRange("datepicker", (long)0, date.getTime(), false, true);
						}
						if(!datepicker1.getText().equals("") && datepicker2.getText().equals("")){
							SimpleDateFormat newsf = new SimpleDateFormat("yyyy-MM-dd");
							Date date = newsf.parse(datepicker1.getText());
							Date today = new Date();
							dateQuery = NumericRangeQuery.newLongRange("datepicker", date.getTime(), today.getTime(), true, true);
						}
						if(!datepicker1.getText().equals("") && !datepicker2.getText().equals("")){
							SimpleDateFormat newsf = new SimpleDateFormat("yyyy-MM-dd");
							Date date1 = newsf.parse(datepicker1.getText());
							Date date2 = newsf.parse(datepicker2.getText());
							dateQuery = NumericRangeQuery.newLongRange("datepicker", date1.getTime(), date2.getTime(), true, true);
						}
					}
					if(rb2.isSelected()){
						if(datepicker3.getText().equals("")){
							dateQuery = new MatchAllDocsQuery();
						}
						if(!datepicker3.getText().equals("")){
							SimpleDateFormat newsf = new SimpleDateFormat("yyyy-MM-dd");
							Date date = newsf.parse(datepicker3.getText());
							dateQuery = NumericRangeQuery.newLongRange("datepicker", date.getTime(), date.getTime(), true, true);
						}
					}
					
					
					if(!cb1.isSelected() && !cb2.isSelected() && !cb3.isSelected() && !cb4.isSelected() && !cb5.isSelected()
							 && !cb6.isSelected() && !cb7.isSelected() && !cb8.isSelected() && !cb9.isSelected()
							 && !cb10.isSelected()){
						resionQuery = new MatchAllDocsQuery();
					}else{
						resionQuery = new BooleanQuery();
						if(cb1.isSelected()){
							rq1 = new TermQuery(new Term("resion","吃饭"));
							((BooleanQuery) resionQuery).add(rq1,BooleanClause.Occur.SHOULD);
						}
						if(cb2.isSelected()){
							rq2 = new TermQuery(new Term("resion","零食"));
							((BooleanQuery) resionQuery).add(rq2,BooleanClause.Occur.SHOULD);
						}
						if(cb3.isSelected()){
							rq3 = new TermQuery(new Term("resion","衣物"));
							((BooleanQuery) resionQuery).add(rq3,BooleanClause.Occur.SHOULD);
						}
						if(cb4.isSelected()){
							rq4 = new TermQuery(new Term("resion","洗漱化妆用品"));
							((BooleanQuery) resionQuery).add(rq4,BooleanClause.Occur.SHOULD);
						}
						if(cb5.isSelected()){
							rq5 = new TermQuery(new Term("resion","家电"));
							((BooleanQuery) resionQuery).add(rq5,BooleanClause.Occur.SHOULD);
						}
						if(cb6.isSelected()){
							rq6 = new TermQuery(new Term("resion","交通"));
							((BooleanQuery) resionQuery).add(rq6,BooleanClause.Occur.SHOULD);
						}
						if(cb7.isSelected()){
							rq7 = new TermQuery(new Term("resion","娱乐"));
							((BooleanQuery) resionQuery).add(rq7,BooleanClause.Occur.SHOULD);
						}
						if(cb8.isSelected()){
							rq8 = new TermQuery(new Term("resion","礼品"));
							((BooleanQuery) resionQuery).add(rq8,BooleanClause.Occur.SHOULD);
						}
						if(cb9.isSelected()){
							rq9 = new TermQuery(new Term("resion","旅游"));
							((BooleanQuery) resionQuery).add(rq9,BooleanClause.Occur.SHOULD);
						}
						if(cb10.isSelected()){
							rq10 = new TermQuery(new Term("resion","其他"));
							((BooleanQuery) resionQuery).add(rq10,BooleanClause.Occur.SHOULD);
						}
					}
					
					if(rb3.isSelected() && !money.getText().equals("")){
						if(qq.getSelectedItem() == " ="){
							moneyQuery = NumericRangeQuery.newDoubleRange("money", 
									Double.valueOf(money.getText()), Double.valueOf(money.getText()), true, true);
						}
						if(qq.getSelectedItem() == " <"){
							moneyQuery = NumericRangeQuery.newDoubleRange("money", 
									0.0, Double.valueOf(money.getText()), true, false);
						}
						if(qq.getSelectedItem() == " >"){
							moneyQuery = NumericRangeQuery.newDoubleRange("money", 
									Double.valueOf(money.getText()), Double.MAX_VALUE, false, true);
						}
						if(qq.getSelectedItem() == " <="){
							moneyQuery = NumericRangeQuery.newDoubleRange("money", 
									0.0, Double.valueOf(money.getText()), true, true);
						}
						if(qq.getSelectedItem() == " >="){
							moneyQuery = NumericRangeQuery.newDoubleRange("money", 
									Double.valueOf(money.getText()), Double.MAX_VALUE, true, true);
						}
					}
					if(rb3.isSelected() && money.getText().equals("")){
						moneyQuery = new MatchAllDocsQuery();
					}
					if(rb4.isSelected()){
						if(money1.getText().equals("") && money2.getText().equals("")){
							moneyQuery = new MatchAllDocsQuery();
						}
						if(money1.getText().equals("") && !money2.getText().equals("")){
							
							moneyQuery = NumericRangeQuery.newDoubleRange("money", 0.0, Double.valueOf(money2.getText()), true, true);
						}
						if(!money1.getText().equals("") && money2.getText().equals("")){
							
							moneyQuery = NumericRangeQuery.newDoubleRange("money", Double.valueOf(money1.getText()), Double.MAX_VALUE, true, true);
						}
						if(!money1.getText().equals("") && !money2.getText().equals("")){
							
							moneyQuery = NumericRangeQuery.newDoubleRange("money", Double.valueOf(money1.getText()), 
									Double.valueOf(money2.getText()), true, true);
						}
					}
					
					
					if(add.getText().equals("")){
						addQuery = new MatchAllDocsQuery();
					}else{
						if(add.getText().equals("\\")){
							aq1 = new TermQuery(new Term("addtext",""));
							aq2 = new MatchAllDocsQuery();
							addQuery = new BooleanQuery();
							((BooleanQuery) addQuery).add(aq1,BooleanClause.Occur.MUST_NOT);
							((BooleanQuery) addQuery).add(aq2,BooleanClause.Occur.MUST);
						}else{
							if(!add.getText().contains(";") && !add.getText().contains("；")){
								addQuery = new TermQuery(new Term("addtext",add.getText()));
							}else{
								String[] subStr = add.getText().split(";|；");
								String str = null;
								for(String s:subStr){
									str = str + " || " + s;
								}
								QueryParser parser = new QueryParser(Version.LUCENE_30,"addtext",new WhitespaceAnalyzer());
								addQuery = parser.parse(str); 
							}
						}
					}
					
					
					if(datepicker4.getText().equals("")){
						timeQuery = new MatchAllDocsQuery();
					}else{
						SimpleDateFormat newsf = new SimpleDateFormat("yyyy-MM-dd");
						Date date = newsf.parse(datepicker4.getText());
						timeQuery = NumericRangeQuery.newLongRange("time", date.getTime(), date.getTime() + (long)86400000, true, false);
					}
					
					
					
					finalQuery.add(dateQuery, BooleanClause.Occur.MUST);
					finalQuery.add(resionQuery, BooleanClause.Occur.MUST);
					finalQuery.add(moneyQuery, BooleanClause.Occur.MUST);
					finalQuery.add(addQuery, BooleanClause.Occur.MUST);
					finalQuery.add(timeQuery, BooleanClause.Occur.MUST);
					
					TopDocs matches = searcher.search(finalQuery, 10);
					TopDocs docMatch;
					if(matches.totalHits > 0)
						docMatch = searcher.search(finalQuery, matches.totalHits);
					else docMatch = matches;
					data = new Object[docMatch.totalHits][7];
					ScoreDoc[] allDoc = docMatch.scoreDocs;
					Double sum = 0.0;
					for(int i=0;i<allDoc.length;i++){
						Date toDate = new Date(Long.valueOf(searcher.doc(allDoc[i].doc).get("datepicker")));
						Date addDate = new Date(Long.valueOf(searcher.doc(allDoc[i].doc).get("time")));
						SimpleDateFormat tosf1 = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat tosf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String toNewDate = tosf1.format(toDate);
						String addNewDate = tosf2.format(addDate);
						data[i][0] = false;
						data[i][1] = searcher.doc(allDoc[i].doc).get("money");
						sum += Double.valueOf(searcher.doc(allDoc[i].doc).get("money"));
						data[i][2] = searcher.doc(allDoc[i].doc).get("resion");
						data[i][3] = toNewDate;
						data[i][4] = searcher.doc(allDoc[i].doc).get("addtext");
						data[i][5] = addNewDate;
						data[i][6] = "双击删除";
					}
					Object[] title = {"","消费金额","消费项","消费日期","说明","记录修改时间","操作"};
					model.setDataVector(data, title);
					tableStyle(tTable);
					total.setText(sum.toString());
					searcher.close();
				} catch (Exception el) {
					// TODO Auto-generated catch block
					el.printStackTrace();
				}
			
		}
		
	}
	
	class resetAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			rb1.setSelected(true);
			rb3.setSelected(true);
			qx.setSelected(false);
			cb1.setSelected(false);
			cb2.setSelected(false);
			cb3.setSelected(false);
			cb4.setSelected(false);
			cb5.setSelected(false);
			cb6.setSelected(false);
			cb7.setSelected(false);
			cb8.setSelected(false);
			cb9.setSelected(false);
			cb10.setSelected(false);
			datepicker1.getInnerTextField().setText("");
			datepicker2.getInnerTextField().setText("");
			datepicker3.getInnerTextField().setText("");
			datepicker4.getInnerTextField().setText("");
			add.setText("");
			money.setText("");
			money1.setText("");
			money2.setText("");
			qq.setSelectedItem(" =");
		}
		
	}
	
	public void tableStyle(JTable table){
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		  table.setShowHorizontalLines(false);
		  table.setShowVerticalLines(false);
		  table.setRowHeight(20);
		  table.getColumnModel().getColumn(0).setMaxWidth(30);
		  table.getColumnModel().getColumn(0).setMinWidth(30);
		  table.getColumnModel().getColumn(1).setMaxWidth(80);
		  table.getColumnModel().getColumn(1).setMinWidth(80);
		  table.getColumnModel().getColumn(2).setMaxWidth(70);
		  table.getColumnModel().getColumn(2).setMinWidth(70);
		  table.getColumnModel().getColumn(3).setMaxWidth(100);
		  table.getColumnModel().getColumn(3).setMinWidth(100);
		  table.getColumnModel().getColumn(5).setMaxWidth(210);
		  table.getColumnModel().getColumn(5).setMinWidth(210);
		  table.getColumnModel().getColumn(6).setMaxWidth(60);
		  table.getColumnModel().getColumn(6).setMinWidth(60);
		  
		  table.getTableHeader().setReorderingAllowed(false);
		  table.getTableHeader().setResizingAllowed(false);
		  
		  table.getColumnModel().getColumn(1).setCellRenderer(renderer);
		  table.getColumnModel().getColumn(2).setCellRenderer(renderer);
		  table.getColumnModel().getColumn(3).setCellRenderer(renderer);
		  table.getColumnModel().getColumn(5).setCellRenderer(renderer);
		  table.getColumnModel().getColumn(6).setCellRenderer(renderer);
		  
		  table.setCellSelectionEnabled(true);
	}
	
	public void init(){
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
		  
		  ButtonGroup group1 = new ButtonGroup();
		  ButtonGroup group2 = new ButtonGroup();
		  group1.add(rb1);
		  group1.add(rb2);
		  group2.add(rb3);
		  group2.add(rb4);
		  rb1.setSelected(true);
		  rb3.setSelected(true);
		  
		  datepicker3.setEnabled(false);
		  money1.setEnabled(false);
		  money2.setEnabled(false);
		  
		  rb1.addActionListener(new JCRButtonAction1());
		  rb2.addActionListener(new JCRButtonAction2());
		  rb3.addActionListener(new JCRButtonAction3());
		  rb4.addActionListener(new JCRButtonAction4());
		  qx.addActionListener(new JCRButtonAction5());
		  
		  button1.addActionListener(new QueryAction());
		  b1.addActionListener(new b1Action());
		  b2.addActionListener(new b2Action());
		  buttonselect.addActionListener(new deleteSelectAction());
		  buttonall.addActionListener(new selectAllAction());
		  buttonnone.addActionListener(new selectNoneAction());
		  button2.addActionListener(new resetAction());
		  button3.addActionListener(new FirstAction());
		  button4.addActionListener(new SecendAction());
		  button5.addActionListener(new ThirdAction());
		  
		  Object[] title = {"","消费金额","消费项","消费日期","说明","记录修改时间","操作"};
		  model = new containBooleanModel(data,title);
			tTable = new JTable(model);
			  JScrollPane tablePane = new JScrollPane(tTable);
				tablePane.setPreferredSize(new Dimension(380, 200));
				tablePane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				tableStyle(tTable);
			  
			  tTable.addMouseListener(new MouseAdapter() {
					public void mouseReleased(java.awt.event.MouseEvent e) {
						
						if (e.getClickCount() == 2
								&& SwingUtilities.isLeftMouseButton(e)) {
							int row = tTable.rowAtPoint(e.getPoint());
							int column = tTable.columnAtPoint(e.getPoint());
							String str = "";
							if(column == 1) str = "修改金额";
							if(column == 2) str = "修改选项";
							if(column == 3) str = "修改日期";
							if(column == 4) str = "修改说明";
							if(column == 6){
								int numb = JOptionPane.showConfirmDialog(null,"您确定删除该记录?","删除",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
								if(numb == 0){
									try{
										SimpleDateFormat newsf = new SimpleDateFormat("yyyy-MM-dd");
										Date date = new Date();
										try {
											date = newsf.parse(tTable.getValueAt(row, 2).toString());
										} catch (java.text.ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										Directory dir = FSDirectory.open(new File("index"));
										IndexWriter writer = new IndexWriter(dir,new WhitespaceAnalyzer(),IndexWriter.MaxFieldLength.UNLIMITED);
										IndexSearcher searcher = new IndexSearcher(dir);
										TermQuery tqy = new TermQuery(new Term("resion",tTable.getValueAt(row, 1).toString()));
										Query qy = NumericRangeQuery.newLongRange("datepicker", date.getTime(), date.getTime(), true, true);
										BooleanQuery query = new BooleanQuery();
										query.add(tqy, BooleanClause.Occur.MUST);
										query.add(qy, BooleanClause.Occur.MUST);
										writer.deleteDocuments(query);
										writer.optimize();
										writer.commit();
										writer.close();
									
										model.removeRow(row);
										
									}catch(Exception el){
										el.printStackTrace();
									}
								}
							}else if(column != 5 && column != 0){
								JDialog textData = new JDialog(parent,str,true);
								if(column != 4){
									textData.setMaximumSize(new Dimension(250, 100));
									textData.setMinimumSize(new Dimension(250, 100));
								}else{
									textData.setMaximumSize(new Dimension(600, 200));
									textData.setMinimumSize(new Dimension(600, 200));
								}
								textData.add(new EachDialog().getBox(row,column-1,tTable, textData, tTable.getValueAt(row, 1).toString(), 
										tTable.getValueAt(row, 2).toString(), tTable.getValueAt(row, 3).toString(), 
										tTable.getValueAt(row, 4).toString(),tTable.getValueAt(row, 5).toString(),true));
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
			  money1.addKeyListener(new TextAction1());
			  money2.addKeyListener(new TextAction2());
			  
			  datepicker1.getInnerTextField().setEditable(false);
			  datepicker2.getInnerTextField().setEditable(false);
			  datepicker3.getInnerTextField().setEditable(false);
			  datepicker4.getInnerTextField().setEditable(false);
			  
			  Toolkit tk = Toolkit.getDefaultToolkit();
			  Image image = tk.createImage("pic/query.png"); /* image.gif是你的图标 */
			  frame.setIconImage(image);
			  Image image1 = tk.createImage("pic/tb.png");
			  Cursor cursor = tk.createCustomCursor(image1, new Point(0,0), "love");
			  frame.setCursor(cursor);
			  
			  pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			  add.setLineWrap(true);
			  
			  label1.setForeground(Color.red);
			  label2.setForeground(Color.red);
			  label2.setFont(new Font("隶书", Font.PLAIN, 15));
			  label3.setForeground(Color.red);
			  label3.setFont(new Font("隶书", Font.PLAIN, 15));
			  label4.setForeground(Color.red);
			  label4.setFont(new Font("隶书", Font.PLAIN, 15));
			  
			  total.setEditable(false);
			  
			  Box up = Box.createHorizontalBox();
			  Box down = Box.createHorizontalBox();
			  Box left = Box.createVerticalBox();
			  Box right = Box.createVerticalBox();
			  Box center = Box.createVerticalBox();	
			  
			  Box box1 = Box.createHorizontalBox();
			  Box box2 = Box.createVerticalBox();	
			  Box box3 = Box.createVerticalBox();
			  Box box4 = Box.createVerticalBox();
			  Box box5 = Box.createHorizontalBox();
			  Box box6 = Box.createVerticalBox();
			  Box box7 = Box.createVerticalBox();
			  Box box8 = Box.createVerticalBox();
			  Box box9 = Box.createVerticalBox();
			  Box box10 = Box.createVerticalBox();
			  
			  Box box11 = Box.createVerticalBox();
			  Box box12 = Box.createHorizontalBox();
			  Box box21 = Box.createHorizontalBox();
			  Box box22 = Box.createHorizontalBox();
			  Box box31 = Box.createHorizontalBox();
			  Box box32 = Box.createHorizontalBox();
			  Box box41 = Box.createHorizontalBox();
			  Box box42 = Box.createHorizontalBox();
			  Box box51 = Box.createVerticalBox();
			  Box box61 = Box.createHorizontalBox();
			  Box box71 = Box.createHorizontalBox();
			  Box box91 = Box.createHorizontalBox();
			  Box box101 = Box.createHorizontalBox();
			  
			  ImageIcon ima = new ImageIcon("pic/ilu.jpg");
			  JLabel label = new JLabel(ima);
			  label.setMaximumSize(new Dimension(200,100));
			  label.setMinimumSize(new Dimension(200,100));
			  box12.add(button3);
			  box12.add(Box.createHorizontalStrut(20));
			  box12.add(button4);
			  box12.add(Box.createHorizontalStrut(20));
			  box12.add(button5);
			  box11.add(Box.createVerticalStrut(20));
			  box11.add(box12);
			  
			  box21.add(dateLabel);
			  box21.add(rb1);
			  box21.add(Box.createHorizontalStrut(5));
			  box21.add(lc1);
			  box21.add(Box.createHorizontalStrut(5));
			  box21.add(datepicker1);
			  box21.add(Box.createHorizontalStrut(5));
			  box21.add(ld1);
			  box21.add(Box.createHorizontalStrut(5));
			  box21.add(datepicker2);
			  box21.add(Box.createHorizontalStrut(35));
			  box22.add(Box.createHorizontalStrut(84));
			  box22.add(rb2);
			  box22.add(Box.createHorizontalStrut(5));
			  box22.add(datepicker3);
			  box22.add(Box.createHorizontalStrut(220));
			  box22.add(b1);
			  box22.add(Box.createHorizontalStrut(40));
			  
			  box31.add(resionLabel);
			  box31.add(Box.createHorizontalStrut(12));
			  box31.add(qx);
			  box31.add(cb1);
			  box31.add(cb2);
			  box31.add(cb3);
			  box31.add(cb4);
			  box31.add(cb5);
			  box31.add(cb6);
			  box31.add(cb7);
			  box31.add(cb8);
			  box31.add(cb9);
			  box31.add(cb10);
			  box31.add(Box.createHorizontalStrut(55));
			  
			  box41.add(moneyLabel);
			  box41.add(rb3);
			  box41.add(Box.createHorizontalStrut(5));
			  box41.add(qq);
			  box41.add(Box.createHorizontalStrut(5));
			  box41.add(money);
			  box41.add(Box.createHorizontalStrut(270));
			  box42.add(Box.createHorizontalStrut(84));
			  box42.add(rb4);
			  box42.add(Box.createHorizontalStrut(5));
			  box42.add(lc);
			  box42.add(Box.createHorizontalStrut(5));
			  box42.add(money1);
			  box42.add(Box.createHorizontalStrut(5));
			  box42.add(ld);
			  box42.add(Box.createHorizontalStrut(5));
			  box42.add(money2);
			  box42.add(Box.createHorizontalStrut(35));
			  
			  box51.add(label2);
			  box51.add(label3);
			  box51.add(label4);
			  
			  box61.add(timeLabel);
			  box61.add(datepicker4);
			  box61.add(Box.createHorizontalStrut(20));
			  box61.add(b2);
			  box61.add(Box.createHorizontalStrut(330));
			  
			  box71.add(button1);
			  box71.add(Box.createHorizontalStrut(65));
			  box71.add(button2);
			  
			  box8.add(tablePane);
			  
			  box91.add(lhj);
			  box91.add(Box.createHorizontalStrut(5));
			  box91.add(total);
			  box91.add(Box.createHorizontalStrut(5));
			  box91.add(ly);
			  box91.add(Box.createHorizontalStrut(190));
			  box91.add(buttonall);
			  box91.add(Box.createHorizontalStrut(20));
			  box91.add(buttonnone);
			  box91.add(Box.createHorizontalStrut(20));
			  box91.add(buttonselect);
			  box91.add(Box.createHorizontalStrut(30));
			  
			  box101.add(label1);
			  
			  box1.add(Box.createHorizontalStrut(50));
			  box1.add(label);
			  box1.add(Box.createHorizontalStrut(50));
			  box1.add(box11);
			  box1.add(Box.createHorizontalStrut(50));
			  box2.add(Box.createVerticalStrut(5));
			  box2.add(box21);
			  box2.add(Box.createVerticalStrut(4));
			  box2.add(box22);
			  box3.add(Box.createVerticalStrut(5));
			  box3.add(box31);
			//  box3.add(box32);
			  box4.add(Box.createVerticalStrut(5));
			  box4.add(box41);
			  box4.add(Box.createVerticalStrut(4));
			  box4.add(box42);
			  box4.add(Box.createVerticalStrut(5));
			  box5.add(addLabel);
			  box5.add(Box.createHorizontalStrut(27));
			  box5.add(pane);
			  box5.add(Box.createHorizontalStrut(10));
			  box5.add(box51);
			  box6.add(Box.createVerticalStrut(5));
			  box6.add(box61);
			  box7.add(Box.createVerticalStrut(5));
			  box7.add(box71);
			  box7.add(Box.createVerticalStrut(5));
			  box9.add(Box.createVerticalStrut(5));
			  box9.add(box91);
			  box10.add(Box.createVerticalStrut(5));
			  box10.add(box101);
			  
			  up.add(Box.createVerticalStrut(10));
			  down.add(Box.createVerticalStrut(10));
			  left.add(Box.createHorizontalStrut(10));
			  right.add(Box.createHorizontalStrut(10));
			  center.add(box1);
			  center.add(box2);
			  center.add(box3);
			  center.add(box4);
			  center.add(box5);
			  center.add(box6);
			  center.add(box7);
			  center.add(box8);
			  center.add(box9);
			  center.add(box10);
			  
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
	}
	
	public static void main(String[] args) {
		new QueryManagementFrame().init();
	}
}