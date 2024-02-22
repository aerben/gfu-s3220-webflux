package digital.erben.webflux001.model;

import java.util.List;

public record CitiesResponse(
    List<City> data,
    List<Link> links,
    Metadata metadata
) {
}
