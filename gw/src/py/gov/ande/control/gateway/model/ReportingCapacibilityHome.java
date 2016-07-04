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
 * Home object for domain model class ReportingCapacibility.
 * @see py.gov.ande.control.gateway.model.ReportingCapacibility
 * @author Hibernate Tools
 */
public class ReportingCapacibilityHome {

	private static final Log log = LogFactory.getLog(ReportingCapacibilityHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ReportingCapacibility transientInstance) {
		log.debug("persisting ReportingCapacibility instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ReportingCapacibility instance) {
		log.debug("attaching dirty ReportingCapacibility instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ReportingCapacibility instance) {
		log.debug("attaching clean ReportingCapacibility instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ReportingCapacibility persistentInstance) {
		log.debug("deleting ReportingCapacibility instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ReportingCapacibility merge(ReportingCapacibility detachedInstance) {
		log.debug("merging ReportingCapacibility instance");
		try {
			ReportingCapacibility result = (ReportingCapacibility) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ReportingCapacibility findById(int id) {
		log.debug("getting ReportingCapacibility instance with id: " + id);
		try {
			ReportingCapacibility instance = (ReportingCapacibility) sessionFactory.getCurrentSession()
					.get("py.gov.ande.control.gateway.model.ReportingCapacibility", id);
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

	public List<ReportingCapacibility> findByExample(ReportingCapacibility instance) {
		log.debug("finding ReportingCapacibility instance by example");
		try {
			List<ReportingCapacibility> results = (List<ReportingCapacibility>) sessionFactory.getCurrentSession()
					.createCriteria("py.gov.ande.control.gateway.model.ReportingCapacibility").add(create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
