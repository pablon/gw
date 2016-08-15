package py.gov.ande.control.gateway.driverUtil;

import java.io.IOException;
import java.util.List;

import org.openmuc.openiec61850.Report;
import org.openmuc.openiec61850.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.model.IedOperation;
import py.gov.ande.control.gateway.model.TagMonitorIec61850Operation;

public class PoolingRunnable extends DriverIec61850 implements Runnable{
	
	private static final Logger logger = LoggerFactory.getLogger(PoolingRunnable.class);

	public PoolingRunnable(IedOperation ied, List<TagMonitorIec61850Operation> tagsWithOutAnyReport) throws IOException, ServiceError {
		setDriverIec61850Parameters(ied.getIpAddress(), ied.getPortAddress(), 0, 0);
		checkIed();
		//doAssociate();
		disconnectToIed();
		
	}

	@Override
	public void run() {
		logger.info("inicio Run");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("error");
			e.printStackTrace();
		}
		logger.info("fin");
		
	}

	@Override
	public void newReport(Report report) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void associationClosed(IOException e) {
		// TODO Auto-generated method stub
		
	}

}
