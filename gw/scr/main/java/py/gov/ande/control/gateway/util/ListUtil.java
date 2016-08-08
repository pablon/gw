package py.gov.ande.control.gateway.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.ejb.FinderException;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.model.TagMonitorIec61850;
import py.gov.ande.control.gateway.util.GenericValidations;

public class ListUtil {
	private static final Logger logger = LoggerFactory.getLogger(ListUtil.class);
	private static String[] columnNamesFinal;
	
	public String[] getColumnNamesFinal() {
		return columnNamesFinal;
	}

	protected static void setColumnNamesFinal(String[] columnNamesFinal) {
		ListUtil.columnNamesFinal = columnNamesFinal;
	}

	public ListUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param tags
	 * @param columnNames
	 * @param excludesFields
	 * @return
	 * @author Pablo
	 * @date 2016-08-06
	 */
	public Object[][] ListToArray(List<TagMonitorIec61850> lists, String[] columnNames, String[] excludesFields) {
		
		return ListToArray(lists, columnNames, excludesFields, new String [] {""} );
	}
	
	/**
	 * Método que retorna un array bidimensional del tipo Objects
	 * @param lists Lista de Objetos
	 * @param columnNames Lista de nombres de columnas del tipo String[] 
	 * @return Object[][]
	 * @date 2016-08-02
	 */
	public Object[][] ListToArray(java.util.List lists, String[] columnNames){
		
		return ListToArray(lists, columnNames, new String [] {""}, new String [] {""} );
	}
	
	/**
	 * Método que retorna un array bidimensional del tipo Objects.
	 * @param lists Lista de objetos
	 * @param columnNames Lista de nombres de columnas del tipo String[] 
	 * @param excludesFields Lista de nombres de columnas del tipo String[] que serán excluidos.
	 * @param orderFields String[] lista de campos que se tendrán en cuenta para ser los primeros de la lista. 
	 * @return Object[][]
	 * @date 2016-08-02
	 */
	public Object[][] ListToArray(java.util.List lists, String[] columnNames, String[] excludesFields, String[] orderFields){
		setColumnNamesFinal(columnNames);
		Object [][] results;
		Object objectInvoked, objectFk;
		if (lists == null) {
			//TODO
		}

		results = new Object[lists.size()][columnNames.length + 1 - excludesFields.length];
		setColumnNamesFinal( filteringOfColumnsIncluded(getColumnNamesFinal(), excludesFields));
		setColumnNamesFinal( columnSortingProcess(getColumnNamesFinal(), orderFields));
		
		int x = 0;
		for ( Object item : lists) {
			
			for (int i = 0; i < columnNamesFinal.length; i++) {	
				
				String metodo = "get"+columnNamesFinal[i].substring(0, 1).toUpperCase()+columnNamesFinal[i].substring(1);
				try {
					//logger.info("Object:"+item.getClass().);
					objectInvoked = item.getClass().getMethod(metodo).invoke(item);
					if (objectInvoked != null && GenericValidations.isPrimitiveOrPrimitiveWrapperOrString(objectInvoked.getClass())){
					results[x][i] = objectInvoked;
					}else {
						if(objectInvoked != null){
							objectFk =  objectInvoked.getClass().getMethod("toString").invoke(objectInvoked);
							//logger.info("objectInvoked is Not null. objectFK= "+objectFk);
							results[x][i] = objectFk;
						}else{
							//logger.info("objectInvoked Null. Metodo: "+metodo+", i="+i);
							results[x][i] = null;
						}
					}
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("No existe metodo: "+metodo);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// se intenta agregar una ultima columna con el id de la fila de la bd.
			// utilizado al momento de rescatar las filas cambiadas, para actualizar la bd según el id
			try {
				results[x][columnNamesFinal.length] = item.getClass().getMethod("getId").invoke(item);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			x++;
		}
		return results;
	}

	/**
	 * Método que recibe una lista de nombres, y los ordena según el órden de la lista sugerida.
	 * realiza un recorrido del array orderFields, y 
	 * busca si se encuentra el campo en el array columnNames.
	 * si lo encuentra, lo lleva hacia los primeros lugares
	 * @param columnNames
	 * @param orderFields 
	 * @return String[] columnNames
	 * @autor Pablo
	 * @date 2016-08-06
	 */
	private static String[] columnSortingProcess(String[] columnNames, String[] orderFields) {
		String temp;
		for(int i = 0; i< orderFields.length; i++){
			for(int j = 0; j< columnNames.length; j++){
				if(orderFields[i] == columnNames[j]){
					temp = columnNames[i];				//rancho
					columnNames[i] = columnNames[j];	//casa donde era rancho
					columnNames[j] = temp;				//rancho donde era casa
				}
			}
		}
		
		return columnNames;
	}

	/**
	 * Método que procesa el filtrado de columnas sin los campos excluidos
	 * @param columnNames
	 * @param excludesFields
	 * @return String[]
	 * @author Pablo
	 * @date 2016-08-06
	 */
	private static String[] filteringOfColumnsIncluded(String[] columnNames, String[] excludesFields) {
		int count = 0;
		String[] columnNamesFinal = new String[columnNames.length - excludesFields.length];
		for (int i = 0; i < columnNames.length; i++) {
			//si la columna esta entre los excluidos, saltar
			boolean skip = false;
			for ( String field : excludesFields) {
				if(field == columnNames[i]){
					skip = true;
					break;
				}
			}
			if(!skip){
				columnNamesFinal[count] = columnNames[i];
				count++;
			}
		}
		return columnNamesFinal;
	}



}
