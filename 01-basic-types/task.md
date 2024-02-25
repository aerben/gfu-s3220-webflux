# Aufgabe 1: Mono und Flux
Implementiere die Methoden in `CitiesService` anhand der Dokumentation die vorliegt.
Tests findest du in `CitiesServiceTest`.

## Hinweise
- Anders als in Java-Streams heißt die Methode zum Begrenzen der Elementanzahl nicht `limit`, sondern `take`.
- Allerdings heißt es sowohl in Streams als auch in Flux `filter`, wenn es darum geht, Elemente nach Eigenschaften zu selektieren
- Fluxes unterstützen wie auch Streams `sort`

# Aufgabe 2: WebClient - GET
Implementiere die beiden Methoden im Service `RapidAPICitiesService` mit Spring RestClient und Spring WebClient.

Du kannst die Tests in `RapidAPICitiesServiceTest` nutzen um zu prüfen, ob deine Implementierung funktioniert. 
Die Tests kannst du gerne erweitern.

## Hinweise
- Um einen HTTP-Call mit den notwendigen Headern zu machen, kannst du dich an dieses Beispiel halten:
```java
public void performGet() {
    WebClient.create().get()
        .uri(URI.create(BASE_URL + "&countryIds=" + countryCode))
        .header(RAPID_URL_API_KEY_HEADER, RAPID_URL_API_KEY_HEADER_VALUE)
        .header(RAPID_URL_API_HOST_HEADER, RAPID_URL_API_HOST_HEADER_VALUE);    
}
```
- Du wirst einige Request-Parameter der API brauchen, um zu filtern. Du findest sie hier: [RapidAPI Docs](https://rapidapi.com/wirefreethought/api/geodb-cities/)
