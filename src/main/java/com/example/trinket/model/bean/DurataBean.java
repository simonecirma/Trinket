package com.example.trinket.model.bean;

public class DurataBean {
    private int numGiorni;

    public DurataBean(int numGiorni) {
        this.numGiorni = numGiorni;
    }

    public DurataBean() {
    }

    public int getNumGiorni() {
        return numGiorni;
    }

    public void setNumGiorni(int numGiorni) {
        this.numGiorni = numGiorni;
    }

    @Override
    public String toString() {
        return "durataBean{" +
                "numGiorni=" + numGiorni +
                '}';
    }
}
