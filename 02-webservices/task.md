# Aufgabe 1: Controller mit Spring Webflux (GET)

Implementiere den Controller `CitiesController` und lasse danach die Tests in `CitiesControllerTest` laufen.

## Hinweis
- Diese Aufgabe ist sehr ähnlich wie die letzten, die du gemacht hast. Gib einfach das `Flux`/`Mono` zurück, was dir das Repository gibt.

# Aufgabe 2: Controller mit Spring WebFlux II (POST, UPDATE, DELETE)

Implementiere den Controller `BookmarksController` und lasse danach die Tests laufen.

## Hinweis
- Auch hier reicht es aus, die Methoden im Repository aufzurufen.
- Aufgabe 1 und 2 zeigen euch, dass Spring Webflux einfach zu verwenden ist, wenn man `Flux` und `Mono` verstanden hat.
- Solltest du dich mit den beiden Datentypen noch schwertun, melde dich gerne!

# Aufgabe 3: Basic Error Handling in Spring WebFlux (I)

In `RatingController#deleteRating`, verweigere die Löschung, wenn das Rating das Feld `locked` auf `true` gesetzt hat.
Gebe in diesem Fall Status-Code 400 zurück. Verwendet Checkpoints und ein `log` an der Stelle, wo der Fehler fliegt.


# Aufgabe 4: Basic Error Handling in Spring WebFlux (II)

Erweitere die Methode `RatingController#createRating` um eine Prüfung, die Requests ablehnt, bei denen die
`id` bereits gesetzt ist. Antworte in diesem Fall mit Status Code 409 (Conflict).

# Aufgabe 5: Tests mit Spring WebFlux

Scheibe Tests für die Änderungen, die du in Aufgaben 3 und 4 durchgeführt hast.
