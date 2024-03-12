package com.example.trinket.model.bean;

import java.sql.Date;
import java.util.List;

public class OrdineBean {
    private int idOrdine;
    private Date dataAcquisto;
    private String fattura;
    private float prezzoTotale;
    private String statoOrdine;
    private List<PacchettoBean> pacchetti;
    private List<Integer> quantitaPacchetto;

    public OrdineBean(int idOrdine, Date dataAcquisto, String fattura, float prezzoTotale, String statoOrdine, String email, List<PacchettoBean> pacchetti, List<Integer> quantitaPacchetto) {
        this.idOrdine = idOrdine;
        this.dataAcquisto = dataAcquisto;
        this.fattura = fattura;
        this.prezzoTotale = prezzoTotale;
        this.statoOrdine = statoOrdine;
        this.pacchetti = pacchetti;
        this.quantitaPacchetto = quantitaPacchetto;
    }

    public OrdineBean() {}

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

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

    public List<PacchettoBean> getPacchetti() {
        return pacchetti;
    }

    public void setPacchetti(List<PacchettoBean> pacchetti) {
        this.pacchetti = pacchetti;
    }

    public List<Integer> getQuantitaPacchetto() {
        return quantitaPacchetto;
    }

    public void setQuantitaPacchetto(List<Integer> quantitaPacchetto) {
        this.quantitaPacchetto = quantitaPacchetto;
    }

    @Override
    public String toString() {
        return "OrdineBean{" +
                "idOrdine=" + idOrdine +
                "dataAcquisto=" + dataAcquisto +
                ", fattura='" + fattura + '\'' +
                ", prezzoTotale=" + prezzoTotale +
                ", statoOrdine='" + statoOrdine + '\'' +
                ", pacchetti='" + pacchetti + '\'' +
                ", QuantitaPacchetti='" + quantitaPacchetto + '\'' +
                '}';
    }
}
