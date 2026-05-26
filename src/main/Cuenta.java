package main;
import java.util.ArrayList;

public class Cuenta {

    private String cvu;
    private String alias;
    private double saldo;

    private Usuario titular;

    private ArrayList<String> historialMovimientos;

    public Cuenta(String cvu, String alias, Usuario titular) {

        this.cvu = cvu;
        this.alias = alias;
        this.titular = titular;

        saldo = 0;

        historialMovimientos = new ArrayList<>();
    }

    public String getCvu() {
        return cvu;
    }

    public String getAlias() {
        return alias;
    }

    public double getSaldo() {
        return saldo;
    }

    public Usuario getTitular() {
        return titular;
    }

    public ArrayList<String> getHistorialMovimientos() {
        return historialMovimientos;
    }

    public void depositar(double monto) {

        saldo += monto;

        historialMovimientos.add(
                "Depósito de $" + monto
        );
    }

    public void retirar(double monto) {

        saldo -= monto;

        historialMovimientos.add(
                "Retiro de $" + monto
        );
    }

    @Override
    public String toString() {

        return "Cuenta{" +
                "cvu='" + cvu + '\'' +
                ", alias='" + alias + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}