package py.gov.ande.control.gateway.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.tree.DefaultMutableTreeNode;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.configuration.TabMappingView;
import py.gov.ande.control.gateway.model.Drivers;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.util.DatabaseUtil;
import py.gov.ande.control.gateway.util.GenericManager;

public class DriversManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DriversManager.class);
	
    //Session session = null;
    protected Boolean iec61850 = false;
    Boolean substation = false;
	Boolean iec101 = false;
    Boolean ied = false;
    Integer iedId = 0;
    String ip = "";
    Integer port = 0;
    String iedName = "";
    int tags = 0;
    int bReports = 0;
    int uReports = 0;
    int arrayId = 0;
    
    
    public int getArrayId() {
		return arrayId;
	}

	protected void setArrayId(int arrayId) {
		this.arrayId = arrayId;
	}

	/**
	 * @return the tags
	 */
	public int getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	private void setTags(int tags) {
		this.tags = tags;
	}

	/**
	 * @return the bReports
	 */
	public int getbReports() {
		return bReports;
	}

	/**
	 * @param bReports the bReports to set
	 */
	private void setbReports(int bReports) {
		this.bReports = bReports;
	}

	/**
	 * @return the uReports
	 */
	public int getuReports() {
		return uReports;
	}

	/**
	 * @param uReports the uReports to set
	 */
	private void setuReports(int uReports) {
		this.uReports = uReports;
	}

	/**
	 * @return the iedName
	 */
	public String getIedName() {
		return iedName;
	}

	/**
	 * @param iedName the iedName to set
	 */
	private void setIedName(String iedName) {
		this.iedName = iedName;
	}

	/**
	 * @return the ip
	 */
	public String getIedIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	private void setIedIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public Integer getIedPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	private void setIedPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the iedId
	 */
	public Integer getIedId() {
		return iedId;
	}

	/**
	 * @param iedId the iedId to set
	 */
	private void setIedId(Integer iedId) {
		this.iedId = iedId;
	}

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

	}

	public void valueChangedOfTheTree(DefaultMutableTreeNode node) {
		if (node == null){
			System.out.println("nada fue seleccionoda");
			return;
		}
		Object nodeInfo = node.getUserObject();
		List<Drivers> driverList = GenericManager.getAllObjects(Drivers.class, Order.asc("id"));
		for (Drivers driver : driverList) {
			if(driver.getIec61850() == true){
				if(Objects.equals(driver.getDescription(), nodeInfo)){
					this.setIec61850(true);
					break;
				}else{
					this.setIec61850(false);
				}
			}else if(driver.getIec101() == true){
				if(Objects.equals(driver.getDescription(), nodeInfo)){
					this.setIec101(true);
					break;
				}else{
					this.setIec101(false);
				}
			}
		}
        /*Se explora lista de ied's */
    	List<Ied> iedList = GenericManager.getAllObjects(Ied.class, Order.asc("id"));
    	int i= 0;
    	for (Ied ieds : iedList) {
    		if(Objects.equals(ieds.getName(), nodeInfo)){
    			logger.info("explorar lista de ied. IedId: "+ieds.getId()+", i: "+i);
    			this.setIed(true);
    			this.setIedId(ieds.getId());
    			this.setIedIp(ieds.getIpAddress());
    			this.setIedPort(ieds.getPortAddress());
    			this.setIedName(ieds.getName());
    			this.setTags(GenericManager.getCountObjets("From TagMonitorIec61850 tag where tag.iedId = "+ieds.getId()));
    			this.setbReports(GenericManager.getCountObjets("From BufferedRcb tag where tag.iedId = "+ieds.getId()));
    			this.setuReports(GenericManager.getCountObjets("From UnbufferedRcb tag where tag.iedId = "+ieds.getId()));
    			this.setArrayId(i);
    			break;
    		}else{
    			this.setIed(false);
    			this.setIedId(0);
    			this.setIedIp("");
    			this.setIedPort(0);
    			this.setIedName("");
    			this.setArrayId(0);
    		}
    		i++;
		}
	}

	/**
	 * Retorna el Drivers o Ied actual seleccionado en el arbol
	 * @return
	 */
	public  DriversManager getValueChangedOfTheTree(){ 
		return this;
	}
}
