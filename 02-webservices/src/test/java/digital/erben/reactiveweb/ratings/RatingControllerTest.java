package digital.erben.reactiveweb.ratings;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = RatingController.class)
class RatingControllerTest {
    private final Faker faker = new Faker();
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private RatingRepository ratingRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RestaurantRepository restaurantRetriever;

    @Test
    void testCreateRating() {
        Rating rating = new Rating(1, 5, 0, 0, false);

        mockSetup(rating);

        this.webTestClient.post()
            .uri("/ratings")
            .body(Mono.just(rating), Rating.class)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Rating.class)
            .isEqualTo(rating);
    }

    @Test
    void testCreateRating_shouldFailOnInvalidRating() {
        Rating rating = new Rating(1, 6, 0, 0, false);

        mockSetup(rating);

        this.webTestClient
            .post()
            .uri("/ratings")
            .body(Mono.just(rating), Rating.class)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    void testCreateRating_shouldFailOnInvalidUser() {
        Rating rating = new Rating(1, 5, 0, -1, false);

        mockSetup(rating);

        this.webTestClient
            .post()
            .uri("/ratings")
            .body(Mono.just(rating), Rating.class)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    private void mockSetup(Rating rating) {
        when(ratingRepository.save(any(Rating.class))).thenReturn(Mono.just(rating));

        when(userRepository.findById(anyInt()))
            .then(
                call ->
                    Mono.just(
                        new User(
                            call.getArgument(0), faker.name().firstName(), faker.name().lastName())));

        when(userRepository.findById(Mockito.argThat(integer -> integer < 0)))
            .thenReturn(Mono.empty());

        when(restaurantRetriever.findById(anyInt()))
            .then(call -> Mono.just(new Restaurant(call.getArgument(0), faker.company().name())));

        when(restaurantRetriever.findById(Mockito.argThat(integer -> integer < 0)))
            .thenReturn(Mono.empty());
    }
}
