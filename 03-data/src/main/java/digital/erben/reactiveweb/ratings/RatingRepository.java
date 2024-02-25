package digital.erben.reactiveweb.ratings;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends ReactiveMongoRepository<Rating, String> {
}
