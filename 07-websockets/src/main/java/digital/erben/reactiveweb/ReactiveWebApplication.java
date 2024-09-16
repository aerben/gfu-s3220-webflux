package digital.erben.reactiveweb;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

@SpringBootApplication
public class ReactiveWebApplication {

	private final EventReceiver eventEmitterHandler;

	public ReactiveWebApplication(EventReceiver eventEmitterHandler) {
		this.eventEmitterHandler = eventEmitterHandler;
    }

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebApplication.class, args);
	}

	@Bean
	public HandlerMapping webSocketHandlerMapping() {
		Map<String, WebSocketHandler> map = new HashMap<>();
		map.put("/event-emitter", eventEmitterHandler);

		SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
		handlerMapping.setOrder(1);
		handlerMapping.setUrlMap(map);
		return handlerMapping;
	}
}
