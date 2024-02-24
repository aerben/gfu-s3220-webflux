package digital.erben.reactiveweb.ratings;

public record Rating(Integer id, int rating, int restaurantId, int userId, boolean locked) {
}
