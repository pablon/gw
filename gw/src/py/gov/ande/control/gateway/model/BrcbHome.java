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
 * Home object for domain model class Brcb.
 * @see py.gov.ande.control.gateway.model.Brcb
 * @author Hibernate Tools
 */
public class BrcbHome {

	private static final Log log = LogFactory.getLog(BrcbHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Brcb transientInstance) {
		log.debug("persisting Brcb instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Brcb instance) {
		log.debug("attaching dirty Brcb instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Brcb instance) {
		log.debug("attaching clean Brcb instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Brcb persistentInstance) {
		log.debug("deleting Brcb instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Brcb merge(Brcb detachedInstance) {
		log.debug("merging Brcb instance");
		try {
			Brcb result = (Brcb) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Brcb findById(int id) {
		log.debug("getting Brcb instance with id: " + id);
		try {
			Brcb instance = (Brcb) sessionFactory.getCurrentSession().get("py.gov.ande.control.gateway.model.Brcb", id);
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

	public List<Brcb> findByExample(Brcb instance) {
		log.debug("finding Brcb instance by example");
		try {
			List<Brcb> results = (List<Brcb>) sessionFactory.getCurrentSession()
					.createCriteria("py.gov.ande.control.gateway.model.Brcb").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
