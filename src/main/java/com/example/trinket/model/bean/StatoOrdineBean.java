package com.example.trinket.model.bean;

public class StatoOrdineBean {
    String stato;

    public StatoOrdineBean(String stato) {
        this.stato = stato;
    }

    public StatoOrdineBean() {}

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "StatoOrdineBean{" +
                "stato='" + stato + '\'' +
                '}';
    }
}
