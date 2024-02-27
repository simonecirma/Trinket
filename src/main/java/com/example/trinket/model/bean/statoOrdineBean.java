package com.example.trinket.model.bean;

public class statoOrdineBean {
    String stato;

    public statoOrdineBean(String stato) {
        this.stato = stato;
    }

    public statoOrdineBean() {}

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
