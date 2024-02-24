package digital.erben.reactiveweb.ratings;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(controllers = RatingController.class)
class RatingControllerTest {
    private final Faker faker = new Faker();
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private RatingRepository ratingRepository;

    @MockBean
    private UserRetriever userRetriever;

    @MockBean
    private RestaurantRetriever restaurantRetriever;

    @Test
    void testCreateRating() {
        Rating rating = new Rating(1, 5, 0, 0, false);

        mockSetup(rating);

        this.webTestClient.post()
            .uri("/ratings")
            .body(Mono.just(rating), Rating.class)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Rating.class)
            .isEqualTo(rating);
    }


    @Test
    void testCreateRating_shouldFailOnInvalidRating() {
        Rating rating = new Rating(1, 6, 0, 0, false);

        mockSetup(rating);

        this.webTestClient.post()
            .uri("/ratings")
            .body(Mono.just(rating), Rating.class)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest();
    }



    @Test
    void testCreateRating_shouldFailOnInvalidUser() {
        Rating rating = new Rating(1, 5, 0, -1, false);

        mockSetup(rating);

        this.webTestClient.post()
            .uri("/ratings")
            .body(Mono.just(rating), Rating.class)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest();
    }


    private void mockSetup(Rating rating) {
        when(ratingRepository.save(any(Rating.class))).thenReturn(Mono.just(rating));

        when(userRetriever.findUser(anyInt())).then(
            call -> Mono.just(new User(call.getArgument(0),
                faker.name().firstName(),
                faker.name().lastName())
            ));

        when(userRetriever.findUser(Mockito.argThat(integer -> integer < 0))).thenReturn(Mono.empty());

        when(restaurantRetriever.findRestaurant(anyInt())).then(
            call -> Mono.just(new Restaurant(call.getArgument(0),
                faker.company().name())
            ));

        when(restaurantRetriever.findRestaurant(Mockito.argThat(integer -> integer < 0))).thenReturn(Mono.empty());

    }
}
