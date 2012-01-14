package cl.ht.facturacion.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import cl.ht.facturacion.client.util.Utils;
import cl.ht.facturacion.client.vo.VOCliente;
import cl.ht.facturacion.client.vo.VOFactura;
import cl.ht.facturacion.client.vo.VOGuia;
import cl.ht.facturacion.dao.DAOFacturas;
import cl.ht.facturacion.dao.connection.DBConnection;

public class DAOFacturasImpl implements DAOFacturas {

	@Override
	public void newFactura(int numerofactura, VOCliente cliente, ArrayList<VOGuia> listaGuias, Calendar fecha) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		PreparedStatement pstm = null;
		
		java.sql.Date fechaSQL = new java.sql.Date(fecha.getTimeInMillis());
		VOGuia guia;
		
		double totalGuias = 0;
		double totalNeto = 0;
		double IVA = 0.19;
		double factor = 1.19;
		double totalIVA = 0;
		double totalFactura = 0;

		try {
			con = new DBConnection("root","walkirias84");
			pstm = con.getConnection().prepareStatement(
					"INSERT INTO factura"+
					" (facnumero,cliente_cliid,facfecha,facneto,faciva,factotal)"+
					" VALUES (?,?,?,?,?,?)");
			
			/*
			 * Total Guias
			 */
			int numeroGuias = listaGuias.size();
			
			//DEBUG
			System.out.println("DAOFAC numero guias: "+numeroGuias);
			
			Iterator<VOGuia> iter = listaGuias.iterator();
			while(iter.hasNext()){
				guia = iter.next();
				totalGuias += guia.getTotal();
				System.out.println("DAOFAC precio guia: "+guia.getTotal());
				System.out.println("DAOFAC total guias en: "+totalGuias+", guia: "+guia.getNumero());
			}
			
			//DEBUG
			System.out.println("DAOFAC total guias final: "+totalGuias);
			
			/*
			 * Total Guias Neto
			 */
			totalNeto = totalGuias / factor;
			
			/*
			 * Total IVA
			 */
			totalIVA = totalNeto * IVA;
			
			/*
			 * Total Factura
			 */
			totalFactura = totalGuias;
			
			System.out.println("Numero de guias a facturar: "+numeroGuias);
			System.out.println("valores factura: ");
			System.out.println("total neto: "+totalNeto);
			System.out.println("total iva: "+totalIVA);
			System.out.println("total factura: "+totalFactura);
			
			pstm.setInt(1, numerofactura);
			pstm.setString(2, cliente.getId());
			pstm.setDate(3, fechaSQL);
			pstm.setDouble(4, Utils.redondeaDecimal(totalNeto));
			pstm.setDouble(5, Utils.redondeaDecimal(totalIVA));
			pstm.setDouble(6, Utils.redondeaDecimal(totalFactura));
			
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
	public VOFactura getLastFactura() {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT MAX(fac.facnumero) FROM factura fac";
			res = stm.executeQuery(sql);
			
			
			res.last();
			
			if(res.getRow() > 0) {
				res.first(); //go to top
				VOFactura factura = new VOFactura();
				factura.setNumero(res.getInt(1));
				
				return factura;
			}
			else {
				System.out.println("No hay registros de facturas");
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
	public VOFactura getFacturaByNumero(int numeroFactura) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM factura fac "+
						" WHERE fac.facnumero = "+numeroFactura+""+
						" AND fac.nula = 0";
			res = stm.executeQuery(sql);
			
			if(res.next()) {
				VOFactura factura = new VOFactura();
				Calendar cal = Calendar.getInstance();
				factura.setId(res.getInt(1));
				factura.setNumero(res.getInt(2));
				factura.setIdCliente(res.getInt(3));
				cal.setTime(res.getDate(4));
				factura.setFecha(cal);
				factura.setValorNeto(Utils.doubleToInt(res.getDouble(5)));
				factura.setValorIva(Utils.doubleToInt(res.getDouble(6)));
				factura.setValorTotal(Utils.doubleToInt(res.getDouble(7)));
				
				System.out.println("llego: "+res.getDouble(5)+","+res.getDouble(6)+","+res.getDouble(7));
				
				return factura;
			}
			else {
				System.out.println("No hay registros de facturas");
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
	public ArrayList<VOFactura> getAllFacturas() {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		VOFactura factura;
		ArrayList<VOFactura> listaFacturas = new ArrayList<VOFactura>();
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM factura fac ORDER BY fac.facnumero";
			res = stm.executeQuery(sql);
			
			while(res.next()) {
				factura = new VOFactura();
				factura.setId(res.getInt(1));
				factura.setNumero(res.getInt(2));
				factura.setIdCliente(res.getInt(3));
				Calendar fecha = Calendar.getInstance();
				fecha.setTimeInMillis(res.getDate(4).getTime());
				factura.setFecha(fecha);
				factura.setValorNeto(res.getInt(5));
				factura.setValorIva(res.getInt(6));
				factura.setValorTotal(res.getInt(7));
				
				listaFacturas.add(factura);
			}
			
			return listaFacturas;
			
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
	public ArrayList<VOFactura> getAllFacturasByClienteDate(VOCliente cliente,
			Calendar fechaDesde, Calendar fechaHasta) {
		// TODO Auto-generated method stub
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		VOFactura factura;
		ArrayList<VOFactura> listaFacturas = new ArrayList<VOFactura>();
		
		java.sql.Date sqlDesde = new java.sql.Date(fechaDesde.getTimeInMillis());
		java.sql.Date sqlHasta = new java.sql.Date(fechaHasta.getTimeInMillis());
		
		 try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM factura fac"+
			" WHERE fac.facfecha >= '"+sqlDesde+"' "+
			" AND fac.facfecha < '"+sqlHasta+"' "+
			" AND fac.cliente_cliid = "+cliente.getId()+"";
			res = stm.executeQuery(sql);

			System.out.println("comparando fechas sql: "+sql);

			while(res.next()) {
				factura = new VOFactura();
				factura.setId(res.getInt(1));
				factura.setNumero(res.getInt(2));
				factura.setIdCliente(res.getInt(3));
				Calendar fecha = Calendar.getInstance();
				fecha.setTimeInMillis(res.getDate(4).getTime());
				factura.setFecha(fecha);
				factura.setValorNeto(res.getInt(5));
				factura.setValorIva(res.getInt(6));
				factura.setValorTotal(res.getInt(7));
				
				listaFacturas.add(factura);
			}

			return listaFacturas;
			
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
	public ArrayList<VOFactura> getAllFacturasByDate(Calendar fechaDesde,
			Calendar fechaHasta) {
		// TODO Auto-generated method stub
		
		System.out.println("@DAOFACTURAS: getallfacturasbydate!");
		
		DBConnection con = null;
		Statement stm = null;
		String sql = null;
		ResultSet res = null;
		
		VOFactura factura;
		ArrayList<VOFactura> listaFacturas = new ArrayList<VOFactura>();
		
		java.sql.Date sqlDesde = new java.sql.Date(fechaDesde.getTimeInMillis());
		java.sql.Date sqlHasta = new java.sql.Date(fechaHasta.getTimeInMillis());
		
		try {
			con = new DBConnection("root","walkirias84");
			stm = con.getConnection().createStatement();
			sql = "SELECT * FROM factura fac"+
			" WHERE fac.facfecha >= '"+sqlDesde+"' "+
			" AND fac.facfecha < '"+sqlHasta+"' ";
			res = stm.executeQuery(sql);

			System.out.println("comparando fechas sql: "+sql);
			
			while(res.next()) {
				factura = new VOFactura();
				factura.setId(res.getInt(1));
				factura.setNumero(res.getInt(2));
				factura.setIdCliente(res.getInt(3));
				Calendar fecha = Calendar.getInstance();
				fecha.setTimeInMillis(res.getDate(4).getTime());
				factura.setFecha(fecha);
				factura.setValorNeto(res.getInt(5));
				factura.setValorIva(res.getInt(6));
				factura.setValorTotal(res.getInt(7));
				
				listaFacturas.add(factura);
			}

			return listaFacturas;
			
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
