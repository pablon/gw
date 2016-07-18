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
 * Home object for domain model class Normalization.
 * @see py.gov.ande.control.gateway.model.Normalization
 * @author Hibernate Tools
 */
public class NormalizationHome {

	private static final Log log = LogFactory.getLog(NormalizationHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Normalization transientInstance) {
		log.debug("persisting Normalization instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Normalization instance) {
		log.debug("attaching dirty Normalization instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Normalization instance) {
		log.debug("attaching clean Normalization instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Normalization persistentInstance) {
		log.debug("deleting Normalization instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Normalization merge(Normalization detachedInstance) {
		log.debug("merging Normalization instance");
		try {
			Normalization result = (Normalization) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Normalization findById(int id) {
		log.debug("getting Normalization instance with id: " + id);
		try {
			Normalization instance = (Normalization) sessionFactory.getCurrentSession()
					.get("py.gov.ande.control.gateway.model.Normalization", id);
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

	public List<Normalization> findByExample(Normalization instance) {
		log.debug("finding Normalization instance by example");
		try {
			List<Normalization> results = (List<Normalization>) sessionFactory.getCurrentSession()
					.createCriteria("py.gov.ande.control.gateway.model.Normalization").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
