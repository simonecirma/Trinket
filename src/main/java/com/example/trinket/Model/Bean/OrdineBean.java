package com.example.trinket.Model.Bean;

import java.sql.Date;

public class OrdineBean {
    Date dataAcquisto;
    String fattura;
    float prezzoTotale;
    String statoOrdine;
    String email;

    public OrdineBean(Date dataAcquisto, String fattura, float prezzoTotale, String statoOrdine, String email) {
        this.dataAcquisto = dataAcquisto;
        this.fattura = fattura;
        this.prezzoTotale = prezzoTotale;
        this.statoOrdine = statoOrdine;
        this.email = email;
    }

    public OrdineBean() {}

    public Date getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(Date dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    public String getFattura() {
        return fattura;
    }

    public void setFattura(String fattura) {
        this.fattura = fattura;
    }

    public float getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(float prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

    public String getStatoOrdine() {
        return statoOrdine;
    }

    public void setStatoOrdine(String statoOrdine) {
        this.statoOrdine = statoOrdine;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "OrdineBean{" +
                "dataAcquisto=" + dataAcquisto +
                ", fattura='" + fattura + '\'' +
                ", prezzoTotale=" + prezzoTotale +
                ", statoOrdine='" + statoOrdine + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
