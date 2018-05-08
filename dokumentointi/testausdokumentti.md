# Testaus

Ohjelmaa on testattu sekä automatisoiduilla JUnit-testeillä, sekä manuaalisesti järjestelmätasolla.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Automatisoidut testit keskittyvät sovelluslogiikan luokkia testaaviin BudgetAppServiceTest ja StatisticsTest-luokkiin 
ja tietojen tallennuksen luokkaa testaavaan TransactionDaoTest-luokkaan.

Testit käyttävät tietojen tallenukseen dao-rajapinnan toteuttavaa luokkaa TestTransactionDao.

### DAO

Dao-luokan toimivuus on testattu JUnitin TemporaryFolderia hyödyntämällä.

### Testauskattavuus

Ui-pakettia lukuunottamatta sovelluksen testuaksen rivikattavuus on 96 % ja haarautumakattavuus 97 % 

![jacoco](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/jacoco.PNG)

## Järjestelmätestaus

Suoritettu manuaalisesti.

### Asennus ja konfigurointi

Sovellus on haettu ja sitä on testattu käyttöohjeen kuvaamalla tavalla Windows-ympäristössä, niin että käynnistyshakemuksessa on on ollut
käyttöohjeen mukainen konfiguraatiotiedosto.

Sovellusta on testattu sekä tilanteissa, joissa transaktioit tallettava tiedosto on ollut olemassa 
ja joissa sitä ei ole ollut jolloin ohjelma on luonut sen itse.

### Toiminnallisuudet

Kaikki määrittelydokumentin ja käyttöohjeen toiminnallisuudet on käyty läpi ja kaikissa kohdissa syötekenttiin 
on yritetty syöttää virheellisiä arvoja.
