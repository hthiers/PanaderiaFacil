package cl.ht.facturacion.dao;

import java.util.ArrayList;
import java.util.Calendar;

import cl.ht.facturacion.client.vo.VOCliente;
import cl.ht.facturacion.client.vo.VOFactura;
import cl.ht.facturacion.client.vo.VOGuia;

public interface DAOFacturas {

	public void newFactura(int numeroFactura, VOCliente cliente, ArrayList<VOGuia> listaGuias, Calendar fecha);
	public VOFactura getFacturaByNumero(int numeroFactura);
	public VOFactura getLastFactura();
	public ArrayList<VOFactura> getAllFacturas();
	public ArrayList<VOFactura> getAllFacturasByDate(Calendar fechaDesde, Calendar fechaHasta);
	public ArrayList<VOFactura> getAllFacturasByClienteDate(VOCliente cliente, Calendar fechaDesde, Calendar fechaHasta);
}
