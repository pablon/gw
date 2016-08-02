package py.gov.ande.control.gateway.util;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
//import javax.persistence.criteria.CriteriaQuery;		//investigar para implementar este criteria
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Clase para utilidades de acceso a la BBDD mediante metodos genericos
 * que manejan la mayoria de los casos posibles
 *
 * Los metodos de busqueda proveen acceso paginado y no paginado, filtrado o no
 * La API provee una interfaz para ordenar opcionalmente el resultado por una cantidad
 * N de ordenes posibles
 *
 * @author pn
 */
public class GenericManager {

    /**
     * Obtener un objeto de la base de datos a partir de su clase y su id
     *
     * @param clazz el literal de clase para el objeto pedido
     * @param id el identificador del objeto
     * @return una instancia del objeto recuperada desde la BBDD
     */
    public static <T> T getObjectById(Class<T> clazz, int id) {
        Session session = createNewSession();
        Criteria criteria = session.createCriteria(clazz);
        criteria.add(Restrictions.eq("id", id));
        List<T> results = criteria.list();
        session.close();
        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    /**
     * Grabar un objeto nuevo en la BBDD
     *
     * @param object un objeto no almacenado previamente
     */
    public static void saveObject(Object object) {
        Session session = createNewSession();
        Transaction tx = session.beginTransaction();
        session.persist(object);
        tx.commit();
        session.close();
    }
    
    public static void saveObject(Object object, Session session) {
        //session.persist(object);
        session.saveOrUpdate(object);
    }
    
    

    /**
     * Actualizar los campos de un objeto en la BBDD
     *
     * @param object un objeto previamente almacenado, a actualizar
     * @return una instancia persistida del mismo objeto
     */
    public static Object updateObject(Object object) {
        Session session = createNewSession();
        Transaction tx = session.beginTransaction();
        Object mergedObject = session.merge(object);
        tx.commit();
        session.close();
        return mergedObject;
    }
    
    //public static Object updateObject(Object object, Session session) {
    public static void updateObject(Object object, Session session) {
        //Object mergedObject = session.merge(object);
    	session.saveOrUpdate(object);
        //return mergedObject;
    }

    /**
     * Borrar un objeto en base a su clase y id
     * Se asume que dicho objeto no posee FK que referencia al mismo en otras tablas, pero se
     * debe verificar el resultado de la operacion para ver si surgio algun tipo de error
     *
     * @param clazz la clase del objeto a borrar
     * @param id el identificador del objeto
     * @return la respuesta del motor a la accion
     */
    public static DatabaseOperationResult deleteObjectById(Class clazz, int id) {
        DatabaseOperationResult.ErrorType errorType = null;
        int recordsAffected = 0;
        RuntimeException exception = null;
        try {
            Session session = createNewSession();
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM " + clazz.getSimpleName() + " o WHERE o.id = :id");
            query.setParameter("id", id);
            recordsAffected = query.executeUpdate();
            tx.commit();
            session.close();
        } catch (RuntimeException e) {
            if (e instanceof ConstraintViolationException) {
                errorType = DatabaseOperationResult.ErrorType.CONSTRAINT_VIOLATION;
            } else {
                errorType = DatabaseOperationResult.ErrorType.OTHER;
            }
            exception = e;
        }

        return new DatabaseOperationResult(errorType, recordsAffected, exception);
    }

    /**
     * Listar todos los objetos de una clase dada, ordenando de manera opcional
     * por una o mas propiedades
     *
     * @param clazz la clase del objeto a traer
     * @param orders una lista de N ordenes posibles al hacer el select
     * @return una lista de los objetos
     */
    public static <T> List<T> getAllObjects(Class<T> clazz, Order... orders) {
        Session session = createNewSession();
        //session.beginTransaction();
        
        Criteria criteria = session.createCriteria(clazz);
        for (Order order : orders) {
            criteria.addOrder(order);
        }

        return criteria.list();
        
        /*session.beginTransaction();
        List result = session.createQuery("from "+clazz.getClass()).getResultList();
        session.getTransaction().commit();
        session.close();
        
        return result;*/
        
        //List result = session.createQuery("from "+clazz.getClass()).getResultList();
        //return result;
    }


    /**
     * Listar todos los objetos de una clase dada, aplicando una lista de filtros
     * especificados, ordenando de manera opcional por una o mas propiedades. 
     * Ejemplo: GenericManager.getFilteredObjects(BienesInformaticos.class,Arrays.asList(Restrictions.isNull("bienesInformaticos"),Restrictions.eq("estaActivo", 1)),Order.asc("id") );
     * @param clazz la clase del objeto a traer
     * @param criterions la lista de criterios especificados
     * @param orders los criterios de ordenes deseados, si los hubiera
     * @return una lista de objetos filtrada
     */
    public static <T> List<T> getFilteredObjects(Class<T> clazz, List criterions, Order... orders) {
        Session session = createNewSession();
        Criteria criteria = session.createCriteria(clazz);
        for (Iterator it = criterions.iterator(); it.hasNext();) {
            Criterion c = (Criterion) it.next();
            criteria.add(c);
        }
        for (Order order : orders) {
            criteria.addOrder(order);
        }
        
        return criteria.list();
    }

    /**
     * Obtener el primer objeto encontrado de la base de datos a partir de su clase y un criterio de búsqueda
     * @param <T>
     *
     * @param clazz el literal de clase para el objeto pedido
     * @param criterions la lista de criterios especificados
     * @return una instancia del objeto recuperada desde la BBDD
     */
    public static <T> Object getFilteredObject(Class<T> clazz, List criterions) {
        Session session = createNewSession();
        @SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(clazz);

        for (@SuppressWarnings("rawtypes")
		Iterator it = criterions.iterator(); it.hasNext();) {
            Criterion c = (Criterion) it.next();
            criteria.add(c);
        }
        
        @SuppressWarnings("rawtypes")
		List results = criteria.list();
        session.close();
        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }
    
    
    /**
     * Buscar en la base de datos, utilizando un objeto POJO cualquiera conocido por Hibernate
     * para crear un criterio dinamico. Se puede aplicar un orden al resultado si asi se desea
     *
     * @param object un POJO cualquiera conocido por Hibernate, para ser utilizado como filtro
     * @param orders los criterios de ordenes deseados, si los hubiera
     * @return una lista de objetos en la cuales se busca
     */
    public static List searchObjectsLike(Object object, Order... orders) {
        Session session = createNewSession();
        Criteria criteria = session.createCriteria(object.getClass());
        criteria.add(Example.create(object).ignoreCase().excludeZeroes().enableLike(MatchMode.ANYWHERE));
        for (Order order : orders) {
            criteria.addOrder(order);
        }
        
        return criteria.list();
    }

    /**
     * Metodo auxiliar para crear una nueva sesion
     *
     * @return una nueva sesion a partir del factory
     */
    public static Session createNewSession() {
        SessionFactory factory = DatabaseUtil.getSessionFactory();
        Session session = factory.openSession();
        
        return session;
    }

    /**
     * Metodo que obtiene la cantidad de objetos en base a un criterio
     * @param criterio de busqueda. Ejemplo: "From Articulo art where art.articulo.id = " + articuloOld.getArticulo().getId()
     * @return int retorna la cantidad de objetos
     */
    public static int getCountObjets(String criterio) {
        DatabaseOperationResult.ErrorType errorType = null;
        RuntimeException exception = null;
        Query query = null;
        try {
            Session session = createNewSession();
            query = session.createQuery(criterio);
        } catch (RuntimeException e) {
            if (e instanceof ConstraintViolationException) {
                errorType = DatabaseOperationResult.ErrorType.CONSTRAINT_VIOLATION;
            } else {
                errorType = DatabaseOperationResult.ErrorType.OTHER;
            }
            exception = e;
        }
        if(query.list().isEmpty())
            return 0;
        else
            return query.list().size();
    }
    
    /**
    *
    * @param Padre
    * @param hijo
    * @author pablo
    */
   public static DatabaseOperationResult BorrarTodosLosHijos(Class padre , Object hijo){
       DatabaseOperationResult.ErrorType errorType = null;
       int recordsAffected = 0;
       RuntimeException exception = null;
       try {
           Session session = createNewSession();
           Transaction tx = session.beginTransaction();
           Query query = session.createQuery("DELETE FROM " + padre.getSimpleName() + " padre WHERE padre."+hijo.getClass().getSimpleName().toString().toLowerCase()+" = :hijo");
           query.setParameter("hijo", hijo);
           System.out.print(query.toString());
           recordsAffected = query.executeUpdate();
           tx.commit();
           session.close();
       } catch (RuntimeException e) {
           if (e instanceof ConstraintViolationException) {
               errorType = DatabaseOperationResult.ErrorType.CONSTRAINT_VIOLATION;
               System.out.print("Error1: "+ e.getMessage());
           } else {
               errorType = DatabaseOperationResult.ErrorType.OTHER;
               System.out.print("Error2: " + e.getMessage());
           }
           exception = e;
       }

       return new DatabaseOperationResult(errorType, recordsAffected, exception);

    }

   /**
    * Metodo que realiza una consulta, y retorna la lista de tuplas.
    * ejemplo: From Drivers drivers ORDER BY drivers.subestation DESC
    * @param <T>
    * @param criterio
    * @return List
    * @author pablo
    */
   public static <T> List<T> getListBasedOnCriteria(String criterio){
       DatabaseOperationResult.ErrorType errorType = null;
       RuntimeException exception = null;
       Query query = null;
       try {
           Session session = createNewSession();
           Transaction tx = session.beginTransaction();
           query = session.createQuery(criterio);
       } catch (RuntimeException e) {
           if (e instanceof ConstraintViolationException) {
               errorType = DatabaseOperationResult.ErrorType.CONSTRAINT_VIOLATION;
           } else {
               errorType = DatabaseOperationResult.ErrorType.OTHER;
           }
           exception = e;
       }
       if(query == null)
           return null;
       else
           return query.list();        
   }
   
   /**
    * Método que retorna un objeto en base a un criterio de busqueda.
    * @param criterio de busqueda. Ejemplo: "From Articulo art where art.articulo.id = " + articuloOld.getArticulo().getId()
    * @return Object
    */
   public static <T> Object getObjectBasedOnCriteria(String criterio){
       DatabaseOperationResult.ErrorType errorType = null;
       RuntimeException exception = null;
       Query query = null;
       try {
           Session session = createNewSession();
           Transaction tx = session.beginTransaction();
           query = session.createQuery(criterio);
       } catch (RuntimeException e) {
           if (e instanceof ConstraintViolationException) {
               errorType = DatabaseOperationResult.ErrorType.CONSTRAINT_VIOLATION;
           } else {
               errorType = DatabaseOperationResult.ErrorType.OTHER;
           }
           exception = e;
       }
       if(query == null)
           return null;
       else
           return query;        
   }
   
   /**
    * Método que retorna el nombre de las columnas de una tabla cualquiera
    * @param <T>
    * @param clazz
    * @return String[]
    * @author pablo
    * @date 2016-08-01
    */
   public static String[] getColumnNames(Class clazz){
	   RuntimeException exception = null;
	   String [] columnNames = null;
       try {
           Session session = createNewSession();
           columnNames = session.getSessionFactory().getClassMetadata(clazz).getPropertyNames();
           
       } catch (RuntimeException e) {
    	   exception = e;
    	   columnNames = new String []{"Hubo un error"};
    	   System.out.println("error: "+e.getMessage());
       }
	   
	   return columnNames;
   }

}
