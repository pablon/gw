package py.gov.ande.control.gateway.manager;

import org.hibernate.Session;
import org.hibernate.Transaction;
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
		String name = "Nombre Provisorio";
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
}
