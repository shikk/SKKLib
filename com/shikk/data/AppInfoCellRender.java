package com.shikk.data;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class AppInfoCellRender extends DefaultTableCellRenderer{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column)
    {
        // TODO Auto-generated method stub
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        //隔行换色
        if (column ==0) {
        	comp.setBackground(Color.LIGHT_GRAY);
		}else if(row%2 ==0 ){
            comp.setBackground(Color.ORANGE);
        }else if(row%2 ==1){
            comp.setBackground(Color.WHITE);
        }
//        if("2".equals(value+"")){
//            comp.setBackground(Color.RED);
//        }else {
//            //如果不加这一行，那么全部变红
//            comp.setBackground(Color.WHITE);
//        }
        return comp;
    }
}
