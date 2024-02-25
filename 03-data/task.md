# Aufgabe: Arbeiten mit CrudRepository
Nutze die Interfaces `RatingRepository`, `UserRepository` und `RestaurantRepository` um dir einen Testdatenbestand aufzubauen. Schreibe dazu einen Test in `RatingRepositoryTest`, welcher folgende Aufgaben ausführt:
- Erstelle 10 Benutzer mit zufälligen Namen (Nutze dazu den `Faker`)
- Erstelle 10 Restaurants mit zufälligen Namen
- Erstelle pro Benutzer 10 Ratings, eines pro Restaurant
- Nutze den StepVerifier um zu prüfen, dass 100 Ratings im Repository sind

# Bonusaufgaben
- Erweitere den letzten Check der letzten Aufgabe. Prüfe, ob jedes Restaurant 10 Ratings hat.
- Ermittle den Durchschnitt aller Ratings, die im Test erzeugt wurden, und drucke ihn am Ende auf System.out
- Ermittle die Durchschnitte nach Restaurant und drucke sie als `Map<Restaurant, Double>` auf die Standardausgabe
