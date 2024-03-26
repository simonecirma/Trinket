package com.example.trinket.model;

import com.example.trinket.model.bean.ImmaginiBean;
import com.example.trinket.model.bean.PacchettoBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacchettoModel {

    private static final Logger logger = Logger.getLogger(PacchettoModel.class.getName());

    private static final String TABLE_NAME_PACCHETTO = "Pacchetto";
    private static final String TABLE_NAME_TIPOLOGIA = "Tipologia";
    private static final String TABLE_NAME_DURATA = "Durata";
    private static final String SELECT_FROM = "SELECT * FROM ";
    private static final String TABLE_NAME_IMMAGINI = "Immagini";
    private static final String CODSERIALE = "CodSeriale";
    private static final String PREZZO = "Prezzo";
    private static final String DESCRIZIONE_RIDOTTA = "DescrizioneRidotta";
    private static final String DESCRIZIONE = "Descrizione";
    private static final String NUM_GIORNI = "NumGiorni";
    private static final String NUM_PACCHETTI = "NumPacchetti";
    private static final String FLAG_DISPONIBILITA = "FlagDisponibilità";


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

    public List<PacchettoBean> getPacchetti() {
        List<PacchettoBean> pacchetti = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO + " WHERE FlagDisponibilità = 1")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PacchettoBean bean = new PacchettoBean();
                    bean.setCodSeriale(rs.getString(CODSERIALE));
                    bean.setNome(rs.getString("Nome"));
                    bean.setPrezzo(rs.getFloat(PREZZO));
                    bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                    bean.setDescrizione(rs.getString(DESCRIZIONE));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                    bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                    bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                    pacchetti.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return pacchetti;
    }

    public List<PacchettoBean> getPacchettiPerAmministratore() {
        List<PacchettoBean> pacchetti = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PacchettoBean bean = new PacchettoBean();
                    bean.setCodSeriale(rs.getString(CODSERIALE));
                    bean.setNome(rs.getString("Nome"));
                    bean.setPrezzo(rs.getFloat(PREZZO));
                    bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                    bean.setDescrizione(rs.getString(DESCRIZIONE));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                    bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                    bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                    pacchetti.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return pacchetti;
    }

    public List<String> getTipo() {
        List<String> tipi = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT TipoPacchetto FROM " + TABLE_NAME_TIPOLOGIA )) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("TipoPacchetto");
                    tipi.add(tipo);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return tipi;
    }

    public List<Integer> getDurata() {
        List<Integer> durata = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT NumGiorni FROM " + TABLE_NAME_DURATA )) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int numGiorni = rs.getInt("NumGiorni");
                    durata.add(numGiorni);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return durata;
    }

    public PacchettoBean getPacchettoById(String codSeriale) {
        PacchettoBean bean = new PacchettoBean();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO +
                        " WHERE CodSeriale = ?")) {
            ps.setString(1, codSeriale);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bean.setCodSeriale(rs.getString(CODSERIALE));
                    bean.setNome(rs.getString("Nome"));
                    bean.setPrezzo(rs.getFloat(PREZZO));
                    bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                    bean.setDescrizione(rs.getString(DESCRIZIONE));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                    bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                    bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                    bean.setImmagini(immaginiPerPacchetto(codSeriale));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return bean;
    }

    public List<ImmaginiBean> immaginiPerPacchetto(String codice) {
        List<ImmaginiBean> immagini = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_IMMAGINI +
                        " WHERE Codice = ?")) {
            ps.setString(1, codice);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ImmaginiBean bean = new ImmaginiBean();
                    bean.setNome(rs.getString("Nome"));
                    bean.setCodice(rs.getString("Codice"));
                    bean.setFlagCopertina(rs.getBoolean("FlagCopertina"));
                    immagini.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return immagini;
    }

    public List<PacchettoBean> filtroDurata(int[] giorni) {
        List<PacchettoBean> pacchetti = new ArrayList<>();
        if (giorni.length == 3) {
            pacchetti = getPacchetti();
        } else if (giorni.length == 2) {
            try (
                    Connection con = ds.getConnection();
                    PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO + " WHERE FlagDisponibilità = 1 AND NumGiorni IN (?, ?) ")) {
                ps.setInt(1, giorni[0]);
                ps.setInt(2, giorni[1]);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        PacchettoBean bean = new PacchettoBean();
                        bean.setCodSeriale(rs.getString(CODSERIALE));
                        bean.setNome(rs.getString("Nome"));
                        bean.setPrezzo(rs.getFloat(PREZZO));
                        bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                        bean.setDescrizione(rs.getString(DESCRIZIONE));
                        bean.setTipo(rs.getString("Tipo"));
                        bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                        bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                        bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                        pacchetti.add(bean);
                    }
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        } else {
            try (
                    Connection con = ds.getConnection();
                    PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO + " WHERE FlagDisponibilità = 1 AND NumGiorni = ?")) {
                ps.setInt(1, giorni[0]);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        PacchettoBean bean = new PacchettoBean();
                        bean.setCodSeriale(rs.getString(CODSERIALE));
                        bean.setNome(rs.getString("Nome"));
                        bean.setPrezzo(rs.getFloat(PREZZO));
                        bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                        bean.setDescrizione(rs.getString(DESCRIZIONE));
                        bean.setTipo(rs.getString("Tipo"));
                        bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                        bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                        bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                        pacchetti.add(bean);
                    }
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
        return pacchetti;
    }

    public List<PacchettoBean> filtroPrezzo(List<PacchettoBean> pacchetti, float minimo, float massimo) {
        List<PacchettoBean> pacchetti2 = new ArrayList<>();
        List<PacchettoBean> pacchetti3 = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO + " WHERE FlagDisponibilità = 1 AND Prezzo >= ? AND Prezzo <= ? ")) {
            ps.setFloat(1, minimo);
            ps.setFloat(2, massimo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PacchettoBean bean = new PacchettoBean();
                    bean.setCodSeriale(rs.getString(CODSERIALE));
                    bean.setNome(rs.getString("Nome"));
                    bean.setPrezzo(rs.getFloat(PREZZO));
                    bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                    bean.setDescrizione(rs.getString(DESCRIZIONE));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                    bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                    bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                    pacchetti2.add(bean);
                }
            }
            for (PacchettoBean elemento : pacchetti) {
                for (PacchettoBean elemento2 : pacchetti2) {
                    if (Objects.equals(elemento.getCodSeriale(), elemento2.getCodSeriale())) {
                        pacchetti3.add(elemento2);
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return pacchetti3;
    }

    public List<PacchettoBean> filtroTipoIndex(String tipo) {
        List<PacchettoBean> pacchetti = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO + " WHERE FlagDisponibilità = 1 AND Tipo = ?")) {
            ps.setString(1, tipo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PacchettoBean bean = new PacchettoBean();
                    bean.setCodSeriale(rs.getString(CODSERIALE));
                    bean.setNome(rs.getString("Nome"));
                    bean.setPrezzo(rs.getFloat(PREZZO));
                    bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                    bean.setDescrizione(rs.getString(DESCRIZIONE));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                    bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                    bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                    pacchetti.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return pacchetti;
    }

    public List<PacchettoBean> filtroTipo(List<PacchettoBean> pacchetti, String[] tipo) {
        List<PacchettoBean> pacchetti2 = new ArrayList<>();
        List<PacchettoBean> pacchetti3 = new ArrayList<>();
        if (tipo.length == 7) {
            pacchetti2 = getPacchetti();
        } else if (tipo.length == 6) {
            pacchetti2 = filtroTipi6(tipo);
        } else if (tipo.length == 5) {
            pacchetti2 = filtroTipi5(tipo);
        } else if (tipo.length == 4) {
            pacchetti2 = filtroTipi4(tipo);
        } else if (tipo.length == 3) {
            pacchetti2 = filtroTipi3(tipo);
        } else if (tipo.length == 2) {
            pacchetti2 = filtroTipi2(tipo);
        } else {
            pacchetti2 = filtroTipo1(tipo);
        }

        for (PacchettoBean elemento : pacchetti) {
            for (PacchettoBean elemento2 : pacchetti2) {
                if (Objects.equals(elemento.getCodSeriale(), elemento2.getCodSeriale())) {
                    pacchetti3.add(elemento2);
                }
            }
        }
        return pacchetti3;
    }

    public List<PacchettoBean> filtroTipo1(String[] tipo) {
        List<PacchettoBean> pacchetti = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO + " WHERE FlagDisponibilità = 1 AND Tipo = ?")) {
            ps.setString(1, tipo[0]);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PacchettoBean bean = new PacchettoBean();
                    bean.setCodSeriale(rs.getString(CODSERIALE));
                    bean.setNome(rs.getString("Nome"));
                    bean.setPrezzo(rs.getFloat(PREZZO));
                    bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                    bean.setDescrizione(rs.getString(DESCRIZIONE));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                    bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                    bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                    pacchetti.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return pacchetti;
    }

    public List<PacchettoBean> filtroTipi2(String[] tipo) {
        List<PacchettoBean> pacchetti = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO + " WHERE FlagDisponibilità = 1 AND Tipo IN (?, ?)")) {
            ps.setString(1, tipo[0]);
            ps.setString(2, tipo[1]);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PacchettoBean bean = new PacchettoBean();
                    bean.setCodSeriale(rs.getString(CODSERIALE));
                    bean.setNome(rs.getString("Nome"));
                    bean.setPrezzo(rs.getFloat(PREZZO));
                    bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                    bean.setDescrizione(rs.getString(DESCRIZIONE));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                    bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                    bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                    pacchetti.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return pacchetti;
    }

    public List<PacchettoBean> filtroTipi3(String[] tipo) {
        List<PacchettoBean> pacchetti = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO + " WHERE FlagDisponibilità = 1 AND Tipo IN (?, ?, ?)")) {
            ps.setString(1, tipo[0]);
            ps.setString(2, tipo[1]);
            ps.setString(3, tipo[2]);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PacchettoBean bean = new PacchettoBean();
                    bean.setCodSeriale(rs.getString(CODSERIALE));
                    bean.setNome(rs.getString("Nome"));
                    bean.setPrezzo(rs.getFloat(PREZZO));
                    bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                    bean.setDescrizione(rs.getString(DESCRIZIONE));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                    bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                    bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                    pacchetti.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return pacchetti;
    }

    public List<PacchettoBean> filtroTipi4(String[] tipo) {
        List<PacchettoBean> pacchetti = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO + " WHERE FlagDisponibilità = 1 AND Tipo IN (?, ?, ?, ?)")) {
            ps.setString(1, tipo[0]);
            ps.setString(2, tipo[1]);
            ps.setString(3, tipo[2]);
            ps.setString(4, tipo[3]);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PacchettoBean bean = new PacchettoBean();
                    bean.setCodSeriale(rs.getString(CODSERIALE));
                    bean.setNome(rs.getString("Nome"));
                    bean.setPrezzo(rs.getFloat(PREZZO));
                    bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                    bean.setDescrizione(rs.getString(DESCRIZIONE));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                    bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                    bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                    pacchetti.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return pacchetti;
    }

    public List<PacchettoBean> filtroTipi5(String[] tipo) {
        List<PacchettoBean> pacchetti = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO + " WHERE FlagDisponibilità = 1 AND Tipo IN (?, ?, ?, ?, ?)")) {
            ps.setString(1, tipo[0]);
            ps.setString(2, tipo[1]);
            ps.setString(3, tipo[2]);
            ps.setString(4, tipo[3]);
            ps.setString(5, tipo[4]);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PacchettoBean bean = new PacchettoBean();
                    bean.setCodSeriale(rs.getString(CODSERIALE));
                    bean.setNome(rs.getString("Nome"));
                    bean.setPrezzo(rs.getFloat(PREZZO));
                    bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                    bean.setDescrizione(rs.getString(DESCRIZIONE));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                    bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                    bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                    pacchetti.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return pacchetti;
    }
    public List<PacchettoBean> filtroTipi6(String[] tipo) {
        List<PacchettoBean> pacchetti = new ArrayList<>();
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_FROM + TABLE_NAME_PACCHETTO + " WHERE FlagDisponibilità = 1 AND Tipo IN (?, ?, ?, ?, ?, ?) ")) {
            ps.setString(1, tipo[0]);
            ps.setString(2, tipo[1]);
            ps.setString(3, tipo[2]);
            ps.setString(4, tipo[3]);
            ps.setString(5, tipo[4]);
            ps.setString(6, tipo[5]);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PacchettoBean bean = new PacchettoBean();
                    bean.setCodSeriale(rs.getString(CODSERIALE));
                    bean.setNome(rs.getString("Nome"));
                    bean.setPrezzo(rs.getFloat(PREZZO));
                    bean.setDescrizioneRidotta(rs.getString(DESCRIZIONE_RIDOTTA));
                    bean.setDescrizione(rs.getString(DESCRIZIONE));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setNumGiorni(rs.getInt(NUM_GIORNI));
                    bean.setNumPacchetti(rs.getInt(NUM_PACCHETTI));
                    bean.setFlagDisponibilita(rs.getBoolean(FLAG_DISPONIBILITA));
                    pacchetti.add(bean);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return pacchetti;
    }

    public synchronized void aggiungiPacchetto(String codSeriale, String nome, Float prezzo, String descrizione, String descrizioneRidotta, String tipo, int numGiorni, int numPacchetti) {
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement
                        ("INSERT INTO " + TABLE_NAME_PACCHETTO + "(CodSeriale, Nome, Prezzo, Descrizione, DescrizioneRidotta, Tipo, NumGiorni, NumPacchetti, FlagDisponibilità) " +
                                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, 1)")) {
            ps.setString(1, codSeriale);
            ps.setString(2, nome);
            ps.setFloat(3, prezzo);
            ps.setString(4, descrizione);
            ps.setString(5, descrizioneRidotta);
            ps.setString(6, tipo);
            ps.setInt(7, numGiorni);
            ps.setInt(8, numPacchetti);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public synchronized void aggiungiCopertina(String codSeriale, String nome) {
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement
                        ("INSERT INTO " + TABLE_NAME_IMMAGINI + "(Nome, Codice, FlagCopertina ) " +
                                "VALUES(?, ?, 1)")) {
            ps.setString(1, nome);
            ps.setString(2, codSeriale);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public synchronized void aggiungiImmagini(String codSeriale, String nome) {
        try (
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement
                        ("INSERT INTO " + TABLE_NAME_IMMAGINI + "(Nome, Codice, FlagCopertina ) " +
                                "VALUES(?, ?, 0)")) {
            ps.setString(1, nome);
            ps.setString(2, codSeriale);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void modificaPacchetto (Float prezzo, int quantita, String codice){
        try(
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE " +TABLE_NAME_PACCHETTO+ " SET Prezzo = ?, NumPacchetti = ?, FlagDisponibilità = 1 WHERE CodSeriale = ? ")) {
            ps.setFloat(1, prezzo);
            ps.setInt(2, quantita);
            ps.setString(3, codice);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void rimuoviPacchetto (String codice){
        try(
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE " +TABLE_NAME_PACCHETTO+ " SET FlagDisponibilità = 0, NumPacchetti = 0 WHERE CodSeriale = ? ")) {
            ps.setString(1, codice);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void modificaNumPacchetti (int quantita, String codice){
        try(
                Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE " +TABLE_NAME_PACCHETTO+ " SET NumPacchetti = NumPacchetti - ? WHERE CodSeriale = ? ")) {
            ps.setInt(1, quantita);
            ps.setString(2, codice);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        int i = 0;

        try(Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT NumPacchetti FROM " +TABLE_NAME_PACCHETTO+ " WHERE CodSeriale = ?")){
            ps.setString(1, codice);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    i = rs.getInt("NumPacchetti");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        if(i == 0){
            try(
                    Connection con = ds.getConnection();
                    PreparedStatement ps = con.prepareStatement("UPDATE " +TABLE_NAME_PACCHETTO+ " SET FlagDisponibilità = 0 WHERE CodSeriale = ? ")) {
                ps.setString(1, codice);
                ps.executeUpdate();
            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
    }
}
