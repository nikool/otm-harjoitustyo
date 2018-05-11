# Käyttöohje

Lataa tiedosto BudgetApp_final.zip. Pakattu kansio sisältää valmiin config.properties-tiedoston ja ajettavan .jar-tiedoston.

## Konfigurointi

Ohjelma olettaa sen käynnistyshakemistosta löytyvän tekstitiedoston config.properties, 
joka määrittelee transaktioiden tallentamiseen käytetyn tiedoston. Tekstitiedoston muoto on:

`transactionFile=transactions.txt`

Tiedosto löytyy valmiiksi julkaisun pakatusta kansiosta, muuten se täytyy itse lisätä.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komentoriviltä komennolla

`java -jar BudgetApp-1.0-final.jar`

Tai tuplaklikkaamalla pakatusta kansiosta purettua BudgetApp-1.0-final.jar-tiedostoa.

## Ohjelman avaus

Ohjelma käynnistyy tervetuloikkunaan:

![welcomeScene](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/welcomeScene.PNG)

Painamalla `Press to enter` pääsee ohjelmaan sisään.

## Alkunäkymä

Ohjelma aukeaa näkymään jossa mitään tietoja ei vielä näytetä

![statScene](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/statScene.PNG)

Voit tässä kohtaa lisätä uuden transaktion ruudun alhaalla olevien ohjeiden mukaisesti

![addTransaction](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/addTransaction.PNG)

Transaktioita voi lisätä myös kaikissa muissa näkymissä.

## Tietonäkymä

Valitsemalla vasemmalla olevista painikkeista jonkun kuukauden tai koko vuoden, 
pääset tarkastelemaan valitun ajanjakson tilastoja ja tietoja.

### Koko vuosi

![wholeYear](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/wholeYear.PNG)

Koko vuoden tilassa voit poistaa kaikki tallennetut transaktiot painamalla `remove all` 
tai generoimaan ohjelman käyttöön randomisoidut tilastot painamalla `add randomized data`

Datan generoiminen helpottaa esim ohjelman testausta. 
Sitä painamalla sovellus luo ohjelman käyttöön joka kuukaudelle randomisoidut tiedot.

Koko vuoden tilassa sovellus näyttää kaikkien kuukausien loppusummat listana 
ja piirtää kuukausien menoista, tuloista ja päiväkeskiarvoista kuvaajan.

### Kuukausi

![oneMonth](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/oneMonth.PNG)

Kuukausinäkymässä sovellus listaa kuukauden tapahtumat. `remove all` napilla voi poistaa koko kuukauden transaktiot. 
`remove selected` napilla voi poistaa listasta hiirellä klikkaamalla valitun transaktion.

Sovellus piirtää tapahtumalistauksen oikella puolelle kuvan kuukauden transaktioista.
