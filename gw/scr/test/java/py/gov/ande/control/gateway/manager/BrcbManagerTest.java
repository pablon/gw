package py.gov.ande.control.gateway.manager;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.criterion.Order;
import org.junit.Test;

import py.gov.ande.control.gateway.model.IedOperation;
import py.gov.ande.control.gateway.util.GenericManager;

public class BrcbManagerTest {

	@Test
	public void testGetAllReportsIdWithSelectedTags() {
		List<IedOperation> iedList = GenericManager.getAllObjects(IedOperation.class, Order.asc("id"));
		List<Integer> reportsList = BrcbManager.getAllReportsIdWithSelectedTags(iedList.get(0));
		/*for (Integer integer : reportsList) {
			System.out.println("getAllReports bufferId: "+integer);
		}*/
		assertEquals(132, reportsList.get(0).intValue());
		
	}

}
