package py.gov.ande.control.gateway.model;
// Generated 12/07/2016 12:48:13 AM by Hibernate Tools 5.1.0.Alpha1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class Drivers.
 * @see py.gov.ande.control.gateway.model.Drivers
 * @author Hibernate Tools
 */
public class DriversHome {

	private static final Log log = LogFactory.getLog(DriversHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Drivers transientInstance) {
		log.debug("persisting Drivers instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Drivers instance) {
		log.debug("attaching dirty Drivers instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Drivers instance) {
		log.debug("attaching clean Drivers instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Drivers persistentInstance) {
		log.debug("deleting Drivers instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Drivers merge(Drivers detachedInstance) {
		log.debug("merging Drivers instance");
		try {
			Drivers result = (Drivers) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Drivers findById(int id) {
		log.debug("getting Drivers instance with id: " + id);
		try {
			Drivers instance = (Drivers) sessionFactory.getCurrentSession()
					.get("py.gov.ande.control.gateway.model.Drivers", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Drivers> findByExample(Drivers instance) {
		log.debug("finding Drivers instance by example");
		try {
			List<Drivers> results = (List<Drivers>) sessionFactory.getCurrentSession()
					.createCriteria("py.gov.ande.control.gateway.model.Drivers").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
