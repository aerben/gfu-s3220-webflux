package digital.erben.reactiveweb.ratings;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RatingRepository {

  private final Map<Integer, Rating> ratings = new ConcurrentHashMap<>();
  private final AtomicInteger idGenerator = new AtomicInteger();

  public Mono<Rating> save(Rating rating) {
    int id = rating.id() == null ? idGenerator.incrementAndGet() : rating.id();
    ratings.put(id, rating);
    return Mono.just(ratings.get(id));
  }

  public Mono<Rating> findById(Integer id) {
    return Mono.justOrEmpty(ratings.get(id));
  }

  public Flux<Rating> findAll() {
    return Flux.fromIterable(ratings.values());
  }

  public Mono<Void> deleteById(Integer id) {
    ratings.remove(id);
    return Mono.empty();
  }
}
