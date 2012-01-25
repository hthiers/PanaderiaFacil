package cl.ht.facturacion.dao;

import java.util.ArrayList;
import java.util.Calendar;

import cl.ht.facturacion.client.vo.VOCliente;
import cl.ht.facturacion.client.vo.VOFactura;
import cl.ht.facturacion.client.vo.VOGuia;
import cl.ht.facturacion.client.vo.VOItemProducto;
import cl.ht.facturacion.client.vo.VOItemProducto;
import cl.ht.facturacion.client.vo.VOProducto;

public interface DAOGuias {

	public void newGuia(String producto1, VOCliente cliente);
	public void newGuia(VOGuia guiaDespacho, VOCliente cliente);
	public void newGuia(int numeroGuia, VOCliente cliente, Calendar fecha, int total);
	
	public void facturarGuia(VOGuia guia, VOFactura factura);
	
	public void newItemGuia(String codproducto, String cantidad, String precio, String total);
	public void newItemGuia(VOItemProducto item);
	public void newItemProducto(VOItemProducto item);
	public boolean addItemsToGuia(ArrayList<VOItemProducto> itemsGuia, VOGuia guia);
	
	public VOItemProducto getItemProductoByProductoID(VOProducto producto);
	public VOItemProducto getItemProductoByProductoYGuia(VOProducto producto, VOGuia guia);
	public VOItemProducto getItemProductoByProductoYGuia(int idProducto, int idGuia);
	public ArrayList<VOItemProducto> getAllItemProductoByGuia(VOGuia guia); //TEST BIGDECIMAL
	
	public VOGuia getGuiaByNumero(int numeroGuia, boolean nulas);
	public VOGuia getGuiaByID(int idGuia);
	public ArrayList<VOGuia> getAllGuias();
	public ArrayList<VOGuia> getAllGuiasByDate(Calendar fechaDesde, Calendar fechaHasta, boolean nulas);
	public ArrayList<VOGuia> getAllGuiasByClienteDate(VOCliente cliente, Calendar fechaDesde, Calendar fechaHasta, boolean nulas);
	public ArrayList<VOGuia> getAllGuiasByClienteDateNoFacturadas(VOCliente cliente, Calendar fechaDesde, Calendar fechaHasta, boolean nulas);
	public ArrayList<VOGuia> getAllGuiasByMonthNoFacturadas(Calendar mes, boolean nulas);
	public ArrayList<VOGuia> getAllGuiasByFactura(int idFactura);
	public ArrayList<VOGuia> getAllGuiasByFacturaProducto(int idFactura, int idProducto);
	
	public int[] getIDProductosInGuiasByFactura(int idFactura);
	public ArrayList<VOProducto> getAllProductosInGuiasByFactura(int idFactura);
	
	public int getPesoVentaByGuia(VOGuia guia);
	
	public boolean checkExistenGuia(int numeroGuia);
	public boolean checkGuiaFacturada(int numeroGuia);
	
	public int getNumeroItemsByGuia(int numeroGuia);
	public VOGuia getLastGuia();
	public VOGuia getLastGuiaByCliente(VOCliente cliente);

	public void anularGuia(VOGuia guia);
	public void desanularGuia(VOGuia guia);
}
