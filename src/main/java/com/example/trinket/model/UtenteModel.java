package com.example.trinket.model;

import com.example.trinket.model.bean.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteModel {
    private static final Logger logger = Logger.getLogger(UtenteModel.class.getName());
    private static final String TABLE_NAME_INDIRIZZO = "IndirizzoSpedizione";
    private static final String TABLE_NAME_INSERISCE = "Inserisce";
    private static final String TABLE_NAME_PAGAMENTO = "MetodoDiPagamento";
    private static final String TABLE_NAME_POSSIEDE = "Possiede";
    private static final String TABLE_NAME_UTENTE = "Utente";

    private static final String INSERT_INTO = "INSERT INTO ";





    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/trinket");
        } catch (NamingException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public List<IndirizzoBean> ricercaIndirizzi (String email){
        List<IndirizzoBean> indirizzi = new ArrayList<>();
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT i.IDIndirizzo, i.NomeCitofono, i.Indirizzo, i.NumeroCivico, i.CAP, i.Città," +
                    " i.Provincia FROM " +TABLE_NAME_INDIRIZZO+ " i, " +TABLE_NAME_INSERISCE+
                    " n WHERE n.Email = ? AND i.IDIndirizzo=n.IDIndirizzo")) {
            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    IndirizzoBean bean = new IndirizzoBean();
                    bean.setIdIndirizzo(rs.getInt("IDIndirizzo"));
                    bean.setNome(rs.getString("NomeCitofono"));
                    bean.setIndirizzo(rs.getString("Indirizzo"));
                    bean.setNumeroCivico(rs.getInt("NumeroCivico"));
                    bean.setCap(rs.getInt("CAP"));
                    bean.setCitta(rs.getString("Città"));
                    bean.setProvincia(rs.getString("Provincia"));
                    indirizzi.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return indirizzi;
    }

    public void rimuoviIndirizzo(int idIndirizzo){
        try(
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM " +TABLE_NAME_INDIRIZZO+ " WHERE IDIndirizzo=?")) {
            ps.setInt(1, idIndirizzo);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void aggiungiIndirizzo (String nome, String indirizzo, int numeroCivico, int cap, String provincia, String comune){
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement( INSERT_INTO  + TABLE_NAME_INDIRIZZO + "(NomeCitofono, Indirizzo, NumeroCivico, CAP, Città, Provincia) " +
            "VALUES(?, ?, ?, ?, ?, ?)")){
            ps.setString(1, nome);
            ps.setString(2, indirizzo);
            ps.setInt(3, numeroCivico);
            ps.setInt(4, cap);
            ps.setString(5, comune);
            ps.setString(6, provincia);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public int getLastID (){
        int i = 0;
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT MAX(IDIndirizzo) AS Max FROM " +TABLE_NAME_INDIRIZZO)){
            try(ResultSet rs = ps.executeQuery()){
                rs.next();
                i = rs.getInt("Max");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return i;
    }

    public void inserisciInserisce(String email){
        int i = getLastID();
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_INTO + TABLE_NAME_INSERISCE + "(IDIndirizzo, Email) " +
            "VALUES(?, ?)")){

            ps.setInt(1, i);
            ps.setString(2, email);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public List<MetodoPagamentoBean> ricercaMetodoPagamento (String email){
        List<MetodoPagamentoBean> carte = new ArrayList<>();
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT p.NumeroCarta, m.Scadenza, m.Titolare, m.Cvv FROM "
            +TABLE_NAME_PAGAMENTO+ " m," +TABLE_NAME_POSSIEDE+ " p  WHERE p.NumeroCarta = m.NumeroCarta " +
            "AND p.Email = ?")) {
            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    MetodoPagamentoBean bean = new MetodoPagamentoBean();
                    bean.setNumeroCarta(rs.getString("NumeroCarta"));
                    bean.setScadenza(rs.getDate("Scadenza"));
                    bean.setTitolare(rs.getString("Titolare"));
                    bean.setCvv(rs.getInt("Cvv"));
                    carte.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return carte;
    }

    public void rimuoviMetodoDiPagamento (String numeroCarta){
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM " +TABLE_NAME_PAGAMENTO+ " WHERE NumeroCarta=?")) {
            ps.setString(1, numeroCarta);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void aggiungiMetodoDiPagamento (String numeroCarta, String intestatario, Date dataDiScadenza, int cvv, String email){
        try(
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(INSERT_INTO + TABLE_NAME_PAGAMENTO + "(NumeroCarta, Scadenza, Titolare, Cvv) " +
                "VALUES(?, ?, ?, ?)");
                PreparedStatement ps2 = con.prepareStatement(INSERT_INTO + TABLE_NAME_POSSIEDE + "(NumeroCarta, Email) " +
                "VALUES(?, ?)")) {
            ps.setString(1, numeroCarta);
            ps.setDate(2, dataDiScadenza);
            ps.setString(3, intestatario);
            ps.setInt(4, cvv);
            ps2.setString(1, numeroCarta);
            ps2.setString(2, email);
            ps.executeUpdate();
            ps2.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void inserisciImmagine (String immagine, String email){
        try (
             Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement
             ("UPDATE " + TABLE_NAME_UTENTE + " SET Immagine = ? WHERE Email = ?")){
                ps.setString(1, immagine);
                ps.setString(2, email);
                ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void modificaInformazioni (String nome, String cognome, String email, String password, Date dataDiNascita, String email2){
        try(
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE " +TABLE_NAME_UTENTE+ " SET Nome = ?, Cognome = ?," +
                " Email = ?, Password = ?, DataDiNascita = ? WHERE Email = ? ")) {
            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setDate(5, dataDiNascita);
            ps.setString(6, email2);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public List<UtenteBean> getUtenti (){
        List<UtenteBean> utenti = new ArrayList<>();
        try(
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM " +TABLE_NAME_UTENTE+ " WHERE FlagAmm = 0")) {
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    UtenteBean bean = new UtenteBean();
                    bean.setEmail(rs.getString("Email"));
                    bean.setNome(rs.getString("Nome"));
                    bean.setCognome(rs.getString("Cognome"));
                    bean.setDataDiNascita(rs.getDate("DataDiNascita"));
                    utenti.add(bean);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utenti;
    }
}

