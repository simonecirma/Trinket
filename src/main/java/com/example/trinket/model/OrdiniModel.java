package com.example.trinket.model;

import com.example.trinket.model.bean.CompostoBean;
import com.example.trinket.model.bean.ImmaginiBean;
import com.example.trinket.model.bean.OrdineBean;
import com.example.trinket.model.bean.PacchettoBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdiniModel {
    private static final Logger logger = Logger.getLogger(OrdiniModel.class.getName());

    private static final String TABLE_NAME_ORDINE = "Ordine";
    private static final String TABLE_NAME_COMPOSTO = "Composto";
    private static final String SELECT_FROM = "SELECT * FROM ";
    private static final String ID_ORDINE = "IdOrdine";
    private static final String DATA_ACQUISTO = "DataAcquisto";
    private static final String FATTURA = "Fattura";
    private static final String PREZZO_TOTALE = "PrezzoTotale";
    private static final String STATO_ORDINE = "StatoOrdine";





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

    public List<OrdineBean> ricercaOrdiniUtente(String email) {
        List<OrdineBean> ordini = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_ORDINE +
                        " WHERE Email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdineBean bean = new OrdineBean();
                    bean.setIdOrdine(rs.getInt(ID_ORDINE));
                    bean.setDataAcquisto(rs.getDate(DATA_ACQUISTO));
                    bean.setFattura(rs.getString(FATTURA));
                    bean.setPrezzoTotale(rs.getFloat(PREZZO_TOTALE));
                    bean.setStatoOrdine(rs.getString(STATO_ORDINE));
                    ordini.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return ordini;
    }

    public List<CompostoBean> dettagliOrdine(int idOrdine) {
        List<CompostoBean> dettagli = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_COMPOSTO +
                        " WHERE IdOrdine = ?")) {
            ps.setInt(1, idOrdine);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CompostoBean bean = new CompostoBean();
                    bean.setCodSeriale(rs.getString("CodSeriale"));
                    bean.setQuantita(rs.getInt("Quantità"));
                    dettagli.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return dettagli;
    }

    public List<OrdineBean> ordiniPerData (String email, Date dataInizio, Date dataFine){
        List<OrdineBean> ordini = new ArrayList<>();
        try (
             Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_ORDINE +
             " WHERE Email = ? AND DataAcquisto >= ? AND DataAcquisto <= ?")) {
            ps.setString(1, email);
            ps.setDate(2, (java.sql.Date) dataInizio);
            ps.setDate(3, (java.sql.Date) dataFine);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdineBean bean = new OrdineBean();
                    bean.setIdOrdine(rs.getInt(ID_ORDINE));
                    bean.setDataAcquisto(rs.getDate(DATA_ACQUISTO));
                    bean.setFattura(rs.getString(FATTURA));
                    bean.setPrezzoTotale(rs.getFloat(PREZZO_TOTALE));
                    bean.setStatoOrdine(rs.getString(STATO_ORDINE));
                    ordini.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return ordini;
    }

    public List<OrdineBean> ordiniPerPrezzo ( List<OrdineBean> ordini, String email, float minimo, float massimo){
        List<OrdineBean> ordini2 = new ArrayList<>();
        List<OrdineBean> ordini3 = new ArrayList<>();

        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_ORDINE +
                        " WHERE Email = ? AND PrezzoTotale >= ? AND PrezzoTotale <= ?")) {
            ps.setString(1, email);
            ps.setFloat(2, minimo);
            ps.setFloat(3, massimo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdineBean bean = new OrdineBean();
                    bean.setIdOrdine(rs.getInt(ID_ORDINE));
                    bean.setDataAcquisto(rs.getDate(DATA_ACQUISTO));
                    bean.setFattura(rs.getString(FATTURA));
                    bean.setPrezzoTotale(rs.getFloat(PREZZO_TOTALE));
                    bean.setStatoOrdine(rs.getString(STATO_ORDINE));
                    ordini2.add(bean);
                }
            }

            for (OrdineBean elemento : ordini) {
                for( OrdineBean elemento2 : ordini2){
                    if(Objects.equals(elemento.getIdOrdine(), elemento2.getIdOrdine())){
                        ordini3.add(elemento2);
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return ordini3;
    }
}
