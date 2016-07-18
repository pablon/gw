package py.gov.ande.control.gateway.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.configuration.TabConfigurationIec61850Listener;
import py.gov.ande.control.gateway.util.GenericManager;

public class IedManager {
	
	private static final Logger logger = LoggerFactory.getLogger(IedManager.class);

	/**
	 * Método que crea los datos de un nuevo IED
	 * @param ip
	 * @param port
	 */
	public Boolean saveIed(String ip, int port){
		logger.info("metodo saveIed. Address: " + ip + ", port: " + port);
		String name = "Nombre Provisorio";
		
		Integer i;
		
		i = GenericManager.getCountObjets("From Ied"); //hasta saber porque no funciona el auto incremental
		
		Ied ied = new Ied();
		//ied.setId(++i);
		ied.setIpAddress(ip);
		ied.setPortAddress(port);
		ied.setName(name);

		try {
			GenericManager.saveObject(ied);
			logger.info("luego del metodo generic manager Save()");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error, luego del metodo generic manager Save()");
			return false;
		}
	}
}
