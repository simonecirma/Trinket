package com.example.trinket.model.bean;

public class TipologiaBean {
    private String tipologia;

    public TipologiaBean(String tipologia) {
        this.tipologia = tipologia;
    }

    public TipologiaBean() {
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public String toString() {
        return "TipologiaBean{" +
                "tipologia='" + tipologia + '\'' +
                '}';
    }
}
