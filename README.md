### BudgetApp 

## Dokumentaatio:

[vaatimusmäärittely.md](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmäärittely.md)

[työaikakirjanpito.md](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/työaikakirjanpito.md)

[arkkitehtuurikuvaus.md](https://github.com/nikool/otm-harjoitustyo/blob/master/dokumentointi/arkkitehtuurikuvaus.md)

## Komentorivitoiminnot

# Testaus

Testit suoritetaan komennolla

````mvn test````

Testikattavuusraportti luodaan komennolla

````mvn jacoco:report````

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

# Suoritettavan jarin generointi

Komento

````mvn package````

generoi hakemistoon target suoritettavan jar-tiedoston BudgetApp-1.0-SNAPSHOT.jar
