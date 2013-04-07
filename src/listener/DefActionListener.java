package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import core.ToolKit;
import core.cache.ConfCache;
import core.handle.PatchHandle;
import core.handle.UIDataHandle;

/**
 * 鼠標的單擊事件監聽器
 * @author szy
 *
 */
public class DefActionListener implements ActionListener{

	//下拉框
	private JComboBox comboBox;
	private JTable jTable;
	
	public DefActionListener(JComboBox comboBox,JTable table) {
		super();
		this.comboBox = comboBox;
		this.jTable = table;
	}
	
	
	public void actionPerformed(ActionEvent event) {
		//下拉框處理 
		comboboxHandle();
		//表格處理 
		Boolean e = tableHandle();
		//
		if(e)
			JOptionPane.showMessageDialog(jTable,"执行完毕,请查看桌面!");
	}
	
	private String searchDir;
	
	/**
	 * 下拉框處理 
	 */
	public void comboboxHandle(){
		//下拉框的值
		searchDir = this.comboBox.getSelectedItem().toString();
		//UI处理
		UIDataHandle.handleComboboxData(searchDir,comboBox);
	}
	
	/**
	 * 表格处理 
	 */
	@SuppressWarnings("unchecked") 
	public Boolean tableHandle(){
		DefaultTableModel tm = (DefaultTableModel)jTable.getModel();
		Vector<Vector<Object>> vcr = tm.getDataVector();
		
		List<String> fileList = new ArrayList<String>();
		for (Vector<Object> vo : vcr) {
			Vector<Object> v = (Vector<Object>) vo;
			String filePath = v.get(
					Integer.valueOf(ConfCache.get("filePathIndex"))).toString();
			if(ToolKit.isNotEmpty(filePath)){
				if(filePath.contains(".java")){
					filePath = filePath.replaceAll(".java", ".class");
				}
				fileList.add(filePath);
			}
		}
		if(fileList.size() <= 0){
			JOptionPane.showMessageDialog(jTable,"表格为空，Return!");
			return false;
		}
		
		//执行处理 
		new PatchHandle(fileList,searchDir).handle();
		return true;
	}

}
