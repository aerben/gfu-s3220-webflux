package digital.erben.reactiveweb.ratings;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

    @Query("SELECT * FROM customer WHERE last_name = :lastname")
    Flux<Customer> findByLastName(String lastName);

    @Query("SELECT * FROM customer ORDER BY birthday DESC")
    Flux<Customer> findAllCustomersOrderByBirthdayDesc();

}
