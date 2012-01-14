package cl.ht.facturacion.services;

public interface ServiceClientes {

	public String getAllClientes();
	public void insertNewCliente(String rut, String nombre, String apellido, String ciudad, String comuna,
			String direccion, String giro, boolean estado);
}
