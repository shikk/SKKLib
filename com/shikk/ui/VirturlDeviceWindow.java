package com.shikk.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Target;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.eltima.components.ui.DatePicker;
import com.shikk.data.BrushData;
import com.shikk.data.Constant;
import com.shikk.data.DBCenter;
import com.shikk.data.InjectEvent;
import com.shikk.data.TargetApp;
import com.shikk.util.CmdCommand;
import com.shikk.util.CommandResult;
import com.shikk.util.FileUtils;
import com.shikk.util.PhoneInfoGenerator;

public class VirturlDeviceWindow implements WindowListener{

	private static final int STATE_IDEL = -1;
	private static final int STATE_NEW_QUALITY = 0;
	private static final int STATE_REMAIN_QUALITY = 1;
	private static final String DefaultFormat = "yyyy-MM-dd";
	private static final int STATE_EXTRA_QUALITY = 2;
	private Date date=new Date();
	private Font font=new Font("Times New Roman", Font.BOLD, 14);
	private Dimension dimension=new Dimension(177,24);
	public JFrame frame;
	private CmdCommand command;
	private JTextField textFieldInputVmName;
	private JTextPane textPanalAllBaseVms;
	private boolean isAppStartOK;
	private TargetApp targetApp;
	private long begain;
	private int state = STATE_IDEL;
	private boolean newQualityWhileFlag;
	private boolean remainQualityWhileFlag;
	private boolean extraQualityWhileFlag;
	private boolean timeCheckWhileFlag = true;
	private boolean stateWhileFlag = true;
	private boolean sleepLongerFlag = false;
	private String androidID;
	private String curDeviceName;
	private DatePicker dpStart;
	private JTextField textField;
	private Boolean lock = new Boolean(false);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TargetApp app = new TargetApp();
					app.setAppId(17);
					VirturlDeviceWindow window = new VirturlDeviceWindow(app);
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
	public VirturlDeviceWindow(TargetApp app) {
		initialize();
		command = new CmdCommand();
		this.targetApp = app;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 527, 606);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(this);
		frame.getContentPane().setLayout(null);
		
		JButton btnCopyBaseVM = new JButton("从base复制虚拟机");
		btnCopyBaseVM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				copyFirstVmFromBase( textFieldInputVmName.getText());
			}

			
		});
		btnCopyBaseVM.setBounds(10, 107, 161, 23);
		frame.getContentPane().add(btnCopyBaseVM);
		
		textFieldInputVmName = new JTextField();
		textFieldInputVmName.setBounds(10, 76, 161, 21);
		frame.getContentPane().add(textFieldInputVmName);
		textFieldInputVmName.setColumns(10);
		
		JLabel lbInputBaseVmName = new JLabel("输入BaseVm名称，带“”");
		lbInputBaseVmName.setBounds(10, 51, 161, 15);
		frame.getContentPane().add(lbInputBaseVmName);
		
		JButton btnGetAllBaseVm = new JButton("所有base虚拟机");
		btnGetAllBaseVm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAllBaseVms();
			}
		});
		btnGetAllBaseVm.setBounds(10, 18, 161, 23);
		frame.getContentPane().add(btnGetAllBaseVm);
		
		JLabel labelBaseVmList = new JLabel("虚拟机列表：");
		labelBaseVmList.setBounds(181, 22, 74, 15);
		frame.getContentPane().add(labelBaseVmList);
		
		textPanalAllBaseVms = new JTextPane();
		textPanalAllBaseVms.setEditable(false);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(textPanalAllBaseVms);
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(181, 51, 243, 97);
		frame.getContentPane().add(scrollPane);
		
		JButton btnNewButton = new JButton("<html> 启动新的虚拟机<br />同时启动应用<html/>");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startVM(getCurDeviceName());
			}

			
		});
		btnNewButton.setBounds(10, 196, 245, 56);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("关闭虚拟机");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (curDeviceName != null && !"".equals(curDeviceName)) {
					stopPlayerProc(curDeviceName);
				}
				
			}
		});
		btnNewButton_2.setBounds(10, 385, 245, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("写入下一个虚拟机的数据");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createNewDevice1();
				createNewDevice2();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_3.setBounds(10, 354, 245, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JLabel label = new JLabel("");
		label.setForeground(Color.WHITE);
		label.setBackground(Color.DARK_GRAY);
		label.setOpaque(true);
		label.setBounds(10, 153, 436, 2);
		frame.getContentPane().add(label);
		
		JLabel lblNewLabel = new JLabel("查看base虚拟机");
		lblNewLabel.setBounds(10, 0, 268, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("新装应用，激活应用步骤");
		lblNewLabel_1.setBounds(10, 158, 185, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBackground(Color.DARK_GRAY);
		lblNewLabel_2.setBounds(276, 158, 2, 335);
		lblNewLabel_2.setOpaque(true);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel label_1 = new JLabel("虚拟机管理");
		label_1.setBounds(303, 171, 97, 15);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("选择某天的虚拟机删除");
		label_2.setBounds(287, 211, 169, 15);
		frame.getContentPane().add(label_2);
		dpStart = new DatePicker(date,DefaultFormat,font,dimension);
		dpStart.setBounds(288, 229, 119, 24);
		dpStart.setLocale(Locale.CHINA);//设置国家
		frame.getContentPane().add(dpStart);
		
		JButton btnNewButton_4 = new JButton("删除");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				try {
					String startStr = dpStart.getText();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date dateStart = dateFormat.parse(startStr+" 00:00:00");
					// 结束统计的日期
					DBCenter center = DBCenter.getInstance();
					center.openDB(DBCenter.DB_NAME);
					ArrayList<String> deviceNames = center.getUseableDeviceNames(dateStart, targetApp);
					for (String name : deviceNames) {
						deleteVM(name);
					}
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
		btnNewButton_4.setBounds(417, 229, 84, 23);
		frame.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("删除最新的x个虚拟机");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText();
				int count = Integer.parseInt(text);
				for (int i = 0; i < count; i++) {
					deleteVM(getCurDeviceName());
				}
			}
		});
		btnNewButton_5.setBounds(338, 270, 163, 23);
		frame.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("xxxx");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_6.setBounds(305, 335, 167, 23);
		frame.getContentPane().add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("删除新的虚拟机");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				deleteVM(getCurDeviceName());
			}
		});
		btnNewButton_7.setBounds(10, 451, 245, 23);
		frame.getContentPane().add(btnNewButton_7);
		
		JButton btnNewButton_1 = new JButton("模拟用户操作");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String curDeviceIP = getCurDeviceIP();
				// 初始 启动程序
				try {
					// home键
					command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell input keyevent 3");
					Thread.sleep(1000);
					// 程序位置
					command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell input tap "+Constant.APP_POSX+" "+Constant.APP_POSY);
					Thread.sleep(Constant.APP_SPLASH_TIME);
					injectEvent(-1,curDeviceIP);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(10, 262, 245, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_8 = new JButton("<html>生成new_devcie.txt<br/>到当前虚拟机目录下<html/>");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewDeviceInfo();
			}
		});
		btnNewButton_8.setBounds(10, 295, 245, 49);
		frame.getContentPane().add(btnNewButton_8);
		
		JButton btnNewButton_9 = new JButton("复制出下一个虚拟机");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cloneDevice();
			}
		});
		btnNewButton_9.setBounds(10, 418, 245, 23);
		frame.getContentPane().add(btnNewButton_9);
		
		JButton btnNewButton_10 = new JButton("一键启动刷新增量系统");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				autoBrushApp(true);
			}
		});
		btnNewButton_10.setBounds(75, 503, 245, 23);
		frame.getContentPane().add(btnNewButton_10);
		
		JButton btnNewButton_11 = new JButton("一键启动刷留存量系统");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				autoBrushApp2(true);
			}
		});
		btnNewButton_11.setBounds(75, 534, 245, 23);
		frame.getContentPane().add(btnNewButton_11);
		
		JButton btnNewButton_12 = new JButton("停止刷新增");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newQualityWhileFlag = false;
			}
		});
		btnNewButton_12.setBounds(353, 503, 119, 23);
		frame.getContentPane().add(btnNewButton_12);
		
		JButton btnNewButton_13 = new JButton("停止刷留存");
		btnNewButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remainQualityWhileFlag = false;
			}
		});
		btnNewButton_13.setBounds(353, 534, 119, 23);
		frame.getContentPane().add(btnNewButton_13);
		
		final JLabel lblNewLabel_3 = new JLabel("运行状态");
		lblNewLabel_3.setBounds(303, 469, 198, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btnNewButton_14 = new JButton("删除今天新增的一部分虚拟机");
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSomeDevice();
			}
		});
		btnNewButton_14.setBounds(303, 368, 169, 23);
		frame.getContentPane().add(btnNewButton_14);
		
		textField = new JTextField();
		textField.setBounds(288, 271, 40, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnuid = new JButton("刷多余的uid部分");
		btnuid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 刷前一天的新增的uid比例
				brushExtraVMs(true);
			}
		});
		btnuid.setBounds(303, 401, 169, 23);
		frame.getContentPane().add(btnuid);
		
		JButton btnNewButton_15 = new JButton("自动运行");
		btnNewButton_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(){
					private boolean hasBrushedArea1 = false;
					private boolean hasBrushedArea2 = false;
					private boolean hasBrushedArea3 = false;
					
					public void run() {
						while (timeCheckWhileFlag) {
						try {
							Thread.sleep(2800);
							System.out.println("timeCheck");
							Date nowTime = new Date();
							Calendar calendar = Calendar.getInstance(Locale.CHINA);
							calendar.setTime(nowTime);
							Calendar tmp0 = Calendar.getInstance(Locale.CHINA);
							tmp0.setTime(nowTime);
							tmp0.set(Calendar.HOUR_OF_DAY, 0);
							tmp0.set(Calendar.MINUTE, 0);
							tmp0.set(Calendar.SECOND, 0);
							tmp0.set(Calendar.MILLISECOND, 0);
							Calendar tmp2 = Calendar.getInstance(Locale.CHINA);
							tmp2.setTime(tmp0.getTime());
							tmp2.set(Calendar.HOUR_OF_DAY, 2);
							Calendar tmp6 = Calendar.getInstance(Locale.CHINA);
							tmp6.setTime(tmp0.getTime());
							tmp6.set(Calendar.HOUR_OF_DAY, 6);
							Calendar tmp24 = Calendar.getInstance(Locale.CHINA);
							tmp24.setTime(tmp0.getTime());
							tmp24.set(Calendar.HOUR_OF_DAY, 24);
							if (calendar.compareTo(tmp0) >0 && calendar.compareTo(tmp2) < 0 && state != STATE_EXTRA_QUALITY && !hasBrushedArea2) {
								while (state != STATE_IDEL) {
									newQualityWhileFlag = false;
									remainQualityWhileFlag = false;
									Thread.sleep(500);
								}
								brushExtraVMs(false);
								hasBrushedArea2 = true;
								command.cmd.runWaitFor("shutdown -s -t 60");
							}else if (calendar.compareTo(tmp6)<0 && state != STATE_IDEL && !hasBrushedArea3) {
//								while (state != STATE_IDEL) {
//									newQualityWhileFlag = false;
//									remainQualityWhileFlag = false;
//									extraQualityWhileFlag = false;
//									Thread.sleep(500);
//								}
//								command.cmd.runWaitFor("shutdown -s -t 60");
//								hasBrushedArea3 = true;
							}else if (calendar.compareTo(tmp24)<0 && state != STATE_NEW_QUALITY && state != STATE_REMAIN_QUALITY && !hasBrushedArea1 ) {
								while (state != STATE_IDEL) {
									newQualityWhileFlag = false;
									remainQualityWhileFlag = false;
									extraQualityWhileFlag = false;
									Thread.sleep(500);
								}
								System.err.println("hasBrushedArea1");
								if (timeCheckWhileFlag) {
									autoBrushApp2(false);
								}
								if (timeCheckWhileFlag) {
									autoBrushApp(false);
								}
								
								hasBrushedArea1 = true;
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						}
					};
					
				}.start();
			}
		});
		btnNewButton_15.setBounds(303, 434, 89, 23);
		frame.getContentPane().add(btnNewButton_15);
		
		JButton button = new JButton("停止");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newQualityWhileFlag = false;
				remainQualityWhileFlag = false;
				extraQualityWhileFlag = false;
				timeCheckWhileFlag = false;
			}
		});
		button.setBounds(400, 434, 74, 23);
		frame.getContentPane().add(button);
		
		new Thread(){
			public void run() {
				while (stateWhileFlag) {
					try {
						Thread.sleep(1800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					switch (state) {
					case STATE_IDEL:
						lblNewLabel_3.setText("运行状态:空闲");
						break;
					case STATE_NEW_QUALITY:
						lblNewLabel_3.setText("运行状态:刷新增量中...");
						break;
					case STATE_REMAIN_QUALITY:
						lblNewLabel_3.setText("运行状态:刷留存量中...");
						break;
					case STATE_EXTRA_QUALITY:
						lblNewLabel_3.setText("运行状态:刷UID量中...");
						break;
					default:
						break;
					}
				}
				
			};
		}.start();
	}
	
	protected void brushExtraVMs(boolean threadFlag) {
		// TODO Auto-generated method stub
		System.out.println("brushExtraVMs");
		if (state == STATE_NEW_QUALITY) {
			JOptionPane.showMessageDialog(null, "刷新增量正在运行，请先停止再进行此操作！！");
			return;
		}
		if (state == STATE_REMAIN_QUALITY) {
			JOptionPane.showMessageDialog(null, "刷留存量已经正在运行！！");
			return;
		}
		state = STATE_EXTRA_QUALITY;
		extraQualityWhileFlag = true;
		if (threadFlag) {
			new Thread(){
				public void run() {
					try {
						float uidPercent = targetApp.getUIDPercent();
						DBCenter center = DBCenter.getInstance();
						center.openDB(DBCenter.DB_NAME);
						Date date = new Date(
								new Date().getTime() - 3600 * 1000 * 24);
						Date dateBegin = center.convertToBeginOfDay(date);
						Date dateEnd = new Date(dateBegin.getTime() + 3600 * 1000
								* 24 - 1);
						int newQualityByDay = center.getNewQualityByDay(dateBegin,
								dateEnd, targetApp);
						int uidRemain = (int) (newQualityByDay * uidPercent);
						// 取出uidRemain个设备名称，清除数据后，启动
						// 所有能刷的留存虚拟机信息（虚拟机名称以及虚拟机刷过的model_ids）
						ArrayList<BrushData> devices = center.getUseableDevices(
								dateBegin, targetApp);
						for (int i = 0; i < uidRemain && extraQualityWhileFlag ; i++) {
							BrushData brushData = devices.get(i);
							autoRun3(brushData.getDeviceName());
						}
						state = STATE_IDEL;
					} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			};
			
			}.start();
		}else{
			try {
				float uidPercent = targetApp.getUIDPercent();
				DBCenter center = DBCenter.getInstance();
				center.openDB(DBCenter.DB_NAME);
				Date date = new Date(
						new Date().getTime() - 3600 * 1000 * 24);
				Date dateBegin = center.convertToBeginOfDay(date);
				Date dateEnd = new Date(dateBegin.getTime() + 3600 * 1000
						* 24 - 1);
				int newQualityByDay = center.getNewQualityByDay(dateBegin,
						dateEnd, targetApp);
				int uidRemain = (int) (newQualityByDay * uidPercent);
				// 取出uidRemain个设备名称，清除数据后，启动
				// 所有能刷的留存虚拟机信息（虚拟机名称以及虚拟机刷过的model_ids）
				ArrayList<BrushData> devices = center.getUseableDevices(
						dateBegin, targetApp);
				for (int i = 0; i < uidRemain && extraQualityWhileFlag ; i++) {
					BrushData brushData = devices.get(i);
					autoRun3(brushData.getDeviceName());
				}
				state = STATE_IDEL;
			} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		}
		
	}

	protected void deleteSomeDevice() {
		// TODO Auto-generated method stub
		System.out.println("deleteSomeDevice");
		try {
			// 获取今天的总新增量
			DBCenter center = DBCenter.getInstance();
			center.openDB(DBCenter.DB_NAME);
			Date today = new Date();
			Date beginOfDay = center.convertToBeginOfDay(today);
			Date endOfDay = new Date(beginOfDay.getTime()+3600*1000*24-1);
			int newQualityToday = center.getNewQualityByDay(beginOfDay, endOfDay, targetApp);
			// 计算第二日留存量
			Date tomorrow = new Date(today.getTime()+3600*1000*24);
			int tomorrowRemain = center.calculateRemain(today, tomorrow, targetApp);
			// 获取已经删除的新增量
			int deletedDevices = center.getDeletedDevices(today, targetApp);
			// 应删除的新增量=今日总新增-次日留存-已经删除的新增
			int deleteCount = newQualityToday - tomorrowRemain - deletedDevices;
			// 获取今日新增的除去已经删除的并且模块为空的虚拟机名称列表
			ArrayList<String> deviceNames = center.getNoModelDeviceNames(today, targetApp);
//			Random rd = new Random();
//			ArrayList<String> tmpDeviceNames = new ArrayList<>();
			for (int i = 0; i < deviceNames.size() && i<deleteCount; i++) {
				deleteVM(deviceNames.get(i));
			}
//			while (tmpDeviceNames.size() < deleteCount && deviceNames.size()>0) {
//				String str = deviceNames.get(rd.nextInt(deviceNames.size()));
//				if (!tmpDeviceNames.contains(str)) {
//					tmpDeviceNames.add(str);
//				}
//			}
//			for (int i = 0; i < tmpDeviceNames.size(); i++) {
//				deleteVM(tmpDeviceNames.get(i));
//			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void autoBrushApp2(boolean threadFlag) {
		// TODO Auto-generated method stub
		if (state == STATE_NEW_QUALITY) {
			JOptionPane.showMessageDialog(null, "刷新增量正在运行，请先停止再进行此操作！！");
			return;
		}
		if (state == STATE_REMAIN_QUALITY) {
			JOptionPane.showMessageDialog(null, "刷留存量已经正在运行！！");
			return;
		}
		state = STATE_REMAIN_QUALITY;
		remainQualityWhileFlag = true;
		if (threadFlag) {
			new Thread(){
				@Override
				public void run() {
					// 今天应刷的每日留存
//					ArrayList<Integer> remainCountList = new ArrayList<>();
					try {
						DBCenter center = DBCenter.getInstance();
						center.openDB(DBCenter.DB_NAME);
						Date today = new Date();
						for (int i = 35; i >=0 && remainQualityWhileFlag; i--) {
							long ttt =  3600l*1000*24*i;
							Date firstInstTime = new Date(today.getTime() - ttt);
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String format = df.format(firstInstTime);
							System.out.println(format);
							// 计算今天应该刷的的留存量
							int remainTotal = center.calculateRemain(firstInstTime,targetApp);
							Date beginOfToday = center.convertToBeginOfDay(today);
							// 数据库读取今天已经刷过的留存量
							int remainBrushed = center.getRemainByDay(beginOfToday, today, firstInstTime, targetApp);
							// 实际还需要刷多少留存
							int remainCount = remainTotal - remainBrushed;
							System.out.println("remainCount: "+remainCount);
							int j = 0;
							// 所有能刷的留存虚拟机信息（虚拟机名称以及虚拟机刷过的model_ids）
							ArrayList<BrushData> devices = center.getUseableDevices(firstInstTime,targetApp);
//							Random rd = new Random();
							ArrayList<BrushData> tmpDevices = new ArrayList<>();
							int m = 0;
							while (tmpDevices.size() < remainCount && m<devices.size()) {
								BrushData data = devices.get(m);
								if (!tmpDevices.contains(data) && !center.isVirDeviceBrushed(data.getDeviceName(), today, targetApp)) {
									tmpDevices.add(data);
								}
								m++;
							}
							while (j < tmpDevices.size() && remainQualityWhileFlag) {
								autoRun2(tmpDevices.get(j).getDeviceName(),tmpDevices.get(j).getBrushed_models());
								j++;
							}
						}
						state= STATE_IDEL;
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}.start();
		}else{
			// 今天应刷的每日留存
//			ArrayList<Integer> remainCountList = new ArrayList<>();
			try {
				DBCenter center = DBCenter.getInstance();
				center.openDB(DBCenter.DB_NAME);
				Date today = new Date();
				for (int i = 35; i >=0 && remainQualityWhileFlag; i--) {
					long ttt =  3600l*1000*24*i;
					Date firstInstTime = new Date(today.getTime() - ttt);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String format = df.format(firstInstTime);
					System.out.println(format);
					// 计算今天应该刷的的留存量
					int remainTotal = center.calculateRemain(firstInstTime,targetApp);
					Date beginOfToday = center.convertToBeginOfDay(today);
					// 数据库读取今天已经刷过的留存量
					int remainBrushed = center.getRemainByDay(beginOfToday, today, firstInstTime, targetApp);
					// 实际还需要刷多少留存
					int remainCount = remainTotal - remainBrushed;
					System.out.println("remainCount: "+remainCount);
					int j = 0;
					// 所有能刷的留存虚拟机信息（虚拟机名称以及虚拟机刷过的model_ids）
					ArrayList<BrushData> devices = center.getUseableDevices(firstInstTime,targetApp);
//					Random rd = new Random();
					ArrayList<BrushData> tmpDevices = new ArrayList<>();
					int m = 0;
					while (tmpDevices.size() < remainCount && m<devices.size()) {
						BrushData data = devices.get(m);
						if (!tmpDevices.contains(data) && !center.isVirDeviceBrushed(data.getDeviceName(), today, targetApp)) {
							tmpDevices.add(data);
						}
						m++;
					}
					while (j < tmpDevices.size() && remainQualityWhileFlag) {
						autoRun2(tmpDevices.get(j).getDeviceName(),tmpDevices.get(j).getBrushed_models());
						j++;
					}
				}
				state= STATE_IDEL;
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void autoRun2(String deviceName,String brushedIds){
		System.out.println("autoRun2 :"+deviceName+"    brusheModelIds:"+brushedIds);
		boolean startOk = startVM(deviceName);
		if (!startOk) {
			return;
		}
		
		while (true) {
			try {
				synchronized (lock) {
					if (!isAppStartOK) {
						lock.wait();
					}
				}
				if (isAppStartOK) {
					DBCenter center = DBCenter.getInstance();
					center.openDB(DBCenter.DB_NAME);
					String curDeviceIp = getCurDeviceIP();
					// 初始 启动程序
					// home键
					command.cmd.runWaitFor("adb -s "+curDeviceIp+" shell input keyevent 3");
					Thread.sleep(1000);
					// 程序位置
					command.cmd.runWaitFor("adb -s "+curDeviceIp+" shell input tap "+Constant.APP_POSX+" "+Constant.APP_POSY);
					Thread.sleep(Constant.APP_SPLASH_TIME);
					// 当前device所有刷过的model_id
					String[] split = brushedIds.split(",");
					boolean needBrushModel = false;
					StringBuilder sbBrushedModels = new StringBuilder();
					for (int i = 0; i < split.length; i++) {
						String idstr = split[i];
						if(idstr!=null && !idstr.trim().equals("")){
							//如果  该模块需要的总量 - 该模块已经刷的量 > 0
							int modelId = Integer.parseInt(idstr.trim());
							int totalCount = center.calculateModelRemain(date, date, targetApp, modelId);
							Date today = new Date();
							Date dateBegin = center.convertToBeginOfDay(today);
							Date dateEnd = new Date(dateBegin.getTime() + (3600*24*1000-1));
							int brushedCount = center.getModelRemainQualityByDay(dateBegin, dateEnd, today, targetApp,modelId);
							if (totalCount- brushedCount > 0) {
								injectEvent(modelId,curDeviceIp);
								needBrushModel = true;
								sbBrushedModels.append(modelId+",");
							}
							
						}
					}
					// 刷默认的模块
					if (!needBrushModel) {
						injectEvent(-1,curDeviceIp);
						sbBrushedModels.append("-1,");
					}
					updateDB(sbBrushedModels.toString().substring(0, sbBrushedModels.length()-1));
					begain = System.currentTimeMillis();
					stopPlayerProc(deviceName);
					System.err.println("关机关机关机关机------------------------dur:"+(System.currentTimeMillis()- begain));
					isAppStartOK = false;
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void autoRun3(String deviceName){
		System.out.println("autoRun3 :"+deviceName);
		boolean startOk = startVM(deviceName);
		if (!startOk) {
			return;
		}
		
		while (true) {
			try {
				synchronized (lock) {
					if (!isAppStartOK) {
						lock.wait();
					}
				}
				if (isAppStartOK) {
					DBCenter center = DBCenter.getInstance();
					center.openDB(DBCenter.DB_NAME);
					String curDeviceIp = getCurDeviceIP();
//					command.cmd.runWaitFor("adb -s "+curDeviceIp+" uninstall "+targetApp.getPkgName());
//					command.cmd.runWaitFor("adb -s "+curDeviceIp+" install "+Constant.AppPkgPath);
					command.cmd.runWaitFor("adb -s "+curDeviceIp+" shell rm -r /data/data/"+targetApp.getPkgName()+"/cache");
					command.cmd.runWaitFor("adb -s "+curDeviceIp+" shell rm -r /data/data/"+targetApp.getPkgName()+"/databases");
					command.cmd.runWaitFor("adb -s "+curDeviceIp+" shell rm -r /data/data/"+targetApp.getPkgName()+"/files");
					command.cmd.runWaitFor("adb -s "+curDeviceIp+" shell rm -r /data/data/"+targetApp.getPkgName()+"/shared_prfs");
					command.cmd.runWaitFor("adb -s "+curDeviceIp+" shell rm -r /mnt/sdcard/Android/data/"+targetApp.getPkgName()+"/cache");
					command.cmd.runWaitFor("adb -s "+curDeviceIp+" shell rm -r /data/data/"+targetApp.getPkgName()+"/operamini.properties");
					
					command.cmd.runWaitFor("adb -s "+curDeviceIp+" shell am start -n "+Constant.TARGET_APP_START_COMPONENT);
					Thread.sleep(Constant.APP_SPLASH_TIME);
					// 当前device所有刷过的model_id
					// 刷默认的模块
						injectEvent(-1,curDeviceIp);
					begain = System.currentTimeMillis();
					stopPlayerProc(deviceName);
					System.err.println("关机关机关机关机------------------------dur:"+(System.currentTimeMillis()- begain));
					isAppStartOK = false;
					break;
				}
			} catch (InterruptedException e) {
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
	}
	
	
	protected void autoBrushApp(boolean threadFlag) {
		System.err.println("autoBrushApp");
		if (state == STATE_NEW_QUALITY) {
			JOptionPane.showMessageDialog(null, "刷新增量已经正在运行！！");
			return;
		}
		if (state == STATE_REMAIN_QUALITY) {
			JOptionPane.showMessageDialog(null, "刷留存量正在运行，请先停止再进行此操作！！");
			return;
		}
		state = STATE_NEW_QUALITY;
		newQualityWhileFlag = true;
		
		if (threadFlag) {
			new Thread(){
				@Override
				public void run() {
					// 今天最多新增
					int newQualityTotalCount = 0;
					try {
						DBCenter center = DBCenter.getInstance();
						center.openDB(DBCenter.DB_NAME);
						// 判断是否今天复制过新的虚拟机出来，如果没有，则复制base  否则直接复制新的虚拟机
						Date beginOfDay = center.convertToBeginOfDay(new Date());
						int maxQuality = center.calculateTodayNewQuality(targetApp);
						int newAdded = center.getNewQualityByDay(beginOfDay, new Date(), targetApp);
						if (newAdded == 0) {
							copyFirstVmFromBase(targetApp.getBaseVmName());
						}
						
						newQualityTotalCount = maxQuality - newAdded;
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int i = 0;
					while (i < newQualityTotalCount && newQualityWhileFlag) {
						System.err.println("autoBrushApp1111");
						autoRun();
						i++;
					}
					deleteSomeDevice();
					state= STATE_IDEL;
				}
			}.start();
		}else{
			// 今天最多新增
			int newQualityTotalCount = 0;
			try {
				DBCenter center = DBCenter.getInstance();
				center.openDB(DBCenter.DB_NAME);
				// 判断是否今天复制过新的虚拟机出来，如果没有，则复制base  否则直接复制新的虚拟机
				Date beginOfDay = center.convertToBeginOfDay(new Date());
				int maxQuality = center.calculateTodayNewQuality(targetApp);
				int newAdded = center.getNewQualityByDay(beginOfDay, new Date(), targetApp);
				if (newAdded == 0) {
					copyFirstVmFromBase(targetApp.getBaseVmName());
				}
				
				newQualityTotalCount = maxQuality - newAdded;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int i = 0;
			while (i < newQualityTotalCount && newQualityWhileFlag) {
				System.err.println("autoBrushApp1111");
				autoRun();
				i++;
			}
			deleteSomeDevice();
			state= STATE_IDEL;
		}
		
		
	}

	public void autoRun() {
		System.out.println("autorun");
		String curDeviceName = getCurDeviceName();
		boolean startOk = startVM(curDeviceName);
		if (!startOk) {
			return;
		}
		while (true) {
			try {
				synchronized (lock) {
					if (!isAppStartOK) {
						lock.wait();
					}
				}
				if (isAppStartOK) {
					DBCenter center = DBCenter.getInstance();
					center.openDB(DBCenter.DB_NAME);
					// 模拟用户操作8s
					createNewDeviceInfo();
					createNewDevice1();
					String curDeviceIP = getCurDeviceIP();
//					command.cmd.runWaitFor("adb -s "+curDeviceIP+" uninstall "+targetApp.getPkgName());
//					command.cmd.runWaitFor("adb -s "+curDeviceIP+" install "+Constant.AppPkgPath);
					command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell rm -r /data/data/"+targetApp.getPkgName()+"/cache");
					command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell rm -r /data/data/"+targetApp.getPkgName()+"/databases");
					command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell rm -r /data/data/"+targetApp.getPkgName()+"/files");
					command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell rm -r /data/data/"+targetApp.getPkgName()+"/shared_prfs");
					command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell rm -r /mnt/sdcard/Android/data/"+targetApp.getPkgName()+"/cache");
					command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell rm -r /data/data/"+targetApp.getPkgName()+"/operamini.properties");
					command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell am start -n "+Constant.TARGET_APP_START_COMPONENT);
					Thread.sleep(Constant.APP_SPLASH_TIME);
					// 得到所有的model_ids
					String modelIdsStr = targetApp.getModelIds();
					StringBuilder sbBrushedModels = new StringBuilder();
					boolean needBrushModel = false;
					if (modelIdsStr != null) {
						String[] modelIds = modelIdsStr.split(",");
						// 轮询model_ids 是否还需要模拟操作，如果不需要进行模块模拟操作，则执行默认操作
						for (int i = 0; i < modelIds.length; i++) {
							if (modelIds[i]!=null&&!"".equals(modelIds[i].trim())) {
								System.out.println("modleiddddd: "+modelIds[i].trim());
								int modelId = Integer.parseInt(modelIds[i].trim());
								//如果  该模块需要的总量 - 该模块已经刷的量 > 0
								int totalCount = center.calculateModelRemain(date, date, targetApp, modelId);
								Date today = new Date();
								Date dateBegin = center.convertToBeginOfDay(today);
								Date dateEnd = new Date(dateBegin.getTime() + (3600*24*1000-1));
								int brushedCount = center.getModelRemainQualityByDay(dateBegin, dateEnd, today, targetApp,modelId);
								if (totalCount - brushedCount>0) {
									injectEvent(modelId,curDeviceIP);
									needBrushModel = true;
									sbBrushedModels.append(modelId+",");
								}
							}
						}
					}
					
					// 刷默认的模块
					if (!needBrushModel) {
						injectEvent(-1,curDeviceIP);
						sbBrushedModels.append("-1,");
					}
					updateDB(sbBrushedModels.toString().substring(0, sbBrushedModels.length()-1));
					createNewDevice2();
					begain = System.currentTimeMillis();
					stopPlayerProc(curDeviceName);
					System.err.println("关机关机关机关机------------------------dur:"+(System.currentTimeMillis()- begain));
					begain = System.currentTimeMillis();
					cloneDevice();
					System.err.println("复制 复制--------------------------------："+(System.currentTimeMillis()-begain));
					isAppStartOK = false;
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void createNewDeviceInfo() {
		String curDeviceName = getCurDeviceName();
		String VMDeviceRoot = Constant.VM_DEVICES_DIR+curDeviceName.substring(1,curDeviceName.length()-1);
		File file = new File(VMDeviceRoot+File.separator+"new_device.txt");
//		File thisDevice = new File(VMDeviceRoot+File.separator+"this_device.txt");
		if (file.exists()) {
			file.delete();
		}
		BufferedWriter writer =  null;
		BufferedWriter writer2 =  null;
			try {
				file.createNewFile();
//				thisDevice.createNewFile();
				writer = new BufferedWriter(new FileWriter(file));
//				writer2 = new BufferedWriter(new FileWriter(thisDevice));
//				CommandResult result = command.cmd.runWaitFor("adb -s "+getCurDeviceIP()+" shell cat /data/new_device.txt");
//				String deviceIdStr = result.stdout;
//				int ind = deviceIdStr.indexOf("imei=");
//				int ind2 = deviceIdStr.indexOf("imsi=");
//				if (ind != -1 && ind2!=-1) {
//					String thisDeviceImei = deviceIdStr.substring(ind+5,ind2-1);
//					writer2.write("imei="+thisDeviceImei);
//				}
				PhoneInfoGenerator.refresh();
				writer.write("imei="+PhoneInfoGenerator.IMEI);
				writer.newLine();
				writer.write("imsi="+PhoneInfoGenerator.IMSI);
				writer.newLine();
				writer.write("phoneNum="+PhoneInfoGenerator.phoneNum);
				writer.newLine();
				writer.write("ip="+PhoneInfoGenerator.IP);
				writer.newLine();
				writer.write("proxyhost="+PhoneInfoGenerator.proxyIP);
				writer.newLine();
				writer.write("proxyport="+PhoneInfoGenerator.proxyPort);
				writer.newLine();
				writer.write("mac="+PhoneInfoGenerator.MAC);
				writer.newLine();
				writer.write("softwareversion="+PhoneInfoGenerator.softwareversion);
				writer.newLine();
				writer.write("networkoper="+PhoneInfoGenerator.netWorkOper);
				writer.newLine();
				writer.write("networkopername="+PhoneInfoGenerator.netWorkOperName);
				writer.newLine();
				writer.write("simoper="+PhoneInfoGenerator.simOper);
				writer.newLine();
				writer.write("simserialnum="+PhoneInfoGenerator.simSerialNo);
				writer.newLine();
				writer.write("deviceModel="+PhoneInfoGenerator.deviceModel);
				writer.newLine();
				writer.write("osversion="+PhoneInfoGenerator.osVersion);
				writer.newLine();
				writer.write("cpuABI="+PhoneInfoGenerator.cpuArm1);
				writer.newLine();
				writer.write("cpuABI2="+PhoneInfoGenerator.cpuArm2);
				writer.newLine();
				writer.write("brand="+PhoneInfoGenerator.brand);
				writer.newLine();
				writer.write("board="+PhoneInfoGenerator.board);
				writer.newLine();
				writer.write("display="+PhoneInfoGenerator.display);
				writer.newLine();
				writer.write("hardware="+PhoneInfoGenerator.hardware);
				writer.newLine();
				writer.write("buildId="+PhoneInfoGenerator.buildId);
				writer.newLine();
				writer.write("manufacture="+PhoneInfoGenerator.manufacture);
				writer.newLine();
				writer.write("device="+PhoneInfoGenerator.device);
				writer.newLine();
				writer.write("product="+PhoneInfoGenerator.product);
				writer.newLine();
				writer.write("serial="+PhoneInfoGenerator.serial);
				writer.newLine();
				writer.write("time="+PhoneInfoGenerator.time);
				writer.newLine();
				writer.write("androidId="+PhoneInfoGenerator.androidId);
				writer.newLine();
				writer.write("hotword="+PhoneInfoGenerator.hotword);
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (writer2 != null) {
					try {
						writer2.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}
	

	protected void copyFirstVmFromBase(String baseVmName) {
		if (baseVmName !=null && !"".equals(baseVmName)) {
			int index = baseVmName.indexOf("basevm");
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String cloneName = baseVmName.substring(0,index-12) +"_"+dateFormat.format(calendar.getTime())+"\"";
			command.cmd.runWaitFor("VBoxManage clonevm "+baseVmName +" --mode all --options keepdisknames --name "+cloneName+" --register");
		}else{
			System.out.println("虚拟机名称为空");
		}
	}

	// 修改成新的数据并复制出一个新的虚拟机
	protected void createNewDevice1() {
		String curDeviceIP = getCurDeviceIP();
		String curDeviceName = getCurDeviceName();
		command.cmd.runWaitFor("adb -s " +curDeviceIP +" remount");
		command.cmd.runWaitFor("adb -s " +curDeviceIP +" shell chmod 777 /data");
		String VMDeviceRoot = Constant.VM_DEVICES_DIR+curDeviceName.substring(1,curDeviceName.length()-1);
		command.cmd.runWaitFor("adb -s "+ curDeviceIP +" push "+VMDeviceRoot+ File.separator+ "new_device.txt /system/new_device.txt");
		command.cmd.runWaitFor("adb -s "+ curDeviceIP +" shell chmod 644 "+"/system/new_device.txt");
		command.cmd.runWaitFor("adb -s "+ curDeviceIP +" push "+VMDeviceRoot+ File.separator+ "new_device.txt /data/new_device.txt");
		command.cmd.runWaitFor("adb -s "+ curDeviceIP +" shell chmod 644 "+"/data/new_device.txt");
		// 修改代理ip
//		command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell am startservice -a com.shikk.ChangeIpService");
		//保存信息
//		File file = new File("/data/new_device.txt");
		File file = new File(VMDeviceRoot+File.separator+ "new_device.txt");
		String deviceModel = "Collpad";
		String OSVersion = "";
		String cpuABI = "";
		String cpuABI2= "";
		String brand = "";
		String board = "";
		String display = "";
		String hardware = "";
		String buildID= "";
		String manufacture = "";
		String product = "";
		String serial = "";
		String time = "";
		androidID = "";
		String device = "";
		if (file.exists()) {
			BufferedReader bfReader =null;
			BufferedWriter bfWriter = null;
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String str = null;
				while ( (str = reader.readLine()) != null) {
					int ind = str.indexOf("=");
					String key = str.substring(0,ind);
					String value = str.substring(ind+1);
					if ("deviceModel".equals(key)) {
						deviceModel = value;
					}else if ("osversion".equals(key)) {
						OSVersion = value;
					}else if ("cpuABI".endsWith(key)) {
						cpuABI = value;
					}else if("cpuABI2".equals(key)){
						cpuABI2 = value;
					}else if("brand".equals(key)){
						brand = value;
					}else if("board".equals(key)){
						board = value;
					}else if("display".equals(key)){
						display = value;
					}else if("hardware".equals(key)){
						hardware = value;
					}else if("buildId".equals(key)){
						buildID = value;
					}else if("manufacture".equals(key)){
						manufacture = value;
					}else if("product".equals(key)){
						product = value;
					}else if("serial".equals(key)){
						serial = value;
					}else if("time".equals(key)){
						time = value;
					}else if("androidId".equals(key)){
						androidID = value;
					}else if("device".equals(key)){
						device = value;
					}
				}
//				写入 build.prop
				
				command.cmd.runWaitFor("adb -s " +curDeviceIP +" pull /system/build.prop "+VMDeviceRoot + File.separator+"build.prop");
//				File propFile = new File("/system/build.prop");
//				File tempPropFile = new File("/data/build.prop");
				File propFile = new File(VMDeviceRoot + File.separator+"build.prop");
				File tempPropFile = new File(VMDeviceRoot+File.separator+"tmpbuild.prop");
				if (tempPropFile.exists()) {
					tempPropFile.delete();
				}
				tempPropFile.createNewFile();
				 bfReader = new BufferedReader(new FileReader(propFile));
				 bfWriter = new BufferedWriter(new FileWriter(tempPropFile));
				String tempStr = "";
				while ((tempStr = bfReader.readLine()) != null) {
					if (tempStr.contains("ro.product.model=")) {
						bfWriter.write("ro.product.model="+deviceModel+"\n");
					}else if (tempStr.contains("ro.build.version.release=")) {
						bfWriter.write("ro.build.version.release="+OSVersion+"\n");
					}else if (tempStr.contains("ro.product.cpu.abi2=")) {
						bfWriter.write("ro.product.cpu.abi2="+cpuABI2+"\n");
					}else if(tempStr.contains("ro.product.cpu.abi=")){
						bfWriter.write("ro.product.cpu.abi="+cpuABI+"\n");
					}else if(tempStr.contains("ro.product.brand=")){
						bfWriter.write("ro.product.brand="+brand+"\n");
					}else if(tempStr.contains("ro.product.board=")){
						bfWriter.write("ro.product.board="+board+"\n");
					}else if(tempStr.contains("ro.build.display.id=")){
						bfWriter.write("ro.build.display.id="+display+"\n");
					}else if(tempStr.contains("ro.build.id=")){
						bfWriter.write("ro.build.id="+buildID+"\n");
					}else if(tempStr.contains("ro.product.manufacturer=")){
						bfWriter.write("ro.product.manufacturer="+manufacture+"\n");
					}else if(tempStr.contains("ro.build.product=")){
						bfWriter.write("ro.build.product="+product+"\n");
					}else if(tempStr.contains("ro.serialno=")){
						bfWriter.write("ro.serialno="+serial+"\n");
					}else if(tempStr.contains("ro.build.date.utc=")){
						bfWriter.write("ro.build.date.utc="+time.substring(0, time.length()-3)+"\n");
					}else if(tempStr.contains("ro.product.name=")){
						bfWriter.write("ro.product.name="+product+"\n");
					}else if(tempStr.contains("ro.product.device=")){
						bfWriter.write("ro.product.device="+device+"\n");
					}else {
						bfWriter.write(tempStr+"\n");
					}
				}
				bfWriter.flush();
				// Android id 写入
//				command.cmd.runWaitFor(new StringBuilder("adb -s "+curDeviceIP+" shell sqlite3").append(" /data/data/com.android.providers.settings/databases/settings.db ").append("\"update secure set value='").append(androidID).append("' where name='android_id';\"").toString());
				command.cmd.runWaitFor("adb -s "+curDeviceIP+" push "+tempPropFile.getAbsolutePath() + " /system/build.prop");
				command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell chmod 644 " + "/system/build.prop");
			}catch (IOException ex){
				ex.printStackTrace();
			} finally{
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (bfWriter != null) {
						bfWriter.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (bfReader != null) {
						bfReader.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void stopPlayerProc(String curDeviceName) {
		CommandResult runWaitFor2 = command.cmd.runWaitFor("VBoxManage controlvm "+curDeviceName+" poweroff");
		System.out.println(runWaitFor2.exit_value+ "  out: "+ runWaitFor2.stdout+"  erro:"+runWaitFor2.stderr);
		command.cmd.runWaitFor("taskkill /f /im player.exe");
		// 确保adb也退出链接
		String deviceIp = getCurDeviceIP();
		if (deviceIp != null) {
//			command.cmd.runWaitFor("adb -s "+deviceIp +" shell");
		}else{
//			command.cmd.runWaitFor("adb shell");
		}
		command.cmd.runWaitFor("adb kill-server");
//		command.cmd.runWaitFor("adb devices");
	}
	
	/**
	 * 模拟用户操作
	 */
	private void injectEvent(int modelId,String curDeviceIP){
		System.out.println("injectEvent:"+modelId);
		BufferedReader reader = null;
		try {
			// 默认的操作
			File file = targetApp.getInjectFile();
			if (modelId != -1) {
				DBCenter center = DBCenter.getInstance();
				center.openDB(DBCenter.DB_NAME);
				String modelInjectPath = center.getModelInjectPath(targetApp, modelId);
				if (modelInjectPath!=null && !modelInjectPath.equals("")) {
					file = new File(modelInjectPath);
				}
			}
			if (file.exists() && file.isFile()) {
				reader = new BufferedReader(new FileReader(file));
				String actionStr = null;
				ArrayList<InjectEvent> eventList = new ArrayList<>();
				InjectEvent event = null;
				// 
				Random rd = new Random();
				while ((actionStr = reader.readLine()) != null) {
					if (actionStr.startsWith("start")) {
						event = new InjectEvent();;
					}else if (actionStr.startsWith("tap") || actionStr.startsWith("swipe") ||actionStr.startsWith("keyevent") || actionStr.startsWith("sleep")|| actionStr.startsWith("text") ) {
						event.getEventCommands().add(actionStr);
					}else if (actionStr.startsWith("percent")) {
						String percentStr = actionStr.replaceAll("percent", "").trim();
						event.setEventPercent(Float.parseFloat(percentStr));
					}else if (actionStr.startsWith("end")) {
						eventList.add(event);
					}else if (actionStr.startsWith("rdtap")) {
						String posStr = actionStr.replace("rdtap ", "");
						String[] split = posStr.split("\\s+");
						int posx1 = Integer.parseInt(split[0]);
						int posy1 = Integer.parseInt(split[1]);
						int posx2 = Integer.parseInt(split[2]);
						int posy2 = Integer.parseInt(split[3]);
						int x = rd.nextInt(posx2 - posx1+1)+posx1;
						int y = rd.nextInt(posy2 - posy1 +1) + posy1;
						actionStr = "tap "+x+" "+ y;
						event.getEventCommands().add(actionStr);
					}
				}
				
				for (InjectEvent ijct : eventList) {
					float percent = ijct.getEventPercent();
					int max = (int) (percent*100);
					int nextInt = rd.nextInt(100)+1;
					if (nextInt<= max) {
						List<String> commands = ijct.getEventCommands();
						for (String commandStr : commands) {
							if (commandStr.contains("sleep")) {
								String timeStr = commandStr.replaceAll("sleep", "").trim();
								Thread.sleep(Integer.parseInt(timeStr));
							}else if(commandStr.contains("text")){
								command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell am broadcast -a com.shikk.imme");
							}else{
								command.cmd.runWaitFor("adb -s "+curDeviceIP+" shell input "+commandStr);
							}
						}
					}
				}
			}else{
				System.err.println("injectEvent  file not exists");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("injectEvent  file not exists");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	// 修改成新的数据并复制出一个新的虚拟机
	protected void createNewDevice2() {
		String curDeviceIP = getCurDeviceIP();
		// 修改代理ip
		command.cmd.runWaitFor("adb -s " + curDeviceIP
				+ " shell am startservice -a com.shikk.ChangeIpService");
		// Android id 写入
		command.cmd
				.runWaitFor(new StringBuilder("adb -s " + curDeviceIP
						+ " shell sqlite3")
						.append(" /data/data/com.android.providers.settings/databases/settings.db ")
						.append("\"update secure set value='")
						.append(androidID)
						.append("' where name='android_id';\"").toString());

	}


	private void showAllBaseVms() {
		CommandResult runWaitFor = command.cmd
				.runWaitFor("VBoxManage list vms");
		if (runWaitFor.success()) {
			String stdout = runWaitFor.stdout;
			StringBuilder builder = new StringBuilder();
			String[] split = stdout.split("\n");
			for (int i = 0; i < split.length; i++) {
				if (split[i] != null && split[i].contains("basevm")) {
					int indexOf = split[i].indexOf("{");
					builder.append(split[i].substring(0,indexOf) + "\n");
				}
			}
			if (builder.length() > 0) {
				textPanalAllBaseVms.setText(builder.toString());
			} else {
				textPanalAllBaseVms.setText("未发现虚拟机");
			}
		} else {
			textPanalAllBaseVms.setText("命令错误：VBoxManage list vms");
		}
	}
	
//	private void copyBaseVM() {
//		String baseVmName = textFieldInputVmName.getText();
//		if (baseVmName !=null && !"".equals(baseVmName)) {
//			int index = baseVmName.indexOf("basevm");
//			Calendar calendar = Calendar.getInstance();
//			
//			String cloneName = baseVmName.substring(0,index-12) +"_"+ calendar.get(Calendar.YEAR)+"_"+(calendar.get(Calendar.MONTH)+1)+"_"+calendar.get(Calendar.DAY_OF_MONTH)+"_"
//					+ calendar.get(Calendar.HOUR_OF_DAY)+ "_"+calendar.get(Calendar.MINUTE)+calendar.get(Calendar.MILLISECOND);
//			command.cmd.runWaitFor("VBoxManage clonevm \""+baseVmName +"\" --mode all --options keepdisknames --name \""+cloneName+"\"" +" --register");
//		}else{
//			System.out.println("虚拟机名称为空");
//		}
//		
//	}
	
	public void cloneDevice() {
		// 复制虚拟机
		String curDeviceName = getCurDeviceName();
		if (curDeviceName !=null && !"".equals(curDeviceName)) {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String cloneName = curDeviceName.substring(1,curDeviceName.length()-21) +"_"+ dateFormat.format(calendar.getTime());
			CommandResult result = command.cmd.runWaitFor("VBoxManage clonevm "+curDeviceName +" --mode all --options keepdisknames --name \""+cloneName+"\"" +" --register");
			if (result.success()) {
				try {
					DBCenter center = DBCenter.getInstance();
					center.openDB(DBCenter.DB_NAME);
					String name = center.getCurDeviceName(targetApp);
					if (name != null && !name.equals("")) {
						center.updateCurDeviceName(targetApp, "\""+cloneName+"\"");
					}else{
						center.addCurDeviceName(targetApp,  "\""+cloneName+"\"");
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// 复制下一个虚拟机的imei到下一个虚拟机的目录下
			String VMDeviceRoot = Constant.VM_DEVICES_DIR+curDeviceName.substring(1,curDeviceName.length()-1);
			String VMDeviceRoot2 = Constant.VM_DEVICES_DIR+cloneName;
			FileUtils.copyFile(VMDeviceRoot+File.separator+"new_device.txt", VMDeviceRoot2+File.separator+"this_device.txt");
		}else{
			System.out.println("虚拟机名称为空");
		}
	}
	/**
	 * 
	 * @param curDeviceName   包含引号
	 */
	private boolean startVM(String curDeviceName) {
		System.out.println("startVM  curDeviceName :"+curDeviceName);
		this.curDeviceName = curDeviceName;
		try {
			if (sleepLongerFlag) {
				Thread.sleep(35000);
				sleepLongerFlag = false;
			}
			DBCenter center = DBCenter.getInstance();
			center.openDB(DBCenter.DB_NAME);
			boolean brushed = center.isVirDeviceBrushed(curDeviceName,new Date(),targetApp);
			if (brushed) {
				return false;
			}
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (curDeviceName !=null) {
			begain = System.currentTimeMillis();
			command.cmd.runPlayerProc("player --vm-name "+curDeviceName);
			new Thread(){
				public void run() {
					String deviceName = null;
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int durTimes = 0;
					while (true) {
						try {
							Thread.sleep(800);
							if (deviceName == null) {
								deviceName = getCurDeviceIP();
								System.out.println("device name : "+deviceName);
								if (deviceName != null &&deviceName.contains("254")) {
									sleepLongerFlag = true;
								}
								if (deviceName != null && deviceName.contains("110") && Constant.DEBUG) {
									sleepLongerFlag = true;
								}
								durTimes++;
								// 以免长时间连接不到设备，强制关闭
								if (durTimes>Constant.NOT_CONNECT_CHECK_TIMES) {
									stopPlayerProc(VirturlDeviceWindow.this.curDeviceName);
									startVM(VirturlDeviceWindow.this.curDeviceName);
									break;
								}
							}else{
								Thread.sleep(1800);
								System.out.println("adb -s "+deviceName +" shell am start -n "+Constant.FIRST_APP_START_COMPONENT);
								CommandResult result = command.cmd.runWaitFor("adb -s "+deviceName +" shell am start -n "+Constant.FIRST_APP_START_COMPONENT);
								System.out.println("xxxxxxxxxxxxxxxxxxx"+result.success());
								if (result.success() && !result.stdout.contains("Error") && !result.stdout.contains("NullPointer")&& !result.stdout.contains("Transaction")) {
									System.out.println("success stdout: "+result.stdout);
									System.out.println("success stderr: "+result.stderr);
									System.err.println("开机 开机-----------------------------------："+(System.currentTimeMillis()-begain));
									begain = System.currentTimeMillis();
//										startApp(deviceName);
									System.out.println("启动程序完成");
									isAppStartOK = true;
									System.err.println("启动启动--------------------------------："+(System.currentTimeMillis()-begain));
									Thread.sleep(2500);
									synchronized(lock){
										lock.notify();
									}
//									updateDB();
									
									break;
								}else{
									System.out.println("faild stdout: "+result.stdout);
									System.out.println("faild stderr: "+result.stderr);
								}
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}.start();
		}else{
			System.out.println("未找到虚拟机");
		}
		return true;
		
	}
	
	// 相应的update app数据库
	private void updateDB(String brushedModels) {
		// 查询在数据库中是否已存在该虚拟机
		BufferedReader reader = null;
		try {
			DBCenter center = DBCenter.getInstance();
			center.openDB(DBCenter.DB_NAME);
			BrushData data = center.isVirDeviceExist(curDeviceName);
			if (data == null) {
				// 添加为新增量
				String VMDeviceRoot = Constant.VM_DEVICES_DIR
						+ curDeviceName
								.substring(1, curDeviceName.length() - 1);
				File thisDevice = new File(VMDeviceRoot + File.separator
						+ "this_device.txt");
				reader = new BufferedReader(new FileReader(thisDevice));
				String imeiStr = reader.readLine();
				if (imeiStr != null) {
					center.addBrushQuality(new Date(), targetApp, new Date(),
							1, 0, curDeviceName, imeiStr.substring(5),brushedModels);
				}
			} else if (center.convertToBeginOfDay(data.getFirstInstTime())
					.getTime() != center.convertToBeginOfDay(new Date())
					.getTime()) {
				// 添加为留存量
				center.addBrushQuality(new Date(), targetApp,
						data.getFirstInstTime(), 0, 1, data.getDeviceName(),
						data.getDeviceImei(),data.getBrushed_models());
			}
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	// 获取当前正在运行的虚拟机的IP，包含引号
	private String getCurDeviceIP() {
		String deviceName = null;
		CommandResult runWaitFor = command.cmd
				.runWaitFor("adb devices");
		String stdout = runWaitFor.stdout;
		String deviceNames[] = stdout.split("\n");
		
		for (int i =  deviceNames.length -1; i >=0 ; i--) {
			if (deviceNames[i]!=null && deviceNames[i].contains("192.168.56")) {
				int index = deviceNames[i].indexOf("\t");
				deviceName = deviceNames[i].substring(0, index);
				return deviceName;
			}
		}
		return deviceName;
	};
	
//	protected void  startApp(String deviceName) {
////		CommandResult runWaitFor1 = command.cmd
////				.runWaitFor("adb -s "+ deviceName +" install D:\\workspace\\MyTestUmeng\\bin\\MyTestUmeng.apk");
////		System.out.println("xxxx111 "+runWaitFor1.success());
//		CommandResult runWaitFor = command.cmd.runWaitFor("adb -s "+deviceName+" shell am start -n "+Constant.APP_START_COMPONENT);
//		if (runWaitFor.success()) {
//			System.out.println("启动程序完成");
//			isAppStartOK = true;
//			System.err.println("启动启动--------------------------------："+(System.currentTimeMillis()-begain));
//		} else {
//			textPanalAllBaseVms.setText("命令错误：VBoxManage list vms");
//		}
//	}
	
	protected void deleteVM(String deviceName) {
		command.cmd.runWaitFor("VBoxManage unregistervm "+deviceName +" --delete");
		command.cmd.runWaitFor("rd /s/q "+Constant.VM_DEVICES_DIR+deviceName.substring(1,deviceName.length()-1));
		try {
			DBCenter center = DBCenter.getInstance();
			center.openDB(DBCenter.DB_NAME);
			center.setBrushDeviceToNull(deviceName);
			center.deleteCurDeviceName(targetApp);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取当前最后一个复制出的虚拟机名称，包含引号
	private String getCurDeviceName() {
		String deviceName = null;
		try {
			DBCenter center = DBCenter.getInstance();
			center.openDB(DBCenter.DB_NAME);
			deviceName = center.getCurDeviceName(targetApp);
			if (deviceName != null && !"".endsWith(deviceName)) {
				return deviceName;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CommandResult runWaitFor = command.cmd
				.runWaitFor("VBoxManage list vms");
		if (runWaitFor.success()) {
			String stdout = runWaitFor.stdout;
			String[] split = stdout.split("\n");
			for (int i = split.length -1 ; i >= 0 ; i--) {
				if (!split[i].contains("basevm")) {
					int indexOf = split[i].indexOf("{");
					deviceName = split[i].substring(0,indexOf-1);
					break;
				}
			}
		} else {
			System.out.println("命令错误：VBoxManage list vms");
		}
		return deviceName;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("AppInfoWindow  windowClosed");
		TargetAppWindow instance = TargetAppWindow.getInstance();
		if (instance != null) {
			instance.frame.setEnabled(true);
			instance.frame.setVisible(true);
		}
		stateWhileFlag = false;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (state != STATE_IDEL) {
			int option = JOptionPane.showConfirmDialog(null, "刷量还在运行，请先停止在推出", " 提示",
					JOptionPane.OK_CANCEL_OPTION);
				TargetAppWindow.frame
						.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}else{
			TargetAppWindow.frame
			.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
				
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
