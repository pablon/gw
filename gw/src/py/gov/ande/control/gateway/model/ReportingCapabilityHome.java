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
 * Home object for domain model class ReportingCapability.
 * @see py.gov.ande.control.gateway.model.ReportingCapability
 * @author Hibernate Tools
 */
public class ReportingCapabilityHome {

	private static final Log log = LogFactory.getLog(ReportingCapabilityHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ReportingCapability transientInstance) {
		log.debug("persisting ReportingCapability instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ReportingCapability instance) {
		log.debug("attaching dirty ReportingCapability instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ReportingCapability instance) {
		log.debug("attaching clean ReportingCapability instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ReportingCapability persistentInstance) {
		log.debug("deleting ReportingCapability instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ReportingCapability merge(ReportingCapability detachedInstance) {
		log.debug("merging ReportingCapability instance");
		try {
			ReportingCapability result = (ReportingCapability) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ReportingCapability findById(int id) {
		log.debug("getting ReportingCapability instance with id: " + id);
		try {
			ReportingCapability instance = (ReportingCapability) sessionFactory.getCurrentSession()
					.get("py.gov.ande.control.gateway.model.ReportingCapability", id);
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

	public List<ReportingCapability> findByExample(ReportingCapability instance) {
		log.debug("finding ReportingCapability instance by example");
		try {
			List<ReportingCapability> results = (List<ReportingCapability>) sessionFactory.getCurrentSession()
					.createCriteria("py.gov.ande.control.gateway.model.ReportingCapability").add(create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
