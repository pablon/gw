package py.gov.ande.control.gateway.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.gov.ande.control.gateway.util.GenericValidations;

public class ListUtil {
	private static final Logger logger = LoggerFactory.getLogger(ListUtil.class);
	
	public ListUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Método que retorna un array bidimensional del tipo Objects
	 * @param lists Lista de Objetos
	 * @param columnNames Lista de nombres de columnas del tipo String[] 
	 * @return Object[][]
	 * @date 2016-08-02
	 */
	public static Object[][] ListToArray(java.util.List lists, String[] columnNames){
		
		return ListToArray(lists, columnNames, new String [] {""}, new String [] {""} );
	}
	
	/**
	 * Método que retorna un array bidimensional del tipo Objects.
	 * @param lists Lista de objetos
	 * @param columnNames Lista de nombres de columnas del tipo String[] 
	 * @param excludesFields Lista de nombres de columnas del tipo String[] que serán excluidos.
	 * @return Object[][]
	 * @date 2016-08-02
	 */
	public static Object[][] ListToArray(java.util.List lists, String[] columnNames, String[] excludesFields, String[] orderFields){
		Object [][] results;
		Object objectInvoked, objectFk, objectFk2;
		if (lists == null) {
			//TODO
		}

		results = new Object[lists.size()][columnNames.length + 1];
		int x = 0;
		for ( Object item : lists) {
			
			for (int i = 0; i < columnNames.length; i++) {
				String metodo = "get"+columnNames[i].substring(0, 1).toUpperCase()+columnNames[i].substring(1);
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
				results[x][columnNames.length] = item.getClass().getMethod("getId").invoke(item);
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

}
