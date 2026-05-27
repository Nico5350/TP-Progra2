package main;

public class CuentaCorporativa extends Cuenta{
	private Empresa empresa;
	public CuentaCorporativa(String cvu, String alias, Usuario titular, Empresa empresa) {
		super(cvu,alias,titular);
		
		if (!empresa.estaAutorizado(titular.obtenerDni())) {
		    throw new IllegalArgumentException("El titular no esta autorizado en la empresa");
		}
		this.empresa = empresa;
	}
	public Empresa obtenerEmpresa() {
		return empresa;
	}
	
	@Override
	public String obtenerTipo() {
		return "Corporativa";
	}
}
