package digital.erben.reactiveweb.cities;

import digital.erben.reactiveweb.cities.model.City;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CitiesController {

	private final CitiesRepository ds;

	public CitiesController(CitiesRepository ds) {
		this.ds = ds;
	}

	/**
	 * Get cities from the dataset
	 *
	 * @param population
	 *            optional parameter. If given, restricts the returned cities to
	 *            ones with population greater than the given value
	 * @return Cities from the dataset
	 */
	@GetMapping("/cities")
	public Flux<City> getAllCities(@RequestParam(value = "fromPopulation", required = false) Long population) {
		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * Retrieve a city by its ID.
	 *
	 * @param id
	 *            The ID of the city to retrieve.
	 * @return The city with the specified ID if found, or a 404 Not Found response
	 *         if not found.
	 */
	@GetMapping("/cities/{id}")
	public Mono<ResponseEntity<City>> getCityById(@PathVariable("id") int id) {
		throw new UnsupportedOperationException("Not implemented");
	}
}
