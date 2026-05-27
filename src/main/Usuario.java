package main;

import java.util.ArrayList;

public class Usuario {
	private String dni;
	private String nombre;
	private String telefono;
	private String email;
	private double totalInvertido;
	private ArrayList<Cuenta> cuentas;
	
	public Usuario(String dni, String nombre, String telefono, String email) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.totalInvertido = 0;
		this.cuentas = new ArrayList<Cuenta>();
	}
	public String obtenerDni() {
		return dni;
	}
	
	public String obtenerNombre() {
		return nombre;
	}
	
	public double obtenerTotalInvertido() {
		return totalInvertido;
	}
	
	public ArrayList<Cuenta> obtenerCuentas(){
		return cuentas;
	}
	
	public void agregarCuentas(Cuenta cuenta) {
		cuentas.add(cuenta);
	}
	
	public void agregarInvertido(double monto) {
		totalInvertido += monto;
	}
	
	public void restarInvertido(double monto) {
		totalInvertido -= monto;
	}
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario: ").append(nombre);
        sb.append(" | DNI: ").append(dni);
        sb.append(" | Tel: ").append(telefono);
        sb.append(" | Email: ").append(email);
        sb.append(" | Total invertido: $").append(totalInvertido);
        return sb.toString();
    }
}
