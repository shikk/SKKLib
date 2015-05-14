package com.shikk.data;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class BrushInfoAdapter extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String columnName[] ={"日期","新增量","留存量（总）"};
	private List<BrushData> dataList = new ArrayList<>();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private NumberFormat decimalFormat = NumberFormat.getPercentInstance();
	
	public BrushInfoAdapter() {
		// TODO Auto-generated constructor stub
		decimalFormat.setMinimumFractionDigits(2);
	}
	
	public List<BrushData> getDataList() {
		return dataList;
	}

	public void setDataList(List<BrushData> dataList) {
		this.dataList = dataList;
		if (this.dataList == null) {
			this.dataList = new ArrayList<>();
		}
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		if (column<3) {
			return columnName[column];
		}else{
			return (column-2)+"天后";
		}
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 38;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		if (column==0) {
			return dateFormat.format(dataList.get(row).getTime());
		}else if (column == 1) {
			return dataList.get(row).getNewQuality();
		}else if (column == 2) {
			return dataList.get(row).getRemainQuality();
		}else {
			ArrayList<Float> list = dataList.get(row).getDaylyRate();
			if (list.size() > column-3) {
				Float float1 = list.get(column-3);
				if (float1.isNaN()) {
					return "0%";
				}
				return decimalFormat.format(float1)+" ("+dataList.get(row).getNewQuality()*float1+")";
			}else{
				return null;
			}
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}
}
