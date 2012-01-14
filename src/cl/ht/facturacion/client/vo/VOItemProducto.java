package cl.ht.facturacion.client.vo;

public class VOItemProducto {

	private int id;
	private VOProducto idprod;
	private VOGuia idguia;
	private int cantidad;
	private int precio;
	private int total;
	
	public VOItemProducto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOItemProducto(int id, VOProducto idprod, VOGuia idguia,
			int cantidad, int precio, int total) {
		super();
		this.id = id;
		this.idprod = idprod;
		this.idguia = idguia;
		this.cantidad = cantidad;
		this.precio = precio;
		this.total = total;
	}
	
	public VOItemProducto(VOProducto idprod, VOGuia idguia,
			int cantidad, int precio, int total) {
		super();
		this.idprod = idprod;
		this.idguia = idguia;
		this.cantidad = cantidad;
		this.precio = precio;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public VOProducto getIdprod() {
		return idprod;
	}

	public void setIdprod(VOProducto idprod) {
		this.idprod = idprod;
	}

	public VOGuia getIdguia() {
		return idguia;
	}

	public void setIdguia(VOGuia idguia) {
		this.idguia = idguia;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
		
}
