package cl.ht.facturacion.client.vo;

public class VOCliente {

	private String id;
	private String rut;
	private String nombres;
	private String apellidos;
	private String ciudad;
	private String comuna;
	private String direccion;
	private String giro;
	private int estado;
	
	public VOCliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOCliente(String id, String rut, String nombres, String apellidos,
			String ciudad, String comuna, String direccion, String giro, int estado) {
		super();
		this.id = id;
		this.rut = rut;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.ciudad = ciudad;
		this.comuna = comuna;
		this.direccion = direccion;
		this.giro = giro;
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getComuna() {
		return comuna;
	}

	public void setComuna(String comuna) {
		this.comuna = comuna;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getGiro() {
		return giro;
	}

	public void setGiro(String giro) {
		this.giro = giro;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
}
