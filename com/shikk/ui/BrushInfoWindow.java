package com.shikk.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;

import com.eltima.components.ui.DatePicker;
import com.shikk.data.BrushData;
import com.shikk.data.BrushInfoAdapter;
import com.shikk.data.DBCenter;
import com.shikk.data.TargetApp;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class BrushInfoWindow implements WindowListener{

	public JFrame frame;
	private static final String DefaultFormat = "yyyy-MM-dd";
	private Date date=new Date();
	private Font font=new Font("Times New Roman", Font.BOLD, 14);
	private Dimension dimension=new Dimension(177,24);
	private DatePicker dpStart;
	private DatePicker dpStop;
	private JTable table;
	private JButton button;
	private TargetApp app;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TargetApp app = new TargetApp();
					app.setAppId(17);
					BrushInfoWindow window = new BrushInfoWindow(app);
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
	public BrushInfoWindow(TargetApp app) {
		this.app = app;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 699, 460);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(this);
		frame.getContentPane().setLayout(null);
		
		dpStart = new DatePicker(date,DefaultFormat,font,dimension);
		dpStop  = new DatePicker(date,DefaultFormat,font,dimension);
		//datepick.setLocation(137, 83);
		dpStart.setBounds(10, 35, 177, 24);
		dpStop.setBounds(217, 35, 177, 24);
//		datepick.setHightlightdays(hilightDays, Color.red);//设置一个月份中需要高亮显示的日子
//		datepick.setDisableddays(disabledDays);//设置一个月份中不需要的日子，呈灰色显示
		dpStart.setLocale(Locale.CHINA);//设置国家
		dpStop.setLocale(Locale.CHINA);
//		datepick.setTimePanleVisible(false);//设置时钟面板可见
		frame.getContentPane().add(dpStart);
		frame.getContentPane().add(dpStop);
		
		JLabel lblNewLabel = new JLabel("开始日期：");
		lblNewLabel.setBounds(10, 10, 177, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("结束日期：");
		label.setBounds(217, 10, 177, 15);
		frame.getContentPane().add(label);
		
		final BrushInfoAdapter adapter = new BrushInfoAdapter();
		table = new JTable(adapter);
		table.setBounds(20, 135, 73, 41);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);   //不可整列移动  
//		 table.setCellSelectionEnabled(true); // 选择单个cell
		 JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(10, 68, 663, 350);
		frame.getContentPane().add(scrollPane);
		
		button = new JButton("查询");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String startStr = dpStart.getText();
					String stopStr = dpStop.getText();
					// 开始统计的日期
					Date dateStart = dateFormat.parse(startStr+" 00:00:00");
					// 结束统计的日期
					Date dateStop = dateFormat.parse(stopStr+" 23:59:59");
					DBCenter dbCenter = DBCenter.getInstance();
					dbCenter.openDB(DBCenter.DB_NAME);
					ArrayList<BrushData> brushDatas = new ArrayList<>();
					while (dateStart.getTime() < dateStop.getTime()) {
						// 这天的日期复制，计算留存时使用
						Date firstInstTime = new Date(dateStart.getTime());
						
						// 吓一天的开始与结束段
						Date tmpDate = new Date(dateStart.getTime()+3600*1000*24-1);
						BrushData brushData = new BrushData();
						brushData.setTime(new Date(dateStart.getTime()));
						System.out.println("dataStart:"+dateFormat.format(dateStart));
						int newQuality = dbCenter.getNewQualityByDay(dateStart, tmpDate, app);
						brushData.setNewQuality(newQuality);;
						int remainQuality = dbCenter.getRemainByDay(dateStart, tmpDate, app);
						brushData.setRemainQuality(remainQuality);
						// 下一天的日期复制
						Date remainStartDate = new Date(dateStart.getTime());
						Date today = new Date();
						today.setTime(today.getTime()-3600*1000*24);
						ArrayList<Float> daylyRate = new ArrayList<>();
						while (remainStartDate.getTime()<today.getTime()) {
							remainStartDate.setTime(remainStartDate.getTime()+3600*1000*24);
							 Date tmpDate1 = new Date(remainStartDate.getTime()+3600*1000*24-1);
							int daylayRemain = dbCenter.getRemainByDay(remainStartDate,tmpDate1, firstInstTime, app);
							daylyRate.add((daylayRemain*1.0f)/newQuality);
//							System.out.println("remainStartDate:"+dateFormat.format(remainStartDate)+"    daylayRemain:"+daylayRemain);
						}
						brushData.setDaylyRate(daylyRate);
						// 开始日期 置为下一天
						dateStart.setTime(dateStart.getTime()+3600*1000*24);
//						System.out.println("time:"+dateFormat.format(brushData.getTime())+"  newQuality:"+newQuality+"   remain:"+remainQuality);
						brushDatas.add(brushData);
					}
					adapter.setDataList(brushDatas);
					
					table.repaint();
					table.updateUI();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button.setBounds(424, 36, 93, 23);
		frame.getContentPane().add(button);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("AppInfoWindow  windowClosed");
		TargetAppWindow instance = TargetAppWindow.getInstance();
//		if (action != ACTION_VIEW_APP && instance != null) {
//			instance.refreshUI();
//		}
		if (instance != null) {
			instance.frame.setEnabled(true);
			instance.frame.setVisible(true);
		}
	
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
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
