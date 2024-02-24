package digital.erben.reactiveweb.cities;

import digital.erben.reactiveweb.cities.model.City;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;


@WebFluxTest(controllers = CitiesController.class)
public class CitiesControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CitiesRepository citiesDataset;

    @Test
    public void testGetAllCities() {
        City testCity = new City(1, "Q64", "city", "Berlin", "Berlin", "Germany", "DE", "Berlin", "BE", "Q64", 52.5200,
            13.4050, 3769495);
        Mockito.when(citiesDataset.loadCities()).thenReturn(Flux.just(testCity));

        webTestClient.get()
            .uri("/cities")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(City.class).returnResult();

    }

    @Test
    public void testGetAllCitiesGreaterThanPopulation() {
        City testCity = new City(1, "Q64", "city", "Berlin", "Berlin", "Germany", "DE", "Berlin", "BE", "Q64", 52.5200,
            13.4050, 3769495);
        City testCity2 = new City(1, "Q64", "city", "Berlin", "Berlin", "Germany", "DE", "Berlin", "BE", "Q64", 52.5200,
            13.4050, 3769495);
        City testCity3 = new City(1, "Q64", "city", "Berlin", "Berlin", "Germany", "DE", "Berlin", "BE", "Q64", 52.5200,
            13.4050, 13);

        Mockito.when(citiesDataset.loadCities()).thenReturn(Flux.just(testCity, testCity2, testCity3));

        webTestClient.get().uri("/cities?fromPopulation=1000")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(City.class)
            .hasSize(2)
            .contains(testCity);
    }

    @Test
    public void testGetCityById() {
        City testCity = new City(1, "Q64", "city", "Berlin", "Berlin", "Germany", "DE", "Berlin", "BE", "Q64", 52.5200,
            13.4050, 3769495);
        Mockito.when(citiesDataset.loadCities()).thenReturn(Flux.just(testCity));

        webTestClient.get()
            .uri("/cities/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(City.class)
            .isEqualTo(testCity)
            .returnResult();
    }

    @Test
    public void testGetCityById_shouldReturn404whenCityNotFound() {
        Mockito.when(citiesDataset.loadCities()).thenReturn(Flux.empty());
        webTestClient.get()
            .uri("/cities/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }
}
