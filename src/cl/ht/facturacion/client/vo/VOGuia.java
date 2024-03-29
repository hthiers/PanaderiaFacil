package cl.ht.facturacion.client.vo;

import java.math.BigDecimal;
import java.util.Calendar;

public class VOGuia {

	private int id;
	private int numero;
	private int idcliente;
	private Calendar fecha; 
	private BigDecimal total;
	private boolean nula;
	
	public VOGuia() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOGuia(int id, int numero, int idcliente, Calendar fecha, BigDecimal total, boolean nula) {
		super();
		this.id = id;
		this.numero = numero;
		this.idcliente = idcliente;
		this.fecha = fecha;
		this.total = total;
		this.nula = nula;
	}

	public boolean isNula() {
		return nula;
	}

	public void setNula(boolean nula) {
		this.nula = nula;
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

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getTotal() {
		return total.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
