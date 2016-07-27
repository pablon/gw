package py.gov.ande.control.gateway.manager;

import java.net.InetAddress;
import java.util.Arrays;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.configuration.TabConfigurationIec61850Listener;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.util.GenericManager;

public class IedManager {
	
	private static final Logger logger = LoggerFactory.getLogger(IedManager.class);
	private Ied ied;
	
	/**
	 * Método que crea los datos de un nuevo IED.
	 * pendiente buffer_time, connection_test, name
	 * @param ip
	 * @param port
	 */
	public Ied saveIed(String ip, int port){
		logger.info("metodo saveIed. Address: " + ip + ", port: " + port);
		String name = "Nombre Provisorio-"+ip;
		ied = new Ied();
		ied.setIpAddress(ip);
		ied.setPortAddress(port);
		ied.setName(name);

		try {
			GenericManager.saveObject(ied);
			//logger.info("luego del metodo generic manager Save()");
			return ied;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error, luego del metodo generic manager Save()");
			return null;
		}
	}

	/**
	 * Método que busca un ied en la BD en base a la ip.
	 * Retorna true si lo encuentra
	 * @param String address
	 * @return Boolean
	 * @author Pablo
	 * @date 2016-07-26
	 */
	public static boolean findIedForIpAddress(String address) {
		Ied ied = (Ied) GenericManager.getFilteredObject(Ied.class, 
    			Arrays.asList(
    					Restrictions.eq("ipAddress", address)
				));		
		if(ied != null){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Método que elimina todos los datos relacionados al ied, como ser brcb, urcb, tags, y el propio Ied
	 * @param iedId
	 * @return
	 */
	public static Boolean deleteIed(int iedId) {
		Session session = GenericManager.createNewSession();
		
		try {
			session.getTransaction().begin();
			logger.info("delete tag_monitor_iec61850");
			session.createQuery(
					"delete TagMonitorIec61850 " +
					"where iedId = :id" )
					.setParameter( "id", iedId )
					.executeUpdate();
			logger.info("delete buffered_rcb");
			session.createQuery(
					"delete BufferedRcb " +
					"where iedId = :id" )
					.setParameter( "id", iedId )
					.executeUpdate();
			logger.info("delete UnbufferedRcb");
			session.createQuery(
					"delete UnbufferedRcb " +
					"where iedId = :id" )
					.setParameter( "id", iedId )
					.executeUpdate();
			logger.info("delete Ied");
			session.createQuery(
					"delete Ied " +
					"where id = :id" )
					.setParameter( "id", iedId )
					.executeUpdate();
			
			session.getTransaction().commit();
			logger.info("luego del commit");
			return true;
		} catch (Exception e) {
			if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE 
					|| session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK ) {
				session.getTransaction().rollback();
				}
			logger.error("luego del rollback");
			return false;
		}
	}
}
