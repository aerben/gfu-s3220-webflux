package digital.erben.reactiveweb;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

import java.net.URI;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class ReactiveWebApplication {

    private final WebClient webClient;

    public ReactiveWebApplication(WebClient webClient) {
        this.webClient = webClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveWebApplication.class, args);
    }

    @GetMapping("/user")
    public Mono<Map<String, Object>> user(@AuthenticationPrincipal OAuth2User principal) {
        return Mono.just(Map.of("name", principal.getAttributes().getOrDefault("name", "NO NAME")));
    }

    @PostMapping("/user/logProfile")
    public Mono<Void> trackUser(@RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient authorizedClient) {
        return
            webClient.get()
                .uri(URI.create("https://api.github.com/user"))
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(System.out::println)
                .doOnError(System.err::println)
                .then();
    }

}
