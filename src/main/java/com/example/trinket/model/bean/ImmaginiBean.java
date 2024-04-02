package com.example.trinket.model.bean;

public class ImmaginiBean {
    private String nome;
    private String codice;
    private boolean flagCopertina;

    public ImmaginiBean() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public boolean isFlagCopertina() {
        return flagCopertina;
    }

    public void setFlagCopertina(boolean flagCopertina) {
        this.flagCopertina = flagCopertina;
    }

    @Override
    public String toString() {
        return "ImmaginiBean{" +
                "nome='" + nome + '\'' +
                ", codice='" + codice + '\'' +
                ", flagCopertina=" + flagCopertina +
                '}';
    }
}
