package main;

public class CuentaRegular extends Cuenta{     
	private static double saldoMaximo = 5000000; // saldo maximo de cuenta regular
    
	public CuentaRegular(String cvu, String alias, Usuario titular) {
        super(cvu, alias, titular);
    }
	
	public static double obtenerSaldoMaximo() { //para que billetera pueda validar el saldo 
		return saldoMaximo;
	}
	@Override
	public void validarDeposito(double monto) {
	    if (obtenerSaldo() + monto > saldoMaximo)
	        throw new IllegalStateException("La transferencia supera el saldo maximo de la cuenta regular.");
	}
	@Override
	public String obtenerTipo() { //para clase cuenta
		return "Regular";
	}
}