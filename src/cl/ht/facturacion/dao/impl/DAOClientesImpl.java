package cl.ht.facturacion.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cl.ht.facturacion.client.vo.VOCliente;
import cl.ht.facturacion.dao.DAOClientes;
import cl.ht.facturacion.dao.connection.DBConnection;

public class DAOClientesImpl implements DAOClientes {

	@Override
	public ArrayList<VOCliente> getAllClientes(boolean activados) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		PreparedStatement pstm = null;
		ResultSet res = null;
		
		ArrayList<VOCliente> listaClientes = new ArrayList<VOCliente>();
		VOCliente cliente;
		String sql = "";
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			
			if(activados)
				sql = "SELECT * FROM cliente cli WHERE cli.estid = 1 ORDER BY cli.clirut";
			else
				sql = "SELECT * FROM cliente cli ORDER BY cli.clirut";
			 
			pstm = con.getConnection().prepareStatement(sql);
			res = pstm.executeQuery();
			
			while(res.next()) {
				cliente = new VOCliente();
				cliente.setId(""+res.getInt(1));
				cliente.setRut(res.getString(2));
				cliente.setNombres(res.getString(3));
				cliente.setApellidos(res.getString(4));
				cliente.setCiudad(res.getString(5));
				cliente.setComuna(res.getString(6));
				cliente.setDireccion(res.getString(7));
				cliente.setGiro(res.getString(8));
				cliente.setEstado(res.getInt(9));
				
				listaClientes.add(cliente);
			}
			
			System.out.println("encontrados: "+listaClientes.size());
			
			return listaClientes;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} finally {
	        if (res != null) try { res.close(); } catch (SQLException logOrIgnore) {}
	        if (stm != null) try { stm.close(); } catch (SQLException logOrIgnore) {}
	        if (pstm != null) try { pstm.close(); } catch (SQLException logOrIgnore) {}
	        if (con != null) try { con.getConnection().close(); } catch (SQLException logOrIgnore) {}
	    }
		
		return null;
	}

	@Override
	public VOCliente getClienteByRut(String rut) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM cliente cli"+
			" WHERE cli.clirut = '"+rut+"' ";
			res = stm.executeQuery(sql);
			
			while(res.next()) {
				VOCliente cliente = new VOCliente();
				cliente.setId(res.getString(1));
				cliente.setRut(res.getString(2));
				cliente.setNombres(res.getString(3));
				cliente.setApellidos(res.getString(4));
				cliente.setCiudad(res.getString(5));
				cliente.setComuna(res.getString(6));
				cliente.setDireccion(res.getString(7));
				cliente.setGiro(res.getString(8));
				cliente.setEstado(res.getInt(9));
				
				return cliente;
			}
			
			res.last();
			if(res.getRow() < 1)
				System.out.println("no se han encontrado clientes");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} finally {
	        if (res != null) try { res.close(); } catch (SQLException logOrIgnore) {}
	        if (stm != null) try { stm.close(); } catch (SQLException logOrIgnore) {}
	        if (con != null) try { con.getConnection().close(); } catch (SQLException logOrIgnore) {}
	    }
		
		return null;
	}

	@Override
	public VOCliente getClienteByName(String name) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM cliente cli"+
			" WHERE cli.clinombre = '"+name+"' ";
			res = stm.executeQuery(sql);
			
			while(res.next()) {
				VOCliente cliente = new VOCliente();
				cliente.setId(res.getString(1));
				cliente.setRut(res.getString(2));
				cliente.setNombres(res.getString(3));
				cliente.setApellidos(res.getString(4));
				cliente.setCiudad(res.getString(5));
				cliente.setComuna(res.getString(6));
				cliente.setDireccion(res.getString(7));
				cliente.setGiro(res.getString(8));
				cliente.setEstado(res.getInt(9));
				
				return cliente;
			}
			
			res.last();
			if(res.getRow() < 1)
				System.out.println("no se han encontrado clientes");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} finally {
	        if (res != null) try { res.close(); } catch (SQLException logOrIgnore) {}
	        if (stm != null) try { stm.close(); } catch (SQLException logOrIgnore) {}
	        if (con != null) try { con.getConnection().close(); } catch (SQLException logOrIgnore) {}
	    }
		
		return null;
	}

	@Override
	public void newCliente(String rut, String name, String lastname,
			String ciudad, String comuna, String direccion, String giro, boolean activated) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		PreparedStatement pstm = null;
		
		 try {
			 con = new DBConnection("root","walkirias84");
			 pstm = con.getConnection().prepareStatement(
					"INSERT INTO cliente"+
					" (clirut,clinombre,cliapellido,cliciudad,clicomuna,clidireccion,cligiro,estid)"+
					" VALUES (?,?,?,?,?,?,?,?)");
			pstm.setString(1, rut);
			pstm.setString(2, name);
			pstm.setString(3, lastname);
			if(!ciudad.equals("") || !ciudad.isEmpty())
				pstm.setString(4, ciudad);
			else
				pstm.setString(4, "temuco");
			if(!comuna.equals("") || !comuna.isEmpty())
				pstm.setString(5, comuna);
			else
				pstm.setString(5, "temuco");
			pstm.setString(6, direccion);
			pstm.setString(7, giro);
			if(activated == true)
				pstm.setInt(8, 1);
			else
				pstm.setInt(8, 2);
			
			int result = pstm.executeUpdate();
			System.out.println("result: "+result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} finally {
	        if (pstm != null) try { pstm.close(); } catch (SQLException logOrIgnore) {}
	        if (con != null) try { con.getConnection().close(); } catch (SQLException logOrIgnore) {}
	    }
	}

	@Override
	public void updateCliente(int id, String rut, String name, String lastname,
			String ciudad, String comuna, String direccion, String giro, boolean activated) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		PreparedStatement pstm = null;
		
		int estado = 2;
		if(activated)
			estado = 1;
		
		 try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"UPDATE cliente"+
					" SET clirut='"+rut+"',clinombre='"+name+"',cliapellido='"+lastname+"',cliciudad='"+ciudad+"',clicomuna='"+comuna+"',clidireccion='"+direccion+"',cligiro='"+giro+"',estid="+estado+""+
					" WHERE cliid= "+id+" ");
			
			int result = pstm.executeUpdate();
			System.out.println("result: "+result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} finally {
	        if (pstm != null) try { pstm.close(); } catch (SQLException logOrIgnore) {}
	        if (con != null) try { con.getConnection().close(); } catch (SQLException logOrIgnore) {}
	    }
	}

	@Override
	public void changeStatusCliente(String rut, boolean activated) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newStatus(String nombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStatus(String nombre, String newNombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStatus(String nombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public VOCliente getClienteByID(int id) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM cliente cli"+
			" WHERE cli.cliid = "+id+" ";
			res = stm.executeQuery(sql);
			
			while(res.next()) {
				VOCliente cliente = new VOCliente();
				cliente.setId(res.getString(1));
				cliente.setRut(res.getString(2));
				cliente.setNombres(res.getString(3));
				cliente.setApellidos(res.getString(4));
				cliente.setCiudad(res.getString(5));
				cliente.setComuna(res.getString(6));
				cliente.setDireccion(res.getString(7));
				cliente.setGiro(res.getString(8));
				cliente.setEstado(res.getInt(9));
				
				return cliente;
			}
			
			if(!res.last())
				System.out.println("no se han encontrado clientes");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} finally {
	        if (res != null) try { res.close(); } catch (SQLException logOrIgnore) {}
	        if (stm != null) try { stm.close(); } catch (SQLException logOrIgnore) {}
	        if (con != null) try { con.getConnection().close(); } catch (SQLException logOrIgnore) {}
	    }
		
		return null;
	}

}
