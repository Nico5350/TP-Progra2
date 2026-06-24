package main;

import java.time.LocalDate;

public abstract class Actividad {
	
	private LocalDate fecha;
	private double monto;
	private boolean aprobada; //el historial pide mostrar aprobado o rechazado
	
	public Actividad(double monto, boolean aprobada) {
		this.fecha = Utilitarios.hoy(); //momento donde se crea la actividad
		this.monto = monto;
		this.aprobada = aprobada; 
	}
	
	public double getMonto() {
		return monto;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public boolean estaAprobada() {
		return aprobada;
	}
	public boolean esInversion() {
	    return false;
	}
	
	public abstract String describir(); //abstracto para transferencia e inversion
	
	@Override
	public String toString() {
		return describir();
	}
}
