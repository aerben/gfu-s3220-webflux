package digital.erben.reactiveweb;

import java.io.IOException;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
public class ReactiveWebApplication {
	@PostConstruct
	public void setupReactorMetrics() {
		Schedulers.enableMetrics();
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(ReactiveWebApplication.class, args);
	}

	@Bean
	public RestClient restClient() {
		return RestClient.create();
	}

	@Bean
	public WebClient webClient() {
		return WebClient.create();
	}
}
