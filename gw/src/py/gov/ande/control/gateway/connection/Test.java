package py.gov.ande.control.gateway.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {

	public static void main(String[] args) {
		try {
			Connection con = Connections.crearConexion();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM drivers");
			
			while (rs.next())
			{
			   System.out.print("Column 1 returned ");
			   System.out.println(rs.getString(2));
			} rs.close();
			st.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
