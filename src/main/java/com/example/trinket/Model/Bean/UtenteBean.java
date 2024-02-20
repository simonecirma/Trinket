package com.example.trinket.Model.Bean;

import java.util.Date;

public class UtenteBean {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Date dataDiNascita;
    private String immagine;
    private boolean flagAmm;

    public UtenteBean(String nome, String cognome, String email, String password, Date dataDiNascita, String immagine, boolean flagAmm) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.dataDiNascita = dataDiNascita;
        this.immagine = immagine;
        this.flagAmm = flagAmm;
    }

    public UtenteBean() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public boolean isFlagAmm() {
        return flagAmm;
    }

    public void setFlagAmm(boolean flagAmm) {
        this.flagAmm = flagAmm;
    }

    @Override
    public String toString() {
        return "utenteBean{" +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", immagine='" + immagine + '\'' +
                ", flagAmm=" + flagAmm +
                '}';
    }
}

