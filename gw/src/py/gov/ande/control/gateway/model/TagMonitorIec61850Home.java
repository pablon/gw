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
 * Home object for domain model class TagMonitorIec61850.
 * @see py.gov.ande.control.gateway.model.TagMonitorIec61850
 * @author Hibernate Tools
 */
public class TagMonitorIec61850Home {

	private static final Log log = LogFactory.getLog(TagMonitorIec61850Home.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(TagMonitorIec61850 transientInstance) {
		log.debug("persisting TagMonitorIec61850 instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TagMonitorIec61850 instance) {
		log.debug("attaching dirty TagMonitorIec61850 instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TagMonitorIec61850 instance) {
		log.debug("attaching clean TagMonitorIec61850 instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TagMonitorIec61850 persistentInstance) {
		log.debug("deleting TagMonitorIec61850 instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TagMonitorIec61850 merge(TagMonitorIec61850 detachedInstance) {
		log.debug("merging TagMonitorIec61850 instance");
		try {
			TagMonitorIec61850 result = (TagMonitorIec61850) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TagMonitorIec61850 findById(int id) {
		log.debug("getting TagMonitorIec61850 instance with id: " + id);
		try {
			TagMonitorIec61850 instance = (TagMonitorIec61850) sessionFactory.getCurrentSession()
					.get("py.gov.ande.control.gateway.model.TagMonitorIec61850", id);
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

	public List<TagMonitorIec61850> findByExample(TagMonitorIec61850 instance) {
		log.debug("finding TagMonitorIec61850 instance by example");
		try {
			List<TagMonitorIec61850> results = (List<TagMonitorIec61850>) sessionFactory.getCurrentSession()
					.createCriteria("py.gov.ande.control.gateway.model.TagMonitorIec61850").add(create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
