package com.example.trinket.model.bean;

public class CompostoBean {
    private int quantita;
    private String codSeriale;
    private int idOrdine;
    private float prezzoUnitario;

    public CompostoBean() {
        this.quantita = 0;
        this.codSeriale = "";
        this.idOrdine = 0;
        this.prezzoUnitario = 0;
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

    public float getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(float prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    @Override
    public String toString() {
        return "CompostoBean{" +
                "quantita=" + quantita +
                ", codSeriale='" + codSeriale + '\'' +
                ", idOrdine=" + idOrdine +
                ", prezzoUnitario=" + prezzoUnitario +
                '}';
    }
}
