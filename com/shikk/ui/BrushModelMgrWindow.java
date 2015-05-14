package com.shikk.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.lang.annotation.Target;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import com.shikk.data.BrushInfoAdapter;
import com.shikk.data.BrushModel;
import com.shikk.data.BrushModelAdapter;
import com.shikk.data.DBCenter;
import com.shikk.data.TargetApp;

import javax.swing.JButton;

public class BrushModelMgrWindow extends JFrame implements WindowListener {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					BrushModel brushModel = new BrushModel();
//					brushModel.setName("xxx");
//					brushModel.setInjectFilePath("path");
//					ArrayList<BrushModel> list = new ArrayList<>();
//					list.add(brushModel);
					DBCenter center = DBCenter.getInstance();
					center.openDB(DBCenter.DB_NAME);
					TargetApp app = new TargetApp();
					app.setAppId(17);
					ArrayList<BrushModel> list = (ArrayList<BrushModel>) center.getAllModels(app);
					BrushModelMgrWindow frame = new BrushModelMgrWindow(app,true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private TargetApp app;
	private BrushModelAdapter adapter;
	private JButton btnNewButton;
	private JButton button;
	private ArrayList<BrushModel> list;
	private boolean isEditable;
	private JButton btnNewButton_1;
	private JFileChooser chooser;
	/**
	 * Create the frame.
	 */
	public BrushModelMgrWindow(TargetApp targetapp,boolean isEditable) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 791, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		app = targetapp;
		try {
			DBCenter center = DBCenter.getInstance();
			center.openDB(DBCenter.DB_NAME);
			list = (ArrayList<BrushModel>) center.getAllModels(app);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		this.isEditable= isEditable;
		adapter = new BrushModelAdapter(list,isEditable);
		table = new JTable(adapter);
		table.setBounds(20, 135, 73, 41);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);   //不可整列移动  
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	            if(table.getSelectedColumn()==0){
	            	//如果是第一列的单元格，则返回，不响应点击
	            	return;
	            }
	         //列响应操作
	        }      
		});
		int[] columnWidth = new int[]{10,150,250,250};
		for (int i = 0; i < columnWidth.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
			 table.getColumnModel().getColumn(i).setMinWidth(columnWidth[i]);
		}
		this.table.getColumnModel().getColumn(3).setCellEditor(new MyButtonEditor());
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 23, 626,315);
		contentPane.add(scrollPane);
		
		btnNewButton = new JButton("新增");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNewModel();
			}

			
		});
		btnNewButton.setBounds(646, 23, 119, 23);
		contentPane.add(btnNewButton);
		
		button = new JButton("删除");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteModel();
			}
		});
		button.setBounds(646, 56, 119, 23);
		contentPane.add(button);
		
		btnNewButton_1 = new JButton("完成");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveChanges();
				BrushModelMgrWindow.this.dispose();
			}
		});
		btnNewButton_1.setBounds(329, 348, 93, 23);
		contentPane.add(btnNewButton_1);
		
		addWindowListener(this);
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("d://"));
	}
	
	
	protected void saveChanges() {
		// TODO Auto-generated method stub
		if (isEditable) {
			if (table.isEditing()) {
				table.getCellEditor().stopCellEditing();
			}
			try {
				// 保存当前选中的modelid到appinfo中
				DBCenter center = DBCenter.getInstance();
				center.openDB(DBCenter.DB_NAME);
				List<BrushModel> modelList = adapter.getModelList();
				StringBuilder modelIds = new StringBuilder();
				for (int i = 0; i < modelList.size(); i++) {
					BrushModel brushModel = modelList.get(i);
					if (brushModel.getChecked()) {
						modelIds.append(brushModel.getId()+",");
					}
				}
				if (modelIds.toString().endsWith(",")) {
					modelIds.deleteCharAt(modelIds.length()-1);
				}
				app.setModelIds(modelIds.toString());
				center.updateTargetApp(app);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	protected void deleteModel() {
		if (!isEditable) {
			return;
		}
		int row = table.getSelectedRow();
		if (row != -1) {
			int resualt = JOptionPane.showConfirmDialog(null, "确定要删除吗？！", "嘿",
					JOptionPane.OK_CANCEL_OPTION);
			if (resualt == JOptionPane.OK_OPTION) {
				BrushModel model = adapter.getModelList().get(row);
				try {
					DBCenter center = DBCenter.getInstance();
					center.openDB(DBCenter.DB_NAME);
					center.deleteModel(model,app);
					adapter.getModelList().remove(row);
					list = (ArrayList<BrushModel>) center.getAllModels(app);
					table.repaint();
					table.updateUI();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} else {
			JOptionPane.showMessageDialog(null, "请选择一个app先", "嘿",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}


	private void addNewModel() {
		if (!isEditable) {
			return;
		}
		adapter.addRow();
		
		try {
			DBCenter center = DBCenter.getInstance();
			center.openDB(DBCenter.DB_NAME);
			center.addNewModel(new BrushModel(), app);
			list = (ArrayList<BrushModel>) center.getAllModels(app);
			table.updateUI();
			table.repaint();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public class MyButtonEditor extends DefaultCellEditor 
	{ 
	    /**
	     * serialVersionUID
	     */ 
	    private static final long serialVersionUID = -6546334664166791132L; 
	    private JPanel panel; 
	    private JButton button; 
	    private int row;
	    public MyButtonEditor() 
	    { 
	        // DefautlCellEditor有此构造器，需要传入一个，但这个不会使用到，直接new一个即可。  
	        super(new JTextField()); 
	        // 设置点击几次激活编辑。  
	        this.setClickCountToStart(1); 
	        this.initButton(); 
	        this.panel = new JPanel(); 
	        // panel使用绝对定位，这样button就不会充满整个单元格。  
	        this.panel.setLayout(new BorderLayout()); 
	        // 添加按钮。  
	        this.panel.add(this.button); 
	    } 
	 
	    private void initButton() 
	    { 
	        this.button = new JButton(); 
	        // 设置按钮的大小及位置。  
	        this.button.setBounds(0, 0, 50, 15); 
	        // 为按钮添加事件。这里只能添加ActionListner事件，Mouse事件无效。  
	        this.button.addActionListener(new ActionListener() 
	        { 
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					// 触发取消编辑的事件，不会调用tableModel的setValue方法。  
	                MyButtonEditor.this.fireEditingCanceled(); 
	                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设定只能选择到文件  
		            int state = chooser.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句  
		            if (state == JFileChooser.CANCEL_OPTION) {  
		                return;// 撤销则返回  
		            } else {  
		                File f = chooser.getSelectedFile();// f为选择到的文件  
		                if (isEditable) {
		                	 table.setValueAt(f.getAbsolutePath(), row, 3);
						}
		            }  
	               
	                // 这里可以做其它操作。  
	                // 可以将table传入，通过getSelectedRow,getSelectColumn方法获取到当前选择的行和列及其它操作等。  
				} 
	        }); 
	 
	    } 
	 
	 
	    /**
	     * 这里重写父类的编辑方法，返回一个JPanel对象即可（也可以直接返回一个Button对象，但是那样会填充满整个单元格）
	     */ 
	    @Override 
	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) 
	    { 
	        // 只为按钮赋值即可。也可以作其它操作。  
	        this.button.setText(value == null ? "" : String.valueOf(value)); 
	        this.row = row;
	        System.out.println("value:"+value);
	        return this.panel; 
	    } 
	 
	    /**
	     * 重写编辑单元格时获取的值。如果不重写，这里可能会为按钮设置错误的值。
	     */ 
	    @Override 
	    public Object getCellEditorValue() 
	    { 
	        return this.button.getText(); 
	    } 
	 
	}


	@Override
	public void windowActivated(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		System.out.println("brushModelWindow  windowClosed");
		saveChanges();
	}


	@Override
	public void windowClosing(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowOpened(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		
	}  
}
