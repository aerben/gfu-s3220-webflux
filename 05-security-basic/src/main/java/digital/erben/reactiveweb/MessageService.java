package digital.erben.reactiveweb;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MessageService {

	@PreAuthorize("authenticated")
	public Mono<String> findMessage() {
		return Mono.just("Hello User!");
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Mono<String> findSecretMessage() {
		return Mono.just("Hello Admin!");
	}

}
