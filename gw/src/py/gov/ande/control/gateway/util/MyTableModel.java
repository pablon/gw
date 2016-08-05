package py.gov.ande.control.gateway.util;

import java.util.HashSet;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Clase utilizada para crear tablas en forma genérica
 * @author Pablo
 * @date 2016-08-02
 * @param <T>
 */
public class MyTableModel<T> extends AbstractTableModel {
	
	private static final long serialVersionUID = -430490490783397713L;
	private String[] columnNames;
	private Object[][] data;
	private HashSet updatedRows = new HashSet();

	/**
	 * Constructor de la tabla que recibe como parámetros, un array bidimensional del tipo Object, 
	 * y un array con la lista de nombres de las columnas. 
	 * @param Object [][] data
	 * @param String[] arrayList
	 */
	public MyTableModel(Object [][] data, String[] columnNames) {
			this.columnNames = columnNames;
			this.data = data;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
	    return columnNames[column];
	}

	@SuppressWarnings("unchecked")
	public Class getColumnClass(int column) {
        for (int row = 0; row < getRowCount(); row++){
            Object o = getValueAt(row, column);
            if (o != null){
                return o.getClass();
            }
        }
        return Object.class;
	}
	
    public boolean isCellEditable(int row, int col) {
        if (col < 1) {
            return true;
        } else {
            return false;
        }
    }
    
    public void setValueAt(Object value, int row, int col) {
    	Object oldValue = getValueAt(row, col);
		if ((oldValue == null) && (value != null)) {
			updatedRows.add(new Integer(row));
		} else if (!oldValue.equals(value)) {
			updatedRows.add(new Integer(row));
		}
		data[row][col] = value;
		fireTableCellUpdated(row, col);
    }
    
    /**
     * Método que retorna un array de indice de filas actualizadas
     * @return
     * @author Pablo
     * @date 2016-08-04
     */
	public Integer[] getUpdatedRowIndexes() {
		Integer[] keys = (Integer[]) updatedRows.toArray(new Integer[0]);
		return keys;
	}
    
}
