package digital.erben.reactiveweb.ratings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Arrays;

@SpringBootApplication
public class R2dbcApplication {
    private static final Logger log = LoggerFactory.getLogger(R2dbcApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(R2dbcApplication.class, args);
    }

}
