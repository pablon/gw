package py.gov.ande.control.gateway.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {

	static final String URL = "jdbc:postgresql://localhost:5432/gateway";
	static final String USER = "pnsql";
	static final String PASS = "pnsql1";

	public static Connection crearConexion() throws ClassNotFoundException, SQLException{
	Class.forName("org.postgresql.Driver");
	Connection conexion = DriverManager.getConnection(URL, USER, PASS);
	if (conexion != null){
	System.out.println("Conexion establecida...");
	return conexion;
	}
	return null;
	}

}
