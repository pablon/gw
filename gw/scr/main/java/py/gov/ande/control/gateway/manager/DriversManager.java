package py.gov.ande.control.gateway.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.tree.DefaultMutableTreeNode;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.configuration.TabMappingView;
import py.gov.ande.control.gateway.model.Drivers;
import py.gov.ande.control.gateway.model.DriversOperation;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.IedOperation;
import py.gov.ande.control.gateway.util.DatabaseOperationResult;
import py.gov.ande.control.gateway.util.DatabaseUtil;
import py.gov.ande.control.gateway.util.GenericManager;

public class DriversManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DriversManager.class);
	
    //Session session = null;
    protected boolean iec61850 = false;
    boolean substation = false;
    boolean iec101 = false;
    boolean ied = false;
    boolean gw = false;
    Integer iedId = 0;
    String ip = "";
    Integer port = 0;
    String iedName = "";
    int tags = 0;
    int bReports = 0;
    int uReports = 0;
    int arrayId = 0;
    
    
    public boolean getGw() {
		return gw;
	}

	protected void setGw(boolean gw) {
		this.gw = gw;
	}

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

	public <T> void valueChangedOfTheTree(DefaultMutableTreeNode node) {
		if (node == null){
			System.out.println("nada fue seleccionoda");
			return;
		}
		Object nodeInfo = node.getUserObject();
		if(Objects.equals(nodeInfo, "Gateway")){
			this.setGw(true);
		}else{
			this.setGw(false);
		}
		//item.getClass().getMethod(metodo).invoke(item);
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
    			//logger.info("explorar lista de ied. IedId: "+ieds.getId()+", i: "+i);
    			this.setIed(true);
    			this.setIedId(ieds.getId());
    			this.setIedIp(ieds.getIpAddress());
    			this.setIedPort(ieds.getPortAddress());
    			this.setIedName(ieds.getName());
    			this.setTags(GenericManager.getCountObjets("from TagMonitorIec61850 as tag inner join tag.ied as ied where ied.id ="+ieds.getId()));
    			this.setbReports(GenericManager.getCountObjets("From BufferedRcb as tag inner join tag.ied as ied where ied.id = "+ieds.getId()));
    			this.setuReports(GenericManager.getCountObjets("From UnbufferedRcb as tag inner join tag.ied as ied where ied.id = "+ieds.getId()));
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
	 * Método utilizado para recibir eventos de click sobre el arbol de drivers de operations
	 * @param node
	 */
	public <T> void valueChangedOfTheTreeOperation(DefaultMutableTreeNode node) {
		if (node == null){
			System.out.println("nada fue seleccionoda");
			return;
		}
		Object nodeInfo = node.getUserObject();
		if(Objects.equals(nodeInfo, "Gateway")){
			this.setGw(true);
		}else{
			this.setGw(false);
		}
		//item.getClass().getMethod(metodo).invoke(item);
		List<DriversOperation> driverList = GenericManager.getAllObjects(DriversOperation.class, Order.asc("id"));
		for (DriversOperation driver : driverList) {
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
    	List<IedOperation> iedList = GenericManager.getAllObjects(IedOperation.class, Order.asc("id"));
    	int i= 0;
    	for (IedOperation ieds : iedList) {
    		if(Objects.equals(ieds.getName(), nodeInfo)){
    			//logger.info("explorar lista de ied. IedId: "+ieds.getId()+", i: "+i);
    			this.setIed(true);
    			this.setIedId(ieds.getId());
    			this.setIedIp(ieds.getIpAddress());
    			this.setIedPort(ieds.getPortAddress());
    			this.setIedName(ieds.getName());
    			this.setTags(GenericManager.getCountObjets("from TagMonitorIec61850Operation as tag inner join tag.iedOperation as ied where ied.id ="+ieds.getId()));
    			this.setbReports(GenericManager.getCountObjets("From BufferedRcbOperation as tag inner join tag.iedOperation as ied where ied.id = "+ieds.getId()));
    			this.setuReports(GenericManager.getCountObjets("From UnbufferedRcbOperation as tag inner join tag.iedOperation as ied where ied.id = "+ieds.getId()));
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
	
	/**
	 * Método que construye un arbol de drivers con la lista de IED
	 * @param nodeRoot
	 * @return DefaultMutableTreeNode
	 * @author Pablo
	 * @date 2016-08-08
	 */
	public static DefaultMutableTreeNode buildTree(DefaultMutableTreeNode nodeRoot){
        DefaultMutableTreeNode subestacion = null, iec61850 = null, ied = null, iec101, temp;
        if(nodeRoot.getChildCount()>0){
        	nodeRoot.removeAllChildren();
        }
        
        List<Drivers> driverList = GenericManager.getListBasedOnCriteria("From Drivers drivers ORDER BY "
        		+ "drivers.subestation DESC,"
        		+ "drivers.iec61850 DESC,"
        		+ "drivers.iec101 DESC");

        for (Drivers drivers : driverList) {
        	if(drivers.getSubestation()){
        		subestacion = new DefaultMutableTreeNode(drivers.getDescription());
        		nodeRoot.add(subestacion);
        	}else if(drivers.getIec61850()){
        		iec61850 = new DefaultMutableTreeNode(drivers.getDescription());
        		subestacion.add(iec61850);
        	}else if(drivers.getIec101()){
        		iec101 = new DefaultMutableTreeNode(drivers.getDescription());
        		subestacion.add(iec101);
        	}else{
        		temp = new DefaultMutableTreeNode(drivers.getDescription());
        		subestacion.add(temp);
        	} 
		}
    
    	List<Ied> iedList = GenericManager.getAllObjects(Ied.class, Order.asc("id"));
    	for (Ied ieds : iedList) {
    		ied = new DefaultMutableTreeNode(ieds.getName());
    		iec61850.add(ied);
		}		

		return nodeRoot;
		
	}
	
	public static DefaultMutableTreeNode buildTreeOperation(DefaultMutableTreeNode nodeRoot){
        DefaultMutableTreeNode subestacion = null, iec61850 = null, ied = null, iec101, temp;
        if(nodeRoot.getChildCount()>0){
        	nodeRoot.removeAllChildren();
        }
        
        List<DriversOperation> driverList = GenericManager.getListBasedOnCriteria("From DriversOperation drivers ORDER BY "
        		+ "drivers.subestation DESC,"
        		+ "drivers.iec61850 DESC,"
        		+ "drivers.iec101 DESC");

        for (DriversOperation drivers : driverList) {
        	if(drivers.getSubestation()){
        		subestacion = new DefaultMutableTreeNode(drivers.getDescription());
        		nodeRoot.add(subestacion);
        	}else if(drivers.getIec61850()){
        		iec61850 = new DefaultMutableTreeNode(drivers.getDescription());
        		subestacion.add(iec61850);
        	}else if(drivers.getIec101()){
        		iec101 = new DefaultMutableTreeNode(drivers.getDescription());
        		subestacion.add(iec101);
        	}else{
        		temp = new DefaultMutableTreeNode(drivers.getDescription());
        		subestacion.add(temp);
        	} 
		}
    
    	List<IedOperation> iedList = GenericManager.getAllObjects(IedOperation.class, Order.asc("id"));
    	for (IedOperation ieds : iedList) {
    		ied = new DefaultMutableTreeNode(ieds.getName());
    		iec61850.add(ied);
		}		

		return nodeRoot;
		
	}

	/**
	 * Método que copia los datos de las tablas de configuración a las tablas de operación.
	 * De modo a que se pueda configurar sin afectar a la operación.
	 * Previamente realiza un borrado de todas las tablas relacionadas.
	 * tablas: drivers, ied, buffered_rcb, unbuffered_rcb, tag_monitor_iec61850
	 * @author Pablo
	 * @date 2016-08-09
	 * @return DatabaseOperationResult
	 */
	public static DatabaseOperationResult rebuildDBOperation() {
		logger.info("inicio");
        DatabaseOperationResult.ErrorType errorType = null;
        int recordsAffected = 0;
        RuntimeException exception = null;
        String criterioIed = "insert into IedOperation (id, name, bufferTime, portAddress,connectionTest,ipAddress, createdAt, updateAt) select ied.id, ied.name, ied.bufferTime, ied.portAddress, ied.connectionTest, ied.ipAddress, ied.createdAt, ied.updateAt from Ied ied";
        String criterioTags = "insert into tag_monitor_iec61850_operation (id, use, telegram_address, ied_id, brcb_id, information_type_id, normalization_id, reporting_capacibiliy_id, urcb_id, name, buffered, unbuffered) select tag.id, tag.use, tag.telegram_address, ied_id, brcb_id, information_type_id, normalization_id, reporting_capacibiliy_id, urcb_id, name, buffered, unbuffered from tag_monitor_iec61850 tag where tag.use = true";
        String criterioDrivers = "insert into DriversOperation (id, description, observation, iec61850, iec101, ied, subestation) "
				+ "select d.id, d.description, d.observation, d.iec61850, d.iec101, d.ied, d.subestation"
				+ " from Drivers d";
        String criterioBrcb = "insert into buffered_rcb_operation (id, reference, dataset, ied_id) select brcb.id, brcb.reference, brcb.dataset, brcb.ied_id from buffered_rcb brcb";
		String criterioUrcb = "insert into unbuffered_rcb_operation (id, referent, dataset, ied_id) select urcb.id, urcb.referent, urcb.dataset, urcb.ied_id from unbuffered_rcb urcb";

		Session session = GenericManager.createNewSession();
		try {
			//logger.info("dentro del try. inicio de transacciones");
			session.getTransaction().begin();
			//session.createSQLQuery("TRUNCATE TABLE tag_monitor_iec61850_operation, drivers_operation, ied_operation, buffered_rcb_operation, unbuffered_rcb_operation").executeUpdate();
			session.createQuery("delete from TagMonitorIec61850Operation").executeUpdate();
			session.createQuery("delete from DriversOperation").executeUpdate();
			session.createQuery("delete from BufferedRcbOperation").executeUpdate();
			session.createQuery("delete from UnbufferedRcbOperation").executeUpdate();
			session.createQuery("delete from IedOperation").executeUpdate();
			recordsAffected = recordsAffected + session.createQuery(criterioIed).executeUpdate();
			recordsAffected = recordsAffected + session.createSQLQuery(criterioBrcb).executeUpdate();			
			recordsAffected = recordsAffected + session.createSQLQuery(criterioUrcb).executeUpdate();
			recordsAffected = recordsAffected + session.createQuery(criterioDrivers).executeUpdate();			
			recordsAffected = recordsAffected + session.createSQLQuery(criterioTags).executeUpdate();
			//logger.info("fin de transacciones");
			session.getTransaction().commit();
			logger.info("luego del commit");
		} catch (RuntimeException e) {
			if (e instanceof ConstraintViolationException) {
				errorType = DatabaseOperationResult.ErrorType.CONSTRAINT_VIOLATION;
			} else {
				errorType = DatabaseOperationResult.ErrorType.OTHER;
			}
			if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE
			  || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK ) {
							session.getTransaction().rollback();
			}
			exception = e;
			logger.error(e.toString());
		} catch (Exception e){
			logger.error("se encontró un error: "+e);
		} 
		finally {
			session.close();
		}
		return new DatabaseOperationResult(errorType, recordsAffected, exception);
	}
}
