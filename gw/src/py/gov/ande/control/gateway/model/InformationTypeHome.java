package py.gov.ande.control.gateway.model;
// Generated 03/07/2016 08:06:54 PM by Hibernate Tools 5.1.0.Alpha1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class InformationType.
 * @see py.gov.ande.control.gateway.model.InformationType
 * @author Hibernate Tools
 */
public class InformationTypeHome {

	private static final Log log = LogFactory.getLog(InformationTypeHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(InformationType transientInstance) {
		log.debug("persisting InformationType instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(InformationType instance) {
		log.debug("attaching dirty InformationType instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InformationType instance) {
		log.debug("attaching clean InformationType instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(InformationType persistentInstance) {
		log.debug("deleting InformationType instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InformationType merge(InformationType detachedInstance) {
		log.debug("merging InformationType instance");
		try {
			InformationType result = (InformationType) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public InformationType findById(int id) {
		log.debug("getting InformationType instance with id: " + id);
		try {
			InformationType instance = (InformationType) sessionFactory.getCurrentSession()
					.get("py.gov.ande.control.gateway.model.InformationType", id);
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

	public List<InformationType> findByExample(InformationType instance) {
		log.debug("finding InformationType instance by example");
		try {
			List<InformationType> results = (List<InformationType>) sessionFactory.getCurrentSession()
					.createCriteria("py.gov.ande.control.gateway.model.InformationType").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
