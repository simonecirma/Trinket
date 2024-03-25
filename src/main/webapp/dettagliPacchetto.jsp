<%@ page import="com.example.trinket.model.bean.PacchettoBean" %>
<%@ page import="com.example.trinket.model.bean.ImmaginiBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<% PacchettoBean pacchetto = (PacchettoBean) request.getAttribute("pacchetto");%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/dettagliPacchetto.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="tutto">
    <div class="slider-wrapper">
        <div class="slider">
            <% for(ImmaginiBean bean : pacchetto.getImmagini()){
                    if(bean.isFlagCopertina()){%>
                        <img src="Immagini/Pacchetti/<%=bean.getNome()%>" alt="Errore Immagini">
            <%      }
                }
            %>
            <% for(ImmaginiBean bean : pacchetto.getImmagini()){
                    if(!bean.isFlagCopertina()){%>
                        <img src="Immagini/Pacchetti/<%=bean.getNome()%>" alt="Errore Immagini">
            <%      }
                }
            %>
        </div>
        <div class="slider-nav">
            <button class="slider-nav-btn" onclick="prevSlide()">&#10094;</button>
            <button class="slider-nav-btn" onclick="nextSlide()">&#10095;</button>
        </div>
    </div>
    <div class="destra">
        <div class="Informazioni-pacchetto">
            <div class="card">
                <div class="Nome"><span class="testo-nome"><%=pacchetto.getNome()%></span></div>
                <div class="Informazioni-pacchetto2">
                    <div class="Tipo"><span class="esperienza">Esperienza: </span><span class="testo-tipo"><%=pacchetto.getTipo()%></span> </div>
                    <div class="Tipo"><span class="esperienza">Durata: </span><span class="testo-tipo"><%=pacchetto.getNumGiorni()%> Giorni</span> </div>
                </div>
                <%if(flagAmm == null || !flagAmm){%>
                    <div class="Prezzo">
                        <span class="testo-prezzo"><span class="esperienza">Prezzo: </span><%=pacchetto.getPrezzo()%>€</span>
                        <form class="quantita" method="post" action="OrdiniControl?action=AggiungiAlCarrello&id=<%=pacchetto.getCodSeriale()%>">
                            <label for="input_quantita">Quantità: </label>
                            <%  int quantitaCarrello = 0;
                                if((carrello != null) && !carrello.getPacchetti().isEmpty()) {
                                    boolean flag = false;
                                    int counter = 0;
                                    for (PacchettoBean bean : carrello.getPacchetti()) {
                                        if (bean.getCodSeriale().equals(pacchetto.getCodSeriale())) {
                                            flag = true;
                                            break;
                                        }
                                        counter++;
                                    }
                                    if (flag) {
                                        quantitaCarrello = carrello.getQuantita().get(counter);
                                    }
                                }
                            %>
                            <input type="number" name="quantita" class="input_quantita" id="input_quantita" max="<%=pacchetto.getNumPacchetti() - quantitaCarrello%>" min="1" required>
                            <div class="main-section">
                                <button class="first-button"> Pronto ad Iniziare? </button>
                                <button class="second-button" type="submit">
                                    <svg viewBox="0 0 24 24" width="20" height="20" stroke="#ffd300" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round" class="css-i6dzq1"><circle cx="9" cy="21" r="1"></circle><circle cx="20" cy="21" r="1"></circle><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path></svg>
                                    Aggiungi al carrello!
                                </button>
                            </div>
                        </form>
                    </div>
                <%}else{%>
                    <div class="Prezzo2">
                        <form class="form" action="AdminControl?action=ModificaPacchetto&id=<%=pacchetto.getCodSeriale()%>" method="post" name="modificaInformazioni" onsubmit="return validate()">
                            <div class="campi">
                                <div class="field">
                                    <label>Prezzo: </label>
                                    <input type="number" name ="Prezzo" class="input-field" value="<%= pacchetto.getPrezzo()%>" min="1" step="0.01">
                                </div>

                                <div class="field">
                                    <label>In Stock: </label>
                                    <input type="number" name ="Rifornimento" class="input-field" placeholder="Seleziona la quantità" value="<%= pacchetto.getNumPacchetti()%>" min="1">
                                </div>
                            </div>
                            <button class="button3">Aggiorna!</button>
                        </form>
                        <button class="button" onclick="location.href='AdminControl?action=RimuoviPacchetto&id=<%=pacchetto.getCodSeriale()%>'">
                            <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none"
                                    viewBox="0 0 69 14"
                                    class="svgIcon bin-top"
                            >
                                <g clip-path="url(#clip0_35_24)">
                                    <path
                                            fill="black"
                                            d="M20.8232 2.62734L19.9948 4.21304C19.8224 4.54309 19.4808 4.75 19.1085 4.75H4.92857C2.20246 4.75 0 6.87266 0 9.5C0 12.1273 2.20246 14.25 4.92857 14.25H64.0714C66.7975 14.25 69 12.1273 69 9.5C69 6.87266 66.7975 4.75 64.0714 4.75H49.8915C49.5192 4.75 49.1776 4.54309 49.0052 4.21305L48.1768 2.62734C47.3451 1.00938 45.6355 0 43.7719 0H25.2281C23.3645 0 21.6549 1.00938 20.8232 2.62734ZM64.0023 20.0648C64.0397 19.4882 63.5822 19 63.0044 19H5.99556C5.4178 19 4.96025 19.4882 4.99766 20.0648L8.19375 69.3203C8.44018 73.0758 11.6746 76 15.5712 76H53.4288C57.3254 76 60.5598 73.0758 60.8062 69.3203L64.0023 20.0648Z"
                                    ></path>
                                </g>
                                <defs>
                                    <clipPath id="clip0_35_24">
                                        <rect fill="white" height="14" width="69"></rect>
                                    </clipPath>
                                </defs>
                            </svg>

                            <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none"
                                    viewBox="0 0 69 57"
                                    class="svgIcon bin-bottom"
                            >
                                <g clip-path="url(#clip0_35_22)">
                                    <path
                                            fill="black"
                                            d="M20.8232 -16.3727L19.9948 -14.787C19.8224 -14.4569 19.4808 -14.25 19.1085 -14.25H4.92857C2.20246 -14.25 0 -12.1273 0 -9.5C0 -6.8727 2.20246 -4.75 4.92857 -4.75H64.0714C66.7975 -4.75 69 -6.8727 69 -9.5C69 -12.1273 66.7975 -14.25 64.0714 -14.25H49.8915C49.5192 -14.25 49.1776 -14.4569 49.0052 -14.787L48.1768 -16.3727C47.3451 -17.9906 45.6355 -19 43.7719 -19H25.2281C23.3645 -19 21.6549 -17.9906 20.8232 -16.3727ZM64.0023 1.0648C64.0397 0.4882 63.5822 0 63.0044 0H5.99556C5.4178 0 4.96025 0.4882 4.99766 1.0648L8.19375 50.3203C8.44018 54.0758 11.6746 57 15.5712 57H53.4288C57.3254 57 60.5598 54.0758 60.8062 50.3203L64.0023 1.0648Z"
                                    ></path>
                                </g>
                                <defs>
                                    <clipPath id="clip0_35_22">
                                        <rect fill="white" height="57" width="69"></rect>
                                    </clipPath>
                                </defs>
                            </svg>
                        </button>
                    </div>
                <%}%>
            </div>
        </div>
        <div class="descrizione-pacchetto">
            <div class="card">
                <div class="descrizione-ridotta">
                    <span class="titolo">Che cosa è incluso?</span>
                    <span class="testo_descrizione_piccola"><%=pacchetto.getDescrizioneRidotta()%></span>
                    <span id="mostra-altro">Mostra Altro...</span>
                </div>

                <div class="descrizione-estesa" id="descrizione-estesa">
                    <span class="testo_descrizione" id="testo_descrizione" style="display: none;"><%=pacchetto.getDescrizione()%></span>
                </div>
            </div>
        </div>
    </div>

</div>

<script>
    let slideIndex = 0;
    const slides = document.querySelectorAll('.slider img');

    function showSlide(n) {
        if (n >= slides.length) {
            slideIndex = 0;
        } else if (n < 0) {
            slideIndex = slides.length - 1;
        } else {
            slideIndex = n;
        }

        for (let i = 0; i < slides.length; i++) {
            slides[i].style.transform = 'translateX(' + (-slideIndex * 100) + '%)';
        }
    }

    function nextSlide() {
        showSlide(slideIndex + 1);
    }

    function prevSlide() {
        showSlide(slideIndex - 1);
    }

    showSlide(slideIndex);


    var mostraAltroSpan = document.getElementById('mostra-altro');
    var descrizioneEstesaDiv = document.getElementById('descrizione-estesa');
    var testoDescrizioneSpan = document.getElementById('testo_descrizione');

    mostraAltroSpan.addEventListener('click', function() {
        if (descrizioneEstesaDiv.style.display === 'none') {
            descrizioneEstesaDiv.style.display = 'block';
            testoDescrizioneSpan.style.display = 'inline'; // Mostra il testo della descrizione
            mostraAltroSpan.textContent = 'Nascondi'; // Cambia il testo dello span
        } else {
            descrizioneEstesaDiv.style.display = 'none';
            mostraAltroSpan.textContent = 'Mostra Altro...'; // Cambia il testo dello span
        }
    });

    document.getElementById("input_quantita").oninput = function() {
        var input = this;
        var max = parseInt(input.max);
        var value = parseInt(input.value);

        if (value > max) {
            input.setCustomValidity("Abbiamo solo <%=pacchetto.getNumPacchetti()%> pacchetti disponibili");
        } else {
            input.setCustomValidity("");
        }
    };
</script>
</body>
</html>
