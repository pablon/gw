package py.gov.ande.control.gateway.driverUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrcbRunnable implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(BrcbRunnable.class);

	public BrcbRunnable(Integer bReportId) {
		// TODO Auto-generated constructor stub
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

}
