# Builder Pattern Assignment Four
&copy; Hogeschool Novi. Geschreven door Nick Stuivenberg

## Installatiehandleiding
De volgende punten moet je zelf aanpassen en nalopen:
 * Maak een database aan met de volgende naam: `spring-boot-builder-example` of pas de naam aan in `src\main\resources\application.properties`
 * Pas gebruikersnaam en/of wachtwoord aan in `src\main\resources\application.properties`.
 
## Gebruikshandleidinggit remote add origin https://github.com/hogeschoolnovi/SD-BE-DP-BuilderPattern_AssFour.git
Je kunt alle gebruikers in de database opvragen door de volgende rest-endpoints aan te roepen:
`http://localhost:8080/api/user/all`

Je kunt gebruikers toevoegen via het volgende rest-endpoint `http://localhost:8080/api/user/signup`.
Dit is een __post__-request en in de body wordt de volgende JSON verwacht:
```json
{
  "username" : "nick",
  "password" : "qwerty",
  "passwordRepeated" : "qwerty",
  "email" : "nick@nick.nl",
  "firstName" : "Nick",
  "lastName" : "Stuivenberg"
}
```

In `UserRegistrationRequest.java` kun je nagaan aan welke eisen de JSON moet voldoen. De
`spring-boot-starter-validation`-library helpt hierbij.

```xml
<!-- Deze library gebruiken we om met Annotaties onze payloads te controleren -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
```
BUG: Op dit moment worden er __altijd__ lege user-objecten aan de database toegevoegd. Jij moet nog de Builder maken om
van een `UserRegistrationRequest`-object een `User` object te maken, voordat je deze aan de repository-laag kunt geven.

## De verschillende packages.
Deze applicatie is onderverdeeld in verschillende packages.

### controller
De controller-laag is de ingang van je backend applicatie. We hebben een controller-klasse met twee rest-endpoints.
Eentje om een gebruiker toe te voegen en eentje om alle gebruikers op te halen.

### service
De controller-laag communiceert met de servicelaag. De controller-laag doet eigenlijk niets anders dan het bericht
doorgeven aan de service laag. De service laag zijn de hersenen van je applicatie. Hier gebeurt alle _business-logica_.

In `UserService.java` staan op dit moment twee methodes. `findAllUsers()` en  `registerUser()`. findAllUsers() vraagt
aan de repository laag om een lijst van alle Users terug te geven. Deze worden vervolgens gereturnd.

De `registerUser`-methode is de plek waar jij moet gaan programmeren. Hier komt de `UserRegistrationRequest`-klasse
binnen. Er vinden enkele checks plaats en dan moet het object omgezet gaan worden.

__Hier__ mag jij iets met een Builder-klasse gaan verzinnen om het ene object naar het andere object te vertalen.
Wanneer je dat allemaal goed doet, wordt het User-object in de database opgeslagen.

### repository
De repository-laag is verantwoordelijk voor de communicatie met de database. Deze klasse zoekt dingen op en geeft het
resultaat terug, schrijft dingen weg naar de database, verwijdert dingen uit de database en update records in de
database. Ook kun je via deze klasse checken of bepaalde enties al voorkomen in de database.

Dat wordt gedaan met de `existsByUsername` en `existsByEmail` methode. We schrijven hier alleen de interface methode 
voor. Spring-boot vertaalt dat naar implementatie code voor ons. Spring-boot kan dat door middel van keywords. In dit
geval `existsBy`. Wat daarna komt __moet__ overeenkomen met een attribuut uit de klasse. In dit geval username en email
uit de User-klasse.

In principe communiceert alleen de service laag met de repository laag.

### domain
(Soms ook model genoemd)
De domain laag bevat jouw domein-klassen die middels Spring-data worden vertaald naar tabellen in de databse. In dit
geval gaat het om een klasse, dus wordt er maar 1 tabel gemaakt. Deze klasse hebben in tegenstelling tot jouw eerder
gemaakte domeinmodellen wel attributen zoals `id`, omdat je deze in de database nodig hebt.

Als je heel netjes wilt programmeren mogen alleen de repository- en de service-laag in aanraking komen met de
domain-laag. De repository ontvangt ze vanuit de service-laag of de database. De service laag vertaalt ze van
`payload`-objecten naar domain-objecten.

### payload
In de payload package staan twee subpackages. `request` en `response`.
In de `request`-package staan alle objecten die binnen kunnen komen in de controller laag. Deze worden doorgegeven aan
de servicelaag. In de servicelaag wordt de inhoud gecheckt en vervolgens worden de objecten vertaald naar
domain-objecten. Daarna worden ze via de repository laag in de database opgeslagen.

In de `request` package staan alle antwoorden die jouw applicatie kan geven. In dit geval is dat alleen een
`MessageReponse` klasse die een String teruggeeft.

Werken met payload-klassen zorgt voor veel extra code. Kies je klasse uit waar je payloads van wilt maken.
Wanneer iemand een lijst met gebruikers ophaalt, wil je bijvoorbeeld bepaalde gegevens juist niet delen met de
eindgebruiker.

## Opdracht
In `UserService.registerUser()` komt een `UserRegistrationRequest` object binnen. Deze moet gevalideerd en omgezet
worden, voordat deze opgeslagen kan worden als User-object. Probeer dit op verschillende manieren op te lossen. Met en
zonder builder. In een aparte klasse, of een methode in de service-klasse.

__Tip:__ Maak een branch voor elke oplossing.

Je kunt met `ctrl` + `shift` + `f` zoeken op `TODO Student`. Dan weet je gelijk waar je moet zijn.