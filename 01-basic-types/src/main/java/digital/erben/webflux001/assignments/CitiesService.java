package digital.erben.webflux001.assignments;

import digital.erben.webflux001.model.City;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Provides Cities data from a repository. */
@Component
public class CitiesService {

	private final CitiesRepository repository;

	public CitiesService(CitiesRepository repository) {
		this.repository = repository;
	}

	/**
	 * Retrieves a Flux of cities with a specified limit from the cached dataset.
	 *
	 * @param limit
	 *            The maximum number of cities to retrieve.
	 * @return A Flux of GeoDataModel.City objects with the specified limit.
	 */
	public Flux<City> getCitiesWithLimit(long limit) {
		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * Retrieves a Flux of cities with a population greater than or equal to the
	 * specified cutoff (inclusive).
	 *
	 * @param cutoffInclusive
	 *            The minimum population cutoff value.
	 * @return A Flux of GeoDataModel.City objects with population greater than or
	 *         equal to the cutoff.
	 */
	public Flux<City> getCitiesWithPopulationGreaterThanInclusive(long cutoffInclusive) {
		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * Retrieves a Flux of cities sorted by population in ascending order.
	 *
	 * @return A Flux of GeoDataModel.City objects sorted by population in ascending
	 *         order.
	 */
	public Flux<City> getCitiesSortedByPopulationAscending() {
		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * Retrieves the first city that matches the given predicate from the cached
	 * dataset.
	 *
	 * @param predicate
	 *            The predicate to match the city against.
	 * @return A Mono that emits the first GeoDataModel.City object that matches the
	 *         predicate.
	 */
	public Mono<City> getFirstCityThatMatches(Predicate<City> predicate) {
		throw new UnsupportedOperationException("Not implemented");
	}
}
