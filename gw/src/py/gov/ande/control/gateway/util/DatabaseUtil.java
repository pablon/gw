package py.gov.ande.control.gateway.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author pn
 */
public class DatabaseUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
        	sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static String getTableNameFromClass(Class<?> clazz) {
        ClassMetadata hibernateMetadata = getSessionFactory().getClassMetadata(clazz);
        String tableName = null;
        if (hibernateMetadata != null && hibernateMetadata instanceof AbstractEntityPersister) {
            AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
            tableName = persister.getTableName();
        }

        return tableName;
    }
}
