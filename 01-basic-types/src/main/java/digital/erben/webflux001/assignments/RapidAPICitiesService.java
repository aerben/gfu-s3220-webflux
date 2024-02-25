package digital.erben.webflux001.assignments;

import digital.erben.webflux001.model.City;
import java.util.List;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/** Uses RapidAPI to fetch latest city data */
@Component
public class RapidAPICitiesService {

	/**
	 * You can find all relevant request parameters here:
	 * <a href="https://rapidapi.com/wirefreethought/api/geodb-cities/">...</a>
	 */
	private static final String BASE_URL = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities?limit=100";

	private static final String RAPID_URL_API_KEY_HEADER = "X-RapidAPI-Key";
	private static final String RAPID_URL_API_KEY_HEADER_VALUE = "16e1546221msh6270284dc1921b8p16483fjsnf33be4140b48";
	private static final String RAPID_URL_API_HOST_HEADER = "X-RapidAPI-Host";
	private static final String RAPID_URL_API_HOST_HEADER_VALUE = "wft-geo-db.p.rapidapi.com";

	/**
	 * Retrieves a list of cities for a given country code.
	 *
	 * @param countryCode
	 *            the country code
	 * @return a list of cities for the given country code
	 */
	public Mono<List<City>> retrieveCitiesForCountry(String countryCode) {
		// hint:
		// WebClient.create().get()
		// .uri(URI.create(
		// BASE_URL + "&countryIds=" + countryCode))
		// .header(RAPID_URL_API_KEY_HEADER, RAPID_URL_API_KEY_HEADER_VALUE)
		// .header(RAPID_URL_API_HOST_HEADER, RAPID_URL_API_HOST_HEADER_VALUE)
		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * Retrieves a list of cities that have a specified number of inhabitants or
	 * more
	 *
	 * @param countryCode
	 *            the country to check for
	 * @param value
	 *            the comparison value
	 * @return the matching cities
	 */
	public Mono<List<City>> retrieveCitiesWithPopulationGreaterThan(String countryCode, int value) {
		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * Retrieves a list of cities that have a specified number of inhabitants or
	 * less
	 *
	 * @param countryCode
	 *            the country to check for
	 * @param value
	 *            the comparison value
	 * @return the matching cities
	 */
	public Mono<List<City>> retrieveCitiesWithPopulationSmallerThan(String countryCode, int value) {
		throw new UnsupportedOperationException("Not implemented");
	}
}
