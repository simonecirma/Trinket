package com.example.trinket.model.bean;

import java.sql.Date;

public class MetodoPagamentoBean {
    String numeroCarta;
    Date scadenza;
    String titolare;
    int cvv;

    public MetodoPagamentoBean(String numeroCarta, Date scadenza, String titolare, int cvv) {
        this.numeroCarta = numeroCarta;
        this.scadenza = scadenza;
        this.titolare = titolare;
        this.cvv = cvv;
    }

    public MetodoPagamentoBean() {}

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

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "MetodoPagamentoBean{" +
                "numeroCarta='" + numeroCarta + '\'' +
                ", scadenza=" + scadenza +
                ", titolare='" + titolare + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
