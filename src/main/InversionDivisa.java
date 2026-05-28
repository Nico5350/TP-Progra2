package main;

public class InversionDivisa extends Inversion{
	private double tasa;
	private String divisa;
	private double cotizacionInicial;
	
	 public InversionDivisa(int id, String dni, String cvu,double monto, int plazoDias, String divisa, double tasa) {
		 super(id, dni, cvu, monto, plazoDias, true);
		 this.tasa = tasa;
		 this.divisa = divisa;
		 this.cotizacionInicial = Utilitarios.consultarCotizacion(divisa);
		 //cotizacion al momento de crear la inversion
	 }
	 //monto a divisas con la cotizacion inicial,calculas intereses de divisa y volves a pesos
	 @Override
	 public double calcularRendimiento() {
		 int diasTranscurridos = Utilitarios.hoy().getDayOfYear() - getFechaInicio().getDayOfYear();
		 double divisasEquivalente = getMonto() / cotizacionInicial;
		 double interesesEnDivisas = divisasEquivalente * (tasa/365) * diasTranscurridos;
		 return interesesEnDivisas * Utilitarios.consultarCotizacion(divisa); 
	 }
	 @Override
	 public double calcularDevolucion() {
	     int diasTranscurridos = Utilitarios.hoy().getDayOfYear() - getFechaInicio().getDayOfYear();
	     double divisasEquivalente = getMonto() / cotizacionInicial;
	     double interesesEnDivisa = divisasEquivalente * (tasa / 365) * diasTranscurridos;
	     interesesEnDivisa = interesesEnDivisa / 2;
	     return (divisasEquivalente + interesesEnDivisa) * Utilitarios.consultarCotizacion(divisa);
	 }
	 //guardamos divisas para saber cotizacion al momento de calcular el rendimiento
	 @Override
	    public String describir() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("fecha: ").append(getFecha()).append("\n");
	        sb.append("origen: ").append(getDni()).append(" (").append(getCvu()).append(")\n");
	        sb.append("desc: Inversion Divisa ").append(divisa).append("\n");
	        sb.append("monto: ").append(getMonto()).append("\n");
	        sb.append("plazo: ").append(getPlazoDias()).append("\n");
	        sb.append(estaAprobada() ? "Aprobado" : "Rechazado");
	        return sb.toString();
	    }
}
