# BudgetApp 

BudgetApp on sovellus, jolla käyttäjä voi seurata omia kuukausittaisia tuloja ja menojaan. Sovellus tarjoaa käyttäjälle hyödyllistä tietoa kuukausibalansseistaan.

## Dokumentaatio:

[Käyttöohje](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/k%C3%A4ytt%C3%B6ohje.md)

[Vaatimusmäärittely](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmäärittely.md)

[Arkkitehtuurikuvaus](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/arkkitehtuurikuvaus.md)

[Testausdokumentti](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/testausdokumentti.md)

[Työaikakirjanpito](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/työaikakirjanpito.md)

## Releaset

[Viikko 5](https://github.com/nikool/otm-harjoitustyo/releases/tag/week5)

# Komentorivitoiminnot

## Testaus

Testit suoritetaan komennolla

````mvn test````

Testikattavuusraportti luodaan komennolla

````mvn jacoco:report````

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

## Suoritettavan jarin generointi

Komento

````mvn package````

generoi hakemistoon target suoritettavan jar-tiedoston BudgetApp-final.jar

## Checkstyle

Tiedostoon checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla

````mvn jxr:jxr checkstyle:checkstyle````
