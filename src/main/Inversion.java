package main;


import java.time.LocalDate;


public abstract class Inversion extends Actividad{
	
	private int id;
	private int plazoDias;
	private String dni;
	private String cvu;
	private LocalDate fechaInicio;
	private boolean activa;
	
	public Inversion(int id, String dni, String cvu, double monto, int plazoDias, boolean aprobada) {
        super(monto, aprobada);
        this.id = id;	//sirve para saber metodo de inversion y para precancelar
        this.dni = dni; //para el historial de transacciones
        this.cvu = cvu;
        this.plazoDias = plazoDias;
        this.fechaInicio = Utilitarios.hoy();
        this.activa = true;
    }
	public boolean esInversion() {
	    return true;
	}
	public int getId() {
		return id;
	}
	public String getDni() {
		return dni;
	}
	public int getPlazoDias() {
		return plazoDias;
	}
	public String getCvu() {
		return cvu;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public boolean estaActiva() { //indica si la inversion sigue viguente
		return activa;
	}
	public void cancelar() {
		activa = false;
	}
	public double obtenerMonto() {
		return getMonto();
	}
	public int obtenerPlazoDias() {
		return plazoDias;
	}
	public LocalDate obtenerFechaInicio() {
		return fechaInicio;
	}
	public boolean esPrecancelable() {
		return true;
	}
	public double calcularDevolucion() {
		return obtenerMonto() + calcularRendimiento() / 2;
	}
	public abstract double calcularRendimiento();
	
	public abstract String describir(); // formatos distintos de inversion
}
