package com.example.trinket.model.bean;

public class IndirizzoBean {
    private int idIndirizzo;
    private String nome;
    private String indirizzo;
    private int numeroCivico;
    private int cap;
    private String citta;
    private String provincia;

    public IndirizzoBean(int idIndirizzo, String nome, String indirizzo, int numeroCivico, int cap, String citta, String provincia) {
        this.idIndirizzo = idIndirizzo;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.numeroCivico = numeroCivico;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
    }

    public IndirizzoBean() {
    }

    public int getIdIndirizzo() {
        return idIndirizzo;
    }

    public void setIdIndirizzo(int idIndirizzo) {
        this.idIndirizzo = idIndirizzo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
                "idIndirizzo='" + idIndirizzo + '\'' +
                "nome='" + nome + '\'' +
                "indirizzo='" + indirizzo + '\'' +
                ", numeroCivico=" + numeroCivico +
                ", cap=" + cap +
                ", citta='" + citta + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }
}
