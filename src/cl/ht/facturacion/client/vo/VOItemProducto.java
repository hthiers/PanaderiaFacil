package cl.ht.facturacion.client.vo;

import java.math.BigDecimal;

public class VOItemProducto {

	private int id;
	private VOProducto idprod;
	private VOGuia idguia;
	private int precio;
	private BigDecimal total;
	private BigDecimal cantidad;
	
	public VOItemProducto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOItemProducto(int id, VOProducto idprod, VOGuia idguia,
			int precio, BigDecimal total, BigDecimal cantidad) {
		super();
		this.id = id;
		this.idprod = idprod;
		this.idguia = idguia;
		this.cantidad = cantidad;
		this.precio = precio;
		this.total = total;
	}
	
	public VOItemProducto(VOProducto idprod, VOGuia idguia,
			int precio, BigDecimal total, BigDecimal cantidad) {
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

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public BigDecimal getTotal() {
		return total.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getCantidad() {
		return cantidad;
		//return cantidad.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
}