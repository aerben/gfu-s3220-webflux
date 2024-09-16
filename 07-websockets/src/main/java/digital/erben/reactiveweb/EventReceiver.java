package digital.erben.reactiveweb;

import static java.time.LocalTime.now;
import static java.util.UUID.randomUUID;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EventReceiver implements WebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        Flux<WebSocketMessage> messageFlux = Flux.interval(Duration.ofMillis(1000L))
            .map(ignored -> new Event(randomUUID().toString(), now().toString()).toString())
            .map(webSocketSession::textMessage);

        return webSocketSession
            .send(messageFlux)
            .and(webSocketSession.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnEach(message -> logger.info(message.get()))
            );
    }
}
