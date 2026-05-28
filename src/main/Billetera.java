package main;

import java.util.*;

public class Billetera implements IBilletera {
	private HashMap<String,Empresa>empresas=new HashMap<>();
	private HashMap<String,Usuario> usuarios;   //Mapa de dnis
	private HashMap<String,Cuenta> cuentas;		//mapa de cvus
	private HashMap<String,String> aliasCvu;	//mapa de alias
	private ArrayList<Actividad> actividades;	//lista global de actividades
	private int contadorIdInversion;			//incrementa cada vez que hay una inverison
	
	public Billetera() {
	    this.usuarios = new HashMap<String, Usuario>();
	    this.cuentas = new HashMap<String, Cuenta>();
	    this.aliasCvu = new HashMap<String, String>();
	    this.actividades = new ArrayList<Actividad>();
	    this.contadorIdInversion = 1;
	}
	
	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email,
			String nombreContacto) {
		if(empresas.containsKey(cuit))
			throw new RuntimeException("La empresa ya esta registrada.");
		empresas.put(cuit,new Empresa(cuit, nombreFantasia, telefono, email, nombreContacto));

	}

	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
		if(!empresas.containsKey(cuitEmpresa)) { //se fija si la empresa exite
			throw new IllegalArgumentException("La empresa no existe");
		}
		Empresa empresa = empresas.get(cuitEmpresa); 	//trae empresa
		if(empresa.estaAutorizado(dniAutorizado)) {		//es nuevo el dni?
			throw new IllegalArgumentException("Usuario ya autorizado");
		}
		empresa.agregarAutorizado(dniAutorizado);		//agrega dni a la lista
	}

	@Override
	public void registrarUsuario(String dni, String nombre, String telefono, String email) {
		if(usuarios.containsKey(dni)) {
			throw new IllegalArgumentException("El usuario ya esta registrado"); //mira si el usuario ya esta registrado
		}
		if(dni == null || nombre == null || telefono == null || email == null) {
			throw new IllegalArgumentException("Datos invalidos"); // valida que ninguno sea nulo
		}
		usuarios.put(dni, new Usuario(dni, nombre, telefono, email)); //crea el nuevo usuario y lo agrega

	}

	@Override
	public String crearCuentaRegular(String dniUsuario, String alias) {
		if(!usuarios.containsKey(dniUsuario)) {	//mira si el usuario existe
			throw new IllegalArgumentException("El usuario no existe");
		}
		if(aliasCvu.containsKey(alias)) {		//mira si el alias ya esta
			throw new IllegalArgumentException("El alias ya esta registrado");
		}
		String cvu = Utilitarios.generarSiguienteCvu(); //generea otro cvu
		Usuario titular = usuarios.get(dniUsuario);
		CuentaRegular cuenta = new CuentaRegular(cvu, alias, titular); //crea la nueva cuenta regular 
		//carga todo
		cuentas.put(cvu, cuenta);
		aliasCvu.put(alias, cvu);
		titular.agregarCuentas(cuenta);
		
		return cvu;
	}

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
		if(!usuarios.containsKey(dniUsuario)) {		//mira si el usuario exist
			throw new IllegalArgumentException("El usuario no existe");
		}
		if(aliasCvu.containsKey(alias)) {			//mira si esta el alias 
			throw new IllegalArgumentException("El alias ya esta registrado");
		}
		//Aca no valida el deposito lo hace en la clase cuentaPremium
		String cvu = Utilitarios.generarSiguienteCvu();
		Usuario titular = usuarios.get(dniUsuario);
		CuentaPremium cuenta = new CuentaPremium(cvu, alias, titular, depositoInicial);
		
		//agrega todo
		cuentas.put(cvu, cuenta);
		aliasCvu.put(alias, cvu);
		titular.agregarCuentas(cuenta);
		
		return cvu;
	}

	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		if(!usuarios.containsKey(dniUsuario)) {		
			throw new IllegalArgumentException("El usuario no existe");
		}
		if(aliasCvu.containsKey(alias)) {			 
			throw new IllegalArgumentException("El alias ya esta registrado");
		}
		if(!empresas.containsKey(cuitEmpresa)) {	//mira si la empresa esta		 
			throw new IllegalArgumentException("La empresa no existe");
		}
		
		Usuario titular = usuarios.get(dniUsuario);
		Empresa empresa = empresas.get(cuitEmpresa);
		
		String cvu = Utilitarios.generarSiguienteCvu();
		CuentaCorporativa cuenta = new CuentaCorporativa(cvu, alias, titular, empresa);
		
		//agrega todo
		cuentas.put(cvu, cuenta);
		aliasCvu.put(alias, cvu);
		titular.agregarCuentas(cuenta);
				
		return null;
	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		// TODO Auto-generated method stub

	}

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		// TODO Auto-generated method stub

	}

	@Override
	public String consultarCvu(String alias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialGlobal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {
		// TODO Auto-generated method stub
		return null;
	}
}