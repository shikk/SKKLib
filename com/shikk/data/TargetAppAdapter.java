package com.shikk.data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TargetAppAdapter extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TargetApp> appList = new ArrayList<>();
	private String columnName[] ={"序号","名称","包名","版本名","版本号","留存率","每日新增上线数"};
	public void setAppList(ArrayList<TargetApp> appList) {
		if (appList == null) {
			this.appList = new ArrayList<>();
		}
		this.appList = appList;
	}

	public List<TargetApp> getAppList(){
		return appList;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return appList.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		Object object = null;
		switch (column) {
		case 0:
			object= appList.get(row).getAppId();
			break;
		case 1:
			object= appList.get(row).getAppName();
			break;
		case 2:
			object= appList.get(row).getPkgName();
			break;
		case 3:
			object= appList.get(row).getVersionName();
			break;
		case 4:
			object= appList.get(row).getVersionCode();
			break;
		case 5:
			object= appList.get(row).getBrushRate();
			break;
		case 6:
			object= appList.get(row).getMaxNewQuality();
			break;
		default:
			break;
		}
		return object;
	}

	/**
	 * 获得单元格的列名
	 */
	@Override
	public String getColumnName(int col) {
		return columnName[col];
	}

	
	/**
	 * 功能：设定单元格为可编辑
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	/**
	 * 功能：将用户修改后的值赋给指定的单元格
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		System.out.println("setValueAt:"+"i :"+rowIndex+"   j:"+columnIndex);
	}
	
	
}
