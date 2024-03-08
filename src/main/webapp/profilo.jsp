<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/profilo.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha384-UG8ao2jwOWB7/oDdObZc6ItJmwUkR/PfMyt9Qs5AwX7PsnYn1CRKCTWyncPTWvaS" crossorigin="anonymous"></script>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <div class="container">
        <div class="intestazioneProfilo">
            <div class="fotoProfilo">
                <form method="post" action="UtenteControl?action=ImmagineProfilo" enctype="multipart/form-data">
                    <% if(immagine.isEmpty()){ %>
                        <img src="Immagini/ImgUtente/iconaProfilo2.jpg" class="iconaProfilo" alt="Foto Profilo">
                        <input type="file" id="uploadInput" style="display: none;" name="immagineProfilo">
                        <button id="uploadLabel" class="uploadLabel" type="button" onclick="document.getElementById('uploadInput').click()">Scegli Immagine</button>
                    <% }else{ %>
                        <img src="Immagini/ImgUtente/<%=immagine%>" class="iconaProfilo" alt="Foto Profilo">
                        <input type="file" id="uploadInput" style="display: none;" name="immagineProfilo">
                        <button id="uploadLabel" class="uploadLabel" type="button" onclick="document.getElementById('uploadInput').click()">Cambia Immagine</button>
                    <% } %>
                    <button id="submitButton" class="uploadLabel" type="submit" style="display: none;">Salva</button>
                </form>
            </div>
            <div class="nomeUtente"> <%=nome%> <%= cognome%> </div>
        </div>

        <div class="menu">
            <table>
                <caption style="display: none;">Informazioni Profilo</caption>
                <tr onclick="showSection('datiPersonali')">
                    <th><button class="infProfilo">Dati personali</button></th>
                </tr>
                <tr onclick="showSection('metodiPagamento')">
                    <th><button class="infProfilo">Metodi di pagamento</button></th>
                </tr>
                <tr onclick="showSection('indirizziSpedizione')">
                    <th><button class="infProfilo">Indirizzi di spedizione</button></th>
                </tr>
            </table>
        </div>
    </div>
    <div id="datiPersonali" class= "card">
        <div class="card2">
            <div class="card3">
                <form class="form" action="UtenteControl?action=ModificaInformazioni" method="post" name="modificaInformazioni" onsubmit="return validate()">
                    <p class="heading">I Tuoi Dati</p>

                    <div class="field">
                        <input type="text" name ="nome" class="input-field" placeholder="Nome" value=<%= nome%>>
                    </div>

                    <div class="field">
                        <input type="text" name ="cognome" class="input-field" placeholder="Cognome" value=<%= cognome%>>
                    </div>

                    <div class="field">
                        <input type="email" name ="email" class="input-field" placeholder="Email" value=<%= email%>>
                    </div>

                    <div class="field">
                        <input type="password" name ="password" class="input-field" placeholder="Password" value=<%= password%>>
                    </div>

                    <div class="field">
                        <input type="password" name ="password2" class="input-field" placeholder="Conferma Password" value=<%= password%>>
                    </div>

                    <div class="field">
                        <label for="dataDiNascita">Data Di Nascita:</label>
                        <input type="date" name ="dataDiNascita" id="dataDiNascita" class="input-field" placeholder="Data Di Nascita" value=<%= dataDiNascita%>>
                    </div>

                    <button type="submit" class="button3">Salva!</button>
                </form>
            </div>
        </div>
    </div>
    <div id="metodiPagamento" class="card" style="display: none;">
        <div class="barraMetodi">
            <button type="button" class="aggiungiMetodo" onclick="showSection('inserimentoCarte')">Nuova Carta</button>
            <button type="button" class="rimuoviMetodo">Elimina Carta</button>
        </div>
    </div>
    <div id="inserimentoCarte" class="card" style="display: none;">
        <div class="card2">
            <div class="card3">
                <form class="form" action="UtenteControl?action=AggiungiMetodo" method="post" name="inserimentoCarta" onsubmit="return validate2()">
                    <p class="heading">Inserisci Metodo Di Pagamento</p>

                    <div class="field">
                        <input type="text" name ="intestatario" class="input-field" placeholder="Nome e Cognome Proprietario" required>
                    </div>

                    <div class="field">
                        <input type="text" name ="numeroCarta" class="input-field" placeholder="Numero Carta" required>
                    </div>

                    <div class="field">
                        <input type="date" name ="dataScadenza" class="input-field" placeholder="Data di Scadenza" required>
                    </div>

                    <div class="field">
                        <input type="text" name ="cvv" class="input-field" placeholder="Cvv" required>
                    </div>

                    <button type="submit" class="button3">Salva!</button>
                </form>
            </div>
        </div>
    </div>
    <div id="indirizziSpedizione" class="card" style="display: none;">
        <div class="barraMetodi">
            <button type="button" class="aggiungiMetodo">Nuovo Indirizzo</button>
            <button type="button" class="rimuoviMetodo">Elimina Indirizzo</button>
        </div>
    </div>

    <script>
        document.getElementById("uploadInput").addEventListener("change", function () {
            var file = this.files[0];
            if (file) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    document.querySelector(".iconaProfilo").src = e.target.result;
                }
                reader.readAsDataURL(file);
                document.getElementById("uploadLabel").style.display = "none";
                document.getElementById("submitButton").style.display = "inline-block";
            }
        });

        function showSection(sectionId) {
            var sections = document.getElementsByClassName('card');
            for (var i = 0; i < sections.length; i++) {
                sections[i].style.display = 'none';
            }
            if(sectionId === "datiPersonali" || sectionId === "inserimentoCarte") {
                document.getElementById(sectionId).style.display = 'flex';
            }else{
                document.getElementById(sectionId).style.display = 'block';
            }
        }

        function validate() {
            var email = document.modificaInformazioni.email.value;
            var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            var password = document.modificaInformazioni.password.value;
            var password2 = document.modificaInformazioni.password2.value;
            var passwordPattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*.-_])(?=.{8,})/;
            var dataDiNascitaInput = document.modificaInformazioni.dataDiNascita.value;
            var dataDiOggi = new Date();
            var dataLimite = new Date();
            dataLimite.setFullYear(dataDiOggi.getFullYear() - 18);

            if (!passwordPattern.test(password)) {
                alert("La Password è errata! Deve contenere almeno 8 caratteri, una lettera maiuscola, una lettera minuscola, un numero e un carattere speciale!");
                document.modificaInformazioni.password.focus();
                return false;
            }

            if (password !== password2) {
                alert("Le Password sono diverse! ");
                document.modificaInformazioni.password2.focus();
                return false;
            }

            var dataDiNascita = new Date(dataDiNascitaInput);

            if(dataDiNascita > dataDiOggi){
                alert("Inserisci una data valida");
                document.modificaInformazioni.dataDiNascita.focus();
                return false;
            }else if(dataDiNascita > dataLimite){
                alert("Per registrarti devi avere almeno 18 anni");
                document.modificaInformazioni.dataDiNascita.focus();
                return false;
            }

            if(!emailPattern.test(email)){
                alert("Inserire una email valida! ");
                document.modificaInformazioni.email.focus();
                return false;
            }else{
                $.ajax({
                    url: 'AccessoControl?action=verificaEmail',
                    type: 'POST',
                    data: {email: email},
                    success: function(response){
                        if(response === "trovato"){
                            alert("Esiste già un account con questa Email! ");
                        }else {
                            document.modificaInformazioni.submit();
                        }
                    },
                    error: function(){
                        alert("Si è verificato un errore durante la verifica della email! ");
                    }
                });
                return false;
            }
        }

        function validate2(){
            var numeroCarta = document.inserimentoCarta.numeroCarta.value;
            var cvv = document.inserimentoCarta.cvv.value;
            var dataScadenzaInput = document.inserimentoCarta.dataScadenza.value;
            var dataDiOggi = new Date();

            if(numeroCarta.length !== 16) {
                alert("Il numero della carta di credito deve contenere esattamente 16 numeri.");
                document.inserimentoCarta.numeroCarta.focus();
                return false;
            }

            if(cvv.length !== 3) {
                alert("Il cvv della carta di credito deve contenere esattamente 3 numeri.");
                document.inserimentoCarta.numeroCarta.focus();
                return false;
            }

            var dataScadenza = new Date(dataScadenzaInput);

            if(dataScadenza < dataDiOggi) {
                alert("Inserisci una data valida");
                document.modificaInformazioni.dataDiNascita.focus();
                return false;
            }
        }

        $(document).ready(function() {
            if(window.location.hash === "#metodiPagamento") {
                $('#metodiPagamento').show();
                $('#datiPersonali').hide();
            }
            // Effettua una richiesta AJAX per ottenere i dati dalla servlet
            $.ajax({
                url: 'UtenteControl?action=OttieniMetodiPagamento',
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    // Itera su ogni oggetto nella lista di prodotti
                    $.each(response, function(index, carte) {
                        // Costruisci il markup HTML per il prodotto
                        var html = '<div class="card4" id="card_'+carte.numeroCarta+'" data-id="' +carte.numeroCarta+ '">';
                        html += '<button type="button" class="rimuoviCarta" >X</button>';
                        html += '<div class="card__front card__part">';
                        html += '<div class="loghiCarta">';
                        html += '<div class="Chip">';
                        html += '<img src="Immagini/Chip.png" alt="Chip">';
                        html += '</div>';
                        html += '<div class="Nfc">';
                        html += '<img src="Immagini/Nfc.png" alt="Nfc">';
                        html += '</div>';
                        html += '<div class="Mastercard">';
                        html += '<img src="Immagini/Mastercard.png" alt="Mastercard">';
                        html += '</div>';
                        html += '</div>';
                        html += '<div class="card_numer" id="card_numer">';
                        html += numeroCarta(carte.numeroCarta);
                        html += '</div>';
                        html += '<div class="card__space-75">';
                        html += '<span class="card__label"> Intestatario </span>';
                        html += '<p class="card__info">' +carte.titolare+ '</p>';
                        html += '</div>';
                        html += '<div class="card__space-25">';
                        html += '<span class="card__label"> Scadenza </span>';
                        html += '<p class="card__info">' +carte.scadenza+ '</p>';
                        html += '</div>';
                        html += '</div>';
                        html += '<div class="card__back card__part">';
                        html += '<div class="card__black-line"> </div>';
                        html += '<div class="card__back-content">';
                        html += '<div class="card__secret">';
                        html += '<p class="card__secret--last">' +carte.cvv+ '</p>';
                        html += '</div>';
                        html += '</div>';
                        html += '</div>';
                        html += '</div>';

                        // Aggiungi il markup HTML alla pagina
                        $('#metodiPagamento').append(html);

                        var numElementi = response.length;
                        if (numElementi === 2) {
                            $('.card4').removeClass().addClass('card2Elem');
                        } else if (numElementi === 3) {
                            $('.card4').removeClass().addClass('card3Elem');
                        }else if(numElementi > 3){
                            $('.card4').removeClass().addClass('cardMoltiElem');
                        }
                    });
                },
                error: function(xhr, status, error) {
                    // Gestisci eventuali errori di richiesta
                    console.error('Errore durante la richiesta AJAX:', error);
                }
            });
        });

        function numeroCarta(stringa) {
            // Divide la stringa in blocchi di 4 caratteri
            var blocchi = stringa.match(/.{1,4}/g);

            var html = '<div class="blocco">' +blocchi[0]+ '</div>';
            html += '<div class="blocco">' +blocchi[1]+ '</div>';
            html += '<div class="blocco">' +blocchi[2]+ '</div>';
            html += '<div class="blocco">' +blocchi[3]+ '</div>';

            return html;
        }

        $(document).ready(function() {
            // Funzione per rimuovere una carta
            function rimuoviCarta(idCarta) {
                // Effettua una richiesta AJAX per rimuovere la carta dal database
                $.ajax({
                    url: 'UtenteControl?action=RimuoviMetodo',
                    type: 'POST',
                    data: { idCarta: idCarta },
                    success: function(response) {
                        // Rimuovi il div card4 dalla pagina
                        $('#carta_' + idCarta).remove();
                    },
                    error: function(xhr, status, error) {
                        console.error('Errore durante la rimozione della carta:', error);
                    }
                });
            }

            // Aggiungi evento click ai bottoni di eliminazione
            $(document).ready(function() {
                $('.rimuoviMetodo').click(function() {
                    var carte = document.getElementsByClassName('rimuoviCarta');
                    for (var i = 0; i < carte.length; i++) {
                        if(carte[i].style.display === 'none') {
                            carte[i].style.display = 'block';
                        }else{
                            carte[i].style.display = 'none';
                        }
                    }
                });

                $(document).on('click', '.rimuoviCarta', function() {
                    var idCarta = $(this).closest('[data-id]').data('id');
                    rimuoviCarta(idCarta);
                    location.reload();
                });
            });
        });

    </script>
</body>
</html>
