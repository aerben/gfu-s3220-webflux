package digital.erben.webflux001;

import java.util.List;

public record CitiesResponse(List<City> data, List<Link> links, Metadata metadata) {
}
