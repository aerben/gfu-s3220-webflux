package digital.erben.webflux001.assignments;

import com.fasterxml.jackson.databind.ObjectMapper;
import digital.erben.webflux001.model.CitiesResponse;
import digital.erben.webflux001.model.City;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class CitiesRepository {

    public Flux<City> loadCities() {
        try {
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("cities.json");
            Objects.requireNonNull(resourceAsStream);
            CitiesResponse geoData = new ObjectMapper().readValue(resourceAsStream, CitiesResponse.class);
            return Flux.fromIterable(geoData.data());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
