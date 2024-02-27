
### Schritt 1: Projekt erstellen

Bevor wir mit dem Coding beginnen, müssen wir das Projekt erstellen. Dies machen wir über [start.spring.io](https://start.spring.io/). Dieser Link generiert ein Projekt, welche die nötigen Abhängigkeiten bereits enthält.

### Schritt 2: `Book` Entity erstellen

Nachdem das Projekt eingerichtet ist, erstellen wir ein Paket namens `model` innerhalb des Hauptpakets `de.gfu.webflux` und fügen eine Klasse `Book` hinzu. Diese Klasse repräsentiert die Datenstruktur eines Buches mit einer ID, einem Titel und einem Autor.

```java
package de.gfu.webflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("books")
public class Book {
    
    @Id
    private Long id;
    private String title;
    private String author;

    // TODO: Konstruktoren, Getter und Setter
}
```
Füge außerdem in die `application.properties` folgende Zeile ein:
```
spring.jpa.hibernate.ddl-auto=update
```
Dadurch wird das Datenbankschema später automatisch angelegt.

### Schritt 3: `BookRepository` erstellen

Erstelle ein Interface `BookRepository` im Paket `repository`. Dieses Interface erbt von `ReactiveCrudRepository`, welches CRUD-Operationen für die `Book`-Entity ermöglicht. Spring Data ermöglicht es uns, diese Funktionalität ohne explizite Implementierung zu nutzen.

```java
package de.gfu.webflux.repository;

import de.gfu.webflux.model.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
}
```

### Schritt 4: `BookController` implementieren

Erstelle ein Paket `controller` und füge eine Klasse `BookController` hinzu. Dieser Controller stellt Endpunkte bereit, um Bücher zu erstellen, zu lesen, zu aktualisieren und zu löschen. Wir verwenden hierfür WebFlux, um asynchrone, nicht-blockierende Anfragen zu ermöglichen.

```java
package de.gfu.webflux.controller;

import de.gfu.webflux.model.Book;
import de.gfu.webflux.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository repository;

    @GetMapping
    public Flux<Book> getAllBooks() {
        return repository.findAll();
    }
    
    // TODO weitere Endpunkte
}
```

### Schritt 5: SpringBootTest schreiben

Schließlich schreiben wir einen Test für den `BookController`. Erstelle dazu eine Klasse `BookControllerTest` im Paket `controller` im Test-Verzeichnis. Nutze `@WebFluxTest` und `@Autowired` um den WebTestClient zu injizieren, mit dem Sie HTTP-Anfragen simulieren können.

```java
package de.gfu.webflux.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import de.gfu.webflux.repository.BookRepository;

@WebFluxTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository repository;

    @Test
    public void testGetAllBooks() {
        webTestClient.get().uri("/books")
                .exchange()
                .expectStatus().isOk();
    }

    // TODO: Weitere Tests für CRUD-Operationen
}
```

Wenn du das Repository nicht mocken willst, kannst du auch `@SpringBootTest` verwenden, wodurch eine richtige Umgebung
erzeugt wird, die auch eine Datenbankanbindung hat.
