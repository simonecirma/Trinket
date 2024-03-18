package com.example.trinket.model.bean;

import java.util.List;

public class CarrelloBean {
    private List<PacchettoBean> pacchetti;
    private List<Integer> quantita;

    public CarrelloBean(List<PacchettoBean> pacchetti, List<Integer> quantita) {
        this.pacchetti = pacchetti;
        this.quantita = quantita;
    }

    public CarrelloBean() {
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
