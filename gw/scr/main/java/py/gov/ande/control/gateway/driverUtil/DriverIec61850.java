package py.gov.ande.control.gateway.driverUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.openmuc.openiec61850.Brcb;
import org.openmuc.openiec61850.ClientAssociation;
import org.openmuc.openiec61850.ClientEventListener;
import org.openmuc.openiec61850.ClientSap;
import org.openmuc.openiec61850.ServerModel;
import org.openmuc.openiec61850.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.util.TestConnections;

public abstract class DriverIec61850 implements ClientEventListener{
	
	private static final Logger logger = LoggerFactory.getLogger(DriverIec61850.class);
    ClientSap clientSap = new ClientSap();
    InetAddress address;
    String iedIp = null;
    int remotePort = 102;
    int setTSelLocal = 0;
    int setTSelRemote = 0;
    //DriverIec61850 eventHandler;
    private ServerModel serverModel = null;
    private ClientAssociation association;
	private Brcb brcb;
	private boolean enableReport = false;
    
    protected boolean isEnableReport() {
		return enableReport;
	}

	public void setEnableReport(boolean enableReport) {
		this.enableReport = enableReport;
	}

	public void setDriverIec61850Parameters(String iedIp, int remotePort, int setTSelLocal, int setTSelRemote){
    	this.iedIp = iedIp;
    	this.remotePort = remotePort;
    	this.setTSelLocal = setTSelLocal;
    	this.setTSelRemote = setTSelRemote;
    }
    
    public void checkIed() throws IOException{
    	address = InetAddress.getByName(iedIp);
    	TestConnections.testConnection2(address);
    }
    
    public void doAssociate(BrcbRunnable eventHandler) throws IOException, ServiceError{
    	association = clientSap.associate(address, remotePort, null, eventHandler);
    	serverModel = association.retrieveModel();
    	association.getAllDataValues();
    }
    
    public ServerModel getServerModel() {
		return serverModel;
    }
    
    public void disconnectToIed(){
    	logger.info("inicio");
    	association.disconnect();
    }
    
    public void brcb(String string) throws ServiceError, IOException{
    	brcb = serverModel.getBrcb(string);
    	association.getRcbValues(brcb);
    }
    		
    public void optFlds(){
        brcb.getTrgOps().setIntegrity(false);					//eliminar integridad pera que llegue solo por DCHG o GI
        //brcb.getIntgPd().setValue(0); 
        brcb.getTrgOps().setGeneralInterrogation(false);		//elimina la interrogacion general
        brcb.getOptFlds().setBufferOverflow(true);
        brcb.getOptFlds().setConfigRevision(true);
        brcb.getOptFlds().setDataReference(true);
        brcb.getOptFlds().setDataSetName(true);
        brcb.getOptFlds().setEntryId(true);
        brcb.getOptFlds().setReasonForInclusion(true);
        brcb.getOptFlds().setReportTimestamp(true);
        brcb.getOptFlds().setSegmentation(true);
        brcb.getOptFlds().setSequenceNumber(true);
    }
    
    public void setRcbValues() throws IOException, ServiceError{
        //association.setRcbValues(rcb, setRptId, setDatSet, setOptFlds, setBufTm, setTrgOps, setIntgPd, setPurgeBuf, setEntryId)
        association.setRcbValues(brcb, true,    true,     true,       true,     true,      true, 	   true,       true);
        association.getRcbValues(brcb);
    }
    
    public void enableReporting() throws ServiceError, IOException{
    	association.enableReporting(brcb);
    }
    
    public void enableGi() throws ServiceError, IOException{
    	association.startGi(brcb);
    }
    
    public void enableThread() throws InterruptedException, ServiceError, IOException {
    	logger.info("inicio");
    	while(enableReport){
				Thread.sleep(20000);
    	}
    	disableReporting();
    	disconnectToIed();
    	logger.info("fin del Thread");
    }
    
    public void disableReporting() throws ServiceError, IOException{
    	logger.info("inicio");
    	association.disableReporting(brcb);
    }
}
