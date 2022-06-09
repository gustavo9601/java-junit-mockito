package org.gmarquezp.junit5.ejemplos.models;

import org.gmarquezp.junit5.ejemplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;
import java.util.Objects;

public class Cuenta {

    private String id;
    private String nombre;
    private String numeroDeCuenta;
    private BigDecimal saldo;

    private Banco banco;

    private boolean estaActiva;

    public Cuenta(String nombre, String numeroDeCuenta, BigDecimal saldo) {
        this.nombre = nombre;
        this.numeroDeCuenta = numeroDeCuenta;
        this.saldo = saldo;
        this.estaActiva = true;
        this.id = null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public void setNumeroDeCuenta(String numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public boolean getEstaActiva() {
        return estaActiva;
    }

    public void setEstaActiva(boolean estaActiva) {
        this.estaActiva = estaActiva;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public boolean isEstaActiva() {
        return estaActiva;
    }

    public void depositar(BigDecimal monto) {
        if (this.estaActiva) {
            this.saldo = this.saldo.add(monto);
        }
    }

    public void retirar(BigDecimal monto) throws DineroInsuficienteException {
        if (this.estaActiva) {
            if(this.saldo.compareTo(monto) >= 0) {
                this.saldo = this.saldo.subtract(monto);
            }else{
                throw new DineroInsuficienteException("No se puede retirar más dinero de lo que hay en la cuenta");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuenta cuenta)) return false;
        return Objects.equals(this.getId(), cuenta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
