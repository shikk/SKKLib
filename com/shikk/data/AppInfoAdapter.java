package com.shikk.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.NumberFormatter;

public class AppInfoAdapter extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean canEditeCell = false;
	private boolean isNewApp ;
	private TargetApp app;;
	private NumberFormat df;
//	private final String[] columnNames = {"column1","column2","column3","column4","column5","column6","column7","column8"};
	public AppInfoAdapter(TargetApp app,boolean isNewApp, boolean canEditCell ){
		this.app = app;
		if (app == null) {
			this.app = new TargetApp();
		}
		this.isNewApp = isNewApp;
		this.canEditeCell = canEditCell;
		df = DecimalFormat.getPercentInstance();
	}
	
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 12;
	}

	@Override
	public String getColumnName(int i) {
		// TODO Auto-generated method stub
		return i+"";
	}
	
	
	@Override
	public Object getValueAt(int i, int j) {
		// TODO Auto-generated method stub
		if (i==0 && j==0) {
			return "间隔时间对应的留存率";
		}else if (i==10 && j==0) {
			return "星期对应的新增上限";
		}else if (i<10 && i%2 ==0  && j != 0 ) {
			return "第"+((i/2)*7+j)+"天";
		}else if (i%2 ==0 && j!=0) {
			return "星期"+j;
		}else if (i<10 && i%2 ==1  && j != 0) {
			if (app!= null) {
				int idx =(i/2)*7+j;
				ArrayList<Float> brushRate = app.getBrushRate();
				if (idx  < brushRate.size()+1) {
					return df.format(brushRate.get(idx-1));
				}
			}else{
				return "";
			}
		}else if(i%2 ==1 && j!=0){
			if (app!= null) {
				int idx =j;
				ArrayList<Integer> newQuality = app.getMaxNewQuality();
				if (idx  < newQuality.size()+1) {
					return newQuality.get(idx-1)+"";
				}
			}else{
				return "";
			}
		}
		return "";
	}

	
	@Override
	public boolean isCellEditable(int i, int j) {
		// TODO Auto-generated method stub
		if (i==0 && j==0) {
			return false;
		}else if (i==10 && j==0) {
			return false;
		}else if (i<10 && i%2 ==0  && j != 0 ) {
			return false;
		}
		return canEditeCell;
	}
	
	@Override
	public void setValueAt(Object obj, int i, int j) {
		Object valueOld = getValueAt(i, j);
		if (isNewApp) {
			if (i*8+j <= 80) { // 该留存率
				try {
					Number number = df.parse((String)obj);
					System.out.println("修改： pos:"+((i/2)*7+j)+"    value:"+number.floatValue());
					app.getBrushRate().set((i/2)*7+j-1, number.floatValue());
					this.getValueAt(i, j);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "数据格式错误,修改未生效","提示", JOptionPane.ERROR_MESSAGE);
				}
				
			}else{
				try {
					app.getMaxNewQuality().set(j-1, Integer.parseInt(obj.toString()));
					this.getValueAt(i, j);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "数据格式错误,修改未生效","提示", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if (!obj.equals(valueOld)) {
			int resualt = JOptionPane.showConfirmDialog(null, "数据已发生改变，确定修改吗？","提示", JOptionPane.OK_CANCEL_OPTION);
			if(resualt == JOptionPane.OK_OPTION){
				if (i*8+j <= 80) { // 该留存率
					try {
						Number number = df.parse((String)obj);
						System.out.println("修改： pos:"+((i/2)*7+j)+"    value:"+number.floatValue());
						app.getBrushRate().set((i/2)*7+j-1, number.floatValue());
						this.getValueAt(i, j);
//						DBCenter center = DBCenter.getInstance();
//						center.openDB(DBCenter.DB_NAME);
//						center.updateTargetApp(app);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "数据格式错误,修改未生效","提示", JOptionPane.ERROR_MESSAGE);
					}
					
				}else{
					try {
						app.getMaxNewQuality().set(j-1, Integer.parseInt(obj.toString()));
						this.getValueAt(i, j);
//						DBCenter center = DBCenter.getInstance();
//						center.openDB(DBCenter.DB_NAME);
//						center.updateTargetApp(app);
					} catch (NumberFormatException e) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "数据格式错误,修改未生效","提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
	
	@Override
	public void fireTableCellUpdated(int i, int j) {
		// TODO Auto-generated method stub
		super.fireTableCellUpdated(i, j);
		System.out.println("i:");
	}
}
