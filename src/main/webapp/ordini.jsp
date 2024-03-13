<%@ page import="com.example.trinket.model.bean.PacchettoBean" %>
<%@ page import="com.example.trinket.model.bean.ImmaginiBean" %>
<%@ page import="com.example.trinket.model.bean.OrdineBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<% List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("Ordini"); %>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/ordini.css" rel="stylesheet" type="text/css">
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <div class="tutto">
        <div class="sezione_filtri">
            <p class="heading">Applica Filtri</p>
            <div class="filtri">
                <form action="UtenteControl?action=filtriOrdini" method="post" name="filtriOrdini" onsubmit="return validate3()">
                    <fieldset>
                        <legend>Data: </legend>
                        <div class="filtro_data">
                            <div class="field">
                                <label for="dataInizio">Da: </label>
                                <input type="date" class="input-field" id="dataInizio" name="dataInizio">
                            </div>
                            <div class="field">
                                <label for="dataFine">A: </label>
                                <input type="date" class="input-field" id="dataFine" name="dataFine">
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <legend>Prezzo: </legend>
                        <div class="filtro_prezzo">
                            <div class="field">
                                <label for="prezzoMinimo">Min: € </label>
                                <input type="number" class="input-field" id="prezzoMinimo" name="prezzoMinimo">
                            </div>

                            <div class="field">
                                <label for="prezzoMassimo">Max: € </label>
                                <input type="number" class="input-field" id="prezzoMassimo" name="prezzoMassimo">
                            </div>
                        </div>
                    </fieldset>

                    <button type="submit" class="button1">Applica!</button>
                </form>
            </div>
        </div>

        <div class="card">
            <table class="ordini" >
                <caption style="display: none;">Storico Ordini</caption>
                <% if(ordini != null && !ordini.isEmpty()){
                    for (OrdineBean bean : ordini){
                        int i=0;
                %>
                        <tr class="singolo_ordine">
                            <th class="top">
                                <div class="idOrdine"><label style ="font-weight: normal;">Id Ordine: </label><%= bean.getIdOrdine()%></div>
                                <div class="dataAcquisto"><label style ="font-weight: normal;">Data Acquisto: </label><%= bean.getDataAcquisto()%></div>
                                <div class="Prezzo_totale"><label style ="font-weight: normal;">Prezzo: </label> <%= bean.getPrezzoTotale()%></div>
                                <div class="statoOrdine"><%= bean.getStatoOrdine()%></div>
                            </th>
                        <% for(PacchettoBean bean2 : bean.getPacchetti()) {
                                int quantita = bean.getQuantitaPacchetto().get(i);
                                i++;
                        %>
                                <th class="bottom">
                                <% for(ImmaginiBean bean3 : bean2.getImmagini()){%>
                                    <% if(bean3.isFlagCopertina()){ %>
                                        <img class="ImmagineCopertina" src="Immagini/Pacchetti/<%=bean3.getNome()%>" alt="Copertina">
                                    <% } %>
                                <%
                                }
                                %>
                                <div class="info_pacchetto">
                                    <div class="Nome"><%= bean2.getNome()%></div>
                                    <div class="DescrizioneRidotta"><%= bean2.getDescrizioneRidotta()%></div>
                                    <div class="Quantita"><label style ="font-weight: normal;">Quantità: </label><%= quantita%></div>
                                    <div class="Prezzo"><label style ="font-weight: normal;">Prezzo: </label><%= bean2.getPrezzo()%></div>
                    <%
                        }
                    %>
                                    <div class="b">
                                        <button class="Btn">
                                            <svg class="svgIcon" viewBox="0 0 384 512" height="1em" xmlns="http://www.w3.org/2000/svg"><path d="M169.4 470.6c12.5 12.5 32.8 12.5 45.3 0l160-160c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L224 370.8 224 64c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 306.7L54.6 265.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l160 160z"></path></svg>
                                            <span class="icon2"></span>
                                            <span class="tooltip">Download</span>
                                        </button>
                                    </div>
                                </div>
                                </th>
                        </tr>
                <%
                    }
                }
                %>
            </table>
        </div>
    </div>

    <script>
        function validate3(){
            var dataFineInput = document.filtriOrdini.dataFine.value;
            var dataDiOggi = new Date();

            var dataFine = new Date(dataFineInput);

            if(dataFine > dataDiOggi) {
                alert("Inserisci una data valida");
                document.filtriOrdini.dataFine.focus();
                return false;
            }

            var dataInizioValue = document.getElementById("dataInizio").value;
            var dataFineValue = document.getElementById("dataFine").value;

            // Controlla se entrambi i campi nel primo fieldset sono vuoti
            if ((dataInizioValue.trim() === "" && dataFineValue.trim() !== "") ||
                (dataFineValue.trim() === "" && dataInizioValue.trim() !== "")) {
                alert("Devi compilare entrambi i campi di data.");
                return false;
            }

            var prezzoMinimoValue = document.getElementById("prezzoMinimo").value;
            var prezzoMassimoValue = document.getElementById("prezzoMassimo").value;

            // Controlla se entrambi i campi nel secondo fieldset sono vuoti
            if ((prezzoMinimoValue.trim() === "" && prezzoMassimoValue.trim() !== "") ||
                (prezzoMassimoValue.trim() === "" && prezzoMinimoValue.trim() !== "")) {
                alert("Devi compilare entrambi i campi di prezzo.");
                return false;
            }

            return true;
        }
    </script>
</body>
</html>
