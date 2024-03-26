<%@ page import="java.util.List" %>
<%@ page import="com.example.trinket.model.bean.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<% List<IndirizzoBean> indirizzi = (List<IndirizzoBean>) request.getAttribute("indirizzi");
 List<MetodoPagamentoBean> metodi = (List<MetodoPagamentoBean>) request.getAttribute("metodi"); %>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/carrello.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha384-UG8ao2jwOWB7/oDdObZc6ItJmwUkR/PfMyt9Qs5AwX7PsnYn1CRKCTWyncPTWvaS" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="tutto">
    <div class="pacchetti">
        <table class="carrello_table">
            <caption style="display: none;">Storico Ordini</caption>
            <%if((carrello != null) && !carrello.getPacchetti().isEmpty()){
                List<Integer> quantita = carrello.getQuantita();
                int i = 0;
                for(PacchettoBean bean : carrello.getPacchetti()){%>
                    <tr class="singolo_pacchetto">
                        <th class="contenuto">
                            <%for(ImmaginiBean bean2 : bean.getImmagini()){
                                if(bean2.isFlagCopertina()){%>
                                    <a href="PacchettoControl?action=DettagliPacchetto&id=<%=bean.getCodSeriale()%>" class="link_immagine">
                                        <img class="ImmagineCopertina" src="Immagini/Pacchetti/<%=bean2.getNome()%>" alt="Copertina">
                                    </a>
                    <%          }
                            }%>
                            <div class="info_pacchetto">
                                <div class="Nome"><a href="PacchettoControl?action=DettagliPacchetto&id=<%=bean.getCodSeriale()%>"><%= bean.getNome()%></a></div>
                                <div class="DescrizioneRidotta"><%= bean.getDescrizioneRidotta()%></div>
                                <div class="Quantita">
                                    <div class ="scritta"><label style="font-weight: normal;">Quantità: </label><span id="quantita<%=i%>"><%= quantita.get(i)%></span></div>
                                    <button id="aggiungi<%=i%>" class="aggiungi" onclick="aggiungiQuantita(<%=i%>, <%=bean.getNumPacchetti()%>)"> + </button>
                                    <button id="rimuovi<%=i%>" class="rimuovi" onclick="rimuoviQuantita(<%=i%>)" <%if(quantita.get(i) == 1){%>style="background-color:rgb(141,141,141)<%}%>"> - </button>
                                </div>
                                <div class="Prezzo">
                                    <div class ="scritta"><label style ="font-weight: normal;">Prezzo: </label><%= bean.getPrezzo()%></div>
                                    <button class="button" onclick="location.href='OrdiniControl?action=RimuoviDalCarrello&id=<%=bean.getCodSeriale()%>'">
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

                            </div>
                        </th>
                    </tr>
            <%      i++;
                }
              }else{%>
            <div class="nessun_elemento">
                <span>Nessun Elemento Nel Carrello!</span>
            </div>
            <%}%>
        </table>
    </div>
    <%if(email != null){%>
    <div class="paymentOptions" id="paymentOptions" style="display: none;">
        <div class="carte">
            <%int i=0;
                for(MetodoPagamentoBean bean : metodi){%>
            <div class="singola_carta">
                <input type="radio" id="metodo<%=i%>" name="paymentMethod">
                <label class="payment-info" for="metodo<%=i%>">
                    <span ><%=bean.getTitolare()%></span><br>
                    <span><%=bean.getNumeroCarta()%></span><br>
                    <span><%=bean.getScadenza()%></span></label><br>
            </div>
            <%  i++;
            }%>
        </div>
        <div class="bottone">
            <button class="conferma" onclick="selectPaymentMethod()">Conferma</button>
        </div>
    </div>

    <div class="paymentOptions" id="indirizziOptions" style="display: none;">
        <div class="carte">
            <%
                for(IndirizzoBean bean : indirizzi){%>
            <div class="singola_carta">
                <input type="radio" id="metodo<%=i%>" name="indirizzi">
                <label class="payment-info" for="metodo<%=i%>">
                    <span><%=bean.getNome()%></span><br>
                    <span><%=bean.getIndirizzo()%> <%=bean.getNumeroCivico()%></span><br>
                    <span><%=bean.getProvincia()%></span><br>
                    <span><%=bean.getCitta()%> <%=bean.getCap()%></span></label><br>

            </div>
            <%  i++;
            }%>
        </div>
        <div class="bottone">
            <button class="conferma" onclick="selectIndirizzi()">Conferma</button>
        </div>
    </div>
    <%}%>
    <div class="checkout">
        <%if(email != null){%>
            <div class="metodo_pagamento">
                <div id="paymentSelection">
                    <span class="dati" id="selectedPayment" style="display: none"></span><br>
                    <span class="dati" id="selectedPayment2" style="display: none"></span><br>
                    <span class="dati" id="selectedPayment3" style="display: none"></span><br>
                    <a href="#" id="choosePayment" onclick="showPaymentOptions()">Scegli metodo di pagamento</a>
                </div>
            </div>
            <div class="indirizzo_spedizione">
                <div id="paymentSelection2">
                    <span class="dati" id="selectedPayment4" style="display: none"></span><br>
                    <span class="dati" id="selectedPayment5" style="display: none"></span><br>
                    <span class="dati" id="selectedPayment6" style="display: none"></span><br>
                    <span class="dati" id="selectedPayment7" style="display: none"></span><br>
                    <a href="#" id="chooseIndirizzo" onclick="showAddressOptions()">Scegli indirizzo di spedizione</a>
                </div>
            </div>
            <div class="prezzo">
                <%float totale = 0;
                  if(carrello != null) {
                    for (int i = 0; i < carrello.getPacchetti().size(); i++) {
                        PacchettoBean bean = carrello.getPacchetti().get(i);
                        int quantita = carrello.getQuantita().get(i);
                        float prezzoArticolo = bean.getPrezzo() * quantita;
                        totale += prezzoArticolo;
                    }
                  }
                  String totaleFormattato = String.format("%.2f", totale);
                %>
                <div class="totale">
                    <span class="totale_scritta">Totale: <span id="totale_numero"><%=totaleFormattato%> </span></span>
                    <button class="Btn" onclick="checkout()">
                        Checkout
                        <svg class="svgIcon" viewBox="0 0 576 512"><path d="M512 80c8.8 0 16 7.2 16 16v32H48V96c0-8.8 7.2-16 16-16H512zm16 144V416c0 8.8-7.2 16-16 16H64c-8.8 0-16-7.2-16-16V224H528zM64 32C28.7 32 0 60.7 0 96V416c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V96c0-35.3-28.7-64-64-64H64zm56 304c-13.3 0-24 10.7-24 24s10.7 24 24 24h48c13.3 0 24-10.7 24-24s-10.7-24-24-24H120zm128 0c-13.3 0-24 10.7-24 24s10.7 24 24 24H360c13.3 0 24-10.7 24-24s-10.7-24-24-24H248z"></path></svg>
                    </button>
                </div>
            </div>
        <%}else{%>
            <div class="avviso">
                <span> Effettua il <a href="login.jsp">login</a> per procedere con l'acquisto.</span>
            </div>
        <%}%>
    </div>
</div>

<script>
    function selectPaymentMethod() {
        var selectedMethod = document.querySelector('input[name="paymentMethod"]:checked');
        var selectedLabel = document.querySelector('label[for="' + selectedMethod.id + '"]');

        var spanElements = selectedLabel.querySelectorAll('span'); // Seleziona tutti gli span all'interno della label
        var selectedPayment = document.getElementById('selectedPayment');
        var selectedPayment2 = document.getElementById('selectedPayment2');
        var selectedPayment3 = document.getElementById('selectedPayment3');

        // Inserisci il testo del primo span nell'elemento span con id "selectedPayment"
        selectedPayment.textContent = "Titolare: " +spanElements[0].textContent;
        // Inserisci il testo del secondo span nell'elemento span con id "selectedPayment2"
        selectedPayment2.textContent = "Numero Carta: " +spanElements[1].textContent;
        // Inserisci il testo del terzo span nell'elemento span con id "selectedPayment3"
        selectedPayment3.textContent = "Data Scadenza: " +spanElements[2].textContent;

        document.getElementById('paymentOptions').style.display = 'none';
        document.getElementById('selectedPayment').style.display = 'block';
        document.getElementById('selectedPayment2').style.display = 'block';
        document.getElementById('selectedPayment3').style.display = 'block';

    }

    function selectIndirizzi() {
        var selectedMethod = document.querySelector('input[name="indirizzi"]:checked');
        var selectedLabel = document.querySelector('label[for="' + selectedMethod.id + '"]');

        var spanElements = selectedLabel.querySelectorAll('span'); // Seleziona tutti gli span all'interno della label
        var selectedPayment = document.getElementById('selectedPayment4');
        var selectedPayment2 = document.getElementById('selectedPayment5');
        var selectedPayment3 = document.getElementById('selectedPayment6');
        var selectedPayment4 = document.getElementById('selectedPayment7');

        // Inserisci il testo del primo span nell'elemento span con id "selectedPayment"
        selectedPayment.textContent = "Nome: " +spanElements[0].textContent;
        // Inserisci il testo del secondo span nell'elemento span con id "selectedPayment2"
        selectedPayment2.textContent = "Indirizzo: " +spanElements[1].textContent;
        // Inserisci il testo del terzo span nell'elemento span con id "selectedPayment3"
        selectedPayment3.textContent = "Provincia: " +spanElements[2].textContent;

        selectedPayment4.textContent = "Città: " +spanElements[3].textContent;


        document.getElementById('indirizziOptions').style.display = 'none';
        document.getElementById('selectedPayment4').style.display = 'block';
        document.getElementById('selectedPayment5').style.display = 'block';
        document.getElementById('selectedPayment6').style.display = 'block';
        document.getElementById('selectedPayment7').style.display = 'block';

    }

    function showPaymentOptions() {
        document.getElementById('paymentOptions').style.display = 'block';
    }

    function showAddressOptions() {
        document.getElementById('indirizziOptions').style.display = 'block';
    }

    function aggiungiQuantita(index, limite) {
        // Ottieni il valore attuale della quantità
        var quantitaSpan = document.getElementById('quantita' + index);
        var quantitaVal = parseInt(quantitaSpan.textContent);

        // Aumenta la quantità di 1
        quantitaVal++;

        if(quantitaVal <= limite) {
            // Aggiorna il valore visualizzato nella pagina
            quantitaSpan.textContent = quantitaVal;

            var rimuovi = document.getElementById('rimuovi' + index);
            rimuovi.removeAttribute('style');

            // Invia una richiesta AJAX per aggiornare la quantità sul server
            $.ajax({
                url: 'OrdiniControl?action=AggiornaQuantita',
                type: 'POST',
                data: {index: index, quantita: quantitaVal},
                success: function (prezzoTotale){
                    var prezzoTot = document.getElementById('totale_numero');
                    prezzoTot.textContent = prezzoTotale;
                },
                error: function (xhr, status, error) {
                    // Gestisci eventuali errori
                    console.error(error);
                }
            });
        }
        if(quantitaVal === 5){
            var aggiungi = document.getElementById('aggiungi' + index);
            aggiungi.style.backgroundColor = "rgb(141,141,141)";
        }
    }

    function rimuoviQuantita(index) {
        // Ottieni il valore attuale della quantità
        var quantitaSpan = document.getElementById('quantita' + index);
        var quantitaVal = parseInt(quantitaSpan.textContent);

        quantitaVal--;
        if(quantitaVal > 0) {
            // Aggiorna il valore visualizzato nella pagina
            quantitaSpan.textContent = quantitaVal;

            var aggiungi = document.getElementById('aggiungi' + index);
            aggiungi.removeAttribute('style');

            // Invia una richiesta AJAX per aggiornare la quantità sul server
            $.ajax({
                url: 'OrdiniControl?action=AggiornaQuantita',
                type: 'POST',
                data: {index: index, quantita: quantitaVal},
                success: function (prezzoTotale){
                    var prezzoTot = document.getElementById('totale_numero');
                    prezzoTot.textContent = prezzoTotale;
                },
                error: function (xhr, status, error) {
                    // Gestisci eventuali errori
                    console.error(error);
                }
            });
        }

        if(quantitaVal === 1){
            var rimuovi = document.getElementById('rimuovi' + index);
            rimuovi.style.backgroundColor = "rgb(141,141,141)";
        }
    }

    function checkout() {
        var selectedPaymentMethod = document.querySelector('input[name="paymentMethod"]:checked');
        var selectedAddress = document.querySelector('input[name="indirizzi"]:checked');

        // Verifica se almeno una opzione è stata selezionata sia per i metodi di pagamento che per gli indirizzi
        if (selectedPaymentMethod && selectedAddress) {
            location.href="OrdiniControl?action=Checkout";
        } else {
            // Mostra un messaggio di errore o impedisce il completamento del checkout
            alert("Seleziona un metodo di pagamento e un indirizzo di spedizione prima di procedere con il checkout.");
        }
    }

</script>

</body>
</html>
