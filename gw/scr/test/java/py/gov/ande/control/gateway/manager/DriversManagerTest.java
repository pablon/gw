package py.gov.ande.control.gateway.manager;

import static org.junit.Assert.*;



import org.junit.Test;

import py.gov.ande.control.gateway.util.DatabaseOperationResult;
import py.gov.ande.control.gateway.util.DatabaseOperationResult.ErrorType;

public class DriversManagerTest {

	@Test
	public void testRebuildDriversOperation() {
		DatabaseOperationResult result = DriversManager.rebuildDBOperation();
		assertEquals(null, result.getErrorType());
	}

}
