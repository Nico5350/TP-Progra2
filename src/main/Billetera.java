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
				
		return cvu;
	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) {
		if(!usuarios.containsKey(dniUsuario)) { 	//verfica usuario
			throw new IllegalArgumentException("El usuario no existe.");
		}
		List<String> resultado = new ArrayList<String>();
		Usuario usuario = usuarios.get(dniUsuario);
		
		for(Cuenta cuenta : usuario.obtenerCuentas()) { //usa foreach para recorrer cuentas
			resultado.add(cuenta.toString());
		}
		return resultado;
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) {
		if(!cuentas.containsKey(cvu)) {			//verifica si existe la cuenta
			throw new IllegalArgumentException("La cuenta no existe");
		}
		return cuentas.get(cvu).obtenerSaldo();	//obtienie el saldo
	}

	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		//validaciones
		if (!cuentas.containsKey(cvuOrigen))
	        throw new IllegalArgumentException("La cuenta origen no existe");
	    if (!cuentas.containsKey(cvuDestino))
	        throw new IllegalArgumentException("La cuenta destino no existe");
	    
	    Cuenta origen = cuentas.get(cvuOrigen);
	    Cuenta destino = cuentas.get(cvuDestino);
	    
	    if (origen.obtenerSaldo() < monto)
	        throw new IllegalStateException("Saldo insuficiente.");

	    origen.retirar(monto);
	    destino.validarDeposito(monto);
	    destino.depositar(monto);
	    
	    String dniOrigen = origen.obtenerTitular().obtenerDni();
	    String dniDestino = destino.obtenerTitular().obtenerDni();

	    Transferencia transferencia = new Transferencia(monto, true, dniOrigen, cvuOrigen, dniDestino, cvuDestino);
	    
	    origen.registrarActividad(transferencia);
	    destino.registrarActividad(transferencia);
	    actividades.add(transferencia);
	}

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		if (!usuarios.containsKey(dni))
	        throw new IllegalArgumentException("El usuario no existe");
	    if (!cuentas.containsKey(cvu))
	        throw new IllegalArgumentException("La cuenta no exist");

	    Cuenta cuenta = cuentas.get(cvu);
	    //valida saldo
	    if (cuenta.obtenerSaldo() < monto)
	        throw new IllegalArgumentException("Saldo insuficiente");
	    //crea nueva inversion
	    int id = contadorIdInversion++;
	    InversionRentaFija inversion = new InversionRentaFija(id, dni, cvu, monto, plazoDias, 0.20);

	    cuenta.retirar(monto);
	    usuarios.get(dni).agregarInvertido(monto);
	    cuenta.registrarActividad(inversion);
	    actividades.add(inversion);

	    return id;
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		//validaciones
		 if (!usuarios.containsKey(dni))
		        throw new IllegalArgumentException("El usuario no existe.");
		 if (!cuentas.containsKey(cvu))
		        throw new IllegalArgumentException("La cuenta no existe.");

		    Cuenta cuenta = cuentas.get(cvu);
		    //valida saldo 
		if (cuenta.obtenerSaldo() < monto)
		        throw new IllegalArgumentException("Saldo insuficiente.");
			//crea nueva inversion de divisa con los paramtros correspondientes
		    int id = contadorIdInversion++;
		    InversionDivisa inversion = new InversionDivisa(id, dni, cvu, monto, plazoDias, divisa, tasa);

		    cuenta.retirar(monto);
		    usuarios.get(dni).agregarInvertido(monto);
		    cuenta.registrarActividad(inversion);
		    actividades.add(inversion);

		    return id;
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		//validaciones de cuenta y usuario
		if (!usuarios.containsKey(dni)) {
	        throw new IllegalArgumentException("El usuario no existe");
		}
		if (!cuentas.containsKey(cvu)) {
	        throw new IllegalArgumentException("La cuenta no existe");
		}
	    Cuenta cuenta = cuentas.get(cvu);
	    //valida tipo de cuenta y saldo o monto de inversion
	    if (!(cuenta instanceof CuentaCorporativa)) {
	        throw new IllegalArgumentException("Solo se puede invertir en liquidez desde cuentas corporativas");
	    }
	    if (monto < FondoLiquidezEmpresarial.obtenerMontoMinimo()) {
	        throw new IllegalArgumentException("El monto minimo para invertir en liquidez es de 20 millones");
	    }
	    if (cuenta.obtenerSaldo() < monto) {
	        throw new IllegalArgumentException("Saldo insuficiente");
	    }
	    //crea inversion
	    int id = contadorIdInversion++;
	    FondoLiquidezEmpresarial inversion = new FondoLiquidezEmpresarial(id, dni, cvu, monto, plazoDias);

	    cuenta.retirar(monto);
	    usuarios.get(dni).agregarInvertido(monto);
	    cuenta.registrarActividad(inversion);
	    actividades.add(inversion);

	    return id;
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		//validaciones de cuenta y usuario
		if (!usuarios.containsKey(dni)) {
	        throw new IllegalArgumentException("El usuario no existe");
		}
		if (!cuentas.containsKey(cvu)) {
	        throw new IllegalArgumentException("La cuenta no existe");
		}
	    Cuenta cuenta = cuentas.get(cvu);
	    Inversion inversionEncontrada = null;
	    //recorre con foreach hasta encontrar la inversion
	    for (Actividad actividad : cuenta.obtenerActividades()) {
	        if (actividad.esInversion()) {
	            Inversion inversion = (Inversion) actividad;
	            if (inversion.getId() == idInversion) {
	                inversionEncontrada = inversion;
	                break;
	            }
	        }
	    }

	    if (inversionEncontrada == null)
	        throw new IllegalArgumentException("La inversion no existe");{
	        }
	    if (!inversionEncontrada.estaActiva()) {
	        throw new IllegalArgumentException("La inversion ya fue cancelada");
	    }
	    if (!inversionEncontrada.esPrecancelable()) {
	        throw new IllegalArgumentException("El fondo de liquidez empresarial no es precancelable");
	    }
	    double rendimiento = inversionEncontrada.calcularRendimiento();
	    double montoADevolver = inversionEncontrada.calcularDevolucion();

	    cuenta.depositar(montoADevolver);
	    usuarios.get(dni).restarInvertido(inversionEncontrada.getMonto());
	    inversionEncontrada.cancelar();

	}

	@Override
	public String consultarCvu(String alias) {
		if(!aliasCvu.containsKey(alias)) {
			throw new IllegalArgumentException("El alias no esta registrado");
		}
		return aliasCvu.get(alias); //busca en el map y devuelve el cvu
	}

	@Override
	public List<String> consultarHistorialGlobal() {
		List<String> resultado = new ArrayList<String>();
		//recorre las actividades y devuelven
		for(Actividad actividad : actividades) {
			resultado.add(actividad.describir());
		}
		return resultado;
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		//valida la cuenta si existe
		if(!cuentas.containsKey(cvu)) {
			throw new IllegalArgumentException("La cuenta no existe");
		}
		List<String> resultado = new ArrayList<String>();
		Cuenta cuenta = cuentas.get(cvu);
		//recorre la actividad de una cuenta
		for(Actividad actividad : cuenta.obtenerActividades()) {
			resultado.add(actividad.describir());
		}
		return resultado;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
		//valida cuenta
		if (!usuarios.containsKey(dniUsuario))
	        throw new IllegalArgumentException("El usuario no existe.");
	    
	    List<String> resultado = new ArrayList<String>();
	    Usuario usuario = usuarios.get(dniUsuario);
	    //recorre actividad de usuario
	    for (Cuenta cuenta : usuario.obtenerCuentas()) {
	        for (Actividad actividad : cuenta.obtenerActividades()) {
	            resultado.add(actividad.describir());
	        }
	    }
	    
	    return resultado;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {
		if(!usuarios.containsKey(dniUsuario)) {
			throw new IllegalArgumentException("El usuario no existe.");

		}
		return usuarios.get(dniUsuario).obtenerTotalInvertido();
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {
	    //valida
		if (cantidadTop <= 0)
	        throw new IllegalArgumentException("La cantidad debe ser positiva");

	    List<Cuenta> listaCuentas = new ArrayList<Cuenta>(cuentas.values());
	    //recorre con burbujeo y ordena
	    for (int i = 0; i < listaCuentas.size() - 1; i++) {
	        for (int j = i + 1; j < listaCuentas.size(); j++) {
	            if (listaCuentas.get(i).obtenerActividades().size() < 
	                listaCuentas.get(j).obtenerActividades().size()) {
	                Cuenta temp = listaCuentas.get(i);
	                listaCuentas.set(i, listaCuentas.get(j));
	                listaCuentas.set(j, temp);
	            }
	        }
	    }

	    List<String> resultado = new ArrayList<String>();
	    for (int i = 0; i < cantidadTop && i < listaCuentas.size(); i++) {
	        resultado.add(listaCuentas.get(i).toString());
	    }
	    //devuelce las cuentas con mayor volumen
	    return resultado;
	}
	//estado del sistema
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("=== BILLETERA ===\n");
	    
	    sb.append("\n-- USUARIOS --\n");
	    for (Usuario usuario : usuarios.values()) {
	        sb.append(usuario.toString()).append("\n");
	    }
	    
	    sb.append("\n-- EMPRESAS --\n");
	    for (Empresa empresa : empresas.values()) {
	        sb.append(empresa.toString()).append("\n");
	    }
	    
	    sb.append("\n-- CUENTAS --\n");
	    for (Cuenta cuenta : cuentas.values()) {
	        sb.append(cuenta.toString()).append("\n");
	    }
	    
	    sb.append("\n-- ACTIVIDADES --\n");
	    for (Actividad actividad : actividades) {
	        sb.append(actividad.describir()).append("\n");
	    }
	    
	    return sb.toString();
	}
}