package cl.ht.facturacion.dao.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
		
		BigDecimal totalGuias = new BigDecimal(0);
		BigDecimal totalNeto = new BigDecimal(0);
		BigDecimal IVA = new BigDecimal(0.19);
		BigDecimal factor = new BigDecimal(1.19);
		BigDecimal totalIVA = new BigDecimal(0);
		BigDecimal totalFactura = new BigDecimal(0);

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
				totalGuias = totalGuias.add(guia.getTotal());
				
				System.out.println("DAOFAC precio guia: "+guia.getTotal().toString());
				System.out.println("DAOFAC total guias en: "+totalGuias.toString()+", guia: "+guia.getNumero());
			}

			//DEBUG
			System.out.println("DAOFAC total guias final: "+totalGuias.toString());
			
			MathContext mc_8 = new MathContext(8,RoundingMode.HALF_UP);

			/*
			 * Total Guias Neto
			 */
			//totalNeto = totalGuias.divide(factor);
			totalNeto = totalGuias.divide(factor, mc_8); // EL RESULTADO ES MUY GRANDE!!!

			/*
			 * Total IVA
			 */
			totalIVA = totalNeto.multiply(IVA, mc_8);
			
			/*
			 * Total Factura
			 */
			totalFactura = totalGuias;
			
			System.out.println("Numero de guias a facturar: "+numeroGuias);
			System.out.println("valores factura: ");
			System.out.println("total neto: "+totalNeto.setScale(0,BigDecimal.ROUND_HALF_UP).toString());
			System.out.println("total iva: "+totalIVA.setScale(0,BigDecimal.ROUND_HALF_UP).toString());
			System.out.println("total factura: "+totalFactura.setScale(0,BigDecimal.ROUND_HALF_UP).toString());
			
			pstm.setInt(1, numerofactura);
			pstm.setString(2, cliente.getId());
			pstm.setDate(3, fechaSQL);
			pstm.setBigDecimal(4, totalNeto.setScale(0,BigDecimal.ROUND_HALF_UP));
			pstm.setBigDecimal(5, totalIVA.setScale(0,BigDecimal.ROUND_HALF_UP));
			pstm.setBigDecimal(6, totalFactura.setScale(0,BigDecimal.ROUND_HALF_UP));
			
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
				factura.setValorNeto(res.getBigDecimal(5));
				factura.setValorIva(res.getBigDecimal(6));
				factura.setValorTotal(res.getBigDecimal(7));
				
				System.out.println("llego: "+res.getBigDecimal(5).toString()+","+res.getBigDecimal(6).toString()+","+res.getBigDecimal(7).toString());
				
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
				factura.setValorNeto(res.getBigDecimal(5));
				factura.setValorIva(res.getBigDecimal(6));
				factura.setValorTotal(res.getBigDecimal(7));
				
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
				factura.setValorNeto(res.getBigDecimal(5));
				factura.setValorIva(res.getBigDecimal(6));
				factura.setValorTotal(res.getBigDecimal(7));
				
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
				factura.setValorNeto(res.getBigDecimal(5));
				factura.setValorIva(res.getBigDecimal(6));
				factura.setValorTotal(res.getBigDecimal(7));
				
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