package digital.erben.reactiveweb.ratings;

import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@Testcontainers
@AutoConfigurationPackage
@ContextConfiguration(classes = {MongoDBTestContainerConfig.class})
public class RatingRepositoryTest {

    Faker faker = new Faker();

    @Autowired
    ReactiveMongoTemplate mongoTemplate;

    @Autowired
    RatingRepository ratingRepository;

    @AfterEach
    void tearDown() {
        ratingRepository.deleteAll().block();
    }

    @Test
    void testSave() {
        // Prepare a test rating
        Rating testRating = new Rating(null, 0, "0", "0", false);

        // Save the test rating
        Mono<Rating> savedRating = ratingRepository.save(testRating);

        StepVerifier.create(savedRating)
            .expectNextMatches(rating -> rating.equals(testRating.withId(rating.id()))).verifyComplete();
    }

}
