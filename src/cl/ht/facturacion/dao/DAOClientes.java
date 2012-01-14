package cl.ht.facturacion.dao;

import java.util.ArrayList;

import cl.ht.facturacion.client.vo.VOCliente;

/**
 * Interfaces de acceso a datos de Clientes
 * @author hernan
 *
 */
public interface DAOClientes {

	public ArrayList<VOCliente> getAllClientes(boolean activados);
	public VOCliente getClienteByRut(String rut);
	public VOCliente getClienteByName(String name);
	public VOCliente getClienteByID(int id);
	public void newCliente(String rut, String name, String lastname, String ciudad, String comuna, String direccion, String giro, boolean activated);
	public void updateCliente(int id, String rut, String name, String lastname, String ciudad, String comuna, String direccion, String giro, boolean activated);
	public void changeStatusCliente(String rut, boolean activated);
	public void newStatus(String nombre);
	public void updateStatus(String nombre, String newNombre);
	public void deleteStatus(String nombre);
	
}
