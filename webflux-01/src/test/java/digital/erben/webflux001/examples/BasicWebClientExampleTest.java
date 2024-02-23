package digital.erben.webflux001.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import digital.erben.webflux001.model.CitiesResponse;
import digital.erben.webflux001.model.City;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.List;

public class BasicWebClientExampleTest {
    public static final String URL = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities?countryIds=DE&minPopulation=200000&limit=100";
    private final ObjectMapper objectMapper;

    public BasicWebClientExampleTest() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    public void callApiViaWebClientToMono() {
        Mono<List<City>> cities = WebClient.create().get()
            .uri(URI.create(URL))
            .header("X-RapidAPI-Key", "16e1546221msh6270284dc1921b8p16483fjsnf33be4140b48")
            .header("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
            .retrieve()
            .bodyToMono(CitiesResponse.class)
            .mapNotNull(CitiesResponse::data);

        StepVerifier
            .create(cities)
            .expectNextMatches(list -> list.size() == 100)
            .expectComplete()
            .verify();
    }

    @Test
    public void callApiViaWebClientToFlux() {
        Flux<City> cities = WebClient.create().get()
            .uri(URI.create(URL))
            .header("X-RapidAPI-Key", "16e1546221msh6270284dc1921b8p16483fjsnf33be4140b48")
            .header("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
            .retrieve()
            .bodyToMono(CitiesResponse.class)
            .flatMapIterable(CitiesResponse::data);

        StepVerifier
            .create(cities)
            .expectNextCount(100)
            .expectComplete()
            .verify();
    }

}
