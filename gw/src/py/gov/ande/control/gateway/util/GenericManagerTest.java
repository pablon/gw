package py.gov.ande.control.gateway.util;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.criterion.Order;
import org.junit.Test;

import py.gov.ande.control.gateway.manager.TagMonitorIec61850Manager;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.TagMonitorIec61850;

public class GenericManagerTest {

	@Test
	public void testGetColumnNames() {
		List<Ied> iedList = GenericManager.getAllObjects(Ied.class, Order.asc("id"));
		System.out.println("ied name: "+iedList.get(0).getName());
		
		List<TagMonitorIec61850> tags = TagMonitorIec61850Manager.getAllObjects(iedList.get(0));
		System.out.println("tags.get(0): "+tags.get(0).getTelegramAddress());
		
		System.out.println("Columns: "+GenericManager.getColumnNames(tags.get(0).getClass())[0]);
		
		for (String columName : GenericManager.getColumnNames(tags.get(0).getClass())) {
			System.out.println("columna: "+columName);
		}
		
		assertEquals("use", GenericManager.getColumnNames(tags.get(0).getClass())[0]);
	}

}
