package digital.erben.webflux001.examples;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import digital.erben.webflux001.model.Bookmark;
import java.net.URI;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Testcontainers
public class ErrorHandlingExample {

	public static final DockerImageName MOCKSERVER_IMAGE = DockerImageName.parse("mockserver/mockserver")
			.withTag("mockserver-" + MockServerClient.class.getPackage().getImplementationVersion());

	@Container
	public MockServerContainer mockServer = new MockServerContainer(MOCKSERVER_IMAGE);

	@Test
	public void shouldHandleErrors() throws JsonProcessingException {
		mockServer.start();
		try (var ignored = setupMockServerReturning400()) {
			WebClient webClient = WebClient.create();

			URI uri = URI.create(mockServer.getEndpoint() + "/bookmarks");
			Flux<Bookmark> result = webClient.get()
				.uri(uri)
				.accept(org.springframework.http.MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(Bookmark.class);

			StepVerifier.create(result)
				.expectNextCount(10)
				.verifyComplete();

			Hooks.resetOnOperatorDebug(); // enabled by IntelliJ on startup
			Mono<Bookmark> postBookmark = webClient.post()
				.uri(uri)
				.retrieve()
				.bodyToMono(Bookmark.class);

			StepVerifier.create(postBookmark)
				.expectNextCount(1)
				.verifyComplete();
		}
	}

	private MockServerClient setupMockServerReturning400() throws JsonProcessingException {
		MockServerClient mockServerClient = new MockServerClient(mockServer.getHost(), mockServer.getServerPort());

		mockServerClient
			.when(request().withPath("/bookmarks").withMethod("POST"))
			.respond(response().withStatusCode(400));

		List<Bookmark> bookmarks = IntStream.range(0, 10).mapToObj(i -> new Bookmark(i, i, i)).toList();
		String serialized = new ObjectMapper().writeValueAsString(bookmarks);

		mockServerClient
				.when(request()
					.withPath("/bookmarks")
					.withMethod("GET")
					.withHeader(Header.header("Accept", MediaType.APPLICATION_JSON.toString())))
				.respond(response()
					.withBody(serialized)
					.withContentType(MediaType.APPLICATION_JSON)
				);

		return mockServerClient;
	}
}
