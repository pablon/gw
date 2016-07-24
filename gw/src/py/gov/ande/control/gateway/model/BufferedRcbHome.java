package py.gov.ande.control.gateway.model;
// Generated 21/07/2016 02:30:08 PM by Hibernate Tools 5.1.0.Alpha1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class BufferedRcb.
 * @see py.gov.ande.control.gateway.model.BufferedRcb
 * @author Hibernate Tools
 */
public class BufferedRcbHome {

	private static final Log log = LogFactory.getLog(BufferedRcbHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(BufferedRcb transientInstance) {
		log.debug("persisting BufferedRcb instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(BufferedRcb instance) {
		log.debug("attaching dirty BufferedRcb instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BufferedRcb instance) {
		log.debug("attaching clean BufferedRcb instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BufferedRcb persistentInstance) {
		log.debug("deleting BufferedRcb instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BufferedRcb merge(BufferedRcb detachedInstance) {
		log.debug("merging BufferedRcb instance");
		try {
			BufferedRcb result = (BufferedRcb) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BufferedRcb findById(int id) {
		log.debug("getting BufferedRcb instance with id: " + id);
		try {
			BufferedRcb instance = (BufferedRcb) sessionFactory.getCurrentSession()
					.get("py.gov.ande.control.gateway.model.BufferedRcb", id);
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

	public List<BufferedRcb> findByExample(BufferedRcb instance) {
		log.debug("finding BufferedRcb instance by example");
		try {
			List<BufferedRcb> results = (List<BufferedRcb>) sessionFactory.getCurrentSession()
					.createCriteria("py.gov.ande.control.gateway.model.BufferedRcb").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
