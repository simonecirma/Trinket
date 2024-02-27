package com.example.trinket.model.bean;

import java.sql.Date;

public class metodoPagamentoBean {
    String numeroCarta;
    Date scadenza;
    String titolare;

    public metodoPagamentoBean(String numeroCarta, Date scadenza, String titolare) {
        this.numeroCarta = numeroCarta;
        this.scadenza = scadenza;
        this.titolare = titolare;
    }

    public metodoPagamentoBean() {}

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public Date getScadenza() {
        return scadenza;
    }

    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }

    public String getTitolare() {
        return titolare;
    }

    public void setTitolare(String titolare) {
        this.titolare = titolare;
    }

    @Override
    public String toString() {
        return "MetodoPagamentoBean{" +
                "numeroCarta='" + numeroCarta + '\'' +
                ", scadenza=" + scadenza +
                ", titolare='" + titolare + '\'' +
                '}';
    }
}
