package digital.erben.webflux001;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class MulticastExampleTest {

    /**
     * This example shows that multiple subscriptions will cause the whole Flux to be re-executed
     */
    @Test
    public void uncachedExample() throws InterruptedException {
        Flux<Integer> source = Flux.range(1, 5)
            .delayElements(Duration.ofMillis(500))
            .doOnNext(e -> System.out.println("Emitting " + e));

        source.subscribe(e -> System.out.println("[Subscriber 1] Received: " + e));
        stop();
        source.subscribe(e -> System.out.println("[Subscriber 2] Received: " + e));
        stop();
    }

    /**
     * Caching will cause the values to be stored in a way that they can instantly be replayed
     */
    @Test
    public void cacheExample() throws InterruptedException {
        Flux<Integer> source = Flux.range(1, 5)
            .delayElements(Duration.ofMillis(500))
            .doOnNext(e -> System.out.println("Emitting " + e))
            .cache();

        source.subscribe(e -> System.out.println("[Subscriber 1] Received: " + e));
        stop();
        source.subscribe(e -> System.out.println("[Subscriber 2] Received: " + e));
        stop();
    }

    /**
     * Multicasting with publish() has a comparable result.
     * The difference is that it does not cache the result but coordinates
     * the consumers to directly receive all results from one Flux.
     */
    @Test
    public void multicastExample() throws InterruptedException {
        Flux<Integer> source = Flux.range(1, 5)
            .delayElements(Duration.ofMillis(250))
            .doOnNext(e -> System.out.println("Emitting " + e))
            .publish().autoConnect(2);

        source.subscribe(e -> System.out.println("[Subscriber 1] Received: " + e));
        source.subscribe(e -> System.out.println("[Subscriber 2] Received: " + e));
        stop();
    }

    private static void stop() throws InterruptedException {
        try {
            Thread.sleep(2500); // Warten, damit beide Abonnements nicht überlappen
        } catch (InterruptedException e) {
            throw e;
        }
    }
}
