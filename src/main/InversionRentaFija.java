package main;

public class InversionRentaFija extends Inversion{
	private double tasa; // valor de la tasa 20%

	public InversionRentaFija(int id, String dni, String cvu, double monto, int plazoDias,
			double tasa) {
		super(id, dni, cvu, monto, plazoDias, true);
		this.tasa = tasa;
	}
	
	@Override
	public double calcularRendimiento() {
		//resta las fechas para darnos los dias transcurrios hasta ahora
		int diasTranscurridos = Utilitarios.hoy().getDayOfYear() - getFechaInicio().getDayOfYear();
	    return getMonto() * (tasa / 365) * diasTranscurridos;
	}
	
	@Override
    public String describir() {
        StringBuilder sb = new StringBuilder();
        sb.append("fecha: ").append(getFecha()).append("\n");
        sb.append("origen: ").append(getDni()).append(" (").append(getCvu()).append(")\n");
        sb.append("desc: Renta Fija\n");
        sb.append("monto: ").append(getMonto()).append("\n");
        sb.append("plazo: ").append(getPlazoDias()).append("\n");
        sb.append(estaAprobada() ? "Aprobado" : "Rechazado");
        return sb.toString();
    }

	
}
