package digital.erben.webflux001.examples;

import net.datafaker.Faker;
import net.datafaker.providers.base.Lorem;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class IntermediatesExample implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        Lorem lorem = new Faker().lorem();
        var publisher =
            Flux.<String>generate(sink -> {
                    sink.next(lorem.paragraph());
                    sink.complete();
                })
                .repeat(10)
                .flatMapIterable(line -> Arrays.asList(line.split(" ")))
                .publish()
                .autoConnect(3);

        var totalCount = publisher.count();

        var wordCountsByFirstCharacter = publisher
            .groupBy(word -> word.toLowerCase().charAt(0))
            .flatMap(groupToCount())
            .collectMap(Map.Entry::getKey, Map.Entry::getValue);

        var wordCountsByLength = publisher
            .groupBy(String::length)
            .flatMap(groupToCount())
            .collectMap(Map.Entry::getKey, Map.Entry::getValue);

        Mono.zip(totalCount, wordCountsByLength, wordCountsByFirstCharacter)
            .log()
            .subscribe(results -> {
                System.out.println("Total word count: " + results.getT1());
                System.out.println("Word counts by first character: " + results.getT2());
                System.out.println("Word counts by length: " + results.getT3());
            }, Throwable::printStackTrace);

    }

    private static <K> Function<GroupedFlux<K, String>, Publisher<? extends Map.Entry<K, Long>>> groupToCount() {
        return (group) -> group.count().map(count -> Map.entry(group.key(), count));
    }
}
