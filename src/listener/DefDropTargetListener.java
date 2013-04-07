package listener;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import core.ToolKit;
import core.cache.StringsCache;

@SuppressWarnings("unchecked")
public class DefDropTargetListener implements DropTargetListener {
	
	private JTable jTable;
	
	public DefDropTargetListener(JTable jTable) {
		super();
		this.jTable = jTable;
	}

	public void dragEnter(DropTargetDragEvent droptargetdragevent) {

	}

	public void dragExit(DropTargetEvent droptargetevent) {

	}

	public void dragOver(DropTargetDragEvent droptargetdragevent) {

	}

	public void drop(DropTargetDropEvent event) {
		if (!isDropAcceptable(event)) {
			event.rejectDrop();
			return;
		}
		event.acceptDrop(DnDConstants.ACTION_COPY);
		
		Transferable transferable = event.getTransferable();
		DataFlavor[] dataFlavors = transferable.getTransferDataFlavors();
		DefaultTableModel myTableModel = null;
		String filePath = null;
		String fileName = null;
		for (DataFlavor dataFlavor : dataFlavors) {
			myTableModel = (DefaultTableModel) jTable.getModel();
			
			//删除空行
			Vector<Vector> dv = myTableModel.getDataVector();
			Iterator<Vector> iterator = dv.iterator();
			while(iterator.hasNext()){
				Vector v = iterator.next();
				if(ToolKit.isEmpty(v.get(0))){
					iterator.remove();
				}
			}
			try {
				if (dataFlavor.equals(DataFlavor.javaFileListFlavor)) {
					//如果是文件
					List<File> dataFiles = (List<File>) transferable
							.getTransferData(dataFlavor);
					for (File file : dataFiles) {
						appendFileToTable(file,myTableModel);
					}
				} else if (dataFlavor.equals(DataFlavor.stringFlavor)) {
					//如果是字符串
					Object dataObj = transferable
							.getTransferData(dataFlavor);
					String dataStr = dataObj.toString();
					BufferedReader bufRed = null;
					String line = null;
					if(ToolKit.isNotEmpty(dataObj)){
						bufRed = new BufferedReader(new StringReader(dataStr));
						while(null != (line = bufRed.readLine())){
							line = line .replace("/", "\\");
							filePath = line;
							fileName = ToolKit.fetchFileNameByFilePath(filePath);
							myTableModel.addRow(new Object[]{"rowNum",fileName,filePath,StringsCache.get("del")});
						}
					}
				}
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		event.dropComplete(true);
	}
	
	private void appendFileToTable(File file,DefaultTableModel myTableModel){
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File f : files){
				if(f.isDirectory()){
					appendFileToTable(f,myTableModel);
				}else{
					String filePath = f.getPath();
					String fileName = f.getName();
					myTableModel.addRow(new Object[]{"rowNum",fileName,filePath,StringsCache.get("del")});
				}
			}
		}else if(file.isFile()){
			String filePath = file.getPath();
			String fileName = ToolKit.fetchFileNameByFilePath(filePath);
			myTableModel.addRow(new Object[]{"rowNum",fileName,filePath,StringsCache.get("del")});
		}
	}
	
	
	public void dropActionChanged(DropTargetDragEvent droptargetdragevent) {

	}

	public boolean isDragAcceptable(DropTargetDragEvent event) {
		return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
	}

	public boolean isDropAcceptable(DropTargetDropEvent event) {
		return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
	}

}
