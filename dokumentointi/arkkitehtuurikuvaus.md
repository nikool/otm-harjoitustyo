# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudatta kolmikerrosarkkitehtuuria. Koodin pakkausrakenne on:

![pakkausrakenne](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/packageChart.PNG)

Pakkaus `ui` sisältää JavaFX:llä toteutetun graafisen käyttöliittymän, `domain` sovelluslogiikan ja `dao` tiedon tallennukseen liittyvän koodin.

## Käyttöliittymä

Käyytöliittymä sisältää kolme näkymää

* tervetulo
* pää
* tilasto

Tervetulo ja päänäkymät on toteutettu omina Scene-olioina. Tilastonäkymä on päänäkymän sisällä esitettävä GridPane-node. Näkymistä tervetulo tai pää on sijoitettuna sovelluksen stageen. Käyttöliittymä rakennetaan ohjelmallisesti luokassa ui.BudgetApp.

Käyttöliittymä kutsuu sovelluslogiikan oliota BudgetAppServiceä ja matemaattisiatoimintoja toteuttavaa oliota Statisticsiä.

## Sovelluslogiikka

Sovelluksen datamallin muodostaa luokka Transaction, joka kuvaa yksittäistä taloustapahtumaa. Transactiolla on tiedossaan summa, kuukausi ja id-numero.

Toiminnasta vastaa BudgetAppService joka käsittelee tietoa TransactionDao-luokan kautta. TransactionDao toteuttaa yleisen Dao-rajapinnan. TransactionDao toteutus injektoidaan sovelluslogiikalle.

Ohjelman pakkauskaavio:

![pakkauskaavio](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/umlChart.png)

## Tietojen tallennus

Pakkauksen `dao` luokka TransactionDao huolehtii käyttäjän transaktioiden tallentamisesta tiedostoon.

Luokka noudatta DAO-suunnittelumallia ja se on mahdollista korvata uudella toteutuksella, jos tallennustapaa halutaan vaihtaa. Testeissä käytetään keskusmuistiin tallentavaa toteutusta.

### Tiedosto

Sovellus tallentaa käyttäjän transaktiot tiedostoon, joka on määritelty config.properties -konfiguraatiotiedostossa.

Sovellus tallentaa transaktioita muodossa:

`1;1.0;12`

jossa tiedot on jaettu merkillä `;`. Ensimmäinen osa on transaktioin id-numero, toinen transaktion summa ja kolmas kuukausi.

### Päätoiminnallisuus

Seuraavaksi kuvataan sovelluksen toiminta kun käyttäjä lisää uuden transaktion tietylle kuukaudelle:

![sekvenssikaavio](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/sequenceChart.png)

## Ohjelmaan jääneet viat

### Käyttöliittymä

Graafinen käyttöliittymä olisi voitu toteuttaa siistimmin, esim radiopainikkeiden sijasta drop down -listalla, sekä lisäämällä tervetuloikkunaan jotakin järkevää toiminnallisuutta (esim. eri käyttäjien tukeminen). Käyttöliittymän koodi on myös turhan monimutkaista ja syytä jakaa useampiin metodeihin.

### Sovelluslogiikka

Sovelluksen olisi ollut hyvä tukea transaktioiden muokkaamista jälkikäteen, useita käyttäjiä ja transaktioiden kommentointia.

### DAO

Sovellus olisi voinut käyttää tietokantaa tiedston sijasta tietojen tallentamiseen.
