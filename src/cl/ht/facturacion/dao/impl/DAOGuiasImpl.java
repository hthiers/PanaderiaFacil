package cl.ht.facturacion.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import cl.ht.facturacion.client.vo.VOCliente;
import cl.ht.facturacion.client.vo.VOFactura;
import cl.ht.facturacion.client.vo.VOGuia;
import cl.ht.facturacion.client.vo.VOItemProducto;
import cl.ht.facturacion.client.vo.VOProducto;
import cl.ht.facturacion.dao.DAOGuias;
import cl.ht.facturacion.dao.connection.DBConnection;
import cl.ht.facturacion.dao.connection.DBConnectionSing;

public class DAOGuiasImpl implements DAOGuias {

	@Override
	public void newGuia(String codprod, VOCliente cliente) {
		// TODO Auto-generated method stub

		DBConnection con = null;
		PreparedStatement pstm = null;

		try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"INSERT INTO guiadespacho"+
					" (clirut,clinombre,cliapellido,cliciudad,clicomuna,cligiro,estid)"+
					" VALUES (?,?,?,?,?,?,?)");
			pstm.setString(1, cliente.getRut());
			pstm.setString(2, cliente.getNombres());
			pstm.setString(3, cliente.getApellidos());
			pstm.setString(4, cliente.getCiudad());
			pstm.setString(5, cliente.getComuna());
			pstm.setString(6, cliente.getGiro());
			if(cliente.getEstado() == 1)
				pstm.setInt(7, 1);
			else
				pstm.setInt(7, 2);
			
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
	public void newGuia(VOGuia guiaDespacho, VOCliente cliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newItemGuia(String idprod, String cantidad, String precio, String total) {
		// TODO Auto-generated method stub

		DBConnection con = null;
		PreparedStatement pstm = null;

		try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"INSERT INTO itemproducto"+
					" (prodid,itmcantidad,itmprecio,itmtotal)"+
					" VALUES (?,?,?,?)");
			pstm.setString(1, idprod);
			pstm.setString(2, cantidad);
			pstm.setString(3, precio);
			pstm.setString(4, total);
			
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
	public void newItemProducto(VOItemProducto item) {
		// TODO Auto-generated method stub		
		
		System.out.println("Item ID y GuiaID: "+item.getId() +","+ item.getIdguia().getId());
		
		DBConnection con = null;
		PreparedStatement pstm = null;

		try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"INSERT INTO itemproducto"+
					" (proid,guiid,itmcantidad,itmpreciounit,itmtotal)"+
					" VALUES (?,?,?,?,?)");
			
			pstm.setInt(1, item.getIdprod().getId());
			pstm.setInt(2, item.getIdguia().getId());
			pstm.setBigDecimal(3, item.getCantidad());
			pstm.setInt(4, item.getPrecio());
			pstm.setBigDecimal(5, item.getTotal());
			
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
	public void newItemGuia(VOItemProducto item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newGuia(int numeroGuia, VOCliente cliente, Calendar fecha, int total) {
		// TODO Auto-generated method stub
		
		java.sql.Date fechaSQL = new java.sql.Date(fecha.getTimeInMillis());
		
		DBConnection con = null;
		PreparedStatement pstm = null;

		try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"INSERT INTO guiadespacho"+
					" (guinumero,cliid,guifecha,guitotal)"+
					" VALUES (?,?,?,?)");
			
			pstm.setInt(1, numeroGuia);
			pstm.setString(2, cliente.getId());
			pstm.setDate(3, fechaSQL);
			pstm.setInt(4, total);
			
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
	public boolean addItemsToGuia(ArrayList<VOItemProducto> itemsGuia, VOGuia guia) {
		// TODO Auto-generated method stub
		
		boolean resultado = false;
		
		DBConnection con = null;
		PreparedStatement pstm = null;
		String sql = null;

		Iterator<VOItemProducto> iter = itemsGuia.iterator();
		
		//Ingresa un nuevo item a la guia según la lista de items que existan en la lista
		while(iter.hasNext()){
			try {
				con = new DBConnection("root","walkirias84");
				sql = "INSERT INTO guiadespacho_has_itemproducto"+
				" (guiadespacho_guiid,itemproducto_itmid)"+
				" VALUES ("+guia.getId()+","+iter.next().getId()+")";
				
				pstm = con.getConnection().prepareStatement(sql);
				
				System.out.println("sql: "+sql);
				
				int result = pstm.executeUpdate();
				System.out.println("result: "+result);
				
				resultado = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e);
			} finally {
		        if (pstm != null) try { pstm.close(); } catch (SQLException logOrIgnore) {}
		        if (con != null) try { con.getConnection().close(); } catch (SQLException logOrIgnore) {}
		    }
		}
		
		return resultado;
	}

	@Override
	public VOGuia getGuiaByNumero(int numeroGuia, boolean nulas) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			
			if(nulas) {
				sql = "SELECT * FROM guiadespacho gui"+
				" WHERE gui.guinumero = "+numeroGuia+"";
			}
			else {
				sql = "SELECT * FROM guiadespacho gui"+
				" WHERE gui.guinumero = "+numeroGuia+"" +
				" AND gui.nula = 0";
			}

			res = stm.executeQuery(sql);
			
			res.last();
			
			if(res.getRow() > 0) {
				res.first(); //go to top
				VOGuia guia = new VOGuia();
				guia.setId(res.getInt(1));
				guia.setNumero(res.getInt(2));
				guia.setIdcliente(res.getInt(3));
				Calendar cal = Calendar.getInstance();
				cal.setTime(res.getDate(4));
				guia.setFecha(cal);
				guia.setTotal(res.getBigDecimal(5));
				
				//Redondear a decimal
				//valorDecimal = new BigDecimal(res.getDouble(5));
				//valorEntero = valorDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
				//guia.setTotal(valorEntero);
			
				guia.setNula(res.getBoolean(7));
				
				System.out.println("creando guia - id: "+guia.getId());
				
				return guia;
			}
			else
				System.out.println("No hay registrosde guias");
			
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
	public VOGuia getGuiaByID(int idGuia) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM guiadespacho gui"+
			" WHERE gui.guiid = '"+idGuia+"' ";
			res = stm.executeQuery(sql);
			
			
			res.last();
			
			if(res.getRow() > 0) {
				res.first(); //go to top
				VOGuia guia = new VOGuia();
				guia.setId(res.getInt(1));
				guia.setNumero(res.getInt(2));
				guia.setIdcliente(res.getInt(3));
				guia.setNula(res.getBoolean(7));
				/*
				 * FALTA AGREGAR CAMPOS!
				 */
			
				return guia;
			}
			else
				System.out.println("No hay registrosde guias");
			
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
	public VOItemProducto getItemProductoByProductoID(VOProducto producto) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM itemproducto itm"+
			" WHERE itm.proid = "+producto.getId()+" ";
			res = stm.executeQuery(sql);
			
			System.out.println("getItemProductoByProductoID SQL: "+sql);
			
			res.last();
			
			if(res.getRow() > 0) {
				res.first(); //go to top
				VOItemProducto item = new VOItemProducto();
				item.setId(res.getInt(1));
				item.setIdprod(producto);
				item.setCantidad(res.getBigDecimal(3));
				item.setPrecio(res.getInt(4));
				item.setTotal(res.getBigDecimal(5));
			
				return item;
			}
			else
				System.out.println("No hay registrosde guias");
			
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
	public VOItemProducto getItemProductoByProductoYGuia(VOProducto producto,
			VOGuia guia) {
		// TODO Auto-generated method stub
		
		DAOProductosImpl daoProductos = new DAOProductosImpl();
		VOItemProducto item = null;
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM itemproducto itm"+
						" WHERE itm.guiid = "+guia.getId()+" "+
						" AND itm.proid = "+producto.getId()+" ";
			res = stm.executeQuery(sql);
			
			if(res.next()) {
				item = new VOItemProducto();
				
				System.out.println("ITEM PRODUCTO");
				System.out.println("id: "+res.getInt(1));
				System.out.println("id producto: "+daoProductos.getProductoByID(res.getInt(2)));
				System.out.println("cantidad: "+res.getInt(4));
				System.out.println("precio: "+res.getInt(5));
				System.out.println("total: "+res.getInt(6));
				
				
				item.setId(res.getInt(1));
				item.setIdprod(daoProductos.getProductoByID(res.getInt(2)));
				item.setCantidad(res.getBigDecimal(4));
				item.setPrecio(res.getInt(5));
				item.setTotal(res.getBigDecimal(6));
			}
			else
				System.out.println("No se encontro item de producto");
			
			return item;
			
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

	//TEST BIGDECIMAL
	@Override
	public ArrayList<VOItemProducto> getAllItemProductoByGuia(VOGuia guia) {
		// TODO Auto-generated method stub
		
		ArrayList<VOItemProducto> listaItems = new ArrayList<VOItemProducto>();
		DAOProductosImpl daoProductos = new DAOProductosImpl();
		VOItemProducto item; //test bigdecimal
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM itemproducto itm"+
			" WHERE guiid = "+guia.getId()+" ";
			res = stm.executeQuery(sql);
			
			System.out.println("getItemProductoByProductoID SQL: "+sql);
			
			while(res.next()) {
				item = new VOItemProducto();
				
				System.out.println("ITEM PRODUCTO");
				System.out.println("id: "+res.getInt(1));
				System.out.println("id producto: "+daoProductos.getProductoByID(res.getInt(2)));
				System.out.println("cantidad: "+res.getBigDecimal(4).toString());
				System.out.println("precio: "+res.getInt(5));
				System.out.println("total: "+res.getBigDecimal(6).toString());
				
				
				item.setId(res.getInt(1));
				item.setIdprod(daoProductos.getProductoByID(res.getInt(2)));
				item.setCantidad(res.getBigDecimal(4));
				item.setPrecio(res.getInt(5));
				item.setTotal(res.getBigDecimal(6));
			
				listaItems.add(item);
			}
			
			return listaItems;
			
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
	public boolean checkExistenGuia(int numeroGuia) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM guiadespacho gui"+
			" WHERE gui.guinumero = "+numeroGuia+" ";
			res = stm.executeQuery(sql);
			
			res.last();
			
			if(res.getRow() > 0)
				return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} finally {
	        if (res != null) try { res.close(); } catch (SQLException logOrIgnore) {}
	        if (stm != null) try { stm.close(); } catch (SQLException logOrIgnore) {}
	        if (con != null) try { con.getConnection().close(); } catch (SQLException logOrIgnore) {}
	    }
		
		return false;
	}

	@Override
	public ArrayList<VOGuia> getAllGuiasByDate(Calendar fechaDesde, Calendar fechaHasta, boolean nulas) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		VOGuia guia;
		ArrayList<VOGuia> listaGuias = new ArrayList<VOGuia>();
		
		java.sql.Date sqlDesde = new java.sql.Date(fechaDesde.getTimeInMillis());
		java.sql.Date sqlHasta = new java.sql.Date(fechaHasta.getTimeInMillis());
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			
			if(nulas) {
				sql = "SELECT * FROM guiadespacho gui"+
				" WHERE gui.guifecha >= '"+sqlDesde+"' "+
				" AND gui.guifecha < '"+sqlHasta+"' ";
			}
			else{
				sql = "SELECT * FROM guiadespacho gui"+
				" WHERE gui.guifecha >= '"+sqlDesde+"' "+
				" AND gui.guifecha < '"+sqlHasta+"' "+
				" AND gui.nula IS FALSE";
			}
				
			res = stm.executeQuery(sql);
			
			System.out.println("comparando fechas sql: "+sql);
			
			while(res.next()) {
				guia = new VOGuia();
				guia.setId(res.getInt(1));
				guia.setNumero(res.getInt(2));
				guia.setIdcliente(res.getInt(3));
				Calendar fecha = Calendar.getInstance();
				fecha.setTimeInMillis(res.getDate(4).getTime());
				guia.setFecha(fecha);
				guia.setTotal(res.getBigDecimal(5));
				
				//Redondear a decimal
				//valorDecimal = new BigDecimal(res.getDouble(5));
				//valorEntero = valorDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
				//guia.setTotal(valorEntero);
				
				guia.setNula(res.getBoolean(7));
				
				listaGuias.add(guia);
			}
			
			return listaGuias;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} finally {
	        if (res != null) try { res.close(); } catch (SQLException logOrIgnore) {}
	        if (stm != null) try { stm.close(); } catch (SQLException logOrIgnore) {}
	        if (con != null) {
	        	System.out.println("con existe");
	        	try { 
	        		con.getConnection().close(); 
	        		con.desconectar();
	        	} catch (SQLException logOrIgnore) {}
	        }
	        else
	        	System.out.println("con no existe");
	    }
		
		return null;
	}
	
	public ArrayList<VOGuia> getAllGuiasByDateSING(Calendar fechaDesde, Calendar fechaHasta) {
		// TODO Auto-generated method stub
		
		DBConnectionSing thread = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		VOGuia guia;
		ArrayList<VOGuia> listaGuias = new ArrayList<VOGuia>();
		
		java.sql.Date sqlDesde = new java.sql.Date(fechaDesde.getTimeInMillis());
		java.sql.Date sqlHasta = new java.sql.Date(fechaHasta.getTimeInMillis());
		
		 try {
			thread = new DBConnectionSing();
			stm = thread.getSingleConnection("root", "walkirias84").createStatement();
			sql = "SELECT * FROM guiadespacho gui"+
			" WHERE gui.guifecha >= '"+sqlDesde+"' "+
			" AND gui.guifecha < '"+sqlHasta+"' "+
			" AND gui.nula = 0";
			res = stm.executeQuery(sql);
			
			System.out.println("comparando fechas sql: "+sql);
			
			while(res.next()) {
				guia = new VOGuia();
				guia.setId(res.getInt(1));
				guia.setNumero(res.getInt(2));
				guia.setIdcliente(res.getInt(3));
				Calendar fecha = Calendar.getInstance();
				fecha.setTimeInMillis(res.getDate(4).getTime());
				guia.setFecha(fecha);
				guia.setTotal(res.getBigDecimal(5));
				
				//Redondear a decimal
				//valorDecimal = new BigDecimal(res.getDouble(5));
				//valorEntero = valorDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
				//guia.setTotal(valorEntero);
				
				listaGuias.add(guia);
			}
			
			return listaGuias;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} finally {
	        if (res != null) try { res.close(); } catch (SQLException logOrIgnore) {}
	        if (stm != null) try { stm.close(); } catch (SQLException logOrIgnore) {}
	    }
		
		return null;
	}
	
	@Override
	public ArrayList<VOGuia> getAllGuias() {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		VOGuia guia;
		ArrayList<VOGuia> listaGuias = new ArrayList<VOGuia>();
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM guiadespacho gui ORDER BY gui.guinumero";
			res = stm.executeQuery(sql);
			
			while(res.next()) {
				guia = new VOGuia();
				guia.setId(res.getInt(1));
				guia.setNumero(res.getInt(2));
				guia.setIdcliente(res.getInt(3));
				Calendar fecha = Calendar.getInstance();
				fecha.setTimeInMillis(res.getDate(4).getTime());
				guia.setFecha(fecha);
				guia.setTotal(res.getBigDecimal(5));
				
				//Redondear a decimal
				//valorDecimal = new BigDecimal(res.getDouble(5));
				//valorEntero = valorDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
				//guia.setTotal(valorEntero);
				
				guia.setNula(res.getBoolean(7));
				
				listaGuias.add(guia);
			}
			
			return listaGuias;
			
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
	public void facturarGuia(VOGuia guia, VOFactura factura) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		PreparedStatement pstm = null;
		
		System.out.println("guia a facturar: "+guia.getId());
		System.out.println("factura corresp. :"+factura.getId());
		
		try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"UPDATE guiadespacho "+
					" SET guiadespacho.facid = "+factura.getId()+" "+
					" WHERE guiadespacho.guiid = "+guia.getId()+" "+
					" AND guiadespacho.nula IS FALSE");
			
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
	public ArrayList<VOGuia> getAllGuiasByFactura(int idFactura) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		VOGuia guia;
		ArrayList<VOGuia> listaGuias = new ArrayList<VOGuia>();
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM guiadespacho gui"+
						" WHERE gui.facid = "+idFactura+" ";
			res = stm.executeQuery(sql);
			
			while(res.next()) {
				guia = new VOGuia();
				guia.setId(res.getInt(1));
				guia.setNumero(res.getInt(2));
				guia.setIdcliente(res.getInt(3));
				Calendar fecha = Calendar.getInstance();
				fecha.setTimeInMillis(res.getDate(4).getTime());
				guia.setFecha(fecha);
				guia.setTotal(res.getBigDecimal(5));
				
				//Redondear a decimal
				//valorDecimal = new BigDecimal(res.getDouble(5));
				//valorEntero = valorDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
				//guia.setTotal(valorEntero);
				
				guia.setNula(res.getBoolean(7));
				
				listaGuias.add(guia);
			}
			
			return listaGuias;
			
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
	public ArrayList<VOGuia> getAllGuiasByFacturaProducto(int idFactura,
			int idProducto) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		VOGuia guia;
		ArrayList<VOGuia> listaGuias = new ArrayList<VOGuia>();
		
		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM guiadespacho gui"+
						" WHERE gui.facid = "+idFactura+" "+
						" AND guiid IN (SELECT guiid FROM itemproducto" +
                        		" WHERE itemproducto.proid = "+idProducto+")";
			
			res = stm.executeQuery(sql);
			
			while(res.next()) {
				guia = new VOGuia();
				guia.setId(res.getInt(1));
				guia.setNumero(res.getInt(2));
				guia.setIdcliente(res.getInt(3));
				Calendar fecha = Calendar.getInstance();
				fecha.setTimeInMillis(res.getDate(4).getTime());
				guia.setFecha(fecha);
				guia.setTotal(res.getBigDecimal(5));
				
				//Redondear a decimal
				//valorDecimal = new BigDecimal(res.getDouble(5));
				//valorEntero = valorDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
				//guia.setTotal(valorEntero);
				
				guia.setNula(res.getBoolean(7));
				
				listaGuias.add(guia);
			}
			
			return listaGuias;
			
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
	public int[] getIDProductosInGuiasByFactura(int idFactura) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		DAOProductosImpl daoProductos = new DAOProductosImpl();
		
		/*
		 * PROBLEMA!
		 * EL ARREGLO ES MAS GRANDE QUE LA CANTIDAD DE PRODUCTOS ENCONTRADOS EN LA FACTURA
		 * ERROR AL TOMAR VALOR EN POSICION NO EXISTENTE EN LA FACTURA
		 */
		int[] productos = new int[daoProductos.getNumeroProductos()];
		
		System.out.println("productos registrados: "+productos.length);
		
		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT DISTINCT proid FROM itemproducto i"+
						" WHERE guiid IN (SELECT guiid FROM guiadespacho"+
										" WHERE guiadespacho.facid = "+idFactura+")";
			
			res = stm.executeQuery(sql);
			
			int x=0;
			while(res.next()){
				System.out.println("esta el prod: "+res.getInt(1));
				productos[x] = res.getInt(1);
				x+=1;
			}
			
			//DEBUG
			System.out.println("DEBUG num productos encontrados: "+x);
			
			return productos;
			
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
	public VOItemProducto getItemProductoByProductoYGuia(int idProducto,
			int idGuia) {
		// TODO Auto-generated method stub
		
		DAOProductosImpl daoProductos = new DAOProductosImpl();
		VOItemProducto item = null;
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM itemproducto itm"+
						" WHERE itm.guiid = "+idGuia+" "+
						" AND itm.proid = "+idProducto+" ";
			res = stm.executeQuery(sql);
			
			if(res.next()) {
				item = new VOItemProducto();
				
				System.out.println("ITEM PRODUCTO");
				System.out.println("id: "+res.getInt(1));
				System.out.println("id producto: "+daoProductos.getProductoByID(res.getInt(2)));
				System.out.println("cantidad: "+res.getInt(4));
				System.out.println("precio: "+res.getInt(5));
				System.out.println("total: "+res.getInt(6));
				
				
				item.setId(res.getInt(1));
				item.setIdprod(daoProductos.getProductoByID(res.getInt(2)));
				item.setCantidad(res.getBigDecimal(4));
				item.setPrecio(res.getInt(5));
				item.setTotal(res.getBigDecimal(6));
			}
			else
				System.out.println("No se encontro item de producto");
			
			return item;
			
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
	public int getNumeroItemsByGuia(int idGuia) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		int numeroItems = 0;
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT COUNT(itm.itmid) FROM itemproducto itm"+
						" WHERE itm.guiid = "+idGuia+" ";
			res = stm.executeQuery(sql);
			
			if(res.next()) {
				numeroItems = res.getInt(1);
			}
			else
				System.out.println("No se encontraron items de producto para esta guia");
			
			return numeroItems;
			
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
	public ArrayList<VOGuia> getAllGuiasByClienteDateNoFacturadas(VOCliente cliente, Calendar fechaDesde,
			Calendar fechaHasta, boolean nulas) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		VOGuia guia;
		ArrayList<VOGuia> listaGuias = new ArrayList<VOGuia>();
		
		java.sql.Date sqlDesde = new java.sql.Date(fechaDesde.getTimeInMillis());
		java.sql.Date sqlHasta = new java.sql.Date(fechaHasta.getTimeInMillis());
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			
			if(nulas) {
				sql = "SELECT * FROM guiadespacho gui"+
						" WHERE gui.guifecha >= '"+sqlDesde+"' "+
						" AND gui.guifecha < '"+sqlHasta+"' "+
						" AND gui.cliid = "+cliente.getId()+""+
						" AND gui.facid IS NULL";
			}
			else {
				sql = "SELECT * FROM guiadespacho gui"+
						" WHERE gui.guifecha >= '"+sqlDesde+"' "+
						" AND gui.guifecha < '"+sqlHasta+"' "+
						" AND gui.cliid = "+cliente.getId()+""+
						" AND gui.nula IS FALSE"+
						" AND gui.facid IS NULL";
			}
			
			res = stm.executeQuery(sql);
			
			System.out.println("comparando fechas sql: "+sql);
			
			while(res.next()) {
				guia = new VOGuia();
				guia.setId(res.getInt(1));
				guia.setNumero(res.getInt(2));
				guia.setIdcliente(res.getInt(3));
				Calendar fecha = Calendar.getInstance();
				fecha.setTimeInMillis(res.getDate(4).getTime());
				guia.setFecha(fecha);
				guia.setTotal(res.getBigDecimal(5));
				
				//Redondear a decimal
				//valorDecimal = new BigDecimal(res.getDouble(5));
				//valorEntero = valorDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
				//guia.setTotal(valorEntero);
				
				guia.setNula(res.getBoolean(7));
				
				listaGuias.add(guia);
			}
			
			return listaGuias;
			
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
	public ArrayList<VOGuia> getAllGuiasByMonthNoFacturadas(Calendar mes, boolean nulas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<VOGuia> getAllGuiasByClienteDate(VOCliente cliente,
			Calendar fechaDesde, Calendar fechaHasta, boolean nulas) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		VOGuia guia;
		ArrayList<VOGuia> listaGuias = new ArrayList<VOGuia>();
		
		java.sql.Date sqlDesde = new java.sql.Date(fechaDesde.getTimeInMillis());
		java.sql.Date sqlHasta = new java.sql.Date(fechaHasta.getTimeInMillis());
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			
			if(nulas) {
				sql = "SELECT * FROM guiadespacho gui"+
						" WHERE gui.guifecha >= '"+sqlDesde+"' "+
						" AND gui.guifecha < '"+sqlHasta+"' "+
						" AND gui.cliid = "+cliente.getId()+"";
			}
			else {
				sql = "SELECT * FROM guiadespacho gui"+
						" WHERE gui.guifecha >= '"+sqlDesde+"' "+
						" AND gui.guifecha < '"+sqlHasta+"' "+
						" AND gui.cliid = "+cliente.getId()+""+
						" AND gui.nula IS FALSE";
			}
			
			res = stm.executeQuery(sql);

			System.out.println("comparando fechas sql: "+sql);

			while(res.next()) {
				guia = new VOGuia();
				guia.setId(res.getInt(1));
				guia.setNumero(res.getInt(2));
				guia.setIdcliente(res.getInt(3));
				Calendar fecha = Calendar.getInstance();
				fecha.setTimeInMillis(res.getDate(4).getTime());
				guia.setFecha(fecha);
				guia.setTotal(res.getBigDecimal(5));

				//Redondear a decimal
				//valorDecimal = new BigDecimal(res.getDouble(5));
				//valorEntero = valorDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
				//guia.setTotal(valorEntero);

				guia.setNula(res.getBoolean(7));
				
				listaGuias.add(guia);
			}

			return listaGuias;
			
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
	public ArrayList<VOProducto> getAllProductosInGuiasByFactura(int idFactura) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		ArrayList<VOProducto> listaProductos = new ArrayList<VOProducto>();
		DAOProductosImpl daoProductos = new DAOProductosImpl();
		
		/*
		 * PROBLEMA!
		 * EL ARREGLO ES MAS GRANDE QUE LA CANTIDAD DE PRODUCTOS ENCONTRADOS EN LA FACTURA
		 * ERROR AL TOMAR VALOR EN POSICION NO EXISTENTE EN LA FACTURA
		 */
//		int[] productos = new int[daoProductos.getNumeroProductos()];
		
//		System.out.println("productos registrados: "+productos.length);
		
		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT DISTINCT proid FROM itemproducto i"+
						" WHERE guiid IN (SELECT guiid FROM guiadespacho"+
										" WHERE guiadespacho.facid = "+idFactura+")";
			
			res = stm.executeQuery(sql);
			
			while(res.next()){
				System.out.println("DEBUG-DAO esta el prod id: "+res.getInt(1));
				
				VOProducto producto = daoProductos.getProductoByID(res.getInt(1));
				listaProductos.add(producto);
			}
			
			//DEBUG
			System.out.println("DEBUG-DAO num productos encontrados: "+listaProductos.size());
			
			return listaProductos;
			
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
	public int getPesoVentaByGuia(VOGuia guia) {
		// TODO Auto-generated method stub
		
		int kgVenta = 0;
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT SUM(itmcantidad) FROM itemproducto itm"+
			" WHERE guiid = "+guia.getId()+" ";
			res = stm.executeQuery(sql);
			
			while(res.next()) {
				kgVenta += res.getInt(1);
			}
			
			return kgVenta;
			
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
	public VOGuia getLastGuia() {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT MAX(gui.guinumero) FROM guiadespacho gui";
			res = stm.executeQuery(sql);
			
			
			res.last();
			
			if(res.getRow() > 0) {
				res.first(); //go to top
				VOGuia guia = new VOGuia();
				guia.setNumero(res.getInt(1));
				
				return guia;
			}
			else {
				System.out.println("No hay registros de guias");
				return null;
			}
			
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
	public VOGuia getLastGuiaByCliente(VOCliente cliente) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM guiadespacho gui"+
					" WHERE gui.cliid = "+cliente.getId()+" "+
					" ORDER BY gui.guiid DESC";
			res = stm.executeQuery(sql);
			
			System.out.println("DEBUG sql: "+sql);
			
			res.last();
			
			if(res.getRow() > 0) {
				res.first(); //go to top
				VOGuia guia = new VOGuia();
				guia.setId(res.getInt(1));
				guia.setNumero(res.getInt(2));
				guia.setIdcliente(res.getInt(3));
				Calendar fecha = Calendar.getInstance();
				fecha.setTimeInMillis(res.getDate(4).getTime());
				guia.setFecha(fecha);
				guia.setTotal(res.getBigDecimal(5));

				//Redondear a decimal
				//BigDecimal valorDecimal = new BigDecimal(res.getDouble(5));
				//int valorEntero = valorDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
				//guia.setTotal(valorEntero);
				
				guia.setNula(res.getBoolean(7));
				
				return guia;
			}
			else {
				System.out.println("No hay registros de guias");
				return null;
			}
			
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
	public void anularGuia(VOGuia guia) {
		// TODO Auto-generated method stub
		DBConnection con = null;
		PreparedStatement pstm = null;

		System.out.println("@DEBUG guia a anular: "+guia.getId());

		try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"UPDATE guiadespacho "+
					" SET guiadespacho.nula = TRUE "+
					" WHERE guiadespacho.guiid = "+guia.getId()+" ");
			
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
	public boolean checkGuiaFacturada(int numeroGuia) {
		// TODO Auto-generated method stub
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM guiadespacho gui"+
			" WHERE gui.guinumero = "+numeroGuia+" "+
			" AND gui.facid IS NOT NULL";
			res = stm.executeQuery(sql);
			
			res.last();
			
			if(res.getRow() > 0)
				return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} finally {
	        if (res != null) try { res.close(); } catch (SQLException logOrIgnore) {}
	        if (stm != null) try { stm.close(); } catch (SQLException logOrIgnore) {}
	        if (con != null) try { con.getConnection().close(); } catch (SQLException logOrIgnore) {}
	    }
		
		return false;
	}

	@Override
	public void desanularGuia(VOGuia guia) {
		// TODO Auto-generated method stub
		DBConnection con = null;
		PreparedStatement pstm = null;
		
		System.out.println("@DEBUG guia a desanular: "+guia.getId());
		
		try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"UPDATE guiadespacho "+
					" SET guiadespacho.nula = FALSE "+
					" WHERE guiadespacho.guiid = "+guia.getId()+" ");
			
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