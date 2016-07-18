package py.gov.ande.control.gateway.model;
// Generated 18/07/2016 01:42:16 AM by Hibernate Tools 5.1.0.Alpha1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class Urcb.
 * @see py.gov.ande.control.gateway.model.Urcb
 * @author Hibernate Tools
 */
public class UrcbHome {

	private static final Log log = LogFactory.getLog(UrcbHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Urcb transientInstance) {
		log.debug("persisting Urcb instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Urcb instance) {
		log.debug("attaching dirty Urcb instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Urcb instance) {
		log.debug("attaching clean Urcb instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Urcb persistentInstance) {
		log.debug("deleting Urcb instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Urcb merge(Urcb detachedInstance) {
		log.debug("merging Urcb instance");
		try {
			Urcb result = (Urcb) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Urcb findById(int id) {
		log.debug("getting Urcb instance with id: " + id);
		try {
			Urcb instance = (Urcb) sessionFactory.getCurrentSession().get("py.gov.ande.control.gateway.model.Urcb", id);
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

	public List<Urcb> findByExample(Urcb instance) {
		log.debug("finding Urcb instance by example");
		try {
			List<Urcb> results = (List<Urcb>) sessionFactory.getCurrentSession()
					.createCriteria("py.gov.ande.control.gateway.model.Urcb").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
