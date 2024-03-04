<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Easy Travel</title>
    <link href="CSS/profilo.css" rel="stylesheet" type="text/css">
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
                        <button id="uploadLabel" class="uploadLabel" type="button" onclick="document.getElementById('uploadInput').click()">Scegli Immagine</button>
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
                    <th>Dati personali</th>
                </tr>
                <tr onclick="showSection('metodiPagamento')">
                    <th>Metodi di pagamento</th>
                </tr>
                <tr onclick="showSection('indirizziSpedizione')">
                    <th>Indirizzi di spedizione</th>
                </tr>
            </table>
        </div>
    </div>
    <div class= "card">
        <div id="datiPersonali" class="informazioniProfilo">
            CIAOOOOO
        </div>
        <div id="metodiPagamento" class="informazioniProfilo" style="display: none;">
            MAAAMMT
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
    </script>
</body>
</html>
