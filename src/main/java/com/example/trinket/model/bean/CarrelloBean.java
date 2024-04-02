package com.example.trinket.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarrelloBean implements Serializable {
    private transient List<PacchettoBean> pacchetti;
    private List<Integer> quantita;

    public CarrelloBean(List<PacchettoBean> pacchetti, List<Integer> quantita) {
        this.pacchetti = pacchetti;
        this.quantita = quantita;
    }

    public CarrelloBean() {
        pacchetti = new ArrayList<>();
        quantita = new ArrayList<>();
    }

    public List<PacchettoBean> getPacchetti() {
        return pacchetti;
    }

    public void setPacchetti(List<PacchettoBean> pacchetti) {
        this.pacchetti = pacchetti;
    }

    public List<Integer> getQuantita() {
        return quantita;
    }

    public void setQuantita(List<Integer> quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "CarrelloBean{" +
                "pacchetti=" + pacchetti +
                ", quantita=" + quantita +
                '}';
    }
}
