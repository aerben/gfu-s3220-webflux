package digital.erben.reactiveweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CorsSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http
            .csrf(CsrfSpec::disable)
            .cors(CorsSpec::disable)
            .authorizeExchange(a -> a
                .pathMatchers("/", "/error", "/webjars/**", "/logout")
                .permitAll()
                .anyExchange()
                .authenticated()
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .logout(l -> l.logoutUrl("/logout"))
            .oauth2Login(withDefaults())
            .build();
    }

    @Bean
    public WebClient oauth2WebClient(ServerOAuth2AuthorizedClientRepository clientRepository,
                                     ReactiveClientRegistrationRepository reactiveClientRegistrationRepository) {
        return WebClient.builder()
            .filter(new ServerOAuth2AuthorizedClientExchangeFilterFunction(reactiveClientRegistrationRepository,
                clientRepository))
            .build();
    }

    @Bean
    public ServerOAuth2AuthorizedClientRepository authorizedClientRepository() {
        return new WebSessionServerOAuth2AuthorizedClientRepository();
    }
}
