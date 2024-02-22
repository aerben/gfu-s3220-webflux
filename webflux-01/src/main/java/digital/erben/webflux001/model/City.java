package digital.erben.webflux001.model;

public record City(
    int id,
    String wikiDataId,
    String type,
    String city,
    String name,
    String country,
    String countryCode,
    String region,
    String regionCode,
    String regionWdId,
    double latitude,
    double longitude,
    int population
) {
}
