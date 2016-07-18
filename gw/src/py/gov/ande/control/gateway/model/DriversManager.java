package py.gov.ande.control.gateway.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.tree.DefaultMutableTreeNode;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import py.gov.ande.control.gateway.util.DatabaseUtil;
import py.gov.ande.control.gateway.util.GenericManager;

public class DriversManager {
	
    //Session session = null;
    protected Boolean iec61850 = false;
    Boolean substation = false;
	Boolean iec101 = false;
    Boolean ied = false;
    
    public Boolean getSubstation() {
		return substation;
	}

	private void setSubstation(Boolean substation) {
		this.substation = substation;
	}

	public Boolean getIec101() {
		return iec101;
	}

	private void setIec101(Boolean iec101) {
		this.iec101 = iec101;
	}

	public Boolean getIed() {
		return ied;
	}

	private void setIed(Boolean ied) {
		this.ied = ied;
	}


    
	public Boolean getIec61850() {
		return iec61850;
	}

	private void setIec61850(Boolean iec61850) {
		this.iec61850 = iec61850;
	}

	public DriversManager() {
		
		//this.session = DatabaseUtil.getSessionFactory().getCurrentSession();
	}

	public void valueChangedOfTheTree(DefaultMutableTreeNode node) {
		if (node == null){
			System.out.println("nada fue seleccionoda");
			return;
		}
		Object nodeInfo = node.getUserObject();
		//theView.panelConf.scrollPaneDetails.removeAll();
		
		List<Drivers> driverList = GenericManager.getAllObjects(Drivers.class, Order.asc("id"));
		for (Drivers driver : driverList) {
			if(driver.getIec61850() == true){
				if(Objects.equals(driver.getDescription(), nodeInfo)){
					System.out.println("click en 61850");
					this.setIec61850(true);
					break;
				}else{
					this.setIec61850(false);
				}
			}else if(driver.getIec101() == true){
				if(Objects.equals(driver.getDescription(), nodeInfo)){
					System.out.println("click en 101");
					this.setIec101(true);
					break;
				}else{
					this.setIec101(false);
				}
			}
		}
		
	}

	public  DriversManager getValueChangedOfTheTree(){ 
		return this;
	}
}
