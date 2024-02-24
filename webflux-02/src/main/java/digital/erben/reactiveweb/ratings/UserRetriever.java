package digital.erben.reactiveweb.ratings;

import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class UserRetriever {

    private final List<User> users = IntStream.range(0, 10).mapToObj(i -> new User(
        i, new Faker().name().firstName(), new Faker().name().lastName()
    )).toList();

    public Mono<User> findUser(Integer id) {
        return Mono.justOrEmpty(users.stream().filter(user -> user.id() == id).findAny());
    }
}
