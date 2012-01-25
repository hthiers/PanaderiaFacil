package cl.ht.facturacion.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cl.ht.facturacion.client.vo.VOItemProducto;
import cl.ht.facturacion.client.vo.VOProducto;
import cl.ht.facturacion.dao.DAOProductos;
import cl.ht.facturacion.dao.connection.DBConnection;

public class DAOProductosImpl implements DAOProductos {

	@Override
	public VOProducto getProductoByCodigo(String codigo) {
		// TODO Auto-generated method stub
		
		System.out.println("codigo ingresado:"+codigo);
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;

		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM producto pro"+
			" WHERE pro.procodigo = "+codigo+"";
			res = stm.executeQuery(sql);
			
			res.last();
			
			if(res.getRow() > 0) {
				res.first(); //go to top
				
				VOProducto producto = new VOProducto();
				producto.setId(res.getInt(1));
				producto.setCodigo(res.getString(2));
				producto.setNombre(res.getString(3));
				producto.setDescripcion(res.getString(4));
				producto.setPrecio(res.getInt(5));
			
				return producto;
			}
			else
				System.out.println("No existe el producto con codigo: "+codigo);
			
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
	public void newItemProducto(VOItemProducto item) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		PreparedStatement pstm = null;
		
		try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"INSERT INTO itemproducto"+
					" (proid,itmcantidad,itmpreciounit,itmtotal)"+
					" VALUES (?,?,?,?)");
			pstm.setInt(1, item.getIdprod().getId());
			pstm.setBigDecimal(2, item.getCantidad());
			pstm.setInt(3, item.getPrecio());
			pstm.setBigDecimal(4, item.getTotal());
			
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
	public VOProducto getProductoByID(int id) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		
		VOProducto producto = new VOProducto();

		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM producto pro"+
			" WHERE pro.proid = "+id+"";
			res = stm.executeQuery(sql);
			
			while(res.next()){
				producto.setId(res.getInt(1));
				producto.setCodigo(res.getString(2));
				producto.setNombre(res.getString(3));
				producto.setDescripcion(res.getString(4));
				producto.setPrecio(res.getInt(5));
			}
			
			return producto;
			
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
	public int getNumeroProductos() {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		int numeroProductos = 0;

		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT COUNT(pro.proid) FROM producto pro";
			res = stm.executeQuery(sql);
			
			if(res.next())
				numeroProductos = res.getInt(1);
			else
				System.out.println("No hay productos registrados");
			
			return numeroProductos;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} finally {
	        if (res != null) try { res.close(); } catch (SQLException logOrIgnore) {}
	        if (stm != null) try { stm.close(); } catch (SQLException logOrIgnore) {}
	        if (con != null) try { con.getConnection().close(); } catch (SQLException logOrIgnore) {}
	    }
		
		return 0;
	}

	@Override
	public void newProducto(VOProducto producto) {
		// TODO Auto-generated method stub

		DBConnection con = null;
		PreparedStatement pstm = null;
		
		try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"INSERT INTO producto"+
					" (procodigo,pronombre,prodescripcion,proprecio)"+
					" VALUES (?,?,?,?)");
			pstm.setInt(1, Integer.parseInt(producto.getCodigo()));
			pstm.setString(2, producto.getNombre());
			pstm.setString(3, producto.getDescripcion());
			pstm.setInt(4, producto.getPrecio());
			
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
	public void updateProducto(VOProducto producto) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		PreparedStatement pstm = null;
		
		try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"UPDATE producto"+
					" SET procodigo = ?, pronombre = ?, prodescripcion = ?, proprecio = ?"+
					" WHERE proid = ?");
			pstm.setInt(1, Integer.parseInt(producto.getCodigo()));
			pstm.setString(2, producto.getNombre());
			pstm.setString(3, producto.getDescripcion());
			pstm.setInt(4, producto.getPrecio());
			pstm.setInt(5, producto.getId());
			
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

	
}
