package main;

import java.util.ArrayList;

public class Empresa {
	private String CUIT;
	private String nombreFantasia;
	private String telefono;
	private String email;
	private String nombreContacto;
	private ArrayList<String> autorizados;
	
public Empresa(String cuit, String nombreFantasia, String telefono, String email, String nombreContacto){
	
	if (cuit==null || nombreFantasia==null || telefono==null || email==null || nombreContacto==null)
		throw new RuntimeException("Los datos pasasdos no son validos");
		
		this.CUIT=cuit;
		this.nombreFantasia=nombreFantasia;
		this.telefono=telefono;
		this.email=email;
		this.nombreContacto=nombreContacto;
		this.autorizados = new ArrayList<String>();
	}
	public void agregarAutorizado(String dni) {
		autorizados.add(dni);
	}
	
	public boolean estaAutorizado(String dni) {
		return autorizados.contains(dni);
	}
	public String obtenerCuit() {
		return CUIT;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Empresa: ").append(nombreFantasia);
	    sb.append(" | CUIT: ").append(CUIT);
	    sb.append(" | Autorizados: ").append(autorizados);
	    return sb.toString();
	}
}