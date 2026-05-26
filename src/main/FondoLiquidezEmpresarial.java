package main;

public class FondoLiquidezEmpresarial extends Inversion{
	private static String activo = "FLE"; //activo de tasa fija
	private static double tasa = 0.08;	// tasa del cativo "FLE"
	private static double montoMinimo = 20000000; //monto minimo de inversion
	
	public FondoLiquidezEmpresarial(int id, String dni, String cvu,double monto, int plazoDias) {
		super(id, dni, cvu, monto, plazoDias, true);
	}
	public static double obtenerMontoMinimo() {
		return montoMinimo;
	}
	
	@Override
	//Consulta cotizacion y * por monto y taza diaria(inverison no precancelable)
	public double calcularRendimiento() {
		int diasTranscurridos = Utilitarios.hoy().getDayOfYear() - getFechaInicio().getDayOfYear();
		double cotizacion = Utilitarios.consultarCotizacion(activo);
		return getMonto() * cotizacion * (tasa/365) * diasTranscurridos;
	}
	
	@Override
    public String describir() {
        StringBuilder sb = new StringBuilder();
        sb.append("fecha: ").append(getFecha()).append("\n");
        sb.append("origen: ").append(getDni()).append(" (").append(getCvu()).append(")\n");
        sb.append("desc: Fondo de Liquidez Empresarial\n");
        sb.append("monto: ").append(getMonto()).append("\n");
        sb.append("plazo: ").append(getPlazoDias()).append("\n");
        sb.append(estaAprobada() ? "Aprobado" : "Rechazado");
        return sb.toString();
    }
}
