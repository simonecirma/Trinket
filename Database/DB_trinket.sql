DROP DATABASE IF EXISTS trinket;
CREATE DATABASE trinket;
USE trinket;

CREATE TABLE Utente
(
    Nome varchar(20) NOT NULL,
    Cognome varchar (20) NOT NULL,
    Email varchar (50) NOT NULL,
    Password varchar (20) NOT NULL,
    DataDiNascita date NOT NULL,
    NumeroTelefono varchar (20) NOT NULL,
    Immagine varchar (50) NOT NULL,
    FlagAmm bit NOT NULL,
    PRIMARY KEY (Email)
);

CREATE TABLE IndirizzoSpedizione
(
    IDIndirizzo int NOT NULL AUTO_INCREMENT,
    Indirizzo varchar (50) NOT NULL,
    NumeroCivico int NOT NULL,
    CAP int NOT NULL,
    Città varchar (100) NOT NULL,
    Provincia varchar (20) NOT NULL,
    PRIMARY KEY (IDIndirizzo)
);

CREATE TABLE Inserisce
(
    IDIndirizzo int NOT NULL,
    Email varchar (50) NOT NULL,
    FOREIGN KEY (IDIndirizzo) REFERENCES IndirizzoSpedizione(IDIndirizzo) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (Email) REFERENCES Utente(Email) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE MetodoDiPagamento
(
    NumeroCarta char (16) NOT NULL,
    Scadenza date NOT NULL,
    Titolare varchar (50) NOT NULL,
    PRIMARY KEY (NumeroCarta)
);

CREATE TABLE Possiede
(
    NumeroCarta char (16) NOT NULL,
    Email varchar (50) NOT NULL,
    FOREIGN KEY (NumeroCarta) REFERENCES MetodoDiPagamento(NumeroCarta) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (Email) REFERENCES Utente(Email) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE StatoOrdine
(
    Stato varchar (100) NOT NULL,
    PRIMARY KEY (Stato)
);

CREATE TABLE Ordine
(
    IdOrdine int NOT NULL AUTO_INCREMENT,
    DataAcquisto date NOT NULL,
    Fattura varchar(100),
    PrezzoTotale float,
    StatoOrdine varchar (30) NOT NULL,
    Email varchar (50)  NOT NULL,
    PRIMARY KEY (IdOrdine),
    FOREIGN KEY (StatoOrdine) REFERENCES StatoOrdine(Stato) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (Email) REFERENCES Utente(Email) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Tipologia
(
    TipoPacchetto varchar (50) NOT NULL,
    PRIMARY KEY (TipoPacchetto)
);

CREATE TABLE Durata
(
    NumGiorni int NOT NULL,
    PRIMARY KEY (NumGiorni)
);

CREATE TABLE Pacchetto
(
    CodSeriale varchar (20) NOT NULL,
    Nome varchar (100) NOT NULL,
    Prezzo float NOT NULL,
    DescrizioneRidotta text NOT NULL,
    Descrizione text NOT NULL,
    Tipo varchar (50) NOT NULL,
    NumGiorni int NOT NULL,
    NumPacchetti int NOT NULL,
    FlagDisponibilità bit NOT NULL,
    PRIMARY KEY (CodSeriale),
    FOREIGN KEY (Tipo) REFERENCES Tipologia(TipoPacchetto) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (NumGiorni) REFERENCES Durata(NumGiorni) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Immagini
(
    Nome varchar (50) NOT NULL,
    Codice varchar (20) NOT NULL,
    FlagCopertina bit NOT NULL,
    PRIMARY KEY (Nome),
    FOREIGN KEY (Codice) REFERENCES Pacchetto(CodSeriale) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Composto
(
    Quantità int NOT NULL,
    CodSeriale varchar (20) NOT NULL,
    IdOrdine int NOT NULL,
    FOREIGN KEY (CodSeriale) REFERENCES Pacchetto(CodSeriale) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (IdOrdine) REFERENCES Ordine(IdOrdine) ON UPDATE cascade ON DELETE cascade
);

INSERT INTO Utente (Nome, Cognome, Email, Password, DataDiNascita, NumeroTelefono,Immagine, FlagAmm)
VALUES ('Donato','Folgieri','df@gmail.com','donato','2001-07-21','+393917598493','',0);
INSERT INTO Utente (Nome, Cognome, Email, Password, DataDiNascita, NumeroTelefono,Immagine, FlagAmm)
VALUES ('Simone','Cirma','sc@gmail.com','simone', '2001-05-27','+393478902231','',1);
INSERT INTO Utente (Nome, Cognome, Email, Password, DataDiNascita, NumeroTelefono,Immagine, FlagAmm)
VALUES ('Giuseppe','Rossi','gp@gmail.com','giuseppe', '1965-03-06','+393756789768','',0);

INSERT INTO IndirizzoSpedizione (Indirizzo, NumeroCivico, CAP, Città, Provincia)
VALUES ('Via Barracco', '4', '81027', 'San Felice a Cancello', 'CE');
INSERT INTO IndirizzoSpedizione (Indirizzo, NumeroCivico, CAP, Città, Provincia)
VALUES ('Via Cupa', '68', '81028', 'Santa Maria A Vico', 'CE');
INSERT INTO IndirizzoSpedizione (Indirizzo, NumeroCivico, CAP, Città, Provincia)
VALUES ('Via Napoli','23', '81024', 'Maddaloni', 'CE');

INSERT INTO Inserisce (IDIndirizzo, Email)
VALUES ('01', 'df@gmail.com');
INSERT INTO Inserisce (IDIndirizzo, Email)
VALUES ('02', 'sc@gmail.com');
INSERT INTO Inserisce (IDIndirizzo, Email)
VALUES ('03', 'gp@gmail.com');

SET @CartaDonato='1234567891234567';
SET @CartaSimone='9876543210987654';
SET @CartaGiuseppe='5974682304568792';

INSERT INTO MetodoDiPagamento (NumeroCarta, Scadenza, Titolare)
VALUES (@CartaDonato, '2029-05-01','Donato Folgieri');
INSERT INTO MetodoDiPagamento (NumeroCarta, Scadenza, Titolare)
VALUES (@CartaSimone, '2030-06-01','Simone Cirma');
INSERT INTO MetodoDiPagamento (NumeroCarta, Scadenza, Titolare)
VALUES (@CartaGiuseppe, '2028-01-01','Giuseppe Rossi');

INSERT INTO Possiede (NumeroCarta, Email)
VALUES (@CartaDonato,'df@gmail.com');
INSERT INTO Possiede (NumeroCarta, Email)
VALUES (@CartaSimone,'sc@gmail.com');
INSERT INTO Possiede (NumeroCarta, Email)
VALUES (@CartaGiuseppe,'gp@gmail.com');

INSERT INTO StatoOrdine (Stato)
VALUES ('In Elaborazione');
INSERT INTO StatoOrdine (Stato)
VALUES ('Spedito');
INSERT INTO StatoOrdine (Stato)
VALUES ('In Consegna');
INSERT INTO StatoOrdine (Stato)
VALUES ('Consegnato');

INSERT INTO Ordine (DataAcquisto, PrezzoTotale, StatoOrdine, Email)
VALUES ('2022-01-01','200.00','In Elaborazione','df@gmail.com');
INSERT INTO Ordine (DataAcquisto, PrezzoTotale, StatoOrdine, Email)
VALUES ('2023-05-05','59.00','Consegnato','sc@gmail.com');
INSERT INTO Ordine (DataAcquisto, PrezzoTotale, StatoOrdine, Email)
VALUES ('2023-03-03','400.00','In Consegna','gp@gmail.com');

INSERT INTO Tipologia (TipoPacchetto)
VALUES ('Terme e SPA');
INSERT INTO Tipologia (TipoPacchetto)
VALUES ('Weekend Romantico');
INSERT INTO Tipologia (TipoPacchetto)
VALUES ('Agriturismi e B&B');
INSERT INTO Tipologia (TipoPacchetto)
VALUES ('Soggiorno Classico');
INSERT INTO Tipologia (TipoPacchetto)
VALUES ('Soggiorno Insolito');
INSERT INTO Tipologia (TipoPacchetto)
VALUES ('Corso di Cucina');
INSERT INTO Tipologia (TipoPacchetto)
VALUES ('Esperienze Gastronomiche');

INSERT INTO Durata (NumGiorni)
VALUES ('1');
INSERT INTO Durata (NumGiorni)
VALUES ('2');
INSERT INTO Durata (NumGiorni)
VALUES ('3');

SET @SMARTBOXE1code := 'DFG54QR398';
SET @SMARTBOXE2code := 'AHER1540MN';
SET @SMARTBOXE3code := 'A983NHF9QP';
SET @SMARTBOXE4code := 'MNJO95T3FX';
SET @SMARTBOXE5code := '098ITSBGF7';
SET @SMARTBOXE6code := 'MNWS80PLDM';
SET @SMARTBOXE7code := 'MMMNNNJHO9';
SET @SMARTBOXE8code := 'NASFGHJTUI8';
SET @SMARTBOXE9code := 'MONIBUVYCT';
SET @SMARTBOXE10code := '123456789P';


INSERT INTO Pacchetto(CodSeriale, Nome, Prezzo, DescrizioneRidotta, Descrizione, Tipo, NumGiorni, NumPacchetti, FlagDisponibilità)
VALUES(@SMARTBOXE1code, 'Ingresso giornaliero alle Terme Stufe di Nerone nei Campi Flegrei per 2', '55.11', 'Per 2 persone, un ingresso giornaliero composto da: piscine, prati, sorgenti, fango, percorso kneipp, vasche idromassaggio, cascatina, grotte termali e giardino d inverno', 'A due passi da Napoli, ti aspetta una giornata di puro relax nei bagni termali delle antiche Terme Stufe di Nerone per 2 persone. Godi di tutti i benefici dell acqua ricca di minerali con 1 ingresso giornaliero per un ampia varietà di servizi. Concediti un viaggio di benessere nelle ravvivanti risorse termali del sottosuolo flegreo, famoso sin dai tempi romani. Nuota nelle piscine termali interne o esterne e immergiti nelle grotte naturali. Concediti un bagno di fango purificante o prova il percorso Kneipp per le gambe. Rilassati nelle vasche idromassaggio e nella cascatina. E per finire, goditi il silenzio del parco termale tra sorgenti naturali, laghetti e prati incastonati nello splendido scenario dei Campi Flegrei. Lascia che stress e tensioni si dissolvano nell acqua!', 'Terme e SPA', '1','5', 1);
INSERT INTO Pacchetto(CodSeriale, Nome, Prezzo, DescrizioneRidotta, Descrizione, Tipo, NumGiorni, NumPacchetti, FlagDisponibilità)
VALUES(@SMARTBOXE2code, '1 notte paradisiaca in esclusivo resort in Salento con passeggiata a cavallo in spiaggia', '159.99', 'Per 2 persone, una notte in camera matrimoniale Standard, una colazione a Buffet, una passeggiata a cavallo in spiaggia (1h)', 'I sentieri tra gli uliveti, la splendida piscina lagunare, la macchia mediterranea da un lato e il mare cristallino dall altro: Eden Resort Country ti aspetta per 1 notte con colazione per 2 persone in un angolo di paradiso sulla Terra, o meglio, sulla sabbia bianca delle spiagge salentine. Parti per una piccola vacanza rilassante a Ugento, all interno di un resort che, nonostante la completa ristrutturazione, conserva ancora l anima delle antiche tenute contadine del Salento. Lasciati viziare fin dal mattino, a colazione, con il tipico pane del forno locale e con la frutta fresca proveniente direttamente dal giardino della struttura. Goditi una suggestiva passeggiata a cavallo dalla durata di circa 1 ora, lungo i chilometri di sabbia bianca finissima del litorale, costeggiato da dune e dalla ricca vegetazione mediterranea. Il paradiso è in Salento e ti aspetta!', 'Soggiorno Classico', '1','5', 1);
INSERT INTO Pacchetto(CodSeriale, Nome, Prezzo, DescrizioneRidotta, Descrizione, Tipo, NumGiorni, NumPacchetti, FlagDisponibilità)
VALUES(@SMARTBOXE3code, 'Palermo da scoprire: 2 notti per 2 nel capoluogo della Sicilia', '199.90', '2 notti con colazione per 2 persone 6 meravigliosi soggiorni nel capoluogo siciliano', 'Esplora Palermo e le sue meraviglie durante un soggiorno di 2 notti con colazione per 2 persone nel soleggiato capoluogo della Sicilia. Parti alla scoperta dei tipici e vivaci mercati cittadini, mangia un gustoso gelato vista mare o fermati per un momento in una caratteristica piazza ombreggiata. La sera rilassati in un elegante palazzo liberty risalente ai primi del Novecento, in una deliziosa locanda nel cuore del centro storico della città o in un moderno hotel 3* o 4* che Smartbox ha selezionato per te. Fai il pieno di energie al mattino con la colazione inclusa nel soggiorno e poi esplora le imponenti chiese barocche e gli edifici dall architettura arabo-normanna che costellano la città. Goditi un meraviglioso soggiorno siciliano in una città tutta da scoprire.', 'Soggiorno Classico', '2','5', 1);
INSERT INTO Pacchetto(CodSeriale, Nome, Prezzo, DescrizioneRidotta, Descrizione, Tipo, NumGiorni, NumPacchetti, FlagDisponibilità)
VALUES(@SMARTBOXE4code, 'Due cuori e una bolla: 1 notte in Bubble Suite in Toscana', '209.90', 'Per 2 persone, una notte in Bubble Suite, una colazione Internazionale', 'Cosa c è più romantico di una notte sotto le stelle? Addormentati contando le stelle all interno di una Bubble Suite immersa nella campagna toscana. Trascorri 1 insolita notte per 2 persone all interno di una bolla dalle caratteristiche di una suite di design con vasca idromassaggio. Al mattino, risvegliati con il profumo dell oliveta storica che circonda la struttura e poi goditi una lenta e abbondante colazione. Insieme alla tua dolce metà, goditi lunghe passeggiate in campagna e lasciati ammaliare dalla tranquillità tipica delle colline toscane. Oppure trascorri qualche ora al mare: la struttura si trova infatti a Vada, una delle località più belle della costa tirrenica. Con l incredibile scenario naturale in cui è immersa la struttura, non avrai bisogno d altro che di due cuori e una bolla per vivere dei momenti da sogno in Toscana.', 'Soggiorno Insolito', '1','5', 1);
INSERT INTO Pacchetto(CodSeriale, Nome, Prezzo, DescrizioneRidotta, Descrizione, Tipo, NumGiorni, NumPacchetti, FlagDisponibilità)
VALUES(@SMARTBOXE5code, '2 suggestive notti in Toscana in Yurta tradizionale con Prosecco', '284.90', 'Per 2 persone, due notti in tenda Yurta, una bottiglia di Prosecco biodinamico', 'Regalati una pausa in Toscana e fuggi insieme a chi ami con Smartbox! L Azienda Agrituristica Pereti ti accoglie nel cuore della Maremma per un soggiorno suggestivo e insolito: goditi 2 notti in una Yurta construita sapientemente all ombra di un querceto antico, e fai tua l emozione di una parentesi nella natura della Toscana. Ti aspetta una pausa all insegna della disconnessione, lontano dalla stressante routine quotidiana. Passeggia tra gli alberi, aguzza gli occhi o l udito per ascoltare i suoni della vita segreta del bosco. Al tramonto, prendi per mano di chi ami e celebra il vostro amore: stappa la bottiglia di Prosecco biodinamico e brinda alla salute e alla natura!', 'Soggiorno Insolito', '2','5', 1);
INSERT INTO Pacchetto(CodSeriale, Nome, Prezzo, DescrizioneRidotta, Descrizione, Tipo, NumGiorni, NumPacchetti, FlagDisponibilità)
VALUES(@SMARTBOXE6code, 'Saperi e sapori tradizionali: 1 notte in agriturismo con cena', '99.90', '1 notte in agriturismo con cena per 2 persone, 73 soggiorni in agriturismi rurali all insegna della tradizione contadina', 'Pianura, collina o mare, prenditi una pausa da vivere insieme a chi vuoi al ritmo lento della natura. Smartbox ti invita per un soggiorno di 2 giorni alla scoperta dell Italia rurale, da Nord a Sud. Trascorri 1 notte con colazione in un agriturismo a scelta per 2 persone e gusta 1 cena tipica. Scegli tra luoghi unici ricavati da antichi casolari e cascine per vivere pienamente la tradizione contadina italiana. Perditi per le stradine dei borghi, tra i sentieri, le chiesette e i tesori nascosti. Un mosaico di saperi da conoscere e sapori da provare. Completa la tua breve fuga con le storie e le curiosità dietro alle culture enogastronomiche di ciascun luogo.', 'Agriturismi e B&B', '1','5', 1);
INSERT INTO Pacchetto(CodSeriale, Nome, Prezzo, DescrizioneRidotta, Descrizione, Tipo, NumGiorni, NumPacchetti, FlagDisponibilità)
VALUES(@SMARTBOXE7code, 'Parigi da sogno: 2 notti con colazione e visita con biglietti salta fila alla Torre Eiffel per 2', '409.80', 'Questo cofanetto regalo contiene 2 esperienze: Esperienza 1: due notti con colazione, Esperienza 2: visita guidata e biglietti salta fila per la Torre Eiffel', 'Stai pensando a una fuga romantica? Parigi è sempre una buona idea! Concediti un soggiorno fiabesco nella Ville Lumière: 2 notti con colazione per 2 persone, l ideale per scoprire tutto il fascino dei suoi colori e della Torre Eiffel. Ecco gli ingredienti di una parentesi da sogno in una delle città più belle del mondo! Goditi tutto lo charme di due meravigliose notti in hotel 3 o 4 stelle e ricarica le energie al mattino con una gustosa colazione, in una vacanza indimenticabile da vivere in 2. Prosegui la tua avventura romantica con una vista mozzafiato su Parigi grazie a Universal Tour Group: sali in cima alla Torre Eiffel per una visita guidata con biglietti salta fila! Il tour ti rivelerà tutti i segreti della Dama di Ferro. Raggiungi la cima, a ben 276 metri di altezza, e goditi il panorama unico sulla città. Sei pronto per farti conquistare dall incanto di Parigi? Prepara le valigie e vivi questo viaggio insieme a chi ami. Questo cofanetto include 2 esperienze da prenotare insieme o separatamente, in base alla tua preferenza e alla disponibilità dei partner.', 'Weekend Romantico', '1','5', 1);
INSERT INTO Pacchetto(CodSeriale, Nome, Prezzo, DescrizioneRidotta, Descrizione, Tipo, NumGiorni, NumPacchetti, FlagDisponibilità)
VALUES(@SMARTBOXE8code, 'Accademia della Pasta di Cristina Lunardini: 5 corsi online per mettere le mani in pasta', '199.90', 'Per 1 persone, cinque corsi e cinquanta video-lezioni con dispense, test e attestato di partecipazione dell Accademia della Pasta di Cristina Lunardini (12h)', 'Se anche per te la pasta è sinonimo di felicità, non devi fare altro che cedere alla tentazione! Smartbox ti propone un offerta irresistibile per deliziare il tuo palato e metterti alla prova: entra in Club Academy con Cristina Lunardini. Apprendi l arte della pasta fatta in casa con un ciclo di 5 corsi online: 60 video lezioni per conoscere i capisaldi e le tecniche di un vero chef in grado di creare il prodotto per eccellenza della cucina italiana nel mondo. Grazie all Accademia della Pasta saprai scegliere gli ingredienti ideali per i diversi formati e imparerai a destreggiarti con il mattarello per impastare, stendere e lavorare la sfoglia con le tecniche sapienti di una vera professionista. Un percorso formativo ideale da seguire nel comfort della propria cucina: potrai accedere alle lezioni per 36 mesi senza limiti, quando e dove vorrai. Guarda i video, sfoglia le dispense e ottieni il certificato finale. Paste fresche, ripiene, lasagne e sformati non avranno più segreti! Metti le mani in pasta e porta in tavola tutto il gusto della tradizione.', 'Corso di Cucina', '1','5', 1);
INSERT INTO Pacchetto(CodSeriale, Nome, Prezzo, DescrizioneRidotta, Descrizione, Tipo, NumGiorni, NumPacchetti, FlagDisponibilità)
VALUES(@SMARTBOXE9code, '3 giorni da sogno nella verde Toscana: 2 notti con colazione e 1 degustazione di vini', '159.80', 'Questo cofanetto regalo contiene 2 esperienze: Esperienza 1: due notti con colazione, Esperienza 2: una degustazione di vini in Toscana', 'Goditi un dolce risveglio tra i colori e la quiete dei vigneti toscani! Ecco per te una proposta irresistibile da vivere con una persona speciale: 2 rilassanti notti in Toscana, in confortevoli B&B, agriturismi e hotel 3 stelle accuratamente selezionati per te e la tua dolce metà. Al mattino, fatti coccolare con i sapori deliziosi della colazione e rendi questa parentesi romantica ancora più gustosa con 1 degustazione di vini da scoprire e assaporare nelle migliori cantine e aziende agricole del territorio. Un viaggio sensoriale dedicato a 2 amanti del vino, per lasciarsi sorprendere dal gusto delle etichette più pregiate e dai sentori aromatici più invitanti. Valigie alla mano e in alto i calici, la Toscana ti aspetta!', 'Esperienze Gastronomiche', '1','5', 1);
INSERT INTO Pacchetto(CodSeriale, Nome, Prezzo, DescrizioneRidotta, Descrizione, Tipo, NumGiorni, NumPacchetti, FlagDisponibilità)
VALUES(@SMARTBOXE10code, 'Sotto il sole d Europa: 2 notti da sogno sulle spiagge più belle', '269.99', '2 notti con colazione per 2 persone, 151 soggiorni sulle spiagge più belle d Europa', 'Quale modo migliore di ricaricare le batterie in coppia se non in riva al mare? Immergiti in una favolosa avventuriera costiera in coppia! Parti alla ricerca delle più incantevoli spiagge d  Europa con un soggiorno di 2 notti con deliziose colazioni per 2 persone. Tra Francia, Spagna, Italia, Portogallo e Grecia, la scelta è tra le tue mani: decidi tu dove trascorrere la tua fuga marittima. Una volta arrivati a destinazione, sistema le valigie ed esplora i dintorni. Passeggia lungo i litorali e, con la sabbia tra le dita, concediti il lusso di sdraiarti in spiaggia o di tuffarti nell  acqua scintillante sotto il sole. Goditi la brezza marina, la vista mozzafiato sulle onde e romantici tramonti insieme a chi vuoi!', 'Soggiorno Classico', '1','5', 1);

INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Yurta1.jpg',@SMARTBOXE5code,1);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Yurta2.jpg',@SMARTBOXE5code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Yurta3.jpg',@SMARTBOXE5code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Europa1.jpg',@SMARTBOXE10code,1);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Europa2.jpg',@SMARTBOXE10code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Europa3.jpg',@SMARTBOXE10code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Europa4.jpg',@SMARTBOXE10code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Palermo1.jpg',@SMARTBOXE3code,1);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Palermo2.jpg',@SMARTBOXE3code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Palermo3.jpg',@SMARTBOXE3code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Salento1.jpg',@SMARTBOXE2code,1);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Salento2.jpg',@SMARTBOXE2code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Salento3.jpg',@SMARTBOXE2code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('TermeStufe1.jpg',@SMARTBOXE1code,1);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('TermeStufe2.jpg',@SMARTBOXE1code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('TermeStufe3.jpg',@SMARTBOXE1code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Parigi1.jpg',@SMARTBOXE7code,1);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Parigi2.jpg',@SMARTBOXE7code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Parigi3.jpg',@SMARTBOXE7code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Parigi4.jpg',@SMARTBOXE7code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Bolla1.jpg',@SMARTBOXE4code,1);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Bolla2.jpg',@SMARTBOXE4code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Bolla3.jpg',@SMARTBOXE4code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Agriturismo1.jpg',@SMARTBOXE6code,1);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Agriturismo2.jpg',@SMARTBOXE6code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Agriturismo3.jpg',@SMARTBOXE6code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Toscana1.jpg',@SMARTBOXE9code,1);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Toscana2.jpg',@SMARTBOXE9code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Toscana3.jpg',@SMARTBOXE9code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Toscana4.jpg',@SMARTBOXE9code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Pasta1.jpg',@SMARTBOXE8code,1);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Pasta2.jpg',@SMARTBOXE8code,0);
INSERT INTO Immagini(Nome, Codice,FlagCopertina)
VALUES ('Pasta3.jpg',@SMARTBOXE8code,0);

INSERT INTO Composto (Quantità, CodSeriale, IdOrdine)
VALUES ('1',@SMARTBOXE1code,'01');
INSERT INTO Composto (Quantità, CodSeriale, IdOrdine)
VALUES ('2',@SMARTBOXE2code,'02');
INSERT INTO Composto (Quantità, CodSeriale, IdOrdine)
VALUES ('2',@SMARTBOXE3code,'03');
INSERT INTO Composto (Quantità, CodSeriale, IdOrdine)
VALUES ('1',@SMARTBOXE4code,'03');