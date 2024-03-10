// Funzione per caricare le province dal JSON
function caricaProvince() {
    fetch('js/citta.json')
        .then(response => response.json())
        .then(data => {
            const provinciaSelect = document.getElementById('provincia');
            data.province.forEach(provincia => {
                let option = document.createElement('option');
                option.textContent = provincia;
                option.value = provincia;
                provinciaSelect.appendChild(option);
            });
        });
}

// Funzione per caricare i comuni in base alla provincia selezionata
function caricaComuni() {
    const provinciaSelect = document.getElementById('provincia');
    const comuneSelect = document.getElementById('comune');

    provinciaSelect.addEventListener('change', () => {
        comuneSelect.innerHTML = ''; // Pulisce le opzioni precedenti

        const provinciaSelezionata = provinciaSelect.value;
        const comuni = datiComuni[provinciaSelezionata];

        comuni.forEach(comune => {
            let option = document.createElement('option');
            option.textContent = comune;
            option.value = comune;
            comuneSelect.appendChild(option);
        });
    });
}

// Carica le province all'avvio
caricaProvince();

// Dati citta.json caricati
let datiComuni = null;
fetch('js/citta.json')
    .then(response => response.json())
    .then(data => {
        datiComuni = data.comuni;
        caricaComuni();
    });
