package cl.ht.facturacion.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	String db = "";
	String loginUser = "";
	String loginPass = "";
	String url = "";
	
	static Connection conn = null;
	
	public DBConnection(String user, String pass) {
	
		db = "facturacion";
		loginUser = user;
		loginPass = pass;
		url = "jdbc:mysql://localhost:3306/"+db;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, loginUser, loginPass);
			
			if (conn!=null){
	            System.out.println("Conección a base de datos "+db+" OK");
	        }
		 }catch(SQLException e){
	         System.out.println(e);
	      }catch(ClassNotFoundException e){
	         System.out.println(e);
	     }
	}
	
	public Connection getConnection(){
	      return conn;
	}
	
	public void desconectar(){
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
