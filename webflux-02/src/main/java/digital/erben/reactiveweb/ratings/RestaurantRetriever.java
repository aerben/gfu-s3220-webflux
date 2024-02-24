package digital.erben.reactiveweb.ratings;

import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class RestaurantRetriever {

    private final List<Restaurant> restaurants = IntStream.range(0, 10).mapToObj(i -> new Restaurant(
        i, new Faker().company().name()
    )).toList();

    public Mono<Restaurant> findRestaurant(Integer id) {
        return Mono.justOrEmpty(restaurants.stream().filter(r -> r.id() == id).findAny());
    }
}
