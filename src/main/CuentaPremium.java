package main;

public class CuentaPremium extends Cuenta{ 
	private static double depositoMinimo = 500000;
    public CuentaPremium(String cvu, String alias, Usuario titular,double depositoInicial) {

        super(cvu, alias, titular);
        
        if (depositoInicial < depositoMinimo) {
        	throw new IllegalArgumentException("El deposito inicial no puede ser menor a 500000");
        }
        depositar(depositoInicial);
    }
    public static double obtenerDepositoMinimo(){
    	return depositoMinimo;
    }
    
    @Override
    public String obtenerTipo() { //para clase cuenta
    	return "Premium";
    }
}