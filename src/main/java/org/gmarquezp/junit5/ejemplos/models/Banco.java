package org.gmarquezp.junit5.ejemplos.models;

import org.gmarquezp.junit5.ejemplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Banco {

    private String nombre;
    private String direccion;
    private String telefono;

    private List<Cuenta> cuentas;

    public Banco(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.cuentas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public Banco addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
        return this;
    }

    public void transferir(Cuenta cuentaOrigen, Cuenta cuentaDestino, BigDecimal monto) throws DineroInsuficienteException {
        if (cuentaOrigen.getSaldo().compareTo(monto) < 0) {
            throw new DineroInsuficienteException("No se puede retirar más dinero de lo que hay en la cuenta");
        }
        cuentaOrigen.retirar(monto);
        cuentaDestino.depositar(monto);
    }
}
