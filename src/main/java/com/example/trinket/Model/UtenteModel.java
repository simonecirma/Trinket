package com.example.trinket.Model;

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

    public UtenteBean login(String email, String password) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UtenteBean bean = new UtenteBean();
        String sql = "SELECT * FROM " +TABLE_NAME_UTENTE+ " WHERE Email = ? AND Password = ? ";
        try{
            con = ds.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while(rs.next()){
                bean.setId(rs.getInt("ID"));
                bean.setNome(rs.getString("Nome"));
                bean.setCognome(rs.getString("Cognome"));
                bean.setEmail(rs.getString("Email"));
                bean.setPassword(rs.getString("Password"));
                bean.setIndirizzo(rs.getString("Indirizzo"));
                bean.setNumeroCivico(rs.getInt("NumeroCivico"));
                bean.setCap(rs.getInt("CAP"));
                bean.setCitta(rs.getString("Città"));
                bean.setProvincia(rs.getString("Provincia"));
                bean.setDataDiNascita(rs.getDate("DataDiNascita"));
                bean.setNumeroTelefono(rs.getString("NumeroTelefono"));
                bean.setImmagine(rs.getString("Immagine"));
                bean.setFlagAmm(rs.getBoolean("FlagAmm"));
            }
        }catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        if (bean == null || bean.getEmail() == null || bean.getEmail().trim().isEmpty()) {
            return null;
        } else {
            return bean;
        }
    }
}

