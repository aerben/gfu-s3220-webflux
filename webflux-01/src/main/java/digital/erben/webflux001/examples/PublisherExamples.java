package digital.erben.webflux001.examples;

import net.datafaker.Faker;
import net.datafaker.providers.entertainment.StarWars;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SuppressWarnings("ALL")
//@Component
public class PublisherExamples implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        var faker = new Faker();
        StarWars starWars = faker.starWars();

        // `just` legt einen Publisher mit einem definierten Wert an
        Mono.just(starWars.callSign())
            .subscribe(System.out::println);

        Flux.just(starWars.callSign(), starWars.callSign())
            .subscribe(System.out::println);

        // Sowohl Mono als auch Flux implementieren das Publisher-Interface
        Publisher<String> mono = Mono.just("Data");
        Publisher<String> flux = Flux.just("Data");

        // `fromCallable` legt ein Mono vom Ergebnis eines Callables an.
        // Im Gegensatz zu `just` ist diese Berechnung bereits asynchron.
        // Ein Flux wird so nicht angelegt.
        Mono.fromCallable(starWars::quotes)
            .subscribe(System.out::println);

        // `error` legt einen Publisher an, der direkt in einem Fehlerzustand ist
        Mono.error(new RuntimeException("Failure"))
            .onErrorResume(e -> Mono.just(e.getMessage()))
            .subscribe(System.out::println);

        Flux.error(new RuntimeException("Failure"))
            .onErrorResume(e -> Mono.just(e.getMessage()))
            .subscribe(System.out::println);

        // Publisher lassen sich mit `create` anlegen. Der übergebene Consumer enthält
        // eine Sink, auf der man mit `success` bzw. `next` neue Werte anlegen kann.
        Mono.create(monoSink -> monoSink.success(starWars.planets()))
            .subscribe(System.out::println);

        Flux.create(fluxSink -> fluxSink.next(starWars.callSign()))
            .subscribe(System.out::println);

        Mono.create(monoSink -> monoSink.error(new RuntimeException("Failure")))
            .onErrorResume(e -> Mono.just(e.getMessage()))
            .subscribe(System.out::println);

        // `delay` legt ein Mono an, das erst nach einer fixen Duration aufgelöst wird.
        Mono.delay(Duration.ofSeconds(1))
            .subscribe(System.out::println);

        // `generate` für Flux funktioniert wie bei Streams.
        // Die übergebene Sink wird aufgerufen, um neue Werte zu erzeugen, solange
        // Subscriber sie anfragen.
        Flux.generate(sink -> sink.next(starWars.quotes()))
            .delayElements(Duration.ofSeconds(1))
            .subscribe(System.out::println);

        Mono.just(starWars.wookieWords())
            .map(String::toLowerCase)
            .filter(s -> s.length() % 2 == 0)
            .subscribe(System.out::println);

    }
}
