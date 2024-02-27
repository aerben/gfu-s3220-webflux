### Aufgabe 1: Grundlegendes Multicasting

#### Ziel
Wir veranschaulichen, wie Multicasting mit `publish()` und `refCount()` funktionieren.

#### Aufgabenstellung
1. **Erstelle ein `Flux`-Objekt**, das Zahlen von 1 bis 5 generiert, wobei zwischen jeder Zahl eine Verzögerung von 1 Sekunde liegt.

```java
Flux<Long> flux = Flux.interval(Duration.ofSeconds(1)).take(5);
```

2. **Verwende `publish()` und `refCount()`**, um ein Multicasting-Setup zu erstellen.

```java
Flux<Long> publishedFlux = flux.publish().refCount();
```

3. **Fügen Sie zwei Abonnenten hinzu**: der erste abonniert sofort, der zweite mit einer Verzögerung von 2 Sekunden.

```java
// Erster Abonnent
publishedFlux.subscribe(e -> System.out.println("Abonnent 1: " + e));

// Zweiter Abonnent mit Verzögerung
Thread.sleep(2000);
publishedFlux.subscribe(e -> System.out.println("Abonnent 2: " + e));
```

4. **Beobachten und notieren Sie, welche Daten jeder Abonnent erhält.**

#### Erwartetes Ergebnis
Der erste Abonnent erhält alle Zahlen von 1 bis 5, während der zweite nur Zahlen ab dem Zeitpunkt seines Abonnements erhält, da `refCount()` das Multicasting startet, sobald der erste Abonnent hinzukommt.

### Aufgabe 2: Caching von Daten

#### Ziel
Demonstrieren Sie die Verwendung von `cache()` in einem WebFlux-Stream.

#### Aufgabenstellung
1. **Erstellen Sie ein `Flux`-Objekt**, das fünf zufällige Zahlen generiert.

```java
Flux<Integer> randomNumbersFlux = Flux.range(1, 5).map(i -> new Random().nextInt(100));
```

2. **Wenden Sie die `cache()`-Methode an**, um die emittierten Daten zu speichern.

```java
Flux<Integer> cachedFlux = randomNumbersFlux.cache();
```

3. **Fügen Sie zwei Abonnenten hinzu**, wobei der zweite Abonnent mit einer Verzögerung hinzugefügt wird, nachdem alle Daten emittiert wurden.

```java
// Erster Abonnent
cachedFlux.subscribe(e -> System.out.println("Abonnent 1: " + e));

// Einfügen einer künstlichen Verzögerung
Thread.sleep(1000); // Stellen Sie sicher, dass dies in einer Umgebung geschieht, die `Thread.sleep()` unterstützt.

// Zweiter Abonnent
cachedFlux.subscribe(e -> System.out.println("Abonnent 2: " + e));
```

4. **Überprüfen Sie, ob beide Abonnenten dieselben Daten erhalten.**

#### Erwartetes Ergebnis
Beide Abonnenten erhalten dieselben fünf zufälligen Zahlen, da `cache()` die emittierten Daten für zukünftige Abonnenten speichert.

### Hinweis für die Implementierung
- Stellen Sie sicher, dass Sie diese Beispiele in einer geeigneten Umgebung ausführen, die Project Reactor unterstützt, wie z.B. eine Spring-WebFlux-Anwendung.
- Beachten Sie, dass `Thread.sleep()` in einem reaktiven Kontext normalerweise vermieden wird. In diesen Übungen dient es nur zur Illustration und sollte in realen Anwendungen durch reaktive Mechanismen ersetzt werden, z.B. durch Verwendung von `delayElements()` in einem `Flux`.
