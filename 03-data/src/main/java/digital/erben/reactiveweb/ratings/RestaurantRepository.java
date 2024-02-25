package digital.erben.reactiveweb.ratings;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends ReactiveMongoRepository<Restaurant, String> {
}
