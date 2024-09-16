package digital.erben.webflux001;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;


@SuppressWarnings("UnnecessaryLocalVariable")
public class RateLimitingAndRetryTest {
    public static final String URL = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities?countryIds=DE&minPopulation=200000&limit=100";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RateLimitingAndRetryTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    public void callApiViaWebClientWithFullErrorHandling() throws JsonProcessingException, InterruptedException {
        WebClient.create().get()
            .uri(URI.create(URL))
            .header("X-RapidAPI-Key", "16e1546221msh6270284dc1921b8p16483fjsnf33be4140b48s")
            .header("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
            .retrieve()
            .onStatus(code(404), response -> Mono.just(new NotFound(response)))
            .onStatus(code(403), response -> Mono.just(new Forbidden(response)))
            .onStatus(code(400), response -> Mono.just(new BadRequest(response)))
            .toEntity(CitiesResponse.class)
            .mapNotNull(HttpEntity::getBody)
            .map(CitiesResponse::data)
            .subscribe(
                System.out::println,
                this::handleException
            );

        Thread.sleep(10000L);
    }

    private void handleException(Throwable throwable) {
        logger.error("Failed request", throwable);
    }

    static class GenericException extends RuntimeException {
        private final ClientResponse clientResponse;

        GenericException(ClientResponse clientResponse) {
            this.clientResponse = clientResponse;
        }

        public ClientResponse getClientResponse() {
            return clientResponse;
        }
    }

    static final class NotFound extends GenericException {
        NotFound(ClientResponse clientResponse) {
            super(clientResponse);
        }
    }

    static final class Forbidden extends GenericException {
        Forbidden(ClientResponse clientResponse) {
            super(clientResponse);
        }
    }

    static final class BadRequest extends GenericException {
        BadRequest(ClientResponse clientResponse) {
            super(clientResponse);
        }
    }

    static Predicate<HttpStatusCode> code(int code) {
        return (httpStatusCode -> httpStatusCode.value() == code);
    }

    @Test
    public void callApiViaWebClientFromAFluxWithRetry() throws JsonProcessingException, InterruptedException {
        var webClient = WebClient.create();

        Flux<String> countries = Flux.just("DE", "FR", "IT", "DE", "DE", "DE", "DE", "DE", "DE", "DE", "DE", "DE", "DE", "DE", "DE", "DE", "DE", "DE", "DE", "DE", "DE");

        Flux<List<City>> cityFlux = countries.flatMap(country -> webClient.get()
            .uri(URI.create(URL))
            .header("X-RapidAPI-Key", "16e1546221msh6270284dc1921b8p16483fjsnf33be4140b48")
            .header("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
            .retrieve()
            .bodyToMono(CitiesResponse.class) // kürzer als toEntity wenn man den Response Code nicht braucht
            .mapNotNull(CitiesResponse::data)
        ).retryWhen(Retry.backoff(3, Duration.ofSeconds(5))); // on failure, retries automatically

        cityFlux.subscribe(l -> System.out.println(l.size()));

        Thread.sleep(100000L);
    }

}
