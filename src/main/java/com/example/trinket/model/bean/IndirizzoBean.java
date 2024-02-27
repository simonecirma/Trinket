package com.example.trinket.model.bean;

public class IndirizzoBean {
    String indirizzo;
    int numeroCivico;
    int cap;
    String citta;
    String provincia;

    public IndirizzoBean(String indirizzo, int numeroCivico, int cap, String citta, String provincia) {
        this.indirizzo = indirizzo;
        this.numeroCivico = numeroCivico;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
    }

    public IndirizzoBean() {
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(int numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "IndirizzoBean{" +
                "indirizzo='" + indirizzo + '\'' +
                ", numeroCivico=" + numeroCivico +
                ", cap=" + cap +
                ", citta='" + citta + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }
}
