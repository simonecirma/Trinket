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
            <div class="singolo_ordine">
                <% if(ordini != null && !ordini.isEmpty()){
                        for (OrdineBean bean : ordini){
                %>
                        <tr>
                            <th><div class="idOrdine"><%= bean.getIdOrdine()%></div></th>
                            <th><div class="dataAcquisto"><%= bean.getDataAcquisto()%></div></th>
                            <th><div class="statoOrdine"><%= bean.getStatoOrdine()%></div></th>
                        </tr>
                            <% for(PacchettoBean bean2 : bean.getPacchetti()) { %>
                                <tr>
                                    <% for(ImmaginiBean bean3 : bean2.getImmagini()){%>
                                            <th><div class="ImmagineCopertina">
                                                <% if(bean3.isFlagCopertina()){ %>
                                                        <img src="Immagini/Pacchetti/<%=bean3.getNome()%>">
                                                <% } %>
                                            </div></th>
                                    <%
                                        }
                                    %>
                                    <th><div class="Nome"><%= bean2.getNome()%></div></th>
                                    <th><div class="Prezzo"><%= bean2.getPrezzo()%></div></th>
                                    <th><div class="DescrizioneRidotta"><%= bean2.getDescrizioneRidotta()%></div></th>
                                    <th><div class="Descrizione"><%= bean2.getDescrizione()%></div></th>
                                    <th><div class="Tipo"><%= bean2.getTipo()%></div></th>
                                    <th><div class="NumGiorni"><%= bean2.getNumGiorni()%></div></th>
                                    <th><div class="NumPacchetti"><%= bean2.getNumPacchetti()%></div></th>
                                </tr>
                            <%
                                }
                        }
                    }
                %>
            </div>
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
