package ui;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import core.cache.ConfCache;
import core.cache.StringsCache;

public class MyDefaultTableModel extends DefaultTableModel {
	
	private static final long	serialVersionUID	= 3787628506856757502L;
	static Integer initEmptyLineNum = Integer.valueOf(ConfCache.get("initEmptyLineNum"));
	
	private static Vector<Object> tableheader = new Vector<Object>(){
		private static final long	serialVersionUID	= 5266729392214649874L;
		{
			add(StringsCache.get("rowNum"));
			add(StringsCache.get("fileName")); add(StringsCache.get("filePath"));
			add(StringsCache.get("operate")); 
		}
	};
	private static Vector<Vector<Object>> tableModel = new Vector<Vector<Object>>(){
		private static final long	serialVersionUID	= 8089391842464819873L;
		{
			for(int i = 0;i<initEmptyLineNum;i++){
				add(new Vector<Object>(){
					private static final long	serialVersionUID	= 1112180804927014530L;
					{
						add("");
						add("");
						add("");
						add(StringsCache.get("del"));
					}
				});
			}
		}
	};
	
/*	private static Object[]		tableheader			= {
			StringsCache.get("rowNum"), StringsCache.get("fileName"),
			StringsCache.get("filePath"), StringsCache.get("operate") };
	private static Object[][]	tableModel			= {
			{ "", "", "", StringsCache.get("del") },
			{ "", "", "", StringsCache.get("del") },
			{ "", "", "", StringsCache.get("del") },
			{ "", "", "", StringsCache.get("del") } };*/
	
	public MyDefaultTableModel() {
		super(tableModel, tableheader);
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		if(column == 0)
			return row + 1;
		return super.getValueAt(row, column);
	}



	@Override
	public boolean isCellEditable(int row, int column) {
		if(column == 3)
			return true;
		return false;
	}
	
	@Override
	public Class getColumnClass(int column){ 
        return getValueAt(0, column).getClass(); 
    }
	
	
}