package com.shikk.ui;

import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.shikk.data.AppInfoAdapter;
import com.shikk.data.AppInfoCellRender;
import com.shikk.data.BrushModel;
import com.shikk.data.DBCenter;
import com.shikk.data.TargetApp;
import com.shikk.util.CmdCommand;
import com.shikk.util.CommandResult;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.Scrollbar;
import java.awt.ScrollPane;
import java.awt.Panel;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JComboBox;

import org.jsoup.select.Evaluator.IsEmpty;

public class AppInfoWindow implements WindowListener{

	public static final int ACTION_ADD_NEW_APP = 0;
	public static final int ACTION_MONIFY_APP  = 1;
	public static final int ACTION_VIEW_APP    = 2;
	public JFrame frame;
	private JTable table;
	private boolean canEdite;
	private int action; 
	private TargetApp app;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton button;
	private JTextField textField_4;
	private JFileChooser chooser;
	private JComboBox<String> comboBox;
	private JTextField textField_5;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TargetApp app = new TargetApp();
					AppInfoWindow window = new AppInfoWindow(app,ACTION_ADD_NEW_APP);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppInfoWindow(TargetApp app,int action) {
		this.app = app;
		this.action = action;
		if (action == ACTION_ADD_NEW_APP || action == ACTION_MONIFY_APP) {
			this.canEdite = true;
		}else{
			this.canEdite = false;
		}
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 459);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(this);
		boolean isNewApp = false;
		if (action == ACTION_ADD_NEW_APP) {
			isNewApp = true;
		}
		AppInfoAdapter adapter = new AppInfoAdapter(app,isNewApp,canEdite);
		table = new JTable(adapter);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);   //不可整列移动  
		 table.setCellSelectionEnabled(true);
//		 table.setCellEditor(new AppInfoCellRender());
		 for (int i = 0; i < table.getColumnCount(); i++) {
			 table.getColumn(i+"").setCellRenderer(new AppInfoCellRender());
		}
		 table.setTableHeader(null);
		int[] columnWidth = new int[]{140,50,50,50,50,50,50};
		for (int i = 0; i < columnWidth.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
			 table.getColumnModel().getColumn(i).setMinWidth(columnWidth[i]);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 163, 624, 214);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(117, 9, 159, 21);
		textField.setEditable(canEdite);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(117, 40, 159, 21);
		textField_1.setEditable(canEdite);
		
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		label = new JLabel("应用名称：");
		label.setBounds(10, 13, 81, 15);
		frame.getContentPane().add(label);
		
		label_1 = new JLabel("包名：");
		label_1.setBounds(10, 43, 81, 15);
		frame.getContentPane().add(label_1);
		
		label_2 = new JLabel("版本名称：");
		label_2.setBounds(10, 74, 81, 15);
		frame.getContentPane().add(label_2);
		
		label_3 = new JLabel("版本号：");
		label_3.setBounds(10, 105, 81, 15);
		frame.getContentPane().add(label_3);
		
		textField_2 = new JTextField();
		textField_2.setBounds(117, 71, 159, 21);
		textField_2.setEditable(canEdite);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(117, 102, 159, 21);
		textField_3.setEditable(canEdite);
		
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(117, 133, 104, 21);
		frame.getContentPane().add(textField_4);
		textField_4.setEditable(canEdite);
		textField_4.setColumns(10);
		textField_5 = new JTextField();
		textField_5.setBounds(415, 40, 219, 21);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);
		
		if (app!= null) {
			textField.setText(app.getAppName());
			textField_1.setText(app.getPkgName());
			textField_2.setText(app.getVersionName());
			textField_3.setText(app.getVersionCode()+"");
			if (app.getInjectFile()!=null) {
				textField_4.setText(app.getInjectFile().getAbsolutePath());
			}else{
				textField_4.setText("");
			}
			textField_5.setText(app.getUIDPercent()+"");
		}
		button = new JButton("完成");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				if (table.isEditing()) {
					table.getCellEditor().stopCellEditing();
				}
				if (action == ACTION_ADD_NEW_APP) {
					NumberFormat df = DecimalFormat.getPercentInstance();
					TargetApp app = new TargetApp();
					app.setAppName(textField.getText());
					app.setPkgName(textField_1.getText());
					app.setVersionName(textField_2.getText());
					app.setVersionCode(Integer.parseInt(textField_3.getText()));
					String injectFilePath = textField_4.getText();
					String uidPercent = textField_5.getText();
					app.setUIDPercent(Float.parseFloat(uidPercent));
					String baseVmName = (String) comboBox.getSelectedItem();
					app.setBaseVmName(baseVmName);
					if (injectFilePath != null && !injectFilePath.equals("")) {
						File file = new File(injectFilePath);
						if (file.exists() && file.isFile()) {
							app.setInjectFile(file);
						}else{
							JOptionPane.showMessageDialog(null, "模拟触摸文件不正确");
							return;
						}
						
					}else{
						JOptionPane.showMessageDialog(null, "模拟触摸文件不能为空！");
						return;
					}
					
					ArrayList<Float> brushList = new ArrayList<>();
					ArrayList<Integer> newQualityList = new ArrayList<>();
					for (int i = 1; i < 10; i+=2) {
						for (int j = 1; j < 8; j++) {
							try {
								brushList.add(df.parse(table.getValueAt(i, j).toString()).floatValue());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					for (int i = 1; i < 8; i++) {
						newQualityList.add(Integer.parseInt(table.getValueAt(11, i).toString()));
					}
					app.setBrushRate(brushList);
					app.setMaxNewQuality(newQualityList);
					
					try {
						DBCenter center = DBCenter.getInstance();
						center.openDB(DBCenter.DB_NAME);
						center.addNewApp(app);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if (action == ACTION_MONIFY_APP) {
					NumberFormat df = DecimalFormat.getPercentInstance();
					TargetApp app = new TargetApp();
					app.setAppId(AppInfoWindow.this.app.getAppId());
					app.setAppName(textField.getText());
					app.setPkgName(textField_1.getText());
					app.setVersionName(textField_2.getText());
					app.setVersionCode(Integer.parseInt(textField_3.getText()));
					String baseVmName = (String) comboBox.getSelectedItem();
					app.setBaseVmName(baseVmName);
					String injectFilePath = textField_4.getText();
					String uidPercent = textField_5.getText();
					app.setUIDPercent(Float.parseFloat(uidPercent));
					if (injectFilePath != null && !injectFilePath.equals("")) {
						File file = new File(injectFilePath);
						if (file.exists() && file.isFile()) {
							app.setInjectFile(file);
						}else{
							JOptionPane.showMessageDialog(null, "模拟触摸文件不正确");
							return;
						}
						
					}else{
						JOptionPane.showMessageDialog(null, "模拟触摸文件不能为空！");
						return;
					}
					ArrayList<Float> brushList = new ArrayList<>();
					ArrayList<Integer> newQualityList = new ArrayList<>();
					for (int i = 1; i < 10; i+=2) {
						for (int j = 1; j < 8; j++) {
							try {
								brushList.add(df.parse(table.getValueAt(i, j).toString()).floatValue());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					for (int i = 1; i < 8; i++) {
						newQualityList.add(Integer.parseInt(table.getValueAt(11, i).toString()));
					}
					app.setBrushRate(brushList);
					app.setMaxNewQuality(newQualityList);
					try {
						DBCenter center = DBCenter.getInstance();
						center.openDB(DBCenter.DB_NAME);
						List<BrushModel> modelList = center.getAllModels(app);
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
				AppInfoWindow.this.frame.dispose();
			}
		});
		button.setBounds(260, 387, 93, 23);
		frame.getContentPane().add(button);
		
		JLabel lblNewLabel = new JLabel("模拟配置文件路径：");
		lblNewLabel.setBounds(10, 138, 133, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("...");
		btnNewButton.setEnabled(canEdite);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设定只能选择到文件  
		            int state = chooser.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句  
		            if (state == JFileChooser.CANCEL_OPTION) {  
		                return;// 撤销则返回  
		            } else {  
		                File f = chooser.getSelectedFile();// f为选择到的文件  
		                if (canEdite) {
		                	textField_4.setText(f.getAbsolutePath());  
						}
		            }  
			}
		});
		btnNewButton.setBounds(231, 130, 45, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("base虚拟机名称：");
		lblNewLabel_1.setBounds(305, 12, 124, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		
		
		Vector<String> vector = new Vector<>();
		CmdCommand command = new CmdCommand();
		CommandResult runWaitFor = command.cmd
				.runWaitFor("VBoxManage list vms");
		int anIndex = 0;
		vector.add("");
		if (runWaitFor.success()) {
			String stdout = runWaitFor.stdout;
			String[] split = stdout.split("\n");
			for (int i = 0; i < split.length; i++) {
				if (split[i] != null && split[i].contains("basevm")) {
					int indexOf = split[i].indexOf("{");
					String substring = split[i].substring(0,indexOf-1);
					vector.add(substring);
					if (app != null && substring.equals(app.getBaseVmName())) {
						anIndex = vector.size()-1;
					}
				}
			}
			if (vector.size() <= 0) {
				vector.add("未发现虚拟机");
			}
		} else {
			vector.add("命令错误：VBoxManage list vms");
		}
		comboBox = new JComboBox<>(vector);
		comboBox.setBounds(415, 13, 219, 21);
		System.out.println("anIndex:"+anIndex);
		comboBox.setSelectedIndex(anIndex);
		comboBox.setEnabled(canEdite);
		frame.getContentPane().add(comboBox);
		
		
		
		JLabel lblUidimei = new JLabel("UID多于IMEI比例：");
		lblUidimei.setBounds(305, 43, 124, 15);
		frame.getContentPane().add(lblUidimei);
		
		
		if (action != ACTION_ADD_NEW_APP) {
			JLabel lblNewLabel_2 = new JLabel("模块内留存：");
			lblNewLabel_2.setBounds(307, 70, 104, 15);
			frame.getContentPane().add(lblNewLabel_2);
			
			String btnStr = "管理";
			if (action == ACTION_VIEW_APP) {
				btnStr = "查看";
			}
			JButton button_1 = new JButton(btnStr);
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent actionevent) {
					
					BrushModelMgrWindow frame = new BrushModelMgrWindow(app,canEdite);
					frame.setVisible(true);
				}
			});
			button_1.setBounds(417, 66, 93, 23);
			frame.getContentPane().add(button_1);
		}
		
		
		
		
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("d://"));
		
		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				System.out.println("tableChanged :"+e.getType());
			}
		});
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("AppInfoWindow  windowClosed");
		TargetAppWindow instance = TargetAppWindow.getInstance();
		if (action != ACTION_VIEW_APP && instance != null) {
			instance.refreshUI();
		}
		if (instance != null) {
			instance.frame.setEnabled(true);
			instance.frame.setVisible(true);
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("AppInfoWindow  windowClosing");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
