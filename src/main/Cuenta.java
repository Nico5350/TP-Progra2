package main;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Cuenta {

    private String cvu;
    private String alias;
    private double saldo;
    private Usuario titular;
    private ArrayList<Actividad> actividades;

    public Cuenta(String cvu, String alias, Usuario titular) {
        this.cvu = cvu;
        this.alias = alias;
        this.titular = titular;
        this.saldo = 0;
        this.actividades = new ArrayList<Actividad>();
    }

    public void depositar(double monto) {
        saldo += monto;
    }

    public void retirar(double monto) {
        saldo -= monto;
    }

    public void registrarActividad(Actividad a) {
        actividades.add(a);
    }

    public ArrayList<Actividad> obtenerActividades() {
        return actividades;
    }

    public double obtenerSaldo() {
        return saldo;
    }

    public String obtenerCvu() {
        return cvu;
    }

    public String obtenerAlias() {
        return alias;
    }

    public Usuario obtenerTitular() {
        return titular;
    }
    public void validarDeposito(double monto) {
       
    }
    
    public Inversion buscarInversion(int idInversion) {
        Iterator<Actividad> it = actividades.iterator();
        while (it.hasNext()) {
            Actividad actividad = it.next();
            if (actividad.esInversion()) {
                Inversion inversion = (Inversion) actividad;
                if (inversion.getId() == idInversion) {
                    return inversion;
                }
            }
        }
        return null;
    }

    public abstract String obtenerTipo();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(obtenerTipo());
        sb.append(": ");
        sb.append(alias);
        sb.append(" (");
        sb.append(cvu);
        sb.append(")");
        return sb.toString();
    }
}