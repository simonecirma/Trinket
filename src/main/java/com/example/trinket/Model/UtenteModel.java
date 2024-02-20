package com.example.trinket.Model;

import com.example.trinket.GestioneErrori;
import com.example.trinket.Model.Bean.UtenteBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteModel {
    private static Logger logger = Logger.getLogger(UtenteModel.class.getName());
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

    public UtenteBean login(String email, String password) throws GestioneErrori {
        try(
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM " +TABLE_NAME_UTENTE+ " WHERE Email = ? AND Password = ? ")){
            ps.setString(1, email);
            ps.setString(2, password);
            try(ResultSet rs = ps.executeQuery()) {
                UtenteBean bean = null;
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
                return bean;
            }
        }catch (SQLException e) {
            throw new GestioneErrori("Errore Durante L'Accesso Ai Dati Dell'Utente");
        }
    }
}

