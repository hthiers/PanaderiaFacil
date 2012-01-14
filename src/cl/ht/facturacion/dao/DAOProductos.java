package cl.ht.facturacion.dao;

import cl.ht.facturacion.client.vo.VOItemProducto;
import cl.ht.facturacion.client.vo.VOProducto;

public interface DAOProductos {

	public VOProducto getProductoByCodigo(String codigo);
	public VOProducto getProductoByID(int id);
	public int getNumeroProductos();
	
	public void newItemProducto(VOItemProducto item);
	public void newProducto(VOProducto producto);
	public void updateProducto(VOProducto producto);
	
}
