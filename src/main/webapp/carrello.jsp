<%@ page import="com.example.trinket.model.bean.MetodoPagamentoBean" %>
<%@ page import="com.example.trinket.model.bean.IndirizzoBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<% List<IndirizzoBean> indirizzi = (List<IndirizzoBean>) request.getAttribute("indirizzi");
 List<MetodoPagamentoBean> metodi = (List<MetodoPagamentoBean>) request.getAttribute("metodi"); %>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/carrello.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="tutto">
    <div class="pacchetti">

    </div>

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
</script>

</body>
</html>
