package py.gov.ande.control.gateway.manager;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.omg.CORBA.portable.ValueOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.configuration.TabConfigurationIec61850Listener;
import py.gov.ande.control.gateway.model.BufferedRcb;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.TagMonitorIec61850;
import py.gov.ande.control.gateway.model.UnbufferedRcb;
import py.gov.ande.control.gateway.util.GenericManager;
import py.gov.ande.control.gateway.util.GenericValidations;

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
	 * @param int iedId
	 * @return boolean
	 * @author Pablo
	 * @date 2016-08-07
	 * Pendiente: implementar commit y rollback
	 */
	public static Boolean deleteIed(int iedId) {
		Session session = GenericManager.createNewSession();
		Ied ied = GenericManager.getObjectById(Ied.class, iedId);

		try {
			GenericManager.BorrarTodosLosHijos(TagMonitorIec61850.class, ied);
			GenericManager.BorrarTodosLosHijos(BufferedRcb.class, ied);
			GenericManager.BorrarTodosLosHijos(UnbufferedRcb.class, ied);
			GenericManager.deleteObjectById(Ied.class, iedId);
			return true;
		} catch (Exception e) {
			return false;
		}

		/*try {
			session.getTransaction().begin();
			logger.info("delete tag_monitor_iec61850");
			session.createQuery(
					"delete tag From TagMonitorIec61850 as tag inner join tag.ied as ied" +
					"where ied.id = :id" )
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
		}*/
	}

	/**
	 * Método que valida la ip, puerto y nombre del ied
	 * @param iedIp
	 * @param iedName
	 * @param iedPort
	 * @return boolean
	 */
	public static boolean validateWidget(String iedIp, String iedName, String iedPort) {
		logger.info("inicio");
		boolean b1 = GenericValidations.validateIpAddress(iedIp);
		//boolean b2 = !(IedManager.findIedForIpAddress(iedIp));
		boolean b3 = GenericValidations.validatePortAddress(iedPort);
		boolean b4 = GenericValidations.validateString(iedName);
		
		if(b1 && b3 && b4){
			logger.info("validado");
			return true;
		}else{
			logger.error("invalido. "+b1+", "+b3+", "+b4);
			return false;
		}
	}

	/**
	 * Método que actualiza los datos del Ied
	 * @param iedIp
	 * @param iedName
	 * @param iedPort
	 * @param iedId
	 * @return
	 */
	public static boolean updateIed(String iedIp, String iedName, String iedPort, int iedId) {
		logger.info("String iedIp: "+iedIp+", String iedName: "+iedName+", String iedPort: "+iedPort);
		Ied ied = GenericManager.getObjectById(Ied.class, iedId);
		ied.setIpAddress(iedIp);
		ied.setPortAddress(Integer.valueOf(iedPort));
		ied.setName(iedName);

		try {
			GenericManager.updateObject(ied);
			//logger.info("luego del metodo generic manager Save()");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error, luego del metodo generic manager Save()");
			return false;
		}
		
	}
	
	/**
	 * Método que retorna una lista de Ied
	 * @return List<Ied>
	 */
	public static List<Ied> getAllObjects(){
		List<Ied> iedList = GenericManager.getAllObjects(Ied.class, Order.asc("id"));
		return iedList;
	}
}
