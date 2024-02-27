package digital.erben.reactiveweb.ratings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class RatingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatingsApplication.class, args);
    }
}
