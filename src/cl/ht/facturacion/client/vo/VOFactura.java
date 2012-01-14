package cl.ht.facturacion.client.vo;

import java.util.Calendar;

public class VOFactura {

	private int id;
	private int numero;
	private int idCliente;
	private Calendar fecha;
	private double valorNeto;
	private double valorIva;
	private double valorTotal;
	
	public VOFactura() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOFactura(int id, int numero, int idCliente, Calendar fecha,
			double valorNeto, double valorIva, double valorTotal) {
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

	public double getValorNeto() {
		return valorNeto;
	}

	public void setValorNeto(double valorNeto) {
		this.valorNeto = valorNeto;
	}

	public double getValorIva() {
		return valorIva;
	}

	public void setValorIva(double valorIva) {
		this.valorIva = valorIva;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}
