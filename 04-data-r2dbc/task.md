# Aufgabe: Queries erstellen mit Spring Data Reactive

Lege zwei neue Methoden an in `CustomerRepository` mit folgender Signatur:
```java
Flux<Customer> findCustomersBornBefore(LocalDate cutoffDate);

Flux<Customer> findByLastNameNotLike(String pattern);
```
Die erste Methode soll alle Kunden zurückgeben, deren Geburtstag vor dem übergebenen Geburtstag liegt.
Die zweite Methode soll alle Kunden zurückgeben, deren Namen nicht dem Pattern entspricht, was übergeben wurde.
Schreibe jeweils die dafür nötigen Queries und verwende die `@Query`-Annotation, um sie den Methoden hinzuzufügen.
Schreibe für beide Methoden einen Test in `CustomerRepositoryTest`.

## Hinweis
- Auch für Datumstypen funktioniert der Vergleich mit ">" oder "<"
- Wenn du in einer @Query einen Methodenparameter verwenden willst, dann halte dich an das erste Beispiel im Code:
```java
@Query("SELECT * FROM customer WHERE last_name = :lastname")
Flux<Customer> findByLastName(String lastName);
```
- Der SQL-Operator für eine Pattern-Suche ist `LIKE` bzw. negiert `NOT LIKE`
