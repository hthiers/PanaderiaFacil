package cl.ht.facturacion.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import cl.ht.facturacion.client.util.Utils;
import cl.ht.facturacion.client.vo.VOFactura;
import cl.ht.facturacion.client.vo.VOGuia;
import cl.ht.facturacion.client.vo.VOItemProducto;
import cl.ht.facturacion.client.vo.VOItemProductoX;
import cl.ht.facturacion.dao.connection.DBConnectionSing;
import cl.ht.facturacion.dao.impl.DAOFacturasImpl;
import cl.ht.facturacion.dao.impl.DAOGuiasImpl;
import cl.ht.facturacion.services.ServiceReports;

public class ServiceReportsImpl implements ServiceReports {

	@Override
	public ArrayList<String> getGuiasReportByDate(String mes) {
		// TODO Auto-generated method stub
		
		ArrayList<String> reportes = new ArrayList<String>();
		DAOGuiasImpl daoGuias = new DAOGuiasImpl();
	    int totalVentaGuias = 0;
	    int totalNumeroGuias = 0;

	    Calendar fechaDesde = Utils.getDateByMonth(mes);
		Calendar fechaHasta = Utils.getNextDateByMonth(mes);
		System.out.println("@REPORTS desde: "+fechaDesde.get(Calendar.MONTH));
		System.out.println("@REPORTS hasta: "+fechaHasta.get(Calendar.MONTH));
		
	    ArrayList<VOGuia> listaAllGuias = daoGuias.getAllGuiasByDateSING(fechaDesde, fechaHasta);
		DBConnectionSing thread = new DBConnectionSing();
		thread.desconectar();
	    
	    totalNumeroGuias = listaAllGuias.size();
	    System.out.println("@REPORTS numero guias: "+totalNumeroGuias);
	    
	    Iterator<VOGuia> itguias = listaAllGuias.iterator();
	    
	    while(itguias.hasNext()) {
	    	VOGuia guia = (VOGuia)itguias.next();
	    	
	    	totalVentaGuias += Utils.doubleToInt(guia.getTotal());
	    }
	    
	    reportes.add("Número de guías emitidas: "+totalNumeroGuias);
	    reportes.add("Total en pesos: $"+totalVentaGuias);
	    
		return reportes;
	}

	@Override
	public ArrayList<String> getFacturasReportByDate(String mes) {
		ArrayList<String> reportes = new ArrayList<String>();
		DAOFacturasImpl daoFacturas = new DAOFacturasImpl();
	    int totalVenta = 0;
	    int facturasEmitidas = 0;
	    int kgsEmitido = 0;
	    int totalIVA = 0;

	    Calendar fechaDesde = Utils.getDateByMonth(mes);
		Calendar fechaHasta = Utils.getNextDateByMonth(mes);
		System.out.println("@REPORTS desde: "+fechaDesde.get(Calendar.MONTH));
		System.out.println("@REPORTS hasta: "+fechaHasta.get(Calendar.MONTH));
		
		ArrayList<VOFactura> listaFacturas = daoFacturas.getAllFacturasByDate(fechaDesde, fechaHasta);
		facturasEmitidas = listaFacturas.size();
		
		DAOGuiasImpl daoGuias = new DAOGuiasImpl();
		Iterator<VOFactura> itfacturas = listaFacturas.iterator();
		
	    while(itfacturas.hasNext()) {
	    	VOFactura factura = (VOFactura)itfacturas.next();
	    	ArrayList<VOGuia> listaGuias = daoGuias.getAllGuiasByFactura(factura.getId());
	    	
	    	for(int x=0; x<listaGuias.size(); x++) {
	    		ArrayList<VOItemProductoX> listaItems = daoGuias.getAllItemProductoByGuia(listaGuias.get(x));
	    		for(int i=0; i<listaItems.size(); i++) {
	    			kgsEmitido += listaItems.get(i).getCantidad();
	    		}
	    	}
	    	
	    	totalVenta += Utils.doubleToInt(factura.getValorTotal());
	    	totalIVA += totalVenta * 0.19;
	    }
		
	    reportes.add("Número de facturas emitidas: "+facturasEmitidas);
	    reportes.add("Cantidad facturado en Kg: "+kgsEmitido+" Kg");
	    reportes.add("Total en pesos: $"+totalVenta);
	    reportes.add("IVA total en pesos: $"+totalIVA);
	    
		return reportes;
	}
}
