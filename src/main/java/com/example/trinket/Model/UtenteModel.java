package com.example.trinket.Model;

import com.example.trinket.Model.Bean.UtenteBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteModel {
    private static final Logger logger = Logger.getLogger(UtenteModel.class.getName());
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

    public synchronized UtenteBean login(String email, String password)  {
        UtenteBean bean = null;
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM " +TABLE_NAME_UTENTE+ " WHERE Email = ? AND Password = ? ")){
            ps.setString(1, email);
            ps.setString(2, password);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bean = new UtenteBean();
                    bean.setNome(rs.getString("Nome"));
                    bean.setCognome(rs.getString("Cognome"));
                    bean.setEmail(rs.getString("Email"));
                    bean.setPassword(rs.getString("Password"));
                    bean.setDataDiNascita(rs.getDate("DataDiNascita"));
                    bean.setImmagine(rs.getString("Immagine"));
                    bean.setFlagAmm(rs.getBoolean("FlagAmm"));
                }
            }
        }catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return bean;
    }

    public synchronized void registrati(String nome, String cognome, String email, String password, Date dataDiNascita, String immagine) {
        try (
             Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement
                ("INSERT INTO " + TABLE_NAME_UTENTE + "(Nome, Cognome, Email, Password, DataDiNascita, Immagine, FlagAmm) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setDate(5, dataDiNascita);
            ps.setString(6, immagine);
            ps.setInt(7, 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public boolean ricercaEmail(String email){
        boolean trovato = false;
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM " +TABLE_NAME_UTENTE+ " WHERE Email = ?")){
            ps.setString(1,email);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    trovato = true;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return trovato;
    }

    public boolean modificaPassword( String nome, String cognome, String email, String password, Date dataDiNascita){
        boolean modifica = false;
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement( "UPDATE " +TABLE_NAME_UTENTE+
            " SET Password = ? WHERE Nome = ? AND Cognome = ? AND Email = ? AND DataDiNascita = ? ")){
            ps.setString(1, password);
            ps.setString(2, nome);
            ps.setString(3,cognome);
            ps.setString(4, email);
            ps.setDate(5, dataDiNascita);
            int i = ps.executeUpdate();
            if(i > 0){
                modifica = true;
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return modifica;
    }
}

