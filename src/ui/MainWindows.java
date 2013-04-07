package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.dnd.DropTarget;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumnModel;

import listener.DefActionListener;
import listener.DefDropTargetListener;
import render.JButtonRender;
import core.cache.StartPathRecordCache;
import core.cache.StringsCache;

/**
 * 主窗口
 * 
 * @author szy
 * 
 */
public class MainWindows extends JFrame {

	private static final long	serialVersionUID	= 6093108093350598211L;

	private JComboBox			jComboBox;
	private JButton				jButton;
	private JTable				jTable;
	private JLabel				jLabel1;
	private JScrollPane			jScrollPane;

	public MainWindows() {
		super();
	}

	public void init() {
		this.setTitle(StringsCache.get("title"));
		// 置頂
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(500, 200, 600, 400);
		// 内容面板容器
		Container jPanel = this.getContentPane();

		jComboBox = new JComboBox();
		jLabel1 = new JLabel();
		jButton = new JButton();
		jScrollPane = new JScrollPane();
		jTable = new JTable();

		jLabel1.setText(StringsCache.get("startPath"));
		jComboBox.setEditable(true);
		jComboBox.setModel(new DefaultComboBoxModel(StartPathRecordCache
				.getStartPathRecordCacheArray()));
		jComboBox.setPreferredSize(new Dimension(300, 30));
		jButton.setText(StringsCache.get("execute"));
		Container panel = new JPanel();
		panel.add(jLabel1);
		panel.add(jComboBox);
		panel.add(jButton);
		jPanel.add(panel, BorderLayout.NORTH);

		jTable.setRowHeight(50);
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选
		// 设置列宽,并且当调整Panel时列宽也能相应的变化
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// 若设置它，列宽永远固定
		// jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		jTable = new JTable();
		
		jTable.setModel(new MyDefaultTableModel());
		new JButtonRender(jTable, 3);		
		jButton.addActionListener(new DefActionListener(jComboBox, jTable));
		jTable.setPreferredScrollableViewportSize(new Dimension(600, 300));
		TableColumnModel tcm = jTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(30);
		tcm.getColumn(1).setPreferredWidth(90);
		tcm.getColumn(2).setPreferredWidth(400);
		tcm.getColumn(3).setPreferredWidth(80);

		new DropTarget(jTable, new DefDropTargetListener(jTable));
		jScrollPane.setViewportView(jTable);
		jPanel.add(jScrollPane, BorderLayout.CENTER);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Window window = e.getWindow();
				window.setVisible(false);
				window.dispose();
				System.exit(0);
			}
		});

		this.pack();
		this.setVisible(true);
	}

}
