<%@ page import="com.example.trinket.model.bean.PacchettoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.trinket.model.bean.ImmaginiBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<% List<PacchettoBean> pacchetti = (List<PacchettoBean>) request.getAttribute("pacchetti");
   List<String> tipi = (List<String>) request.getAttribute("tipi");
   List<Integer> durata = (List<Integer>) request.getAttribute("durata");%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/catalogo.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha384-UG8ao2jwOWB7/oDdObZc6ItJmwUkR/PfMyt9Qs5AwX7PsnYn1CRKCTWyncPTWvaS" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="navbar.jsp" %>
    <div class="tutto">
        <div class="sezione_filtri">
            <p class="heading">Applica Filtri</p>
            <div class="filtri">
                <form action="PacchettoControl?action=FiltriPacchetti" method="post" name="filtriPacchetti" onsubmit="return validate3()" class="filtriPacchetti_form">
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
                        <button type="button" class="button1" onclick="location.href='PacchettoControl?action=OttieniPacchetti'">Ripristina</button>
                    </div>
                </form>
            </div>
        </div>
        <div class="pacchetti">
            <%if(flagAmm != null && flagAmm){%>
            <div class="barraMetodi">
                <button type="button" class="aggiungiPacchetto" onclick="showSection('inserimentoPacchetti')">Aggiungi Pacchetto</button>
            </div>
            <div id="inserimentoPacchetti" class="card" style="display: none;">
                <div class="card2">
                    <div class="card3">
                        <form class="form" action="AdminControl?action=AggiungiPacchetto" method="post" name="inserimentoPacchetto" enctype="multipart/form-data">
                            <p class="heading_form">Inserisci Nuovo Pacchetto</p>

                            <div class="field_form">
                                <input type="text" name ="codSeriale" class="input-field_form" placeholder="Codice Seriale Univoco" required>
                            </div>

                            <div class="field_form">
                                <input type="text" name ="Nome" class="input-field_form" placeholder="Nome Pacchetto" required>
                            </div>

                            <div class="field_form">
                                <input type="number" name ="Prezzo" class="input-field_form" placeholder="Prezzo" min="1" step="0.01" required>
                            </div>

                            <div class="field_form">
                                <input type="text" name ="Descrizione" class="input-field_form" placeholder="Descrizione" required>
                            </div>

                            <div class="field_form">
                                <input type="text" name ="DescrizioneRidotta" class="input-field_form" placeholder="Descrizione Ridotta" required>
                            </div>

                            <div class="field_form">
                                <select class="menuPacchetto" id="Tipo" name="Tipo" required>
                                    <option value="" selected>Scegli la Tipologia</option>
                                    <%for(String temp : tipi){%>
                                    <option value="<%=temp%>"><%=temp%></option>
                                    <%}%>
                                </select>
                            </div>

                            <div class="field_form">
                                <select class="menuPacchetto" id="Durata" name="Durata" required>
                                    <option value="" selected>Scegli la Durata</option>
                                    <%for(int i : durata){%>
                                    <option value="<%=i%>"><%=i%></option>
                                    <%}%>
                                </select>
                            </div>

                            <div class="field_form">
                                <label for="immagini">Carica copertina:</label>
                                <input type="file" class="input-field_form" id="copertina" name="Copertina">
                            </div>

                            <div class="field_form">
                                <label for="immagini">Carica immagini:</label>
                                <input type="file" class="input-field_form" id="immagini" name="Immagini" multiple>
                            </div>

                            <div class="field_form">
                                <input type="number" name ="NumPacchetti" class="input-field_form" placeholder="Quanti Pacchetti vuoi inserire" min="1" required>
                            </div>

                            <button type="submit" class="button3">Salva!</button>
                        </form>
                    </div>
                </div>
            </div>
            <%}%>
            <div class="card" id="catalogo">
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

    function showSection(sectionId) {
        var sections = document.getElementsByClassName('card');
        if(document.getElementById(sectionId).style.display !== 'flex') {
            for (var i = 0; i < sections.length; i++) {
                sections[i].style.display = 'none';
            }
            document.getElementById(sectionId).style.display = 'flex';
        }else{
            document.getElementById(sectionId).style.display = 'none';
            document.getElementById("catalogo").style.display = 'flex';
        }
    }
</script>

</body>
</html>
