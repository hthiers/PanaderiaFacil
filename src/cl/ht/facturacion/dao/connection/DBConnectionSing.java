package cl.ht.facturacion.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionSing {
	
	String db = "";
	String loginUser = "";
	String loginPass = "";
	String url = "";
	
	static Connection conn = null;
	
	public DBConnectionSing() {}
	
	public void desconectar(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    conn = null;
	}
	
	public Connection getSingleConnection(String user, String pass) {
		
		db = "facturacion";
		loginUser = user;
		loginPass = pass;
		url = "jdbc:mysql://localhost:3306/"+db;
		
		if(conn == null) {
			try{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url, loginUser, loginPass);
				
				if (conn!=null){
		            System.out.println("Conexión a base de datos "+db+" OK");
		        }
			 }catch(SQLException e){
		         System.out.println(e);
		      }catch(ClassNotFoundException e){
		         System.out.println(e);
		     }
		}
		
		return conn;
	}
	
}