package py.gov.ande.control.gateway.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.hibernate.Session;

public class ListUtil {

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
		
		return ListToArray(lists, columnNames, new String [] {""} );
	}
	
	/**
	 * Método que retorna un array bidimensional del tipo Objects.
	 * @param lists Lista de objetos
	 * @param columnNames Lista de nombres de columnas del tipo String[] 
	 * @param excludesFields Lista de nombres de columnas del tipo String[] que serán excluidos.
	 * @return Object[][]
	 * @date 2016-08-02
	 */
	public static Object[][] ListToArray(java.util.List lists, String[] columnNames, String[] excludesFields ){
		Object [][] results;
		if (lists == null) {
			//TODO
		}

		results = new Object[lists.size()][columnNames.length + 1];
		int x = 0;
		for ( Object item : lists) {

			for (int i = 0; i < columnNames.length; i++) {
				String metodo = "get"+columnNames[i].substring(0, 1).toUpperCase()+columnNames[i].substring(1);
				try {
					results[x][i] = item.getClass().getMethod(metodo).invoke(item);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("No existe Nombre metodo: "+metodo);
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
