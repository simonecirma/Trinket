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
    <div class= "card">
        <div id="datiPersonali" class="informazioniProfilo">
            <div class="card2">
                <div class="card3">
                    <form class="form" action="UtenteControl?action=ModificaInformazioni" method="post" name="modificaInformazioni" onsubmit="return validate()">
                        <p id="heading">I Tuoi Dati</p>

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
        <div id="metodiPagamento" class="informazioniProfilo" style="display: none;">
            <div class="card4">
                <div class="card__front card__part">
                    <div class="loghiCarta">
                        <div class="Chip">
                            <img src="Immagini/Chip.png" alt="Chip">
                        </div>
                        <div class="Nfc">
                            <img src="Immagini/Nfc.png" alt="Nfc">
                        </div>
                        <div class="Mastercard">
                            <img src="Immagini/Mastercard.png" alt="Mastercard">
                        </div>
                    </div>
                    <div class="card_numer">
                        <div>**** **** **** 6258</div>
                    </div>
                    <div class="card__space-75">
                        <span class="card__label">Intestatario</span>
                        <p class="card__info">John Doe</p>
                    </div>
                    <div class="card__space-25">
                        <span class="card__label">Scadenza:</span>
                        <p class="card__info">10/25</p>
                    </div>
                </div>

                <div class="card__back card__part">
                    <div class="card__black-line"></div>
                    <div class="card__back-content">
                        <div class="card__secret">
                            <p class="card__secret--last">420</p>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div id="indirizziSpedizione" class="informazioniProfilo" style="display: none;">
            PAAAAATT
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
            var sections = document.getElementsByClassName('informazioniProfilo');
            for (var i = 0; i < sections.length; i++) {
                sections[i].style.display = 'none';
            }
            document.getElementById(sectionId).style.display = 'block';
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
    </script>
</body>
</html>
