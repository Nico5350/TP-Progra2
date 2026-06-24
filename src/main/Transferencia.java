package main;


public class Transferencia extends Actividad{
	private String dniOrigen;
	private String cvuOrigen;
	private String dniDestino;
	private String cvuDestino;
	
	public Transferencia(double monto, boolean aprobada, String dniOrigen, String cvuOrigen,
			String dniDestino, String cvuDestino) {
		
		super(monto, aprobada);
		this.dniOrigen = dniOrigen;
		this.cvuOrigen = cvuOrigen;
		this.dniDestino = dniDestino;
		this.cvuDestino = cvuDestino;
	}
	//StringBuilder requisito del tp (construye strings de a partes)
	@Override
	public String describir() {
        StringBuilder sb = new StringBuilder();
        sb.append("fecha: ").append(getFecha()).append("\n");
        sb.append("origen: ").append(dniOrigen).append(" (").append(cvuOrigen).append(")\n");
        sb.append("destino: ").append(dniDestino).append(" (").append(cvuDestino).append(")\n");
        sb.append("monto: ").append(getMonto()).append("\n");
        sb.append(estaAprobada() ? "Aprobado" : "Rechazado");
        return sb.toString();
    }
}
