package com.example.trinket.model.bean;

public class CompostoBean {
    private int quantita;
    private String codSeriale;
    private int idOrdine;

    public CompostoBean(int quantita, String codSeriale, int idOrdine) {
        this.quantita = quantita;
        this.codSeriale = codSeriale;
        this.idOrdine = idOrdine;
    }

    public CompostoBean() {
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getCodSeriale() {
        return codSeriale;
    }

    public void setCodSeriale(String codSeriale) {
        this.codSeriale = codSeriale;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    @Override
    public String toString() {
        return "CompostoBean{" +
                "quantita=" + quantita +
                ", codSeriale='" + codSeriale + '\'' +
                ", idOrdine=" + idOrdine +
                '}';
    }
}
