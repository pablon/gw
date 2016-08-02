package py.gov.ande.control.gateway.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.model.TagMonitorIec61850;

public class MyTableModel<T> extends AbstractTableModel {
	
	ArrayList<String> cols = new ArrayList<>();
	private String[] columnNames;
	private List<TagMonitorIec61850> data;
	private static final Logger logger = LoggerFactory.getLogger(MyTableModel.class);
	

	public  MyTableModel(java.util.List<T> lists) {
		logger.info("Constructor. lists.size: "+lists.size());
		 this.data = (List<TagMonitorIec61850>) lists;
		try {
			T object = (T) lists.get(0);
			columnNames = GenericManager.getColumnNames(object.getClass());
			for (String columName : columnNames) {
				cols.add(columName);
			}
		} catch (Exception e) {
			logger.error("Could not connect to database");
		}
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return cols.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
         TagMonitorIec61850 p = data.get(rowIndex);
        Object[] values=new Object[]{
        		p.getUse(), p.getName(),   p.getInformationTypeId(), p.getTelegramAddress(), 
        		p.getReportingCapacibiliyId(), p.getBuffered(), p.getUnbuffered(), 
        		p.getIedId(), p.getNormalizationId(), p.getBrcbId(), p.getUrcbId()};
        return values[columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
	    return cols.get(column);
	}
	

    /*public Class getColumnClass(int c) {
    	System.out.println("getColumn: "+c);
        return getValueAt(0, c).getClass();
    }*/
    
    /*@Override public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
      }*/
	
	public Class getColumnClass(int column){
		Object value=this.getValueAt(0,column);
		/*System.out.println("getColumnClass: column: "+column+
				", class: "+value.getClass());*/
		if(column==0){
			return Boolean.class;
		}
		return (value==null?Object.class:value.getClass());
	}

}
