package digital.erben.webflux001.assignments;

import digital.erben.webflux001.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.function.Predicate;

import static org.mockito.Mockito.when;

public class CitiesServiceTest {

    private CitiesService service;
    private CitiesRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CitiesRepository.class);
        service = new CitiesService(repository);
    }

    private City createCity(int id, String city, int population) {
        return new City(
                id,
                "wikiDataId" + id,
                "type",
                city,
                city + "Name",
                "country",
                "countryCode",
                "region",
                "regionCode",
                "regionWdId",
                0.0, // latitude
                0.0, // longitude
                population
        );
    }

    @Test
    void getCitiesWithLimit() {
        var cities = List.of(
                createCity(1, "City1", 100000),
                createCity(2, "City2", 200000)
        );
        when(repository.loadCities()).thenReturn(Flux.fromIterable(cities));

        Flux<City> result = service.getCitiesWithLimit(1);

        StepVerifier.create(result)
                .expectNext(cities.get(0))
                .verifyComplete();
    }

    @Test
    void getCitiesWithPopulationGreaterThanInclusive() {
        var cities = List.of(
                createCity(1, "City1", 100000),
                createCity(2, "City2", 200000),
                createCity(3, "City3", 300000)
        );
        when(repository.loadCities()).thenReturn(Flux.fromIterable(cities));

        Flux<City> result = service.getCitiesWithPopulationGreaterThanInclusive(150000);

        StepVerifier.create(result)
                .expectNextMatches(city -> city.population() >= 150000)
                .expectNextMatches(city -> city.population() >= 150000)
                .verifyComplete();
    }

    @Test
    void getCitiesSortedByPopulationAscending() {
        var cities = List.of(
                createCity(3, "City3", 300000),
                createCity(1, "City1", 100000),
                createCity(2, "City2", 200000)
        );
        when(repository.loadCities()).thenReturn(Flux.fromIterable(cities));

        Flux<City> result = service.getCitiesSortedByPopulationAscending();

        StepVerifier.create(result)
                .expectNext(cities.get(1), cities.get(2), cities.get(0)) // Expected order after sorting
                .verifyComplete();
    }

    @Test
    void getFirstCityThatMatches() {
        var cities = List.of(
                createCity(1, "City1", 100000),
                createCity(2, "City2", 200000),
                createCity(3, "City3", 300000)
        );
        when(repository.loadCities()).thenReturn(Flux.fromIterable(cities));

        Predicate<City> predicate = city -> city.population() > 100000;
        var result = service.getFirstCityThatMatches(predicate);

        StepVerifier.create(result)
                .expectNext(cities.get(1)) // First city that matches the predicate
                .verifyComplete();
    }
}
