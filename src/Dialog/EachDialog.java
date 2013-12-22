package Dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;


import Frame.RecordFrame;

import com.eltima.components.ui.DatePicker;

public class EachDialog {
	private JButton save = new JButton("保存修改",new ImageIcon("pic/save.gif"));
	private JButton exit = new JButton("取消",new ImageIcon("pic/exit.gif"));
	private JTextField txtmoney = new JTextField(20);
	private JComboBox cboxresion = new JComboBox(new String[]{"吃饭","零食","衣物","洗漱化妆用品","家电","交通","娱乐","礼品","旅游","其他"});
	private DatePicker dpdatepicker = new DatePicker(null,"yyyy-MM-dd",null,null);
	private JTextArea taadd = new JTextArea();
	private JScrollPane addPane = new JScrollPane(taadd);
	private int m = 0;
	private int rrow = 0;
	private String str0 = null;
	private String str1 = null;
	private String str2 = null;
	private String str3 = null;
	private String str4 = null;
	private JTable tablenew = null;
	private JDialog dia = null;
	private boolean pdtf;
	
	class SaveAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			Date time = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timestr = sf.format(time);
			int n = m;
			int nnum,column45;
			if(pdtf == true){
				nnum = n+1;
				column45 = 5;
			}else{
				nnum = n;
				column45 = 4;
			}

			try{
				switch(n){
				case 0:{
					if(!txtmoney.getText().equals("")){
						Directory dir = FSDirectory.open(new File("index"));
						IndexWriter writer = new IndexWriter(dir,new WhitespaceAnalyzer(),IndexWriter.MaxFieldLength.UNLIMITED);
						Document doc = new Document();
						SimpleDateFormat newsf1 = new SimpleDateFormat("yyyy-MM-dd");
						Date datepickerdate = newsf1.parse(str2);
						doc.add(new Field("resion",str1,Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
						doc.add(new NumericField("money",Field.Store.YES, true).setDoubleValue(Double.valueOf(txtmoney.getText())));
						doc.add(new NumericField("datepicker",Field.Store.YES, true).setLongValue(datepickerdate.getTime()));
						doc.add(new Field("addtext",str3,Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
						doc.add(new NumericField("time",Field.Store.YES, true).setLongValue(new Date().getTime()));
						//IndexSearcher searcher = new IndexSearcher(dir);
						TermQuery tqy = new TermQuery(new Term("resion",str1));
						Query qy = NumericRangeQuery.newLongRange("datepicker", datepickerdate.getTime(), datepickerdate.getTime(), true, true);
						BooleanQuery query = new BooleanQuery();
						query.add(tqy, BooleanClause.Occur.MUST);
						query.add(qy, BooleanClause.Occur.MUST);
						writer.deleteDocuments(query);
						writer.addDocument(doc);
						writer.optimize();
						writer.commit();
						writer.close();
						tablenew.setValueAt(txtmoney.getText(), rrow, nnum);
						tablenew.setValueAt(timestr, rrow, column45);
						
						JOptionPane.showMessageDialog(null,"保存成功！","提示", JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null,"消费金额不能为空，请填写完整！","提示", JOptionPane.WARNING_MESSAGE);
					}
					break;
				}
				case 1:{
					if(!cboxresion.getSelectedItem().toString().equals("")){
						Directory dir = FSDirectory.open(new File("index"));
						IndexWriter writer = new IndexWriter(dir,new WhitespaceAnalyzer(),IndexWriter.MaxFieldLength.UNLIMITED);
						Document doc = new Document();
						SimpleDateFormat newsf1 = new SimpleDateFormat("yyyy-MM-dd");
						Date datepickerdate = newsf1.parse(str2);
						doc.add(new Field("resion",cboxresion.getSelectedItem().toString(),Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
						doc.add(new NumericField("money",Field.Store.YES, true).setDoubleValue(Double.valueOf(str0)));
						doc.add(new NumericField("datepicker",Field.Store.YES, true).setLongValue(datepickerdate.getTime()));
						doc.add(new Field("addtext",str3,Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
						doc.add(new NumericField("time",Field.Store.YES, true).setLongValue(new Date().getTime()));
						IndexSearcher searcher = new IndexSearcher(dir);
						TermQuery tqy1 = new TermQuery(new Term("resion",cboxresion.getSelectedItem().toString()));
						TermQuery tqy2 = new TermQuery(new Term("resion",str1));
						Query qy = NumericRangeQuery.newLongRange("datepicker", datepickerdate.getTime(), datepickerdate.getTime(), true, true);
						BooleanQuery query1 = new BooleanQuery();
						BooleanQuery query2 = new BooleanQuery();
						query1.add(tqy1, BooleanClause.Occur.MUST);
						query1.add(qy, BooleanClause.Occur.MUST);
						query2.add(tqy2, BooleanClause.Occur.MUST);
						query2.add(qy, BooleanClause.Occur.MUST);
						TopDocs matches = searcher.search(query1,10);
						if(matches.totalHits == 0){
							writer.deleteDocuments(query2);
							writer.addDocument(doc);
							writer.optimize();
							writer.commit();
							writer.close();
							tablenew.setValueAt(cboxresion.getSelectedItem().toString(), rrow, nnum);
							tablenew.setValueAt(timestr, rrow, column45);
							
							JOptionPane.showMessageDialog(null,"保存成功！","提示", JOptionPane.INFORMATION_MESSAGE);
						}else{
							writer.optimize();
							writer.commit();
							writer.close();
							JOptionPane.showMessageDialog(null,"你可能已经输入了相同的记录或者出现其他问题，请检查并重新输入！","提示", JOptionPane.WARNING_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null,"消费项不能为空，请填写完整！","提示", JOptionPane.WARNING_MESSAGE);
					}
					
					
					break;
				}
				case 2:{
					if(!dpdatepicker.getText().equals("")){
						Directory dir = FSDirectory.open(new File("index"));
						IndexWriter writer = new IndexWriter(dir,new WhitespaceAnalyzer(),IndexWriter.MaxFieldLength.UNLIMITED);
						Document doc = new Document();
						SimpleDateFormat newsf1 = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat newsf2 = new SimpleDateFormat("yyyy-MM-dd");
						Date datepickerdate = newsf1.parse(str2);
						Date newDate = newsf2.parse(dpdatepicker.getText());
						doc.add(new Field("resion",str1,Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
						doc.add(new NumericField("money",Field.Store.YES, true).setDoubleValue(Double.valueOf(str0)));
						doc.add(new NumericField("datepicker",Field.Store.YES, true).setLongValue(newDate.getTime()));
						doc.add(new Field("addtext",str3,Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
						doc.add(new NumericField("time",Field.Store.YES, true).setLongValue(new Date().getTime()));
						IndexSearcher searcher = new IndexSearcher(dir);
						TermQuery tqy = new TermQuery(new Term("resion",str1));
						Query qy1 = NumericRangeQuery.newLongRange("datepicker", newDate.getTime(), newDate.getTime(), true, true);
						Query qy2 = NumericRangeQuery.newLongRange("datepicker", datepickerdate.getTime(), datepickerdate.getTime(), true, true);
						BooleanQuery query1 = new BooleanQuery();
						BooleanQuery query2 = new BooleanQuery();
						query1.add(tqy, BooleanClause.Occur.MUST);
						query1.add(qy1, BooleanClause.Occur.MUST);
						query2.add(tqy, BooleanClause.Occur.MUST);
						query2.add(qy2, BooleanClause.Occur.MUST);
						TopDocs matches = searcher.search(query1,10);
						if(matches.totalHits == 0){
							writer.deleteDocuments(query2);
							writer.addDocument(doc);
							writer.optimize();
							writer.commit();
							writer.close();
							tablenew.setValueAt(dpdatepicker.getText(), rrow, nnum);
							tablenew.setValueAt(timestr, rrow, column45);
							
							JOptionPane.showMessageDialog(null,"保存成功！","提示", JOptionPane.INFORMATION_MESSAGE);
						}else{
							writer.optimize();
							writer.commit();
							writer.close();
							JOptionPane.showMessageDialog(null,"你可能已经输入了相同的记录或者出现其他问题，请检查并重新输入！","提示", JOptionPane.WARNING_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null,"消费日期不能为空，请填写完整！","提示", JOptionPane.WARNING_MESSAGE);
					}
					
				
					break;
				}
				case 3:{
				//	System.out.println(taadd.getText());
				//	System.out.println(str1);
				//	System.out.println(str2);
					Directory dir = FSDirectory.open(new File("index"));
					IndexWriter writer = new IndexWriter(dir,new WhitespaceAnalyzer(),IndexWriter.MaxFieldLength.UNLIMITED);
					Document doc = new Document();
					SimpleDateFormat newsf1 = new SimpleDateFormat("yyyy-MM-dd");
					Date datepickerdate = newsf1.parse(str2);
					doc.add(new Field("resion",str1,Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
					doc.add(new NumericField("money",Field.Store.YES, true).setDoubleValue(Double.valueOf(str0)));
					doc.add(new NumericField("datepicker",Field.Store.YES, true).setLongValue(datepickerdate.getTime()));
					doc.add(new Field("addtext",taadd.getText(),Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
					doc.add(new NumericField("time",Field.Store.YES, true).setLongValue(new Date().getTime()));
					//IndexSearcher searcher = new IndexSearcher(dir);
					TermQuery tqy = new TermQuery(new Term("resion",str1));
					Query qy = NumericRangeQuery.newLongRange("datepicker", datepickerdate.getTime(), datepickerdate.getTime(), true, true);
					BooleanQuery query = new BooleanQuery();
					query.add(tqy, BooleanClause.Occur.MUST);
					query.add(qy, BooleanClause.Occur.MUST);
					writer.deleteDocuments(query);
					writer.addDocument(doc);
					writer.optimize();
					writer.commit();
					writer.close();
					tablenew.setValueAt(taadd.getText(), rrow, nnum);
					tablenew.setValueAt(timestr, rrow, column45);
					
					JOptionPane.showMessageDialog(null,"保存成功！","提示", JOptionPane.INFORMATION_MESSAGE);
					break;
					}
				}
			}catch(Exception el){
				el.printStackTrace();	
			}	
		}
	}
	
	class ExitAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			dia.setVisible(false);
		}
	}
	
	class TextAction implements KeyListener {
		public void keyReleased(KeyEvent e){

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			Pattern p = Pattern.compile("[0-9,.]");
			Matcher mm = p.matcher(String.valueOf(e.getKeyChar()));
			boolean b = mm.matches();
			if(!b){    //如果输入的字符匹配数字为false则此次按键事件作废，就不会打印出你按的键的值了
			    e.consume();
			}
			
		     if(String.valueOf(e.getKeyChar()).equals(".") &&  txtmoney.getText().contains(".")){
		    	 e.consume();
		     }
		     if(String.valueOf(e.getKeyChar()).equals(".") && txtmoney.getText().equals("")){
		    	 txtmoney.setText("0");
		     }
		     if(!String.valueOf(e.getKeyChar()).equals(".") &&  txtmoney.getText().equals("0")){
		    	 e.consume();
		     }
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	
	}
	
	public Box getBox(int r, int num,JTable table,JDialog dialog,String money,String resion,String datepicker,String add,String time,boolean tf){
		pdtf = tf;
		Box main = Box.createVerticalBox();
		Box end = Box.createHorizontalBox();
		rrow = r;
		m = num;
		str0 = money;
		str1 = resion;
		str2 = datepicker;
		str3 = add;
		str4 = time;
		tablenew = table;
		dia = dialog;
		save.addActionListener(new SaveAction());
		exit.addActionListener(new ExitAction());
		if(num == 0){
			end.add(Box.createHorizontalStrut(10));
			end.add(save);
			end.add(Box.createHorizontalStrut(35));
			end.add(exit);
			end.add(Box.createHorizontalStrut(10));
			main.add(Box.createVerticalStrut(5));
			main.add(txtmoney);
			main.add(Box.createVerticalStrut(5));
			main.add(end);
			main.add(Box.createVerticalStrut(5));
			txtmoney.setText(money);
			txtmoney.addKeyListener(new TextAction());
		}
		if(num == 1){
			end.add(Box.createHorizontalStrut(10));
			end.add(save);
			end.add(Box.createHorizontalStrut(35));
			end.add(exit);
			end.add(Box.createHorizontalStrut(10));
			main.add(Box.createVerticalStrut(5));
			main.add(cboxresion);
			main.add(Box.createVerticalStrut(5));
			main.add(end);
			main.add(Box.createVerticalStrut(5));
			cboxresion.setSelectedItem(resion);
		}
		if(num == 2){
			end.add(Box.createHorizontalStrut(10));
			end.add(save);
			end.add(Box.createHorizontalStrut(35));
			end.add(exit);
			end.add(Box.createHorizontalStrut(10));
			main.add(Box.createVerticalStrut(5));
			main.add(dpdatepicker);
			main.add(Box.createVerticalStrut(5));
			main.add(end);
			main.add(Box.createVerticalStrut(5));
			dpdatepicker.getInnerTextField().setEditable(false);
			dpdatepicker.getInnerTextField().setText(datepicker);
		}
		if(num == 3){
			addPane.setSize(400,200);
			addPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			taadd.setLineWrap(true);
			end.add(Box.createHorizontalStrut(10));
			end.add(save);
			end.add(Box.createHorizontalStrut(35));
			end.add(exit);
			end.add(Box.createHorizontalStrut(10));
			main.add(Box.createVerticalStrut(5));
			main.add(addPane);
			main.add(Box.createVerticalStrut(5));
			main.add(end);
			main.add(Box.createVerticalStrut(5));
			taadd.setText(add);
		}
		return main;
	}	
	
	public void run() throws IOException{
		JFrame test = new JFrame("test");
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.add(new EachDialog().getBox(0,3,null,null,"QQ","","","","",false));
		test.pack();
		test.setVisible(true);
	}
	
	public static void main(String[] args) throws IOException {
		EachDialog te = new EachDialog();
		te.run();
	}
}
