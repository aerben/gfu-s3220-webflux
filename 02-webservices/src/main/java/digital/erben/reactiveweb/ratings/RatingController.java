package digital.erben.reactiveweb.ratings;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	private final RatingRepository repository;
	private final UserRepository userRepository;
	private final RestaurantRepository restaurantRepository;
	private final MeterRegistry registry;

	public RatingController(RatingRepository repository, UserRepository userRepository,
                            RestaurantRepository restaurantRepository, MeterRegistry registry) {
		this.repository = repository;
		this.userRepository = userRepository;
		this.restaurantRepository = restaurantRepository;
        this.registry = registry;
	}

	@PostConstruct
	public void setupRatings() {
		for (int i = 0; i < 10; i++) {
			this.repository.save(new Rating(i, 5, i, i, false)).block();
		}
	}

	@PostMapping
	public Mono<ResponseEntity<Rating>> createRating(@RequestBody Mono<Rating> rating) {
		return rating.doOnSuccess(this::checkIfRatingInBounds) //
			.doOnSuccess(r -> {
				if (r.id() != null) {
					throw new IllegalArgumentException("id already set");
				}
			})
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
	public Mono<ResponseEntity<Rating>> getRating(@PathVariable("id") Integer id) {
		return repository.findById(id) //
			.map(ResponseEntity::ok) //
			.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}

	@GetMapping
	public Flux<Rating> getAllRatings() {
		return repository.findAll()
			.name("get-all-ratings")
			.tag("k", "v")
			.tap(Micrometer.metrics(registry));
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteRating(@PathVariable("id") Integer id) {
        return repository
			.findById(id)
			.flatMap(this::checkIfLocked)
            .flatMap(i -> repository.deleteById(id))
            .<ResponseEntity<Void>>thenReturn(ResponseEntity.ok().build())
            .onErrorReturn(ResponseEntity.badRequest().build());
	}

	private Mono<Rating> checkIfLocked(Rating rating) {
		if (rating.locked()) {
			return Mono.error(new IllegalArgumentException(""));
		} else {
			return Mono.just(rating);
		}
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
