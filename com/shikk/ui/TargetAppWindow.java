package com.shikk.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import com.shikk.data.DBCenter;
import com.shikk.data.TargetApp;
import com.shikk.data.TargetAppAdapter;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class TargetAppWindow implements WindowListener{

	public static JFrame frame;
	private JTable table;
	private JTextField textField;
	private TargetAppAdapter appAdapter;
	private ArrayList<TargetApp> allAppList;
	private static TargetAppWindow instance;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TargetAppWindow window = new TargetAppWindow();
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
	public TargetAppWindow() {
		initialize();
		instance = this;
	}

	public static TargetAppWindow getInstance(){
		return instance;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 875, 458);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(this);
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("菜单");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("菜单1");
		menu.add(menuItem);
		
		DBCenter dbCenter = DBCenter.getInstance();
		try {
			dbCenter.openDB(DBCenter.DB_NAME);
			allAppList = dbCenter.getTargetAppList();
			System.out.println(allAppList.size());
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		appAdapter = new TargetAppAdapter();
		appAdapter.setAppList(allAppList);
		table = new JTable(appAdapter);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);   //不可整列移动  
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 700, 350);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().setLayout(null);
		
		
		JButton btnNewButton = new JButton("App详情");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				showAppInfo();
			}
		});
		btnNewButton.setBounds(728, 29, 109, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("修改");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				modifyAppInfo();
			}
		});
		btnNewButton_1.setBounds(728, 62, 109, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("添加App");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				addNewAppInfo();
			}
		});
		btnNewButton_2.setBounds(728, 95, 109, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnapp = new JButton("删除App");
		btnapp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				deleteApp();
			}
		});
		btnapp.setBounds(728, 128, 109, 23);
		frame.getContentPane().add(btnapp);
		
		textField = new JTextField();
		textField.setBounds(143, 367, 207, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("根据名称快速搜索");
		label.setBounds(10, 370, 138, 15);
		frame.getContentPane().add(label);
		
		JButton button = new JButton("搜索");
		button.setBounds(364, 366, 93, 23);
		frame.getContentPane().add(button);
		
		JButton btnApp = new JButton("App刷量记录");
		btnApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showBrushInfo();
			}
		});
		btnApp.setBounds(728, 161, 109, 23);
		frame.getContentPane().add(btnApp);
		
		JButton btnNewButton_4 = new JButton("模拟器设置");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageVirtulDevices();
			}
		});
		btnNewButton_4.setBounds(728, 194, 109, 23);
		frame.getContentPane().add(btnNewButton_4);
		 
//		  table.getTableHeader().setResizingAllowed(false);   //不可拉动表格
		int[] columnWidth = new int[]{30,100,120,50,50,200,200};
		for (int i = 0; i < columnWidth.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
			 table.getColumnModel().getColumn(i).setMinWidth(columnWidth[i]);
		}
		
	}
	
	
	protected void manageVirtulDevices() {
		// TODO Auto-generated method stub
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			List<TargetApp> appList = appAdapter.getAppList();
			new VirturlDeviceWindow(appList.get(selectedRow)).frame
					.setVisible(true);
			frame.setEnabled(false);
		} else {
			JOptionPane.showMessageDialog(null, "请选择一个app先", "嘿",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}


	protected void showBrushInfo() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			List<TargetApp> appList = appAdapter.getAppList();
			new BrushInfoWindow(appList.get(selectedRow)).frame.setVisible(true);
			System.out.println("appId:" + appList.get(0).getAppId());
			frame.setEnabled(false);
		} else {
			JOptionPane.showMessageDialog(null, "请选择一个app先", "嘿",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}


	protected void deleteApp() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			int resualt = JOptionPane.showConfirmDialog(null, "确定要删除吗？如果删除将不可恢复，且相应的刷量数据也将丢失！", "嘿",
					JOptionPane.OK_CANCEL_OPTION);
			if (resualt == JOptionPane.OK_OPTION) {
				TargetApp app = appAdapter.getAppList().get(selectedRow);
				try {
					DBCenter center = DBCenter.getInstance();
					center.openDB(DBCenter.DB_NAME);
					center.deleteApp(app);
					refreshUI();
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


	protected void addNewAppInfo() {
		// TODO Auto-generated method stub
		new AppInfoWindow(null,AppInfoWindow.ACTION_ADD_NEW_APP).frame.setVisible(true);
		frame.setEnabled(false);
	}

	protected void modifyAppInfo() {
		
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			List<TargetApp> appList = appAdapter.getAppList();
			new AppInfoWindow(appList.get(selectedRow),AppInfoWindow.ACTION_MONIFY_APP).frame.setVisible(true);
			System.out.println("appId:" + appList.get(0).getAppId());
			frame.setEnabled(false);
		} else {
			JOptionPane.showMessageDialog(null, "请选择一个app先", "嘿",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void showAppInfo() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			List<TargetApp> appList =  appAdapter.getAppList();
			new AppInfoWindow(appList.get(selectedRow),AppInfoWindow.ACTION_VIEW_APP).frame.setVisible(true);
			System.out.println("appId:"+appList.get(0).getAppId());
			frame.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(null, "请选择一个app先", "嘿", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void windowActivated(WindowEvent windowevent) {
		// TODO Auto-generated method stub
//		refreshUI();
	}

	public void refreshUI() {
		DBCenter dbCenter = DBCenter.getInstance();
		try {
			dbCenter.openDB(DBCenter.DB_NAME);
			allAppList = dbCenter.getTargetAppList();
			System.out.println("windowActivated   list size:"+allAppList.size());
			appAdapter.setAppList(allAppList);
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

	@Override
	public void windowClosed(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		System.out.println("windowClosed");
	}

	@Override
	public void windowClosing(WindowEvent windowevent) {
		// 点击了退出系统按钮
		int option = JOptionPane.showConfirmDialog(null, "确定要退出吗？", " 提示",
				JOptionPane.OK_CANCEL_OPTION);
		if (JOptionPane.OK_OPTION == option) {
			// 点击了确定按钮
			DBCenter.getInstance().closeDB();
			System.exit(0);
		} else {
			TargetAppWindow.frame
					.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}

	}

	@Override
	public void windowDeactivated(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		System.out.println("windowDeactivated");
	}

	@Override
	public void windowDeiconified(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		System.out.println("windowDeiconified");
	}

	@Override
	public void windowIconified(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		System.out.println("windowIconified");
	}

	@Override
	public void windowOpened(WindowEvent windowevent) {
		// TODO Auto-generated method stub
		System.out.println("windowOpened");
	}
}
