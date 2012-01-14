package cl.ht.facturacion.services.impl;

import cl.ht.facturacion.dao.impl.DAOClientesImpl;
import cl.ht.facturacion.services.ServiceClientes;

public class ServiceClientesImpl implements ServiceClientes {

	@Override
	public String getAllClientes() {
		// TODO Auto-generated method stub
//		String resultado = "";
//		DAOClientesImpl daoClientes = new DAOClientesImpl();
//		ResultSet rs = daoClientes.getAllClientes();
//		try {
//			if(rs.getFetchSize() > 0)
//				resultado = "vacio";
//			else
//				resultado = ""+rs.getFetchSize();
//				
//			return resultado;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return null;
	}

	@Override
	public void insertNewCliente(String rut, String nombre, String apellido,
			String ciudad, String comuna, String direccion, String giro, boolean estado) {
		// TODO Auto-generated method stub
		DAOClientesImpl daoClientes = new DAOClientesImpl();
		daoClientes.newCliente(rut, nombre, apellido, ciudad, comuna, direccion, giro, estado);
	}

}
