package digital.erben.reactiveweb.bookmarks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

public class BookmarksControllerTest {

    private BookmarksController controller;

    @Mock
    private BookmarkService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new BookmarksController(service);
    }

    @Test
    public void getBookmark() {
        Bookmark bookmark = new Bookmark(1L, 1L);
        when(service.findById(1L)).thenReturn(Mono.just(bookmark));

        StepVerifier.create(controller.getBookmark(1L))
            .expectNext(bookmark)
            .verifyComplete();
    }

    @Test
    public void getAllBookmarks() {
        Bookmark bookmark1 = new Bookmark(1L, 1L);
        Bookmark bookmark2 = new Bookmark(2L, 2L);
        when(service.findAll()).thenReturn(Flux.just(bookmark1, bookmark2));

        StepVerifier.create(controller.getAllBookmarks())
            .expectNext(bookmark1, bookmark2)
            .verifyComplete();
    }

    @Test
    public void createBookmark() {
        Bookmark bookmark = new Bookmark(1L, 1L);
        when(service.save(bookmark)).thenReturn(Mono.just(bookmark));

        StepVerifier.create(controller.createBookmark(bookmark))
            .expectNext(bookmark)
            .verifyComplete();
    }

    @Test
    public void updateBookmark() {
        Bookmark bookmark = new Bookmark(1L, 1L);
        when(service.findById(1L)).thenReturn(Mono.just(bookmark));
        when(service.save(bookmark)).thenReturn(Mono.just(bookmark));

        StepVerifier.create(controller.updateBookmark(1L, bookmark))
            .expectNext(ResponseEntity.ok(bookmark))
            .verifyComplete();
    }

    @Test
    public void updateBookmarkNotFound() {
        Bookmark bookmark = new Bookmark(1L, 1L);
        when(service.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(controller.updateBookmark(1L, bookmark))
            .expectNext(ResponseEntity.notFound().build())
            .verifyComplete();
    }

    @Test
    public void deleteBookmark() {
        when(service.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(controller.deleteBookmark(1L))
            .verifyComplete();
    }
}
