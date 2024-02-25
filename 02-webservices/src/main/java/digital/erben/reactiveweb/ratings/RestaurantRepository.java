package digital.erben.reactiveweb.ratings;

import java.util.List;
import java.util.stream.IntStream;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RestaurantRepository {

	private final List<Restaurant> restaurants = IntStream.range(0, 10)
			.mapToObj(i -> new Restaurant(i, new Faker().company().name())).toList();

	public Mono<Restaurant> findById(Integer id) {
		return Mono.justOrEmpty(restaurants.stream().filter(r -> r.id() == id).findAny());
	}
}
