package py.gov.ande.control.gateway.model;

import org.hibernate.Session;

import py.gov.ande.control.gateway.util.DatabaseUtil;

public class DriversManager {
	
    Session session = null;

	public DriversManager() {
		
		this.session = DatabaseUtil.getSessionFactory().getCurrentSession();
	}

}
