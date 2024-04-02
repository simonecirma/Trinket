package com.example.trinket.model.bean;

import java.util.List;

public class PacchettoBean {
    private String codSeriale;
    private String nome;
    private float prezzo;
    private String descrizioneRidotta;
    private String descrizione;
    private String tipo;
    private int numGiorni;
    private int numPacchetti;
    private boolean flagDisponibilita;
    private List<ImmaginiBean> immagini;

    public PacchettoBean() {
    }

    public String getCodSeriale() {
        return codSeriale;
    }

    public void setCodSeriale(String codSeriale) {
        this.codSeriale = codSeriale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public String getDescrizioneRidotta() {
        return descrizioneRidotta;
    }

    public void setDescrizioneRidotta(String descrizioneRidotta) {
        this.descrizioneRidotta = descrizioneRidotta;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNumGiorni() {
        return numGiorni;
    }

    public void setNumGiorni(int numGiorni) {
        this.numGiorni = numGiorni;
    }

    public int getNumPacchetti() {
        return numPacchetti;
    }

    public void setNumPacchetti(int numPacchetti) {
        this.numPacchetti = numPacchetti;
    }

    public boolean isFlagDisponibilita() {
        return flagDisponibilita;
    }

    public void setFlagDisponibilita(boolean flagDisponibilita) {
        this.flagDisponibilita = flagDisponibilita;
    }

    public List<ImmaginiBean> getImmagini() {
        return immagini;
    }

    public void setImmagini(List<ImmaginiBean> immagini) {
        this.immagini = immagini;
    }

    @Override
    public String toString() {
        return "PacchettoBean{" +
                "codSeriale='" + codSeriale + '\'' +
                ", nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                ", descrizioneRidotta='" + descrizioneRidotta + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", tipo='" + tipo + '\'' +
                ", numGiorni=" + numGiorni +
                ", numPacchetti=" + numPacchetti +
                ", flagDisponibilita=" + flagDisponibilita +
                ", immagini=" + immagini +
                '}';
    }
}
