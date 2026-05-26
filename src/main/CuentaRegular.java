package main;

public class CuentaRegular extends Cuenta {         //creo un sistema de herencia para las tres tipos de cuentas, siguiendo el formato presentado en el esquema de la primer entrega

    public CuentaRegular(String cvu, String alias, Usuario titular) {

        super(cvu, alias, titular);
    }
}