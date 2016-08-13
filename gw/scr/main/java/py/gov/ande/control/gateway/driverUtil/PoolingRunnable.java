package py.gov.ande.control.gateway.driverUtil;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.model.TagMonitorIec61850Operation;

public class PoolingRunnable extends DriverIec61850 implements Runnable{
	
	private static final Logger logger = LoggerFactory.getLogger(PoolingRunnable.class);

	public PoolingRunnable(List<TagMonitorIec61850Operation> tagsWithOutAnyReport) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		logger.info("inicio Run");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("error");
			e.printStackTrace();
		}
		logger.info("fin");
		
	}

}
