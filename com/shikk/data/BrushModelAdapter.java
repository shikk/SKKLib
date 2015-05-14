package com.shikk.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class BrushModelAdapter extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<BrushModel> list;
	private boolean isTableEditable;
	private String[] columnNames = {"","名称","模块留存率","模拟脚本路径"};
	
	public BrushModelAdapter(ArrayList<BrushModel> list,boolean isTableEditable){
		if (list !=null) {
			this.list = list;
		}
		else {
			this.list = new ArrayList<>();
		}
		this.isTableEditable = isTableEditable;
	}
	
	public List<BrushModel> getModelList(){
		return this.list;
	}
	
	public void setModelList(ArrayList<BrushModel> list){
		this.list = list;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public String getColumnName(int arg0) {
		// TODO Auto-generated method stub
		return columnNames[arg0];
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg1==0) {
			return list.get(arg0).getChecked();
		}else if (arg1 == 1) {
			return list.get(arg0).getName();
		}else if (arg1 == 2) {
			String brushRate = list.get(arg0).getBrushRate().toString();
			return brushRate.replace("[", "").replace("]", "");
		}else if (arg1 == 3) {
			return list.get(arg0).getInjectFilePath();
		}
		return null;
	}
	
	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println("value:"+arg0+"   row:"+arg1+"    column:"+arg2);
		if (arg2 == 0) {
			list.get(arg1).setChecked((Boolean) arg0);
		}else if (arg2 == 1) {
			list.get(arg1).setName((String) arg0);
		}else if (arg2 == 2) {
			String valueStr = (String) arg0;
			String[] split = valueStr.split(",");
			ArrayList<Float> rateList = new ArrayList<>();
			for (int i = 0; i < split.length; i++) {
				if (split[i]!= null && !split[i].equals("")) {
					rateList.add(Float.parseFloat(split[i]));
				}
			}
			list.get(arg1).setBrushRate(rateList);
		}else if (arg2 == 3) {
			list.get(arg1).setInjectFilePath((String) arg0);
		}
		
		try {
			DBCenter center = DBCenter.getInstance();
			center.openDB(DBCenter.DB_NAME);
			center.updateModel(list.get(arg1));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return isTableEditable;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		 if(columnIndex==0)
         { return Boolean.class;
         }
         return Object.class; 
	}
	
	public void addRow(){
		list.add(new BrushModel());
	}

}
