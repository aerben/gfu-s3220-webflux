package digital.erben.reactiveweb.ratings;

public record Rating(String id, int rating, String restaurantId, String userId, boolean locked) {
    public Rating withId(String id) {
        return new Rating(id, this.rating, this.restaurantId, this.userId, this.locked);
    }
}
