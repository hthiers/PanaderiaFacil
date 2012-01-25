package cl.ht.facturacion.client.vo;

import java.math.BigDecimal;
import java.util.Calendar;

public class VOFactura {

	private int id;
	private int numero;
	private int idCliente;
	private Calendar fecha;
	private BigDecimal valorNeto;
	private BigDecimal valorIva;
	private BigDecimal valorTotal;
	
	public VOFactura() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOFactura(int id, int numero, int idCliente, Calendar fecha,
			BigDecimal valorNeto, BigDecimal valorIva, BigDecimal valorTotal) {
		super();
		this.id = id;
		this.numero = numero;
		this.idCliente = idCliente;
		this.fecha = fecha;
		this.valorNeto = valorNeto;
		this.valorIva = valorIva;
		this.valorTotal = valorTotal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getValorNeto() {
		return valorNeto.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public void setValorNeto(BigDecimal valorNeto) {
		this.valorNeto = valorNeto;
	}

	public BigDecimal getValorIva() {
		return valorIva.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public void setValorIva(BigDecimal valorIva) {
		this.valorIva = valorIva;
	}

	public BigDecimal getValorTotal() {
		return valorTotal.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}
