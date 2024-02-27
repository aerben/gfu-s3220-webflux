package digital.erben.reactiveweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MessageController {

	private final MessageService messages;

	public MessageController(MessageService messages) {
		this.messages = messages;
	}

	@GetMapping("/message")
	public Mono<String> message() {
		return this.messages.findMessage();
	}

	@GetMapping("/secret")
	public Mono<String> secretMessage() {
		return this.messages.findSecretMessage();
	}

}
