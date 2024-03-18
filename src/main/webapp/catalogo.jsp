<%@ page import="com.example.trinket.model.bean.PacchettoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.trinket.model.bean.ImmaginiBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<% List<PacchettoBean> pacchetti = (List<PacchettoBean>) request.getAttribute("pacchetti");%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/catalogo.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="navbar.jsp" %>
    <div class="tutto">
        <div class="sezione_filtri">
            <p class="heading">Applica Filtri</p>
            <div class="filtri">
                <form action="PacchettoControl?action=FiltriPacchetti" method="post" name="filtriPacchetti" onsubmit="return validate3()">
                    <fieldset>
                        <legend>Durata: </legend>
                        <div class="filtro_durata">
                            <input type="checkbox" id="option1" name="option" value="1">
                            <label for="option1">1 Giorno</label><br>

                            <input type="checkbox" id="option2" name="option" value="2">
                            <label for="option2">2 Giorni</label><br>

                            <input type="checkbox" id="option3" name="option" value="3">
                            <label for="option3">3 Giorni</label><br>
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
                    <fieldset>
                        <legend>Tipologia: </legend>
                        <div class="filtro_tipologia">
                            <input type="checkbox" id="option4" name="option_tipo" value="Terme e SPA">
                            <label for="option4">Terme e SPA</label><br>

                            <input type="checkbox" id="option5" name="option_tipo" value="Weekend Romantico">
                            <label for="option5">Weekend Romantico</label><br>

                            <input type="checkbox" id="option6" name="option_tipo" value="Agriturismi e BeB">
                            <label for="option6">Agriturismi e B&B</label><br>

                            <input type="checkbox" id="option7" name="option_tipo" value="Soggiorno Classico">
                            <label for="option7">Soggiorno Classico</label><br>

                            <input type="checkbox" id="option8" name="option_tipo" value="Soggiorno Insolito">
                            <label for="option8">Soggiorno Insolito</label><br>

                            <input type="checkbox" id="option9" name="option_tipo" value="Corso di Cucina">
                            <label for="option9">Corso di Cucina</label><br>

                            <input type="checkbox" id="option10" name="option_tipo" value="Esperienze Gastronomiche">
                            <label for="option10">Esperienze Gastronomiche</label><br>
                        </div>
                    </fieldset>

                    <div class="bottoni">
                        <button type="submit" class="button1">Applica!</button>
                        <button type="submit" class="button1" onclick="'PacchettoControl?action=OttieniPacchetti'">Ripristina Filtri</button>
                    </div>
                </form>
            </div>
        </div>
        <div class="pacchetti">
            <% if(!pacchetti.isEmpty()){
                for(PacchettoBean bean : pacchetti){%>
                <button class="singolo_pacchetto" onclick="location.href='PacchettoControl?action=DettagliPacchetto&id=<%=bean.getCodSeriale()%>'">
                    <%for(ImmaginiBean bean2 : bean.getImmagini()){
                        if(bean2.isFlagCopertina()){%>
                            <div class="immagine">
                                <img src="Immagini/Pacchetti/<%=bean2.getNome()%>" alt="Errore Immagine">
                            </div>
                    <%  }
                    }%>
                    <div class="nome">
                        <span> <%=bean.getNome()%></span>
                    </div>
                    <div class="info">
                        <div class="giorni">
                            <img src="Immagini/Calendario.png" alt="Errore">
                            <span>Durata: <%=bean.getNumGiorni()%>
                                    <%if(bean.getNumGiorni()==1){%>
                                        giorno
                                    <%}else{%>
                                        giorni
                                    <%}%>
                            </span>
                        </div>
                        <div class="persone">
                            <img src="Immagini/NumPersone.jpg" alt="Errore">
                            <span>Per 2 Persone</span>
                        </div>
                    </div>
                    <div class="prezzo">
                        <span> <%=bean.getPrezzo()%> €</span>
                    </div>
                </button>
            <%}
            }else{%>
            <div class="nessun_elemento">
                <span>Nessun Elemento Trovato! </span>
            </div>
            <%}%>
        </div>
    </div>

<script>
    function validate3(){
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
