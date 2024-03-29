package digital.erben.reactiveweb.ratings;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingRepository repository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public RatingController(RatingRepository repository, UserRepository userRepository,
                            RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    public Mono<ResponseEntity<Rating>> createRating(@RequestBody Mono<Rating> rating) {
        return rating.doOnSuccess(this::checkIfRatingInBounds) //
            .checkpoint("validate") //
            .flatMap(this::checkIfUserExists) //
            .checkpoint("check-user") //
            .flatMap(this::checkIfRestaurantExists) //
            .checkpoint("check-restaurant")//
            .flatMap(this.repository::save) //
            .checkpoint("save") //
            .map(ResponseEntity::ok) //
            .log("handle-error") //
            .onErrorResume(IllegalArgumentException.class,
                (error) -> Mono.just(ResponseEntity.badRequest().build()))
            .checkpoint("handle-error"); //
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Rating>> getRating(@PathVariable("id") String id) {
        return repository.findById(id) //
            .map(ResponseEntity::ok) //
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping
    public Flux<Rating> getAllRatings() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteRating(@PathVariable("id") String id) {
        return repository.deleteById(id);
    }

    private Mono<Rating> checkIfRestaurantExists(Rating in) {
        return this.restaurantRepository //
            .findById(in.restaurantId()) //
            .switchIfEmpty(Mono.error(new IllegalArgumentException("Restaurant not found"))) //
            .thenReturn(in);
    }

    private Mono<Rating> checkIfUserExists(Rating in) {
        return this.userRepository //
            .findById(in.userId()) //
            .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found"))) //
            .thenReturn(in);
    }

    private void checkIfRatingInBounds(Rating in) {
        if (in.rating() < 0 || in.rating() > 5) {
            throw new IllegalArgumentException("Rating out of bounds");
        }
    }
}
