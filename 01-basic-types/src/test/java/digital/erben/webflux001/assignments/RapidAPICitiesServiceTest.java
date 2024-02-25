package digital.erben.webflux001.assignments;

import digital.erben.webflux001.model.City;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class RapidAPICitiesServiceTest {

	private RapidAPICitiesService remoteGeoDataService;

	@BeforeEach
	public void setupService() {
		this.remoteGeoDataService = new RapidAPICitiesService();
	}

	@Test
	void retrieveCitiesForCountryTest() {
		Mono<List<City>> de = remoteGeoDataService.retrieveCitiesForCountry("DE");
		StepVerifier.create(de).expectNextMatches(list -> list.size() == 100).verifyComplete();
	}

	@Test
	void retrieveCitiesForPopulationSmallerThanTest() {
		Mono<List<City>> de = remoteGeoDataService.retrieveCitiesWithPopulationSmallerThan("DE", 10);
		StepVerifier.create(de)
				.expectNextMatches(
						list -> list.size() == 100 && list.stream().allMatch(city -> city.population() <= 10))
				.verifyComplete();
	}

	@Test
	void retrieveCitiesForPopulationLargerThanTest() {
		Mono<List<City>> de = remoteGeoDataService.retrieveCitiesWithPopulationGreaterThan("DE", 3000000);
		List<City> block = de.block();
		System.out.println(block);
		StepVerifier.create(de).expectNextMatches(list -> list.stream().allMatch(city -> city.population() >= 3000000))
				.verifyComplete();
	}
}
