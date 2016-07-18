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
 * Home object for domain model class TagCommandIec61850.
 * @see py.gov.ande.control.gateway.model.TagCommandIec61850
 * @author Hibernate Tools
 */
public class TagCommandIec61850Home {

	private static final Log log = LogFactory.getLog(TagCommandIec61850Home.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(TagCommandIec61850 transientInstance) {
		log.debug("persisting TagCommandIec61850 instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TagCommandIec61850 instance) {
		log.debug("attaching dirty TagCommandIec61850 instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TagCommandIec61850 instance) {
		log.debug("attaching clean TagCommandIec61850 instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TagCommandIec61850 persistentInstance) {
		log.debug("deleting TagCommandIec61850 instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TagCommandIec61850 merge(TagCommandIec61850 detachedInstance) {
		log.debug("merging TagCommandIec61850 instance");
		try {
			TagCommandIec61850 result = (TagCommandIec61850) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TagCommandIec61850 findById(int id) {
		log.debug("getting TagCommandIec61850 instance with id: " + id);
		try {
			TagCommandIec61850 instance = (TagCommandIec61850) sessionFactory.getCurrentSession()
					.get("py.gov.ande.control.gateway.model.TagCommandIec61850", id);
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

	public List<TagCommandIec61850> findByExample(TagCommandIec61850 instance) {
		log.debug("finding TagCommandIec61850 instance by example");
		try {
			List<TagCommandIec61850> results = (List<TagCommandIec61850>) sessionFactory.getCurrentSession()
					.createCriteria("py.gov.ande.control.gateway.model.TagCommandIec61850").add(create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
