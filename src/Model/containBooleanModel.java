package Model;

import javax.swing.table.DefaultTableModel;

public class containBooleanModel extends DefaultTableModel{
	public containBooleanModel(Object[][] cells,Object[] title){
		super(cells,title);
	}
	
	public Class getColumnClass(int c){
		return getValueAt(0,c).getClass();
	}
	
	public boolean isCellEditable(int row,int col){
		if(col == 0)
			return true;
		return false;
	}
}
