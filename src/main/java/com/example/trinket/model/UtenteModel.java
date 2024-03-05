package com.example.trinket.model;

import com.example.trinket.model.bean.IndirizzoBean;
import com.example.trinket.model.bean.MetodoPagamentoBean;
import com.example.trinket.model.bean.OrdineBean;

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
    private static final String TABLE_NAME_ORDINE = "Ordine";
    private static final String TABLE_NAME_UTENTE = "Utente";




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
            PreparedStatement ps = con.prepareStatement("SELECT i.Indirizzo, i.NumeroCivico, i.CAP, i.Città," +
                    " i.Provincia FROM " +TABLE_NAME_INDIRIZZO+ " i, " +TABLE_NAME_INSERISCE+
                    " n WHERE n.Email = ? AND i.IDIndirizzo=n.IDIndirizzo")) {
            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    IndirizzoBean bean = new IndirizzoBean();
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

    public List<MetodoPagamentoBean> ricercaMetodoPagamento (String email){
        List<MetodoPagamentoBean> carte = new ArrayList<>();
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT p.NumeroCarta, m.Scadenza, m.Titolare FROM " +
            "" +TABLE_NAME_PAGAMENTO+ " m," +TABLE_NAME_POSSIEDE+ " p  WHERE p.NumeroCarta = m.NumeroCarta " +
            "AND m.Email = ?")) {
            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    MetodoPagamentoBean bean = new MetodoPagamentoBean();
                    bean.setNumeroCarta(rs.getString("NumeroCarta"));
                    bean.setScadenza(rs.getDate("Scadenza"));
                    bean.setTitolare(rs.getString("Titolare"));
                    carte.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return carte;
    }

    public List<OrdineBean> ricercaOrdiniUtente (String email){
        List<OrdineBean> ordini = new ArrayList<>();
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM " +TABLE_NAME_ORDINE+
            " WHERE Email = ?")) {
            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    OrdineBean bean = new OrdineBean();
                    bean.setDataAcquisto(rs.getDate("DataAcquisto"));
                    bean.setFattura(rs.getString("Fattura"));
                    bean.setPrezzoTotale(rs.getFloat("PrezzoTotale"));
                    bean.setStatoOrdine(rs.getString("StatoOrdine"));
                    ordini.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return ordini;
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
        List<OrdineBean> ordini = new ArrayList<>();
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
}

