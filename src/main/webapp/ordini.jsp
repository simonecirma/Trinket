<%@ page import="com.example.trinket.model.bean.PacchettoBean" %>
<%@ page import="com.example.trinket.model.bean.ImmaginiBean" %>
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
    <div class="a">
    <div class="sezione_filtri">
        <p class="heading">Applica Filtri</p>
        <div class="filtri">
            <form action="UtenteControl?action=Filtri" method="post">
                <div class="filtro_data">
                    <div class="field">
                        <label for="dataInizio">Da: </label>
                        <input type="date" class="input-field" id="dataInizio">
                    </div>
                    <div class="field">
                        <label for="dataFine">A: </label>
                        <input type="date" class="input-field" id="dataFine">
                    </div>
                </div>

                <div class="filtro_prezzo">
                    <input type="range" min="10" max="500" value="50" class="prezzo" id="myRange">
                    <p class="valore_prezzo"><span id="valore"></span>€</p>
                </div>

                <button type="submit" class="button1">Applica!</button>
            </form>
        </div>
    </div>

    <div class="card">
        <table class="ordini">
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
                                    <img class="ImmagineCopertina" src="Immagini/Pacchetti/<%=bean3.getNome()%>">
                                <% } %>
                            <%
                            }
                            %>
                                <div class="info_pacchetto">
                                    <div class="Nome"><%= bean2.getNome()%></div>
                                    <div class="DescrizioneRidotta"><%= bean2.getDescrizioneRidotta()%></div>
                                    <div class="Quantita">Quantità: <%= quantita%></div>
                                    <div class="Prezzo">Prezzo: <%= bean2.getPrezzo()%></div>
                                    <div class="generaFattura"> <button class="bottone"></button></div>
                                </div>
                            </th>
            <%
                    }
                    i=0;
            %>
                    </tr>
            <%
                }
            }
            %>
        </table>
    </div>
    </div>
    <script>
    // Seleziona l'elemento dello slider e l'elemento dove visualizzare il valore
    var slider = document.getElementById("myRange");
    var valoreOutput = document.getElementById("valore");

    // Aggiorna il valore visualizzato quando si muove lo slider
    slider.addEventListener("input", function() {
    valoreOutput.textContent = slider.value;
    });
    </script>

</body>
</html>
