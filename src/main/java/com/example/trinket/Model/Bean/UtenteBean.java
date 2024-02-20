package com.example.trinket.Model.Bean;

import java.util.Date;

public class UtenteBean {
    int id;
    String nome;
    String cognome;
    String email;
    String password;
    String indirizzo;
    int numeroCivico;
    int cap;
    String citta;
    String provincia;
    Date dataDiNascita;
    String numeroTelefono;
    String immagine;
    boolean flagAmm;

    public UtenteBean(int id, String nome, String cognome, String email, String password, String indirizzo, int numeroCivico, int cap, String citta, String provincia, Date dataDiNascita, String numeroTelefono, String immagine, boolean flagAmm) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.indirizzo = indirizzo;
        this.numeroCivico = numeroCivico;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.dataDiNascita = dataDiNascita;
        this.numeroTelefono = numeroTelefono;
        this.immagine = immagine;
        this.flagAmm = flagAmm;
    }

    public UtenteBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(int numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
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
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", numreoCivico='" + numeroCivico + '\'' +
                ", cap=" + cap +
                ", città='" + citta + '\'' +
                ", provincia='" + provincia + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                ", immagine='" + immagine + '\'' +
                ", flagAmm=" + flagAmm +
                '}';
    }
}

